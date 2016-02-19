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
import actors.AbsActor;
import actors.impl.base.BaseActorRef;
import actors.impl.base.BaseAbsActor;

public class DecentActorRef extends BaseActorRef 
{ 
    @Override
    public DecentAbsActor getActor()
    {
        System.out.println("DecentActorRef::getActor");
        AbsActor truc= super.getActor();
        System.out.println("DecentActorRef::getAcrtor. Trouvé : "+truc);
        return (DecentAbsActor) super.getActor();
    }
    public void setAcceptedType(Class t) { getActor().setAcceptedType(t); }
    public String getName() { return getActor().getName(); }
    public Integer getSerieNumber() {return getActor().getSerieNumber();}
    public void setActorSystem(DecentActorSystem as)
    {
        getActor().setActorSystem(as);
    }
    public Class getAcceptedType() {return getActor().getAcceptedType();}
}
