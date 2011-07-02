---
title: Extracting A Property from Collection Elements
layout: default
--- # Categories
- java
- programming
- tidbits
---

I wrote a little tutorial about <a href="http://coffeaelectronica.com/blog/2005/01/jakarta-commons-transformers/">Transformers</a> a while back and now I found a nice little use for them today. I needed (and do every now and then) need to extract the value of one property from every element of a collection. Yes, I could write an iterator loop and pull it out myself, but that takes more lines of code and is not reusable like this approach… I live for re-usability.

I needed a list containing the ids (long) of the elements in a collection. 

<code lang="java">
private static final Transformer tx = new InvokerTransformer("getId",null,null);

public static Long[] getIds(List list){
        Collection coll = CollectionUtils.collect(list,tx);
        return(coll != null ? (Long[])coll.toArray(new Long[0]) : null);
}</code>

The <tt>InvokerTransformer</tt> invokes the specified method and returns the result as the result of transformation. In this case, I want the result of the <tt>getId()</tt> method. The <tt>CollectionUtils.collect()</tt> method runs the transformer on each element in the incoming collection and creates a new collection containing the transformed results. Short and sweet.

