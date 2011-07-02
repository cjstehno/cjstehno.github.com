---
title: Obsessed With Ranges
layout: default
--- # Categories
- groovy
- javascript
- programming
- puzzle
- ruby
- tidbits
---

Okay, I had drafts for three more postings of different solutions to the Home on the Range problem mentioned in <a href="http://coffeaelectronica.com/blog/?s=ranges">earlier postings</a>. I decided that I need to merge them into one post to save you all from being overly spammed by odd little programming snippets.

First off, we have an alternate Groovy solution based on the recursive approach which was necessary for the Clojure implementation:

<code lang="groovy">
def iter( items, holder, str ){
    if( items.isEmpty() ) return "${str[0..(str.length()-3)]}."
	
	def it = items.remove(0) as int
	if( it+1 == (items[0] as int) ){
		iter( items, holder ?: it, str )
	} else {
		iter( items, null, holder ? "$str$holder-$it, " : "$str$it, " )
	}
}

def rangize( items ){
    iter( items, null, '' )
}
</code>

I had fun with this one as I got to play with some Groovy syntax sugar; however, I was unable to do it in a single function definition, so you get two.

Next, we have a solution done using Ruby, which I was surprised to see that I had not previously done.

<code lang="ruby">
def rangize( items )
	holder, str = nil, ''
	for i in (0...items.size())
		if items[i].to_i + 1 == items[i+1].to_i then
			if !holder then holder = items[i] end
		else
			str << (holder ? "#{holder}-" : '') << "#{items[i]}, "
			holder = nil
		end
	end
	str.chomp(', ') << '.'
end
</code>

Well, after working on it I realized why I didn't bother before... other than a few Ruby syntax items, it's not all that interesting.

The final one is the kicker. I got a little crazy and decide to do an implementation of the recursive approach using browser-based JavaScript:

<code lang="javascript">
function rangize(nums){
	function iter(items, holder, str){
		if(items.length == 0) return str;
		
		var itm = items[0];
		if( (parseInt(itm)+1) == parseInt(items[1]) ){
			return iter( items.slice(1), holder ? holder : itm, str);
		} else {
			return iter( items.slice(1), null, str + (holder ? holder+ '-' + itm : itm) + ', ' );
		}
	}
	var str = iter(nums,null,'');
	return str.slice(0, str.length-2) + '.';
}
</code>

Yes, you can nest functions inside of functions in JavaScript... I never realized that. The implementaion is not quite as clean as some of the others since JavaScript lacks some of the fancy string manipulation syntax; howerver, it really helps to show that JavaScript is a fairly robust and powerful language in itself.

For better or worse, you could add some additional helper functions (also nested) to clean up the iteration a bit, though it does make it a bit more "busy" when you first look at it:

<code lang="javascript">
function rangize(nums){
	function eqNxt( items ){ return (parseInt(items[0])+1) == parseInt(items[1]); }
	
	function endcap( str ){ return str.slice(0, str.length-2) + '.'; }			
	
	function iter(items, holder, str){
		if(items.length == 0) return str;
		
		return iter( 
			items.slice(1), 
			eqNxt(items) ? (holder ? holder : items[0]) : null, 
			eqNxt(items) ? str : (str + (holder ? holder+ '-' + items[0] : items[0]) + ', ')
		);
	}
	return endcap( iter(nums,null,'') );
}
</code>

Here is the whole code for the HTML test page:

<code lang="html">
<html>
	<head>
		<title>Home on The Range</title>
		<script type="text/javascript">
			function rangize(nums){
				function iter(items, holder, str){
					if(items.length == 0) return str;
					
					var itm = items[0];
					if( (parseInt(itm)+1) == parseInt(items[1]) ){
						return iter( items.slice(1), holder ? holder : itm, str);
					} else {
						return iter( items.slice(1), null, str + (holder ? holder+ '-' + itm : itm) + ', ' );
					}
				}
				var str = iter(nums,null,'');
				return str.slice(0, str.length-2) + '.';
			}
		</script>
	</head>
	<body onload="alert(rangize(['1','2','3','5','7','8','9','10','13']))"></body>
</html>
</code>

I think I have burned this coding puzzle to the end, so hopefully I can move on to something else... any suggestions?

