---
title: Interview Question: Find 2 Matching Ints
layout: default
--- # Categories
- interview
- java
- programming
- puzzle
---

Okay, here is a little question I have taken to asking in the interviews we have been giving lately. The questions is as follows:

<blockquote>Assume that you have an array of <tt>int</tt>s with exactly two of them being equivalent. Write a method to return the <tt>int</tt> that is duplicated.</blockquote>

Upon the first candidates failing this one for the most part, I have started compiling a catalog of all the possible and sensible solutions to the problem. It's kind of a fun little project. Below are some of the implementations I have come up with.

When I came across this question, it did not have an answer provided. My first shot at it was the following, but without the array sorting. Oops, without the sorting it works as long as they are next to each other, hence the need for sorting:

<code lang="java">
public int findMatching(final int[] array){
    Arrays.sort(array); 
    for(int i=0; i<array.length-1; i++){
        if(array[i] == array[i+1]) return array[i];
    }
    throw new IllegalArgumentException("Array contains no matches!");
}
</code>

The second implementation was something I had as a first thought but could not remember the exact functionality of the <tt>Set</tt> <tt>add(Object)</tt> method, which is kind of important in this case.

<code lang="java">
public int findMatching(final int[] array){
    final Set<Integer> set = new HashSet<Integer>(array.length);
    for(int i : array){
        if(!set.add(i)) return i;
    }
    throw new IllegalArgumentException("Array contains no matches!");
}</code>

The third implementation is the one most of our candidates seem to jump for, brute force, comparing every element with every other element (we still give credit for it though):

<code lang="java">
public int findMatching(final int[] array){
    for(int i=0; i<array.length; i++){
        for(int j=0; j<array.length; j++){
            if(i != j && array[i] == array[j]) return array[i];
        }
    }
    throw new IllegalArgumentException("Array contains no matches!");
}</code>

You will notice that I used an <tt>IllegalArgumentException</tt> to denote the lack of matches. You can't really return a -1 or something like that since your <tt>int</tt>s could be of any value.

I am sure that there are one or two more interesting solutions for this problem, but thought I would share what I have found. These are always fun little code problems to play with.

Yes, we are still using this in our interview process, but I am not afraid that a potential candidate will find this since they generally don't know my name, and likewise I don't advertise what company I work for. Actually, I would probably give a "golf clap" to the candidate that walks into our interview with a print out of this entry. Don't break yourself trying to find out... it's not worth it.

