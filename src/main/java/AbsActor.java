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

import actors.exceptions.UnsupportedMessageException;

/**
 * Defines common properties of all actors.
*/
public abstract class AbsActor<T extends Message> implements Actor<T> 
{

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;
    protected Class<Message> accepted_type;

    protected void setAcceptedType(Class<Message> t) { accepted_type=t; }

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) 
    {
        this.self = self;
        return this;
    }
    abstract void do_receive(Message message);
    public void receive(Message m)
    {
        if (accepted_type.isInstance(m)) { do_receive(m); }
        else { throw new UnsupportedMessageException(m);  }
    }
}
