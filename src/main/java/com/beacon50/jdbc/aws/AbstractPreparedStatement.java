package com.beacon50.jdbc.aws;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 *
 */
public abstract class AbstractPreparedStatement implements PreparedStatement {

	final private Logger log = Logger
			.getLogger("com.beacon50.jdbc.aws.AbstractPreparedStatement");

	public ResultSet executeQuery(String sql) throws SQLException {
		log.info("executeQuery(String sql) called");
		return null;
	}

	public void setNull(int i, int i1) throws SQLException {
		log.info("setNull(int i, int i1) called");
	}

	public void setByte(int i, byte b) throws SQLException {
		log.info("setByte(int i, byte b) called");
	}

	public void setShort(int i, short i2) throws SQLException {
		log.info("setShort(int i, short i2) called");
	}

	public void setBytes(int i, byte[] bytes) throws SQLException {
		log.info(" setBytes(int i, byte[] bytes) called");
	}

	public void setTime(int i, Time time) throws SQLException {
		log.info("setTime(int i, Time time)  called");
	}

	public void setTimestamp(int i, Timestamp timestamp) throws SQLException {
		log.info("setTimestamp(int i, Timestamp timestamp) called");
	}

	public void setAsciiStream(int i, InputStream inputStream, int i1)
			throws SQLException {
		log.info("setAsciiStream(int i, InputStream inputStream, int i1) called");
	}

	public void setUnicodeStream(int i, InputStream inputStream, int i1)
			throws SQLException {
		log.info("setUnicodeStream(int i, InputStream inputStream, int i1) called");
	}

	public void setBinaryStream(int i, InputStream inputStream, int i1)
			throws SQLException {
		log.info("setBinaryStream(int i, InputStream inputStream, int i1) called");
	}

	public void clearParameters() throws SQLException {
		log.info("clearParameters() called");
	}

	public void setObject(int i, Object o, int i1) throws SQLException {
		log.info("setObject(int i, Object o, int i1) called");
	}

	public void addBatch() throws SQLException {
		log.info("addBatch() called");
	}

	public void setCharacterStream(int i, Reader reader, int i1) throws SQLException {
		log.info("setCharacterStream(int i, Reader reader, int i1)  called");
	}

	public void setRef(int i, Ref ref) throws SQLException {
		log.info("setRef(int i, Ref ref) called");
	}

	public void setBlob(int i, Blob blob) throws SQLException {
		log.info("setBlob(int i, Blob blob) called");
	}

	public void setClob(int i, Clob clob) throws SQLException {
		log.info("setClob(int i, Clob clob) called");
	}

	public void setArray(int i, Array array) throws SQLException {
		log.info("setArray(int i, Array array) called");
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		log.info("getMetaData() called");
		return null;
	}

	public void setDate(int i, Date date, Calendar calendar) throws SQLException {
		log.info(" setDate(int i, Date date, Calendar calendar)  called");
	}

	public void setTime(int i, Time time, Calendar calendar) throws SQLException {
		log.info(" setTime(int i, Time time, Calendar calendar) called");
	}

	public void setTimestamp(int i, Timestamp timestamp, Calendar calendar)
			throws SQLException {
		log.info("setTimestamp(int i, Timestamp timestamp, Calendar calendar) called");
	}

	public void setNull(int i, int i1, String s) throws SQLException {
		log.info("setNull(int i, int i1, String s) called");
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		log.info("getParameterMetaData()  called");
		return null;
	}

	public void setRowId(int i, RowId rowId) throws SQLException {
		log.info("setRowId(int i, RowId rowId) called");
	}

	public void setNString(int i, String s) throws SQLException {
		log.info("setNString(int i, String s) called");
	}

	public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {
		log.info("setNCharacterStream(int i, Reader reader, long l) called");
	}

	public void setNClob(int i, NClob nClob) throws SQLException {
		log.info("setNClob(int i, NClob nClob) called");
	}

	public void setClob(int i, Reader reader, long l) throws SQLException {
		log.info("setClob(int i, Reader reader, long l) called");
	}

	public void setBlob(int i, InputStream inputStream, long l) throws SQLException {
		log.info("setBlob(int i, InputStream inputStream, long l) called");
	}

	public void setNClob(int i, Reader reader, long l) throws SQLException {
		log.info("setNClob(int i, Reader reader, long l) called");
	}

	public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
		log.info("setSQLXML(int i, SQLXML sqlxml)  called");
	}

	public void setObject(int i, Object o, int i1, int i2) throws SQLException {
		log.info("setObject(int i, Object o, int i1, int i2)  called");
	}

	public void setAsciiStream(int i, InputStream inputStream, long l)
			throws SQLException {
		log.info("setAsciiStream(int i, InputStream inputStream, long l) called");
	}

	public void setBinaryStream(int i, InputStream inputStream, long l)
			throws SQLException {
		log.info("setBinaryStream(int i, InputStream inputStream, long l) called");
	}

	public void setCharacterStream(int i, Reader reader, long l) throws SQLException {
		log.info("setCharacterStream(int i, Reader reader, long l) called");
	}

	public void setAsciiStream(int i, InputStream inputStream) throws SQLException {
		log.info("setAsciiStream(int i, InputStream inputStream) called");
	}

	public void setBinaryStream(int i, InputStream inputStream) throws SQLException {
		log.info("setBinaryStream(int i, InputStream inputStream) called");
	}

	public void setCharacterStream(int i, Reader reader) throws SQLException {
		log.info("setCharacterStream(int i, Reader reader) called");
	}

	public void setNCharacterStream(int i, Reader reader) throws SQLException {
		log.info("setNCharacterStream(int i, Reader reader) called");
	}

	public void setClob(int i, Reader reader) throws SQLException {
		log.info("setClob(int i, Reader reader) called");
	}

	public void setBlob(int i, InputStream inputStream) throws SQLException {
		log.info("setBlob(int i, InputStream inputStream) called");
	}

	public void setNClob(int i, Reader reader) throws SQLException {
		log.info("setNClob(int i, Reader reader) called");
	}

	public int executeUpdate(String s) throws SQLException {
		log.info("executeUpdate(String s) called");
		return 0;
	}

	public int getMaxFieldSize() throws SQLException {
		log.info("getMaxFieldSize() called");
		return 0;
	}

	public void setMaxFieldSize(int i) throws SQLException {
		log.info("setMaxFieldSize(int i) called");
	}

	public int getMaxRows() throws SQLException {
		log.info("getMaxRows() called");
		return 0;
	}

	public void setMaxRows(int i) throws SQLException {
		log.info("setMaxRows(int i) called");
	}

	public void setEscapeProcessing(boolean b) throws SQLException {
		log.info("setEscapeProcessing(boolean b) called");
	}

	public int getQueryTimeout() throws SQLException {
		log.info("getQueryTimeout() called");
		return 0;
	}

	public void setQueryTimeout(int i) throws SQLException {
		log.info("setQueryTimeout(int i) called");
	}

	public void cancel() throws SQLException {
		log.info("cancel() called");
	}

	public SQLWarning getWarnings() throws SQLException {
		log.info("getWarnings() called");
		return null;
	}

	public void clearWarnings() throws SQLException {
		log.info("clearWarnings() called");
	}

	public void setCursorName(String s) throws SQLException {
		log.info("setCursorName(String s) called");
	}

	public boolean execute(String s) throws SQLException {
		log.info("execute(String s) called");
		return false;
	}

	public boolean getMoreResults() throws SQLException {
		log.info("getMoreResults() called");
		return false;
	}

	public void setFetchDirection(int i) throws SQLException {
		log.info("setFetchDirection(int i) called");
	}

	public int getFetchDirection() throws SQLException {
		log.info("getFetchDirection() called");
		return 0;
	}

	public void setFetchSize(int i) throws SQLException {
		log.info("setFetchSize(int i) called");
	}

	public int getFetchSize() throws SQLException {
		log.info("getFetchSize() called");
		return 0;
	}

	public int getResultSetConcurrency() throws SQLException {
		log.info("getResultSetConcurrency() called");
		return 0;
	}

	public int getResultSetType() throws SQLException {
		log.info("getResultSetType() called");
		return 0;
	}

	public void addBatch(String s) throws SQLException {
		log.info("addBatch(String s) called");
	}

	public void clearBatch() throws SQLException {
		log.info("clearBatch() called");
	}

	public int[] executeBatch() throws SQLException {
		log.info("executeBatch() called");
		return new int[0];
	}

	public Connection getConnection() throws SQLException {
		log.info("getConnection() called");
		return null;
	}

	public boolean getMoreResults(int i) throws SQLException {
		log.info("getMoreResults(int i) called");
		return false;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		log.info("getGeneratedKeys() called");
		return null;
	}

	public int executeUpdate(String s, int i) throws SQLException {
		log.info("executeUpdate(String s, int i) called");
		return 0;
	}

	public int executeUpdate(String s, int[] ints) throws SQLException {
		log.info("executeUpdate(String s, int[] ints) called");
		return 0;
	}

	public int executeUpdate(String s, String[] strings) throws SQLException {
		log.info("executeUpdate(String s, String[] strings) called");
		return 0;
	}

	public boolean execute(String s, int i) throws SQLException {
		log.info("execute(String s, int i) called");
		return false;
	}

	public boolean execute(String s, int[] ints) throws SQLException {
		log.info("execute(String s, int[] ints) called");
		return false;
	}

	public boolean execute(String s, String[] strings) throws SQLException {
		log.info("execute(String s, String[] strings) called");
		return false;
	}

	public int getResultSetHoldability() throws SQLException {
		log.info("getResultSetHoldability() called");
		return 0;
	}

	public boolean isClosed() throws SQLException {
		log.info("isClosed() called");
		return false;
	}

	public void setPoolable(boolean b) throws SQLException {
		log.info("setPoolable(boolean b) called");
	}

	public boolean isPoolable() throws SQLException {
		log.info("isPoolable() called");
		return false;
	}

	public <T> T unwrap(Class<T> tClass) throws SQLException {
		log.info("unwrap(Class<T> tClass) called");
		return null;
	}

	public boolean isWrapperFor(Class<?> aClass) throws SQLException {
		log.info("isWrapperFor(Class<?> aClass) called");
		return false;
	}
}
