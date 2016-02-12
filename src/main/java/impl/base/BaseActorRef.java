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

package actors.impl.base;

import actors.ActorRef;
import actors.ActorSystem;
import actors.Message;
import actors.Actor;
import actors.impl.base.SendingThread;

import actors.exceptions.ShouldNotHappenException;

public class BaseActorRef<T extends Message> implements ActorRef<T>
{
    public Actor getActor() { return getActorSystem().getActor(this);  }
    public BaseActorSystem getActorSystem() { return getActor().getActorSystem(); }

    @Override
    public void send(Message message, ActorRef to) 
    { 
        Actor actor_to = actor_system.getActor(to);
        SendingThread sending_thread=new SendingThread(message,actor_to);
        Thread t = new Thread( sending_thread );
        t.start();
    }

    @Override
    public int compareTo(ActorRef other) { return getActorSystem().compareRefs(this,other); }
}
