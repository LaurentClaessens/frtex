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

import actors.impl.ActorSystemImpl;
import actors.Actor;
import actors.ActorRef;
import actors.Message;
import actors.exceptions.NoSuchActorException;

public class EchoActorSystem extends ActorSystemImpl
{
    public EchoActorSystem() { super(); }
    @Override
    public ActorRef<EchoText> actorOf(Class actor,ActorMode mode)
    {
        EchoActorRef ar = (EchoActorRef) super.actorOf(EchoActor.class,mode);
        ar.setActorSystem(this);
        ar.setSerieNumber( this.newSerieNumber() );
        return ar;
    }
}
