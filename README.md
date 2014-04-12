#SimpleJDBC

SimpleJDBC is a JDBC driver for [Amazon's SimpleDB](http://aws.amazon.com/simpledb/).
Thus, this driver makes it possible to use the myriad tools available that leverage standard JDBC
interfaces to facilitate working with relational data (i.e. SimpleJDBC enables ETL tools to import or
extract data from SimpleDB via existing logic coded for working with JDBC). All SimpleDB
communication is via Amazon's SDK.

Keep in mind, SimpleDB isn't a traditional relational database -- in fact, it's quite different.
Nevertheless, it is possible to map basic SQL commands (and therefore JDBC) to the core API offered by Amazon.
Using SimpleJDBC is straightforward. All that is required is Java 5 and the jar files provided in the download.

## Using it

You load SimpleJDBC like you would any other JDBC driver.

```
Properties prop = new Properties();
prop.setProperty("secretKey", ...);
prop.setProperty("accessKey", ...);

Class.forName("com.beacon50.jdbc.aws.SimpleDBDriver");
Connection con = DriverManager.getConnection("jdbc:simpledb://sdb.amazonaws.com", prop);
```

Note, with Amazon's web services, there is no notion of a username or password --
authorization is provided by keys; thus, to use SimpleJDBC, you must provide your Amazon secret key and your access key.

With a Connection instance, you can issue queries like `SELECT`, `UPDATE`, `DELETE`, and `INSERT` --
you can use JDBC `Statement`s or even `PreparedStatement`s (although, the notion of a pre-compiled
SQL query in SimpleDB doesn't exist; consequently, there isn't any affect on performance).

For example, SQL `INSERT`s are handled just like normal:

```
Statement st = conn.createStatement();
String insert = "INSERT INTO users (name, age) VALUES ('Ann Smith', 33)";
int val = st.executeUpdate(insert);
```

or

```
PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, age) VALUES (?, ?)");
pstmt.setString(1, "Annie Smith");
pstmt.setInt(2, 33);
int val = pstmt.executeUpdate();
```

Note, because SimpleDB lacks rich data types (everything is a string),
SimpleJDBC attempts to encode and decode numeric values. Thus, in the two `INSERT` statements above,
33 will be zero padded when stored in SimpleDB (i.e. 000033) and decoded back to 33 upon a `SELECT` query.

You can use the all too familiar JDBC ResultSet too:

```
String qry = "select * from users where name = 'Joe Smith'";
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery(qry);

while (rs.next()) {
    int iage = rs.getInt("age");
    //...
}
```

SimpleJDBC supports the standard SQL `select count(*) from YOUR_DOMAIN`.
The resultant `ResultSet` will contain a record named count, which you can then retrieve via a `getInt` call.

```
String qry = "select count(*) from users_tst";
Statement st = conn.createStatement();
ResultSet rs = st.executeQuery(qry);

while (rs.next()) {
   int icnt = rs.getInt("count");
  //do something...
}
```

Most basic SQL/JDBC features have been implemented -- you can view various test cases for
more details. Note -- using SimpleDB forces you to understand eventual consistency; that is,
ACID-ness isn't supported in SimpleDB. Nevertheless, in exchange for consistency (the "C" in ACID) you get
massive scalability + reliability. In practice, this means you might run into cases where an immediate
SELECT following an INSERT might not return a result. Consequently, you'll see that all test cases in
SimpleJDBC sleep so as to provide for a deterministic situation in a JUnit environment.


##What is SimpleDB?

SimpleDB is a cloud-based key/value store exposed via a web service interface. SimpleDB is a
massively scalable, highly available datastore -- it's logically defined as domains containing items.
SimpleDB also permits items to contain attributes. Think of a domain much like you would a table in
the relational sense. Domains can have many items (which are similar to rows) and items can have many
attributes (which are like columns in the relational world).

Attributes are really just name/value pairs and the "pair" aspect isn't limited to one value. That is,
an attribute name can have a collection (or list) of associated values. What's more, all data within
SimpleDB is represented as a String, which is distinctly different from a standard RDBMS, which typically
support myriad datatypes.

SimpleDB doesn't support the notion of cross-domain joins, so you can't query for items in multiple
domains. You could, however, overcome this limitation by performing multiple SimpleDB queries and doing
the join on your end. Items don't have keys per se. The key or unique identifier for an item is the item's name.

Searching in SimpleDB is different: data comparisons are done lexicographically. Lexicographic
searching can cause issues when looking for numbered data (including dates), but all is not lost.
One way to fix the searching-on-distance issue is by padding numeric data (which this driver attempts to facilitate).

What SimpleJDBC supports:

`INSERT` statements:
  * create a domain (i.e. table) if not already in existence
  * Thus, `"INSERT INTO Races (Name, Distance) VALUES ('Richmond Marathon', 26.2)"` is a valid insert statement
    that will create a domain named `Races` with an item having a unique id attribute (item name) and two attributes:
    `Name` and `Distance` with values
  * Numeric values are encoded 
  * All inserted objects are given an id (if one isn't provided (i.e. id or ID)).
    Right now, this `id` isn't guaranteed to be unique.

`DELETE` statements
  * Deletes only support comparing values by equals (=); that is, the
    statement `"DELETE from Races where Name = 'Richmond Marathon'"` works
    but `"DELETE from Races where distance > 13.1"` would not (yet)

`UPDATE` statements
  * Numeric values are encoded

`SELECT` statements
  * Numeric values are encoded and decoded


TODO:
 see Google code issue list (http://code.google.com/p/simpledb-jdbc/issues/list)