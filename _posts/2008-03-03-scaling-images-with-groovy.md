---
title: Scaling Images With Groovy
layout: default
--- # Categories
- groovy
- programming
- tidbits
---

I had the need to scale some large JPG images from my camera so I whipped up a quick little <a href="http://groovy.codehaus.org">Groovy</a> script to do the trick. Thought I would share.

<code>
// groovy ScaleImages.groovy DIRNAME SCALE%
    
import javax.imageio.ImageIO
import java.io.File
import java.awt.Image
import java.awt.Color
import java.awt.image.BufferedImage
    
class ScaleImages {
    
    static void main(String[] args){
        def directory = new File(args[0])
        if(!directory.isDirectory()){
            System.out.println("You must specify a valid directory!")
            System.exit(0)
        }
    
        def scale = Integer.valueOf(args[1])
    
        directory.eachFile {
            def image = ImageIO.read(it)
            int w = image.getWidth() * scale / 100
            int h = image.getHeight() * scale / 100
    
            def scaled = image.getScaledInstance(w,h,Image.SCALE_SMOOTH)
   
            def newImage = new BufferedImage(w,h,image.getType())
            def graphics = newImage.createGraphics()
            graphics.drawImage(scaled,0,0,w,h,Color.white,null)
			
            if(ImageIO.write(newImage,"jpg",new File(it.getParent(),"scaled_" + it.getName()))){
                System.out.println("scaled: " + it);
            } else {
                System.out.println("failed: " + it);
            }
        }
    }
}</code>

It's pretty simple. You load the image file to create a <tt>BufferedImage</tt>. You then create a scaled <tt>Image</tt> and draw it onto the new empty <tt>BufferedImage</tt> and save it off. I would recommend some performance enhancements if you are doing huge batches of images but for a directory containing a handful of images it works great and pretty fast. Also, note that this does not handle sub-directories of images, only the directory that you give it.

