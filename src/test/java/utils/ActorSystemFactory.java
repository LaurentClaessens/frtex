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

package actors.utils;

import actors.AbsActorSystem;
import actors.ActorSystem;
import org.reflections.Reflections;

import java.util.Set;

import actors.impl.base.BaseActorSystem;
import actors.ActorSystemImpl;

/**
 * Scans the classpath and instantiates the concrete class that implements {@link it.unipd.math.pcd.actors.ActorSystem}.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public class ActorSystemFactory {
    private static final String BASE_PACKAGE = "actors";

    /**
     * Returns an instance of the sole concrete class that implements {@link ActorSystem}.
     *
     * @return An instance of an implementation of {@link ActorSystem}
     */
    public static final ActorSystem buildActorSystem() {
        ActorSystem system=new ActorSystemImpl();
        return system;
    }
}
