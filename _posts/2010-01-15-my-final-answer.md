---
title: My Final Answer
layout: default
--- # Categories
- development
- java
- programming
- thoughts
- tidbits
---

I use the Java 'final' keyword quite a bit and I often get asked why. I guess the quick answer would be, "why not"?

I tend to use 'final' for instance variables, method parameters and local variables. The keyword can also be used to make classes and methods final but I leave that for the cases when I actually want that specific functionality.

Final instance variables (private fields) are useful for case when you want to specify the value once and then never again. This is helpful when configuring an object through Constructor dependency injection:

<code lang="java">
public class MyController implements Controller {

    private final MyService myService;

    public MyController(final MyService myService){
        this.myService = myService;
    }

    // other methods
}</code>

This allows you to configure the service object in the controller and not have to worry about any of the other methods overwriting it.

Using final for method parameters is quite handy since it keeps you from unexpectedly overwriting one. Consider the following code:

<code lang="java">
public int count(List<String> list, String name){
    int count = 0;
    Iterator<String> names = list.iterator();
    while(names.hasNext()){
        name = names.next();
        if(name.equals(name)) count++;
    }
    return count;
}</code>

which contains a possibly hard to find variable overwrite error, which becomes quite obvious when you use final

<code lang="java">
public int count(final List<String> list, final String name){
    int count = 0;
    Iterator<String> names = list.iterator();
    while(names.hasNext()){
        String nme = names.next();
        if(nme.equals(name)) count++;
    }
    return count;
}</code>

Local variables are made final for basically the same reason.

Now, I know that simply because a variable is final does not mean it's internal data cannot change, such as

<code lang="java">
final Map<String,Date> dates = new HashMap<String,Date>();
dates.get("Anniversary"); // will work fine
dates.put("Birthday");    // will work fine
</code>

The final keyword does not make them immutable, you just cannot overwrite them. Final makes you think a bit before overwriting a variable with a new object and it can sometimes point out, and save you from, potential bugs.

[caption id="attachment_680" align="alignleft" width="257" caption="Eclipse Save Actions"]<a href="http://coffeaelectronica.com/blog/wp-content/uploads/2010/01/save-actions.png"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/01/save-actions-257x300.png" alt="" title="save-actions" width="257" height="300" class="size-medium wp-image-680" /></a>[/caption]Making your IDE do the work for you. If you use Eclipse (or one of its derivatives) you can have it automatically add 'final' to your code. Go into the Preferences and search for "Save Actions" and you should get something like what is show here. I generally auto-final method parameters and local variables since I have found that making private fields final can cause issues with some reflection and/or byte-code manipulation techniques.

Once you have the save actions setup, you will automatically have final added every time you save a file. I would imagine IntelliJ and NetBeans both have some similar functionality available.

Ultimately, the use of final comes down to personal preference. I find it useful and helpful to use the final keyword throughout my code. If you don't then you really don't have to use it unless you find a case where it's absolutely necessary. To me, though, I figure why not use it?

And that's my final answer. 

