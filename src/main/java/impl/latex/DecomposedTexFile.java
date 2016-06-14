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

DESCRIPTION

    This describe a tex file in the "decomposed" state. That is
    - a list of block. Each block contains a part of the tex file.
    - a map filename -> number that indicate that "filename" is inputed in the 'number'th element of the list.
    - a map filename -> content that indicate for each filename its recursive content.
        
    The blocks containing an \input contain only that line. These lines are the limits of the blocks  [1]

    It is only possible to add text in the last block.

EXAMPLE

    Let the file

    <begin of file>
    blah bla
    bla \input{other1} bloh
    foo \input{other2}
    <end of file>

    The block list will be

    [  "blah bla","bla \input{other1} bloh","foo \input{other2}"   ]

    filename_to_number will contain
    "other1"  ->  1
    "other2"  ->  2

    and filename_to_content will contain
    "other1"  ->  (recursive) content of file "other1.tex"
    "other2"  ->  (recursive) content of file "other2.tex"

     [1] This is the expected use. In other words, this is how LatexActor.receive use this class.
//*/

package actors.impl.latex;


import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


class DecomposedTexFile
{
    private ArrayList<String> blocks_list;
    private Map<String,Integer> filename_to_number;
    private Map<String,String> filename_to_content;
    private StringBuilder string_builder;
    private Integer last_block;

    public DecomposedTexFile()
    {
        blocks_list = new ArrayList<String>();
        filename_to_number = new HashMap<String,Integer>();
        filename_to_content = new HashMap<String,String>();
        last_block=0;
        string_builder = new StringBuilder();
    }
    public void addLine(String line) { string_builder.append(line); }
    public Integer size() {return blocks_list.size();}

    private String  filenameToInputFilename(String filename)
    {
        if (filename.endsWith(".tex")) { return filename.replace(".tex",""); }
        return filename;
    }

    
    // NEW BLOCK
    //
    // - closes the last block
    // - opens a new one
    // - If you give the 'String filename' argument, it associates the given
    //   filename with the new block. 
    //   If you give "foo" as argument, you mean that the block 
    //   will contain \input{foo}
    public void newBlock() 
    {
        blocks_list.add(last_block,string_builder.toString());
        blocks_list.add(""); 
        last_block++;
        string_builder = new StringBuilder();
    }
    public void newBlock(String filename) 
    { 
        newBlock();
        filename_to_number.put(filename,last_block);
    }
    public Boolean stillWaiting()
    // A true here does not mean that the work is finished.
    // It only means that all the \input encountered so far are filled.
    // Maybe a new \input is still to be found.
    {
        return filename_to_number.size()>0;
    }
    public void makeSubstitution(File filepath, String content)
    {
        String filename = filepath.getName().toString();
        String input_filename=filenameToInputFilename(filename);
        String initial_text=blocks_list.get(filename_to_number.get(input_filename));
        String input_statement = "\\input{"+input_filename+"}";
        initial_text=initial_text.replace(input_statement,content);
        filename_to_number.remove(filename);
    }
    public String getRecomposition()
    {
        StringBuilder content_builder = new StringBuilder();       
        for ( String bl : blocks_list )
        {
            content_builder.append(bl);
        }
        return content_builder.toString();
    }
}
