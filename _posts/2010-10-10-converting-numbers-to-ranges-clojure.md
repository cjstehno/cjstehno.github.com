---
title: Converting Numbers to Ranges: Clojure
layout: default
--- # Categories
- clojure
- programming
- puzzle
---

Ok, in a wild mood, I decided to go back and try some <a href="http://clojure.org">Clojure</a> again to work out another implementation of the number range problem. I think Clojure needed some time to sink into my brain, as it seemed to make more sense this time around. Working out the solution for this was not all that bad:

<code lang="clojure">
(defn rangify [items]
  (loop [its items holder nil outp ""]
    (if (empty? its)
      outp
      (if (= (+ (first its) 1) (first (rest its)) )
        (if (nil? holder)
          (recur (rest its) (first its) outp )
          (recur (rest its) holder outp )
        )
        (if (nil? holder)
          (recur (rest its) nil (print-str outp (first its) "," ) )
          (recur (rest its) nil (print-str outp holder "-" (first its) "," ) )
        )
      )
    )
  )
)
</code>

I am sure that there is still a lot that can be done to make this cleaner and more efficient. Even now it does not fully satisfy the criteria since the output is not quite right, as shown below:

<pre>
ranges=> (rangify [1 2 3 4 5 6 10])
" 1 - 6 , 10 ,"
</pre>

After a little more thought, I was able to move some of the function calls around and merge them into the recur calls to simplify and tighten the code down a bit so that we end up with:

<code lang="clojure">
(defn rangify [items]
  (loop [its items holder nil outp ""]
    (if (empty? its)
      outp
      (if (= (inc (first its)) (first (rest its)) )
        (recur (rest its) (if holder holder (first its) ) outp )
        (recur (rest its) nil
          (if holder
            (print-str outp holder "-" (first its) "," )
            (print-str outp (first its) "," )
          )
        )
      )
    )
  )
)
</code>

This, actually is a bit cleaner and easier to read, but it sure seems like an odd language. I decided to put out what I have and come back to finish it later. It was a fun exercise.
