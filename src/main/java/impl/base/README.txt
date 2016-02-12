The actors.impl.base package contains a minimal implementation of the actor system that is devoted to pass the unipd tests.

For a real live actor system mechanism, you should derive from actors.impl.decent which is a more decent implementation.

REFERENCE TO THE ACTOR SYSTEM

BaseActor has a reference to the actor system that created him.
BaseActorSystem   getActorSystem()
