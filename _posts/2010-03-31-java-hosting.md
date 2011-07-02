---
title: Java Hosting?
layout: default
--- # Categories
- hosting
- java
- programming
- technology
- thoughts
---

I love Java. I love the language. I love the JVM and I love the ever-increasing list of languages that run on the JVM. One thing that I really don't like is the high cost of hosting Java web applications. Yes, if you search around you can find some that say they provide Java hosting via Tomcat or Jetty, but unless you get a dedicated server you are not really getting much out of it and there tend to be a lot of limitations involved.

I pay about $72 annually for hosting, and with that I get very limited support for hosting Java web applications. Basically I get a shared Tomcat instance that I cannot configure or restart myself, which is pretty useless beyond very simple applications. I don't even have java command line access to run my own server from my account. In order to get true, useful, Java hosting you need to bump up your monthly cost quite a bit. Real Java hosting generally starts around $300 per year and goes up from there if you really need to get serious with it (adequate RAM, and JVM configurations). Google App Engine is on the right track but it's limiting; you are limited to JPA or JDO and not even the full power of either of those along with other limitations. Where is the affordable yet functional Java web hosting for the developer or hobbyist with no real budget?

I then decided to look into some of the other web development languages and found that Ruby (on Rails) has a lot of the same issues with good inexpensive hosting. That struck me as odd since I would think RoR would have lower memory and system requirements. I also looked into Python and found that there seems to be very few options available for it. The big winner in the hosting support I guess should come as no surprise... PHP. PHP has come standard with every hosting package I have ever had or have ever really looked into... at no extra cost. I guess this is why it remains to be so popular.

I think I will have to dig deeper into PHP for some of my personal web projects. If not that, I may have to look at setting up my own server outside my firewall, which just brings up all kinds of other problems.

