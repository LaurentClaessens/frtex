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

// This is a message type which is identical to EchoText, but in a different class,
// in order to test if the 'accepted_type' verification works.

package actors.impl.Echo;

import actors.AbsMessage;
import actors.Actor;
import actors.ActorRef;

public class EchoTextTwo extends EchoText
{
    public EchoTextTwo(ActorRef from, ActorRef to,Integer d)
    {
        super(from,to,d);
    }
}
