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
 * An actor in the <code>pcd-actor</code> system that receives
 * messages of a defined type.
 */

public interface Actor<T extends Message> {

    /**
     * Defines the interface of the actor.
     *
     * @param message The type of messages the actor can receive
     * @throws it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException If the message is not supported by
     *         the actor.
     */
    void receive(T message);
}
