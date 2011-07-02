---
title: CruiseControl Project Operations
layout: default
--- # Categories
- programming
- ruby
- tidbits
---

One of the developers I work with figured out the URL for firing the various CruiseControl build operations (resume, pause, build, etc) and it was jokingly noted that now all we need is a <a href="http://ruby-lang.org">Ruby</a> script to fire them.

And so, viola! Here is a simple ruby script that will do just that. It will run the specified operation command on one project (or all if no project is specified). You do have to configure the script with your CruiseControl url and your project names, but it's well worth it.

<code lang="ruby">
#
#	exec_in_cc.rb
#
#	Used to perform actions on the projects managed by CruiseControl. 
#	If no project is specified, the given operation will be run on all projects (internal array of them).
#
#	ruby exec_in_cc.rb operation [project]
#
#	Christopher J. Stehno (2/7/2008)
#
require 'net/http'
require 'uri'
   
# customize these to fit your pojects
cruise_url = 'http://builder:8000'
projects = ['nightly-build','releases','site-build']
   
# require the operation param
if ARGV[0] == nil
    puts "You must specify an operation!"
    exit
else 
    operation = ARGV[0]
end
    
# check for specified project, if none use all of them
unless ARGV[1] == nil then projects = [ARGV[1]] end
    
projects.each {|project|
    url = URI.parse(cruise_url)
    res = Net::HTTP.start(url.host, url.port) {|http|
        http.get("/invoke?operation=#{operation}&objectname=CruiseControl+Project%3Aname%3D#{project}")
    }
    success = res.body.index('Invocation successful') != nil ? 'Success' : 'Failed'
    puts "#{project}.#{operation}: #{success}"
}
</code>

This took about 30 minutes to write and will save at least that much time over the life of its use. Ruby is excellent for this kind of scripting. Something I have tried to do more often is to script tasks like this. You may feel that you are wasting time when you should be doing other things, but usually with a repetitive task like this you really notice the value. The code above is the whole script; however, I have also attached it to this post for download.

If you find it useful or come up with some good improvements to it, I would love to hear about them.

