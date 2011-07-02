---
title: Playlist Randomization: Ruby-style
layout: default
--- # Categories
- programming
- ruby
- tidbits
---

Yesterday I posted a little script for randomizing WinAmp playlists, <a href="http://coffeaelectronica.com/blog/2007/12/playlist-randomization/">Playlist Randomization</a>. I ended the post with an interest in writing the same thing in <a href="http://ruby-lang.org">Ruby</a>. I was able to do it in about ten minutes.

<code lang="ruby">
# rand_playlist.rb
lines = []
File.open("#{ARGV[0]}","r") do |file|
    while(line = file.gets)
        unless line[0..0] == '#' 
            lines << line
        end
    end
end
    
lines.sort! { rand(3) - 1 }
    
out_file = File.new("random_#{ARGV[0]}","w");
lines.each do |line|
    out_file.puts line
end
</code>

You run this one the same as the last, except using ruby:

<pre>ruby rand_playlist.rb rock_n_roll.m3u</pre>

I wonder if there are other languages I should try implementing this in.
