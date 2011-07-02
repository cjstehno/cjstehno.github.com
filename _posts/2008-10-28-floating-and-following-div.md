---
title: Floating and Following Div
layout: default
--- # Categories
- css
- javascript
- programming
- prototype
- tidbits
---

I needed one of those <tt>DIV</tt>s that appears on call and then stays in view even when you scroll, until you close it. For lack of a better name, I call it the floating following div, and it's pretty easy to make. With a little help from <a href="http://prototypejs.org">Prototype</a> we can even make it work across the major browsers.

First you need to put the div to be floated somewhere on your page. The page itself can be anything you want.

<code lang="html"><div id="movable">This is my floating area</div></code>

 and then you need to give it some initial style:

<code lang="css">
#movable {
    position: absolute;
    left: 100px;
    width: 200px;
    height: 200px;
    background-color: red;
}</code>

Once all that is on the page, you will need some JavaScript to do the fancy stuff:

<code lang="html">
<script type="text/javascript" src="prototype.js"></script>
<script type="text/javascript">          
    Event.observe(window,'load',function(evt){
        $('movable').hide();
            
        Event.observe('showme','click',showDiv);
              
        Event.observe(window,'scroll', function(evt){
            $('movable').setStyle({ top: 8 + document.viewport.getScrollOffsets().top + 'px' });
        });
    });
            
    function showDiv(evt){
        $('movable').show();
    }
</script>
</code>

This causes the "movable" element to be hidden. Once the button with an id of "showme" is clicked, the element will be shown and will then follow along with vertical scrolling, staying up near the top of the view port. The key to this following motion is the function mapped to the <tt>window</tt> scrolling event:

<code lang="javascript">$('movable').setStyle({ top: 8 + document.viewport.getScrollOffsets().top + 'px' });</code>

The <a href="http://prototypejs.org/api/document/viewport/getscrolloffsets">document.viewport.getScrollOffsets()</a> function is provided by Prototype.

It's nothing exciting, but it works... just another thing posted here for future reference.

