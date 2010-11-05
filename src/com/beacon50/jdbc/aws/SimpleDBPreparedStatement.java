package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.*;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang.StringUtils;

import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SimpleDBPreparedStatement extends AbstractPreparedStatement {

    private SimpleDBConnection connection;
    private CCJSqlParserManager parserManager;
    private String sql;
    private List<String> args;

    protected SimpleDBPreparedStatement(String sql, SimpleDBConnection conn) {
        this.sql = sql.trim();
        this.connection = conn;
        this.parserManager = new CCJSqlParserManager();
        this.args = new LinkedList<String>();
    }

    public int executeUpdate() throws SQLException {
        try {

            if (StringUtils.startsWithIgnoreCase(this.sql, "INSERT")) {
                return handleInsert();
            } else if (StringUtils.startsWithIgnoreCase(this.sql, "DELETE")) {
                // return handleDelete(sql);
                return 0;
            } else if (StringUtils.startsWithIgnoreCase(this.sql, "UPDATE")) {
                return handleUpdate();
            } else {
                throw new SQLException("statement type " + this.sql
                        + " not implemented yet");
            }
        } catch (JSQLParserException e1) {
            throw new SQLException("SQL statement was malformed");
        }
    }

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
                attributes.add(new ReplaceableAttribute().withName(column.getColumnName())
                        .withValue(this.args.get(count)));
            }
            count++;
        }
        if (id == null) {
            id = String.valueOf(Math.abs(new Random(new java.util.Date().getTime()).nextLong()));
        }
        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();
        data.add(new ReplaceableItem().withName(id).withAttributes(attributes));
        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        return 1;
    }

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
            List<Expression> expressions = (List<Expression>) update.getExpressions();
            List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();

            int count = 0;
            for (int x = 0; x < sizeSets; x++) {
                String attributeName = columns.get(x).getColumnName();
                // System.out.println("looking for attribute name " +
                // attributeName);
                String replaceValue = this.args.get(x);
                // System.out.println("replace value is  " + replaceValue);
                attributes.add(new ReplaceableAttribute().withName(attributeName)
                        .withValue(replaceValue).withReplace(true));
                count++;
            }
            data.add(new ReplaceableItem().withName(item.getName()).withAttributes(
                    attributes));
            returnval++;
        }

        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
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

    public void setObject(int parameterIndex, Object x) throws SQLException {
    	System.out.println("setObject was called with " + x);
        this.setString(parameterIndex, x.toString());
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
}
