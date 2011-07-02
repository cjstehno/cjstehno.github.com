---
title: Trimming a StringBuffer
layout: default
--- # Categories
- java
- programming
- tidbits
---

I was working with a StringBuffer recently refactoring some code that was doing a lot of string concatenation and I realized that there is no <tt>trim()</tt> method in StringBuffer. I found this to be an odd omission, so I wrote a little utility method to do it.

<code lang="java">
public static final StringBuffer trim(final StringBuffer sb){
	if(sb == null) return null; 
	while( sb.length() > 0 && Character.isWhitespace( sb.charAt(sb.length()-1) ) ){
		sb.deleteCharAt(sb.length()-1);
	}
	while( sb.length() > 0 && Character.isWhitespace( sb.charAt(0) ) ){
		sb.deleteCharAt(0);
	}
	return sb;
}
</code>

It's nothing pretty but it works. If you have a better algorithm for doing this more efficiently, I would love to hear about it.

You could do the same with <tt>StringBuilder</tt>; however, since they do not share a common interface with the required methods, it would have to be a copy of the above method with <tt>StringBuilder</tt> replacing <tt>StringBuffer</tt>.

As a bonus, here is a little test of the method:

<code lang="java">
@Test
public void trim(){
	final StringBuffer buf = new StringBuffer("  alpha bravo charlie   ");

	final StringBuffer sbuf = StringBufferUtils.trim(buf);

	assertEquals(sbuf,buf);
	assertSame(sbuf,buf);
	assertEquals("alpha bravo charlie",buf.toString());
}</code>

I will eventually put this into one of my shared libraries once they come available, but for now, enjoy!



