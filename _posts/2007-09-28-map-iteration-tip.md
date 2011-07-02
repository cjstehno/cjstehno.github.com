---
title: Map Iteration Tip
layout: default
--- # Categories
- java
- programming
- tidbits
---

Say you have a <tt>Map</tt> and that you need to iterate over its contents and do something with both the key and the value for each mapping. I have often seen the following code used:

<code lang="java">
Iterator i = map.keySet().iterator();
while(i.hasNext()){
    Object key = i.next();
    Object val = map.get(key);
    // do something with them
}</code>

While this is correct and generally there is nothing wrong with it, you are doing an extra <tt>get()</tt> call into the map; however, if you iterate over the entry Set you can remove that extra call:

<code lang="java">
Iterator i = map.entrySet().iterator();
while(i.hasNext()){
    Entry entry = (Entry)i.next();
    Object key = entry.getKey();
    Object val = entry.getValue();
    // do something with them
}</code>

Also, as a side note, with Java 5 and above you can use the new foreach loop to simplify things even more:

<code lang="java">
for(Entry<Object,Object> entry : map.entrySet()){
    Object key = entry.getKey();
    Object val = entry.getValue();
    // do something
}</code>

It’s not going to double your processing speed or anything, but it is more efficient, especially when you are iterating over a large map of items.
