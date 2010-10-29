package com.beacon50.jdbc.aws;

import java.sql.*;
import java.util.Properties;

/**
 *
 */
public class SimpleDBDriver implements Driver {

    static {
        try {
            DriverManager.registerDriver(new SimpleDBDriver());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param url
     * @param info
     * @return
     * @throws SQLException
     */
    public Connection connect(String url, Properties info) throws SQLException {
        try {
            if (!url.startsWith("jdbc:simpledb")) {
                throw new SQLException("incorrect url");
            }
            return new SimpleDBConnection(info.getProperty("accessKey"), info.getProperty("secretKey"));
        } catch (Exception e) {
            throw new SQLException("unable to connect");
        }

    }

    public boolean acceptsURL(String url) throws SQLException {
        if (!url.startsWith("jdbc:simpledb")) {
            return false;
        } else {
            return true;
        }
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean jdbcCompliant() {
        return false;
    }
}
