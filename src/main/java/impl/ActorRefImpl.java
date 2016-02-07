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

package actors.impl;

import actors.ActorRef;
import actors.ActorSystem;
import actors.Message;
import actors.Actor;
import actors.impl.SendingThread;

import actors.exceptions.ShouldNotHappenException;

public class ActorRefImpl<T extends Message> implements ActorRef<T>
{
    private ActorSystemImpl actor_system;
    private Integer serie_number;

    public void setActorSystem(ActorSystemImpl as) { actor_system=as;  }
    public void setSerieNumber(Integer n) { serie_number=n;  }

    public ActorRefImpl(ActorSystemImpl ac,Integer number) 
    { 
        actor_system=ac; 
        serie_number=number;
    }
    public String getName() { return serie_number.toString(); }
    public Actor getActor() { return getActorSystem().getActor(this);  }
    public ActorSystemImpl getActorSystem() { return actor_system; }

    @Override
    public void send(Message message, ActorRef to) 
    { 
        System.out.println("ooNEJRooINinYh 1 "+to);
        Actor actor_to = actor_system.getActor(to);
        SendingThread sending_thread=new SendingThread(message,actor_to);
        Thread t = new Thread( sending_thread );
        t.start();
    }
    public Integer getSerieNumber() { return serie_number;  }

    @Override
    public int compareTo(ActorRef other) { return getActorSystem().compareRefs(this,other); }
}
