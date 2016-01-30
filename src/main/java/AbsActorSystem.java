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
import actors.exceptions.NoSuchActorException;

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;

/**
 * A map-based implementation of the actor system.
 */

public abstract class AbsActorSystem implements ActorSystem {

    private Map<ActorRef,Actor> actors_map;
    public AbsActorSystem()
    {
        actors_map = new HashMap<ActorRef,Actor>();
    }

    @Override
    public Actor getActor(ActorRef reference) { return actors_map.get(reference); } 
    public void setActor(ActorRef reference,Actor actor) { actors_map.put(reference,actor); }
    public Collection<Actor> actors_list() { return actors_map.values(); }
    public Set<ActorRef> actors_ref_list() { return actors_map.keySet(); }
    @Override
    public ActorRef<? extends Message> actorOf(Class<? extends Actor> actor, ActorMode mode) {

        // ActorRef instance
        ActorRef<?> reference;
        try
        {
            // Create the reference to the actor
            reference = this.createActorReference(mode);
            // Create the new instance of the actor
            Actor actorInstance = ((AbsActor) actor.newInstance()).setSelf(reference);
            // Associate the reference to the actor
            setActor(reference, actorInstance);
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
    protected abstract ActorRef createActorReference(ActorMode mode);

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
    public void stop() 
    {
        for (Actor act : actors_list()) { act.stop();  }
    }
    public void stop(ActorRef<?> actor) { }
}
