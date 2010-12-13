package com.beacon50.jdbc.aws;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;

/**
 *
 */
public class SimpleDBPreparedStatement extends AbstractPreparedStatement {
	
	final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.SimpleDBPreparedStatement");
    private SimpleDBConnection connection;
    private CCJSqlParserManager parserManager;
    private String sql;
    private List<String> args;
    private int updateCount = 0;
    private ResultSet resultSet;
    
    protected SimpleDBPreparedStatement(String sql, SimpleDBConnection conn) {
        this.sql = sql.trim();
        this.connection = conn;
        this.parserManager = new CCJSqlParserManager();
        this.args = new LinkedList<String>();
    }

    public int executeUpdate() throws SQLException {
        try {
        	log.info("executeUpdate() called with sql: " + this.sql);
            if (StringUtils.startsWithIgnoreCase(this.sql, "INSERT")) {
                return handleInsert();
            } else if (StringUtils.startsWithIgnoreCase(this.sql, "DELETE")) {
                return handleDelete();                
            } else if (StringUtils.startsWithIgnoreCase(this.sql, "UPDATE")) {
                return handleUpdate();
            } else if (StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
                this.resultSet = this.executeQuery();
                return (this.resultSet != null) ? 1 : 0;
            } else {
                throw new SQLException("statement type " + this.sql
                        + " not implemented yet");
            }
        } catch (JSQLParserException e1) {
            throw new SQLException("SQL statement was malformed");
        }
    }
    
    public ResultSet executeQuery() throws SQLException {
        try {
        	log.info("select stmt made for PreparedStmt: " + this.sql);
            Select select = (Select) this.parserManager.parse(new StringReader(sql));
            
            int qCount = StringUtils.countMatches(sql, "?");            
            for(int x = 0; x < qCount; x++){
            	String value = this.args.get(x);
            	this.sql = this.sql.replaceFirst("\\?", SimpleDBUtils.quoteValue(value));
            }
            
            log.info("after replacing ?'s, new sql is : " + this.sql);
            
            String origDomain = ((PlainSelect) select.getSelectBody()).getFromItem().toString();
            String domain =((PlainSelect) select.getSelectBody()).getFromItem().toString();
            sql = sql.replaceAll(origDomain, SimpleDBUtils.quoteName(domain));
            SelectRequest selectRequest = new SelectRequest(sql);
            List<Item> items = this.connection.getSimpleDB().select(selectRequest)
                    .getItems();
            return getSimpleDBResultSet(domain, items);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("exception caught in executing query");
        }
    }

    protected ResultSet getSimpleDBResultSet(String domain, List<Item> items) {
        return new SimpleDBResultSet(this.connection, items, domain);
    }
    
    private int handleDelete() throws JSQLParserException {		
    	int returnval = 0;
        Delete delete = (Delete) this.parserManager.parse(new StringReader(this.sql));
        String domain = delete.getTable().getName();
        Expression express = delete.getWhere();

        String[] vals = express.toString().split("=");

        DeleteAttributesRequest req = new DeleteAttributesRequest();
        req.setDomainName(domain);
        //if vals doesn't include id, then find one with an id
        if (vals[0].equalsIgnoreCase("id")) {
            String value = SimpleDBUtils.quoteValue(this.args.get(0));            
            req.setItemName(value);
            this.connection.getSimpleDB().deleteAttributes(req);
            returnval = 1;
        } else {
            String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) +
                    " WHERE " + express.toString();
                        
            int argsSize = this.args.size();
            for (int x = 0; x < argsSize; x++) {
                qury = qury.replaceFirst("\\?", SimpleDBUtils.quoteValue(this.args.get(x)));
            }
            
            SelectRequest selectRequest = new SelectRequest(qury);
            List<Item> items = this.connection.getSimpleDB().select(selectRequest).getItems();

            for (Item item : items) {
                req.setItemName(item.getName());
                this.connection.getSimpleDB().deleteAttributes(req);
                returnval++;
            }
        }
        return returnval;
	}

	@SuppressWarnings("unchecked")
	private int handleInsert() throws JSQLParserException {
        Insert insert = (Insert) this.parserManager.parse(new StringReader(sql));
        String domain = insert.getTable().getName();

        try {
            this.connection.getSimpleDB().createDomain(new CreateDomainRequest(domain));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Column> columns = (List<Column>) insert.getColumns();
        List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
        int count = 0;
        String id = null;

        for (Column column : columns) {
            if (column.getColumnName().equalsIgnoreCase("id")) {
                id = this.args.get(count);
            } else {
                attributes.add(this.getReplaceableAttribute(column.getColumnName(), 
                				this.args.get(count), false));
            }
            count++;
        }
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();
        data.add(this.getReplaceableItem(attributes, id));
        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        return 1;
    }

    @SuppressWarnings("unchecked")
	private int handleUpdate() throws JSQLParserException, SQLException {
        int returnval = 0;
        Update update = (Update) this.parserManager.parse(new StringReader(this.sql));
        String domain = update.getTable().getName();

        String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) +
                " WHERE " + update.getWhere().toString();

        int sizeSets = update.getColumns().size(); // this is the size of sets
        int argsSize = this.args.size();
        for (int x = sizeSets; x < argsSize; x++) {
            qury = qury.replaceFirst("\\?", SimpleDBUtils.quoteValue(this.args.get(x)));
        }

        SelectRequest selectRequest = new SelectRequest(qury);
        List<Item> items = this.connection.getSimpleDB().select(selectRequest).getItems();
        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        for (Item item : items) {
            List<Column> columns = (List<Column>) update.getColumns();            
            List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();

            int count = 0;
            for (int x = 0; x < sizeSets; x++) {
                String attributeName = columns.get(x).getColumnName();
                // System.out.println("looking for attribute name " +
                // attributeName);
                String replaceValue = this.args.get(x);
                // System.out.println("replace value is  " + replaceValue);
                attributes.add(/*new ReplaceableAttribute().withName(attributeName)
                        .withValue(replaceValue).withReplace(true)*/
                		this.getReplaceableAttribute(attributeName, replaceValue, true));
                count++;
            }
            data.add(/*new ReplaceableItem().withName(item.getName()).withAttributes(
                    attributes)*/
            		this.getReplaceableItem(attributes, item.getName()));
            returnval++;
        }

        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        this.updateCount = returnval;
        return returnval;

    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeDate(x));
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeZeroPadding(x, 5));
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        this.args.add(parameterIndex - 1, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeZeroPadding((float) x, 5));
    }
    //todo consider refactoring into chain
    public void setObject(int parameterIndex, Object x) throws SQLException {
        if (x instanceof Double) {
            this.setDouble(parameterIndex, (Double) x);
        } else if (x instanceof Float) {
            this.setFloat(parameterIndex, (Float) x);
        } else if (x instanceof Integer) {
            this.setInt(parameterIndex, (Integer) x);
        } else if (x instanceof Long) {
            this.setLong(parameterIndex, (Long) x);
        } else {
            this.setString(parameterIndex, x.toString());
        }
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeZeroPadding(x.floatValue(), 5));
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.setString(parameterIndex, Boolean.toString(x));
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeZeroPadding(x, 5));
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        this.args.add(parameterIndex - 1, SimpleDBUtils.encodeZeroPadding(x, 5));
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.setString(parameterIndex, x.toString());
    }
    
    /**
	 * 
	 * @param attributes
	 * @param id
	 * @return ReplaceableItem
	 */
	protected ReplaceableItem getReplaceableItem(List<ReplaceableAttribute> attributes,
			String id) {
		return new ReplaceableItem().withName(id).withAttributes(attributes);
	}

	/**
	 * 
	 * @param column
	 * @param expressionVal
	 * @return ReplaceableAttribute
	 */
	protected ReplaceableAttribute getReplaceableAttribute(String name,
			String expressionVal, boolean replace) {
		return new ReplaceableAttribute().withName(name).withValue(expressionVal)
				.withReplace(replace);
	}

	public boolean execute() throws SQLException {
		int val = this.executeUpdate();
		if(val > 0){
			return true;
		}else{
			return false;
		}
	}

	public int getUpdateCount() throws SQLException {
		return this.updateCount;
	}

	public void close() throws SQLException {
		this.updateCount = 0;
		this.sql = null;
		this.resultSet = null;
	}

	public ResultSet getResultSet() throws SQLException {
		return this.resultSet;
	}

	
}
