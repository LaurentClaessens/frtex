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

import actors.ActorSystemImpl;
import actors.ActorSystem.ActorMode;
import actors.ActorRef;
import actors.ActorRefImpl;
import actors.Actor;
import actors.AbsActor;
import actors.exceptions.ShouldNotHappenException;
import actors.exceptions.IllegalModeException;
import actors.exceptions.NoSuchActorException;

import actors.impl.base.BaseActorMap;

public class BaseActorSystem extends ActorSystemImpl
{

    private BaseActorMap actors_map;
    public BaseActorSystem() 
    { 
        actors_map=new BaseActorMap();
    }

    @Override
    public BaseAbsActor getActor(ActorRef ref) 
    {
        BaseActorRef reference = (BaseActorRef) ref;
        return actors_map.getActor(reference); 
    } 
    @Override
    protected void setActor(ActorRefImpl ref,AbsActor actor) 
    { 
        BaseActorRef base_ref=(BaseActorRef) ref;
        BaseAbsActor base_actor=(BaseAbsActor) actor;
        actors_map.put(base_ref,base_actor); 
    }

    protected final ActorRef createActorReference(ActorMode mode) throws IllegalModeException
    {
        if (mode!=ActorMode.LOCAL)
        {
            throw new IllegalModeException(mode);
        }
        BaseActorRef actor_ref;
        actor_ref = new BaseActorRef();
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
    public void join()
    {
        Boolean still_up=true;
        while (still_up==true)
        {
            still_up=test_if_something_up();
        }
    }
    private Boolean isActive(BaseActorRef ref) { return actors_map.isActive(ref);  }
    private void setActive(BaseActorRef ref,Boolean b) { actors_map.setActive(ref,b);  }
    private Set<BaseActorRef> actors_ref_list() {return actors_map.actors_ref_list();}
    private Collection<BaseAbsActor> actors_list() {return actors_map.actors_list();}
}
