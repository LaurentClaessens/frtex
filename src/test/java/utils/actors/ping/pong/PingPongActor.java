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

package actors.utils.actors.ping.pong;

import actors.AbsActor;
import actors.utils.messages.ping.pong.PingMessage;
import actors.utils.messages.ping.pong.PingPongMessage;
import actors.utils.messages.ping.pong.PongMessage;

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class PingPongActor extends AbsActor<PingPongMessage> {

    private PingPongMessage lastMessage;

    public PingPongMessage getLastMessage() {
        return lastMessage;
    }

    /**
     * Responds to a {@link PingMessage} with a {@link PongMessage}.
     *
     * @param message The type of messages the actor can receive
     */
    @Override
    public void receive(PingPongMessage message) {
        this.lastMessage = message;
        if (message instanceof PingMessage)
            System.out.println("Dans receive::PingPongActor "+sender);
            self.send(new PongMessage(), sender);
    }
}
