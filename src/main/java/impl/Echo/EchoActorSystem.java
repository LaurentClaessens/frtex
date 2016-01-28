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

package actors.impl.Echo;

import actors.AbsActorSystem;
import actors.ActorRef;
import actors.Actor;
import actors.exceptions.NoSuchActorException;

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;

public class EchoActorSystem extends AbsActorSystem
{
    private Integer  created_serie_number;
    private Map<ActorRef,Actor> actors_map;

    public EchoActorSystem()
    {
        created_serie_number=-1;
        actors_map = new HashMap<ActorRef,Actor>();
    }

    protected final EchoActorRef createActorReference(ActorMode mode)
    {
        EchoActorRef actor_ref;
        synchronized(created_serie_number)
        {
            actor_ref = new EchoActorRef(this,++created_serie_number);
        }
        return actor_ref;
    }
    public Actor getActor(ActorRef reference) { return actors_map.get(reference); } 
    public void setActor(ActorRef reference,Actor actor) { actors_map.put(reference,actor); }
    public void stop() {}
    public void stop(ActorRef<?> actor) { }
}
