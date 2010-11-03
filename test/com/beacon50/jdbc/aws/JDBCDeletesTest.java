package com.beacon50.jdbc.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.*;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 */
public class JDBCDeletesTest {
    //private static AmazonSimpleDB sdb;
    //private static String domain;
    private String domain = "users";

    @Test
    public void deleteTest() throws Exception {
        String qry = "delete from users where name = 'Martha Smith'";
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        int count = st.executeUpdate(qry);
        assertEquals("should have removed one value", 1, count);

    }

    @Test
    public void deleteExecuteTest() throws Exception {
        String qry = "delete from users where name = 'Martha Smith'";
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        boolean val = st.execute(qry);
        assertEquals("should have removed one value", true, val);

    }


    @After
    public void deInitialize() throws Exception {
        AmazonSimpleDBClient sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));
        Thread.sleep(2000);
        String qry = "select * from `users` where name = 'Martha Smith'";
        SelectRequest selectRequest = new SelectRequest(qry);
        boolean itemFound = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            itemFound = true;
        }
        if (itemFound) {
            fail("item was found -- therefore delete didn't work");
        }

        sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_02"));
        sdb.deleteDomain(new DeleteDomainRequest(domain));
        Thread.sleep(2000);
    }


    @Before
    public void initialize() throws Exception {
        AmazonSimpleDBClient sdb = new AmazonSimpleDBClient(
                new BasicAWSCredentials(System.getProperty("accessKey"),
                        System.getProperty("secretKey")));

        sdb.createDomain(new CreateDomainRequest(domain));

        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName("user_02").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Martha Smith"),
                new ReplaceableAttribute().withName("age").withValue("34")));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
        Thread.sleep(2000);
    }


    public static Connection getConnection() throws Exception {
        Connection con = null;
        Properties prop = new Properties();
        prop.setProperty("secretKey", System.getProperty("secretKey"));
        prop.setProperty("accessKey", System.getProperty("accessKey"));
        Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
        return DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
    }
}