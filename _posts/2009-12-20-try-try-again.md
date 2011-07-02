---
title: ...Try Try Again
layout: default
--- # Categories
- development
- java
- programming
- thoughts
- tidbits
---

After yesterday's post, <a href="http://coffeaelectronica.com/blog/2009/12/if-at-first-you-dont-succeed/">If At First You Don't Succeed...</a>, I thought of a slightly different approach to the retry execution method:

<code lang="java">
public <T> T execute( final Retriable<T> op ) throws Exception {
    for(int r=0; r<maxRetries; r++){
        try {
            return op.execute();

        } catch(final Exception e) {
            if( ArrayUtils.contains(catchAndThrow, e.getClass()) ) throw e;

            if(log.isWarnEnabled()){
                log.warn("RetryCaughtException[" + r + "]: " + e.getMessage());
            }
        }   
    }

    if(log.isWarnEnabled()) {
        log.warn("RetriesFailed: " + op.getClass().getName());
    }    

    throw new MaxRetriesExceededException(maxRetries,op.getClass().getName());
}
</code>

This approach does not rely on the boolean to end the loop. If an exception is thrown it will try again until a return value is returned or the retry count has been exceeded. With this approach you only fall out of the loop on a retry exceeded condition, hence the logging and the thrown exception. This feels a little cleaner than the other approach.

I have not run it through the unit testing so, you might do that first if you intend to use this code.
