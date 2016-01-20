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

import actors.impl.ActorSystemImpl;
import actors.utils.ActorSystemFactory;
import actors.utils.actors.TrivialActor;
import actors.utils.messages.TrivialMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases about {@link ActorRef} type.
 *
 */
public class ActorRefTest {

    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        system = ActorSystemFactory.buildActorSystem();
    }

    @Test
    public void shouldImplementComparable() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        ActorRef ref2 = system.actorOf(TrivialActor.class);
        Assert.assertNotEquals("Two references must appear as different using the compareTo method",
                0, ref1.compareTo(ref2));
        Assert.assertEquals("A reference must be equal to itself according to compareTo method",
                0, ref1.compareTo(ref1));
    }
}





