package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.*;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.DeleteDeParser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class SimpleDBStatement extends AbstractStatement {
    final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.SimpleDBStatement");

    private SimpleDBConnection connection;
    private CCJSqlParserManager parserManager;
    private ResultSet resultSet;
    private int updateCnt = -1;

    protected SimpleDBStatement(Connection conn) {
        this.connection = (SimpleDBConnection) conn;
        this.parserManager = new CCJSqlParserManager();
    }

    public boolean execute(String sql) throws SQLException {
        try {
            this.updateCnt = -1;
            if (StringUtils.startsWithIgnoreCase(sql, "INSERT")) {
                return (handleInsert(sql) > 0) ? true : false;
            } else if (StringUtils.startsWithIgnoreCase(sql, "DELETE")) {
                return (handleDelete(sql) > 0) ? true : false;
            } else if (StringUtils.startsWithIgnoreCase(sql, "UPDATE")) {
                return (handleUpdate(sql) > 0) ? true : false;
            } else if (StringUtils.startsWithIgnoreCase(sql, "SELECT")) {
                this.resultSet = this.executeQuery(sql);
                return (this.resultSet != null) ? true : false;
            } else {
                return false;
            }
        } catch (JSQLParserException e1) {
            throw new SQLException("SQL statement was malformed");
        }
    }

    public ResultSet getResultSet() throws SQLException {
        return this.resultSet;
    }

    public Connection getConnection() throws SQLException {
        return this.connection;
    }

    /**
     * SELECT * FROM MY_DOMAIN
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            if (sql.contains("count(*)")) {
                return this.handleSelectCount(sql);
            } else {
                Select select = (Select) this.parserManager.parse(new StringReader(sql));
                String origDomain = ((PlainSelect) select.getSelectBody()).getFromItem().toString();
                String domain = this.getReadTableName(((PlainSelect) select.getSelectBody()).getFromItem());
                sql = sql.replaceAll(origDomain, SimpleDBUtils.quoteName(domain));
                SelectRequest selectRequest = new SelectRequest(sql);
                List<Item> items = this.connection.getSimpleDB().select(selectRequest)
                        .getItems();
                return getSimpleDBResultSet(domain, items);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("exception caught in executing query");
        }
    }

    protected ResultSet getSimpleDBResultSet(String domain, List<Item> items) {
        return new SimpleDBResultSet(this.connection, items, domain);
    }

    private ResultSet handleSelectCount(final String sql) throws JSQLParserException {
        final Select select = (Select) this.parserManager.parse(new StringReader(sql));
        String domain = this.getReadTableName(((PlainSelect) select.getSelectBody()).getFromItem());
        final int count = connection.getSimpleDB().domainMetadata(
                new DomainMetadataRequest(domain)).getItemCount();
        return getSimpleDBResultSet(domain, new ArrayList<Item>(Collections
                .nCopies(1, new Item("", new ArrayList<Attribute>(Collections.nCopies(1,
                new Attribute("count", Integer.toString(count))))))));
    }


    public int executeUpdate(String sql) throws SQLException {
        try {
            this.updateCnt = -1;
            if (StringUtils.startsWithIgnoreCase(sql, "INSERT")) {
                return handleInsert(sql);
            } else if (StringUtils.startsWithIgnoreCase(sql, "DELETE")) {
                return handleDelete(sql);
            } else if (StringUtils.startsWithIgnoreCase(sql, "UPDATE")) {
                return handleUpdate(sql);
            } else {
                throw new SQLException("statement type " + sql + " not implemented yet");
            }
        } catch (JSQLParserException e1) {
            throw new SQLException("SQL statement was malformed");
        }
    }

    @SuppressWarnings("unchecked")
    private int handleUpdate(String sql) throws JSQLParserException {
        int returnval = 0;
        Update update = (Update) this.parserManager.parse(new StringReader(sql));
        String domain = this.getWriteTableName(update.getTable());

        String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) + " WHERE "
                + update.getWhere().toString();

        SelectRequest selectRequest = new SelectRequest(qury);
        List<Item> items = this.connection.getSimpleDB().select(selectRequest).getItems();
        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();
        for (Item item : items) {

            List<Column> columns = (List<Column>) update.getColumns();
            List<Expression> expressions = (List<Expression>) update.getExpressions();
            List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();

            int count = 0;
            for (Column column : columns) {
                String attributeName = column.getColumnName();
                SQLExpressionVisitor vistor = new SQLExpressionVisitor();
                Expression exprs = expressions.get(count);
                String value = vistor.getValue(exprs);
                attributes.add(getWriteReplaceableAttribute(attributeName, value, true));
                count++;
            }
            data.add(getWriteReplaceableItem(attributes, item.getName()));
            returnval++;
        }
        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        this.updateCnt = returnval;
        return returnval;
    }

    /**
     * @param sql
     * @return
     */
    private int handleDelete(String sql) throws JSQLParserException {
        log.info("handle delete incoming query: " + sql);
        int returnval = 0;
        Delete delete = (Delete) this.parserManager.parse(new StringReader(sql));

        String domain = this.getWriteTableName(delete.getTable());
        log.info("handle delete domain is: " + domain);
        Expression express = delete.getWhere();

        SQLDeleteExpressionVisitor vistor = new SQLDeleteExpressionVisitor();
        vistor.setValue(express);

        String[] vals = express.toString().split("=");

        log.info("handle delete expression vals are " + vals);

        DeleteAttributesRequest req = new DeleteAttributesRequest();
        req.setDomainName(domain);
        // if vals doesn't include id, then find one with an id
        if (vals[0].equalsIgnoreCase("id")) {

            String value = vals[1];
            if (new Character(value.charAt(0)).toString().equals("'")) {
                value = value.substring(1, (value.length() - 1));
            }
            req.setItemName(value);
            this.connection.getSimpleDB().deleteAttributes(req);
            returnval = 1;
        } else {
            String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) + " WHERE "
                    + express.toString();
            log.info("handle delete 1st select query: " + qury);
            SelectRequest selectRequest = new SelectRequest(qury);
            List<Item> items = this.connection.getSimpleDB().select(selectRequest)
                    .getItems();

            for (Item item : items) {
                req.setItemName(item.getName());
                this.connection.getSimpleDB().deleteAttributes(req);
                returnval++;
            }
        }
        return returnval;
    }

    public int getUpdateCount() throws SQLException {
        return this.updateCnt;
    }

    /**
     * @param sql
     * @return at this point an int hardcoded to 1
     * @throws JSQLParserException
     */
    @SuppressWarnings("unchecked")
    private int handleInsert(String sql) throws JSQLParserException {
        Insert insert = (Insert) this.parserManager.parse(new StringReader(sql));
        String domain = this.getWriteTableName(insert.getTable());
        try {
            this.connection.getSimpleDB().createDomain(new CreateDomainRequest(domain));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Column> columns = (List<Column>) insert.getColumns();
        List list = ((ExpressionList) insert.getItemsList()).getExpressions();

        List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
        int count = 0;
        String id = null;

        for (Column column : columns) {

            SQLExpressionVisitor vistor = new SQLExpressionVisitor();
            String expressionVal = vistor.getValue((Expression) list.get(count));

            if (column.getColumnName().equalsIgnoreCase("id")) {
                id = expressionVal;
            } else {
                attributes.add(getWriteReplaceableAttribute(column.getColumnName(),
                        expressionVal, false));
            }
            count++;
        }

        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(getWriteReplaceableItem(attributes, id));
        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        return 1;
    }

    protected String getWriteTableName(Table table) {
        return table.getName();
    }

    protected String getReadTableName(FromItem fromItem) {
        return fromItem.toString();
    }


    /**
     * @param attributes
     * @param id
     * @return ReplaceableItem
     */
    protected ReplaceableItem getReadReplaceableItem(List<ReplaceableAttribute> attributes,
                                                     String id) {
        return this.getWriteReplaceableItem(attributes, id);
    }

    protected ReplaceableItem getWriteReplaceableItem(List<ReplaceableAttribute> attributes,
                                                      String id) {
        return new ReplaceableItem().withName(id).withAttributes(attributes);
    }

    /**
     * @param column
     * @param expressionVal
     * @return ReplaceableAttribute
     */
    protected ReplaceableAttribute getWriteReplaceableAttribute(String name,
                                                                String expressionVal, boolean replace) {
        return this.getReadReplaceableAttribute(name, expressionVal, replace);
    }

    protected ReplaceableAttribute getReadReplaceableAttribute(String name,
                                                               String expressionVal, boolean replace) {
        return new ReplaceableAttribute().withName(name).withValue(expressionVal)
                .withReplace(replace);
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return this.executeUpdate(sql);
    }

    public void close() throws SQLException {
        this.updateCnt = -1;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return new SimpleDBResultSet(this.connection,
                new ArrayList<Item>(), "");
    }
}
