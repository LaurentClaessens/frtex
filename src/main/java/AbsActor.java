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

import actors.exceptions.ShouldNotHappenException;

/**
 * Defines common properties of all actors.
*/
public abstract class AbsActor<T extends Message> implements Actor<T> 
{

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;
    protected Class accepted_type;
    protected MailBox<T> mail_box;

    protected void setAcceptedType(Class<Message> t) { accepted_type=t; }
    
    protected AbsActor()
    {
        mail_box = new actors.MailBox<T>();
    }
    public MailBox<T> getMailBox() {return mail_box;}

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
    public abstract void receive(T m);
    public void send(T m, ActorRef to)
    {
        if (accepted_type.isInstance(m)) 
        {
            to.getActor().receive(m); 
        }
        else { throw new ShouldNotHappenException("Trying to send a message of wrong type. Your actor implementation should not have such evil plans.");}
    }
    public void stop() { getMailBox().close(); }
}
