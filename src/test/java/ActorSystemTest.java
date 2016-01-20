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

import actors.exceptions.NoSuchActorException;
import actors.utils.ActorSystemFactory;
import actors.utils.actors.TrivialActor;
import actors.utils.messages.TrivialMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests features of an actors' system.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class ActorSystemTest {

    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        system = ActorSystemFactory.buildActorSystem();
    }

    @Test
    public void shouldCreateAnActorRefWithActorOfTest() {
        ActorRef ref = system.actorOf(TrivialActor.class);
        Assert.assertNotNull("A reference was created and it is not null", ref);
    }

    @Test
    public void shouldCreateAnActorRefOfWithActorModeLocalTest() {
        ActorRef ref = system.actorOf(TrivialActor.class, ActorSystem.ActorMode.LOCAL);
        Assert.assertNotNull("A reference to a local actor was created and it is not null", ref);
    }

    /**
     * It is not requested to implement remote mode for actors anymore. So, an attempt to create a remote
     * actor should rise an {@link IllegalArgumentException}
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateAnActorRefOfWithActorModeRemoteTest() {
        system.actorOf(TrivialActor.class, ActorSystem.ActorMode.REMOTE);
    }

    @Test
    public void shouldBeAbleToCreateMoreThanOneActor() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        ActorRef ref2 = system.actorOf(TrivialActor.class);
        Assert.assertNotEquals("Two references that points to the same actor implementation are not equal", ref1, ref2);
    }

    @Test(expected = NoSuchActorException.class)
    public void shouldStopAnActorAndThisCouldNotBeAbleToReceiveNewMessages() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        system.stop(ref1);
        ref1.send(new TrivialMessage(), ref1);
    }

    @Test(expected = NoSuchActorException.class)
    public void shouldStopAnActorAndThisCouldNotStoppedASecondTime() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        system.stop(ref1);
        system.stop(ref1);
    }
}
