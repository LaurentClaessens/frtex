# actors

It provides an actor system and an implementation that read a LaTeX file and return a new LaTeX file in which the "\input" are (recursively) replaced by the content.
(this is under development)

## General classes

* `Message`
* `Mail`
* `MailBox`

## An abstract implementation : the Decent actor system.

The types are

* `DecentActorSystem` its special features are :
    * `void setUpActor(DecentActorRef,DecentActor)`. Give to the two references the attribute they need and associate them in the actor map.
    * `DecentActorRef createPair()` (abstract). This function has to be overridden in the extensions. The override has to create a pair actor/actor reference and call `setUpActror` with these two as arguments.
* `DecentActorRef`
* `DecentAbsActor` its special features are :
    * __reference to the actor system__ you get the actor system with `public DecentActorSystem getActorSystem()`.
    * __accepted type__ Each actor has its own accepted type of message. By default it is the one of the system, but is can be set to any other class. No check is done, but the accepted message type of one actor is supposed to be a subclass of the accepted message type of the system. 

    We have the following two methods :
        * `void setAcceptedType()` that should be used only one. During the creation process of the actor. See the method `setUpActor`.
        * `Class getAcceptedType()`.
        
    * __name/serie number__ Each actor has a name with the usual method `String getName()`. The default name is based on the creation ordering (zero for the first, and so on). The serie number is private.

```java
ActorSystem getActorSystem()
```
returns the actor system which created him.


## Next implementation : your actor system

In order to make the things clearly you should create your own actor system by derivation from the "decent" actor system. You should create the following classes
* `yourActor`
* `yourActorRef`
* `yourActorSystem`

### Some override that you should do

* In the class `yourActorSystem` 

```java
public void setUpActor(yourActorRef ref,yourActor act)
{
    super.setUpActor(ref,act);
    // give here to ref and act the properties they need
}
```

* In the class `yourActorSystem` 

```java
public yourActorRef createPair()
{
    yourActorRef reference = new yourActorRef();
    yourActor actor = new yourActor();
    setUpActor(reference,actor);
    return reference;
}
```

* In the class `yourActorSystem` 

```java
@Override
public  yourActor getActor(DecentActorRef reference) { return (yourActor) super.getActor(reference); }
```
The cast should work because the actor has a reference to its actor system. Thus only actors build from your actor system should get into that method.

* In the class `yourActor` 

```java
@Override
public  YourActorRef getSelfReference() { return (YourActorRef) super.getSelfReference() ; }
```

* In the class `yourActor` 

```java
@Override
public LatexActorSystem getActorSystem() { return (LatexActorSystem) super.getActorSystem(); }
```

* In the class `yourActorRef` 

```java
@Override                                   
public LatexActor getActor() { return (LatexActor) super.getActor(); }                                           
```

* In the class `yourActor`
```java
    @Override
    public void receive(Message m)
    {
        super.receive(m);
        // do whatever your actor should do.
    }
```


## The Latex actor system

The LaTeX implementation uses the actor system in order to, being given a LaTeX file as input, produces the LaTeX code with all the \input done (recursively). 
    This is still under development.

The "latex actor system" is intended to take as input a LaTeX filename and produce as output the "recursive" content of that file with all the \input{file} explicitly replaced by the content of "file.tex".

### How it works

The behaviour of an actor is :

- read its LaTeX file
- create a list of the inputed files.
- for each of them, ask an other actor to make the job.
- when all the answers are received, recompose the LaTeX file
- send the result to the actor whose asked.

### What you need to know

The only class you have to work with is `LatexCode`. That class provides an abstraction that hides the actor system to the user.

### Working actor vs. Active actor

A `LatexActor` can be active or not, as any actor. The `LatexActor` has an other status that is "working" or not.

An actor which is requested to create the code of the file "foo.tex" reads this file and send a request message to a new actor each time that it encounters a "\input" in "foo.tex". Such an actor has to be able to read new messages since it relies on the answer messages in order to complete its work.

So such a working actor is set "inactive" in order to unlock its mail box. But it is still working and cannot be requested to work on an other tex file until "foo.tex" is completed and sent to the calling actor.

The `LatexActorSystem` has a method 

```java
public LatexActorRef getNonWorkingActor()
```
which return an actor reference to an actor who can be requested to deal with a new tex file. The actor is 'working' from the moment it is returned.

### Latex message

The latex actor system recognize two types of messages.

* `LatexMessage` (abstract)
* `LatexRequestMessage` (extends `LatexMessage`)
* `LatexAnswerMessage` (extends `LatexMessage`)

### Hypothesis on the LaTeX source code (simplification)

* The filenames are more or less standard. Like only one dot, no curly braces and so on.

* The percent symbol should always mean a comment, with the exception of "\%". This can be a limitation if you have URL in which you substituted special characters with their %xx representation.

* We suppose that each .tex file in included only once. Thus if foo.tex needs bar.tex, it is impossible that bar.tex was already processed when the actor in charge of foo.tex initiate its work.

    In this case, the answer can be sent directly to the asking actor. 
    If a tex file could be included more than once, we should maybe use a principal actor.


## The Echo actor system

It is only for testing purpose. The system manage the message type "EchoText".

### EchoText (the message type)

The `EchoText` type has two subtypes `EchoTextOne` and `EchoTextTwo` that only exist for testing purpose of the `accepted_type` system.

### Special features
* `EchoActor` has a reference to the __last message__


### TODO

* The actor reference often calls its actor in order to answer questions like the accepted type or the actor system. One should memoize them.
* There is duplication of code between the `<Foo>impl` and the `Base` implementations. In particular, between `BaseActorMap` and `ActorMap`.

### HISTORY

The skeleton is from [rcardin](https://github.com/rcardin/pcd-actors), but I got quite far away from his architecture. For example

* I removed the use of generics and add a class variable `accepted_type`
* I removed the base 'abstract' first implementation.
* I removed the interfaces

### TEST

use `mvn test` to see the result.
