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

import java.io.File;


/**
 * Represent a LaTeX code.
 */

public class LatexCode
{

    private File main_file;


    public LatexCode(String filename)
        /*
         * @param the name of the LaTeX file, including the path.
         */
    {
        main_file = new File(filename).getAbsoluteFile();
    }

    public String getExplicitCode()
    {
        /*
         * @return the explicit code as a string.
         */
        LatexActorSystem system= new LatexActorSystem();
        LatexActorRef main_actor_ref = system.getMainActor();
        LatexMainActor main_actor = (LatexMainActor) main_actor_ref.getActor();

        main_actor.sendRequest(main_file);
        main_actor.waitWorking();
        return main_actor.getAnswer();
    }

}


