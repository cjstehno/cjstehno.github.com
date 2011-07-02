---
title: Ubuntu for the Noncomittal
layout: default
--- # Categories
- linux
- technology
- ubuntu
---

Some people are reluctant to do a full conversion from the familiarity and comfort of a Windows OS to the unknown environment that is Linux. Fear not, there are a couple ways you can give it a try with very little commitment.

The first thing you will need to do in each option is to download the <a href="http://ubuntu.com">Ubuntu</a> <dfn title="CD image file">ISO</dfn> file for your specific hardware (generally 32-bit desktop version). I choose Ubuntu because it is very easy to use and very well supported, and of course its free.

<strong>1. Virtual Machine</strong>

Using <a href="http://www.virtualbox.org">VirtualBox</a> (free) from Sun Microsystems, you can setup a virtual computer that runs on your current Windows OS. Basically VirtualBox runs as an appliation that "hosts" another operating system.

Once you have VirtualBox installed you can create a virtual machine using the Ubuntu ISO file to "install" the OS.

<strong>2. Wubi Dual Boot</strong>

Another low-impact means of trying out linux is to use the <a href="http://wubi-installer.org">Wubi Installer</a> which will install Ubuntu on your windows machine, creating a dual boot so that you can select which OS to enter when you start up your computer. The benefit of wubi is that if/when you no longer want ubuntu around (or if you break it) you can just go into windows and uninstall wubi just like uninstalling any other software. (I fibbed a little at the start of this posting, you don't actually need the ISO for this installation method).

<strong>3. Extra Computer</strong>

The third option assumes that you have an older (though still somewhat modern) computer laying around unused. You will need to take the ISO file and burn it to a CD, I recommend using using <a href="http://imgburn.com">ImgBurn</a>. Once that is done boot up the target computer and be sure that the BIOS is set to boot from the CD. It should start up with some Ubuntu options; you can either take the plunge and install it, or you can play around with the liveCD version for a bit first.

Installing Ubuntu is very friendly and generally better and faster than
installing a windows os.

<strong>Disclaimer</strong>

I am only providing the top-level pieces of how to do these installations; you should still read any and all relevant documentation before getting into this, especially if you are doing this on your only computer. Generally, these installation methods are very safe, but you can still cause damage if you do something stupid (you do still have full access to your host OS in the first two options, so you could conceivably delete your windows OS files if, for example). I take no ownership or responsability for any harm caused by these installations.

Now go have some fun.