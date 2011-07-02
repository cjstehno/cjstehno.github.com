---
title: More Fun with the Jukebox
layout: default
--- # Categories
- android
- groovy
- servers
- technology
- thoughts
- tidbits
---

I wanted to post a quick follow-up about the <a href="http://coffeaelectronica.com/blog/2010/10/from-junk-box-to-jukebox-in-a-couple-hours/">home jukebox server</a> I detailed recently. I have done my power usage testing and here is what I got

<blockquote>Between 10/22/2010 0654 and 10/16/2010 1010 the server used 6.38 kilowatt-hours.</blockquote>

After some quick Groovy-based calculation:

<code lang="groovy">
def kwh = 6.38, kwhcost = 0.12
def duration /* days */ = (((time('10/22/2010 06:54') - time('10/16/2010 10:10')) / 1000) / 3600) / 24
def costPerDay = (kwh * kwhcost)/duration
def costPerMonth = costPerDay * 30
def time( d ){ new java.text.SimpleDateFormat('MM/dd/yyyy HH:mm').parse( d ).getTime()}
def money( m ){ new java.text.DecimalFormat('$0.00').format( m )}
println "${money(costPerDay)}/day ==> ${money(costPerMonth)}/month"
</code>

I found that the server uses roughly $0.13/day or $3.93/month on average based on the time duration I tested in which the server was under normal usage. Obviously, this number is very dependent on the system you build your server with. Given some of the new Atom-based boxes that are available, you could probably really cut that cost down even more.

If you consider that most hosting with any useful functionality is at least $3-5 per month, and a lot of the cloud-based services that are coming out are in the range of $5-10 per month... $3-5 per month for your own server with unlimited possibilities is not bad at all. Also there is one cost-cutting measure you can do with your own server that you cannot do with hosting... turn it off.

Finally, I also found a useful tool for working with the server. If you have an Android phone you can install <a href="http://code.google.com/p/connectbot/">ConnectBot</a> which gives you SSH shell access to your server. I don't have my SSH port forwarded from the router so this only works inside my home network via wireless, but ConnectBot makes it very easy to check on the status of the box, do a shutdown or restart the web server without having to fire up your desktop box.

