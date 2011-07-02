---
title: Interesting Way to Hide Behind Interfaces
layout: default
--- # Categories
- development
- java
- programming
- tidbits
---

While working on one of my projects I stumbled on an interesting way to code to interfaces even when the "factory" class is in a different package than the interface and implementation. Say you have an interface, <tt>IBar</tt> in a package called <tt>app.manager</tt>:

<code lang="java">
package app.manager;
    
public interface IBar {
    String getValue();
}</code>

and an implementation of the interface in the same package, called <tt>Bar</tt>:

<code lang="java">
package app.manager;
    
public class Bar implements IBar {
    
    private String value;
   
    protected Bar(final String value){
        this.value = value;
    }
    
    public String getValue() {return value;}
}</code>

Notice that the interface and the implementation are in the same package and that the constructor for <tt>Bar</tt> is <tt>protected</tt> so that instances cannot be simply constructed unless you extend the class... and that is exactly what we are going to do in the "client" class, <tt>Foo</tt>:

<code lang="java">
package app;
    
import app.manager.Bar;
import app.manager.IBar;
    
public class Foo {
    
    private IBar bar;
    
    public static void main(final String[] args){
        final Foo foo = new Foo();
        final IBar bar = foo.getBar();
        System.out.println("--> " + bar.getValue());
    }
    
    public IBar getBar(){
        if(bar == null) bar = new Bar("TheValue"){}; // <-- the magic
        return bar;
    }
}</code>

As you can see here, the <tt>Foo</tt> class is in a different package than the interface and its implementation. You can play an interesting little code trick and do an inline extension of <tt>Bar</tt> to implement the <tt>IBar</tt> interface to be stored for later use. Obviously, any client class could just do this in their code; however, it requires the extra step which should make you stop and think about what you are doing. Also in the documentation for the protected constructor in <tt>Bar</tt> you would want to document where valid instances can be retrieved and that by contract the class should never be directly instantiated anywhere. This allows you to code to interfaces while still maintaining useful package hierarchies.

This is not profound or anything but I thought it was an interesting little trick. The question now is whether or not it is bad design... I guess I will have to play with it for a while to find out.
