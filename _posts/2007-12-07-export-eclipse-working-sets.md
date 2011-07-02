---
title: Export Eclipse Working Sets
layout: default
--- # Categories
- eclipse
- programming
- tidbits
---

I came across a question related to this on <a href="http://eclipsezone.com">EclipseZone</a> where I posted a <a href="http://eclipsezone.com/eclipse/forums/t104065.html">response</a> (and a follow-up). It seemed like something that I should blog about for future reference.

<b>Export Working Set Definition</b>

If you just want to share your working set definition with other members on your team, it's very easy.

<ol>
<li>Via the menu bar: File > "Export..."</li>
<li>Select "General" / "Working Sets"</li>
<li>Click "Next >"</li>
<li>Select the working set(s) you want to export and where you want to export the file.</li>
<li>Click "Finish" to perform the export.</li>
</ol>

You will have a file containing the project-based definition of your working set. This can be imported by your team so that they can have the same working set, as long as they have the files that it represents with the same paths. Generally, this is probably a safe assumption for people on the same team using the same IDE.

<b>Export Working Set Files</b>

If you want to export the actual files contained in your working set, the steps are a little different, but they make sense once you think about it.

<b>Note:</b> I generally have the "Top Level Emements" of my views set to "Working Sets", so this is based on that assumption. It is easy to toggle (View arrow menu).

<ol>
<li>Right-click on the working set you want to export</li>
<li>Select "Export..."</li>
<li>Select "File System" (jar and archive will probably work with this too)</li>
<li>You will then have the File system export dialog with your working set pre-populated.</li>
<li>Select the directory you want to export to</li>
<li>You can either export only the directories explicitly defined in your working set using "Create only selected directories" or you can create any missing parent directories using "Create directory structure for files".</li>
<li>Click Finish and you have your exported files.</li>
</ol>

This method does not export the working set definition, just the files themselves. Using both methods you could export the working set and the files so that your team can have everything they need.

These techniques are useful when sharing files or when you want to extract a component and create a new project from it.
