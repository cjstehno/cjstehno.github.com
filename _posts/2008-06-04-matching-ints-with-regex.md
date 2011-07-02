---
title: Finding Matching Ints Using Regex
layout: default
--- # Categories
- java
- programming
- puzzle
- regex
- tidbits
---

If you are following along you may have noticed that I have been compiling a long list of solutions to my <a href="http://coffeaelectronica.com/blog/2008/02/interview-question-find-2-matching-ints/">Finding Matching Ints</a> problem. I was talking to one of my co-workers, who is not a developer but used to do some Perl hacking, and he suggested that it could be done with regular expressions. Lo and behold, with the help of another of my more regular-expression-ized co-workers we found this to be true:

<code lang="java">
public int findDuplicate(final int[] array){
    final Pattern p = Pattern.compile(".*?(\\d+. ).*?\\1.*");
    final Matcher m = p.matcher(join(array));
    if(m.find()){
        return Integer.valueOf(m.group(1).trim());
    }
    throw new IllegalArgumentException("No match found!");
}

private String join(final int[] array){
    final StringBuilder str = new StringBuilder();
    for(final int i : array){
        str.append(i).append(" ");
    }
    return str.toString();
}
</code>

Granted, it's not quite as straight-forward as the other solutions, but it is a very novel approach to solving the problem... leave it to a Perl guy. :-) I wonder what the runtime of this would be?


