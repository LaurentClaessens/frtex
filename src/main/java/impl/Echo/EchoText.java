
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

import actors.AbsMessage;
import actors.Actor;
import actors.ActorRef;

// 'EchoText' is a message that is supposed to be resent with a -1 on the data.
public class EchoText extends AbsMessage<Integer>
{
    private ActorRef from_actor;
    private ActorRef to_actor;
    private final Integer data;
    private final String tag="echo";

    public EchoText(ActorRef from, ActorRef to,Integer d)
    {
        from_actor=from;
        to_actor=to;
        data=d;
    }
    public String getTag() {return tag;} 
    public Integer getData() { return data; }
    public ActorRef getSender() { return from_actor; }
}
