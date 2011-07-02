---
title: ExtJS Application Development 3: Packaging
layout: default
--- # Categories
- development
- extjs
- javascript
- programming
---

Continuing on from "<a href="http://coffeaelectronica.com/blog/2010/11/extjs-application-development-2-setup/">ExtJS Application Development 2: Setup</a>"…

Packaging in JavaScript is not really a new thing, many libraries have implemented it in one form or another over the years. ExtJS does it very well using namespaces.

The first thing we need for packaging, is to define our namespaces. Namespaces isolate your scripts so that they do not interact with each other or with 3rd-party libraries in unexpected ways. As an example, say you have two developers on a large team working on two different components. One is working on the Message of the Day and another is working on an Instant Messenger functionality... but they both create a panel called MessagePanel. Oops! Namespaces allow each of these developers to work in their own package per se, such as “myco.mod” and “myco.im” respectively so that they can work without colliding.

For our little example we will create a namespace called “demo.mod”, and it’s simple to do. At the top of your application.js file add one line (outside the onReady function):

<code lang="JavaScript">Ext.namespace('demo','demo.mod');</code>

From the tutorial, <a href="http://www.sencha.com/learn/Tutorial:Application_Layout_for_Beginners">Application Layout for Beginners</a>, we have the basic application object layout, which sets up the basic application object. Some of this, we have already done, below is what will replace our onReady function call (wiithout our widget code for now):

<code lang="JavaScript">
demo.app = function() {
    // do NOT access DOM from here; elements don't exist yet
    // private variables
    // private functions
    // public space
    return {
        // public properties, e.g. strings to translate
 
        // public methods
        init: function() {
            alert('Application successfully initialized');
        }
    };
}();</code>

This code provides a common place to access application properties, objects and methods. In order to install and initialize this object you need to add a line to the index.html file after all other JavaScript imports:

<code lang="html"><script type="text/javascript">Ext.onReady(demo.app.init, demo.app);</script></code>

This will load the app object when the page loads, and call its init function to get all of our application object installed. Now, we need to start adding back the pieces of our Message of the Day dialog. Just paste the content from the old onReady function into the new init function and you will end up with the same functionality, just setup a little different:

<code lang="JavaScript">
init: function() {
	var messagePanel = new Ext.Panel({
	  autoLoad:'message.html',
		bodyStyle:'padding:10px;'
	});

	var closeButton = new Ext.Button({
		text:'Close',
		handler:function(){
			Ext.getCmp('mod-window').close();
		}
	});

	var modCheckbox = new Ext.form.Checkbox({
		boxLabel:'Always show on startup',
		checked:true
	});

	var win = new Ext.Window({
		id:'mod-window',
	  title:'Message of the Day',
	  draggable:false,
	  modal:true,
	  resizable:false,
	  width:400,
	  height:300,
		autoScroll:true,
	  items:[ messagePanel ],
		buttons:[ modCheckbox, ' ', closeButton ]
	});
	win.show();
}</code>

It seems like we are going in the wrong direction, at this point. We actually have more code and complexity than we started with; however, the app object creation and packaging setup really only needs to be done once per page of your application so it’s pretty low impact. Also, we still have some refactoring to do with the Message of the Day component to take advantage of the packaging system.

Converting the component instances into their own component classes will have to wait until the next installment. Packaging is fairly simple to use, but very useful. It is also helpful to break your JavaScript up into multiple files based around functional groups, packages or even single components, similar to the Java one-class-one-file idea.

<blockquote>Source code for this article can be found on GitHub at <a href="https://github.com/cjstehno/extjs-demo/tree/packaging">extjs-demo</a>. The work up through the end of this posting is tagged as “packaging”.</blockquote>

