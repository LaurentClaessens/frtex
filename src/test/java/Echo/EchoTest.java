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

import actors.ActorRef;

import actors.impl.Echo.EchoActor;
import actors.impl.Echo.EchoActorSystem;
import actors.impl.Echo.EchoText;

import java.lang.InterruptedException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EchoTest  
{
    private EchoActorSystem system; 
    private ActorRef echo_actor;
    private ActorRef echo_one_actor;
    private ActorRef echo_two_actor;

    @Before
    public void initialize()
    {
        system = new EchoActorSystem();

        echo_actor = system.actorOf(EchoActor.class);
        echo_one_actor = system.actorOf(EchoActor.class);
        echo_two_actor = system.actorOf(EchoActor.class);

        echo_one_actor.setAcceptedType(EchoTextOne);
        echo_two_actor.setAcceptedType(EchoTextTwo);
    }

    @Test
    public void Numbering()
    {
        EchoActorSystem system = new EchoActorSystem();
        ActorRef a1 = system.actorOf(EchoActor.class);
        ActorRef a2 = system.actorOf(EchoActor.class);

        Assert.isEqual(a1.getSerieNumber,0);
        Assert.isEqual(a2.getSerieNumber,1);
    }
    @Test
    public void setAcceptedTypeVerification() throws InterruptedException
    {
        System.out.println("LANCEMENT de acceptedTypeVerification");

        echo_one_actor.setAcceptedType(EchoTextOne);
        echo_two_actor.setAcceptedType(EchoTextTwo);

        Assert.isEqual(echo_actor.getAcceptedType(),EchoText.class);
        Assert.isEqual(echo_one_actor.getAcceptedType(),EchoTextOne.class);
        Assert.isEqual(echo_two_actor.getAcceptedType(),EchoTextTwo.class);
    }

    @Test
    public void acceptedTypeVerification() throws InterruptedException
    {
        EchoText mE = new EchoText(echo_actor,echo_actor,20);
        echo_actor.send(mE,echo_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor "+echo_actor.getLastMessage().data);
        Assert.isEqual(echo_actor.getLastMessage().data,1);

        EchoText mE = new EchoText(echo_one_actor,echo_two_actor,20);
        echo_one_actor.send(mE,echo_two_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor "+echo_one_actor.getLastMessage().data);
        Assert.isEqual(echo_actor.getLastMessage().data,1);


        EchoTextOne mO = new EchoTextOne(echo_actor,echo_actor,23);
        echo_actor.send(mO,echo_actor);
        Thread.sleep(1000);
        System.out.println("echo_actor"+echo_actor.getLastMessage().data);
        Assert.isEqual(echo_actor.getLastMessage().data,1);

        EchoTextOne mO = new EchoTextOne(echo_actor,echo_one_actor,23);
        echo_actor.send(mO,echo_one_actor);
        Thread.sleep(1000);
        Assert.isEqual(echo_actor.getLastMessage().data,1);
    }
    @Test (expected = UnsupportedMessageException.class)  
    public void nonAcceptedTypeVerification() throws InterruptedException
    {
        System.out.println("LANCEMENT de nonAcceptedTypeVerification");

        EchoTextOne mO = new EchoTextOne(echo_actor,echo_two_actor,23);
        echo_actor.send(mO,echo_two_actor);
        Thread.sleep(1000);
        Assert.isEqual(echo_actor.getLastMessage().data,1);
    }
}
