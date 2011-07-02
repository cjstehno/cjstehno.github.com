---
title: Squeezing in Clojure
layout: default
--- # Categories
- clojure
- programming
- thoughts
---

I had originally decided to make <a href="http://www.scala-lang.org/">Scala</a> my Language of the year for 2009, but other things came to the forefront that kept me on other tasks, both personal and development-related so I have decided to try and squeeze in <a href="http://clojure.org">Clojure</a> before the year ends. A little light reading over lunch.

Clojure is pretty interesting so far. I found this example very interesting (from <a href="http://pragprog.com/titles/shcloj/programming-clojure">Programming Clojure by Stuart Halloway</a>)

<code lang="java">
public class StringUtils {
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}</code>

The <tt>isBlank()</tt> method checks to see whether a string is blank: either empty or consisting of only whitespace. Here is a similar implementation in Clojure:

<code>(defn blank? [s] (every? #(Character/isWhitespace %) s))</code>

if you know me, you know I like nice tight code... so this makes me happy. 

I also decided that since I have spent some time on the Android platform this year, which is very standard, though limited, Java, I figured it would be a nice bit of mental exercise to learn something so completely different. Clojure is a LISP style functional language, which as can be seen from the example above, is a bit of a stretch from Java. The nice thing to note is that Clojure is a JVM-language so it runs on the standard JVM and has access to any/all Java APIs.

I have noticed that I tend to favor JVM-based languages more than others... I guess I like to learn something new without straying too far from the power and flexability that I already have avaiable. I like to keep things practical so that if/when the need arises to use one of the alternate languages that I know, I will be functional with it.
