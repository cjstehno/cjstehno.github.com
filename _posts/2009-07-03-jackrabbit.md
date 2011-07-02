---
title: Jackrabbit
layout: default
--- # Categories
- jackrabbit
- java
- jcr
- programming
- tidbits
---

I decided to play around a little with <a href="http://jackrabbit.apache.org">Jackrabbit</a> the other day... here are some of my notes...

I am running on Ubuntu with <a href="http://tomcat.apache.org">Tomcat 6</a> and all I had to do to get started was download the Jackrabbit war distribution and install it in the webapps directory, then add the jcr-1.0.jar (downloaded from Sun) to the TOMCAT/lib directory. Once you start up the server and go into the context you get a nice welcome screen:

[caption id="attachment_492" align="alignnone" width="600" caption="Welcome screen"]<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/10/jack-screen-1.png" alt="Welcome screen" title="jack-screen-1" width="600" height="291" class="size-full wp-image-492" />[/caption]

I created a content repo in my home directory: 

<pre>/home/cjstehno/.jackrabbit</pre>

Once the repo is configured you get a list of common clients, with links on how to use them:

[caption id="attachment_494" align="alignnone" width="600" caption="Common clients"]<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/07/jack-screen-2.png" alt="Common clients" title="jack-screen-2" width="600" height="240" class="size-full wp-image-494" />[/caption]

I was interested in Standard WebDAV to do some simple content sharing between a few computers. It seemed simple enough and there is even a WebDAV connection setup in Ubuntu, but I had a heck of a time getting it to work. Finally I just tried pasting the connection URL into Nautilus directly:

<pre>http://localhost:8080/jackrabbit/repository/default</pre>

but that didn't work, so I tried a slight change,

<pre>dav://localhost:8080/jackrabbit/repository/default</pre>

which worked and asked me for a username and password, which the documentation says will take anything until you configure it. It looked like I had everything up and running:

[caption id="attachment_495" align="alignnone" width="600" caption="View from Nautilus"]<img src="http://coffeaelectronica.com/blog/wp-content/uploads/2009/07/jack-screen-3.png" alt="View from Nautilus" title="jack-screen-3" width="600" height="316" class="size-full wp-image-495" />[/caption]

Looks can be deceiving... I could add files but not edit them or add directories; however, after a refresh of the WebDAV view my folder was there. It did allow me to copy files from it into other directories and then work with them as normal. It does not seem like a very useful means of accessing the files. I can view everything in the web interface, but that is really not much better.

I decided to try connecting using one of the more programmatic means, the RMI client provided with Jackrabbit. It was actually very easy to connect and use. After digging around for a while with the files I had in the repository, It seems that this content repository stuff is quite flexible and could be very powerful, but the learning curve to make good use of it may be a little high... and there seems to be little documentation.

I wrote a little dumper app, which I called Jackalope so I could see how things were laid out in the repo:

<code lang="java">
public class Jackalope {
   public static void main( final String[] args ) {
        final ClientRepositoryFactory factory = new ClientRepositoryFactory();
        Session session = null;
        try {
            final Repository repo = factory.getRepository( "//localhost/jackrabbit.repository" );
            session = repo.login( new SimpleCredentials("cjstehno", "foo".toCharArray() ) );

            final String user = session.getUserID();
            final String name = repo.getDescriptor(Repository.REP_NAME_DESC);
            System.out.println("Logged in as " + user + " to a " + name + " repository.");

            final Workspace ws = session.getWorkspace();
            System.out.println("Workspace: " + ws.getName());

            final Node node = session.getRootNode();
            System.out.println("Node: " + node.getName());

            final NodeIterator children = node.getNodes();
            System.out.println("Children: " + children.getSize());
            while(children.hasNext()){
                final Node child = (Node)children.next();
                System.out.println("--> " + child.getName() + " [" + child.getPrimaryNodeType().getName() + "]");

                final NodeDefinition nodeDef = child.getDefinition();
                System.out.println("-->\tn: " + nodeDef.getDeclaringNodeType().getName());

                final PropertyIterator props = child.getProperties();
                while(props.hasNext()){
                    final Property prop = (Property)props.next();
                    System.out.println("-->\tp: " + prop.getName() + " = " + prop.getValue().getString());
                }

                if(! child.getPrimaryNodeType().isNodeType( "rep:system" ) ){
                    final VersionHistory history = child.getVersionHistory();
                    System.out.println("--> labels: " + Arrays.toString( history.getVersionLabels() ));
                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            session.logout();
        }
    }
}
</code>

It's nothing fancy, just a little code to poke around. There was apparently an Eclipse plug-in for navigating through JCR repos, but it has since died away. (Also, if you get errors running the code above, remove the last if block related to versioning... the versioning functionality is not well documented and this was bombing a lot for me).

Interesting stuff, but not really what I was looking for. I will keep it in mind for future use. 
