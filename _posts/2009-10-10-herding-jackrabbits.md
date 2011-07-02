---
title: Herding Jackrabbits
layout: default
--- # Categories
- development
- jackrabbit
- java
- jcr
- programming
---

I have been playing with <a href="http://jackrabbit.apache.org">Jackrabbit</a>, Apache's Java Content Repository (JCR 1.0) (see also <a href="http://jcp.org/en/jsr/detail?id=170">JSR-170</a>) implementation. So far, the JCR is a very interesting and seemingly under-utilized API, but I for the purpose of this discussion I am going to assume that you already have a bare minimum of understanding of Jackrabbit and the JCR. The documentation is a little thin in some parts so I decided to share some of my experimentation with NodeType registry and SQL searching of Nodes.

The registration of custom NodeTypes is apparently not part of the 1.0 spec, since the only implementation for registering custom node types seems to be in a Jackrabbit-specific class, though it does have a note about changes in JCR 2.0 moving the functionality into the core interface, which is nice. The basic code needed to load and register node types is as follows:

<code lang="java">
final NodeTypeManagerImpl manager = (NodeTypeManagerImpl)session.getWorkspace().getNodeTypeManager();
manager.registerNodeTypes(new FileInputStream(cndFile), NodeTypeManagerImpl.TEXT_X_JCR_CND, true);
</code>

The <tt>registerNodeTypes(...)</tt> boolean parameter being true forces a reload of the node types even if they have already been installed. You will notice that the node types are contained in a separate "cnd" file (see <a href="http://jackrabbit.apache.org/node-type-notation.html">Node Type Definition</a>). The file I used for my test was as follows:

<code>
<baggage = 'http://baggage.sourceforge.net/baggage'>

[baggage:Note]

> nt:base

- baggage:text (string) = ''

primary

mandatory autocreated

version
</code>

I am not sure at this point whether or not this is a grammar that they created themselves or if it is some external standard. It seems a bit clunky, but it gets the job done. Basically I am creating a namespace called "baggage" and adding a new node type to it called "baggage:Note" which will have one auto-created required property called "baggage:text".

It seems from some other tests I have done that you really need your own node types if you are going to do any querying of your content. You can use the default types but it could get really cluttered and cause your searches to slow down (Note: it uses Lucene internally for indexing and searching).

Once the new node types have been registered we can use them simply by creating nodes with them:

<code lang="java">
final Node root = session.getRootNode();
final Node notesRoot = root.addNode("notes-root");

final Node node1 = notesRoot.addNode("Something Interesting","baggage:Note");
node1.setProperty("baggage:text","This is some note that I would write.");

final Node node2 = notesRoot.addNode("Another Note","baggage:Note");
node2.setProperty("baggage:text","More really cool text content.");
</code>

which creates a "notes-root" node off of the main root node and then adds some "baggage:Note" nodes to it.

Once you have a few nodes with content it might be nice to search through them. The JCR gives you a fairly straight-forward query functionality using either XPath or SQL. I chose SQL for my test. Let's find all the "baggage:Note" nodes that contain the text 'cool'. The following code shows how this is done:

<code lang="java">
final QueryManager queryManager = session.getWorkspace().getQueryManager();

final String sql = "select * from baggage:Note where contains(baggage:text,'cool')";
final Query xq = queryManager.createQuery(sql, Query.SQL);
final QueryResult result = xq.execute();

final RowIterator rows = result.getRows();
log.info("Found: " + rows.getSize());
while(rows.hasNext()){
    final Row row = rows.nextRow();
    for(final String col : result.getColumnNames()){
        log.info(col + " = " + row.getValue(col).getString());
    }
    log.info("---");
}
</code>

Using my sample nodes, it will find one matching node and send the following output to the log appender:

<pre>
556  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - Found: 1
560  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - baggage:text = More really cool text content.
560  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - jcr:primaryType = baggage:Note
563  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - jcr:path = /notes-root/Another Note
564  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - jcr:score = 5035
564  [main] INFO  net.sourceforge.baggage.jcrex.JcrExp2  - ---
</pre>

The code for the whole test class is shown below:

<code lang="java">
public class JcrExp2 {

    private static final Logger log = LoggerFactory.getLogger(JcrExp2.class);

    public static void main(final String[] args) throws Exception {
        final Repository repository = new TransientRepository();
        final Session session = repository.login(new SimpleCredentials("username", "password".toCharArray()));

        final String cndFile = "baggage.cnd";

        final NodeTypeManagerImpl manager = (NodeTypeManagerImpl)session.getWorkspace().getNodeTypeManager();
        manager.registerNodeTypes(new FileInputStream(cndFile), NodeTypeManagerImpl.TEXT_X_JCR_CND, true);
        log.info("Registered Node types");

        try {
            final Node root = session.getRootNode();

            final Node notesRoot = root.addNode("notes-root");
            log.info("Added NotesRoot: " + notesRoot);

            final Node node1 = notesRoot.addNode("Something Interesting","baggage:Note");
            node1.setProperty("baggage:text","This is some note that I would write.");

            final Node node2 = notesRoot.addNode("Another Note","baggage:Note");
            node2.setProperty("baggage:text","More really cool text content.");

            session.save();

            // query

            final QueryManager queryManager = session.getWorkspace().getQueryManager();

            //          final String sql = "select * from baggage:Note";
            final String sql = "select * from baggage:Note where contains(baggage:text,'cool')";
            final Query xq = queryManager.createQuery(sql, Query.SQL);
            final QueryResult result = xq.execute();

            final RowIterator rows = result.getRows();
            log.info("Found: " + rows.getSize());
            while(rows.hasNext()){
                final Row row = rows.nextRow();
                for(final String col : result.getColumnNames()){
                    log.info(col + " = " + row.getValue(col).getString());
                }
                log.info("---");
            }

            // cleanup

            notesRoot.remove();
            session.save();

        } catch(final Exception e){
            log.error("Something bad has happened! " + e.getMessage(),e);

        } finally {
            session.logout();
        }
    }
}
</code>

You will need the JCR 1.0 jar as well as the jackrabbit 1.6 jar, both of which can be downloaded from the Jackrabbit downloads page.

The JCR seems pretty useful and flexible. I am surprised that it is not used more than it seems to be.





