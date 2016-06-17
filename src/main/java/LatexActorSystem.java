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

package actors.impl.latex;

import actors.DecentActorSystem;
import actors.DecentActorRef;

public class LatexActorSystem extends DecentActorSystem
{
    public LatexActorSystem() { super(LatexMessage.class); }
    void setUpActor(LatexActorRef ref,LatexActor act)
    {
        super.setUpActor(ref,act);
    }
    @Override
    public LatexActorRef createPair()
    {
        LatexActorRef reference = new LatexActorRef();
        LatexActor actor = new LatexActor();
        setUpActor(reference,actor);
        return reference;
    }
    public LatexActorRef getMainActor()
    /**
     * Create a LatexMainActor and return a reference to it.
     */
    {
        LatexActorRef reference = new LatexActorRef();
        LatexMainActor actor = new LatexMainActor();
        setUpActor(reference,actor);
        return reference;
    }
    public synchronized LatexActorRef getNonWorkingActor()
    /**
    * Return an actor which not working and create an new actor is everyone is occupied.  
    * <p>
    * See the README.md for the difference between 'working' and 'active'.
    */

    {
        for (DecentActorRef ar : getActorRefList())
        {
            LatexActorRef actor_ref = (LatexActorRef) ar;
            if (!actor_ref.getActor().isWorking())
            {
                actor_ref.getActor().setWorking();
                return actor_ref;
            }
        }
        // here all actors are working
        LatexActorRef actor_ref = createPair();
        actor_ref.getActor().setWorking();
        return actor_ref;
    }
}
