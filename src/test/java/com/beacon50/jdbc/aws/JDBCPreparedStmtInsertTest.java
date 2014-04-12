package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class JDBCPreparedStmtInsertTest {


    @Before
    public void initialize() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, age) VALUES (?, ?)");
        pstmt.setString(1, "Anny Smith");
        pstmt.setInt(2, 33);
        int val = pstmt.executeUpdate();
        assertEquals("val should be 1", 1, val);
        Thread.sleep(2000);
    }

    @Test
    public void assertExecuteData() throws Exception {
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();

        String qry = "select * from `users` where name = 'Anny Smith'";
        SelectRequest selectRequest = new SelectRequest(qry);
        boolean itemFound = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            String id = item.getName();
            assertNotNull("id was null?", id);
            itemFound = true;
            List<Attribute> attrs = item.getAttributes();
            for (Attribute attr : attrs) {
                if (attr.getName().equals("name")) {
                    assertEquals("name wasn't Anny Smith", "Anny Smith", attr.getValue());
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
