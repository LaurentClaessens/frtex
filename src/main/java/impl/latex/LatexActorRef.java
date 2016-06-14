package actors.impl.latex;

import actors.DecentActorRef;

public class LatexActorRef extends DecentActorRef 
{
    @Override
    public LatexActor getActor()
    {
        return (LatexActor) super.getActor();
    }
    public void waitWorking()
    {
        getActor().waitWorking();
    }
}
