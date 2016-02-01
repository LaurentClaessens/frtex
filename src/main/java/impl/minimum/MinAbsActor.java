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

package actors.impl.minimum;

import actors.AbsActor;
import actors.AbsActorSystem;
import actors.Message;
import actors.exceptions.UnsupportedMessageException;

public abstract class MinAbsActor<T extends Message> extends AbsActor<T>
{
    private AbsActorSystem actor_system;
    public MinAbsActor(AbsActorSystem ac,Class<Message> type)
    { 
        super(); 
        actor_system=ac;
        setAcceptedType(type);
    }
    public MinAbsActor(AbsActorSystem ac)
    { 
        super(); 
        actor_system=ac;
    }
    public MinAbsActor() {};        // After using this one, you have to put the actor system by hand.
    public abstract void processMessage(T m);
    private void processNextMessage()
    {
        if ( mail_box.size()>0 )
        {
            T m;
            synchronized(mail_box) { m=mail_box.poll(); }
            processMessage(m);
        }
    }

    public void do_receive(Message message)
    {
        T m=(T) message;
        synchronized(mail_box) { mail_box.add(m);}
        processNextMessage();
    }
    @Override
    public void receive(Message m)
    {
        if (accepted_type.isInstance(m)) 
        {
            do_receive(m); 
        }
        else { throw new UnsupportedMessageException(m);  }
    }
}
