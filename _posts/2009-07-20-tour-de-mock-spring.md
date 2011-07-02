---
title: Tour de Mock 1: Spring-Mock
layout: default
--- # Categories
- java
- mocking
- programming
- servlet
- spring
- testing
---

A common practice used in unit testing is the use of "<a href="http://en.wikipedia.org/wiki/Mock_object">Mock Objects</a>", usually called "mocking". There are a handful of robust, mature mocking APIs available and it can be difficult to determine which approach works best for you and your project. With this series of posts I intend to compare some (if not all, over time) of the more well-known and seasoned mocking APIs to showcase their usage, benefits, and drawbacks.

To accomplish this comparison I will be writing tests using each API to exercise a simple, yet not-too-trivial test case, requiring the mocking of both interfaces and concrete classes. I will be writing the test cases using <a href="http://junit.org">JUnit 4</a> with annotations.

For example purposes, let's suppose we have a servlet which is used to retrieve a list of email addresses when given a list name, <tt>listName</tt> request parameter:

<code lang="java">
public class EmailListServlet extends HttpServlet {

	private EmailListService emailListService;

	public void init() throws ServletException {
		final ServletContext servletContext = getServletContext();
		this.emailListService = (EmailListService)servletContext.getAttribute(EmailListService.KEY);

		if(emailListService == null) throw new ServletException("No ListService available!");
	}

	protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
		final String listName = req.getParameter("listName");
		final List<String> list = emailListService.getListByName(listName);
		PrintWriter writer = null;
		try {
			writer = res.getWriter();
			for(final String email : list){
				writer.println(email);
			}
		} finally {
			if(writer != null) writer.close();
		}
	}
}
</code>

You will notice that the servlet pulls the list from an <tt>EmailListService</tt> service interface:

<code lang="java">
public interface EmailListService {
	public static final String KEY = "com.stehno.mockery.service.EmailListService";

	/**
	 * Retrieves the list of email addresses with the specified name. If no list
	 * exists with that name an IOException is thrown.
	 */
	List<String> getListByName(String listName) throws IOException;
}
</code>

Okay, so it's not great design, but this is what you get sometimes... if it makes you feel better you can say that you started working on a project and this is one of the servlets you are supposed to write a test for. Feel better now?

To start off with, let's just say we are going to write a unit test for the servlet without any help from a mocking API. You create the test case (just a plain old class with <tt>@Test</tt> annotations in it with JUnit 4 and realize that the first thing you need to do is mock out the <tt>EmailListService</tt> interface. That's not too hard; it's an interface so you can simply implement it with your own <tt>MockEmailListService</tt> class. Not bad at all.

Next you realize that you need to get this service class into the <tt>ServletContext</tt> by way of a <tt>ServletConfig</tt> instance. Both of these are interfaces with no server-independent implementations... and they have quite a few methods to implement. What's worse is that you will eventually need to implement <tt>HttpServletRequest</tt> and <tt>HttpServletResponse</tt>. Go look at their JavaDocs. You will end up writing more code for your mock implementations than there are lines of code in the rest of the project and you won't be using more than about 25% of it for this test.

Now, you could figure it's just the price you pay for testing and trudge onward, but wait, <a href="http://http://springsource.org">SpringFramework</a> to the rescue. The spring-mock (called spring-test in later releases) module provides a good set of web-related mock implementations, and this is the first stop on our mocking tour.

The <tt>org.springframework.mock.web</tt> package provides <tt>ServletConfig</tt>, <tt>ServletContext</tt>, <tt>MockHttpServletRequest</tt> and a <tt>MockHttpServletResponse</tt> mock implementations so that we don't have to implement them ourselves. Let's create our first example test, called <tt>EmailListServlet_SpringMockTest</tt>. 

In the <tt>@Before public void before()</tt> method you will see the instantiation of our <tt>MockEmailListService</tt> (yes, we still have to create that one) and it's injection into the <tt>ServletContext</tt> by way of the <tt>ServletConfig</tt>. The request and response mocks are also instantiated for use in the test methods. (If you are unfamiliar with JUnit 4 testing, you may want to check out the documentation, or just understand that the <tt>@Before</tt> annotation means that the method will be called before each test method, annotated with <tt>@Test</tt>).

<code lang="java">
@Before
public void before() throws ServletException {
	final MockEmailListService emailListService = new MockEmailListService();
	final MockServletConfig servletConfig = new MockServletConfig();

	servletConfig.getServletContext().setAttribute(EmailListService.KEY, emailListService);
	this.servlet = new EmailListServlet();

	servlet.init(servletConfig);

	this.request = new MockHttpServletRequest();
	this.response = new MockHttpServletResponse();
}</code>

Our implementation of the <tt>EmailListService</tt> simply returns a list of emails when a non-null list name is used, and throws an exception if a null list name is passed:

<code lang="java">
private static class MockEmailListService implements EmailListService {
	@Override
	public List<String> getListByName(final String listName) throws IOException {
		if(listName == null){
			throw new IOException();
		} else {
			return Arrays.asList("larry@stooge.com","moe@stooge.com","curley@stooge.com");
		}
	}
}</code>

The first test to write is a simple test of the exception thrown when no list name is specified:

<code lang="java">
@Test(expected=IOException.class)
public void doGet_without_list() throws Exception {
	servlet.doGet(request, response);
}</code>	

That's pretty simple. Now, let's test the case when a list name is actually passed in:
<code lang="java">
@Test
public void doGet_with_list() throws Exception {
	request.setParameter("listName", "foolist");

	servlet.doGet(request, response);

	assertEquals(
            "larry@stooge.com" + sep + "moe@stooge.com" + sep + "curley@stooge.com" + sep,
            response.getContentAsString()
        );
}</code>

Again, it's pretty straight-forward. The whole test class is as follows:

<code lang="java">
public class EmailListServlet_SpringMockTest {

	private static final String sep = System.getProperty("line.separator");
	private EmailListServlet servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void before() throws ServletException {
		final MockEmailListService emailListService = new MockEmailListService();

		final MockServletConfig servletConfig = new MockServletConfig();
		servletConfig.getServletContext().setAttribute(EmailListService.KEY, emailListService);

		this.servlet = new EmailListServlet();
		servlet.init(servletConfig);

		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}

	@Test(expected=IOException.class)
	public void doGet_without_list() throws Exception {
		servlet.doGet(request, response);
	}

	@Test
	public void doGet_with_list() throws Exception {
		request.setParameter("listName", "foolist");

		servlet.doGet(request, response);

		assertEquals("larry@stooge.com" + sep + "moe@stooge.com" + sep + "curley@stooge.com" + sep,response.getContentAsString());
	}

	private static class MockEmailListService implements EmailListService {
		@Override
		public List<String> getListByName(final String listName) throws IOException {
			if(listName == null){
				throw new IOException();
			} else {
				return Arrays.asList("larry@stooge.com","moe@stooge.com","curley@stooge.com");
			}
		}
	}
}</code>

The spring mocking API is great, when you can use it, but it only has mocks for common (known) APIs; for anything beyond that you will have to do the mocking yourself. I try to use it whenever I can since it provides the implementation plumbing that you would need for some of the other mocking techniques.

<blockquote>You can find the source code used in this posting in my <a href="http://github.com/cjstehno/TourDeMock">TourDeMock</a> project.</blockquote>
