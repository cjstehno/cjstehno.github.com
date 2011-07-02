---
title: Finding Duplicate Ints: Clojure
layout: default
--- # Categories
- clojure
- development
- programming
- thoughts
- tidbits
---

Finding Duplicate Ints: Clojure

I return to my common interview question (see <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Interview Question: Find 2 Matching Ints</a>) with a solution based on <a href="http://clojure.org">Clojure</a>.

I worked on the solution to this problem in a few steps, which I will share. First, I assumed that the list of numbers has been sorted:

<code>
(defn find-dupint [intseq]
    (loop [result nil its intseq]
        (if (= result (peek its))
            result
            (recur (peek its) (pop its))
        )
    )
)
</code>

This function simmply iterates over the list of numbers and returns the first number whose value matches the next number in the sequence. This code will fail if the numers are not in order and the problem definition says that they can be in any order, so I worked up the following code to sort the numbers before looping through them:

<code>
(defn find-dupint [intseq]
	(let [sorint (sort intseq)]
        (loop [result nil its sorint]
            (if (= result (first its))
                result
                (recur (first its) (rest its))
            )
        )
    )
)
</code>

Notice that I changed <tt>(peek coll)</tt> and <tt>(pop coll)</tt> to <tt>(first coll)</tt> and <tt>(rest coll)</tt>, which work on sequences rather than stacks. I kept getting a <tt>ClassCastException</tt> the other way.

Now all that's left is to have an exception thrown when no duplicated number is found. The function as written returns a <tt>nil</tt>, which is ok for Clojure I guess but not really the same behavior as the other versions of this code as it is defined, so I dipped into the Java interoperability features to add some exception throwing for the finished product:

<code>
(defn find-dupint [intseq]
	(let [sorint (sort intseq)]
        (loop [result nil its sorint]
            (if (= result (first its))
                (if (nil? result)
                    (throw (new IllegalArgumentException "No duplicate found!"))
                    result
                )
                (recur (first its) (rest its))
            )
        )
  )
)
</code>

Note: the <tt>IllegalArgumentException</tt> does not need to be fully qualified because the <tt>java.lang</tt> package is imported by default, just as it is in Java.

The output from using this function is shown below:

<pre>
1:1 user=> (find-dupint [1 2 3])
java.lang.IllegalArgumentException: No duplicate found! (repl-1:1)

1:2 user=> (find-dupint [1 2 3 2])
2
</pre>

An intesting side-effect of this version is that you are not constrained to use ints, or even numbers for that matter. You could actually use any <tt>Comparable</tt> object. (It would not be that difficult to add the ability to really generalize the function by passing in a comparator)

A few other comparable objects are tested below:

<pre>
1:1 user=> (find-dupint ["a" "c" "b" "a"])
"a"

1:2 user=> (find-dupint [1.1 1.4 1.3 1.22 1.1])
1.1
</pre>

I guess I should rename the function "find-dup" to reflect the broader scope than just integers.

At this point, reviewing the code, it's not all that less verbose than the Java version, but it gets the job done. I will have to come back to this problem again as I get more experience with Clojure... I am willing to bet there is a more concise way to achieve the same results.

