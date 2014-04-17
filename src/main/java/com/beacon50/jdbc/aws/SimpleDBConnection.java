package com.beacon50.jdbc.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 *
 */
public class SimpleDBConnection extends AbstractConnection {

	final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.SimpleDBConnection");
	private AmazonSimpleDB sdb;
	
	@SuppressWarnings("unused")
	private SimpleDBConnection() {}

	protected AmazonSimpleDB getSimpleDB() {
		return this.sdb;
	}

	/**
	 * 
	 * @param accessId AWS access id
	 * @param accessSecret AWS secret
	 */
	protected SimpleDBConnection(String accessId, String accessSecret) {
		this.sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(accessId,
				accessSecret));
	}

    /**
     * Creates a connection to specified endpoint (i.e. localhost)
     * @param accessId AWS access id
     * @param accessSecret AWS secret
     * @param endPoint AWS alternate endpoint
     */
    protected SimpleDBConnection(String accessId, String accessSecret, String endPoint) {
        this(accessId, accessSecret);
        log.info("Setting endpoint to: " + endPoint);
        this.sdb.setEndpoint(endPoint);
    }
	/**
	 * 
	 * @param accessId AWS access id
	 * @param accessSecret AWS secret
	 * @param proxy Proxy info
	 */
	protected SimpleDBConnection(String accessId, String accessSecret,
			SimpleDBProxy proxy) {
		log.info("creating a connection with proxy information: " + proxy);
		ClientConfiguration conf = getAWSConfiguration(proxy);
		this.sdb = new AmazonSimpleDBClient(new BasicAWSCredentials(accessId,
				accessSecret), conf);
		log.info("done creating SDB connection, Client is init'ed -> " + this.sdb.toString());
	}

	/**
	 * 
	 * @param proxy Proxy info
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
		log.info("Creating a statement");
		return new SimpleDBStatement(this);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		log.info("Creating a PreparedStatement with the following SQL: " + sql);
		return new SimpleDBPreparedStatement(sql, this);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		log.info("createStatement(int resultSetType, int resultSetConcurrency) was called.");
		return new SimpleDBStatement(this);
	}

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }
}
