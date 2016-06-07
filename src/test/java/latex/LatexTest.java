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

import actors.impl.latex.LatexActorSystem;
import actors.impl.latex.LatexMessage;
import actors.impl.latex.LatexActor;
import actors.ActorRef;

public class LatexTest
{
    private LatexActorSystem system;
    @Test
    public void test_content() throws InterruptedException
    {
        System.out.println("DÉPART DU TEST LATEX");
        LatexActorSystem system= new LatexActorSystem();
        ActorRef main_actor = system.createPair();
        LatexMessage main_message = new LatexMessage(main_actor,main_actor,"ask","tex_files/test.tex");
        main_actor.send(main_message,main_actor);
        Thread.sleep(500);
        System.out.println("Le fil principal LATEX se relance");
    }
}
