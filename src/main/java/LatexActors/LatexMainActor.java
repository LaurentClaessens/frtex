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


package frtex.actors;

import actors.Message;
import java.util.HashMap;
import actors.exceptions.ShouldNotHappenException;

/**
 * The actor who is in charge of the main tex file.
 *
 * <p>
 * This class is not intended to be used by the end-user.
 * In particular, the function getAnswer will not wait the end of the work and will
 * result in a NullPointerException in most cases.
 *
*/

public class LatexMainActor extends LatexActor
{
    private String the_answer;
    
    public LatexMainActor()
    {
        super();
        working=true;
    }
    public String getAnswer() { return the_answer; }
    @Override
    protected void receiveAnswer(Message m)
    {
        LatexAnswerMessage message = (LatexAnswerMessage) m;
        the_answer=message.getContent();
        working=false;
    }
}

