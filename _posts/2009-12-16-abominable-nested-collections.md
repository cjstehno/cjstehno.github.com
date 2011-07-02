---
title: Abominable Nested Collections
layout: default
--- # Categories
- development
- java
- programming
- thoughts
- tidbits
---

The dreaded nested collection, one of the most vile creatures in the lazy-development arsenal. What's wrong with nested collections? Nothing, if they are required to solve a problem... everything if they are used as the lazy-man's domain object or data transfer object. Consider this case where you have AddressBeans being mapped to their zip code and then again to their state:

<code lang="java">Map<String,Map<String,AddressBean>> map = getAddresses();</code>

or worse yet, back in the pre-Java 5 days:

<code lang="java">Map map = getAddresses();</code>

How meaningless is that? In the second case you will probably stumble over that Map a few times before you even figure out what's in it, or you will have to trace down into the code and see what's being put into it.

Code should be straight-forward and expressive. When you find yourself writing a Collection of Collections (or arrays or maps), step back and look at the larger picture. Do you really need such a complex construct? If so, does it really need to be so general that the caller must figure out it's meaning?

Generally the answer is no. This example could easily be replaced with a wrapper object, say <tt>AddressGroup</tt> that may internally still use the nested collection, or some other storage mechanism that the caller really does not care about as long as the data is accessible. This wrapper object would also provided a richer less error-prone means of accessing the data.

<code lang="java">
public class AddressGroup {

    private Map<String,Map<String,AddressBean>> addresses;
    
    public AddressBean[] getAddressesForState(String state){
        // return the addresses for the selected state
    }
    
    public AddressBean[] getAddressesForZip(String zip){
        // return the addresses for the selected zip
    }
    
    Iterator<AddressBean> addresses(){
        // return an iterator over all addresses
    }
    
    // other accessors as needed
}
</code>

With this wrapper object, we now have a much more meaningful statement:

<code lang="java">AddressGroup addressGroup = getAddresses();</code>

The point here is that there is nothing stopping you from writing your own "collection objects" that can be used to abstract away some of the nastiness of complex raw data structures. Doing this reduces the complexity of your code while at the same time making it easier to read and understand.
