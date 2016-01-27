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

// An 'EchoActor' is an actor that does -1 on the data and resent the message if the data is still positive.

package actors.impl.Echo;

import actors.AbsActor;
import actors.ActorRef;
import actors.Message;
import actors.MailBox;



public class EchoActor extends AbsActor<EchoText>
{
    
    private actors.MailBox<EchoText> mail_box = new actors.MailBox<EchoText>();
    private EchoActorRef myReference;
    private EchoActorRef getActorRef() { return myReference;  }
    EchoActor() {accepted_type=EchoText.class;}

    private void process_next_message()
    {
        if ( mail_box.size()>0 )
        {
            EchoText m;
            synchronized(mail_box) { m=mail_box.poll(); }
            process(m);   
        }
    }
    @Override
    public void do_receive(Message message)
    {
        synchronized(mail_box) { mail_box.add( (EchoText) message);}
        process_next_message();
    }
    @Override
    public  void send(EchoText m, ActorRef to) 
    {
        getActorRef().send(m,to); 
    }

    public MailBox getMailBox() { return mail_box;  }

    protected void process(EchoText m)
    {
        Integer data=m.getData()-1;
        if (data > 0)
        {
            EchoText new_message = new EchoText(getActorRef(),m.getSender(),data);
            getActorRef().send(new_message,getActorRef());
        }
    }
}
