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
public class JDBCDeletesTest {
    
    private String domain = "users";

    @Test
    public void deleteTest() throws Exception {
        String qry = "delete from users where name = 'Martha Smith'";
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        int count = st.executeUpdate(qry);
        assertEquals("should have removed one value", 1, count);

    }

    @Test
    public void deleteExecuteTest() throws Exception {
        String qry = "delete from users where name = 'Martha Smith'";
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        boolean val = st.execute(qry);
        assertEquals("should have removed one value", true, val);

    }


    @After
    public void deInitialize() throws Exception {
    	AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
    	
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
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();

        sdb.createDomain(new CreateDomainRequest(domain));

        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName("user_02").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Martha Smith"),
                new ReplaceableAttribute().withName("age").withValue("34")));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
        Thread.sleep(2000);
    }

}
