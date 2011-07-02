---
title: ExtJS Application Development 2: Setup
layout: default
--- # Categories
- development
- extjs
- javascript
- programming
---

Continuing on from "<a href="http://coffeaelectronica.com/blog/2010/11/extjs-application-development-1-introduction/">ExtJS Application Development 1: Introduction</a>"...

Let’s create a project directory called “ext-demo”, with a sub-directory called “js”. Download the <a href="http://www.sencha.com/products/js/">ExtJS</a> library and copy the unzipped directory into your “js” directory. Your ExtJS library directory will most likely be named with the version number. You can leave it as is, or remove the version name, as we will do so that the directory is simply named “ext”.

Some of the files in the ExtJS package are not really needed by a deployment. You can delete the following files and folders

<pre>/extdemo/js/ext/examples
/extdemo/js/ext/test
/extdemo/js/ext/docs
/extdemo/js/ext/src
/extdemo/js/ext/welcome
/extdemo/js/ext/index.html
/extdemo/js/ext/INCLUDE_ORDER.txt</pre>

Now we have ExtJS installed and almost ready to go. To finish off you need to configure your pages to use it. 

Add an index.html file to the root directory of your project and open it in your editor. Create the minimal ExtJs launch page by starting out with the template given by the Basic Page Setup tutorial, which we can trim that down to:

<code lang="html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css">

		<!-- Include here your own css files if you have them. -->

		<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all-debug.js"></script>

		<!-- Include your application script files -->

		<title>Ext Demo</title>
	</head>
	<body></body>
</html>
</code>

To this, add your application script include right after the ExtJS includes.

<code lang="html"><script type=”text/javascript” src=”js/application.js”></script></code>

This will be your entry point to ExtJS development. Open the application.js file and add the following:

<code lang="JavaScript">
// Path to the blank image must point to a valid location on your server
Ext.BLANK_IMAGE_URL = 'js/ext/resources/images/default/s.gif';
Ext.onReady(function() {
    alert("Hello ExtJS!");
});
</code>

Now, when you run the index.html page in your browser, you will see the obligatory “Hello World” application for ExtJS.

Since ExtJS has such a rich set of widgets, let’s make this into a bit more interesting... just a bit. Let’s replace the alert message box with something more Ext-y:

<code lang="JavaScript">
var win = new Ext.Window({
	title:'Ext Demo',
	html:"<div style='padding:25px;text-align:center;font-size:20pt;font-weight:bold;'>Hello ExtJS!</div>"
});
win.show();</code>

This creates a new window (dialog) with the given title and html content. Now when you refresh the page, you get something more attractive.
[caption id="attachment_820" align="alignleft" width="249" caption="ExtJS Says Hello."]<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/11/hello_ext.png" alt="" title="ExtJS Says Hello" width="249" height="136" class="size-full wp-image-820" />[/caption]
This window is movable, resizable, and non-modal. With a few additional properties, we can make it fixed, non-resizable, and modal. Just add the following config options after the html property (be sure to add a comma to the end of the html line):

<code lang="JavaScript">draggable:false,
modal:true,
resizable:false</code>

You can do a lot more than we are going into here; you will have to poke around the docs and samples on your own for more information.

For the purpose of example, let’s say we want to extend this code to create a simple "messagae of the day" modal pop-up window that will show users some predefined message. In order to implement this we will make some changes to our little hello world window:

<code lang="JavaScript">
var messagePanel = new Ext.Panel({
	autoLoad:'message.html',
	bodyStyle:'padding:10px'
});

var win = new Ext.Window({
	title:'Message of the Day',
	draggable:false,
	modal:true,
	resizable:false,
	width:400,
	height:300,
	autoScroll:true,
	items:[ messagePanel ]
});
win.show();
</code>

We removed the html property from the window and added an items property which contains a panel as the only item. We configured the panel as a standard Ext panel widget and set its autoLoad property to load our message content. The message content will come from a server url which will provide us with a single message as an HTML fragment (in this case an HTML file, but it could be any server url). We also added some styling and scrolling so that the window looks nice.

Unfortunately, due to browser security restrictions, we will need to run our code on a server from here on out. I will leave that setup for you and your favorite IDE.

[caption id="attachment_825" align="alignright" width="300" caption="Message of the Day"]<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2010/11/mod_start.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/11/mod_start.png" alt="" title="MOD" width="300" height="229" class="size-full wp-image-825" /></a>[/caption]The figure to the right show the dialog that we have now. You can close the window with the X up in the top right corner; however, it would be nice to have a close button as well, so lets add one:

<code lang="JavaScript">
var closeButton = new Ext.Button({
	text:'Close',
	handler:function(){
		Ext.getCmp('mod-window').close();
	}
});</code>

This defines a simple close button that will close the window when it is clicked. The “mod-window” is added to our window as its id and the button is added to the window using it’s “buttons” property:

<code lang="JavaScript">
var win = new Ext.Window({
	id:'mod-window',
	buttons:[ closeButton ],
	...</code>

And to finish off, let’s add one of those “always show on startup” checkboxes so you can opt-out of the messages. Again, it’s pretty simple:

<code lang="JavaScript">
var modCheckbox = new Ext.form.Checkbox({
	boxLabel:'Always show on startup',
	checked:true
});</code>

which is added to the button bar as:

<code lang="JavaScript">buttons:[ modCheckbox, ' ', closeButton ]</code>

and we end up with:
[caption id="attachment_833" align="aligncenter" width="300" caption="Finished Dialog"]<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/11/mod_final.png" alt="" title="MOD Dialog" width="300" height="229" class="size-full wp-image-833" />[/caption]

Now, step back and look at the JavaScript you have written to create this message box. It’s not bad, about 30 lines or so of fairly straight-forward JavaScript code. Consider that this is only one tiny part of your rich UI application which will probably consist of other dialogs, grids, lists, ajax calls, forms and a pile of other widgets. Applications developed using ExtJS, or any JavaScript framework for that matter, can explode into hundreds or thousands of lines of script code that, in general, are not really checked by a compiler and not usually unit tested... that’s scary.

But, don’t go searching for your binki just yet, there is hope. ExtJS provides a powerful means of packaging and componentizing your application so that your script code is more manageable and reusable, but that will be covered in the next installment.

<blockquote>Source code for this article can be found on GitHub at <a href="https://github.com/cjstehno/extjs-demo">extjs-demo</a>. The work up through the end of this posting is tagged as "setup" (if I figured out how to do tags correctly).
</blockquote>
