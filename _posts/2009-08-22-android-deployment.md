---
title: Android Deployment
layout: default
--- # Categories
- android
- development
- programming
- tidbits
---

While playing around with <a href="http://android.com">Android</a> app development, I ran into an annoying issue when I tried to deploy my app into my real phone. It was working fine in the emulator, but I kept getting errors when I tried to install it on my phone. 

Here is what I was doing:

<ol>
    <li>I ran the proper Eclipse Wizard to export and sign the APK file</li>
    <li>Pushed the APK file out to my web server</li>
    <li>Pointed the phone's browser to the APK file to download it</li>
    <li>Clicked on the download to install it - and got an error about not being able to find the AndroidManifest.xml file</li>
</ol>

After some poking around the internet I was able to figure out that the mime type of APK files must be correct for the device to install them properly (picky picky). It must be "application/vnd.android.package-archive" for .apk files.

So, I dove into the apache config (often /usr/local/apache/conf/httpd.conf in case you are following along) and found the configuration for mime types referencing an external default mime type configuration file. I opened that file and as expected there was no mime type specified for APK files. I added the correct directive to the apache config to support the new mime type:

<code>AddType application/vnd.android.package-archive .apk</code>

I then restarted Apache and tried to do the install again... with the same results. Ok, how else could I ensure the correct mime type? Ah ha! The <tt>A</tt> tag in HTML has a <tt>type</tt> attribute used to specify the content type of the link contents.

I created an index.html page for my app and added a link with the correct content type:

<code lang="html"><a href="myapp.apk" type="application/vnd.android.package-archive">MyApp</a></code>

Surely, it would work now.

It's didn't work... and don't call me Shirley.

I was getting frustrated because all of the documentation I could find about installing apps on your own phone basically said to "just do it" with no more help than that. I figured that I was doing something stupid or missing something simple so I dropped it there for the night.

I tentatively picked it back up in the morning as a background thought. I knew that web-based delivery of Android apps worked because <a href="http://slideme.org">SlideMe</a> delivers their Android Marketplace app that way. That's when it hit me... what do they do that I am not doing?

I checked the source of their download page and there was nothing special.

I fired up Eclipse's TCP Monitor and downloaded the app through it to view the headers and wondered why I didn't think to do that sooner. The response headers coming back with their download were the following (I removed the cookie information, but the rest is as it came):

<pre>
HTTP/1.1 200 OK
Date: Thu, 20 Aug 2009 13:35:40 GMT
Server: Apache/2.2.3 (CentOS)
X-Powered-By: PHP/5.2.4
Expires: Sun, 19 Nov 1978 05:00:00 GMT
Last-Modified: Thu, 20 Aug 2009 13:35:40 GMT
Cache-Control: store, no-cache, must-revalidate
Cache-Control: post-check=0, pre-check=0
Vary: Accept-Encoding
Content-Encoding: gzip
Content-Type: application/vnd.android.package-archive
Cache-control: private
Set-Cookie: [removed]; expires=Sat, 12 Sep 2009 17:09:00 GMT; path=/; domain=.slideme.org
Set-Cookie: SERVERID=i-5f71cf36; path=/
Connection: close
Transfer-Encoding: chunked
</pre>

When I did the same thing for my download I got the following:

<pre>
HTTP/1.1 200 OK
Date: Thu, 20 Aug 2009 13:37:26 GMT
Server: Apache/2.0.52 (Red Hat)
Last-Modified: Thu, 20 Aug 2009 01:56:29 GMT
ETag: "[removed]"
Accept-Ranges: bytes
Content-Length: 64428
Keep-Alive: timeout=15, max=100
Connection: Keep-Alive
Content-Type: application/vnd.android.package-archive
</pre>

Notice that the content type was set the same in each; however, the slideme.org response header has a bunch of extra cache control headers. I thought maybe they had something to do with it, but because mine was showing up with the correct content type I gave it one more clean try using my phone.

It worked.

All I can figure is that it was either some sort of server or browser caching even though I restarted the server. This may be the reason why slideme.org has all the cache control headers. I will have to play with it for a bit. I will post if I find anything useful.

So what it comes down to is that as long as you have your mime type (content type) set properly, you can install your apps from your web server. The world is right again.

