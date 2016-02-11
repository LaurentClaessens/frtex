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

package actors.impl.decent;

import actors.Message;
import actors.impl.ActorSystemImpl;
import actors.ActorSystem.ActorMode;


public abstract class DecentActorSystem extends ActorSystemImpl
{
    private Class accepted_type=Message.class;
    public DecentActorSystem(Class t) 
    {
      super(); 
      accepted_type=t;
    }
    @Override
    public DecentActorRef actorOf()
    {
        ActorRefImpl ar = (ActorRefImpl) super.actorOf(accepted_type,ActorMode.LOCAL);
        ar.setActorSystem(this);
        ar.setAcceptedType(accepted_type);
        return ar;
    }
}
