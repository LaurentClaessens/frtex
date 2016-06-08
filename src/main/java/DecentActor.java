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
import actors.DecentActorSystem;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.UnsupportedMessageException;

public abstract class DecentActor
{

    private String my_name;
    private Integer serie_number;
    protected DecentActorRef self;         // self-reference
    protected Class accepted_type=Message.class;
    protected MailBox mail_box;
    private  DecentActorSystem actor_system;
    protected DecentActorRef sender;   // sender of the being processed message

    protected DecentActor() 
    { 
        mail_box = new actors.MailBox(); 
        mail_box.setAcceptedType(accepted_type);
    }
    public String getName() {return my_name;}
    public MailBox getMailBox() {return mail_box;}
    public Class getAcceptedType() {return accepted_type; }
    public void setAcceptedType(Class t) {accepted_type=t;}
    public void setSerieNumber(Integer n) {serie_number=n;}
    public Integer getSerieNumber() {return serie_number;}

    public void setActorSystem(DecentActorSystem as) { actor_system=as; }
    public DecentActorSystem getActorSystem() { return actor_system;  }

    protected final DecentActor setSelf(DecentActorRef self) 
    {
        this.self = self;
        return this;
    }
    public abstract void receive(Message m);
    private void processNextMessage()
    {
        Mail mail;
        synchronized(this) //mail_box,sender
        {
            if ( mail_box.size()>0 )
            {
                mail=mail_box.poll(); 
                sender=mail.getSender();
                receive( mail.getMessage()  );
            }
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
            throw new UnsupportedMessageException(message);
        }
    }
    public void send(Message message, DecentActorRef to)
    {
        DecentActor actor_to = getActorSystem().getActor(to);
        if (!actor_to.getAcceptedType().isInstance(message)) 
        {
            throw new UnsupportedMessageException(message);
        }
        actor_to.putInMailBox(message); 
    }
    public void stop() { 
        actor_system.stop(self);
    }
}
