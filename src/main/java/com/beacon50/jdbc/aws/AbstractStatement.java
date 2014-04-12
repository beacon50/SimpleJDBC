package com.beacon50.jdbc.aws;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 *
 */
public abstract class AbstractStatement implements Statement {
	
	final private Logger log = Logger.getLogger("com.beacon50.jdbc.aws.AbstractStatement");

    public int getMaxFieldSize() throws SQLException {
    	log.info("getMaxFieldSize() called");
        return 0;
    }

    public void setMaxFieldSize(int i) throws SQLException {
    	log.info("setMaxFieldSize(int i) called");
    }

    public int getMaxRows() throws SQLException {
    	log.info("getMaxRows()called");
        return 0;
    }

    public void setMaxRows(int i) throws SQLException {
    	log.info(" setMaxRows(int i) called");
    }

    public void setEscapeProcessing(boolean b) throws SQLException {
    	log.info("setEscapeProcessing(boolean b)  called");
    }

    public int getQueryTimeout() throws SQLException {
    	log.info("getQueryTimeout()  called");
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

   
    public boolean getMoreResults() throws SQLException {
    	log.info(" getMoreResults() called");
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


    public boolean getMoreResults(int i) throws SQLException {
    	log.info("getMoreResults(int i)  called");
        return false;
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
    	log.info("isPoolable()  called");
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
