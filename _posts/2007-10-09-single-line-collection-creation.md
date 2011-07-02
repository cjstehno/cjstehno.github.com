---
title: Single-line Collection Creation
layout: default
--- # Categories
- java
- programming
- tidbits
---

I like having nice compact code. No, I am not one of those 'write the whole app on one line' developers, but I do like code collapsed and out of the way. One of the things that has always annoyed me was that while you can create and populate an array on one line, you cannot do the same with <tt>Map</tt>s, <tt>List</tt>s, and <tt>Set</tt>s... but I finally realized that there is a very simple way to do it using instance initializers.

<code lang="java">Map<String,String> map = new HashMap<String, String>(){{put("akey","avalue");}}</code>

Basically you are anonymously extending the <tt>HashMap</tt> and calling the <tt>put</tt> method to populate the data. Notice the double curley braces.

I am not suggesting that all of your collection populating should be done this way; however, it is nice when you simply want a single value put in a map for some reason.
