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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MailBoxTest {

    private MailBox mail_box;
    private actors.Message m2;

    @Before
    public void init() 
    {
        mail_box=new actors.MailBox();
    }

    @Test
    public void MailBoxFIFO()
    {
        mail_box.add( new actors.EchoText("hello 1")  );
        mail_box.add( new actors.Message("hello 2")  );
        mail_box.add( new actors.Message("hello 3")  );
        m2=mail_box.poll();
        Assert.assertTrue(m2.getText().equals("hello 1"));
        mail_box.add( new actors.Message("hello 4")  );
        m2=mail_box.poll();
        Assert.assertTrue(m2.getText().equals("hello 2"));
        Assert.assertTrue(mail_box.size()==2);
    }
}
