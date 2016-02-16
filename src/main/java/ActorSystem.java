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

/**
 * The system of actors. Using the system it is possible to:
 *      - Create a new instance of an actor</li>
 *      - Stopping an actor</li>
 */
public interface ActorSystem {

    /**
     * Create an instance of {@code actor} returning a {@link ActorRef reference} to it using the given {@code mode}.
     *
     * @param actor The type of actor that has to be created
     * @param mode The mode of the actor requested
     *
     * @return A reference to the actor
     */
    ActorRef<? extends Message> actorOf(Class<? extends Actor> actor, ActorMode mode);

    /**
     * Create an instance of {@code actor} that executes locally.
     *
     * @param actor The type of actor that has to be created
     * @return A reference to the actor
     */
    ActorRef<? extends Message> actorOf(Class<? extends Actor> actor);

    /**
     * Stops {@code actor}.
     *
     * @param actor The actor to be stopped
     */


    // Return the actor corresponding to the given reference.
    Actor getActor(ActorRef actor);

    void stop(ActorRef<?> actor);

    /**
     * Stops all actors of the system.
     */
    void stop();

    /**
     * Possible modes to create an actor. {@code LOCALE} mode is used to create an actor
     * that acts in the local system. {@code REMOTE} mode is used to create remote actors.
     */
    enum ActorMode {
        LOCAL,
        REMOTE
    }
}
