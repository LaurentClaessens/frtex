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


import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;


/**
    This describe a tex file in the "decomposed" state. 
    <p>
    That is
    <ul>
    <li> a list of block. Each block contains a part of the tex file.
    <li> a map filename -> number that indicate that "filename" is inputed in the 'number'th element of the list.
    <li> a map filename -> content that indicate for each filename its recursive content.
    </ul>
        
    The blocks containing an \input contain only that line. These lines are the limits of the blocks  [1]

    It is only possible to add text in the last block.

EXAMPLE

    Let the file

    <code>
    blah bla
    bla \input{other1} bloh
    foo \input{other2}
    <code>

    The block list will be

    [  "blah bla","bla \input{other1} bloh","foo \input{other2}"   ]

    filename_to_number will contain
    "other1"  ->  1
    "other2"  ->  2

    and filename_to_content will contain
    "other1"  ->  (recursive) content of file "other1.tex"
    "other2"  ->  (recursive) content of file "other2.tex"

     [1] This is the expected use. In other words, this is how LatexActor.receive use this class.
*/


class DecomposedTexFile
{
    private ArrayList<String> blocks_list;
    private Map<String,Integer> filename_to_number;
    private Map<String,String> filename_to_content;
    private StringBuilder block_builder;
    private Integer last_block;
    
    public DecomposedTexFile()
    {
        blocks_list = new ArrayList<String>();
        filename_to_number = new HashMap<String,Integer>();
        filename_to_content = new HashMap<String,String>();
        last_block=0;
        block_builder = new StringBuilder();
    }
    public void addLine(String line) { block_builder.append(line); }
    public Integer size() {return blocks_list.size();}

    private String  filenameToInputFilename(String filename)
    {
        if (filename.endsWith(".tex")) { return filename.replace(".tex",""); }
        return filename;
    }

    public void closeBlock()
        /**
         * add to the block list the content of the current buffer 'block_builder'.
         */
    {
        blocks_list.add(last_block,block_builder.toString());
    }
    public void newBlock() 
    {
        /**
         * close being building the block and initiate a new block.
         * @see closeBlock
         */
        closeBlock();
        blocks_list.add(""); 
        last_block++;
        block_builder = new StringBuilder();
    }
    public void newBlock(String filename) 
        /**
         * close being building the block and initiate a new block.
         * Associate the new block with a filename.
         * @see closeBlock(),newBlock()
         */
    { 
        newBlock();
        filename_to_number.put(filename,last_block);
    }
    public Boolean stillWaiting()
        /**
         * Return true if there are still some requests that are not yet answered.
         *
         * <p>
            A true here does not mean that the work is finished.
            It only means that all the \input encountered so far are filled.
            Maybe a new \input is still to be found.
         */
    {
        return filename_to_number.size()>0;
    }
    public void makeSubstitution(File filepath, String content)
    {
        String filename = filepath.getName().toString();
        String input_filename=filenameToInputFilename(filename);

        Integer block_number =  filename_to_number.get(input_filename);
        String initial_text=blocks_list.get(block_number);

        String input_statement = "\\input{"+input_filename+"}";
        String final_text = initial_text.replace(input_statement,content);

        blocks_list.set(block_number,final_text);
        filename_to_number.remove(input_filename);
    }
    public String getRecomposition()
    {
        StringBuilder content_builder = new StringBuilder();       
        for ( String bl : blocks_list )
        {
            content_builder.append(bl);
        }
        String content = content_builder.toString();
        // If the file is empty, the content does not finish with "\n".
        // position 23685-14680
        if (content.endsWith("\n")) { content=content.substring(0,content.length()-1); }
        return content;
    }
}
