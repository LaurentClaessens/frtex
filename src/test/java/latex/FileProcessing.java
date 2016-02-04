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

public class FileProcessing implements Runnable
{
    private LatexActor caller;
    private String filename;
    private String content;

    FileProcessing(LatexActor caller,String filename) 
    {
        this.caller=caller;
        this.filename=filename;
        content="";
    }
    public void run() 
    {
        BufferReader reader = new BufferReader( new FileReader(filename)  );
        while (String line = reader.readLine())
        {
            System.out.println(line);
        }
    }
}
