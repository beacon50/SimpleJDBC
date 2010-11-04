package com.beacon50.jdbc.aws;

import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.beacon50.jdbc.aws.util.SimpleJDBCTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class JDBCResultSetMetaDataTest {

    @Before
    public void initialize() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        Statement st = conn.createStatement();

        String insert = "INSERT INTO users (name, age) VALUES ('Ann Smith', 33)";
        int val = st.executeUpdate(insert);
        assertEquals("val should be 1", 1, val);

        String insert2 = "INSERT INTO users (name, age) VALUES ('Joe Smith', 30)";
        int val2 = st.executeUpdate(insert2);
        assertEquals("val should be 1", 1, val2);

        String insert3 = "INSERT INTO users (name, age, weight) VALUES ('Bill Smith', 33, 220)";
        int val3 = st.executeUpdate(insert3);
        assertEquals("val should be 1", 1, val3);

        Thread.sleep(1500);
    }

    @Test
    public void metaDataTest() throws Exception {
        Connection conn = SimpleJDBCTestHelper.getConnection();
        String qry = "select * from users";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry);
        ResultSetMetaData meta = rs.getMetaData();
        assertEquals("val should be 3", 3, meta.getColumnCount());

        for (int i = 1; i <= meta.getColumnCount(); i++) {
            String name = meta.getColumnName(i);
            int type = meta.getColumnType(i);
            assertNotNull("name was null?", name);
            assertEquals("val wasn't greater than 0", true, (type > 0));
        }
    }

    @After
    public void deinitialize() throws Exception {
        Thread.sleep(2000);
        AmazonSimpleDB sdb = SimpleJDBCTestHelper.getAmazonSimpleDBClient();
        sdb.deleteDomain(new DeleteDomainRequest("users"));
    }
}
