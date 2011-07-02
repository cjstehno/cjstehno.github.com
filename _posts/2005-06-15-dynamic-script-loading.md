---
title: Dynamic Script Loading
layout: default
--- # Categories
- javascript
- programming
- tidbits
---

I figured out a way to dynamically load JavaScript files at runtime. There are times when you may not always need to import all of your external JavaScripts, or maybe you are using Ajax to load content into a div and you also need to import some script that the content needs. Here is the solution and it works in IE and <a href="http://mozilla.org/firefox">FireFox</a>: 

<code lang="javascript">
function loadLibrary(path){
    var headElt = document.getElementsByTagName("head").item(0);
    var scriptElt = headElt.appendChild(document.createElement("script"));
    scriptElt.setAttribute("type","text/javascript");
    scriptElt.setAttribute("src",path);
}</code>

Pretty simple, and all you have to do to use it is: 

<code lang="javascript">loadLibrary("scripts/myscript.js");</code>

This works for dynamically loading stylesheet too if you add a link element instead of a script element: 

<code lang="javascript">
function loadStylesheet(path){
    var headElt = document.getElementsByTagName("head").item(0);
    var scriptElt = headElt.appendChild(document.createElement("link"));
    scriptElt.setAttribute("type","text/css");
    scriptElt.setAttribute("rel","stylesheet");
    scriptElt.setAttribute("href",path);
}</code>

