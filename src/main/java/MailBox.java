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

import actors.Message;
import java.util.LinkedList;
import java.util.Queue;


/**
*  An actors has its mail box.
*/

public class MailBox<M extends Message>
{
    private Queue<M> queue = new LinkedList<M>();

    public void  add(M m) 
    {
        try { synchronized(this) { queue.add(m); } }
        catch (ClassCastException e)
        {
            throw  UnsupportedMessageException("I'm a Echo mail box receiving"+m.typename());  
        }
    }

    public M poll()  // return the first element and then remove it
    {
        M m;
        synchronized(this) { m= queue.poll();  }
        return m;
    }           
    public int size() {return queue.size();  }
}

