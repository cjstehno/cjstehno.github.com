---
title: Proper Care and Feeding of Your JDBC Code
layout: default
--- # Categories
- java
- jdbc
- programming
- spring
- tidbits
---

If you�ve been developing very long, you have probably run across this numerous times, but for those of you that are new to JDBC, here is a quick tip that will help you greatly in the long run (I have seen it done wrong many times).

When you perform your connection retrieval (directly or from a pool - use a pool as often as possible), make sure that you clean up your resources when you are done.

<code lang="java">
Connection conn = null;
Statement st = null;
ResultSet rs = null;
try {
    conn = getConnection();
    st = conn.createStatement();
    rs = st.executeQuery(sql);
    while(rs.next()){
            // ... work it
    }
    // minimize code here to free conn ASAP.
} catch(Exception ex){
    // log this or something
} finally {
    if(rs != null){
            try {rs.close();} catch(Exception e){}
    }
    if(st != null){
            try {st.close();} catch(Exception e){}
    }
    if(conn != null){
            try {conn.close();} catch(Exception e){}
    }
}</code>

If you follow this format as a general rule, you will end up with fewer database related problems... especially if you use connection pooling. Connections that are not disposed of properly can cause very bad things to happen in your application. 

Also, note that I mention minimizing the code within the try/catch block. You want to use your connection and clean it up as soon as possible (again, especially if you are pooling) so that the resources are not hanging around needlessly. 

Another interesting point is the set of empty catch blocks. We have all been told that empty catch blocks are a bad thing; however, I have seen this style used in pretty much every description of this code. The damn JDBC developers decided that every method should throw a <tt>SQLException</tt>, even though there is little you can really do about it when you are trying to close up the connection. That is why you just ignore them; though here I ignore them separately so that one does not affect the others.

You can clean up this code even more if you use the <a href="http://commons.apache.org/dbutils">Jakarta Commons - DbUtils API</a>:

<code lang="java">
Connection conn = null;
Statement st = null;
ResultSet rs = null;
try {
    conn = getConnection();
    st = conn.createStatement();
    rs = st.executeQuery(sql);
    while(rs.next()){
            // ... work it
    }
    // minimize code here to free conn ASAP.
} catch(Exception ex){
    // log this or something
} finally {
    DbUtils.closeQuietly(rs);
    DbUtils.closeQuietly(st);
    DbUtils.closeQuietly(conn);
}</code>

You are not really required to close the <tt>ResultSet</tt>, and the <tt>Statement</tt> if you close the <tt>Connection</tt> properly; however, it adds an extra level of assurance especially when you are using a driver that may not be fully compliant or properly implemented.

The <a href="http://springframework.org">Spring Framework</a> also provides some very useful APIs for working with JDBC such as template methods, row mappers, and callback closure-like structures, an example would be:

<code lang="java">
new ConnectionCallback(){
    public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
        st = conn.createStatement();
        rs = st.executeQuery(sql);
        while(rs.next()){
            // ... work it
        }
        // minimize code here to free conn ASAP.
    }
}
</code>

There are quite a few helpful constructs in the Spring JDBC API to make JDBC interaction easier to work with.