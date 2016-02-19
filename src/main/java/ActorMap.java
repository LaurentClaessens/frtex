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
import actors.ActorRefImpl;
import actors.Actor;
import actors.AbsActor;

// The 'ActorMap' is mainly a wrapper for the two needed maps : 
// - one maps actor references "impl" to the actual actor
// - one maps actor references "impl" to the activity status.

// However, due to the fact that the unipd's tests are build on ActorRef instead of ActorRefImpl while my functions are build over ActorRefImpl, this ActorMap adds more maps that are wrapper around an organization problem :
// - ActorRef to ActorRefImpl

// Most of the functions are overloaded to accept ActorRef and ActorRefImpl as argument. I don't make them polymorphic since I know that 
// 1. there will always be an ActorRefImpl associated with a given ActorRef object
// 2. in real live, we build the application over the "Decent" implementation that don't suffer the problem.
//
// Naming convention : the "ref" prefix refers to ActorRef and the "Impl" prefix refers to ActorRefImpl.

public class ActorMap
{
    private Map<ActorRefImpl,AbsActor>  impl_to_actor;    // ex : actors_map;
    private Map<ActorRefImpl,Boolean> impl_to_active;  //ex : active_map;

    private Map<ActorRef,ActorRefImpl> ref_to_impl;     // this one is the wrapper around an organization problem.

    public Collection<AbsActor> actors_list() { return impl_to_actor.values(); }
    public Set<ActorRef> actors_ref_list() { return ref_to_impl.keySet(); }
    public Set<ActorRefImpl> actors_impl_list() { return impl_to_actor.keySet(); }

    public AbsActor getActor(ActorRefImpl impl_ref) 
    {
        System.out.println("ActorMap::getActor --0    "+impl_ref);
        if (!isActive(impl_ref)) 
        {
            throw new ShouldNotHappenException("The actor activeness should have been verified before.");
        }
        System.out.println("ActorMap::getActor --1");
        AbsActor aa =  impl_to_actor.get(impl_ref); 
        System.out.println("ActorMap::getActor --2");
        return impl_to_actor.get(impl_ref); 
    }
    public AbsActor getActor(ActorRef abs_ref) 
    {
        ActorRefImpl ari = ref_to_impl.get(abs_ref);
        return getActor( ref_to_impl.get(abs_ref)  ); 
    }
    public ActorRefImpl refToImpl(ActorRef ref) { return ref_to_impl.get(ref); }

    public ActorMap()
    {
        impl_to_actor = new HashMap<ActorRefImpl,AbsActor>();
        impl_to_active = new HashMap<ActorRefImpl,Boolean>();
        ref_to_impl = new HashMap<ActorRef,ActorRefImpl>();
    }
    public void assingRefToImpl(ActorRef ref,ActorRefImpl impl)
    {
        ref_to_impl.put(ref,impl);
    }
    public void put(ActorRefImpl impl_reference,AbsActor actor)
    {
        for (ActorRefImpl impl : actors_impl_list())
        {
            if (impl==impl_reference) 
            {
                throw new AlreadyListedActor("This actor reference is already in the list.");
            }
        }
        impl_to_actor.put(impl_reference,actor);
        impl_to_active.put(impl_reference,true);
    }
    public void setActive(ActorRefImpl ref,Boolean b){impl_to_active.put(ref,b);}
    public void setActive(ActorRef ref,Boolean b){impl_to_active.put(  ref_to_impl.get(ref) ,b);}
    public Boolean isActive(ActorRefImpl reference) {return impl_to_active.get(reference);}
    public Boolean isActive(ActorRef reference) {return isActive(ref_to_impl.get(reference));}
}
