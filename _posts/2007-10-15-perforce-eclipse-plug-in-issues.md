---
title: Perforce Eclipse Plug-in Issues
layout: default
--- # Categories
- eclipse
- perforce
- programming
---

The <a href="http://perforce.com">Perforce</a> integration plug-in for <a href="http://eclipse.org">Eclipse</a> seems to have some issues with Eclipse 3.3.x, which just came out a few weeks ago.

I have been using 3.3 since it was released along with the Perforce (most current version) plug-in, which I have been using for quite a while with no issues. After the upgrade, other developers I work with and I have started to have odd crashes. Eclipse would be working fine and then just die. The only things I could see in the log file (workspace/.meta/.log) were related to the perforce plug-in. The worst part was that the workspace became unloadable even when the .lock file was removed.

After fiddling around with it and still having issues. I upgraded to a clean Eclipse 3.3.1 installation with a minimum of extra plug-ins (I tend to accumulate plug-ins). I even shut down unused plug-ins that come with Eclipse (as a side note, this makes Eclipse run faster). I was able to work for about a week before it crashed again.

I found out that if you remove the perforce feature directory and plugins directories (not the top-level, but those specific to perforce) you can gain access to the workspace. From there you can reinstall the plug-in from the update manager and at least use it again until it crashes.

I submitted a bug report to Perforce and they said that they are already on the issue and that a new version with the fix will be coming out before the end of the year… hopefully a little sooner than that, but I know how software cycles can go. :-)

<b>Another work-around</b> - 11/01/2007

My contact at Perforce also told me that you can open a different workspace, disable the perforce plugin, restart in the desired workspace and then enable the plugin again. This seems to work as well and it is a little faster turn around time.

<b>New Version in Beta</b> - 2/13/2008

It looks like they have a new version currently in beta. Oddly enough I have found that if I create your project completely in Eclipse 3.3.x rather than importing an a project from an older Eclipse version, it works with no problems.

A friend of mine who now works for a different company actually had some face time with the Perforce plug-in developers and (on my request) asked them about whether there was any chance of them open-sourcing the plug-in. The answer was “no” due to some proprietary code in the plug-in. Oh well it was worth a try.


