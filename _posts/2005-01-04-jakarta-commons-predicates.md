---
title: Jakarta Commons Collections - Predicates
layout: default
--- # Categories
- development
- java
- programming
---

Nestled in the <a href="http://commons.apache.org">Jakarta Commons</a> is a monstrosity called the <a href="http://commons.apache.org/collections">Jakarta Commons - Collections</a> API. It contains a wealth of extensions to the standard collections as well as new collections and collection-related utilities.

To try and document the use of the whole API would be a good topic for a book (and there are a few: <a href="http://www.amazon.com/s/ref=br_ss_hs/103-3887221-2235807?platform=gurupa&url=index%3Dblended&keywords=jakarta+commons&Go.x=0&Go.y=0&Go=Go">Amazon</a>). I am going to cover one of my favorite interfaces from this API, the Predicate, and its implementations. From the documentation for the Predicate interface:

<blockquote>A Predicate defines a functor interface implemented by classes that perform a predicate test on an object. Predicate instances can be used to implement queries or to do filtering.</blockquote>

That sums it up pretty well; but, how do you use it? 

<b>General Usage</b>

Let’s say we have an <tt>ArrayList</tt> containing ten <tt>Integer</tt> objects as follows:

<code lang="java">
List<Integer> numbers = new ArrayList<Integer>();
for(int i=0; i<10; i++){
        numbers.add(i);
}</code>

Now let’s say that for some reason only the even numbers in the list are relevant and that the rest can be ignored and/or removed. There are three main approaches to doing this with Predicates. First, you can select all even numbers from the list into a new <tt>Collection</tt>. Second, you can filter the list so that all non-even numbers are removed from the list. Third, you can create a predicated list that will only store even numbers.

Before we go any farther, we need a Predicate to work with. The Predicate interface is pretty simple, containing only a single method to implement, so I will just show it below:

<code lang="java">
public class EvenIntegerPredicate implements Predicate {
        public boolean evaluate(Object obj){
                boolean accept = false;
                if(obj instanceof Integer){
                        accept = ((Integer)obj).intValue() % 2 == 0;
                }
                return(accept);
        }
}</code>

The <tt>evaluate()</tt> method is called for each element to be tested. In this case, the object must be an Integer implementation and have an even value to be accepted.

<b>Select the Even Numbers</b>

This case uses the <tt>select(Collection,Predicate)</tt> method of the <tt>CollectionUtils</tt> class. This method selects all elements from input collection which match the given predicate into an output collection.

<code lang="java">
Predicate evenPred = new EvenIntegerPredicate();
Collection nums = CollectionUtils.select(numbers,evenPred);</code>

which will yield a new collection containing only the even numbers from the original list while the original list will remain unchanged. 

<b>Filter the Collection</b>

This next method is good when you are able to reuse the original collection once it is filtered. The <tt>CollectionUtils.filter(Collection,Predicate)</tt> method filters the collection by testing each element and removing any that the predicate rejects.

<code lang="java">CollectionUtils.filter(numbers,new EvenIntegerPredicate());</code>

Once again, only the even values are preserved; however, this time, the original collection is maintained. 

<b>Predicated List</b>

In the third approach, we use a method that allows new values to be added to the list and tested at the same time. This approach is best when you have control over the original collection and could possibly add new elements to the collection. For this we use the <tt>predicatedList(List,Predicate)</tt> method of the <tt>ListUtils</tt> class which returns a predicated list backed by the given list. Only values that are accepted by the predicate will be added to the list any other values will cause an <tt>IllegalArgumentException</tt> to be thrown.

<code lang="java">
List<Integer> list = new ArrayList<Integer>();
Predicate evenPred = new EvenIntegerPredicate();
List predList = ListUtils.predicatedList(list,evenPred);
predList.add(new Integer(2));
predList.add(new Integer(4));
predList.add(new Integer(6));
predList.add(new Integer(8));
predList.add(new Integer(10));

// this next one will throw an IllegalArgumentException
predList.add(new Integer(11));</code>

The resulting list will contain only the even values (you should be sure to use the predicated list (predList) not the original backing list.

<b>Combining Predicates</b>

I will take this discussion one step farther and pose the question, “what if you only want even integers greater than 5?" 
Your first thought might be to re-write the <tt>EvenIntegerPredicate</tt> to handle this, but a better approach would be to write a new predicate that only accepts values greater than a specified value.

<code lang="java">
public class GreaterThanPredicate implements Predicate {
        private int value;

        public GreaterThanPredicate(int value){
                this.value = value;
        }

        public boolean evaluate(Object obj){
                boolean accept = false;
                if(obj instanceof Integer){
                        accept = ((Integer)obj).intValue() > value;
                }
                return(accept);
        }
}</code>

Now we have a predicate that matches even numbers and a predicate that matches numbers greater than a specified number… how do we combine them? 

Two Predicate implementations jump to mind, <tt>AllPredicate</tt> and <tt>AndPredicate</tt>. The <tt>AllPredicate</tt> is built with an array of Predicates that must all evaluate to true for the containing predicate to be true. The <tt>AndPredicate</tt>, which we will use here, takes two predicates as arguments and returns true if both evaluate to true.

<code lang="java">
Predicate evenInt = new EvenIntegerPredicate();
Predicate greater = new GreaterThanPredicate(5);
Predicate andPred = new AndPredicate(evenInt,greater);
</code>

which could be used in any of the previous examples to accept only even numbers greater than 5. 

<b>Conclusion</b>

Predicates are a powerful tool for object filtering and searching. They are fairly simple to learn and if written properly, very reusable. At first they may feel a bit like excess code, but once you find yourself using the same predicate in multiple projects, you will see the benefits.  
