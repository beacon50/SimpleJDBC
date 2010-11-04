package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class JDBCPreparedStmtUpdateTest {
	private String domain;

	@Before
	public void initialize() throws Exception {
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
		domain = "users";
		sdb.createDomain(new CreateDomainRequest(domain));

		List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

		data.add(new ReplaceableItem().withName("user_01").withAttributes(
				new ReplaceableAttribute().withName("name").withValue(
						"Joe Smith"),
				new ReplaceableAttribute().withName("gender").withValue(
						"male"),
				new ReplaceableAttribute().withName("age").withValue("34")));

		sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
		Thread.sleep(1500);
	}
	
	
	@Test
    public void testUpdatePrepStm() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        String update = "UPDATE users SET age = ? where name = ?";
        
        PreparedStatement pstmt = conn.prepareStatement(update); 
        pstmt.setInt(1, 45);
        pstmt.setString(2, "Joe Smith");
        
        int val = pstmt.executeUpdate();
        assertEquals("val should be 1", 1, val);
        
        Thread.sleep(1500);
        
        String qry = "select * from users where name = 'Joe Smith'";
        Connection conn2 = SimpleJDBCTestHelper.getConnection();
        Statement st = conn2.createStatement();
        ResultSet rs = st.executeQuery(qry);

        boolean wasFound = false;
        while (rs.next()) {
            String age = rs.getString("age");
            assertEquals("string age wasn't 00045!", "00045", age); //not decoded!

            int iage = rs.getInt("age");
            assertEquals("int age wasn't 45!", 45, iage);

            wasFound = true;
        }
        assertTrue("age wasn't found for inserted object", wasFound);
        
    }
	
	@Test
    public void testUpdatePrepStmMore() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        String update = "UPDATE users SET age = ?, gender = ? where name = ?";
        
        PreparedStatement pstmt = conn.prepareStatement(update); 
        pstmt.setInt(1, 45);
        pstmt.setString(2, "female");
        pstmt.setString(3, "Joe Smith");
        
        int val = pstmt.executeUpdate();
        assertEquals("val should be 1", 1, val);
        Thread.sleep(1500);
        
        String qry = "select * from users where name = 'Joe Smith'";
        Connection conn2 = SimpleJDBCTestHelper.getConnection();
        Statement st = conn2.createStatement();
        ResultSet rs = st.executeQuery(qry);

        boolean wasFound = false;
        while (rs.next()) {
            String gender = rs.getString("gender");
            assertEquals("gender wasn't female", "female", gender);
            wasFound = true;
        }
        assertTrue("gender wasn't found for inserted object", wasFound);
        
    }

	@Test
    public void testUpdatePrepStmMoreWhere() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        String update = "UPDATE users SET age = ? where name = ? and gender = ?";
        
        PreparedStatement pstmt = conn.prepareStatement(update); 
        pstmt.setInt(1, 45);
        pstmt.setString(2, "Joe Smith");
        pstmt.setString(3, "male");
        
        int val = pstmt.executeUpdate();
        assertEquals("val should be 1", 1, val);
        Thread.sleep(1500);
        
        String qry = "select * from users where name = 'Joe Smith'";
        Connection conn2 = SimpleJDBCTestHelper.getConnection();
        Statement st = conn2.createStatement();
        ResultSet rs = st.executeQuery(qry);

        boolean wasFound = false;
        while (rs.next()) {
        	String age = rs.getString("age");
            assertEquals("string age wasn't 00045!", "00045", age); //not decoded!

            int iage = rs.getInt("age");
            assertEquals("int age wasn't 45!", 45, iage);

            wasFound = true;
        }
        assertTrue("gender wasn't found for inserted object", wasFound);
        
    }

	
	@After
	public void deInitialize() throws Exception {
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
		Thread.sleep(1500);
		String qry = "select * from `users` where name = 'Joe Smith'";
		SelectRequest selectRequest = new SelectRequest(qry);
		boolean itemFound = false;
		for (Item item : sdb.select(selectRequest).getItems()) {
			itemFound = true;			
		}
		if (!itemFound) {
			fail("item wasn't found?");
		}

		sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_01"));
		sdb.deleteDomain(new DeleteDomainRequest(domain));
	}
}
