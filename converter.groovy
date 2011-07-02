import groovy.util.XmlSlurper

def xmlfile = new File(args[0])
def assetDir = new File(xmlfile.parent as String,'rc/img/assets')
def postDir = new File(xmlfile.parent as String,'_posts')

def xml = new XmlSlurper(false,true).parse( xmlfile )
xml.declareNamespace( wp:'http://wordpress.org/export/1.1/', content:'http://purl.org/rss/1.0/modules/content/' )

println 'Starting...'

xml.channel[0].item.each { item->
	def postType = item.'wp:post_type'.text()
	if( postType == 'attachment' ){
		handleAttachment item, assetDir
		
	} else if( postType == 'post' ){
		handlePost item, postDir
		
	} else if( postType == 'page' ){
		// not supported
	}
}

println 'Done.'

def handleAttachment( item, dir ){
	def url = item.'wp:attachment_url'?.text()
	def name = item.'wp:post_name'.text()
	def ext = url[ url.lastIndexOf('.')..-1 ]
	
	def imagefile = new File( dir, "$name$ext" )
	if( imagefile.exists() ){
		println "Skipping '$imagefile', it already exists..."
	} else {
		print "Downloading '$url' to '$imagefile'..."
		
		imagefile.setBytes( new URL(url).getBytes() )
		println 'Done.'
	}
}

def handlePost( item, dir ){
	def date = item.'wp:post_date'.text()[0..9]
	def postName = item.'wp:post_name'.text()
	
	def postFile = new File(dir, "${date}-${postName}.md")
	if( postFile.exists() ){
		println "Skipping '$postFile', already exists..."
		
	} else {
		print "Writing '$postFile'..."
		
		postFile << "---\n"
		postFile << "title: ${item.title}\n"
		postFile << "layout: default\n"

		if( item.category ){
			postFile << '--- # Categories\n'
			item.category.each { cat->
				if( cat.@domain == 'post_tag' || cat.@domain == 'category' ){
					def tag = cat.@nicename
					postFile << "- ${tag}\n"	
				}
			}	
		}

		postFile << "---\n\n"
		
		postFile << item.'content:encoded'.text()
		postFile << '\n'
		
		println 'Done.'
	}
}



