package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 *
 */
public class SimpleDBResultSet extends AbstractResultSet {

    private List<Item> items;
    private ListIterator<Item> iter;
    private int currentPos = -1;
    @SuppressWarnings("unused")
	private Item currentItem;
    private SimpleDBConnection connection;
    private String domain;
    final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.SimpleDBResultSet");
    /**
     * 
     * @param connection
     * @param items
     * @param domain
     */
    protected SimpleDBResultSet(SimpleDBConnection connection, List<Item> items, String domain) {
        this.connection = connection;
        this.items = items;
        this.iter = items.listIterator();
        this.domain = domain;
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return new SimpleDBResultSetMetaData(this.connection, this.items, this.domain);
    }
    
    public boolean next() throws SQLException {
        if (this.iter.hasNext()) {
            currentPos = this.iter.nextIndex();
            this.currentItem = iter.next();
            return true;
        } else {
            return false;
        }
    }

    public int getInt(int index) throws SQLException {
    	log.info("getInt(int index) called with index " + index);
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        Attribute attribute = attributes.get((index - 1));
        return SimpleDBUtils.decodeZeroPaddingInt(getAttributeValue(attribute));
    }

    private void checkPosition() throws SQLException {
        if (currentPos < 0) {
            throw new SQLException("you must call next() on a ResultSet first!");
        }
    }

    public int getInt(String label) throws SQLException {
    	log.info("getInt(String label) called with label " + label);
    	checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        for (Attribute attribute : attributes) {
            if (getAttributeName(attribute).equals(label)) {
                return SimpleDBUtils.decodeZeroPaddingInt(getAttributeValue(attribute));
            }
        }
        throw new SQLException("attribute name " + label + " doesn't exist!");
    }


    public Object getObject(int i) throws SQLException {
        return this.getString(i);
    }


    public Object getObject(String s) throws SQLException {
        return this.getString(s);
    }

    public int getRow() throws SQLException {
        return (this.currentPos + 1);
    }

    public String getString(int columnIndex) throws SQLException {
    	log.info("getString(int columnIndex) called with index " + columnIndex);
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        Attribute attribute = attributes.get((columnIndex - 1));
        return getAttributeValue(attribute);
    }

    public String getString(String columnLabel) throws SQLException {
    	log.info("getString(String columnLabel) called with value " + columnLabel);
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        for (Attribute attribute : attributes) {
            if (getAttributeName(attribute).equals(columnLabel)) {
                return getAttributeValue(attribute);
            }
        }
        throw new SQLException("attribute name " + columnLabel + " doesn't exist!");
    }

    
    protected String getAttributeValue(Attribute attribute) {
        return attribute.getValue();
    }


    protected String getAttributeName(Attribute attribute) {
        return attribute.getName();
    }

    public String toString(){
    	return ToStringBuilder.reflectionToString(this);
    }

	public void close() throws SQLException {
		this.currentPos = -1;
	}
}	
