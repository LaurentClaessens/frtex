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

import actors.Mail;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.UnsupportedMessageException;

// accepted_type has default value 'Message', so that no verification is done.
// Rationale :
// Since the type of message that the actor has to deal with is only given as generic type variable, I guess that it is impossible to perform the verification.
// The implementation 'DecentAbsActor' has a method "setAcceptedType" that allows the user to set the type of message to be accepted.
// See also :
// http://stackoverflow.com/questions/34989911/java-do-something-if-implementation-t-of-base-and-something-else-if-any-othe

public abstract class AbsActor<T extends Message> implements Actor<T> 
{

    protected ActorRef<T> self;         // self-reference
    protected Class accepted_type=Message.class;
    protected MailBox<T> mail_box;
    private  ActorSystemImpl actor_system;
    protected ActorRef<T> sender;   // sender of the being processed message

    protected AbsActor() 
    { 
        mail_box = new actors.MailBox<T>(); 
    }
    public MailBox<T> getMailBox() {return mail_box;}
    public Class getAcceptedType() {return accepted_type; }

    public void setActorSystem(ActorSystemImpl as) { actor_system=as; }
    public ActorSystemImpl getActorSystem() { return actor_system;  }

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
            Mail mail;
            synchronized(this) //mail_box,sender
            {
                mail=mail_box.poll(); 
                sender=mail.getSender();
            }
            T m=(T) mail.getMessage();
            String name = Thread.currentThread().getName();
            receive(m);
        }
    }
    public void putInMailBox(Message message)
    {
        if (getAcceptedType().isInstance(message)) 
        { 
            Mail mail=new Mail(message,self);
            synchronized(mail_box) { mail_box.add(mail);}
            processNextMessage();
        }
        else 
        { 
            System.out.println("AbsActor::putInMailBox -- ne devrait pas.");
            throw new UnsupportedMessageException(message);
        }
    }
    public void send(T message, ActorRef to)
    {
        AbsActor actor_to = getActorSystem().getActor(to);
        if (!actor_to.getAcceptedType().isInstance(message)) 
        {
            throw new UnsupportedMessageException(message);
        }
        actor_to.putInMailBox(message); 
    }
    public void stop() { getMailBox().close(); }
}
