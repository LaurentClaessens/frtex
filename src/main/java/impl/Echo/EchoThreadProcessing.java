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

package actors.impl.Echo;

import actors.ActorRef;
import actors.Message;

// Arguments are :
// - message that has to be EchoText
// - actor (reference) that has to process
// - actor (reference) sender
// Then makes the process of the message for the given actor.

public class EchoThreadProcessing implements Runnable
{
    private ActorRef actor_making;
    private ActorRef sender;
    private EchoText message;

    EchoThreadProcessing(Message m, ActorRef t,ActorRef s) 
    {
        message=(EchoText)  m;
        actor_making=t;
        sender=s;
    }
    public void run() 
    {
        Integer data=message.getData()-1;
        System.out.println("Nombre : "+data.toString());
        if (data > 0)
        {
            EchoText new_message = new EchoText(actor_making,sender,data);
            actor_making.send(new_message,sender);
        }
    }
}
