---
title: Spooky Java Code
layout: default
--- # Categories
- java
- programming
- tidbits
- wtf
---

Okay, file this under, "Holy crap!", because I just had an odd "I wonder if..." thought last night
that turned into something both interesting and frightening at the same time. I will say this in 
it's own little text box to bring home the oddness:

<blockquote>In Java you can name a member with dollar sign ($) or underscore (_) alone.</blockquote>
    
You might want to read that again so that it sinks in. This means that you can have the following 
code that will compile and run along happily with no errors.

<code lang="java">
public class Odd {

    private static final String $ = "Dollar-sign: ";
    private static final String _ = "Underscore: ";

    public static void $(final String name){
        System.out.println($ + name);
    }

    public static void _(final String name){
        System.out.println(_ + name);
    }
}</code>

Now, the underscore is not all that far out. I knew it could be used __in__ member names, but not
__as__ the member name by itself. The dollar sign just blew my mind.

What I need to find out is whether this is "to spec" or if it's just some oddity of the Mac JVM. I will need to give this a try on my Windows box at home.

Now, in no way do I want to promote the use of these as method or variable names as they lead
to functionally obfuscated code. They should probably never be used, but it seemed like an 
appropriate pre-Halloween posting. 
