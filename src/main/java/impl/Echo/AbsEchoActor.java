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

import actors.AbsActor;
import actors.EchoText;
import actors.ActorRef;
import actors.Message;

public abstract class AbsEchoActor extends AbsActor<EchoText>
{

    private actors.MailBox mail_box = new actors.MailBox();
    private EchoActorRef myReference;

    private EchoActorRef getActorRef() { return myReferece;  }

    public abstract  void process(EchoText m);
    private void process_next_message()
    {
        if ( mail_box.size()>0 )
        {
            synchronized(mail_box) { actors.Message m=mail_box.poll(); }
            process(m);   
        }
    }
    public void receive(EchoText message)
    {
        mail_box.add(message);
        process_next_message();
    }
    public void receive(Message m)
    {
        throw UnsupportedMessageException;
    }

    public void send(actors.Message   m, ActorRef to) 
    {
        getActorRef().send(m,to); 
    }
}

