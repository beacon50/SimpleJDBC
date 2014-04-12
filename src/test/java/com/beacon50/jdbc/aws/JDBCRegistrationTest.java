package com.beacon50.jdbc.aws;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 */
public class JDBCRegistrationTest {

    @Test
    public void registerDriver() {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));

        try {
            Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
            con = DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
            assertTrue("connection wasn't instance of SimpleDB", (con instanceof SimpleDBConnection));
        } catch (Exception e) {
            fail("an exception was generated in attempting to load the driver");
        }
    }
}