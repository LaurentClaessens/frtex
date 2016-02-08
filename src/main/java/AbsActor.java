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
    protected Class accepted_type;
    protected MailBox<T> mail_box;
    private  AbsActorSystem actor_system;

    protected AbsActor() { mail_box = new actors.MailBox<T>(); }
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
    private void processNextMessage()
    {
        if ( mail_box.size()>0 )
        {
            Mail<T> mail;
            synchronized(this) //mail_box,sender
            {
                mail=mail_box.poll(); 
                sender=mail.getSender();
            }
            T m = mail.getMessage();
            receive(m);
        }
    }
    public void putInMailBox(Message message)
    {
        System.out.println("ooVVZMooHFBuoQ "+message.getClass().getSimpleName()+" comparé à "+accepted_type);
        System.out.println("MinAbsActor::receive  1");
        if (accepted_type.isInstance(message)) 
        { 
            T m=(T) message;
            Mail<accepted_type> mail=Mail(m,self);
            synchronized(mail_box) { mail_box.add(mail);}
            processNextMessage();
        }
        else { throw new UnsupportedMessageException(message);  }
    }
    public void send(T m, ActorRef to)
    {
        if (accepted_type.isInstance(m)) 
        {
            actor_system.getActor(to).putInMailBox(m); 
        }
        else { throw new ShouldNotHappenException("Trying to send a message of wrong type. Your actor implementation should not have such evil plans.");}
    }
    public void stop() { getMailBox().close(); }
}
