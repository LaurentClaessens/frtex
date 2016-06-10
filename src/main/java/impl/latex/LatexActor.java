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

package actors.impl.latex;

import actors.Message;
import java.util.HashMap;
import actors.exceptions.ShouldNotHappenException;
import actors.DecentActor;

// A LatexActor is 'working' until it succeed to send the answer to who asked that.
// It is constructing the decomposition outside the 'receive' function because it is sending messages while decomposing (each time an input is found)
// Therefore it is possible to receive answers before completing the decomposition and its mailbox has to be open before to initiate the decomposition.
// To be clear : the 'working' attribute is not related to the openness of the mailbox.
public class LatexActor extends DecentActor
{
    private Boolean working;

    // The 'decomposition' object is shared between the file processing thread and the 'received' function.
    // The first one is adding new blocks while the second one is filling the map 'filename_to_content'
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
    private void sendAnswer(LatexRequestMessage message)
    {
        LatexAnswerMessage answer_message = new LatexAnswerMessage(getSelfReference(),message.getSender(),"answer",message.getFilename());
        answer_message.setContent(decomposition.getRecomposition());

        send(answer_message,message.getSender());
        working=false;
    }
    protected void sendRequest(String filename)
    {
        System.out.println("Requesting "+filename);
        LatexActorRef to = getActorSystem().getNonWorkingActor();
        LatexRequestMessage request_message = new LatexRequestMessage(getSelfReference(),to,filename);
        send(request_message,to);
    }
    @Override
    public LatexActorRef getSelfReference()
    {
        return (LatexActorRef) super.getSelfReference();
    }
    @Override
    public void receive(Message m)
    {
        if (!getAcceptedType().isInstance(m)) 
        { 
            throw new ShouldNotHappenException("A message of type different from 'LatexMessage' is received by the LaxteActor."); 
        }

        if (LatexRequestMessage.class.isInstance(m))
        {
            if (isWorking())
            {
                throw new ShouldNotHappenException("This actor is already working and should not reveive new requests."); 
            }
            request_message = (LatexRequestMessage) m;
            decomposition = new DecomposedTexFile();
            processing = new FileProcessing(request_message.getFilename(),decomposition,this);
            Thread t = new Thread(processing);
            t.start();
        }

        if (LatexAnswerMessage.class.isInstance(m))
        {
            LatexAnswerMessage message = (LatexAnswerMessage) m;
            processing.makeSubstitution(message.getFilename(),message.getContent());
            if (processing.isFinished())
            {
                sendAnswer(request_message);
            }
        }

    }
}
