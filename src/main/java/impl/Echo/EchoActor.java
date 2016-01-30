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
    
    private ActorRef getActorRef() { return self; }

    public EchoActor() 
    {
        super();
        accepted_type=EchoText.class;
    }

    @Override
    public void processMessage(EchoText m)
    {
        EchoText message = (EchoText) m;
        EchoThreadProcessing processing_thread=new EchoThreadProcessing(message,getActorRef(),m.getSender());
        Thread t = new Thread(processing_thread);
        t.start();
    }
}
