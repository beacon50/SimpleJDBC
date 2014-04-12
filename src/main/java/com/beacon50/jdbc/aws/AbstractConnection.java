package com.beacon50.jdbc.aws;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 *
 */
public abstract class AbstractConnection implements Connection {

	final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.AbstractConnection");

	public CallableStatement prepareCall(String s) throws SQLException {
		log.info("prepareCall(String s) called");
		return null;
	}

	public String nativeSQL(String s) throws SQLException {
		log.info("nativeSQL(String s) called");
		return null;
	}

	public void setAutoCommit(boolean b) throws SQLException {
		log.info("setAutoCommit(boolean b) called");
	}

	public boolean getAutoCommit() throws SQLException {
		log.info("getAutoCommit() called");
		return false;
	}

	public void commit() throws SQLException {
		log.info("commit() called");
	}

	public void rollback() throws SQLException {
		log.info("prepareCall(String s) called");
	}

	public void close() throws SQLException {
		log.info("close() called");
	}

	public boolean isClosed() throws SQLException {
		log.info("isClosed() called");
		return false;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		log.info("getMetaData() called");
		return null;
	}

	public void setReadOnly(boolean b) throws SQLException {
		log.info("setReadOnly(boolean b) called");
	}

	public boolean isReadOnly() throws SQLException {
		log.info("isReadOnly() called");
		return false;
	}

	public void setCatalog(String s) throws SQLException {
		log.info("setCatalog(String s) called");
	}

	public String getCatalog() throws SQLException {
		log.info("getCatalog() called");
		return null;
	}

	public void setTransactionIsolation(int i) throws SQLException {
		log.info("setTransactionIsolation(int i) called");
	}

	public int getTransactionIsolation() throws SQLException {
		log.info("getTransactionIsolation() called");
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		log.info("getWarnings() called");
		return null;
	}

	public void clearWarnings() throws SQLException {
		log.info(" clearWarnings() called");
	}

	

	public PreparedStatement prepareStatement(String s, int i, int i1)
			throws SQLException {
		log.info("prepareStatement(String s, int i, int i1) called");
		return null;
	}

	public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
		log.info("prepareCall(String s, int i, int i1) called");
		return null;
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		log.info("getTypeMap()  called");
		return null;
	}

	public void setTypeMap(Map<String, Class<?>> stringClassMap) throws SQLException {
		log.info("setTypeMap(Map<String, Class<?>> stringClassMap) called");
	}

	public void setHoldability(int i) throws SQLException {
		log.info(" setHoldability(int i) called");
	}

	public int getHoldability() throws SQLException {
		log.info("getHoldability() called");
		return 0;
	}

	public Savepoint setSavepoint() throws SQLException {
		log.info("setSavepoint() called");
		return null;
	}

	public Savepoint setSavepoint(String s) throws SQLException {
		log.info("setSavepoint(String s) called");
		return null;
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		log.info("rollback(Savepoint savepoint) called");
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		log.info("releaseSavepoint(Savepoint savepoint) called");
	}

	public Statement createStatement(int i, int i1, int i2) throws SQLException {
		log.info("createStatement(int i, int i1, int i2) called");
		return null;
	}

	public PreparedStatement prepareStatement(String s, int i, int i1, int i2)
			throws SQLException {
		log.info("prepareStatement(String s, int i, int i1, int i2) called");
		return null;
	}

	public CallableStatement prepareCall(String s, int i, int i1, int i2)
			throws SQLException {
		log.info("prepareCall(String s, int i, int i1, int i2) called");
		return null;
	}

	public PreparedStatement prepareStatement(String s, int i) throws SQLException {
		log.info("prepareStatement(String s, int i)  called");
		return null;
	}

	public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
		log.info("prepareStatement(String s, int[] ints)  called");
		return null;
	}

	public PreparedStatement prepareStatement(String s, String[] strings)
			throws SQLException {
		log.info("prepareStatement(String s, String[] strings) called");
		return null;
	}

	public Clob createClob() throws SQLException {
		log.info("createClob() called");
		return null;
	}

	public Blob createBlob() throws SQLException {
		log.info("createBlob() called");
		return null;
	}

	public NClob createNClob() throws SQLException {
		log.info(" createNClob() called");
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		log.info("createSQLXML() called");
		return null;
	}

	public boolean isValid(int i) throws SQLException {
		log.info("isValid(int i) called");
		return false;
	}

	public void setClientInfo(String s, String s1) throws SQLClientInfoException {
		log.info("setClientInfo(String s, String s1) called");
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		log.info("setClientInfo(Properties properties) called");
	}

	public String getClientInfo(String s) throws SQLException {
		log.info("getClientInfo(String s) called");
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		log.info("getClientInfo() called");
		return null;
	}

	public Array createArrayOf(String s, Object[] objects) throws SQLException {
		log.info("createArrayOf(String s, Object[] objects) called");
		return null;
	}

	public Struct createStruct(String s, Object[] objects) throws SQLException {
		log.info("createStruct(String s, Object[] objects) called");
		return null;
	}

	public <T> T unwrap(Class<T> tClass) throws SQLException {
		log.info("unwrap(Class<T> tClass) called");
		return null;
	}

	public boolean isWrapperFor(Class<?> aClass) throws SQLException {
		log.info("isWrapperFor(Class<?> aClass called");
		return false;
	}
}
