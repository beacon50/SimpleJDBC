package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;

/**
 *
 */
public class JDBCUpdatesTest {

    private static String domain;


    @Test
    public void testExecuteUpdate() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        String update = "UPDATE users SET age = 45 where name = 'Joe Smith'";
        boolean val = st.execute(update);
        assertEquals("val should be true", true, val);
        assertEquals("val should be 1", 1, st.getUpdateCount());
        Thread.sleep(2000);
    }


    @Test
    public void testUpdate() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        String update = "UPDATE users SET age = 45 where name = 'Joe Smith'";
        int val = st.executeUpdate(update);
        assertEquals("val should be 1", 1, val);
        assertEquals("val should be 1", 1, st.getUpdateCount());
        Thread.sleep(2000);
    }

    @Before
    public void initialize() throws Exception {
    	AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
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
    	AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
        Thread.sleep(2000);
        String qry = "select * from `users` where name = 'Joe Smith'";
        SelectRequest selectRequest = new SelectRequest(qry);
        boolean itemFound = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            itemFound = true;
            List<Attribute> attrs = item.getAttributes();
            for (Attribute attr : attrs) {
                if (attr.getName().equals("name")) {
                    assertEquals("name wasn't Joe Smith", "Joe Smith", attr.getValue());
                } else {
                    //note! values are encoded! 
                    assertEquals("name wasn't 00045", "00045", attr.getValue());
                }
            }
        }
        if (!itemFound) {
            fail("item wasn't found?");
        }

        sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_01"));
        sdb.deleteDomain(new DeleteDomainRequest(domain));
    }
}
