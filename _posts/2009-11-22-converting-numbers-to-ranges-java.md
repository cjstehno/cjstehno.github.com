---
title: Converting Numbers to Ranges: Java
layout: default
--- # Categories
- java
- programming
- puzzle
---

Yesterday I posted my Python code for the <a href="http://coffeaelectronica.com/blog/2009/11/converting-numbers-to-ranges-python/">Converting Numbers to Ranges</a> problem. Today I decided to do a quick Java implementation. 

<code lang="java">
public class HomeOnTheRange {

    public static void main( final String[] args ) {
        System.out.println( convertToRanges( args ) );
    }

    public static String convertToRanges(final String[] args){
        final int[] nums = new int[args.length];
        for( int n=0; n<args.length; n++){
            nums[n] = Integer.valueOf( args[n] );
        }

        String hold = null;
        final StringBuilder str = new StringBuilder();

        for(int i=0; i<args.length; i++){
            if( i+1 < args.length && nums[i]+1 == nums[i+1] ){
                if(hold == null) hold = args[i];
            } else {
                if(hold != null){
                    str.append(hold).append('-').append(args[i]).append(", ");
                    hold = null;
                } else {
                    str.append( args[i] ).append(", ");
                }
            }
        }

        str.delete( str.length()-2, str.length() ).append( '.' );

        return str.toString();
    }
}
</code>

There is not much difference from the Python version; I guess with simple problems like these you are going to end up with pretty much the same form of solution unless you can find some tricky little piece of the language that works better as a solution.

One thing I did different in the Java solution is that I pre-converted the argument String array into an integer array. I noticed that you really end up doing the string to int conversion twice for most of the input elements, which in Java can really add up for a large data set. I wonder if the same problem is inherent in the python solution? I also did the ending period a little different here, simple deleting the end of the string buffer and adding a period; an approach similar to the python version would probably be a little better, but either way is fine.

I guess you could write a separate method to do the string to int conversion and then cache the results so that you are still doing the conversion once and "inline" with the main loop. With a little thought about the problem space you can see that each string is converted and used at most twice, so you can setup the caching to convert and cache on the first use and then just return the converted int on the second call. 

<code lang="java">
public class HomeOnTheRange2 {

    private static final ThreadLocal<Converter> converter = new ThreadLocal<Converter>(){
        @Override
        protected Converter initialValue() {
            return new Converter();
        };
    };

    public static void main( final String[] args ) {
        System.out.println( convertToRanges( args ) );
    }

    public static String convertToRanges(final String[] args){
        String hold = null;
        final StringBuilder str = new StringBuilder();

        final Converter conv = converter.get();
        for(int i=0; i<args.length; i++){
            if( i+1 < args.length && conv.toInt( args[i] )+1 == conv.toInt( args[i+1] )){
                if(hold == null) hold = args[i];
            } else {
                if(hold != null){
                    str.append(hold).append('-').append(args[i]).append(", ");
                    hold = null;
                } else {
                    str.append( args[i] ).append(", ");
                }
            }
        }

        str.delete( str.length()-2, str.length() ).append( '.' );

        return str.toString();
    }

    private static final class Converter {

        private String key;
        private int value;

        public int toInt(final String str){
            if( !str.equals( key ) ){
                this.key = str;
                this.value = Integer.valueOf( str );
            }
            return value;
        }
    }
}
</code>

You will see that this code no longer does the initial conversion loop. The string to int conversion is now done using the <tt>Converter</tt> class, which simply converts and caches the value on the first request for a value, and will simply return the cached int for a second call of the same number. This makes the assumption that the numbers are in ascending order, which is valid for this problem. I also added the converter as a <tt>ThreadLocal</tt> variable since this conversion is now very tied to the order in which the values are converted. Making it thread-safe ensures that two calls to this method on different threads will not mess with each others values.

I considered just using a Map of some sort, but without some sort of bounds you end up caching every number, when as you can see you really only need one.

Technically delving that deep into the problem to come up with a custom caching solution is really premature optimization, which is generally a bad thing. You should just to the original conversion as needed and then do the followup refactoring if performance bottlenecks lead you to do so.
