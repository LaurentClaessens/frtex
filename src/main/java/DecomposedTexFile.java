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

package frtex;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import java.util.StringJoiner;

import frtex.utils.MultiFIFOMap;


/**
    This describe a tex file in the "decomposed" state. 
    <p>
    That is
    <ul>
    <li> a list of block. Each block contains a part of the tex file.
    <li> a map filename -> number that indicate that "filename" is inputed in the 'number'th element of the list.
    <li> a map filename -> content that indicate for each filename its recursive content.
    </ul>
        
    Blocks that are containing a \input contains only that statement[1].

    It is only possible to add text in the last block.

    [1] this is the expected use. That rule is in fact enforced in the function extractInput in FileProcessing.java

EXAMPLE

    Let the file

    <code>
    blah bla
    bla \input{other1} bloh
    foo \input{other2}
    <code>

    The block list will be

    [  "blah bla","bla ","\input{other1}"," bloh","foo ","\input{other2}"   ]

    filename_to_number will contain
    "other1"  ->  1
    "other2"  ->  2

    and filename_to_content will contain
    "other1"  ->  (recursive) content of file "other1.tex"
    "other2"  ->  (recursive) content of file "other2.tex"

     [1] This is the expected use. In other words, this is how LatexActor.receive use this class.
*/

public class DecomposedTexFile
{
    private ArrayList<DecompositionBlock> blocks_list;
    private MultiFIFOMap<String,DecompositionBlock> filename_to_block;
    private Map<String,String> filename_to_content;
    private Integer last_block;
    private DecompositionBlock current_block;
    
    public DecomposedTexFile()
    {
        blocks_list = new ArrayList<DecompositionBlock>();
        filename_to_block = new MultiFIFOMap<String,DecompositionBlock>();
        filename_to_content = new HashMap<String,String>();
        current_block = new DecompositionBlock();
    }
    public void addString(String s) 
    { 
        if (current_block.isOpen()) 
        {
            current_block.addString(s); 
        }
        else
        {
            createNewBlock();
            current_block.addString(s);
        }
    }
    private void createNewBlock()
    {
        current_block = new DecompositionBlock();
    }
    public void attachCurrentBlock()
        /**
         * Attach current_block (the one being filled) to the chain.
         */
    {
        if (!current_block.isAttached())
        {
            blocks_list.add(current_block);
            current_block.setAttached();
        }
    }
    public void newBlock() 
    {
        attachCurrentBlock();
        createNewBlock();
    }
    public void closeBlock()
    {
        current_block.close();
        attachCurrentBlock();
    }
    public void newBlock(String filename) 
        /**
         * close being building the block and initiate a new block.
         * Associate the new block with a filename.
         * @see closeBlock(),newBlock()
         */
    { 
        newBlock();
        filename_to_block.add(filename,current_block);
    }
    public Integer size() {return blocks_list.size();}

    private String  filenameToInputFilename(String filename)
    {
        if (filename.endsWith(".tex")) { return filename.replace(".tex",""); }
        return filename;
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
        return filename_to_block.size()>0;
    }
    public void makeSubstitution(File filepath, String content)
    {
        String filename = filepath.getName().toString();
        String input_filename=filenameToInputFilename(filename);

        DecompositionBlock block =  filename_to_block.poll(input_filename);

        if (block==null)
        {
            System.out.println("Seem to have a problem with "+filename);
        }
        String initial_text=block.getText();

        String input_statement = "\\input{"+input_filename+"}";
        String final_text = initial_text.replace(input_statement,content);

        block.setText(final_text);
    }
    public String getRecomposition()
    {
        StringBuilder content_builder = new StringBuilder();
        for ( DecompositionBlock bl : blocks_list )
        {
            content_builder.append(bl.getText());
        }
        return content_builder.toString();
    }
    public String show()
        // for debug purpose
    {
        StringBuilder show_builder = new StringBuilder();       
        show_builder.append("->");
        for ( DecompositionBlock bl : blocks_list )
        {
            show_builder.append("---\n");
            show_builder.append(bl.getText());
            show_builder.append("---\n");
        }
        show_builder.append("-- current --\n");
        show_builder.append(current_block.getText());
        show_builder.append("-- end current --\n");

        show_builder.append("<-");
        return show_builder.toString();
    }
}
