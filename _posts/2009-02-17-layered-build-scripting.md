---
title: Layered Build Scripting
layout: default
--- # Categories
- ant
- development
- java
- programming
- thoughts
---

In a recent posting, <a href="http://coffeaelectronica.com/blog/2009/01/boilerplate-ant-build/">Boilerplate Ant Build</a>, I presented my basic Ant build starting point. This post takes that one step farther and presents suggestions for using an <a href="http://ant.apache.org">Ant</a> build file as a tool box for building more "smart" build tools.

As a developer you should strive to keep your project buildable and accessible to all the bells and whistles of your IDE while also maintaining a simple straight-forward command line interface for use in other tools or at those times when you don't need/want to fire up your IDE for a simple task. Ant is a great build automation tool that is supported on the command line and by all the major IDEs.

With your Ant Build Script you can compile, test, and deploy the project with simple command line entries or IDE actions. As I started to use this approach more and more I found that I ended up using the same commands over and over and some of them were on the long-ish side especially when typed in the command line:

<pre>ant clean-all test deploy-remote -Ddeploy.remote.conn=myuname:mypword@server:/home/myuname/artifacts</pre>
    
Not hard to pull from memory after you do it a few times, but when you are in a hurry you are more apt to make a mistake. What I like to do, and I recommend, is to create simple shortcut helper scripts for running these common commands. Your first thought might be to just add them to the build.xml file, and this is not a horrible idea, but I prefer keeping them separate from the "official" builder since in some cases these scripts may only work for my environment or under special circumstances. They are secondary tools that make use of your build tool kit.

So how should you write them? You could create a second Ant build file for your tasks and just call ant with that file, which could make ant calls into the main build
script.

<pre>ant -f yourfile.xml clean-dev-deploy</pre>
    
Nothing wrong with that, but I recommend learning a multi-platform scripting language such as <a href="http://groovy.codehaus.org">Groovy</a> or <a href="http://ruby-lang.org">Ruby</a> and doing your secondary script using that language. Apart from keeping your skills fresh and learning a new language, it also provides a very rich scripting environment that can be used on any platform supported by the language (Groovy and Ruby are supported on Windows, Linux and Mac). You may wonder why I am not promoting bash shell scripting here and one reason is that I have never been very fond of it, but also it is more tied to the operating system and less functional when compared with the other two I mentioned.

These scripts should reside in the root of the project directory right along side your build.xml file for ease of management and use. They should be checked into your source control, though you may want to XXX-out passwords (meaning you will need to checkout a copy and customize it for your use - and not check it back in) or parametrize it to add those in at run time.

The deployment command line I mentioned previously becomes simple and repeatable with a little Ruby foo...

<code lang="ruby">puts `ant clean-all test deploy-remote -Ddeploy.remote.conn=myuname:mypword@server:/home/myuname/artifacts`</code>
    
I end up with a simple script that will run the same set of tasks every time without a chance of me flubbing the directory or username. I tend to give them short concise names that distinguish what they are used for, such as <tt>devpush.rb</tt> in this case. You can point your IDE to this script and have it run it with the click of a button (at least in <a href="http://eclipse.org">Eclipse</a> you can, not sure of others). You can also run it on the command line

<pre>ruby devpush.rb</pre>

which is the most portable way, or if you make the script executable (<tt>chmod +x</tt>):

<pre>./devpush.rb</pre>
    
I have these scripts for every server that I deploy code to. Once you get the hang of your chosen scripting language you can also find a lot of other build/deployment related tasks to script such as the script I have for doing a tomcat deployment of a war file. It stops the server, archives the existing context, copies out the new war, cleans the log files and starts the server... all using a ruby script.

There are also some other interesting alternatives to Ant that I have taken an interest in lately, <a href="http://gant.codehaus.org/">Gant</a> and <a href="http://www.gradle.org/">Gradle</a>, both of which are build scripting DSLs based on Groovy; Gant is ant-like, while Gradle is more maven-esque. They both seem quite promising.

Keep your builds simple, fast and unambiguous so that you can focus on the real work of developing software <em>of</em> your project not <em>for</em> your project.

<blockquote><strong>Side note on choosing between Groovy and Ruby</strong>

I love both Ruby and Groovy as scripting languages so it's hard to really push one over the other. They each have their strengths and weaknesses. Ruby is great for build scripting and general command line task scripting. It has a rich library of built in functionality, it's fast and you can get a lot of function for very little code. Groovy is best when you really need Java integration or Database integration, which is much simpler using JDBC... or when you need to do something more advanced that, as a Java developer you know how to do in Java, but not in Ruby. Groovy is just an extension of Java, though a very powerful one.</blockquote>



