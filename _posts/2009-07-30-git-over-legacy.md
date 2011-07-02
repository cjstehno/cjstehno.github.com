---
title: Git Over Your Legacy Environment
layout: default
--- # Categories
- git
- programming
- scm
---

I struggled for a good title for this posting so I am sorry if it's not very clear. The basic goal here is to show how <a href="http://git-scm.org">Git</a> can help you overcome a poor or obsolete development environment which you have no control over.


Let's say you are stuck with a sub-par source control (that you cannot change) and an IDE that is tightly bound to the server and/or very out-of-date.

I have been looking for ways to maintain my code with the required source control system and run it on the integrated IDE, while doing the bulk of my development on a modern full-featured IDE. (No, I cannot just check out the code and work with it, trust me). I finally got close to what I need by using Git. Basically you create a git repository from the source control managed project and then clone it to another directory so you can pull changes back and forth as needed.

Go into your normal source control managed project and type



<code>git init</code>



to create a git repository. Then you will want to add and commit all your relevant files using



<code>
git add *

git commit -m "initial commit"


</code>

though your needs may vary. Once you have everything in git you can clone this repository into another directory
 (non-existant)


<code>git clone -l --no-hardlinks . ../path/newdir
</code>


On the original repository you can make modifications and changes then go into the new repository and run



<code>git pull
</code>


to pull those changes into it. Once you make changes in the new location you can get (git) them into the original repository using 



<code>git pull ../path/newdir</code>



Just make sure you are doing commits to get the changes into git otherwise nothing will be transferred.


I don't really recommend this for this use if you can avoid it but it does show you how you can do a quick one-off workspace if needed... or as in my case, a modern working environment that does not interfere with the legacy systems.



Alas, even all of this does not help my in what I needed it for. Our source control at work is one of those micro-managing ones that considers itself the master of everything. The files are marked read-only, which is not a big deal but in order to get the scm to recognize the modification you need to check out the file, modify it, and check it back in which can only be done through the client interface (no command line). The only option would be to checkout the entire project, but it's also one of those SCMs that makes a big fuss if anyone has your file checked out.

Oh well.
