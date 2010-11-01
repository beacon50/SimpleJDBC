package com.beacon50.jdbc.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test case inserts a new user object via
 * Amazon's API, then attempts to select
 * the data via SimpleJDBC. Lastly, the test case
 * removes the user record via Amazon's API.
 */
public class JDBCSelectsTest {

    private static String domain;

    @Test
    public void testSelect() throws Exception {
        String qry = "select * from users where name = 'Joe Smith'";
        Connection conn = this.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry);

        boolean wasFound = false;
        while (rs.next()) {
            String age = rs.getString("age");
            assertEquals("age wasn't 34!", "34", age);

            int iage = rs.getInt("age");
            assertEquals("age wasn't 34!", 34, iage);

            wasFound = true;
        }
        assertTrue("age wasn't found for inserted object", wasFound);
    }

    @Test
    public void testSelectExecute() throws Exception {
        String qry = "select * from users where name = 'Joe Smith'";
        Connection conn = this.getConnection();
        Statement st = conn.createStatement();
        boolean val = st.execute(qry);

        assertTrue("nothing was retrieved", val);

        ResultSet rs = st.getResultSet();

        assertNotNull("result set was null!", rs);

        boolean wasFound = false;
        while (rs.next()) {
            String age = rs.getString("age");
            assertEquals("age wasn't 34!", "34", age);

            int iage = rs.getInt("age");
            assertEquals("age wasn't 34!", 34, iage);

            wasFound = true;
        }
        assertTrue("age wasn't found for inserted object", wasFound);
    }

    public Connection getConnection() throws Exception {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));
        Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
        return DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
    }

    @Before
    public void initialize() throws Exception {
        AmazonSimpleDB sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));
        domain = "users";
        sdb.createDomain(new CreateDomainRequest(domain));

        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName("user_01").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Joe Smith"),
                new ReplaceableAttribute().withName("age").withValue("34")));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
        Thread.sleep(2000);
    }

    @After
    public void deInitialize() throws Exception {
        Thread.sleep(2000);
        AmazonSimpleDB sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));
        sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_01"));
        sdb.deleteDomain(new DeleteDomainRequest(domain));
    }
}
