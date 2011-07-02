---
title: Generics and Comparison
layout: default
--- # Categories
- comparator
- generics
- java
- programming
---

A few years back, I wrote a post about how to use the <tt>Comparable</tt> and <tt>Comparator</tt> interfaces for comparing objects, "<a href="http://coffeaelectronica.com/blog/2004/10/two-if-by-comparison/">Two, If By Comparison</a>". While I was bringing the post over to this new site, I realized that it was written in the age long ago before Java generics. I decided to do a quick update to show how adding generics makes is a little easier.

Our first update will be the Comparable version of the Person class:

<code lang="java"> 
public class Person implements Comparable<Person> {
    private String id, firstName, lastName;
    private int age;

    // not showing all the getters and setters here...

    public int compareTo(Person person){
            // compare the last names using their compareTo methods
            return(lastName.compareTo(person.getLastName()));
    }
}</code>

Note the Person parameter in the <tt>Comparable</tt> interface and the Person parameter in the <tt>compareTo()</tt> method. It gets rid of a few lines and makes the code a little more clear.

The <tt>Comparator</tt>s become a bit more straight-forward as well:

<code lang="java">
public class LastNameComparator implements Comparator<Person> {

    public boolean equals(Object obj){
            // we're just going to say that any LastNameComparators are equal
            return(obj instanceof LastNameComparator);
    }

    public int compare(Person p1, Person p2){
            // compare their lastNames
            return(p1.getLastName().compareTo(p2.getLastName()));
    }
}

public class AgeComparator implements Comparator<Person> {

    public boolean equals(Object obj){
            // we're just going to say that any AgeComparators are equal
            return(obj instanceof AgeComparator);
    }

    public int compare(Person p1, Person p2){
            // compare their ages
            int result = 0; // defaults to equal
            if(p1.getAge() > p2.getAge()){
                    result = 1;
            } else if(p1.getAge() < p2.getAge()){
                    result = -1;
            }

            return(result);
    }
}</code>

Okay, so it's nothing really astounding or Earth-shattering, but I felt the need to give a little update.
