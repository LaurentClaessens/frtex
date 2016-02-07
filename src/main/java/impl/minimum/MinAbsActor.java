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

package actors.impl.minimum;

import actors.AbsActor;
import actors.AbsActorSystem;
import actors.Message;

public abstract class MinAbsActor<T extends Message> extends AbsActor<T>
{
    private AbsActorSystem actor_system;
    public void setAcceptedType(Class t) {  accepted_type=t; }
    public MinAbsActor(AbsActorSystem ac,Class<Message> type)
    { 
        super(); 
        actor_system=ac;
        setAcceptedType(type);
        System.out.println("Construit MinAbsActor avec "+type);
    }
    public MinAbsActor(AbsActorSystem ac)
    { 
        super(); 
        actor_system=ac;
        System.out.println("Constructeur à 1 paramètre "+accepted_type);
    }
    public MinAbsActor()  
    {
        System.out.println("Constructeur à zéro paramètre ");
    }
        
}
