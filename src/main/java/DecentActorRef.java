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

import actors.Message;

public class DecentActorRef 
{ 
    private DecentActorSystem  actor_system;

    public DecentActorSystem getActorSystem()  {return actor_system;}
    public DecentActor getActor()
    {
        return getActorSystem().getActor(this);  
    }
    public void setAcceptedType(Class t) { getActor().setAcceptedType(t); }
    public String getName() { return getActor().getName(); }
    public Integer getSerieNumber() {return getActor().getSerieNumber();}

    public void setActorSystem(DecentActorSystem as)
    {
        actor_system=as;
        getActor().setActorSystem(as);
    }

    public Class getAcceptedType() {return getActor().getAcceptedType();}
    public void send(Message message, DecentActorRef to) 
    { 
        getActorSystem().send(message,to);
    }
}
