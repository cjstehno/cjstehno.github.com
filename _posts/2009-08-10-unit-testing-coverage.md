---
title: Unit Testing: Coverage
layout: default
--- # Categories
- coverage
- development
- java
- programming
- testing
---

I had originally intended to cover test coverage in my previous post <a href="http://coffeaelectronica.com/blog/2009/08/unit-testing-and-you/">Unit Testing and You</a>; however, it seems that Wordpress has some issues with long posts. So, I broke this out into a separate post.

[caption id="attachment_205" align="alignleft" width="150" caption="Coverage View in Eclipse"]<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-coverage.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/08/testing-coverage-150x150.png" alt="Coverage View in Eclipse" title="testing-coverage" width="150" height="150" class="size-thumbnail wp-image-205" /></a>[/caption] Another key, but often overlooked, part of unit testing is test coverage. Test coverage tools use byte code instrumentation to measure how much of your code is "exercised" during your unit tests. This can make overlooked, normally hidden, paths through your code appear obvious so that you can write tests to cover those cases as well.

The image shows the <a href="http://www.eclemma.org/">EclEmma</a> plug-in coverage results view in Eclipse. You can see that it shows how much your unit tests have "covered" each method of the class. This display is cumulative so that if you run all of your unit tests it will show you what has not been covered by any of them. It will also highlight the source in the editors with red, yellow or green based on the coverage.

If unit testing let's you know whether or not your code works, test coverage lets you know when you are done testing. Now, coverage is not the be-all end-all statistic of how good your unit tests are, but it is a good baseline starting point, and it is something measurable that you can put a pin in.

Coverage has become a bit of a game to me, as I tend to accept anything over 90% as being acceptable unless there is something glaringly easy and untested; however, obtaining that last 10% can be quite an exercise. Full 100% coverage can sometimes be unobtainable due to they way certain code constructs are measured, but like I said... 90% and above is really an obtainable goal. You can decide with your peers and coworkers what your coverage goal should be on your project. As a rule of thumb, if you write a unit test at all you probably have at least 75% coverage without even trying.

There are a handful of tools available for performing coverage evaluation: <a href="http://www.atlassian.com/software/clover/">Clover</a>, <a href="http://emma.sourceforge.net/">Emma</a>, and <a href="http://cobertura.sourceforge.net/">Cobertura</a>. I am sure there are others, that's just what came off the top of my head. 

I recommend using the same coverage tool for both your IDE and your build tools, though you may want to use more than one in each. Keeping a common set of tools let's you better predict how things will work when building in one tool or the other. I tend to use Emma, because it has a good Eclipse plug-in as well as a good maven report plug-in.
