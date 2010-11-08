package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
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
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;

public class JDBCSelectCountTest {
	@Test
    public void testSelect() throws Exception {
        String qry = "select count(*) from users_tst";
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry);

        boolean wasFound = false;
        while (rs.next()) {
            int icnt = rs.getInt("count");
            assertEquals("count wasn't 2", 2, icnt);
            wasFound = true;
        }
        assertTrue("count can't be found", wasFound);
    }
    
    @Before
    public void initialize() throws Exception {
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
        String domain = "users_tst";
        sdb.createDomain(new CreateDomainRequest(domain));
        Thread.sleep(1000);
        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName("user_01").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Zoey Smith"),
                new ReplaceableAttribute().withName("age").withValue("34")));
        
        data.add(new ReplaceableItem().withName("user_02").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Joey Smith"),
                new ReplaceableAttribute().withName("age").withValue("32")));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
        Thread.sleep(2000);
    }

    @After
    public void deInitialize() throws Exception {
        Thread.sleep(2000);
        String domain = "users_tst";
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
        sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_01"));
        sdb.deleteDomain(new DeleteDomainRequest(domain));
    }
}
