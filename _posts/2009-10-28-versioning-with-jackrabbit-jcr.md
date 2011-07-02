---
title: Versioning with Jackrabbit (JCR)
layout: default
--- # Categories
- jackrabbit
- java
- jcr
- programming
- tidbits
---

With my recent exploration into <a href="http://jackrabbit.apache.org">Jackrabbit</a>, the JCR implementation, I figured out the basics of how to use its versioning functionality and decided I should do a little post about it so that I don't lose the code somewhere in the shuffle that is my project-space.

I'm going to start out by dumping out the code for my little test runner:

<code lang="java">
public class JcrVersioning {

    private static final Logger log = LoggerFactory.getLogger(JcrVersioning.class);

    public static void main(final String[] args) throws Exception {
        final Repository repository = new TransientRepository();
        final Session session = repository.login(new SimpleCredentials("username", "password".toCharArray()));

        try {
            final Node root = session.getRootNode();

            // Store content
            final Node textNode = root.addNode("mynode");

            final Node noteNode = textNode.addNode("alpha");
            noteNode.addMixin("mix:versionable");
            noteNode.setProperty("content", "I like jackrabbit");

            session.save();
            noteNode.checkin();

            // Retrieve content
            final Node node = root.getNode("mynode/alpha");
            log.info("Path: " + node.getPath() + " --> " + node.getProperty("content").getString());

            noteNode.checkout();
            noteNode.setProperty("content","Jackrabbit is cool");

            session.save();
            noteNode.checkin();

            log.info("After Modification: " + noteNode.getPath() + " --> " + noteNode.getProperty("content").getString());

            ////
            final VersionHistory vh = noteNode.getVersionHistory();
            final VersionIterator vi = vh.getAllVersions();

            vi.skip(1);
            while (vi.hasNext()) {
                final Version v = vi.nextVersion();
                final NodeIterator ni = v.getNodes();

                while (ni.hasNext()) {
                    final Node nv = ni.nextNode();
                    log.info(" - Found version: " + v.getCreated().getTime() + " --> " + nv.getProperty("content").getString());
                }
            }

            noteNode.checkout();

            final VersionHistory versionHistory = noteNode.getVersionHistory();
            final VersionIterator versionIterator = versionHistory.getAllVersions();

            versionIterator.skip(versionIterator.getSize()-2);

            noteNode.restore(versionIterator.nextVersion(), true);
            noteNode.checkin();

            log.info("After Restore: " + noteNode.getPath() + " --> " + noteNode.getProperty("content").getString());

            // Remove content
            root.getNode("mynode").remove();
            session.save();

        } finally {
            session.logout();
        }
    }
}
</code>

which creates a couple nodes, one of which has content. The content is set, modified and then restored to it's initial revision using the JCR versioning functionality. The enabler for versioning the the <tt>mix:versionable</tt> mixin set on the versionable node. Once that is set you need to be cautious of how your <tt>save()</tt> and <tt>checkout()</tt> and <tt>checkin()</tt> calls interact.

When you run this example you will get something like:

<pre>
INFO  JcrVersioning  - Path: /mynode/alpha --> I like jackrabbit
INFO  JcrVersioning  - After Modification: /mynode/alpha --> Jackrabbit is cool
INFO  JcrVersioning  -  - Found version: Wed Oct 28 19:26:59 CDT 2009 --> I like jackrabbit
INFO  JcrVersioning  -  - Found version: Wed Oct 28 19:26:59 CDT 2009 --> Jackrabbit is cool
INFO  JcrVersioning  - After Restore: /mynode/alpha --> I like jackrabbit
</pre>

Like I said this is really just a storage are for some sample code I came up with... it is what it is. Hopefully it can help if you are stuck with JCR versioning.
