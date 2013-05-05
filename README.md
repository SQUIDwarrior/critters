critters
========

This is my implementation of a game called Critters that one of my college professors used in a CS class. 

What is it?
===========

Critters is about death, rebirth, and watching tiny little AIs run around and eat each other.

Ok...
=====

Basically, Critters is an AI simulation that places a group of creatures called "critters" in an arena and has them 
fight to the...er compete...for dominance! Whichever critter takes over the board, wins.

How does it work?
=================

Each Critter is represented by a single character, such as 'P', that is assigned a random position on the board. Critters 
can perform a single move per "round" (a round is like a frame in a video): Turn right, turn left, hop (move forward one 
space), or infect.  Critters also have a limited sense of what is around them, and what direction they are facing (north,
south, east, and west). They know what is one space in front, behind, and to the left and right of them. They also know
what type of thing is near them (empty, other, self, or wall).

Did you say "infect"?
=====================

Yes. It-

Gross.
======

Ok, not the best word. But it's how Critters replicate themselves. As previously mentioned, one of the Critter's actions 
is to "infect". If a Critter is facing another critter of a different type ("other"), it can "infect" that critter, 
turning it into a clone of itself. Infecting a critter of the same type (or the wall) has no effect. It also means that 
critter can't take another action that round.

Can I create new Critters?
==========================

Yes! All Critters implement the abstract class Critter, with has three abstract methods to override to define a critter:

````java  
public abstract Action getAction();
````
This is where the magic happens. Here you define your Critter's behavior by returning an Action, based on the information
you have about your surroundings.

````java
public abstract char getSymbol();
````
This is the character that represents your Critter on the board.

````java
public abstract String getDescription();
````
This is simply a description of your Critter that appears on the configuration panel.

Once you've done that, all you have to do is place your Critter in the "critters.type" package. It will show up as an 
option in the configuration GUI.

How do I run it?
================

I haven't made a good build/run script yet so for now just download the source, compile it, and then run the 
critters.gui.CrittersMain class.

Bleh! This code is AWFUL!
=========================

Yes I know. I wrote it in afternoon 5 years ago at my girlfriend's (now wife) apartment while I watched a Terminator 
marathon on HBO. So cut me some slack.

But what is this "genetics" package, or this "smellField" thingy?
=================================================================

Well, those are features I haven't finished yet. The first is an attempt to toy with some genetic programming, and the 
second was to add a sense of smell to the Critters. Both works in progress.
