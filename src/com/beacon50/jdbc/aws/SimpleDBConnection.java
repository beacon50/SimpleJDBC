package com.beacon50.jdbc.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class SimpleDBConnection extends AbstractConnection {

    private AmazonSimpleDB sdb;
    private String domain;

    private SimpleDBConnection() {
    }

    protected AmazonSimpleDB getSimpleDB() {
        return this.sdb;
    }

    protected String getDomain() {
        return this.domain;
    }

    protected SimpleDBConnection(String domain, String accessId, String accessSecret) {
        this.domain = domain;
        this.sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(accessId, accessSecret));
    }

    protected SimpleDBConnection(String accessId, String accessSecret) {
        this.sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(accessId, accessSecret));
    }

    public Statement createStatement() throws SQLException {
        return new SimpleDBStatement(this);
    }


}
