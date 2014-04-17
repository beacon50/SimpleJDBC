package com.beacon50.jdbc.aws;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

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

    @Test
    public void registerDriverWithEndpoint() {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));
        prop.setProperty("endPoint", "localhost:8080");
        try {
            Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
            con = DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
            assertTrue("connection wasn't instance of SimpleDB", (con instanceof SimpleDBConnection));
        } catch (Exception e) {
            fail("an exception was generated in attempting to load the driver");
        }
    }

    @Test
    public void shouldAcceptURL() throws Exception {
        SimpleDBDriver driver = new SimpleDBDriver();
        assertFalse("should have returned false", driver.acceptsURL("jdbc:oracle://sdb.amazonaws.com"));
        assertTrue("should have returned true", driver.acceptsURL("jdbc:simpledb://sdb.amazonaws.com"));
    }

    @Test
    public void invalidRegisterDriver() {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));

        try {
            Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
            con = DriverManager.getConnection("jdbc:oracle://sdb.amazonaws.com", prop);
            fail("The driver loaded and it shouldn't have.");
        } catch (Exception e) {
            assertTrue("An exception of SQLException type was not thrown?", (e instanceof SQLException));
        }
    }
}