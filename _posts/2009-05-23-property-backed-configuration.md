---
title: Property-backed Configuration
layout: default
--- # Categories
- development
- java
- programming
- tidbits
---

A useful trick I started doing for property-backed configuration files is to provide something a little more rich than a simple <tt>Properties</tt> object as a provider. Use a <tt>Properties</tt> instance as a delegate to pull out the properties, but provide a more useful interface for the values themselves.

Say you have a properties file such as:

<pre>path.storage = /some/path</pre>

You could simply access these via a <tt>Properties</tt> instance, and use it directly as in:

<code lang="java">
String path = props.getProperty("path.storage");
File file = new File(path,"archive.zip");
</code>

but consider a potentially more useful approach

<code lang="java">File file = config.getStorageFile("archive.zip");</code>

with 

<code lang="java">
public File getStorageFile(String name){
    return new File( props.getProperty("path.storage"), name );
}</code>

You could even pull the file extension into the method if it made sense to do so... say, if all files in that path were .zip files you could then enforce more control on how it was used such as:

<code lang="java">
public File getStorageFile(String name){
    return new File(props.getProperty("path.storage"),name + ".zip");
}</code>

<code lang="java">File file = config.getStorageFile("archive");</code>

These helper methods would be part of a class called Config or something similar that delegates to an internal <tt>Properties</tt> object:

<code lang="java">
public class Config {
		
    private final Properties props;
		
    public Config(Properties p){
        this.props = p;
    }
		
    public File getStorageFile(String name){
        return new File(props.getProperty("path.storage"),name);
    }
}</code>

You could also do the loading of the properties file inside this class. Some property validation could also be helpful.

This strategy really helped to clean up a project that originally had a lot of properties that were used all over the place with very little order. Converting to this approach actually made the code more understandable and less error prone as well.
