---
title: Tour de Mock 5: Groovy
layout: default
--- # Categories
- groovy
- programming
- testing
- tidbits
---

I decided to add another post to my series of mocking comparisons (see <a href="http://coffeaelectronica.com/blog/?s=Tour+de+Mock">Tour de Mock</a>). I have been working a lot with <a href="http://groovy.codehaus.org">Groovy</a> and felt that it would make an interesting addition considering the language provides a rich mocking ability without any additional libraries required.

The @Before method shows the flexibility of Groovy right away with the mocking of the service interface as well as the ServletContext and ServletConfig interfaces:

<code lang="groovy">
@Before
void before(){
	emailListService = [
		getListByName:{ name->
			if( 'foolist' == name ){ return LIST }
			throw new IOException()
		}
	] as EmailListService

	def servletContext = [
		getAttribute:{ key->
			assertEquals EmailListService.KEY, key
			emailListService
		}
	] as ServletContext

	def servletConfig = [ getServletContext:{servletContext} ] as ServletConfig

	emailListServlet = new EmailListServlet()
	emailListServlet.init servletConfig
}
</code>

Groovy allows you to take a map of closures keyed with the method name and cast it as an instance of an interface, basically built-in mocking.

The first test is where we test the doGet method with no listName parameter:

<code lang="groovy">
@Test(expected=IOException.class)
void doGet_without_list(){
	def request = [
		getParameter:{ pname->
			assertEquals 'listName', pname
			null
		}
	] as HttpServletRequest

	emailListServlet.doGet request, [] as HttpServletResponse
}
</code>

If you don't use Groovy much, these mocked instances may seem less readable than some of the other mocking APIs; however, with a little practice you can really see the broad scope of mocking capabilities it has.

The test with listName data shows a successful result:

<code lang="groovy">
@Test
void doGet_with_list(){
	def request = [
		getParameter:{ pname->
			assertEquals 'listName', pname
			'foolist'
		}
	] as HttpServletRequest

	def outcalls = [].addAll( LIST )
	PrintWriter.metaClass.println = { out-> assertEquals outcalls.remove(0), out }

	def response = [ getWriter:{ new PrintWriter(new StringWriter()) } ] as HttpServletResponse

	emailListServlet.doGet request, response
}
</code>

where we can also see the generally tricking mocking of the PrintWriter and its ordered sequence of calls. In this case you are working with simple code rather than any additional API functionality. The list of expected email strings is provided in the order they are to be called and the mock PrintWriter verifies that the current call is the same as the next expected value.

The whole test case is shown below:

<code lang="groovy">
package com.stehno.mockery;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static junit.framework.Assert.*

import org.junit.After
import org.junit.Test
import org.junit.Before;

import com.stehno.mockery.service.EmailListService;

class EmailListServlet_GroovyMockTest {

	private def LIST = ['larry@stooge.com','moe@stooge.com','curley@stooge.com']
	private def emailListServlet
	private def emailListService

	@Before
	void before(){
		emailListService = [
			getListByName:{ name->
				if( 'foolist' == name ){ return LIST }
				throw new IOException()
			}
		] as EmailListService

		def servletContext = [
			getAttribute:{ key->
				assertEquals EmailListService.KEY, key
				emailListService
			}
		] as ServletContext

		def servletConfig = [ getServletContext:{servletContext} ] as ServletConfig

		emailListServlet = new EmailListServlet()
		emailListServlet.init servletConfig
	}

	@Test(expected=IOException.class)
	void doGet_without_list(){
		def request = [
			getParameter:{ pname->
				assertEquals 'listName', pname
				null
			}
		] as HttpServletRequest

		emailListServlet.doGet request, [] as HttpServletResponse
	}

	@Test
	void doGet_with_list(){
		def request = [
			getParameter:{ pname->
				assertEquals 'listName', pname
				'foolist'
			}
		] as HttpServletRequest

		def outcalls = [].addAll( LIST )
		PrintWriter.metaClass.println = { out-> assertEquals outcalls.remove(0), out }

		def response = [ getWriter:{ new PrintWriter(new StringWriter()) } ] as HttpServletResponse

		emailListServlet.doGet request, response
	}

	@After
	void after(){
		emailListServlet = null
		emailListService = null
	}
}
</code>

I found Groovy to be a very useful mocking tool and would recommend it, especially if you are already using Groovy in your project. Beyond this basic functionality there are also some mocking APIs written for Groovy that add additional features or provide simpler mocking capabilities.

All of the source for this series of posts can be found at <a href="http://github.com">GitHub</a> in my <a href="http://github.com/cjstehno/TourDeMock">TourDeMock</a> repo. Or, you can just go <a href="http://github.com/cjstehno/TourDeMock/archives/master">download it</a>.
