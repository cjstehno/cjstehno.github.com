---
title: Converting Numbers to Ranges: Python
layout: default
--- # Categories
- programming
- puzzle
- python
---

I have decided to start doing little programming puzzle problems in various languages, since my duplicate int finding problem is getting old and repetitive. My first puzzle comes from a site called <a href="http://codegolf.com">Code Golf</a> and it is titled <a href="http://codegolf.com/home-on-the-range">Home on the Range</a>. I decided to start with <a href="http://python.org">Python</a> since it is something I have been playing with.

Basically the puzzle boils down to taking a series of numbers (in sequence) as input and producing a result where the numbers that are reduced to ranges where applicable, as below:

<pre>[1 2 3 6 9 10 11 15] becomes "1-3, 6, 9-11, 15."</pre>

After a couple iterations and a handful of language/api reference searches I came up with:

<code lang="python">
import sys
    
def convert_to_ranges(nums):
    ranges, holder = '',''
    num_count = len(nums)
    cap = endcap(num_count)

    for i in range(num_count):
        if i+1 < num_count and int(nums[i])+1 == int(nums[i+1]):
            if not holder: holder = nums[i]
        else:
            if holder:
                ranges += holder + '-' + nums[i] + cap(i)
                holder = ''
            else:
                ranges += nums[i] + cap(i)
    
    return ranges

def endcap(ln):
    return lambda idx: '.' if idx == ln-1 else ', '
    
print(convert_to_ranges(sys.argv[1:]))
</code>

I had originally had the <tt>holder</tt> variable as a list to hold each grouped number, but I realized that you really only need to store the first one so that you can use it to create the start of the grouping once you find the end of it. The lambda function (<tt>endcap()</tt>) was originally just a normal function, but I wanted to play with some interesting built-in features, and it actually worked out nicely. The python ternary operator is also interesting ( VAL if CONDITION else OTHERVAL ); it reads better than other ternary operators.

The <tt>int()</tt> calls I make in the function are simply there because the input comes from the standard in, where they are strings.



