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

import actors.exceptions.AlreadyListedActor;
import actors.exceptions.NoSuchActorException;


// The 'ActorMap' is a wrapper for the two needed maps : 
// - one maps actor references to the actual actor
// - one maps actor references to the activity status.

public class ActorMap
{
    private Map<ActorRef,Actor> actors_map;
    private Map<ActorRef,Boolean> active_map;

    public Collection<Actor> actors_list() { return actors_map.values(); }
    public Set<ActorRef> actors_ref_list() { return actors_map.keySet(); }
    public Actor getActor(ActorRef ref) 
    {
        if (!isActive(ref)) {throw new NoSuchActorException();}
        // Cette ligne peut ête enlevée :
        Actor a = actors_map.get(ref); 
        return actors_map.get(ref); 
    }

    public ActorMap()
    {
        actors_map = new HashMap<ActorRef,Actor>();
        active_map = new HashMap<ActorRef,Boolean>();
    }
    public void put(ActorRef reference,Actor actor)
    {
        for (ActorRef ref : actors_ref_list())
        {
            if (ref==reference) 
            {
                throw new AlreadyListedActor("This actor reference is already in the list.");
            }
        }
        actors_map.put(reference,actor);
        active_map.put(reference,true);
    }
    public void setActive(ActorRef ref,Boolean b){active_map.put(ref,b);}
    public Boolean isActive(ActorRef reference) {return active_map.get(reference);}
}
