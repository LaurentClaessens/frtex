// An 'EchoActor' is an actor that does -1 on the data and resent the message if the data is still positive.


public class EchoActor implements Actor<EchoText>
{
    private Boolean is_working;
    private actors.MailBox mail_box = new actors.MailBox();
    private void process(actors.impl.EchoText m)
    {
        int data=m.getData()-1;
        if (data > 0)
        {
            actors.impl.EchoText new_message = actors.impl.EchoText(this,m.getSender(),data);
            m.from_actor
        }
        is_working=false;
    }
    private void process_next_message()
    {
        if (!is_working && mail_box.size()>0 )
        {
            actors.Message m=mail_box.poll();
            process(m);   
        }
    }

    void receive(T message)
    {
        mail_box.add(message);
        process_next_message();
    }
    void receive(Message m)
    {
        throw UnsupportedMessageException;
    }
    void send(T Message, ActorRef to)
    {
    }
}
