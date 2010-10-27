package com.beacon50.jdbc.aws;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public abstract class AbstractConnection implements Connection {
    
    public PreparedStatement prepareStatement(String s) throws SQLException {
        return null;  
    }

    public CallableStatement prepareCall(String s) throws SQLException {
        return null;  
    }

    public String nativeSQL(String s) throws SQLException {
        return null;  
    }

    public void setAutoCommit(boolean b) throws SQLException {
        
    }

    public boolean getAutoCommit() throws SQLException {
        return false;  
    }

    public void commit() throws SQLException {
        
    }

    public void rollback() throws SQLException {
        
    }

    public void close() throws SQLException {
        
    }

    public boolean isClosed() throws SQLException {
        return false;  
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return null;  
    }

    public void setReadOnly(boolean b) throws SQLException {
        
    }

    public boolean isReadOnly() throws SQLException {
        return false;  
    }

    public void setCatalog(String s) throws SQLException {
        
    }

    public String getCatalog() throws SQLException {
        return null;  
    }

    public void setTransactionIsolation(int i) throws SQLException {
        
    }

    public int getTransactionIsolation() throws SQLException {
        return 0;  
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;  
    }

    public void clearWarnings() throws SQLException {
        
    }

    public Statement createStatement(int i, int i1) throws SQLException {
        return null;  
    }

    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
        return null;  
    }

    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
        return null;  
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;  
    }

    public void setTypeMap(Map<String, Class<?>> stringClassMap) throws SQLException {
        
    }

    public void setHoldability(int i) throws SQLException {
        
    }

    public int getHoldability() throws SQLException {
        return 0;  
    }

    public Savepoint setSavepoint() throws SQLException {
        return null;  
    }

    public Savepoint setSavepoint(String s) throws SQLException {
        return null;  
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        
    }

    public Statement createStatement(int i, int i1, int i2) throws SQLException {
        return null;  
    }

    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
        return null;  
    }

    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
        return null;  
    }

    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
        return null;  
    }

    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
        return null;  
    }

    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
        return null;  
    }

    public Clob createClob() throws SQLException {
        return null;  
    }

    public Blob createBlob() throws SQLException {
        return null;  
    }

    public NClob createNClob() throws SQLException {
        return null;  
    }

    public SQLXML createSQLXML() throws SQLException {
        return null;  
    }

    public boolean isValid(int i) throws SQLException {
        return false;  
    }

    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
        
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        
    }

    public String getClientInfo(String s) throws SQLException {
        return null;  
    }

    public Properties getClientInfo() throws SQLException {
        return null;  
    }

    public Array createArrayOf(String s, Object[] objects) throws SQLException {
        return null;  
    }

    public Struct createStruct(String s, Object[] objects) throws SQLException {
        return null;  
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;  
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;  
    }
}
