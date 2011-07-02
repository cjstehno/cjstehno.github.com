---
title: Java Preferences API
layout: default
--- # Categories
- java
- preferences
- programming
- tidbits
---

The Java Preferences API (java.util.preferences) appeared in the 1.4.x release of the JDK with little fanfare or ceremony; it was just one of the new features in the release and it is not one that you hear about very often. I don't think I have ever seen it being used anywhere, but it is quite flexible and useful.

Preferences are references hierarchical by package name, similar to the Java Logging API (or <a href="http://logging.apache.org">Log4J</a>). This allows for preferences to be stored a various levels (nodes) of the hierarchy.

There are two node branches availabe, the <tt>UserNode</tt> branch and the <tt>SystemNode</tt> branch. The JavaDocs state that

<blockquote>The precise description of "user" and "system" will vary from implementation to implementation.</blockquote>

But, in general "user" preferences will be different for each user of an application, while system preferences are more global in scope such as application configuration details.

You retrieve the preferences for the class you are working with with the following statement:

<code lang="java">
Preferences userPrefs = Preferences.userNodeForPackage(com.you.MyClass.class);
Preferences systemPrefs = Preferences.systemNodeForPackage(com.you.MyClass.class);
</code>

You can really use any class in your application as a reference point, as long as it is a logical association. Once you have a Preferences object, you can get and set values with simple accessor methods:

<code lang="java">
userPrefs.putInt("historyCount",10);
int histCnt = userPrefs.getInt("historyCount",5);

systemPrefs.putBoolean("allowEdit",true);
boolean allowEdit = systemPrefs.getBoolean("allowEdit",false);
</code>

Notice, that in the <tt>getInt()</tt> method the second parameter is a default value of the preference so that if it has not been defined yet, you still have a value to fall back on. Also, the data is persisted automatically… you don’t need to explicitly save it.

If you want to export your preferences for backup or import into another system, the API provides two export methods, one for just the current node, and the other for the current node and its child nodes.

<code lang="java">
userPrefs.exportNode( outstream ); // for just the one node, no child nodes

userPrefs.exportSubtree( outstream ); // for the node and its child nodes
</code>

Importing preferences is done using the <tt>importPreferences(InputStream)</tt> method, which will import all the given preferences into the local preference system.

<code lang="java">
Preferences.importPreferences( instream );
</code>

The import/export format for preferences is a defined XML format (see JavaDocs for DTD). The preferences I detailed earlier would look something like (results may vary):

<code lang="xml">
<preferences>
    <root type="user">
        <node name="com">
            <node name="you">
                <node name="MyClass">
                    <map>
                        <entry key="historyCount" value="10" />
                    </map>
                </node>
            </node>
        </node>
    </root>
    <root type="system">
        <node name="com">
            <node name="you">
                <node name="MyClass">
                    <map>
                        <entry key="allowEdit" value="false" />
                    </map>
                </node>
            </node>
        </node>
    </root>
</preferences>
</code>

One more point of interest is that you can register a listener for node and preference change events:

<code lang="java">
userPrefs.addNodeChangeListener( listener );
userPrefs.addPreferenceChangeListener( listener );
</code>

Node change events occur when a node is modified by adding or removing a child node. Preference change events are fired when the data contained in a preference node is modified. These could be useful for cases when you have interrelated preferences such that when you delete or change one of them, you need to modify the others accordingly.

So, the next time you think about using a <tt>Properties</tt> object, reconsider and think about whether or not a <tt>Preferences</tt> object might serve your needs better.
