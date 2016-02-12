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

import actors.impl.decent.DecentActorSystem;
import actors.impl.decent.DecentActorRef;
import actors.ActorRef;
import actors.Actor;
import actors.exceptions.ShouldNotHappenException;

public class EchoActorSystem extends DecentActorSystem
{

    public EchoActorSystem() 
    { 
        super(EchoText.class); 
        created_serie_number=-1;
    }
    @Override
    public ActorRef<EchoText> actorOf(Class<? extends Actor> actor, ActorMode mode) 
    {
        if (actor!=EchoActor.class)
        {
            throw new ShouldNotHappenException("Only EchoActor are supported by the Echo actor system.");
        }
        if (mode!=ActorMode.LOCAL)
        {
            throw new ShouldNotHappenException("Only local actors are supported by the Echo actor system.");
        }
        return actorOf(EchoActor.class,ActorMode.LOCAL);
    }

    public DecentActorRef actorOf()
    {
        DencetActorRef ar = (DecentActorRef) super.actorOf(EchoActor.class,ActorMode.LOCAL);
        ar.setActorSystem(this);
        synchronized(created_serie_number)
        {
            ar.setSerieNumber( newSerieNumber() );
        }
        ar.setAcceptedType(EchoText.class);
        return ar;
    }
}
