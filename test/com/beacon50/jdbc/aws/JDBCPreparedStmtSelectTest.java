package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;

public class JDBCPreparedStmtSelectTest {
	private static String domain;
	
	@Test
    public void testSelect() throws Exception {
        String qry = "select * from users where name = ?";
        Connection conn = SimpleJDBCTestHelper.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(qry);
        pstmt.setString(1, "Zoe Smith");        
        
        int val = pstmt.executeUpdate();
        assertTrue("val should not be 0", val > 0);
        ResultSet rs = pstmt.getResultSet();

        assertEquals("row count wasn't 0", 0, rs.getRow());

        boolean wasFound = false;
        while (rs.next()) {
            String age = rs.getString("age");
            assertEquals("age wasn't 34!", "00034", age);

            int iage = rs.getInt("age");
            assertEquals("age wasn't 34!", 34, iage);

            assertEquals("row count wasn't 1", 1, rs.getRow());
            wasFound = true;
        }
        assertTrue("age wasn't found for inserted object", wasFound);
    }

	
	@Test
    public void testSelectWithTwoVals() throws Exception {
        String qry = "select * from users where name = ? and age = ?";
        Connection conn = SimpleJDBCTestHelper.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(qry);
        pstmt.setString(1, "Zoe Smith");        
        pstmt.setInt(2, 34);
        
        int val = pstmt.executeUpdate();
        assertTrue("val should not be 0", val > 0);
        ResultSet rs = pstmt.getResultSet();

        assertEquals("row count wasn't 0", 0, rs.getRow());

        boolean wasFound = false;
        while (rs.next()) {
            String age = rs.getString("age");
            assertEquals("age wasn't 34!", "00034", age);

            int iage = rs.getInt("age");
            assertEquals("age wasn't 34!", 34, iage);

            assertEquals("row count wasn't 1", 1, rs.getRow());
            wasFound = true;
        }
        assertTrue("age wasn't found for inserted object", wasFound);
    }

	
	@Before
	public void initialize() throws Exception {
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
		domain = "users";
		sdb.createDomain(new CreateDomainRequest(domain));
		Thread.sleep(1000);
		List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

		data.add(new ReplaceableItem().withName("user_066").withAttributes(
				new ReplaceableAttribute().withName("name").withValue("Zoe Smith"),
				new ReplaceableAttribute().withName("age").withValue("00034")));

		sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
		Thread.sleep(2000);
	}

	@After
	public void deInitialize() throws Exception {
		Thread.sleep(2000);
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
		sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_066"));
		sdb.deleteDomain(new DeleteDomainRequest(domain));
	}
}
