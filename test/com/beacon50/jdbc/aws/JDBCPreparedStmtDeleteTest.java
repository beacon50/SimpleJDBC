package com.beacon50.jdbc.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class JDBCPreparedStmtDeleteTest {
	private String domain = "users";

	@Test
	public void deleteTest() throws Exception {
		String qry = "delete from users where age = ?";
		Connection conn = SimpleJDBCTestHelper.getConnection();
		PreparedStatement stmt = conn.prepareStatement(qry);
		stmt.setInt(1, 34);
		int count = stmt.executeUpdate();
		assertEquals("should have removed 2 values", 2, count);

	}
	
	@After
	public void deInitialize() throws Exception {
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
		Thread.sleep(1500);
		String qry = "select * from `users` where age = '34'";
		SelectRequest selectRequest = new SelectRequest(qry);
		boolean itemFound = false;
		for (Item item : sdb.select(selectRequest).getItems()) {
			itemFound = true;
		}
		if (itemFound) {
			fail("item was found -- therefore delete didn't work");
		}
		sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_09"));
		sdb.deleteAttributes(new DeleteAttributesRequest(domain, "user_06"));
		sdb.deleteDomain(new DeleteDomainRequest(domain));
		Thread.sleep(1500);
	}

	@Before
	public void initialize() throws Exception {
		AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();

		sdb.createDomain(new CreateDomainRequest(domain));

		List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

		data.add(new ReplaceableItem().withName("user_09").withAttributes(
				new ReplaceableAttribute().withName("name").withValue("Mark Smith"),
				new ReplaceableAttribute().withName("age").withValue("00034")));
		
		data.add(new ReplaceableItem().withName("user_06").withAttributes(
				new ReplaceableAttribute().withName("name").withValue("Jude Smith"),
				new ReplaceableAttribute().withName("age").withValue("00034")));

		sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
		Thread.sleep(1500);
	}
}
