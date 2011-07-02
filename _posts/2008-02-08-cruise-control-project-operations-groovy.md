---
title: Cruise Control Project Operations (Groovy)
layout: default
--- # Categories
- groovy
- programming
- tidbits
---

Yesterday I posted a little <a href="http://ruby-lang.org">Ruby</a> script for performing [CruiseControl](http://cruisecontrol.sourceforge.net) operations (see <a href="http://coffeaelectronica.com/blog/2008/02/cruisecontrol-project-operations/">Cruise Control Project Operations</a>); I got to thinking last night that this would also be quite easy to do in <a href="http://groovy.codehaus.org">Groovy</a>... and it was:

<code>
package cc;
    
class CruiseExec {
    private static cruiseUrl = 'http://builder:8000';
    private static projects = ['nightly-build','releases','site-build']
        
    static void main(args) {
        def operation = null;
        if(args.length == 0 || args[0] == null){
            System.out.println("No operation specified!")
            System.exit(0)
        } else {
            operation = args[0]
        }
    
        if(args.length == 2 && args[1] != null) projects = [args[1]]
     
        projects.each { project ->
            def url = new URL("${cruiseUrl}/invoke?operation=${operation}&objectname=CruiseControl+Project%3Aname%3D${project}")
            def success = url.getText().contains('Invocation successful')
            System.out.println("${project}.${operation}: ${success}")
        }
    }
}	
</code>

It was very easy, easier than Ruby in fact since I work primarily in Java so I did not have to go looking up odd syntax questions as I did when writing the Ruby version. It is interesting to note that in this version I took the more Object oriented approach and wrote a class rather than a naked script. You could pull the meat out of the main method and make an even shorter script version if you are so inclined.

It's always interesting to compare the same functionality across different languages, so I thought I'd share. Also, it does perform the exact same functionality, so you are welcome to use this version as a replacement for the Ruby version.

Again, I have attached the source code, in case the browser cut-and-paste messes things up for you:

