# actors

This is the java project for the cours at unipd. The skeleton is from rcardin[1].

It provides an actor system and several implementations.

## First implementation stage : `Abs<Foo>` and `<Foo>Impl` (non abstract)

These are the files that implement the very minimal actor system devoted to pass the unipd requirements. The root of the distinction between this implementation and `impl.base` is a name clash : the name `ActorRef` is imposed by unipd and is an interface. I'd like to name `ActorRef` the minimal non-abstract implementation of `AbsActorRef`, but I can't.

Thus the first stage of implementation have some strange naming and is decomposed into `Abs<Foo>` and `<Foo>Impl`.

Its special feature is

    BaseActorSystem getActorSystem()

that returns the actor system which created him.


## Second implementation stage : the Base actor system (abstract)

This is the first stage of my own implementation that will be useful for more purposes than passing the unipd's tests. It mainly serves as a wrapper around the preceding one in order to get rid of naming problems.

For a real live actor system mechanism, you should derive from actors.impl.decent which is a more decent implementation.

## Third implementation stage : the Decent actor system (abstract)

The "decent" implementation of the actor system is based on the "base" implementation and adds some features that are needed to decently work. This is the only stage you really worry about when you want to use this actor model.

### accepted type

Each actor has its own accepted type of message. By default it is the one of the system, but is can be set to any other class. No check is done, but the accepted message type of one actor is supposed to be a subclass of the accepted message type of the system.

### name / serie number

Each actor has a name with the usual method

    String getName()

The default name is based on the creation ordering (zero for the first, and so on). The serie number is private.


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

It is only for testing purpose. The system manage the message type "EchoText" whose has two subtypes "EchoTextOne" and "EchoTextTwo". They only exist in order to test the "accepted_type" system.


# TODO

* The actor reference often calls its actor in order to answer questions like the accepted type or the actor system. One should memoize them.
* There is duplication of code between the `<Foo>impl` and the `Base` implementations. In particular, between `BaseActorMap` and `ActorMap`.


[1] https://github.com/rcardin/pcd-actors
