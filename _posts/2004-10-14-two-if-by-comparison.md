---
title: Two, If By Comparison
layout: default
--- # Categories
- comparator
- java
- programming
---

There are two ways to compare objects, directly if they implement the <tt>java.lang.Comparable</tt> interface, and indirectly using an implementation of the <tt>java.util.Comparator</tt> interface.

<strong>java.lang.Comparable</strong>

The Comparable interface defines only a single method signature, the <tt>compareTo()</tt> method that takes as a parameter the object that the implementing object is being compared to. It returns a negative integer, zero, or a positive integer when the implementing object is less than, equal to, or greater than the specified object respectively.

The Comparable interface is best used when your object has a logical point of comparison, such as an order number, date stamp, or unique name and that point of comparison is fixed, meaning that the comparison is always performed on the same comparison point (e.g. always compared by the date stamp). This also assumes that you are the developer of the object or that you are able to extend it to allow for a Comparable implementation (if neither of these is the case, you should use the Comparator interface instead — see below).

First you need a class for your object. 

<code lang="java">
public class Person {
    private String id, firstName, lastName;
    private int age;
    public String getId(){return(id);}
    public String getFirstName(){return(firstName);}
    public String getLastName(){return(lastName);}
    public int getAge(){return(age);}
    public void setId(String id){this.id = id;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setAge(int age){this.age = age;}
}</code>

Now, say you have a list of people (Person objects) and that you want to display them ordered by their last names. You can make the Person class implement Comparable and perform the comparison based on the <tt>lastName</tt> property. This comparison is very easy due to the fact that Strings implement Comparable themselves, as do many standard data object classes (Numbers, Dates, etc.). The comparable Person class is shown below:

<code lang="java">
public class Person implements Comparable {
    private String id, firstName, lastName;
    private int age;

    public String getId(){return(id);}
    public String getFirstName(){return(firstName);}
    public String getLastName(){return(lastName);}
    public int getAge(){return(age);}
    public void setId(String id){this.id = id;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setAge(int age){this.age = age;}

    public int compareTo(Object obj){
            // cast the obj as a Person -- we are only comparing people
            Person pObj = (Person)obj;
            // compare the last names using their compareTo methods
            return(lastName.compareTo(pObj.getLastName()));
    }
}</code>

Then you can run your list of Person objects through a <tt>Collections.sort()</tt> method and you will have a list of people ordered by their last names. But what if, when you display your list of people, the user wants the ability to sort the list by the first name or the age? You could add a new property to your object called <tt>compareBy</tt> that takes a parameter used to identify which property of the object will be used in the comparison. Then in your <tt>compareTo()</tt> method you will need to base your comparison on the property that <tt>compareBy</tt> is pointing to. This is not a very clean approach. It would be better to use a <tt>java.util.Comparator</tt> implementation.

<strong>java.util.Comparator</strong>

The Comparator interface defines two method signatures for implementation, <tt>compare()</tt> that takes as parameters the two objects to be compared and <tt>equals()</tt> which takes an object to be compared to the Comparator. The return value of the <tt>compare()</tt> method is basically the same as that for the <tt>compareTo()</tt> method of the Comparable interface. The main difference between the Comparator and a Comparable object is that Comparators perform the comparison external to the objects being compared and therefore can be reusable over many different object types.

Let’s start out with our clean Person class again: 

<code lang="java">
public class Person {
    private String id, firstName, lastName;
    private int age;

    public String getId(){return(id);}
    public String getFirstName(){return(firstName);}
    public String getLastName(){return(lastName);}
    public int getAge(){return(age);}
    public void setId(String id){this.id = id;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setAge(int age){this.age = age;}
}</code>

and then define a comparator to do the work of the comparable Person we created (so we don’t lose any functionality).

<code lang="java">
public class LastNameComparator implements Comparator {

    public boolean equals(Object obj){
            // we're just going to say that any LastNameComparators are equal
            return(obj instanceof LastNameComparator);
    }

    public int compare(Object obj1, Object obj2){
            // cast both objects are Person
            Person p1 = (Person)obj1;
            Person p2 = (Person)obj2;

            // compare their lastNames
            return(p1.getLastName().compareTo(p2.getLastName()));
    }
}</code>

That’s all it takes. You can run your List of Person objects through the version of <tt>Collections.sort()</tt> that accepts a List and a Comparator to sort your list by last name. You have gained something by doing this… you can now change the sort criteria on the fly. Let’s create a Comparator to compare by age.

<code lang="java">
public class AgeComparator implements Comparator {

    public boolean equals(Object obj){
            // we're just going to say that any AgeComparators are equal
            return(obj instanceof AgeComparator);
    }

    public int compare(Object obj1, Object obj2){
            // cast both objects are Person
            Person p1 = (Person)obj1;
            Person p2 = (Person)obj2;

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

Now you can use that instead of the <tt>LastNameComparator</tt> to order the Person objects by their ages. The Comparator at first seems like more coding, but if you design your Comparators well, you will be able to reuse them in the future, especially if you throw in some reflection. The <a href="http://commons.apache.org/collections">Jakarta Commons Collections API</a> has a set of useful Comparators, though I think one of the most useful Comparators is found in the <a href="http://commons.apache.org/beanutils">Jakarta Commons Bean Utils API</a>, called the <tt>BeanComparator</tt>. The <tt>BeanComparator</tt> uses reflection compare two objects based on the value of a specified property. Using the <tt>BeanComparator</tt> to perform our comparisons would be much simpler:

<code lang="java">
Collections.sort(people,new BeanComparator("lastName"));
// - or -
Collections.sort(people,new BeanComparator("age"));
</code>

Now how is that for simple and straight forward?  

So that is a basic introduction to using the Comparable and Comparator interfaces. In general, it is better to use Comparators so that your comparison is not so tightly tied to your implementations. 

<strong>Additional Reading</strong>

<a href="http://www.javaworld.com/javaworld/jw-12-2002/jw-1227-sort_p.html">Sort it Out</a> (Alex Blewitt, <a href="http://javaworld.com">JavaWorld</a>, 2002) - This article takes a little more broad top-level approach, but has some good information too. 

