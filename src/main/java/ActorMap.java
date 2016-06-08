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
import actors.exceptions.ShouldNotHappenException;
import actors.DecentActor;


/*
     ACTOR MAP

It links actor references to the actual actor and to the activity status. 

 The 'ActorMap' is mainly a wrapper for the two needed maps : 
 - one maps actor references to actual actors (AbsActor)
 - one maps actor references "high" to the activity status.

//*/

public class ActorMap 
{
    private Map<DecentActorRef,DecentActor>  ref_to_actor;
    private Map<DecentActorRef,Boolean> ref_to_active; 

    public ActorMap()
    {
        ref_to_actor = new HashMap<DecentActorRef,DecentActor>();
        ref_to_active = new HashMap<DecentActorRef,Boolean>();
    }

    public Collection<DecentActor> actors_list() { return ref_to_actor.values(); }
    public Set<DecentActorRef> actors_ref_list() { return ref_to_actor.keySet(); }

    public DecentActor getActor(DecentActorRef reference) 
    {
        DecentActor a;
        synchronized(ref_to_actor)
        {
            a = ref_to_actor.get(reference);
        }
        if (a==null)
        {
            synchronized(ref_to_actor){System.out.println("The references are :"+actors_ref_list());}
            throw new ShouldNotHappenException("You are looking for a non existing actor");
        }
        return a;
    }
    public void put(DecentActorRef reference, DecentActor actor)
    {
        for (DecentActor a : actors_list())
        {
            if (a==actor) 
            {
                throw new AlreadyListedActor("This actor reference is already in the list.");
            }
        }
        synchronized(ref_to_actor) { ref_to_actor.put(reference,actor);}
        synchronized(ref_to_active) {  ref_to_active.put(reference,true); }
    }

    public void setActive(DecentActorRef ref,Boolean b)
    {
        ref_to_active.put( ref ,b);
    }

    public Boolean isActive(DecentActorRef reference)
    {
        Boolean a ;
        synchronized(ref_to_active) { a= ref_to_active.get(reference);  }
            
        if (a==null)
        {
            throw new ShouldNotHappenException("You are looking for a non existing actor");
        }
        return a;
    }
}
