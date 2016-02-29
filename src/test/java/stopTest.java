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
import actors.utils.messages.counter.Decrement;
import actors.utils.messages.ping.pong.PingMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class stopTest {

    private ActorSystem system;

    @Before
    public void init() {
        this.system = ActorSystemFactory.buildActorSystem();
    }

    @Test
    public void stopCorrectly() throws InterruptedException 
    {
        TestActorRef ref = new TestActorRef(system.actorOf(StoreActor.class));
        TestActorRef counter = new TestActorRef(system.actorOf(CounterActor.class));
        CounterActor act = (CounterActor) counter.getUnderlyingActor(system);
        for (int i = 0; i < 100; i++) {
            ref.send(new Increment(), counter);
        }
        //act.stop();

        Thread.sleep(4000);
        Assert.assertEquals("The message has to be received by the actor", 100, act.getCounter());

    }

}
