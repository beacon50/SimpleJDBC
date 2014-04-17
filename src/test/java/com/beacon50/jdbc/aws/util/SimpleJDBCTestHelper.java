package com.beacon50.jdbc.aws.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SimpleJDBCTestHelper {

    public static final String TEST = "local-test";
    public static final String SECRET_KEY = "secretKey";
    public static final String ACCESS_KEY = "accessKey";
    public static final String PROXY_HOST = "proxyHost";
    public static final String PROXY_PASSWORD = "proxyPassword";
    public static final String PROXY_USERNAME = "proxyUsername";
    public static final String PROXY_PORT = "proxyPort";
    public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";
    public static final String END_POINT = "endPoint";

    public static Connection getConnection() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(SECRET_KEY, System.getProperty(SECRET_KEY));
        prop.setProperty(ACCESS_KEY, System.getProperty(ACCESS_KEY));
        //setting for testing only
        if (System.getProperty("env", "").equals(TEST)) {
            prop.setProperty(END_POINT, HTTP_LOCALHOST_8080);
        }

        if (System.getProperty(PROXY_HOST) != null && (!System.getProperty(PROXY_HOST).equals(""))) {
            prop.setProperty(PROXY_HOST, System.getProperty(PROXY_HOST, ""));
            prop.setProperty(PROXY_PASSWORD, System.getProperty(PROXY_PASSWORD, ""));
            prop.setProperty(PROXY_USERNAME, System.getProperty(PROXY_USERNAME, ""));
            prop.setProperty(PROXY_PORT, System.getProperty(PROXY_PORT, ""));
        }

        Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
        return DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
    }

    public static AmazonSimpleDB getAmazonSimpleDBClient() throws Exception {
        AmazonSimpleDB client = null;
        if (System.getProperty(PROXY_HOST) != null && (!System.getProperty(PROXY_HOST).equals(""))) {
            ClientConfiguration conf = new ClientConfiguration();
            conf.setProxyHost(System.getProperty(PROXY_HOST));
            conf.setProxyPassword(System.getProperty(PROXY_PASSWORD, ""));
            conf.setProxyUsername(System.getProperty(PROXY_USERNAME, ""));
            conf.setProxyPort(Integer.parseInt(System.getProperty(PROXY_PORT, "80")));

            client = new AmazonSimpleDBClient(
                    new BasicAWSCredentials(System.getProperty(ACCESS_KEY),
                            System.getProperty(SECRET_KEY)), conf
            );
        } else {
            client = new AmazonSimpleDBClient(new BasicAWSCredentials(
                    System.getProperty(ACCESS_KEY), System.getProperty(SECRET_KEY)));
        }

        if (System.getProperty("env", "").equals(TEST)) {
            client.setEndpoint(HTTP_LOCALHOST_8080);
        }
        return client;
    }
}
