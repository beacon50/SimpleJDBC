package com.beacon50.jdbc.aws.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class SimpleJDBCTestHelper {

	public static Connection getConnection() throws Exception {
		Properties prop = new Properties();
		prop.setProperty("secretKey", System.getProperty("secretKey"));
		prop.setProperty("accessKey", System.getProperty("accessKey"));
		
		if (System.getProperty("proxyHost") != null && (!System.getProperty("proxyHost").equals(""))) {			
			prop.setProperty("proxyHost", System.getProperty("proxyHost", ""));
			prop.setProperty("proxyPassword", System.getProperty("proxyPassword", ""));
			prop.setProperty("proxyUsername", System.getProperty("proxyUsername", ""));
			prop.setProperty("proxyPort", System.getProperty("proxyPort", ""));
		}
		
		Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
		return DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com",	prop);
	}

	public static AmazonSimpleDB getAmazonSimpleDBClient() throws Exception {

		if (System.getProperty("proxyHost") != null && (!System.getProperty("proxyHost").equals(""))) {
			
			ClientConfiguration conf = new ClientConfiguration();
			conf.setProxyHost(System.getProperty("proxyHost"));
			conf.setProxyPassword(System.getProperty("proxyPassword", ""));
			conf.setProxyUsername(System.getProperty("proxyUsername", ""));
			conf.setProxyPort(Integer.parseInt(System.getProperty("proxyPort", "80")));
			
			return new AmazonSimpleDBClient(
				new BasicAWSCredentials(System.getProperty("accessKey"), 
						System.getProperty("secretKey")), conf);
		} else {
			return new AmazonSimpleDBClient(new BasicAWSCredentials(
				System.getProperty("accessKey"), System.getProperty("secretKey")));
		}
	}
}
