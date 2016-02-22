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

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

import actors.ActorMap;
import actors.exceptions.NoSuchActorException;
import actors.exceptions.IllegalModeException;
import actors.exceptions.ShouldNotHappenException;

public class ActorSystemImpl extends AbsActorSystem {

    private ActorMap actors_map;
    public ActorSystemImpl() { actors_map=new ActorMap(); }

    public AbsActor getActor(ActorRef reference) 
    {
        return actors_map.getActor(reference); 
    } 
    protected void setActor(ActorRefImpl impl,AbsActor actor) 
    { 
        actors_map.put(impl,actor); 
    }
    protected  ActorRefImpl createActorReference(ActorMode mode) throws IllegalModeException
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new IllegalModeException(mode);
        }
        ActorRefImpl actor_ref = new ActorRefImpl();
        return actor_ref;
    }
    @Override
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor_type, ActorMode mode) {
        ActorRefImpl reference;
        try
        {
            reference = this.createActorReference(mode);
            AbsActor abs_actor = (AbsActor) actor_type.newInstance();
            abs_actor.setSelf(reference);

            ActorRefImpl impl = (ActorRefImpl) reference;
            impl.setActorSystem(this);
            setActor(impl,abs_actor);

        } catch (InstantiationException | IllegalAccessException e) {
            throw new NoSuchActorException(e);
        }
        return reference;
    }
    @Override
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor) 
    {
        return this.actorOf(actor, ActorMode.LOCAL);
    }
    public int compareRefs(ActorRef one,ActorRef two)
    {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (one==two) { return EQUAL;  }
        for (ActorRef ref :  actors_ref_list() )
        {
            if (ref==one) { return 1; }
            if (ref==two) { return -1; }
        }
        throw new ShouldNotHappenException("comparaison should always be possible.");
    }
    private Boolean isActive(ActorRef ref) { return actors_map.isActive(ref);  }
    private void setActive(ActorRef ref,Boolean b) { actors_map.setActive(ref,b);  }
    @Override 
    public void stop(ActorRef<?> actor_ref)
    {
        if (isActive(actor_ref)){  setActive(actor_ref,false) ;}
        else { throw new NoSuchActorException("This actor is not active anymore.");  }
    }
    @Override
    public void stop() 
    {
        for (ActorRef act_ref : actors_ref_list()) { stop(act_ref);  }
    }
    private Set<ActorRef> actors_ref_list() 
    {
        return actors_map.actors_ref_list();
    }
    private Collection<AbsActor> actors_list() 
    {
        return actors_map.actors_list();
    }
    public void send(Message message, ActorRef ref_to)
    {
        if (!isActive(ref_to)) { throw new NoSuchActorException(); }

        SendingThread sending_thread=new SendingThread(message,ref_to,this);
        Thread t = new Thread( sending_thread );
        t.start();
    }
}
