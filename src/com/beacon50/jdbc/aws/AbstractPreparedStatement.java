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

/**
 *
 */
public abstract class AbstractPreparedStatement implements PreparedStatement{
    
    public ResultSet executeQuery() throws SQLException {
        return null;  
    }
  
    public void setNull(int i, int i1) throws SQLException {
        
    }
    
    public void setByte(int i, byte b) throws SQLException {
        
    }

    public void setShort(int i, short i2) throws SQLException {
        
    }

    public void setBytes(int i, byte[] bytes) throws SQLException {
        
    }
  
    public void setTime(int i, Time time) throws SQLException {
        
    }

    public void setTimestamp(int i, Timestamp timestamp) throws SQLException {
        
    }

    public void setAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {
        
    }

    public void setUnicodeStream(int i, InputStream inputStream, int i1) throws SQLException {
        
    }

    public void setBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {
        
    }

    public void clearParameters() throws SQLException {
        
    }

    public void setObject(int i, Object o, int i1) throws SQLException {
        
    }

   
    public boolean execute() throws SQLException {
        return false;  
    }

    public void addBatch() throws SQLException {
        
    }

    public void setCharacterStream(int i, Reader reader, int i1) throws SQLException {
        
    }

    public void setRef(int i, Ref ref) throws SQLException {
        
    }

    public void setBlob(int i, Blob blob) throws SQLException {
        
    }

    public void setClob(int i, Clob clob) throws SQLException {
        
    }

    public void setArray(int i, Array array) throws SQLException {
        
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return null;  
    }

    public void setDate(int i, Date date, Calendar calendar) throws SQLException {
        
    }

    public void setTime(int i, Time time, Calendar calendar) throws SQLException {
        
    }

    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar) throws SQLException {
        
    }

    public void setNull(int i, int i1, String s) throws SQLException {
        
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;  
    }

    public void setRowId(int i, RowId rowId) throws SQLException {
        
    }

    public void setNString(int i, String s) throws SQLException {
        
    }

    public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {
        
    }

    public void setNClob(int i, NClob nClob) throws SQLException {
        
    }

    public void setClob(int i, Reader reader, long l) throws SQLException {
        
    }

    public void setBlob(int i, InputStream inputStream, long l) throws SQLException {
        
    }

    public void setNClob(int i, Reader reader, long l) throws SQLException {
        
    }

    public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
        
    }

    public void setObject(int i, Object o, int i1, int i2) throws SQLException {
        
    }

    public void setAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        
    }

    public void setBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        
    }

    public void setCharacterStream(int i, Reader reader, long l) throws SQLException {
        
    }

    public void setAsciiStream(int i, InputStream inputStream) throws SQLException {
        
    }

    public void setBinaryStream(int i, InputStream inputStream) throws SQLException {
        
    }

    public void setCharacterStream(int i, Reader reader) throws SQLException {
        
    }

    public void setNCharacterStream(int i, Reader reader) throws SQLException {
        
    }

    public void setClob(int i, Reader reader) throws SQLException {
        
    }

    public void setBlob(int i, InputStream inputStream) throws SQLException {
        
    }

    public void setNClob(int i, Reader reader) throws SQLException {
        
    }

    public ResultSet executeQuery(String s) throws SQLException {
        return null;  
    }

    public int executeUpdate(String s) throws SQLException {
        return 0;  
    }

    public void close() throws SQLException {
        
    }

    public int getMaxFieldSize() throws SQLException {
        return 0;  
    }

    public void setMaxFieldSize(int i) throws SQLException {
        
    }

    public int getMaxRows() throws SQLException {
        return 0;  
    }

    public void setMaxRows(int i) throws SQLException {
        
    }

    public void setEscapeProcessing(boolean b) throws SQLException {
        
    }

    public int getQueryTimeout() throws SQLException {
        return 0;  
    }

    public void setQueryTimeout(int i) throws SQLException {
        
    }

    public void cancel() throws SQLException {
        
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;  
    }

    public void clearWarnings() throws SQLException {
        
    }

    public void setCursorName(String s) throws SQLException {
        
    }

    public boolean execute(String s) throws SQLException {
        return false;  
    }

    public ResultSet getResultSet() throws SQLException {
        return null;  
    }

    public int getUpdateCount() throws SQLException {
        return 0;  
    }

    public boolean getMoreResults() throws SQLException {
        return false;  
    }

    public void setFetchDirection(int i) throws SQLException {
        
    }

    public int getFetchDirection() throws SQLException {
        return 0;  
    }

    public void setFetchSize(int i) throws SQLException {
        
    }

    public int getFetchSize() throws SQLException {
        return 0;  
    }

    public int getResultSetConcurrency() throws SQLException {
        return 0;  
    }

    public int getResultSetType() throws SQLException {
        return 0;  
    }

    public void addBatch(String s) throws SQLException {
        
    }

    public void clearBatch() throws SQLException {
        
    }

    public int[] executeBatch() throws SQLException {
        return new int[0];  
    }

    public Connection getConnection() throws SQLException {
        return null;  
    }

    public boolean getMoreResults(int i) throws SQLException {
        return false;  
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return null;  
    }

    public int executeUpdate(String s, int i) throws SQLException {
        return 0;  
    }

    public int executeUpdate(String s, int[] ints) throws SQLException {
        return 0;  
    }

    public int executeUpdate(String s, String[] strings) throws SQLException {
        return 0;  
    }

    public boolean execute(String s, int i) throws SQLException {
        return false;  
    }

    public boolean execute(String s, int[] ints) throws SQLException {
        return false;  
    }

    public boolean execute(String s, String[] strings) throws SQLException {
        return false;  
    }

    public int getResultSetHoldability() throws SQLException {
        return 0;  
    }

    public boolean isClosed() throws SQLException {
        return false;  
    }

    public void setPoolable(boolean b) throws SQLException {
        
    }

    public boolean isPoolable() throws SQLException {
        return false;  
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;  
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;  
    }
}
