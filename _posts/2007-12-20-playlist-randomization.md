---
title: Playlist Randomization
layout: default
--- # Categories
- groovy
- programming
- tidbits
---

I love <a href="http://winamp.com">WinAmp</a>; however, I have always felt that it's playlist randomization was a little on the weak side. Not really wanting to dive into writing a C++ winamp plugin, I took the alternate approach of writing a <a href="http://groovy.codehaus.org">Groovy</a> script to randomize playlist files.

<code>
// PlaylistRandomizer.groovy
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.security.SecureRandom

def songs = new ArrayList()
new File(args[0]).eachLine {
    if(!it.startsWith('#')){
        songs << it
    }
}
    
Collections.shuffle(songs,new SecureRandom())
    
def writer = new File("random_${args[0]}").newWriter()
songs.each {
    writer.writeLine(it)
}
writer.close()
    
println 'Done.'
</code>

You execute it with the file name of the playlist you want to shuffle.

<pre>groovy PlaylistRandomizer rock_n_roll.m3u</pre>

and it will generate a new, shuffled file, <tt>random_rock_n_roll.m3u</tt>.

It's pretty simple and straight-forward. I am sure that I could spend a bit more time with it and pare it down a bit, but isn't quick simplistic functionality one of the benefits of scripting languages?

<b>Note:</b> I used <tt>SecureRandom</tt> instead of just the standard <tt>Random</tt> because it provides better shuffling, though the difference is not all that significant.

For some fun and practice, I should implement the same script in <a href="http://ruby-lang.org">Ruby</a>.
