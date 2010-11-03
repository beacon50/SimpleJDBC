package com.beacon50.jdbc.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 */
public class SimpleDBConnection extends AbstractConnection {

	private AmazonSimpleDB sdb;
	private String domain;

	private SimpleDBConnection() {
	}

	protected AmazonSimpleDB getSimpleDB() {
		return this.sdb;
	}

	protected String getDomain() {
		return this.domain;
	}

	/**
	 * 
	 * @param domain
	 * @param accessId
	 * @param accessSecret
	 */
	protected SimpleDBConnection(String domain, String accessId,
			String accessSecret) {
		this.domain = domain;
		this.sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(accessId,
				accessSecret));
	}

	/**
	 * 
	 * @param accessId
	 * @param accessSecret
	 */
	protected SimpleDBConnection(String accessId, String accessSecret) {
		this.sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(accessId,
				accessSecret));
	}

	/**
	 * 
	 * @param accessId
	 * @param accessSecret
	 * @param proxyHost
	 * @param proxyPassword
	 * @param proxyPort
	 */
	protected SimpleDBConnection(String accessId, String accessSecret,
			SimpleDBProxy proxy) {

		ClientConfiguration conf = getAWSConfiguration(proxy);
		this.sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(accessId,
				accessSecret), conf);
	}
	/**
	 * 
	 * @param proxy
	 * @return AWS ClientConfiguration
	 */
	private ClientConfiguration getAWSConfiguration(SimpleDBProxy proxy) {
		ClientConfiguration conf = new ClientConfiguration();
		conf.setProxyHost(proxy.getHost());
		conf.setProxyPassword(proxy.getPassword());
		conf.setProxyUsername(proxy.getUsername());
		conf.setProxyPort(proxy.getPort());
		return conf;
	}

	/**
	 * 
	 */
	public Statement createStatement() throws SQLException {
		return new SimpleDBStatement(this);
	}
}
