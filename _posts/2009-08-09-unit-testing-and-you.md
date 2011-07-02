---
title: Unit Testing and You
layout: default
--- # Categories
- development
- java
- programming
- testing
---

<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/green-bar.png" alt="green-bar" title="green-bar" width="208" height="124" class="alignleft size-full wp-image-188" />Unit testing seems to be one of the more misunderstood development practices. It tends to be viewed as adding too much development time or maintenance overhead, when in the long run the reality is that you save development time and maintenance overhead by taking care of potential bugs before they happen at the time when you are most engaged in the code, rather than weeks or months later when you may have forgotten half what was done or are looking through someone else's code.

The devoted unit testers often talk about being "test infected", and it really does happen. As unit testing becomes more second nature to you and just part of your development cycle, you will feel a loss or uneasy about the times when you cannot write tests for the code you are developing (generally a case when starting at a new company that has not yet embraced unit testing). You feel wrong about not testing and that is a good thing.

In my opinion, there is no excuse for not having at least some unit testing of your code. Ideally, the more coverage the better; however, some is better than none. You don't have to get into "Test Driven Development" if you don't want to... it does not appeal to every one, just learn to do "Test-centric Development", which is a phrase we coined at a company I used to work for. It means development with good unit testing, though not necessarily done before the code.

So, before I get too far off topic, let's go into the "Why", the "What" and the "Where" of unit testing, and then I will talk about the basic tools you need to start testing. 

<strong>Why Write Unit Tests</strong>

Why should you write unit tests? It's a question that I have heard many times. The best answer I have heard is 

<blockquote>"how do you know your code works, if you don't test it?"</blockquote>

... and no, QA testing does not count because I have yet to come across a QA department that will regression test an entire application every time you make a change to the code, they may exists, but not in any company I have ever heard of. Unit testing your code provides a first line of defense against bugs and proof that your code does what it should. In writing testing you are also using your code in a similar way that another developer will, meaning that your unit tests can serve as a 100% accurate usage example and source of functional documentation. How often do your JavaDoc comments actually reflect the behavior of the code? Unit test "documentation" will never be out of sync with the code; it can't be, if it's not it will fail and you will have to fix it.

Unit testing also provides a level of confidence around making changes. If you have ever had to change a significant amount of code with and without unit tests, you will agree that you feel much more confident when you have good unit test coverage to prove that your changes did not break anything unexpectedly.

The last point as to why we write unit test is that you can then have them runnable on command and/or automated by the build process, before check-ins, etc. so that you know you are building your artifacts or checking into source control a clean set of code... again part of the confidence.

<strong>What do I Unit Test?</strong>

Ideally, unit testing should be all-pervasive; every path through every method of every class (not interfaces) should be tested through various scenarios including normal values, boundary conditions and exceptions. Unfortunately, this ideal case is not always practical. So it boils down to "do the best you can".

Most of us are not lucky enough to be starting a project from scratch. When you come into an untested project a month or a year into it's development you need to be realistic and draw a "line in the sand" such that from this point forward we will unit test our code to the best of our abilities. The older "legacy" code can be picked at and tested as feature requests and bugs cause you to delve into those untested waters.

<strong>Where do Unit Tests Go?</strong>

[caption id="attachment_192" align="alignleft" width="300" caption="Separation of production source and test source"]<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-separation.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-separation-300x213.png" alt="Separation of production source and test source" title="testing-separation" width="300" height="213" class="size-medium wp-image-192" /></a>[/caption] Unit test source code should reside in a separate directory structure from the "production" source code, and should also be compiled into a separate class directory structure; however, the package structure in the test source and production source directories should be the same.

Generally you should not release test source or compiled test code into a production environment, or in most cases even a testing/QA environment (unless you have specific test cases that are run in QA only). The separation of test source and classes from the production source and classes makes this filtering easier. The world will not end if you do allow your testing code into production; it is really more a matter of limiting the bulk of your production artifacts.

This separation does not in any way hinder your ability to have your test classes access the classes they are trying to test. Everything is still on the same classpath. In your IDE you would just mount both as source directories and everything would work seamlessly. Some IDEs do provide additional support for defining test directories in a slightly different way than normal source directories, but you will have to look into that on your own.

<strong>Running Unit Tests</strong>

There are a variety of ways to run your unit tests. You can run tests using the JUnit test runner from the command line (from <a href="http://junit.org">JUnit</a>):

<pre>java org.junit.runner.JUnitCore TestClass1.class [...other test classes...]</pre>

If you are using <a href="http://ant.apache.org/">Ant</a>, you can add a target to run your tests:

<code lang="xml">
<target name="test" depends="compile">
    <junit printsummary="yes" haltonfailure="yes">
        <classpath refid="test.runtime.classpath" />
        <formatter type="plain" />
        <batchtest fork="yes" todir="${test.report.dir}">
            <fileset dir="${test.build.dir}" includes="**/*Test.class" />
        </batchtest>
    </junit>
</target>
</code>

Once that is in place you can run your tests with:

<pre>ant test</pre>

You can use <a href="http://maven.apache.org">Maven</a>, which will "strongly suggest" where you should put your production and test source code, following the convention over configuration approach to development. Once your tests are in place or you have reconfigured Maven to your project setup you can run:

<pre>mvn test</pre>

which will generate a simple console-based test report.

<pre>
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running net.sourceforge.codeperks.io.zip.ZipBuilderTest
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.159 sec
Running net.sourceforge.codeperks.io.IoTemplateTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.01 sec

Results :

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
</pre>

Generally, you will probably be running your tests in your editor-of-choice, rather than the command line.

[caption id="attachment_196" align="alignleft" width="150" caption="Eclipse JUnit Runner Results"]<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-runineclipse.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-runineclipse-150x150.png" alt="Eclipse JUnit Runner Results" title="testing-runineclipse" width="150" height="150" class="size-thumbnail wp-image-196" /></a>[/caption] Or you can run JUnit tests from pretty much any IDE on the market. The image is an example of the <a href="http://eclipse.org">Eclipse</a> JUnit test runner and its results. As you can see it shows you which tests passed and which tests failed, if any. It also shows you how long each took to run.

You also see the "green bar" that you will hear avid testers talk about. If any of your tests do not pass, the green bar will be red to let you know that bad things have happened (hence the logo at the beginning of this article, "Embrace the Green Bar").

That should be enough project setup and general testing talk, how about we set up some good habits now with a healthy dose of "best practices" for unit testing.

<strong>Best Practices</strong>

When you write unit tests, you should treat them like real code. Pull out common code into shared classes, refactor repeated functionality into methods just like you would with the code you are testing. Also, you should use expressive names for your tests; the test case class should be named the same as the class you are testing with "Test" added to the end and your actual test methods should be named with underscores rather than the normal camel case notation (this is one of those opinion items, but the underscores do read better in test reports). As an example:

<code lang="java">public void handleRequest_with_null_userId(){}</code>

reads a bit more easily, in my opinion, than

<code lang="java">public void handleRequestWithNullUserId(){}</code>

Either is fine and technically correct, though some developers prefer one over the other.

One very important concept in good unit testing is the practice of coding to interfaces rather than implementations. Components such as services and DAOs should be interface-driven, meaning that you have an interface <tt>SomeDao</tt> that is implemented by <tt>SomeHibernateDao</tt> so that your code uses the interfaces rather than the actual implementation class. This promotes decoupling and allows for greater flexibility in testing as well as stronger unit test isolation.

The example below shows an API for some temperature sensing hardware. The <tt>TemperatureHardware</tt> interface has a standard implementation called <tt>DX39TemperatureHardware</tt> (made up) which access the hardware layer to get data from thermocouples regarding the temperature.

<code lang="java">
public interface TemperatureHardware {
    double getTemperature( short tcid );
}

public class DX39TemeratureHardware implements TemperatureHardware {
    public double getTemperature( short tcid ){
        // some fancy native hardware-specific stuff
    }
}
</code>

So now in testing this code, you can base your testing around the use of the interface because chances are you don't actually have the hardware itself. Coding to interfaces makes it much easier to integrate mock objects into your unit testing, and mock objects, you will eventually find to be an indispensable part of unit testing.

I have written quite a few posts about <a href="http://coffeaelectronica.com/blog/tag/mocking/">testing with mocks</a> but to summarize; mocking is the replacement of the complex (or even not so complex) dependencies of the object being tested with mocked out versions of those objects so that you can control their behavior and help isolate the test. There are a number of great mocking APIs availble, three of which are such as <a href="http://jmock.org">JMock</a> (<a href="http://coffeaelectronica.com/blog/tag/jmock/">related posts</a>), <a href="http://mockito.org">Mockito</a> (<a href="http://coffeaelectronica.com/blog/tag/mockito/">related posts</a>) and <a href="http://easymock.org">EasyMock</a> (<a href="http://coffeaelectronica.com/blog/tag/easymock/">related posts</a>).

I will leave the mocking examples to the referred postings as they go into the uses of mocking in greater detail than I would here.

<strong>Conclusion</strong>

<div style="float:right;padding:2px;"><iframe src="http://rcm.amazon.com/e/cm?lt1=_blank&bc1=000000&IS2=1&bg1=FFFFFF&fc1=000000&lc1=0000FF&t=coffeael-20&o=1&p=8&l=as1&m=amazon&f=ifr&asins=0974514012" style="width:120px;height:240px;" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"></iframe></div>

That about summarizes my thoughts on unit testing. Most of this has been gone over and over on various blogs, books, and presentations, but I felt it important enough to present it one more time. Unit testing often gets lumped into the same box as agile and extreme programming which can put a bad taste on it when some projects/teams are looking into testing. I have nothing against extreme programming, but I have run across organizations that have a real negative attitude towards it and see unit testing as just "another one of those fluffy feel good code-hippie things". It's really not. Embrace the green bar and join us on the testing side.

As a foot note I recommend reading "<a href="http://www.amazon.com/gp/product/0974514012?ie=UTF8&tag=coffeael-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=0974514012">Pragmatic Unit Testing in Java with JUnit</a><img src="http://www.assoc-amazon.com/e/ir?t=coffeael-20&l=as2&o=1&a=0974514012" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;" />" and the <a href="http://junit.org">JUnit</a> web site documentation for more information about testing with JUnit.

As a foot note to the foot note, I will also add that JUnit is not the only unit testing tool available for Java, there is another called <a href="http://testng.org">TestNG</a>, which I tried a few years back. It seemed good and some people swear by it; however, the tool support for it seemed to lag behind that for JUnit and I have always been comfortable with the features provided by JUnit, so I never saw a need to switch.

