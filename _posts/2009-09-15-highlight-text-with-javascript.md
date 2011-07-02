---
title: Highlight Text with JavaScript
layout: default
--- # Categories
- javascript
- programming
- prototype
- tidbits
---

A question arose recently about how to highlight a word or words in the text of a <tt>div</tt> element. It turns out that it's actually pretty easy using <a href="http://prototypejs.org">Prototype</a>.

The example below is the code needed to highlight each occurrance (up to ten of them) of the word 'pick' in the div. The operation will be performed when the content div is clicked.
    
<code lang="html">
<html> 
    <head> 
        <style type="text/css">
            #content span { background-color: yellow; }
        </style>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.2/prototype.js"></script>
        <script type="text/javascript">
            Event.observe(window,'load',function(){
                $('content').observe('click',highlight);
            });
            
            function highlight(){
                $('content').innerHTML = $('content').innerHTML.sub('pick','<span>pick</span>',10);                
            }
        </script>
    </head>
    <body>
    
        <div id="content">
            Peter Piper picked a peck of pickeled peppers. 
            How many peppers did Peter Piper pick?
        </div>
    
    </body>
</html>
</code>

<b>Note:</b> I used to Google-hosted version of the <a href="http://www.prototypejs.org/2008/5/27/prototype-hosted-on-google-s-servers">prototype library</a> which is handy.

This could easily be refactored to do any sort of style operation to the selected text, or replace it altogether. I will have to give this a try with JQuery as a comparison.
