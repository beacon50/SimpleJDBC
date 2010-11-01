package com.beacon50.jdbc.aws;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 *
 */
public abstract class AbstractStatement implements Statement {


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
