---
title: Ant Input Prompting and Private Targets
layout: default
--- # Categories
- ant
- building
- programming
- tidbits
---

I have found the <a href="http://ant.apache.org">Ant</a> <tt>input</tt> tag useful lately for setting up runtime parameters of an Ant build. We have a few different server configuration settings that vary based on which server the artifact is being built for and the <tt>input</tt> tag makes this really easy:

<code lang="xml"><input message="Enter configuration name: " addproperty="config.name" defaultvalue="${config.name.default}" /></code>

The downside of this is that it will prompt you to enter this every time you run the build, which can become annoying and really prohibits automated building. This is where the <tt>unless</tt> attribute of the <tt>target</tt> tag comes into play.

First create a private target (one whose name starts with "-") that will prompt for the config name:

<code lang="xml">
<target name="-prompt-for-config">
    <input message="Enter configuration name: " addproperty="config.name" defaultvalue="${config.name.default}" />
</target>
</code>
    
Then add the <tt>unless</tt> attribute to check for the presence of the <tt>config.name</tt> property:

<code lang="xml">
<target name="-prompt-for-config" unless="config.name">
    <input message="Enter configuration name: " addproperty="config.name" defaultvalue="${config.name.default}" />
</target>
</code>

which will cause this task to be run only if the specified property is not set. The you can have other tasks depend on this private task, which will only run if you have not specified the <tt>config.name</tt> property on the ant command line.

<code lang="xml">
<target name="compile" depends="-prompt-for-config" description="Compiles the java sources.">
    <!-- do stuff -->
</target>
</code>
    
Calling ant with the following will not prompt the user for the <tt>config.name</tt>:

<pre>ant compile -Dconfig.name=foo</pre>
    
I have used this in a few places now to make the build a bit more flexible, such as for doing server deployments, artifact installations, etc. It is a handy ant trick to keep in mind.
