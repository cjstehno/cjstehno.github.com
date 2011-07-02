---
title: Sorting in JavaScript
layout: default
--- # Categories
- javascript
- programming
- tidbits
---

Every now and then you need to sort something in JavaScript and though, it's not all that hard to do, it is not the best-documented feature. Here is a quick little summary of how to do it.

You call the sort function of an array with your comparator. A JavaScript comparator is just a function that returns -1, 0, or 1 depending on whether a is less than b, a is equal to b, or a is greater than b:

<code lang="javascript">
myarray.sort(function(a,b){
    if(a < b){
        return -1;
    } else if(a == b){
        return 0;
    } else { // a > b
        return 1;
    }
});
</code>

This is just an example, your function can base the comparison on whatever you want, but it needs to return -1,0,1. Say you had a set of custom JavaScript objects that you want sorted by age:

<code lang="javascript">
var people = [{name:'Bob',age:21}, {name:'Fred',age:34}, {name:'Dan',age:19}];
</code>

You could easily sort them using

<code lang="javascript">
people.sort(function(a,b){
    if(a.age < b.age){
        return -1;
    } else if(a.age == b.age){
        return 0;
    } else { // a > b
        return 1;
    }
});
</code>

Not too hard to do. It's actually very similar to the Java Comparator interface.
