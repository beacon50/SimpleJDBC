package com.beacon50.jdbc.aws;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 */
public abstract class AbstractResultSetMetaData implements ResultSetMetaData {
    
   

    public boolean isAutoIncrement(int i) throws SQLException {
        return false;
    }

    public boolean isCaseSensitive(int i) throws SQLException {
        return false;
    }

    public boolean isSearchable(int i) throws SQLException {
        return false;
    }

    public boolean isCurrency(int i) throws SQLException {
        return false;
    }

    public int isNullable(int i) throws SQLException {
        return 0;
    }

    public boolean isSigned(int i) throws SQLException {
        return false;
    }

    public int getColumnDisplaySize(int i) throws SQLException {
        return 0;
    }


    public String getSchemaName(int i) throws SQLException {
        return null;
    }

    public int getPrecision(int i) throws SQLException {
        return 0;
    }

    public int getScale(int i) throws SQLException {
        return 0;
    }

    public String getTableName(int i) throws SQLException {
        return null;
    }

    public String getCatalogName(int i) throws SQLException {
        return null;
    }

   

    public String getColumnTypeName(int i) throws SQLException {
        return null;
    }

    public boolean isReadOnly(int i) throws SQLException {
        return false;
    }

    public boolean isWritable(int i) throws SQLException {
        return false;
    }

    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;
    }

    public String getColumnClassName(int i) throws SQLException {
        return null;
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
