---
title: Mocking Objects With Non-Empty Constructors
layout: default
--- # Categories
- java
- jmock
- mocking
- programming
- tidbits
---

While writing mock objects with <a href="http://jmock.org">JMock</a>, I have run into a reoccurring issue, you cannot mock concrete classes that do not have an empty constructor (this has been addressed by the JMock development team; however, the specific code line has not yet been released). Sometimes, for one reason or another, you have neither an interface nor empty constructor to mock an object with. The way JMock creates its proxied mock objects is �on creation", meaning that when you create the mock, the proxy object is created and stored, thereby not allowing you any way to specify constructor arguments.

With a little extension to the mocking API you can still mock those classes. The solution is a simple extension of the <a href="http://cglib.sourceforge.net">CGLIB</a>-based functionality that is already there such that the proxy is created only when the <tt>proxy()</tt> method is called. The code of the two classes needed is shown below:

<code lang="java">
public class CGLIBCoreLazyMock extends AbstractDynamicMock implements MethodInterceptor {

        private Enhancer enhancer;
        private Class[] argTypes;
        private Object[] args;
        private Object proxy;

        public CGLIBCoreLazyMock(Class mockedType,Class[] argTypes,Object[] args){
                super(
                        mockedType,
                        mockNameFromClass(mockedType),
                        new LIFOInvocationDispatcher()
                );
                this.argTypes = argTypes;
                this.args = args;
                this.enhancer = new Enhancer();
                enhancer.setSuperclass(mockedType);
                enhancer.setCallback(this);
        }

        public Object proxy() {
                if(proxy == null){
                        this.proxy = enhancer.create(argTypes,args);
                }
                return(proxy);
        }

        public Object intercept(Object thisProxy, Method method,
                Object[] args, MethodProxy superProxy ) throws Throwable {
                return mockInvocation(new Invocation(proxy,method,args));
        }
}</code>

and then a Mock extension. 

<code lang="java">
public class LazyMock extends org.jmock.Mock {
        public LazyMock(Class mockedType,Class[] argTypes,Object[] args){
                super(new CGLIBCoreLazyMock(mockedType,argTypes,args));
        }
}</code>

By adding this functionality to JMock, you can mock these classes and still use all of the stub and expectation features that JMock provides. My first resolution to this problem was a quick custom hack using CGLIB. As it threatened to get more complex, I took a peek at the JMock source and found that it would not be hard to implement.

I guess I could have gotten the source and added it directly to their code base, and I even looked at doing that right off, but their project was Ant-based and seemed to be missing some of the pieces required for the build. I just made a little extension jar and it seems to work well enough.

<blockquote><b>Update:</b> <a href="http://jmock.org">JMock 2</a> fixes this issue internally and has a lot of other improvements. See <a href="http://coffeaelectronica.com/blog/2007/09/are-you-still-mocking-me/">Are You Still Mocking Me?</a> for updated information.</blockquote>
