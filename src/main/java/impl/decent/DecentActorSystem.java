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

package actors.impl.decent;

import actors.Message;
import actors.Actor;
import actors.impl.ActorSystemImpl;
import actors.ActorSystem.ActorMode;
import actors.exceptions.ShouldNotHappenException;


public abstract class DecentActorSystem extends ActorSystemImpl
{
    private Integer created_serie_number;
    private Class accepted_type=Message.class;

    public Integer newSerieNumber()  { return ++created_serie_number;  }
    public DecentActorSystem(Class t) 
    {
      super(); 
      accepted_type=t;
    }
    @Override
    public DecentActorRef actorOf(Class<? extends Actor> actor,ActorMode mode)
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new ShouldNotHappenException("Only local actors are implemented.");
        }
        return actorOf();
    }
    public DecentActorRef actorOf()
    {
        DecentActorRef ar = (DecentActorRef) super.actorOf(accepted_type,ActorMode.LOCAL);
        DecentAbsActor actor = ar.getActor();
        actor.setAcceptedType(accepted_type);
        synchronized(created_serie_number){ actor.setSerieNumber(newSerieNumber());  }
        return ar;
    }
}
