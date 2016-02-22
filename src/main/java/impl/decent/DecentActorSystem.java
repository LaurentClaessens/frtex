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

import java.util.Collection;
import java.util.Set;

import actors.Message;
import actors.Actor;
import actors.ActorMap;
import actors.ActorSystemImpl;
import actors.ActorRefImpl;
import actors.ActorSystem.ActorMode;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.IllegalModeException;

/*
 About newDecentActorRef
This function cannot be named 'actorOf()'. When EchoActorSystem::actorOf(2-parameters)
 calls the actorOf(2-parameters) here, the latter should call 'actorOf()', but
that would call the Echo's actorOf() and creates circular calls ending in a
StackOverflow.
//*/

public abstract class DecentActorSystem extends ActorSystemImpl
{
    private Integer created_serie_number;
    private Class accepted_type=Message.class;
    private ActorMap actors_map;

    public DecentAbsActor getActor(DecentActorRef reference) 
    {
        return (DecentAbsActor) actors_map.getActor(reference); 
    } 
    protected void setActor(DecentActorRef ref,DecentAbsActor actor) 
    { 
        actors_map.put(ref,actor); 
    }

    protected final DecentActorRef createActorReference(ActorMode mode) throws IllegalModeException
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new IllegalModeException(mode);
        }
        DecentActorRef actor_ref = new DecentActorRef();
        return actor_ref;
    }

    private Boolean isActive(DecentActorRef ref) { return actors_map.isActive(ref);  }
    private void setActive(DecentActorRef ref,Boolean b) { actors_map.setActive(ref,b);  }

    public Integer newSerieNumber()  { return ++created_serie_number;  }
    public DecentActorSystem(Class t) 
    {
      super(); 
      accepted_type=t;
      created_serie_number=-1;
      actors_map=new ActorMap();
    }
    public void setUpActor(DecentActorRef ref,DecentAbsActor actor)
    {
        super.setUpActor(ref,actor);
        actor.setAcceptedType(accepted_type);
        synchronized(created_serie_number)
        { 
            actor.setSerieNumber(newSerieNumber());  
        }
    }
    public DecentActorRef newDecentActorRef(Class<? extends Actor> actor_type)
    {
        DecentActorRef decent_ref = (DecentActorRef)  super.actorOf(actor_type,ActorMode.LOCAL);

        DecentAbsActor decent_actor = decent_ref.getActor();
        setUpActor(decent_ref,decent_actor);
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
