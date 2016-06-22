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

package frtex.LatexActors;

import actors.Message;
import java.util.HashMap;
import actors.exceptions.ShouldNotHappenException;

import actors.DecentActor;
import frtex.DecomposedTexFile;
import frtex.FileProcessing;

import java.io.File;


/**
 *
 * A LatexActor is 'working' until it succeed to send the answer to who asked that.
 * It is constructing the decomposition outside the 'receive' function because it is sending messages while decomposing (each time an input is found)
 * Therefore it is possible to receive answers before completing the decomposition and its mailbox has to be open before to initiate the decomposition.
 * To be clear : the 'working' attribute is not related to the openness of the mailbox.
 *
 */






public class LatexActor extends DecentActor
    /**
     * The 'decomposition' object is shared between the file processing thread and the 'received' function.
     * The first one is adding new blocks while the second one is filling the map 'filename_to_content'
     */
{
    protected Boolean working;

    
    private DecomposedTexFile decomposition;
    private FileProcessing processing;
    private LatexRequestMessage request_message;

    public LatexActor() 
    {
        super();
        setAcceptedType(LatexMessage.class);
    }
    public Boolean isWorking()  {return working;}
    public void setWorking()  {working=true;}

    @Override
    public LatexActorSystem getActorSystem()
    {
        return (LatexActorSystem) super.getActorSystem();
    }
    public void sendAnswer()
    {
        LatexAnswerMessage answer_message = new LatexAnswerMessage(getSelfReference(),request_message.getSender(),request_message.getFilepath());
        answer_message.setContent(decomposition.getRecomposition());

        send(answer_message,request_message.getSender());
        working=false;
    }
    public void sendRequest(File filepath)
    {
        //System.out.println("Requesting "+filepath);
        LatexActorRef to = getActorSystem().getNonWorkingActor();
        LatexRequestMessage request_message = new LatexRequestMessage(getSelfReference(),to,filepath);
        send(request_message,to);
    }
    @Override
    public LatexActorRef getSelfReference()
    {
        return (LatexActorRef) super.getSelfReference();
    }
    private void receiveRequest(Message m)
    {
        request_message = (LatexRequestMessage) m;
        decomposition = new DecomposedTexFile();
        processing = new FileProcessing(request_message.getFilepath(),decomposition,this);
        Thread t = new Thread(processing);
        t.start();
    }
    protected void receiveAnswer(Message m)
    {
        LatexAnswerMessage message = (LatexAnswerMessage) m;
        //System.out.println("Received : "+message.getFilepath().toString());
        processing.makeSubstitution(message.getFilepath(),message.getContent());
        if (processing.isFinished()) { sendAnswer(); }
    }
    @Override
    public void receive(Message m)
    /**
    * One cannot check in 'receive' if the actor is already working. 
    * <p>
    * Making
    * <code>
    *     if (isWorking()) { throw something;  }
    * </code>
    * will not work. 
    * The reason is that the latex actor system has to set this actor to working=true before to return him (if not, the same actor could be given to several files processing requests). Thus the request message will certainly be send to a working actor.
    */

    {
        super.receive(m);
        if (LatexRequestMessage.class.isInstance(m)) { receiveRequest(m); }
        if (LatexAnswerMessage.class.isInstance(m)) { receiveAnswer(m); }
    }
    public void waitWorking()
    {
        while(isWorking())
        {
            try{ Thread.sleep(10); } catch(InterruptedException a) {}
        }
    }
}
