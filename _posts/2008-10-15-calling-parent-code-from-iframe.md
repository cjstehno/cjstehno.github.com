---
title: Calling Parent Code from IFrame
layout: default
--- # Categories
- html
- javascript
- programming
- tidbits
---

Every now and then I have needed to access the parent page enclosing an <tt>IFrame</tt> and I have never really found a good straight-forward example of how to do it, so I end up having to work it out each time I need it. So, for a parent (enclosing page) with an <tt>IFrame</tt> as follows:

<code lang="html">
<html>
    <head>
        <title>IFrame test</title>
        <script type="text/javascript">
        function closeIFrame(){
            if(confirm("Are you sure you want to close the iframe?")){
                document.getElementById("frame").style.display = 'none';
            }
        }
        </script>
    </head>
    <body>
        <iframe id="frame" src="visitor.html" width="200" height="200"></iframe>
    </body>
</html>
</code>

where the "visitor.html" page called by the <tt>IFrame</tt> is given as:

<code lang="html">
<html>
    <head>
        <title>Visitor</title>
        <script type="text/javascript">
        function closeMe(){
            parent.closeIFrame();
        }
        </script>
    </head>
    <body>
        <p>Hello, just visiting.</p>
        <button onclick="closeMe()">Close Me</button>
    </body>
</html>
</code>
    
When the "Close Me" button is clicked, the parent page will dispose of the <tt>IFrame</tt>, but the JavaScript function to do this actually resides in the parent page. 

One thing to note, is that both pages must reside on the same domain (or sub-domain) or else the script will not work, due to security restrictions.

