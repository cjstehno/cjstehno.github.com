---
title: DiscGear Title Exporting
layout: default
--- # Categories
- java
- programming
- tidbits
---

We bought a few of the DVD storage units from <a href="http://www.discgear.com/">DiscGear</a>, which I highly recommend if you have more than a few CDs or DVDs that you want to keep in physical rather than digital form. 

You get a storage unit to keep all the discs in with a pull-out drawer listing all the contents of that unit. You also have a book filled with the literature from your discs (covers, inserts, etc.). Once you get all the setup you can then type it all up using a little application they give you so that it's not all handwritten. The downside of the little title manager application is that pretty much all it is good for is printing out the title index sheets... but ah, wait, did I mention that it's written in Java?

The index manager is a small Java Swing application that uses a serialized Hashtable as its save file. I wanted to pull out the titles and other information I entered so that I could use it in our other media management software. I wrote a little Java and put the discgear.jar file on the classpath... loaded the database file and piece of cake!

<code lang="java">public class Extractor {
	private static final String[] UNITS = {"A","B"};

	public static void main(final String[] args) {
		final String objfile = "database.ser";
		final String outfile = "discs.txt";

		final Hashtable<String, StorageUnit> container = loadObject(objfile);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(outfile));

			for(final String unit : UNITS){
				final StorageUnit storageUnit = container.get(unit);

				writer.println();
				writer.print("=== ");
				writer.print(storageUnit.label);
				writer.println(" ===");

				final Vector<Disc> discs = storageUnit.discs;
				for(int d=0; d<discs.size(); d++){
					writer.print(d+1);
					writer.print('\t');
					writer.println(discs.get(d).getTitle());
				}
			}

		} catch(final Exception e){
			e.printStackTrace();
		} finally {
			if(writer != null){
				try { writer.close(); } catch(final Exception e){}
			}
		}
	}

	private static Hashtable<String, StorageUnit> loadObject(final String objfile) {
		Hashtable<String, StorageUnit> container = null;

		InputStream instr = null;
		try {
			instr = new BufferedInputStream(new FileInputStream(new File(objfile)));
			container = (Hashtable<String, StorageUnit>)SerializationUtils.deserialize(instr);
		} catch(final Exception e){
			e.printStackTrace();
		} finally {
			if(instr != null){
				try { instr.close(); } catch(final Exception ex){}
			}
		}
		return container;
    }
}</code>

The output is fairly simple, though you could spruce it up a bit if you needed to. Basically it generates a text file with the titles grouped by box in order as they appear in the storage unit (numbered so that you can find them in the box).


<pre>
=== A ===
1	Mad Max (1980)
2	Journey to the Center of the Earth
3	Misery (1990)
...

=== B ===
1	Spaceballs
2	Rush
3	The Great Escape
...
</pre>

It's pretty down and dirty and I would have done it in <a href="http://groovy.codehaus.org">Groovy</a>; however, I did not have it installed on the computer I was using and this was pretty simple to pull together.

I am posting this just in case someone else comes across the need.
