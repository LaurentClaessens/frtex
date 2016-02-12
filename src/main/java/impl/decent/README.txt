The "decent" implementation of the actor system is based on the "Impl" implementation and adds some features that are needed to decently work.

ACCEPTED TYPE

Each actor has its own accepted type of message. By default it is the one of the system, but is can be set to any other class. No check is done, but the accepted message type of one actor is supposed to be a subclass of the accepted message type of the system.

NAME / SERIE NUMBER

Each actor has a name with the usual method
String getName()
The default name is based on the creation ordering (zero for the first, and so on). The serie number is private.


REMARK 

The "Impl" already contains a reference to the actor system.
