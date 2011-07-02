---
title: Spring Multipart Support With Annotations
layout: default
--- # Categories
- annotations
- java
- multipart
- programming
- spring
---

In working on the multi-file upload support in <a href="http://springsource.org">Spring</a> (see <a href="http://coffeaelectronica.com/blog/2008/02/spring-multipart-issue/">Spring Multipart Issue</a>), I had to code up a simple example of how it works now. It took me a little searching to figure out how to use multipart support with the new controller annotation configurations, though it turns out to be really easy. I figured I'd post a quick summary of how it's done.

With your standard file upload form:

<code lang="html">
<form action="upload.do" method="post" enctype="multipart/form-data">
    <div>File 1: <input type="file" name="file" /></div>
    <input type="submit" />
</form>
</code>
    
posting to your uploading controller, which is defined as follows:

<code lang="java">
@Controller
@RequestMapping("/upload.do")
public class UploadController {

    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView upload(@RequestParam("file") final MultipartFile multipartFile){
        final ModelAndView mav = new ModelAndView("done");

        // do stuff with the file

        return mav;
    }
}
</code>

which just seems way too simple. Don't forget to configure the <tt>MultipartResolver</tt> along with the rest of your spring-mvc config:

<code lang="xml">
<context:annotation-config />
<context:component-scan base-package="com.stehno.springmulti" />

<bean class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping" />
<bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter" />

<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/jsp/" />
    <property name="suffix" value=".jsp" />
</bean>
    
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="maxUploadSize" value="100000"/><!-- Max size in bytes. -->
</bean>
</code>
    
and that's it.

Like I said, nothing real exciting here, really just a quick setup starting point.

<b>Note:</b> my fix of the multiple-file upload support is on hold until Spring 3.0 is officially released. I know what needs to be done, it's just a waiting game now. :-)

