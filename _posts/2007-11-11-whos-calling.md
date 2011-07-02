---
title: Who's Calling?
layout: default
--- # Categories
- java
- programming
- tidbits
---

Want to know the class of the object calling the method you are in?

<code lang="java">Reflection.getCallerClass(1);</code>

It’s not recommended because it’s a sun-specific class and not in the standard Java API, but it will do the trick. I do not really condone using non-API classes like this unless you really really have to, and even then you should comment it and externalize it so that it can be replaced as necessary with a better implementation at a later time.

<b>Note:</b> the parameter is how deep into the stack you want to go.

