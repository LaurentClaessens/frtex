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

// This is testing the Echo implementation of my actor system.
//
// To be tested 
//   - if the serie number of the first one is -1 or 0 or 1.
//   - if the ordering is correct.
//   - try to send a Message of type other than EchoText

import actors.ActorRef;

import actors.impl.Echo.EchoActor;
import actors.impl.Echo.EchoActorRef;
import actors.impl.Echo.EchoActorSystem;
import actors.impl.Echo.EchoText;

import java.lang.InterruptedException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EchoTest {

    @Test
    public void Numbering()
    {
        EchoActorSystem system = new EchoActorSystem();
        ActorRef a1 = system.actorOf(EchoActor.class);
        ActorRef a2 = system.actorOf(EchoActor.class);
    }
    @Test
    public void Send()
    {
        EchoActorSystem system = new EchoActorSystem();
        ActorRef a1 = system.actorOf(EchoActor.class);
        ActorRef a2 = system.actorOf(EchoActor.class);
        EchoText m1 = new EchoText(a1,a2,50);
        EchoText m2 = new EchoText(a2,a1,10);
        EchoText m3 = new EchoText(a2,a1,100);
        a1.send(m1,a2);
        a2.send(m2,a1);
        a2.send(m3,a1);
        system.join();
    }
}
