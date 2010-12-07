package com.beacon50.jdbc.aws;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 *
 */
public abstract class AbstractResultSet implements ResultSet {
    public boolean next() throws SQLException {
        return false;
    }
   
    public boolean wasNull() throws SQLException {
        return false;
    }

    public String getString(int i) throws SQLException {
        return null;
    }

    public boolean getBoolean(int i) throws SQLException {
        return false;
    }

    public byte getByte(int i) throws SQLException {
        return 0;
    }

    public short getShort(int i) throws SQLException {
        return 0;
    }

  

    public long getLong(int i) throws SQLException {
        return 0;
    }

    public float getFloat(int i) throws SQLException {
        return 0;
    }

    public double getDouble(int i) throws SQLException {
        return 0;
    }

    public BigDecimal getBigDecimal(int i, int i1) throws SQLException {
        return null;
    }

    public byte[] getBytes(int i) throws SQLException {
        return new byte[0];
    }

    public Date getDate(int i) throws SQLException {
        return null;
    }

    public Time getTime(int i) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(int i) throws SQLException {
        return null;
    }

    public InputStream getAsciiStream(int i) throws SQLException {
        return null;
    }

    public InputStream getUnicodeStream(int i) throws SQLException {
        return null;
    }

    public InputStream getBinaryStream(int i) throws SQLException {
        return null;
    }

    public String getString(String s) throws SQLException {
        return null;
    }

    public boolean getBoolean(String s) throws SQLException {
        return false;
    }

    public byte getByte(String s) throws SQLException {
        return 0;
    }

    public short getShort(String s) throws SQLException {
        return 0;
    }


    public long getLong(String s) throws SQLException {
        return 0;
    }

    public float getFloat(String s) throws SQLException {
        return 0;
    }

    public double getDouble(String s) throws SQLException {
        return 0;
    }

    public BigDecimal getBigDecimal(String s, int i) throws SQLException {
        return null;
    }

    public byte[] getBytes(String s) throws SQLException {
        return new byte[0];
    }

    public Date getDate(String s) throws SQLException {
        return null;
    }

    public Time getTime(String s) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(String s) throws SQLException {
        return null;
    }

    public InputStream getAsciiStream(String s) throws SQLException {
        return null;
    }

    public InputStream getUnicodeStream(String s) throws SQLException {
        return null;
    }

    public InputStream getBinaryStream(String s) throws SQLException {
        return null;
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    public void clearWarnings() throws SQLException {

    }

    public String getCursorName() throws SQLException {
        return null;
    }

    public int findColumn(String s) throws SQLException {
        return 0;
    }

    public Reader getCharacterStream(int i) throws SQLException {
        return null;
    }

    public Reader getCharacterStream(String s) throws SQLException {
        return null;
    }

    public BigDecimal getBigDecimal(int i) throws SQLException {
        return null;
    }

    public BigDecimal getBigDecimal(String s) throws SQLException {
        return null;
    }

    public boolean isBeforeFirst() throws SQLException {
        return false;
    }

    public boolean isAfterLast() throws SQLException {
        return false;
    }

    public boolean isFirst() throws SQLException {
        return false;
    }

    public boolean isLast() throws SQLException {
        return false;
    }

    public void beforeFirst() throws SQLException {

    }

    public void afterLast() throws SQLException {

    }

    public boolean first() throws SQLException {
        return false;
    }

    public boolean last() throws SQLException {
        return false;
    }

    public boolean absolute(int i) throws SQLException {
        return false;
    }

    public boolean relative(int i) throws SQLException {
        return false;
    }

    public boolean previous() throws SQLException {
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

    public int getType() throws SQLException {
        return 0;
    }

    public int getConcurrency() throws SQLException {
        return 0;
    }

    public boolean rowUpdated() throws SQLException {
        return false;
    }

    public boolean rowInserted() throws SQLException {
        return false;
    }

    public boolean rowDeleted() throws SQLException {
        return false;
    }

    public void updateNull(int i) throws SQLException {

    }

    public void updateBoolean(int i, boolean b) throws SQLException {

    }

    public void updateByte(int i, byte b) throws SQLException {

    }

    public void updateShort(int i, short i1) throws SQLException {

    }

    public void updateInt(int i, int i1) throws SQLException {

    }

    public void updateLong(int i, long l) throws SQLException {

    }

    public void updateFloat(int i, float v) throws SQLException {

    }

    public void updateDouble(int i, double v) throws SQLException {

    }

    public void updateBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {

    }

    public void updateString(int i, String s) throws SQLException {

    }

    public void updateBytes(int i, byte[] bytes) throws SQLException {

    }

    public void updateDate(int i, Date date) throws SQLException {

    }

    public void updateTime(int i, Time time) throws SQLException {

    }

    public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {

    }

    public void updateAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    public void updateBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    public void updateCharacterStream(int i, Reader reader, int i1) throws SQLException {

    }

    public void updateObject(int i, Object o, int i1) throws SQLException {

    }

    public void updateObject(int i, Object o) throws SQLException {

    }

    public void updateNull(String s) throws SQLException {

    }

    public void updateBoolean(String s, boolean b) throws SQLException {

    }

    public void updateByte(String s, byte b) throws SQLException {

    }

    public void updateShort(String s, short i) throws SQLException {

    }

    public void updateInt(String s, int i) throws SQLException {

    }

    public void updateLong(String s, long l) throws SQLException {

    }

    public void updateFloat(String s, float v) throws SQLException {

    }

    public void updateDouble(String s, double v) throws SQLException {

    }

    public void updateBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {

    }

    public void updateString(String s, String s1) throws SQLException {

    }

    public void updateBytes(String s, byte[] bytes) throws SQLException {

    }

    public void updateDate(String s, Date date) throws SQLException {

    }

    public void updateTime(String s, Time time) throws SQLException {

    }

    public void updateTimestamp(String s, Timestamp timestamp) throws SQLException {

    }

    public void updateAsciiStream(String s, InputStream inputStream, int i) throws SQLException {

    }

    public void updateBinaryStream(String s, InputStream inputStream, int i) throws SQLException {

    }

    public void updateCharacterStream(String s, Reader reader, int i) throws SQLException {

    }

    public void updateObject(String s, Object o, int i) throws SQLException {

    }

    public void updateObject(String s, Object o) throws SQLException {

    }

    public void insertRow() throws SQLException {

    }

    public void updateRow() throws SQLException {

    }

    public void deleteRow() throws SQLException {

    }

    public void refreshRow() throws SQLException {

    }

    public void cancelRowUpdates() throws SQLException {

    }

    public void moveToInsertRow() throws SQLException {

    }

    public void moveToCurrentRow() throws SQLException {

    }

    public Statement getStatement() throws SQLException {
        return null;
    }

    public Object getObject(int i, Map<String, Class<?>> stringClassMap) throws SQLException {
        return null;
    }

    public Ref getRef(int i) throws SQLException {
        return null;
    }

    public Blob getBlob(int i) throws SQLException {
        return null;
    }

    public Clob getClob(int i) throws SQLException {
        return null;
    }

    public Array getArray(int i) throws SQLException {
        return null;
    }

    public Object getObject(String s, Map<String, Class<?>> stringClassMap) throws SQLException {
        return null;
    }

    public Ref getRef(String s) throws SQLException {
        return null;
    }

    public Blob getBlob(String s) throws SQLException {
        return null;
    }

    public Clob getClob(String s) throws SQLException {
        return null;
    }

    public Array getArray(String s) throws SQLException {
        return null;
    }

    public Date getDate(int i, Calendar calendar) throws SQLException {
        return null;
    }

    public Date getDate(String s, Calendar calendar) throws SQLException {
        return null;
    }

    public Time getTime(int i, Calendar calendar) throws SQLException {
        return null;
    }

    public Time getTime(String s, Calendar calendar) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
        return null;
    }

    public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
        return null;
    }

    public URL getURL(int i) throws SQLException {
        return null;
    }

    public URL getURL(String s) throws SQLException {
        return null;
    }

    public void updateRef(int i, Ref ref) throws SQLException {

    }

    public void updateRef(String s, Ref ref) throws SQLException {

    }

    public void updateBlob(int i, Blob blob) throws SQLException {

    }

    public void updateBlob(String s, Blob blob) throws SQLException {

    }

    public void updateClob(int i, Clob clob) throws SQLException {

    }

    public void updateClob(String s, Clob clob) throws SQLException {

    }

    public void updateArray(int i, Array array) throws SQLException {

    }

    public void updateArray(String s, Array array) throws SQLException {

    }

    public RowId getRowId(int i) throws SQLException {
        return null;
    }

    public RowId getRowId(String s) throws SQLException {
        return null;
    }

    public void updateRowId(int i, RowId rowId) throws SQLException {

    }

    public void updateRowId(String s, RowId rowId) throws SQLException {

    }

    public int getHoldability() throws SQLException {
        return 0;
    }

    public boolean isClosed() throws SQLException {
        return false;
    }

    public void updateNString(int i, String s) throws SQLException {

    }

    public void updateNString(String s, String s1) throws SQLException {

    }

    public void updateNClob(int i, NClob nClob) throws SQLException {

    }

    public void updateNClob(String s, NClob nClob) throws SQLException {

    }

    public NClob getNClob(int i) throws SQLException {
        return null;
    }

    public NClob getNClob(String s) throws SQLException {
        return null;
    }

    public SQLXML getSQLXML(int i) throws SQLException {
        return null;
    }

    public SQLXML getSQLXML(String s) throws SQLException {
        return null;
    }

    public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {

    }

    public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {

    }

    public String getNString(int i) throws SQLException {
        return null;
    }

    public String getNString(String s) throws SQLException {
        return null;
    }

    public Reader getNCharacterStream(int i) throws SQLException {
        return null;
    }

    public Reader getNCharacterStream(String s) throws SQLException {
        return null;
    }

    public void updateNCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    public void updateNCharacterStream(String s, Reader reader, long l) throws SQLException {

    }

    public void updateAsciiStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    public void updateBinaryStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    public void updateCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    public void updateAsciiStream(String s, InputStream inputStream, long l) throws SQLException {

    }

    public void updateBinaryStream(String s, InputStream inputStream, long l) throws SQLException {

    }

    public void updateCharacterStream(String s, Reader reader, long l) throws SQLException {

    }

    public void updateBlob(int i, InputStream inputStream, long l) throws SQLException {

    }

    public void updateBlob(String s, InputStream inputStream, long l) throws SQLException {

    }

    public void updateClob(int i, Reader reader, long l) throws SQLException {

    }

    public void updateClob(String s, Reader reader, long l) throws SQLException {

    }

    public void updateNClob(int i, Reader reader, long l) throws SQLException {

    }

    public void updateNClob(String s, Reader reader, long l) throws SQLException {

    }

    public void updateNCharacterStream(int i, Reader reader) throws SQLException {

    }

    public void updateNCharacterStream(String s, Reader reader) throws SQLException {

    }

    public void updateAsciiStream(int i, InputStream inputStream) throws SQLException {

    }

    public void updateBinaryStream(int i, InputStream inputStream) throws SQLException {

    }

    public void updateCharacterStream(int i, Reader reader) throws SQLException {

    }

    public void updateAsciiStream(String s, InputStream inputStream) throws SQLException {

    }

    public void updateBinaryStream(String s, InputStream inputStream) throws SQLException {

    }

    public void updateCharacterStream(String s, Reader reader) throws SQLException {

    }

    public void updateBlob(int i, InputStream inputStream) throws SQLException {

    }

    public void updateBlob(String s, InputStream inputStream) throws SQLException {

    }

    public void updateClob(int i, Reader reader) throws SQLException {

    }

    public void updateClob(String s, Reader reader) throws SQLException {

    }

    public void updateNClob(int i, Reader reader) throws SQLException {

    }

    public void updateNClob(String s, Reader reader) throws SQLException {

    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
