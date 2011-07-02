---
title: Java and IPv6
layout: default
--- # Categories
- development
- java
- programming
- tidbits
---

In case you have not run into it yet, <a href="http://en.wikipedia.org/wiki/IPv6">IPv6</a> is creeping into our lives. Recently I had to work on functionality that had to support the storing of both IPv4 and IPv6 in a database record... and it's actually pretty easy.

Let's say we have tow IP addresses, one of each type:

<code>
157.166.224.26
1080:0:0:0:8:800:200C:417A</code>

The first thing we need to do is parse the string values into <tt>java.net.InetAddress</tt> objects.

<code lang="java">InetAddress inet = InetAddress.getByName( ipstr );</code>

which works in either case. From there you may want to determine which type of address you are working with. The only way that I found (though I didn't spend all that long) was to do an instance check:

<code lang="java">
if( inet instanceof Inet6Address ){ 
	// you have an IPv6 address
} else if( inet instanceof Inet4Address ){
	// you have an IPv4 address
} else {
	// I dont know what you have
}
</code>

From there you will need to convert the InetAddress object into bytes for storage. You could store the addresses as strings, but you end up more dependant on hacky format recognition tricks, and the byte conversion is nice and pre-built for you:

<code lang="java">byte[] bytes = inet.getAddress();</code>

Now you are ready to store the address in the database. Without going into all the db-related code you end up with:

<code lang="java">statement.setBytes( 1, bytes );</code>

You could just as easily write the bytes to an output stream or other storage format.

Once you have the address stored somewhere, you need to pull it out again. First you need to read the bytes from your storage location. Assuming a database, you get something like:

<code lang="java">byte[] inbytes = resultSet.getBytes( 1 );</code>

With these bytes you can create an InetAddress again,

<code lang="java">InetAddress inetIn = InetAddress.getByAddress( inbytes );</code>

From there, a nice <tt>getHostAddress()</tt> call will get you your formatted IP address string, for both IPv4 and IPv6. Notice there is no code specific to either type. The instance checking is only required if you want to know specifically which type you have... and there may be a better way to do that, I didn't come across one.

One oddity I found is that IPv4 addresses mapped to IPv6 addresses can get a little strange depending on how your application handles them; the InetAddress class parses them fine, but converts them to IPv4 addresses rather than noting that they are mapped addresses. Not a big deal, but something to keep in mind.
