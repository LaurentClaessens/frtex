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

import actors.Message;
import actors.AbsActor;

public class SendingThread implements Runnable
{
    private ActorRef actor_ref_to;
    private Message message;
    private ActorSystemImpl actor_system;

    public SendingThread(Message m, ActorRef t,ActorSystemImpl as) 
    {
        message=m;
        actor_ref_to=t;
        actor_system=as;
    }
    public void run() 
    {
        AbsActor actor_to = actor_system.getActor(actor_ref_to);
        actor_to.putInMailBox(message); 
    }
}
