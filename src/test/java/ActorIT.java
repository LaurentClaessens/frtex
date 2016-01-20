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

import actors.utils.ActorSystemFactory;
import actors.utils.actors.TrivialActor;
import actors.utils.actors.counter.CounterActor;
import actors.utils.actors.ping.pong.PingPongActor;
import actors.utils.actors.StoreActor;
import actors.utils.messages.StoreMessage;
import actors.utils.messages.counter.Increment;
import actors.utils.messages.ping.pong.PingMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test suite on actor features.
 *
 */
public class ActorIT {

    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        this.system = ActorSystemFactory.buildActorSystem();
    }

    @Test
    public void shouldBeAbleToSendAMessage() throws InterruptedException {
        TestActorRef ref = new TestActorRef(system.actorOf(StoreActor.class));
        StoreActor actor = (StoreActor) ref.getUnderlyingActor(system);
        // Send a string to the actor
        ref.send(new StoreMessage("Hello World"), ref);
        // Wait that the message is processed
        Thread.sleep(2000);
        // Verify that the message is been processed
        Assert.assertEquals("The message has to be received by the actor", "Hello World", actor.getData());
    }

    @Test
    public void shouldBeAbleToRespondToAMessage() throws InterruptedException {
        TestActorRef pingRef = new TestActorRef(system.actorOf(PingPongActor.class));
        TestActorRef pongRef = new TestActorRef(system.actorOf(PingPongActor.class));

        pongRef.send(new PingMessage(), pingRef);

        Thread.sleep(2000);

        PingPongActor pingActor = (PingPongActor) pingRef.getUnderlyingActor(system);
        PingPongActor pongActor = (PingPongActor) pongRef.getUnderlyingActor(system);

        Assert.assertEquals("A ping actor has received a ping message", "Ping",
                pingActor.getLastMessage().getMessage());
        Assert.assertEquals("A pong actor has received back a pong message", "Pong",
                pongActor.getLastMessage().getMessage());
    }

    @Test
    public void shouldNotLooseAnyMessage() throws InterruptedException {
        TestActorRef counter = new TestActorRef(system.actorOf(CounterActor.class));
        for (int i = 0; i < 200; i++) {
            TestActorRef adder = new TestActorRef(system.actorOf(TrivialActor.class));
            adder.send(new Increment(), counter);
        }

        Thread.sleep(2000);

        Assert.assertEquals("A counter that was incremented 1000 times should be equal to 1000",
                200, ((CounterActor) counter.getUnderlyingActor(system)).getCounter());
    }
}
