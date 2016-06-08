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

/*
     read the LaTeX file and return two things
     - a list of strings that are forming the file
     - a map filename -> number that indicate that "filename" is inputed in the 'number'th element of the list.
     EXAMPLE
     [  "blah bla","bla \input{other1} bloh","foo \input{other2}"   ]
     the map will contain
     "other1"  ->  1
     "other2"  ->  2
     and moreover the blocks with input will contain only one line.
//*/

package actors.impl.latex;

class DecomposedTexFile
{
    private ArrayList<String> decomposition;
    private Map<String,Integer> filename_to_number;

    void DecomposedTexFile()
    {
        decomposition = new ArrayList<String>();
        filename_to_number = HashMap<String,Integer>();
    }
    
    public addLine(String line)
    // add a line to the last block
    {
        String text=decomposition.get( decomposition.size()-1  );
        text=text+line;
        decomposition.set( decomposition.size()-1, text  );
    }
    public newLine()
    {
        decomposition.add("");
    }
}
