---
title: Circular Arrays
layout: default
--- # Categories
- java
- programming
- tidbits
---

Another one of those, "well, duh" moments... a very easy way to do wrap-around or circular array indexing.

<code lang="java">i = (i + 1) % N</code>

Where i is your current index and N is the length of the array.

Say you have an array of five elements. When you are currently on element of index 2, your next index will be:

<code lang="java">i = (2 + 1) % 5 = 3</code>

However, once you get to the last element, index 4:

<code lang="java">i = (4 + 1) % 5 = 0</code>

Viola, you are back at 0 again. I don't know why but I really neglect the mod operator (%). It has some interesting uses.

