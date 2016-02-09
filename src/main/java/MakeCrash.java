package actors;

public class MakeCrash
{
    private Integer foo;
    public MakeCrash() {}
    public String get() 
    {
        Boolean blob=true;
        if (blob)
        {
            throw new  NullPointerException();
        }
        return foo.toString();  
    }
}

