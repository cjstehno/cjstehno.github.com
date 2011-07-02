---
title: Spring Multifile Uploading Bug: Fixed
layout: default
--- # Categories
- java
- multipart
- programming
- spring
---

Well, they beat me to it; the <a href="http://coffeaelectronica.com/blog/2008/02/spring-multipart-issue/">Spring Multipart Issue</a> has been fixed as of Spring 3.0-RC1. I am happy to see that though a little bummed because I was looking forward to fixing it and submitting a patch. Nice work, to whoever fixed it!

I wrote a general posting about how to do file uploads with the new spring annotations (see <a href="http://coffeaelectronica.com/blog/2009/06/spring-multipart-support-with-annotations/">Spring Multipart Support With Annotations</a>) so now, with the new multi-file support you can have more than one input file with the same form input name.

The input form becomes

<code lang="html">
<form action="upload.do" method="post" enctype="multipart/form-data">
    <div>File 1: <input type="file" name="files" /></div>
    <div>File 2: <input type="file" name="files" /></div>
    <input type="submit" />
</form>
</code>

to provide two files with the same form field name. 

The controller code does not really change much, other than the fact that the multi-file support does not seem to extend to the <tt>@RequestParam()</tt> annotation support by default, so I had to use a different method signature.

<code lang="java">
@Controller
@RequestMapping("/upload.do")
public class MultifileUpload {

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView upload(final MultipartHttpServletRequest request){
		final ModelAndView mav = new ModelAndView("done");

		final List<MultipartFile> multipartFiles = request.getFiles("files");

		// do stuff with the file
		System.out.println("found " + multipartFiles.size() + " files");

		return mav;
	}
}
</code>

It's nice to see that this bug got fixed, and they seem to have dropped the provided support for other upload APIs, though you could still write your own implementation as needed.

