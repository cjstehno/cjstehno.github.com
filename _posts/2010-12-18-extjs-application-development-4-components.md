---
title: ExtJS Application Development 4: Components
layout: default
--- # Categories
- development
- extjs
- javascript
- programming
---

Finishing up from "<a href="http://coffeaelectronica.com/blog/2010/12/extjs-application-development-3-packaging/">ExtJS Application Development 3: Packaging</a>"...

On of the nicest features of ExtJS is, by far, its component and extension framework. It provides a rich set of available UI and data components and an unparalleled means of building on top of them. More information can be found by reading the tutorial: <a href="http://www.sencha.com/learn/Manual:Component:Extending_Ext_Components">Extending Ext Components</a> at Sencha's web site.

When we left off, we had a bunch of code specific to a single component, dropped right in the init function of our app. Let’s clean that up and take advantage of the packaging. Create a new JavaScript file called “demo.mod.ModDialog.js”. Be sure to add this new JS file to your index.html page just before the initialization script. Putting your components into different JavaScript files can really help to organize your project and help keep things reusable and manageable.

The ModDialog is just an extension of the Ext.Window object so in the new script file, let’s start out by defining it as such:

<code lang="JavaScript">
demo.mod.ModDialog = Ext.extend( Ext.Window, {
    id:'mod-window',
    title:'Message of the Day',
    draggable:false,
    modal:true,
    resizable:false,
    width:400,
    height:300,
    autoScroll:true
});</code>

The code in the app init function for the window instantiation becomes:

<code lang="JavaScript">
var win = new demo.mod.ModDialog({
  items:[ messagePanel ],
  buttons:[ modCheckbox, ' ', closeButton ]
});
win.show();</code>

Notice, that we changed the constructor to use the new component, while we left the items and buttons properties here because we will be handling these differently. This does show you a basic idea of how you can still override and configure your extended component in the same manner as its parent component.

There is a shortcut notation for defining instances of components in ExtJS, using JSON notation. For instance, we can represent our close buttons in a slightly simpler manner as:

<code lang="JavaScript">
var closeButton = {
	text:'Close',
	handler:function(){
		Ext.getCmp('mod-window').close();
	}
};</code>

Where this really becomes useful is over in the demo.mod.ModDialog.js file, where we can move our button config over using the JSON notation for the button and the checkbox as:

<code lang="JavaScript">
demo.mod.ModDialog = Ext.extend( Ext.Window, {
    id:'mod-window',
    title:'Message of the Day',
    draggable:false,
    modal:true,
    resizable:false,
    width:400,
    height:300,
    autoScroll:true,
    buttons:[
        {
            xtype:'checkbox',
            boxLabel:'Always show on startup',
            checked:true
        },
        ' ',
        {
            text:'Close',
            handler:function(){
                Ext.getCmp('mod-window').close();
            }
        }
    ]
});</code>

You may notice the xtype property being set in the checkbox definition. The xtype is a property used to determine the type of component being instantiated. Most containers have a default set, such as the buttons property; however, since we are adding a checkbox, we need to specify it as such so that a button is not created instead.

When we try to do a similar move for the message panel definition, we run into problems. The items property is one of those that you must usually set in the initComponent function of your extended object (refer to the documenation for more information Extending Ext Components). With that in mind we can then add an initComponent function and place the panel definition in it:

<code lang="JavaScript">
initComponent: function(){
	Ext.apply(this, {
		items:[
			{
				autoLoad:'message.html',
				bodyStyle:'padding:10px;'
			}
		]
	});
	demo.mod.ModDialog.superclass.initComponent.apply(this, arguments);
}</code>

We end up with an application.js file looking like:

<code lang="JavaScript">
// Path to the blank image must point to a valid location on your server
Ext.BLANK_IMAGE_URL = 'js/ext/resources/images/default/s.gif';

Ext.namespace('demo', 'demo.mod');

demo.app = function() {
    // do NOT access DOM from here; elements don't exist yet
    // private variables
    // private functions
    // public space
    return {
        // public properties, e.g. strings to translate

        // public methods
        init: function() {
            var win = new demo.mod.ModDialog();
            win.show();
        }
    };
}();</code>

and a demo.mod.ModDialog.js file looking like:

<code lang="JavaScript">
demo.mod.ModDialog = Ext.extend( Ext.Window, {
    id:'mod-window',
    title:'Message of the Day',
    draggable:false,
    modal:true,
    resizable:false,
    width:400,
    height:300,
    autoScroll:true,
    buttons:[
        {
            xtype:'checkbox',
            boxLabel:'Always show on startup',
            checked:true
        },
        ' ',
        {
            text:'Close',
            handler:function(){
                Ext.getCmp('mod-window').close();
            }
        }
    ],

    initComponent: function(){
        Ext.apply(this, {
            items:[
                {
                    autoLoad:'message.html',
                    bodyStyle:'padding:10px;'
                }
            ]
        });
        demo.mod.ModDialog.superclass.initComponent.apply(this, arguments);
    }
});</code>

And, I guess for completeness, the index.html file ends up as:

<code lang="html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">

		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all-debug.js"></script>
		<script type="text/javascript" src="js/application.js"></script>
        <script type="text/javascript" src="js/demo.mod.ModDialog.js"></script>

        <script type="text/javascript">Ext.onReady(demo.app.init, demo.app);</script>

		<title>Ext Demo</title>
	</head>
	<body></body>
</html></code>

True, we may end up with more code with this method; however, you have gained a great deal of structure, which is especially handy in JavaScript development. You can keep yourself to a single component per file paradigm, or you can simply group sub-components with a major component. 

<div style="float:right"><iframe src="http://rcm.amazon.com/e/cm?lt1=_blank&bc1=000000&IS2=1&bg1=FFFFFF&fc1=000000&lc1=0000FF&t=coffeael-20&o=1&p=8&l=as1&m=amazon&f=ifr&md=10FE9736YVPPT7A0FBG2&asins=1935182110" style="width:120px;height:240px;" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"></iframe></div>

As you add more and more script to your application, you will find it much more managable this way. 

Also, should you need a Message of the Day dialog in another project or for a different part of your app, you have only to bring the file over and use it (with your ExtJS setup of course).

Well, that's it for this introductory tour of ExtJS. I hope this has provided a decent starting point. I am doing a lot of ExtJS development right now and am currently reading <a href="http://www.amazon.com/gp/product/1935182110?ie=UTF8&tag=coffeael-20&linkCode=as2&camp=1789&creative=390957&creativeASIN=1935182110">Ext JS in Action</a><img src="http://www.assoc-amazon.com/e/ir?t=coffeael-20&l=as2&o=1&a=1935182110" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;display:none" /> so I am sure I will have more postings soon.

<blockquote>Source code for this article can be found on GitHub at extjs-demo. The work up through the end of this posting is tagged as “<a href="https://github.com/cjstehno/extjs-demo/tree/component">component</a>”.</blockquote>

