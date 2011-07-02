---
title: Collapsible Divs With JQuery
layout: default
--- # Categories
- html
- javascript
- jquery
- programming
- tidbits
---

I coded up a nice little collapsible-group side bar thingy using <a href="http://jquery.com">JQuery</a> and it was surprisingly easy. Say you have a sidebar with collapsible group content such as:

<code lang="html">
<div class="container">
	<div class="group">
		<div class="group-title">Group A</div>
		<div class="group-content">
			This is where you would put the content for Group A.
		</div>
	</div>
	<div class="group">
		<div class="group-title">Group B</div>
		<div class="group-content">
			This is where you would put the content for Group B.
		</div>
	</div>
	<div class="group">
		<div class="group-title">Group C</div>
		<div class="group-content">
			This is where you would put the content for Group C.
		</div>
	</div>
</div>
</code>

where you have group block titles and group content that you want to be able to toggle the visibility of. With a couple lines of JavaScript and JQuery it's a sinch:

<code lang="javascript">
$('div.group-title').bind('click',function(evt){
	$(evt.target).parent().find('.group-content').slideToggle(500);
});   
</code>

which will be put inside an onload handler (also using JQuery). When the group title is clicked, the group-content block will toggle by sliding up or down in about half a second. With this model you can also place any number of these "components" on a page without the concern about event collision since the event handling is based on the click location.

Add in a little CSS and you end up with:
<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2009/12/collapsable-div.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/12/collapsable-div.png" alt="" title="collapsable-div" width="208" height="188" class="alignnone size-full wp-image-657" /></a>

