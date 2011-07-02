---
title: Creating Spring Contexts Programmatically
layout: default
--- # Categories
- programming
- spring
---

If you are familiar with the <a href="http://springframework.org">Spring Framework</a> I am sure that you know how to create a context and fill it with beans using XML (DTD or Schema) or maybe even using properties files (have to try that one sometime), but have you ever tried creating a context programmatically... purely in Java? It's actually not all that difficult, just a little verbose.

The context itself is created as the <tt>GenericWebApplicationContext</tt>. Let's consider a scenario in which you have two beans, a Spring MVC <tt>Controller</tt> implementation called <tt>AddressController</tt>, and a service bean, called <tt>AddressService</tt>, such that the service bean must be injected into the controller bean.

First we create the context:

<code lang="java">GenericWebApplicationContext context = new GenericWebApplicationContext();</code>

Then we create the service bean and add it to the context:

<code lang="java">
    RootBeanDefinition addressSvcBean = new RootBeanDefinition(AddressService.class);
    context.registerBeanDefinition("addressService",addressSvcBean);
</code>

Not too bad. Now the interesting one, the controller will also need a reference to the service bean.

<code lang="java">
    RootBeanDefinition addressCtrBean = new RootBeanDefinition(AddressController.class);
    MutablePropertyValues props = new MutablePropertyValues();
    props.addPropertyValue("addressService",addressSvcBean);
    addressCtrBean.setPropertyValues(props);
    context.registerBeanDefinition("addressController",addressCtrBean);
</code>

Notice that you pass in the <tt>addressSvcBean</tt> object to the <tt>addressService</tt> property; this is the dependency injection in action. You now have a context with two beans, one of which depends on the other.

<b>Note:</b> that an actual controller implementation would probably require more properties to be specified but this is just to show you how it's done.

Finally you will need to startup the context:

<code lang="java">
    context.refresh();
    context.start();
</code>

Why would you ever want to do this over one of the less tedious methods? Maybe you just like doing things in code or you need a simple context with a few beans, some of which are mocked, and its just easier to handle in code. If nothing else it's nice to know that it can be done. I guess that it would also be useful if you had some other configuration format that you wanted to support... your "parser" would call/generate this code underneath.
