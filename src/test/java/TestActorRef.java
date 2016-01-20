/*
Copyright 2016 Laurent Claessens
contact : moky.math@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/

package actors;

import actors.*;

/**
 * Decorates an {@link ActorRef} adding the ability to get the underlying actor associated to the reference.
 *
 */
public class TestActorRef<T extends Message> implements ActorRef<T> {

    private ActorRef<T> reference;

    public TestActorRef(ActorRef<T> actorRef) {
        this.reference = actorRef;
    }

    /**
     * Returns the {@link Actor} associated to the internal reference.
     * @param system Actor system from which retrieving the actor
     *
     * @return An actor
     */
    public Actor<T> getUnderlyingActor(ActorSystem system) {
        // TODO To implement
        return null;
    }

    @Override
    public void send(T message, ActorRef to) {
        reference.send(message, to);
    }

    @Override
    public int compareTo(ActorRef o) {
        return reference.compareTo(o);
    }

    @Override
    public boolean equals(Object obj) {
        return reference.equals(obj);
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }
}
