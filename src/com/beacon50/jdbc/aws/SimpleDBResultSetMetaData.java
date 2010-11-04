package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DomainMetadataRequest;
import com.amazonaws.services.simpledb.model.Item;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 *
 */
public class SimpleDBResultSetMetaData extends AbstractResultSetMetaData {

    private SimpleDBConnection connection;
    private List<Item> items;
    private String domain;

    protected SimpleDBResultSetMetaData(SimpleDBConnection connection, List<Item> items, String domain) {
        this.connection = connection;
        this.items = items;
        this.domain = domain;
    }

    public String getColumnLabel(int i) throws SQLException {
        return this.getColumnName(i);
    }

    public String getColumnName(int i) throws SQLException {
        Item item = items.get(0);
        List<Attribute> attributes = item.getAttributes();
        try {
            Attribute attribute = attributes.get(i - 1);
            return attribute.getName();
        } catch (IndexOutOfBoundsException e) {
            return "Unknown";
        }
    }

    public int getColumnType(int i) throws SQLException {
        return Types.VARCHAR; //for now
    }


    public int getColumnCount() throws SQLException {
        return connection.getSimpleDB().domainMetadata(
                new DomainMetadataRequest(domain)).getAttributeNameCount();
    }
}
