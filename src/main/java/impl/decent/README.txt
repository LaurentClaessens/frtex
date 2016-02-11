The "decent" implementation of the actor system is based on the "Impl" implementation and adds the class variable "accepted type".

Each actor has its own accepted type of message. By default it is the one of the system, but is can be set to any other class. No check is done, but the accepted message type of one actor is supposed to be a subclass of the accepted message type of the system.
