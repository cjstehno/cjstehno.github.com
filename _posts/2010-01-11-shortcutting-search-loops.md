---
title: Shortcutting Search Loops
layout: default
--- # Categories
- development
- java
- programming
- tidbits
---

A simple mistake I have seen a lot is not breaking out of a search loop once you have found what you are searching for. Take the code below, for example:

<code lang="java">
public User findUser(String name){
    User foundUser = null;
    
    for( User user : product.getUsers() ){
        if( name.equals(user.getName()) ){
            foundUser = user;
        }
    }
    
    return foundUser;
}
</code>

This code will loop through <i>every</i> user even after you have found the user you are looking for, which for large sets of data can get very inefficient. You can shortcut the loop with a break statement:

<code lang="java">
public User findUser(String name){
    User foundUser = null;
    
    for( User user : product.getUsers() ){
        if( name.equals(user.getName()) ){
            foundUser = user;
            break;
        }
    }
    
    return foundUser;
}
</code>

which will drop the control out of the loop once your user has been found. You could also simply return the user when found, such as

<code lang="java">
public User findUser(String name){
    for( User user : product.getUsers() ){
        if( name.equals(user.getName()) ){
            return user;
        }
    }
    return null;
}
</code>

This version causes there to be two exit points in the method, which is often frowned upon. Personally, I don't see a problem with this in short simple methods.

Lastly, if you are a couple levels deep in a loop and cannot simply use a "return" for some reason, you can use a label to break out of the loop. Consider this example of a Map of Lists (something that you generally should not do):

<code lang="java">
User foundUser = null;
Iterator<List<User>> lists = users.values();
finder: for( List<User> list : users.values() ){
    for( User user : list){
        if( name.equals(user.getName()){
            foundUser = user;
            break finder;
        }
    }
}
</code>

which will break out of the loop labeled "finder" (the outer loop).

There are, of course, other ways of breaking out of loops, but these are the most general and cleanest, in my opinion.
