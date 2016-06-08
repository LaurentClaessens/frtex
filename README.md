# actors

It provides an actor system and several implementations.

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

The class `yourActorSystem` should contain the following methods :

```java
public void setUpActor(yourActorRef ref,yourActor act)
{
    super.setUpActor(ref,act);
    // give here to ref and act the properties they need
}
```
and
```java
public yourActorRef createPair()
{
    yourActorRef reference = new yourActorRef();
    yourActor actor = new yourActor();
    setUpActor(reference,actor);
    return reference;
}
```

Moreover the method `getActor` should be overridden as
```java
@Override
public  yourActor getActor(DecentActorRef reference)
{
    return (yourActor) super.getActor(reference);
}
```
The cast should work because the actor has a reference to its actor system. Thus only actors build from your actor system should get into that method.

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

### Special feature

The actor system need more functionalities than the basic one.

- A method `getFreeActor` that return an actor reference to an actor that is not working (in the sense that he has already send its result). If no free actors are available, create a new one.

### Hypothesis on the LaTeX source code (simplification)

* For the sake of simplicity, it assumes that all inputs are on lines that _begin_ with \input.


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

