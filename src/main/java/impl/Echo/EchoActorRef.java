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

import actors.exceptions.ShouldNotHappenException;

public class EchoActorRef implements ActorRef<EchoText>
{
    private EchoActorSystem actor_system;
    private Integer serie_number;

    public EchoActorRef(EchoActorSystem ac,Integer number) { 
        actor_system=ac; 
        serie_number=number;
    }

    public void send(Message m, ActorRef to) 
    { 
        EchoActor actor = (EchoActor) actor_system.getActor(this);
        synchronized (actor.getMailBox()) { actor.getMailBox().add(m);  }
    }
    public int getSerieNumber() { return serie_number;  }
    public int compareTo(ActorRef oth)
    {
        EchoActorRef other=(EchoActorRef) oth;
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (other==this) { return EQUAL;  }
        if (other.getSerieNumber()>serie_number) { return -1; }
        if (other.getSerieNumber()<serie_number) { return 1; }
        throw new ShouldNotHappenException("comparaison should always be possible.");
    }

}
