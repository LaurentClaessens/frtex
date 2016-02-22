/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package actors;

import actors.utils.ActorSystemFactory;
import actors.utils.actors.TrivialActor;
import actors.utils.messages.TrivialMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases about {@link ActorRef} type.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
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
    public void shouldImmplementComparable() {
        ActorRef ref1 = system.actorOf(TrivialActor.class);
        ActorRef ref2 = system.actorOf(TrivialActor.class);
        Assert.assertNotEquals("Two references must appear as different using the compareTo method",
                0, ref1.compareTo(ref2));
        Assert.assertEquals("A reference must be equal to itself according to compareTo method",
                0, ref1.compareTo(ref1));
    }
}
