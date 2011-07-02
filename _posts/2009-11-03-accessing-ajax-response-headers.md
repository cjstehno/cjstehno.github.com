---
title: Accessing Ajax Response Headers
layout: default
--- # Categories
- ajax
- javascript
- jquery
- programming
- prototype
- tidbits
---

One way to handle error responses with Ajax requests is to add an HTTP Response Header to the response, denoting the resulting command status, while still returning content or data related to the error in the response. Generally when using Ajax calls you don't want a raw exception or server error to bubble up to your JavaScript handlers.

Let's say we have a simple quote service at "blurb.jsp":

<code lang="html">
<%@page import="java.util.Random"%>
<%!
	private static final Random rng = new Random();
	private static final String[] quotes = {
		"I regret that I have but one life to live for my country.",
		"To be or not to be, that is the question...",
		"I drank what?",
		"I am what I am and that's all I am"
	};
%>
<%
	try {
        // WARNING: intentional random error below
		final String quote = quotes[rng.nextInt(quotes.length+1)];
		
		response.setContentType("text/plain");
		response.setHeader("X-Status","OK");
		out.write(quote);
		
	} catch(Exception e){
		response.setHeader("X-Status","ERR");
		out.write("Something bad has happened: " + e.getMessage());
	} finally {
		out.close();
	}
%>
</code>

All this does is setup some simple quote strings and randomly provide one of them as a text response. I have added an error condition by allowing the random number generated to exceed the maximum index of the array so that we can get a random error response. In both cases, success or failure, the response header "X-Status" is given a value of "OK" or "ERR" respectively to denote the type of response being sent.

Handling this response with <a href="http://prototypejs.com">Prototype</a> is pretty simple:

<code lang="html">
<html> 
    <head> 
        <script type="text/javascript" 
            src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.1.0/prototype.js"></script>
        <script type="text/javascript">
            Event.observe(window,'load',function(){
                $('quote').observe('click',handleClick);
            });
            
            function handleClick(evt){
				new Ajax.Request('blurb.jsp', {
					onSuccess: function(response) {
						var quoteElt = $('quote');
						if( 'ERR' == response.getHeader('X-G-Status') ){
							quoteElt.setStyle({color:'red'});
						} else {
							quoteElt.setStyle({color:'black'});
						}
						quoteElt.update(response.responseText);
					}
				});
            }
        </script>
    </head>
    <body>
    
        <blockquote id="quote" style="border:1px dashed green;padding:4px;">Click me for a quote...</blockquote>
    
    </body>
</html>
</code>

Basically, in your "onSuccess" handler function you just pull the header value out of the response object. You could conceivably do any kind of response handling you wanted for an "error" response. In the little demo, you will get a quote from the server each time you click on the quote box; successful quotes will be black, while errors will be in red text.

For the heck of it, I decided to try the same thing in <a href="http://jquery.com">JQuery</a> (without making a whole separate post about it). I am just including the script sections, since they are the only difference:

<code lang="html">
<script type="text/javascript" 
    src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript">
    jQuery(function(){
        $('#quote').bind('click',handleClick);
    });
    
    function handleClick(evt){
        var jqr = jQuery.get(
            'blurb.jsp', 
            function(text){
                var quoteElt = $('#quote');
                if( 'ERR' == jqr.getResponseHeader('X-Status') ){
                    quoteElt.css('color','red');
                } else {
                    quoteElt.css('color','black');
                }
                quoteElt.html(text);
            },
            'text'
        );
    }
</script>
</code>

Surprisingly enough, this was a bit more difficult (or convoluted) to do with JQuery since JQuery does not seem to provide any wrapper access to the response itself. You have to use the XMLHTTPRequest object, which works fine but as you can see in the Ajax callback it leads to some interesting code; the <tt>jqr</tt> varible contains the request object and is used inside the callback function. It just feels a lot less clean. 

Maybe I am not clear on how to use the JQuery Ajax support yet and I am missing something simple... or the developers of JQuery decided that since the times you actually need the response object itself are pretty limited, they could get away without providing direct access. Ultimately though, the support is there and that's what's really important.

