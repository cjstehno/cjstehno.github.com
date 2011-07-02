---
title: Spring Inner-class Instantiation
layout: default
--- # Categories
- java
- programming
- spring
- tidbits
---

The other day I ran into something I had never tried to do with <a href="http://springframework.org">Spring</a> before; define a bean as an instance of an inner class. I did a little searching through the Spring docs, but could not find anything about it, negative or positive. So, I just gave it a try.

Consider the class: 

<code lang="java">
public abstract class IService {
    public static class ServiceImpl extends IService {
            // something useful...
    }
}</code>

which would have a bean definition of: 

<code lang="xml"><bean id="myService" class="com.some.pkg.IService$ServiceImpl" /></code>

where the $ is the separator between the main class and the inner class. This is how its represented in the actual class file so it makes sense. Damn, I love Spring!
