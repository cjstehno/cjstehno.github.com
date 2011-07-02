---
title: Find Matching Ints with JavaScript
layout: default
--- # Categories
- javascript
- programming
- puzzle
- tidbits
---

I realized that I had yet to come up with a JavaScript solution for my duplicate ints interview question, (see <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Interview Question: Find 2 Matching Ints</a>) so I decide a quick implementation would be fun.

<code lang="html">
<html>
    <head>
        <script type="text/javascript">
            function findDup(items){
                items.sort();
                for( var i=0; i<items.length-1; i++){
                    if( items[i] == items[i+1] ) return items[i];
                }
                throw "No duplicate values!";
            }
        </script>
    </head>
    <body onload="alert( findDup( new Array( 6, 9, 2, 5, 1, 6) ) )">
    </body>
</html>
</code>

There is not much to it, and nothing really exciting. I also worked up quick versions using <a href="http://prototypejs.org">Prototype</a> and <a href="http://jquery.com">JQuery</a>; however, neither one really provided any useful deviation from the standard JavaScript approach.
