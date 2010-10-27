package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.*;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang.StringUtils;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SimpleDBStatement extends AbstractStatement {


    private SimpleDBConnection connection;
    private CCJSqlParserManager parserManager;

    protected SimpleDBStatement(Connection conn) {
        this.connection = (SimpleDBConnection) conn;
        this.parserManager = new CCJSqlParserManager();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            Select select = (Select) this.parserManager.parse(new StringReader(sql));
            String domain = ((PlainSelect) select.getSelectBody()).getFromItem().toString();
            sql = sql.replaceAll(domain, SimpleDBUtils.quoteName(domain));
            SelectRequest selectRequest = new SelectRequest(sql);
            List<Item> items = this.connection.getSimpleDB().select(selectRequest).getItems();
            return new SimpleDBResultSet(items);
        } catch (Exception e) {
            throw new SQLException("exception caught in executing query");
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        try {

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

    private int handleUpdate(String sql) throws JSQLParserException {
        int returnval = 0;
        Update update = (Update) this.parserManager.parse(new StringReader(sql));
        String domain = update.getTable().getName();

        System.out.println("domin is " + domain);

        //find it first
        String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) +
                " WHERE " + update.getWhere().toString();

        System.out.println(qury);
        SelectRequest selectRequest = new SelectRequest(qury);
        List<Item> items = this.connection.getSimpleDB().select(selectRequest).getItems();
        System.out.println("found " + items.size() + " items, proceeding to update");
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
                System.out.println("attribute name is " + attributeName + " and value is " + value);
                attributes.add(new ReplaceableAttribute().withName(attributeName).withValue(value).withReplace(true));
                count++;
            }
            System.out.println("adding this data for name" + item.getName() + " and atts " + attributes);
            data.add(new ReplaceableItem().withName(item.getName()).withAttributes(attributes));

            returnval++;
        }
        //batch them up and call it
        this.connection.getSimpleDB().batchPutAttributes(new BatchPutAttributesRequest(domain, data));
        return returnval;
    }

    /**
     * @param sql
     * @return
     */
    private int handleDelete(String sql) throws JSQLParserException {
        int returnval = 0;
        Delete delete = (Delete) this.parserManager.parse(new StringReader(sql));
        String domain = delete.getTable().getName();
        Expression express = delete.getWhere();

        String[] vals = express.toString().split("=");

        DeleteAttributesRequest req = new DeleteAttributesRequest();
        req.setDomainName(domain);
        //if vals doesn't include id, then find one with an id
        if (vals[0].equalsIgnoreCase("id")) {

            String value = vals[1];
            if (new Character(value.charAt(0)).toString().equals("'")) {
                value = value.substring(1, (value.length() - 1));
            }
            req.setItemName(value);
            this.connection.getSimpleDB().deleteAttributes(req);
            returnval = 1;
        } else {
            String qury = "SELECT * FROM " + SimpleDBUtils.quoteName(domain) +
                    " WHERE " + express.toString();
            System.out.println(qury);
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

    /**
     * @param sql
     * @return at this point an int hardcoded to 1
     * @throws JSQLParserException
     */
    private int handleInsert(String sql) throws JSQLParserException {
        Insert insert = (Insert) this.parserManager.parse(new StringReader(sql));
        String domain = insert.getTable().getName();

        try{
        this.connection.getSimpleDB().createDomain(new CreateDomainRequest(domain));
        }catch(Exception e){
            e.printStackTrace();
        }

        List<Column> columns = (List<Column>) insert.getColumns();
        List list = ((ExpressionList) insert.getItemsList()).getExpressions();

        List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
        int count = 0;
        String id = null;

        for (Column column : columns) {

            SQLExpressionVisitor vistor = new SQLExpressionVisitor();
            String expressionVal = vistor.getValue((Expression)list.get(count));
            System.out.println("value is " + expressionVal);
            
//            String expressionVal = list.get(count).toString();
//            if (new Character(expressionVal.charAt(0)).toString().equals("'")) { //character is a String
//                expressionVal = expressionVal.substring(1, (expressionVal.length() - 1));
//            } else { //expression is some number or date?
//                expressionVal = handleNonStringEncoding(expressionVal);
//            }

            if (column.getColumnName().equalsIgnoreCase("id")) {
                id = expressionVal;
            } else {
                attributes.add(new ReplaceableAttribute().withName(column.getColumnName())
                        .withValue(expressionVal));
            }
            count++;
        }

        if (id == null) {
            id = String.valueOf(Math.abs(new Random(new Date().getTime()).nextLong()));
        }

        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName(id).withAttributes(attributes));
        this.connection.getSimpleDB().batchPutAttributes(
                new BatchPutAttributesRequest(domain, data));
        System.out.println(data);
        return 1;
    }

    //TODO handle DATES

    private String handleNonStringEncoding(String expressionVal) {
        if (StringUtils.isNumeric(expressionVal)) { //it's an int or long but just make a long
            expressionVal = SimpleDBUtils.encodeZeroPadding(Long.parseLong(expressionVal), 5);
        } else { //must have a . in it
            expressionVal = SimpleDBUtils.encodeZeroPadding(Float.parseFloat(expressionVal), 5);
        }
        return expressionVal;
    }


}
