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

package actors.utils.actors.counter;

import actors.AbsActor;
import actors.utils.messages.counter.*;

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class CounterActor extends AbsActor<CounterMessage> {

    private int counter = 0;

    @Override
    public void receive(CounterMessage message) {
        if (message instanceof Increment) {
            counter++;
            System.out.println("Incrément reçu : "+message);
        } else if (message instanceof Decrement) {
            System.out.println("Décrément reçu : "+message);
            counter--;
        } else if (message instanceof Get) {
            self.send(new Result(counter), sender);
        }
    }

    public int getCounter() {
        return counter;
    }
}
