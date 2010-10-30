package com.beacon50.jdbc.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 *  This test case inserts via SimpleJDBC and then
 *  attempts to locate and verify the correct object
 *  via Amazon's API.
 *
 *  NOTE: this test case demonstrates the auto-creation
 *  of domains -- that is, the users domain doesn't
 *  exist before the test is run (and it is deleted after). 
 */
public class JDBCInsertsTest {

    @BeforeClass
    public static void initialize() throws Exception {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        String insert = "INSERT INTO users (name, age) VALUES ('Ann Smith', 33)";
        int val = st.executeUpdate(insert);
        assertEquals("val should be 1", 1, val);
        Thread.sleep(2000);
    }

    @Test
    public void assertData() {
        AmazonSimpleDBClient sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));

        String qry = "select * from `users` where name = 'Ann Smith'";
        SelectRequest selectRequest = new SelectRequest(qry);
        boolean itemFound = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            String id = item.getName();
            assertNotNull("id was null?", id);
            itemFound = true;
            List<Attribute> attrs = item.getAttributes();
            for (Attribute attr : attrs) {
                if (attr.getName().equals("name")) {
                    assertEquals("name wasn't Ann Smith", "Ann Smith", attr.getValue());
                } else {
                    assertEquals("name wasn't 00033", "00033", attr.getValue());
                }

            }
        }
        if (!itemFound) {
            fail("item wasn't found?");
        }
    }


    public static Connection getConnection() throws Exception {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));
        Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
        return DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
    }

    @AfterClass
    public static void deinitialize() throws Exception {
        Thread.sleep(2000);
        AmazonSimpleDBClient sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));

        sdb.deleteDomain(new DeleteDomainRequest("users"));
    }

}
