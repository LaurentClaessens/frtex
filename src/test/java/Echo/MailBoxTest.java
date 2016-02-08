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


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import actors.ActorRef;
import actors.MailBox;
import actors.impl.Echo.EchoText;
import actors.impl.Echo.EchoActor;
import actors.impl.Echo.EchoActorSystem;

public class MailBoxTest {

    private MailBox mail_box;
    private EchoText m2;

    @Test
    public void MailBoxFIFO()
    {
        MailBox<EchoText> mail_box=new MailBox<EchoText>();
        EchoActorSystem system = new EchoActorSystem();
        ActorRef a1 = system.actorOf(EchoActor.class);
        ActorRef a2 = system.actorOf(EchoActor.class);

        mail_box.add( new EchoText(a1,a2,3)  );
        mail_box.add( new EchoText(a1,a2,13)  );
        mail_box.add( new EchoText(a1,a2,23)  );

        EchoText m2=mail_box.poll().getMessage();
        Assert.assertTrue(m2.getData()==3);
        mail_box.add( new EchoText(a1,a2,33)  );
        m2=mail_box.poll().getMessage();
        Assert.assertTrue(m2.getData()==13);
        Assert.assertTrue(mail_box.size()==2);
    }
}
