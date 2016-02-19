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
import actors.impl.base.BaseActorSystem;
import actors.impl.base.BaseActorRef;
import actors.ActorSystem.ActorMode;
import actors.exceptions.ShouldNotHappenException;


public abstract class DecentActorSystem extends BaseActorSystem
{
    private Integer created_serie_number;
    private Class accepted_type=Message.class;

    public Integer newSerieNumber()  { return ++created_serie_number;  }
    public DecentActorSystem(Class t) 
    {
      super(); 
      accepted_type=t;
      created_serie_number=-1;
    }
    public DecentActorSystem()
    {
        System.out.println("This zero-parameter constructor is only for UNIPD tests purpose. Must not be used in real live.");
    }

    // This function cannot be named 'actorOf()'. When EchoActorSystem::actorOf(2-parameters)
    // calls the actorOf(2-parameters) here, the latter should call 'actorOf()', but
    // that would call the Echo's actorOf() and creates circular calls ending in a
    // StackOverflow.
    public DecentActorRef newDecentActorRef(Class<? extends Actor> actor_type)
    {
        DecentActorRef decent_ref = ((BaseActorRef)  super.actorOf(actor_type,ActorMode.LOCAL)).upgradeToDecentActorRef();

        DecentAbsActor decent_actor = decent_ref.getActor();
        decent_actor.setAcceptedType(accepted_type);
        synchronized(created_serie_number)
        { 
            decent_actor.setSerieNumber(newSerieNumber());  
        }
        return decent_ref;
    }
    @Override
    public DecentActorRef actorOf(Class<? extends Actor> actor_type,ActorMode mode)
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new ShouldNotHappenException("Only local actors are implemented.");
        }
        return newDecentActorRef(actor_type);
    }
}
