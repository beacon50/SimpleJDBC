package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;

/**
 * This test case inserts via SimpleJDBC and then
 * attempts to locate and verify the correct object
 * via Amazon's API.
 * <p/>
 * NOTE: this test case demonstrates the auto-creation
 * of domains -- that is, the users domain doesn't
 * exist before the test is run (and it is deleted after).
 */
public class JDBCInsertsTest {

    @Before
    public void initialize() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        String insert = "INSERT INTO users (name, age) VALUES ('Ann Smith', 33)";
        int val = st.executeUpdate(insert);
        assertEquals("val should be 1", 1, val);
        assertEquals("val should be -1", -1, st.getUpdateCount());
        Thread.sleep(2000);
    }

    @Test
    public void assertExecuteData() throws Exception {
    	AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();

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

    @After
    public void deinitialize() throws Exception {
        Thread.sleep(2000);
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
        sdb.deleteDomain(new DeleteDomainRequest("users"));
    }

}
