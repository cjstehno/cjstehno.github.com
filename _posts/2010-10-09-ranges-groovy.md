---
title: Converting Numbers to Ranges: Groovy
layout: default
--- # Categories
- groovy
- programming
- puzzle
---

A while back, I worked out a coding exercize (see <a href="http://coffeaelectronica.com/blog/2009/11/converting-numbers-to-ranges-java/">Converting Numbers to Ranges: Java</a>) where you convert a set of string input numbers and convert them to a more condensed range format, such as

<pre>1 2 3 6 8 10 11 12 15 --becomes--> 1-3, 6, 8, 10-12, 15.</pre>

I thought about doing a version of this with Groovy at the time, but it didn't seem like it would be all that different from that Java version. Well, having been working with Groovy a lot more lately, I decided to give it a whirl and here is what I came up with:

<code lang="groovy">
def rangeize( items ){
    def hold, str = ''
    
    items.eachWithIndex { it,i->
        if( (it as int)+1 == (items[i+1] as int) ){
            if( !hold ) hold = it
        } else {
            str += hold ? "$hold-${it}, " : "${it}, "
            hold = null
        }
    }
    
    str[ 0..(str.length()-3) ] + '.' 
}
</code>

The problem states that the input will be in order and have no duplicate entries, so I don't need to account for those.

Other than some slightly simplified syntax, it's not all that much different from the Java version, thought it's a bit more condensed.

I kept the string to int conversions inline on this version as I am still up in the air about how much overhead that would really add. You could just as easily convert the string array before doing the grouping.

Always fun to practice.
