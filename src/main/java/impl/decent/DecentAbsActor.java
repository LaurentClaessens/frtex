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

import java.util.UUID;

import actors.impl.base.BaseAbsActor;
import actors.AbsActorSystem;
import actors.Message;
import actors.impl.decent.DecentActorSystem;

// This class has no generic parameter because the message type is passed as argument.
public abstract class DecentAbsActor extends BaseAbsActor
{
    private String my_name;
    private Integer serie_number;
    private Class accepted_type;

    public void setSerieNumber(Integer n) {serie_number=n;}
    public void setAcceptedType(Class t) {accepted_type=t;}
    public String getName() { return my_name; }
    public Class getAcceptedType() { return accepted_type; }
    @Override
    public DecentActorSystem getActorSystem()
    {
        return (DecentActorSystem) super.getActorSystem();
    }
}
