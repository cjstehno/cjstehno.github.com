---
title: Too Groovy
layout: default
--- # Categories
- development
- groovy
- programming
- thoughts
---

I have been doing a lot of Groovy development for almost a year now and after seeing a lot of my own code as well as others, I have realized that we (myself and the people I work with at least) have been fighting ourselves with our own code. 

Let's start with a common downside of Groovy... lack of tool support. This is both a consequence of the language and in some cases a slow-moving IDE community. Add on top of that less-experienced Groovy developers who tend to embrace the language to an (sometimes overly) high degree. When you have code like this:

<code lang='Groovy'>
class Foo {
	def name
	
	def sayHello = { n->
		println "Hello, $n"
	}
}
</code>

Ultimately, there is nothing wrong with this code. It's beautifully concise Groovy. Now consider this in a Grails service class or something with a lot more functionality around it and a lot more going on. Your IDE becomes a fancy and/or expensive text editor.

After working like this for a while, I came to the realization that Groovy doesn't have to be as far from Java as possible. We should actually take the best of both worlds and reap the most benefit. If you write the previous code as:

<code lang='Groovy'>
class Foo {
	String name
	
	void sayHello( String n ){
		println "Hello, $n"
	}
}
</code>

With a few changes, we now get some IDE support since you have given it more actual data to work with. We have also made it easier for other developers to understand the intentions of the methods and paramters. 

I remember being overjoyed when Java 5 added typed collections so that you could stop guessing what was being stored in them. Now, we have put ourselves right back into that situration, and even worse... now, you can even lose return types, parameter types and variable types. It's the hammer and nail scenario again. Just because Groovy lets you do everything type-less, doesn't mean you have to develop that way.

The following code, shows how you can have the Groovy goodness without losing the solid Java foundation:

<code lang="Groovy">
private void catchAndLog( String msg, op ){
	try {
		op()
	} catch( e ){
		log.error msg, e
	}
}
</code>

Use the features that Groovy provides, but keep the good parts of Java that make your IDE and other developers more useful. I am compiling a set of my own personal practices to aid in achieving that end. I will post them when I have a good set together.