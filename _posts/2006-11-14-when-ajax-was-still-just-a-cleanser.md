---
title: When Ajax Was Still Just a Cleanser
layout: default
--- # Categories
- ajax
- javascript
- programming
---

A few years ago the company I was working for did the <a href="http://simcityscape.com">SimCityScape</a> web interface, which was the online component to the SimCity 2 game (EA Games/Maxis). I was the developer assigned to do the high-end JavaScript and any other programming for the site, while the CSS and design work was done by one of our graphic designers. One of the requirements was that the game interface should not have to reload when an action is performed... it was a very heavy interface with a pile of images that all had to be placed with some complex JavaScript -- to reload this page with each action would have made the game unplayable.

These days I am sure the first thing that pops into your head is "AJAX, baby!"... well maybe without the "baby" part; however, back a couple years ago Ajax was still just a cleanser and there was no well-supported means of accomplishing "behind the scenes" data transfer without a page reload.

So what did I do? Well, I’ll tell you but you have to promise you won’t run away screaming... I am going to say a word that has a lot of undue negativity associated with it... applet.

Yes, I said it; I used an applet embedded in the page to allow the interaction on the front end to communicate with the backend, sans reload. It worked great, though I am not saying there weren’t some issues with it, especially since we had a "no plugin" requirement put on us, meaning that I only had the MS IE JVM (which is still lost somewhere around Java 1.1) available to me. I was able to use JavaScript to interface with the applet on the page which would fire off a request to the server. The server response was formatted text that was parsed into a data structure and returned to the JavaScript on the page (no <a href="http://json.org">JSON</a> back then either). The nice thing about using an applet was that you could pass Java objects back to JavaScript and "just use them". No fuss. No conversion.

Today I had the thought that even with all of this Ajax goodness, there still may be an interest in using an applet... so I will work a simple little example that does everything I mentioned above.

Let’s just make a simple data retrieval applet to demonstrate my point. You can take it from there if you are interested. The applet itself is quite simple. You just want to extend <tt>JApplet</tt> and override the <tt>init()</tt> method. All we are doing in our <tt>init()</tt> is getting the server url (there are other ways of getting this, but this is nice and easy). The applet code is shown below:

<code lang="java">
public class ControllerApplet extends JApplet {
    private String baseUrl;

    public void init() {
            this.baseUrl = getParameter("base.url");
            super.init();
    }

    public Map retrieveData(int id) throws Exception {
            Map map = new HashMap();
            URL url = new URL(baseUrl + "?pid=" + id);
            BufferedReader reader = null;
            try {
                    reader = new BufferedReader(
                            new InputStreamReader(url.openStream())
                    );
                    String line = reader.readLine();
                    if(line != null){
                            String[] nvps = line.split(";");
                            for(int n=0; n<nvps.length; n++){
                                    String[] parts = nvps[n].split(":");
                                    ap.put(parts[0],parts[1]);
                            }
                    }
            } catch(Exception ex){throw ex;}
            finally {
                    if(reader != null){
                            try {reader.close();} catch(Exception e){}
                    }
            }
            return(map);
    }
}</code>

You will notice that the interesting method is the <tt>retrieveData(int)</tt> method. This is where the fun is. This method opens a stream to a local url (must be on the same server) and retrieves data from a JSP page (shown later) using a parameter. The data returned is read into a map which will then be used by the JavaScript that calls the applet.

Let’s embed this applet in a page and see something happen. I am only going to show the important fragments:

<code lang="html">
<APPLET name='controller' code='controller.ControllerApplet' width='0' height='0' mayscript='mayscript'>
    <PARAM name='base.url' value='http://localhost:8080/data.jsp' />
</APPLET>
    
<form name='theForm'>
    Person:
    <select name='pid'>
            <option value='1'>Abe Ableman</option>
            <option value='2'>Bob Baker</option>
    </select><br/>
    <button onClick='doSubmit(document.theForm.pid.value)'>Retrieve Info</button>
</form>
</code>

There is a lot to look at here. The first thing is the applet tag (you probably should use the Java Plugin object/embed tags, but this works for both IE and FireFox). The applet tag is given a name so that it can be easily referenced and <tt>mayscript</tt> is set so that we can call the applet using JavaScript. Also of note is the fact that this applet is 0 by 0; It's a stealth applet.

The form is just a simple select list to pick a person that you want to retrieve information about. Now we need the JavaScript function that does the work:

<code lang="javascript">
function doSubmit(pid){
    try {
            var data = document.applets['controller'].retrieveData(pid);

            var info = 'Name: ' + data.get('Name') + '\n' +
                    'Department: ' + data.get('Department') + '\n' +
                    'Title: ' + data.get('Title');
            alert(info);
    } catch(ex){
            alert('Exception: ' + ex.toString());
    }
}</code>

That wasn’t too bad. Basically all you have to do is call the <tt>retrieveData(int)</tt> method on the applet and the applet does the rest. One feature I have always liked about this is the ability to pass actual Java objects from the applet to the JavaScript. You will note that the data structure used here is a <tt>Map</tt> and JavaScript has no problems letting you handle it on the client side.

The JSP page I used for this is just a simple data switch:

<code>
<%@ page language="java" contentType="text/plain" pageEncoding="UTF-8"%><%
String pid = request.getParameter("pid");
if(pid != null && pid.equals("1")){
    out.println("Name:Abe Ableman;Department:Accounting;Title:Head Bean Counter;");
} else if(pid != null && pid.equals("2")){
    out.println("Name:Bob Baker;Department:Development;Title:Code Slave;");
} %>
</code>

Ultimately that’s all there is to it and this example works on IE 6 and FireFox 1.5. You can expand this to use XML, SOAP, formal HTTP clients, etc, but this is the meat of it all. The big problem you run into is security restraints that keep you in your own domain and the fact that the applet must be loaded before you can use it. The latter seems obvious, but I ran into cases where things were not loaded as you would expect.

In my opinion, Ajax is a more stable way of doing things, but you should always keep alternatives like this in mind. Applets got a bad reputation over the years that I don’t think they really deserve; they were an amazing addition to the web when they came out and they can still be pretty cool.
