---
title: Recursive Solution to Finding Ints Question
layout: default
--- # Categories
- java
- programming
- puzzle
- tidbits
---

Back in my posting of <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Interview Question: Find 2 Matching Ints</a> I showed a few different solutions to the problem. A candidate we had recently suggested solving it via recursion; I decided to whip up a little recursive solution for my collection:

<code lang="java">
public static int find(int[] array){
    return scan(new HashSet<Integer>(),array,0);
}

private static int scan(Set<Integer> values, int[] array, int idx){
    if(idx == array.length){
        throw new IllegalArgumentException("No match exists");
    } else if(!values.add(array[idx])){
        return array[idx];
    }
    return scan(values,array,++idx);
}
</code>

Note that this solution requires an additional method to perform the recursion but there are no loops. An alternate version removes the <tt>Set</tt> and uses pre-sorting of the array.

<code lang="java">
public static int find(int[] array){
    Arrays.sort(array);
    return scan(array,0);
}

public static int scan(int[] array, int idx){
    if(idx == array.length){
        throw new IllegalArgumentException("No match exists");
    } else if(array[idx] == array[idx+1]){
        return array[idx];
    }
    return scan(array,++idx);
}
</code>

This problem has really become an interesting study; as I use it as one of our tests for interview candidates I really find it an interesting ruler to compare how various developers think. Two interesting common threads are that most developers find the brute-force approach (double for loop) which is good but very telling, the other is that when faced with the idea that it is possible an array may be passed in without a match, they struggle on what to do at that point. The first solution people look at is some signal like a -1 or <tt>null</tt>, neither of which works. After hinting they will come across the idea of the exception but usually want to create their own unique exception for this method.

I think it would also be an interesting exercise to implement this problem in other languages such as Groovy, Ruby or Scala.

