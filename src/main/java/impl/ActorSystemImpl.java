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

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

import actors.AbsActorSystem;
import actors.ActorRef;
import actors.Actor;
import actors.exceptions.ShouldNotHappenException;

public class ActorSystemImpl extends AbsActorSystem
{
    private Integer created_serie_number;
    private Map<ActorRef,Actor> actors_map;
    public ActorSystemImpl() 
    { 
        created_serie_number=-1; 
        actors_map = new HashMap<ActorRef,Actor>();
    }


    // increment the serie number of 1 and return the result.
    public Integer newSerieNumber()  { return ++created_serie_number;  }
    @Override
    public Actor getActor(ActorRef reference) { return actors_map.get(reference); } 
    @Override
    protected void setActor(ActorRef reference,Actor actor) { actors_map.put(reference,actor); }
    public Collection<Actor> actors_list() { return actors_map.values(); }
    public Set<ActorRef> actors_ref_list() { return actors_map.keySet(); }

    protected final ActorRef createActorReference(ActorMode mode)
    {
        ActorRef actor_ref;
        synchronized (created_serie_number)
        {
            actor_ref = new ActorRefImpl(this,newSerieNumber());
        }
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
        System.out.println("boh il n'y a plus rien...");
    }
    @Override
    public void stop() 
    {
        for (Actor act : actors_list()) { act.stop();  }
    }

    public int compareRefs(ActorRef one,ActorRef two)
    {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (one==two) { return EQUAL;  }
        for (ActorRef ar :  actors_ref_list() )
        {
            if (ar==one) { return 1; }
            if (ar==two) { return -1; }
        }
        throw new ShouldNotHappenException("comparaison should always be possible.");
    }
}
