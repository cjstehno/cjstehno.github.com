---
title: Groovy Unit Testing: Tools
layout: default
--- # Categories
- groovy
- java
- programming
- testing
- tidbits
---

The <a href="http://javamug.org/">Java Metroplex User Group</a> meeting last night was a presentation on unit testing and mocking with <a href="http://groovy.codehaus.org">Groovy</a>, presented by <a href="http://www.agiledeveloper.com/blog">Venkat Subramaniam</a>, a well-known author and consultant... and a great presenter to watch, by the way. In his presentation he showed various techniques for using Groovy to write unit testing for both Java classes and other Groovy classes.

Count me among the converted, but I was not sure just how easy it would be to bring Groovy into a Java project while still maintaining good <a href="http://eclipse.org">Eclipse</a> and <a href="http://maven.apache.org">Maven</a> support. So, I did a quick little experiment and found out that the integration is trivial.

Say you have a maven project, we'll call it "groover", already hapily running maven under Java 6 and Maven 2. Add the following dependencies (assuming you dont already have JUnit 4):

<code lang="xml">
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.6</version>
    <type>jar</type>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy</artifactId>
    <version>1.7.0</version>
    <type>jar</type>
    <scope>test</scope>
</dependency>
</code>

You now have maven setup to run unit tests written in Groovy (assuming you have Groovy 1.7 installed locally as well). 

To get everything working in Eclipse, import your project (if it's not already in Eclipse) and be sure you have the Groovy Eclipse plug-in installed. You may also want/need the Maven Eclipse plug-in, but that is not a requirement. Once the project is imported, right click on the project name and convert it to a Groovy project. Now Eclipse is ready to go too.

Let's proove that it works by writing a simple little class and a Groovy test for it:

<code lang="java">
package foo;

public class Something {
	public String doIt(String in){
		return in.concat(" gabadata!");
	}
}
</code>

and then the test is:

<code>
package foo

import org.junit.Test
import static org.junit.Assert.*

class SomethingTest {
	@Test
	void doIt(){
		def result = new Something().doIt('foo')
		assertEquals "foo gabadata!", result
	}
}
</code>

Run the test using Ctrl+Alt+X T (normal JUnit run command in Eclipse) and it works just like any other JUnit test. Run the test in a console with maven with "mvn test" and it still works just like normal.

My next thought was that surely it would not work with my coverage tools! I was wrong, coverage worked fine in both the IDE and in maven.

So, by writing unit tests in Groovy I get to write less code, have built in powerful mocking support, and no tool integration issues? Sign me up!


