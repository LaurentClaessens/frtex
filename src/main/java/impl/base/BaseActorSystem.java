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

import java.util.Collection;
import java.util.Set;

import actors.AbsActorSystem;
import actors.ActorSystem.ActorMode;
import actors.ActorRef;
import actors.Actor;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.IllegalModeException;
import actors.exceptions.NoSuchActorException;

import actors.impl.base.ActorMap;

public class BaseActorSystem extends AbsActorSystem
{
    private ActorMap actors_map;

    public BaseActorSystem() 
    { 
        actors_map=new ActorMap();
    }

    @Override
    public BaseActor getActor(ActorRef reference) 
    {
        return actors_map.getActor(reference); 
    } 
    @Override
    protected void setActor(ActorRef reference,Actor actor) 
    { 
        actors_map.put(reference,actor); 
    }

    protected final ActorRef createActorReference(ActorMode mode) throws IllegalModeException
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new IllegalModeException(mode);
        }
        ActorRef actor_ref;
        actor_ref = new BaseActorRef(this);
        return actor_ref;
    }
    private Boolean test_if_something_up()
    {
        for (Actor act : actors_list()) 
        {
            if (act.getMailBox().size()>0) return true;
        }
        return false;
    }
    @Override
    public void join()
    {
        Boolean still_up=true;
        while (still_up==true)
        {
            still_up=test_if_something_up();
        }
    }
    private Boolean isActive(ActorRef ref) { return actors_map.isActive(ref);  }
    private void setActive(ActorRef ref,Boolean b) { actors_map.setActive(ref,b);  }
    private Set<ActorRef> actors_ref_list() {return actors_map.actors_ref_list();}
    private Collection<Actor> actors_list() {return actors_map.actors_list();}
    @Override 
    public void stop(ActorRef<?> actor)
    {
        if (isActive(actor)){  setActive(actor,false) ;}
        else { throw new NoSuchActorException("This actor is not active anymore.");  }
    }
    @Override
    public void stop() 
    {
        for (ActorRef act_ref : actors_ref_list()) { stop(act_ref);  }
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
}
