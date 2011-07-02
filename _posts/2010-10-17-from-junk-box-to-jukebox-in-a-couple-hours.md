---
title: From Junk Box to Jukebox in a Couple Hours
layout: default
--- # Categories
- servers
- technology
- tidbits
---

I finally got tired of griping about not having enough space on my phone to store all of my music, and not wanting to pay for one of the music cloud storage services... so I decided to bite the bullet and setup my own using <a href="http://ubuntu.com">Ubuntu Server</a>, <a href="http://subsonic.org">SubSonic Music Streamer</a> and its Android app. It only took a couple hours, most of which was baby-sitting installations, and now I am able to listen to any/all of my music whenever and wherever I want; it's quite nice.

The steps are pretty straight forward, but going into this process I must make it clear that I am not an expert network/server administrator, I am a developer, therefore if this process causes you or your equipment any harm in any way, sorry, but it's not my fault. Continue at your own risk.

First, you need a computer to work with. I had an old Dell desktop that is about four or five years old and it is working nicely. Hook up a keyboard, mouse and monitor to the box... if it's an old desktop box you should have all the connections you need. You will also need to connect the box to your network.

It almost goes without saying that you will need internet access of at least DSL, but really I would not do this with anything less than a high-speed cable internet connection.

[caption id="attachment_777" align="alignleft" width="200" caption="Ubuntu.com"]<a href="http://ubuntu.com"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/10/ubuntu-page.png" alt="" title="ubuntu-page" width="200" height="99" class="size-full wp-image-777" /></a>[/caption]Download <a href="http://www.ubuntu.com/server">Ubuntu 10.10 Server</a> (or whatever the most current version is when you read this). Make sure that you are downloading the server version, not the desktop. Also make sure that you download the appropriate version for your system, 32- or 64-bit. I had to use 32-bit since it was an old desktop box. 

Burn the .iso file you downloaded to a CD (I recomment <a href="http://www.imgburn.com/">ImgBurn</a> if you don't have a favorite image buring application). Once you have the CD created, pop it into the box you are building and boot from it. You may have to fiddle with your boot order in your bios, but the CD is bootable.

Once the Ubuntu installation menu comes up, just follow the instructions. A couple items to be aware of: You will want to enable the automatic security updates so that you don't have to bother with it yourself. Also, you will come to a server installation screen (after the restart I think)... at this point I selected LAMP server, Tomcat server and OpenSSH server. The SSH server is required for this, though the others are not; I plan on adding more to this box so I wanted to have the servers ready to go.

Once the installation is done, disconnect the keyboard, mouse and monitor, but leave the box running. You now have a nice clean Linux web server to play with.

I recommend giving the new server box a dedicated IP address on your network so that you don't need to keep track of it. This is gererally very specific to your router, so I can't really go into it here.

From your desktop box, laptop, whatever, connect to the new server using your favorite SSH client (for windows you can use <a href="http://cygwin.com">Cygwin</a> or <a href="http://www.chiark.greenend.org.uk/~sgtatham/putty/">Putty</a>).

All of my music is on a NAS on my local network, so I had to create a mount of the music directory on the server box. There is a nice wiki article about how to do this, <a href="https://wiki.ubuntu.com/MountWindowsSharesPermanently">Mount Windows Shares Permanently</a> so that's easy enough.

[caption id="attachment_779" align="alignright" width="200" caption="Subsonic.org"]<a href="http://subsonic.org"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/10/subsonic-page.png" alt="" title="subsonic-page" width="200" height="123" class="size-full wp-image-779" /></a>[/caption]Now download the deb installer file from <a href="http://www.subsonic.org/">SubSonic</a> and follow the installation instructions. There are also some additional packages they recommend installing. I did.

I eventually want to have SubSonic use the default servers on the box, but this installation method seemed to be the fastest one to get a server up and running.

Once you have SubSonic installed you will need to create a directory for your playlists, "/var/playlists" by default.

Now that the server is running, go ahead and login and change the admin password and set the music and playlist locations. I created a second user to user for myself that did not have admin permissions. Make sure that you can see a list of your music artists down the left hand side and you are ready to go... at least for access on your own network.

[caption id="attachment_781" align="alignleft" width="200" caption="Lifehacker.com"]<a href="http://lifehacker.com/127276/geek-to-live--how-to-access-a-home-server-behind-a-routerfirewall"><img src="http://coffeaelectronica.com/blog/wp-content/uploads/2010/10/lifehacker-page.png" alt="" title="lifehacker-page" width="200" height="124" class="size-full wp-image-781" /></a>[/caption]In order to have access to SubSonic from the outside world, you need to setup port forwarding on your router to map one of the external ports to the server and port that your SubSonic instance is running on. Again, this is router-specific, but <a href="http://lifehacker.com">Lifehacker</a> has a good overview in the article "<a href="http://lifehacker.com/127276/geek-to-live--how-to-access-a-home-server-behind-a-routerfirewall">How to Access a Home Server Behind a Router Firewall</a>".

Once you have access from the outside world, you can install the Android or iPhone apps for SubSonic and play your music while you are away from home. The Android app is quite good. I created two profiles, one for home when I can use my local wireless network, and another for access when I am away from my network... helps to keep the bandwidth down. Using the phone is also a nice way to test out the external interface. Turn off your wifi antenna and connect via 3G. 

One concern I have about running my own server is power consumption. The box I am using was a desktop box and not really made to conserve energy. I am looking into ways of setting up smart power usage, sleeping, etc, but that will come in another posting. For now I am going to test out the actual power consuption using one of those <a href="http://www.thinkgeek.com/gadgets/travelpower/7657/">Killawatt</a> plug-in wattage meters so that I can see what I am doing to my monthly power bill. 

One thought I had is that if the power consumption is too high, I might look into building a really low-power box with an atom processor and solid-state drive or something else really bare bones and cheap.

I have a few other projects in mind for this now that I have a server running, though I don't think I will be moving my blog hosting there any time soon, but you never know.

If any of this is too vague, please let me know and I can add more detail. This is intended for users with minimal Linux, networking, and hardware experience, but sometimes I take more for granted than I realize.

Good luck and have fun!
