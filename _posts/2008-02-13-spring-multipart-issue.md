---
title: Spring Multipart Issue
layout: default
--- # Categories
- java
- multipart
- programming
- spring
- wtf
---

I have been implementing some file upload handlers for s <a href="http://springframework.org">Spring</a> MVC-based application I am working on (at work) and I ran into a frustrating issue with Spring's nicely integrated <tt>MultipartResolver</tt> API. It doesn't support multiple file entries with the same input field name. So, as in my case, if you have a dynamic list of files being imported each having the same HTML input name (not something I can easily change at this point), the resolver will throw an exception about multiple files with the same field name.

In version 2.0.x it simply took the last one of the group and silently went about its business (see <tt>CommonsFileUploadSupport.java</tt>).

<code lang="java">
// multipart file field
CommonsMultipartFile file = new CommonsMultipartFile(fileItem);
multipartFiles.put(file.getName(), file);
if (logger.isDebugEnabled()) {
    logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() +
        " bytes with original filename [" + file.getOriginalFilename() + "], stored " +
        file.getStorageDescription());
}</code>

The "same" code in the 2.5 line throws an exception, which is better at least technically, due to the fact that it tells you about the problem.

<code lang="java">
// multipart file field
CommonsMultipartFile file = new CommonsMultipartFile(fileItem);
if (multipartFiles.put(file.getName(), file) != null) {
    throw new MultipartException("Multiple files for field name [" + file.getName() + "] found - not supported by MultipartResolver");
}
if (logger.isDebugEnabled()) {
    logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() +
        " bytes with original filename [" + file.getOriginalFilename() + "], stored " +
        file.getStorageDescription());
}</code>

Originally I thought, "cool, I found a Spring bug that I can fix and submit"... then I saw the new code (2.5.x) and realized that this was <i>intentional</i>. I did some searching through the bug tickets and did find one related to this issue <a href="http://jira.springframework.org/browse/SPR-2784">Support MultipartFile-array Property</a>. It was then that I found the root of my frustration.

<blockquote>It seems that COS doesn't support file parts with the same name, thus the feature cannot be implemented there.</blockquote>

I cannot say whether or not <a href="http://servlets.com/cos/">COS</a> has a production-ready implementation of a multipart handler, but Jason Hunter is a smart guy so it is at the very least decent; however, it seems short-sighted for the Spring developers to limit the capabilities of file uploading based on the limitations of an API that has not been updated since 2002. Honestly, after looking deeper into the COS source and docs, I am not sure that it will <i>not</i> handle multiple inputs correctly. It looks like the <tt>MultipartRequest</tt> might not, but the <tt>MultipartParser</tt> seems pretty raw such that it might be easy to implement a work-around.

In either case, I will have to write or re-write some Spring code to implement another <tt>MultipartResolver</tt> that will handle multiple input files. It can be done in less than 10 lines of added code if I cut and paste the relevant Spring sources... there are a couple layers of inheritance to deal with and the code that needs to change is in the top parent class. D'oh. (backseat coding, but this probably would have been a good place for a strategy pattern). I do not advocate the cut and paste approach for anything other than absolute necessity (which has only happened one other time for me in the past four years).

The <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC-1867</a>, remains silent on this matter.

Oh, well, when I have a few spare minutes I will setup a test of the COS <tt>MultipartParser</tt> and see what it can handle. This still may be a fixable Spring bug yet. I will post results.

All in all, if this is the first and only gripe I have about Spring after using it for about four years... that's not too bad at all. Also, I must say that their source code is very pleasant to browse through.
