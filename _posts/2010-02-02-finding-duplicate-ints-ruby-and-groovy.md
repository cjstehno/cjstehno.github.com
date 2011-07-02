---
title: Finding Duplicate Ints: Ruby and Groovy
layout: default
--- # Categories
- groovy
- programming
- puzzle
- ruby
- tidbits
---

I am going back to my old standard playtime function, <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">finding the duplicated integer</a> in a set of integers. I have been playing around a lot more with Groovy lately and I realized that I had never really done a <a href="http://groovy.codehaus.org">Groovy</a> version of that little function.

<code lang="groovy">
def findDups( n ){
    n.sort()
    for( i in (1..n.size())){
        if(n[i] == n[i-1]) return n[i]
    }
}
</code>

In this case, I am allowing null to be the value returned when no duplicate is found. It does seem to be a more realistic value for Groovy. It's a pretty straight forward function, along the same lines as the other languages. I thought maybe I could find some really cool feature of Groovy that would make this radically different from the Java version... nope. It does collapse nicely down to a single line though:

<code lang="groovy">
def findDups( n ){ n.sort(); for( i in (1..n.size()) ) if(n[i] == n[i-1]) return n[i] }
</code>

If you like that sort of thing.

Shortly after, I decided to do a quick little <a href="http://ruby-lang.org">Ruby</a> version as well:

<code lang="ruby">
def findDups( n )
    n.sort!()
    for i in (1..n.size())
        if(n[i] == n[i-1]) then return n[i] end
    end
    return nil
end
</code>

Nothing exciting in either case, but worth doing for completeness.
