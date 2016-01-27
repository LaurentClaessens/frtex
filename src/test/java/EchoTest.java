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

// This is testing the Echo implementation of my actor system.
//
// To be tested 
//   - if the serie number of the first one is -1 or 0 or 1.
//   - if the ordering is correct.
//   - try to send a Message of type other than EchoText

package actors;


import actors.AbsActorSystem;
import actors.impl.Echo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EchoTest {

    private AbsActorSystem system;

    @Before
    public void init() 
    {
        system = new AbsActorSystem();
    }
    @Test
    public void Numbering()
    {
        a1 = system.ActorOf(EchoActor);
        a2 = system.ActorOf(EchoActor);
        Assert.assertTrue(a1.getSerieNumber()==0);
        Assert.assertTrue(a1.getSerieNumber()==2);
        Assert.assertTrue(a2>a1);
    }
}
