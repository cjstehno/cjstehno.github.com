---
title: Simple Hashing
layout: default
--- # Categories
- java
- programming
- tidbits
---

A co-worker showed me this recently. Say you have the following user information

<pre>
Name: John Doe
YOB: 1973
</pre>

and you need to generate a six character string identifier for the user. First, you need to convert the name to a long by parsing it with a radix of 36. 

<code lang="java">long nameId = Long.parseLong("John Doe",36);</code>

Then, lets use an exclusive OR (^) to blend the name and birth year to get a new identifier value (adds a little obfuscation):

<code lang="java">long id = 1973L ^ nameId;</code>

To limit the number of characters in the final string, we need to put an upper limit on the number by taking the modulus of the max value:

<code lang="java">
long limit = Long.parseLong("zzzzzz",36);
long value = id % limit;
</code>

Note that we want six characters so there are six Zs. To get the string value simply convert the long to a string using a radix of 36.

<code lang="java">String idstr = Long.toString(value,36);</code>

Interesting.
