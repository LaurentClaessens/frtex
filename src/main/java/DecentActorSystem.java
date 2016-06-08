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

import java.util.Collection;
import java.util.Set;

import actors.Message;
import actors.ActorMap;
import actors.DecentActorRef;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.IllegalModeException;
import actors.exceptions.NoSuchActorException;
import actors.exceptions.UnsupportedMessageException;

public abstract class DecentActorSystem
{
    private Integer created_serie_number;
    private Class accepted_type=Message.class;
    private ActorMap actors_map;

    public DecentActor getActor(DecentActorRef reference) 
    {
        return (DecentActor) actors_map.getActor(reference); 
    } 
    protected void setActor(DecentActorRef ref,DecentActor actor) 
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
    public void setUpActor(DecentActorRef actor_ref,DecentActor decent_actor)
    // link the actor reference 'actor_ref' to the actor 'actor'
    {
        decent_actor.setSelf(actor_ref);
        decent_actor.setAcceptedType(accepted_type);
        actor_ref.setActorSystem(this);
        actors_map.put(actor_ref,decent_actor); 
        synchronized(created_serie_number)
        { 
            decent_actor.setSerieNumber(newSerieNumber());  
        }
    }
    public DecentActorRef actorOf(Class actor_type,ActorMode mode)
    {
        throw new ShouldNotHappenException("Use createPair instead.");
    }

    public void send(Message message, DecentActorRef ref_to)
    {
        if (!isActive(ref_to)) { throw new NoSuchActorException(); }
        if ( !getActor(ref_to).getAcceptedType().isInstance(message)  )
        {
            Class at = getActor(ref_to).getAcceptedType();
            throw new UnsupportedMessageException(message);
        }
        SendingThread sending_thread=new SendingThread(message,ref_to,this);
        Thread t = new Thread( sending_thread );
        t.start();
    }
    public void stop(DecentActorRef actor_ref)
    {
        if (isActive(actor_ref)){  setActive(actor_ref,false) ;}
        else { throw new NoSuchActorException("This actor is not active anymore.");  }
    }
    public void stop() 
    {
        for (DecentActorRef act_ref : actors_ref_list())
        {
            stop(act_ref);
        }
    }
    private Set<DecentActorRef> actors_ref_list() 
    {
        return actors_map.actors_ref_list();
    }
    private Collection<DecentActor> actors_list() 
    {
        return actors_map.actors_list();
    }
    public abstract DecentActorRef createPair();
    public enum ActorMode {
        LOCAL,
        REMOTE
    }
} 
