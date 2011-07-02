---
title: ServletContext Revelations
layout: default
--- # Categories
- java
- programming
- servlet
- tidbits
---

From the <tt>ServletContext</tt> JavaDocs

<blockquote>There is one context per "web application" per Java Virtual Machine. (A "web application" is a collection of servlets and content installed under a specific subset of the server's URL namespace such as /catalog and possibly installed via a .war file.)</blockquote>

I never really thought too deeply about how the <tt>ServletContext</tt> behaved until I saw it used differently at my new job. I am used to getting an instance of  <tt>ServletContext</tt> and accessing it directly, which is fine in the case of web applications that consist of a single context (or .war file); however, now I am dealing with an application that spans across multiple application contexts (and .war files)...  <tt>ServletContext</tt> can be used a bit differently under such circumstances.

Consider two web applications, Foo and Bar, each deployed on the same server. In the "Foo" application, if you call <tt>getServletContext()</tt> and then <tt>getContextPath()</tt>, it will be "/Foo", and likewise on "Bar" the same code would yield "/Bar". The interesting part of this is that if, in "Foo" set an attribute on the  <tt>ServletContext</tt>, it will not be directly accessible on "Bar"; however, you can get it by getting a reference to the "Foo"  <tt>ServletContext</tt>... this is the part I had never seen before.

<code lang="java">
// on Foo
getServletContext().setAttribute("foo","I have foo");

// on Bar
getServletContext().getAttribute("foo") // is null
getServletContext("/Foo").getAttribute("foo"); // is "I have foo"
</code>

It makes sense when you think about it but it surprised me that after all the years I have been working with web applications, that I had never run across a multi-war application that needed to cross-communicate.

You can also, it seems, use "/", but perhaps that is only if you have a ROOT context available... I will have to test that one a little more. Along those same lines, you can use the full path to resolve the context, such as a servlet path, though I would imagine that it does have to be a valid path. If you do try and use a context that does not exist, you will get an error.
