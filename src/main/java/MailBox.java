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

import java.util.LinkedList;
import java.util.Queue;

import actors.Message;
import actors.Mail;
import actors.exceptions.ShouldNotHappenException;


/**
*  An actors has its mail box.
*/

public class MailBox
{
    private Queue<Mail> queue = new LinkedList<Mail>();
    private DecentActorRef proprietary;  
    private Class accepted_type=Message.class;

    public void setProprietary(DecentActorRef ref) { proprietary=ref;  }
    public void setAcceptedType(Class t) { accepted_type=t; }
    public void add(Message m)
    {
        if (!accepted_type.isInstance(m))
        {
           throw new ShouldNotHappenException("Messages that are not of the correct type should be already filtered.");
        }
        Mail mail = new Mail(m,proprietary);
        add(mail);
    }
    public void add(Mail m) 
    {
        try { synchronized(this) { queue.add(m); } }
        catch (ClassCastException e)
        {
           throw new ShouldNotHappenException("Messages that are not of the correct type should be already filtered.");
        }
    }
    public Mail poll()  // return the first element and then remove it
    {
        synchronized(this) { return queue.poll();  }
    }           
    public int size() {return queue.size();  }
}

