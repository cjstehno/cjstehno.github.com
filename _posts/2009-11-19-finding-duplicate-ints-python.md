---
title: Finding Duplicate Ints: Python
layout: default
--- # Categories
- programming
- puzzle
- python
- thoughts
---

I decided to start playing around with <a href="http://python.org">Python</a> a bit and figured I should do what is becoming my personal tradition of implementing the <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Duplicate Int Finding Problem</a> with Python.

It's actually a very interesting language and I am surprised that I have not really looked into it sooner. It has a rich set of built-in libraries and tons of extension modules.

The code I came up with is pretty straight-forward:

<code lang="python">
def find_dup_int(items):
    items.sort()
    for i in range(len(items)-1):
        if items[i] == items[i+1]:
            return items[i]
    raise RuntimeError('No duplicate values found!')
</code>

I need to find a handful of other more interesting problems to try when playing with other languages, since this one seems to look basically the same in each language I have tried.

I will have to spend a little more time with Python, it feels very useful, and as you can see, not very verbose (though not to a fault).
