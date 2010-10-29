package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;

import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

/**
 *
 */
public class SimpleDBResultSet extends AbstractResultSet {

    private List<Item> items;
    private ListIterator<Item> iter;
    private int currentPos = -1;
    private Item currentItem;

    protected SimpleDBResultSet(List<Item> items) {
        this.items = items;
        this.iter = items.listIterator();
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
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        Attribute attribute = attributes.get(index);
        return Integer.parseInt(attribute.getValue());
    }

    private void checkPosition() throws SQLException {
        if (currentPos < 0) {
            throw new SQLException("you must call next() on a ResultSet first!");
        }
    }

    public int getInt(String label) throws SQLException {
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(label)) {
                return Integer.valueOf(attribute.getValue());
            }
        }
        throw new SQLException("attribute name " + label + " doesn't exist!");
    }

    public String getString(int columnIndex) throws SQLException {
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        Attribute attribute = attributes.get(columnIndex);
        return attribute.getValue();
    }

    public String getString(String columnLabel) throws SQLException {
        checkPosition();

        Item item = items.get(currentPos);
        List<Attribute> attributes = item.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(columnLabel)) {
                return attribute.getValue();
            }
        }
        throw new SQLException("attribute name " + columnLabel + " doesn't exist!");
    }


}
