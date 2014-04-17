package com.beacon50.jdbc.aws;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 */
public class SimpleDBDriver implements Driver {

    static {
        try {
            DriverManager.registerDriver(new SimpleDBDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static final String PROXY_HOST = "proxyHost";
    public static final String SECRET_KEY = "secretKey";
    public static final String ACCESS_KEY = "accessKey";
    public static final String END_POINT = "endPoint";

    /**
     * @param url pattern is "jdbc:simpledb://sdb.amazonaws.com"
     * @param info properties representing connection info
     * @return Connection
     * @throws SQLException
     */
    public Connection connect(String url, Properties info) throws SQLException {
        try {
            if (!acceptsURL(url)) {
                throw new SQLException("incorrect url");
            }
            //assume for now that if there is any proxy
            //information, then do it all...
            if (propertyExists(info.getProperty(PROXY_HOST))) {
                return new SimpleDBConnection(info.getProperty(ACCESS_KEY),
                        info.getProperty(SECRET_KEY), new SimpleDBProxy(info));
            } else if (propertyExists(info.getProperty(END_POINT))) {
                return new SimpleDBConnection(info.getProperty(ACCESS_KEY),
                        info.getProperty(SECRET_KEY), info.getProperty(END_POINT));
            } else {
                return new SimpleDBConnection(info.getProperty(ACCESS_KEY),
                        info.getProperty(SECRET_KEY));
            }
        } catch (Exception e) {
            throw new SQLException("unable to connect");
        }
    }

    private boolean propertyExists(String prop) {
        return prop != null && !prop.equals("");
    }

    public boolean acceptsURL(String url) throws SQLException {
        if (!url.startsWith("jdbc:simpledb")) {
            return false;
        } else {
            return true;
        }
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
            throws SQLException {
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

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
