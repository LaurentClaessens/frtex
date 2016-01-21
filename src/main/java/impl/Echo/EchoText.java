// 'EchoText' is a message that is supposed to be resent with a -1 on the data.


public class EchoText implements Message<int>
{

    private actors.Actor from_actor;
    private actors.Actor to_actor;
    private int data;

    EchoText(actors.Actor from,actors.Actor to,int d)
    {
        from_actor=from;
        to_actor=to;
        data=d;
    }

    public String getTag() {return "echo";} 
    public String getData()
    {
        return data;
    }
}
