---
title: Perforce Frills
layout: default
--- # Categories
- eclipse
- java
- perforce
- programming
- scm
---

I know I have not finished the 1.0 release of my <a href="http://eclipsefrills.sourceforge.net">EclipseFrills</a> plug-in, but I have already started thinking about new frills. One set that I am really hot on right now are my <a href="http://perforce.com">Perforce</a> plug-in frills. There are a few little additions that I have wanted to add to their plug-in that I am finally going to implement.

<ul>
<li>Create String Containing Changelist File Paths</li>
<li>Create a Working Set from Changelist</li>
<li>Add Search of Changelist Files</li>
<li>Ability to Shelve/Unshelve Changelist</li>
<li>Export files in Changelist</li>
</ul>

The big one is the "Shelving and Un-shelving of changes". This is a feature present in <a href="http://jetbrains.com">IntelliJ</a> and even <a href="http://microsoft.com">Visual Studio</a>, but it has been lacking in <a href="http://eclipse.org">Eclipse</a>. I will be implementing it at first for Perforce, and then I will look into doing the same for Subversion and maybe CVS. Basically my plan for implementing this is simple, use the changelist to determine the files that have been edited (different from repository) then pull the local content of those files off into a storage location (not yet determined) for later recovery. You would then be free to revert your changes (or perhaps the shelver will do it for you) and go on about your business. Later, you are free to unshelve your changes by integrating them back into the code (using the Eclipse diff tool).

This is all at the beginning stage right now; however, it is something that I have wanted to have for a while and have already come across multiple uses for so I have some need to finish it soon. The downside is that Perforce being a commercial product, I only have access to it at work so I will have to develop it off-hours remotely or something which will take longer.

Also, being that this plug-in relies on commercial products, it will have its own "feature" separate from the other frills, though it will be available from the same download site.

I hope to have the 1.0 version of the frills done soon and to have a beta version of some of Perforce frills functionality before too long.

<b>Side Note:</b> If these features already exist in Eclipse, please let me know. I was unable to find anything like them currently available.


