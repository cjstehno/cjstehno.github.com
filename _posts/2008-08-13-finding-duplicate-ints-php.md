---
title: Finding Duplicate Ints: PHP
layout: default
--- # Categories
- php
- programming
- puzzle
- tidbits
---

In my over-long running thread about <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Finding Matching Ints</a> (initial posting), I have have come across another solution from a php developer that I recently interviewed... in php.

<code lang="php">
function findInts($array){
    $out = Array();
    foreach($array as $num){
        if(array_exist($out,$num){
            return $num;
        }
        array_push($num);
    }
}
</code>

He also mentioned the pre-sorting approach as well in order to speed things up. I still need to fully validate the php functions that he mentions, but it seems correct. I also didn't go into the error-case much with him, not really being a php expert myself.
