---
title: Hidden Gems in Arrays.asList()
layout: default
--- # Categories
- java
- programming
- tidbits
---

I have used the <tt>Arrays.asList(Object)</tt> method numerous times over the years and I have not needed to look at the documentation for it in a while... apparently not since before Java 5. In Java 5 it has been given a nice new shiny coat of paint with generics and var-args.

<code lang="java">public static <T> List<T> asList(T... a)</code>

what this means is that now when you need to create a quick List of something for testing or other purposes, instead of

<code lang="java">List<String> strings = Arrays.asList(new String[]{"alpha","bravo","charlie"});</code>

you can simplify it down to...

<code lang="java">List<String> strings = Arrays.asList("alpha","bravo","charlie");</code>

Nothing astounding, but interesting and useful all the same. Saves a little typing. One thing to note here is that the <tt>List</tt> create by this method is unmodifiable so if you need to be able to add/remove items you will need to add the generated list to another list that is modifiable.
