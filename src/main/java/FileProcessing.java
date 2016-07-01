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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Character;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import java.util.Map;
import java.util.HashMap;

import frtex.LatexActors.LatexActor;
import frtex.LatexActors.LatexMainActor;
import frtex.LatexActors.LatexActorRef;
import frtex.LatexActors.LatexActorSystem;

import frtex.exceptions.BadInputException;
import frtex.utils.StringUtils;
import frtex.utils.LineReader;

public class FileProcessing implements Runnable
/*
   This class is intended to be launch in a separate thread. It fills the blocks of the 'decomposed' tex file of the calling actor.
   In the same time, the calling actor receive messages that fill the 'filename_to_content' map.
//*/
{
    private File filepath;
    private LatexActor calling_actor;
    private DecomposedTexFile decomposed_file;
    private Boolean parsing;
    private Path pwd;

    public FileProcessing(File filepath, DecomposedTexFile decomposed, LatexActor calling_actor)
    {
        this.filepath=filepath;
        this.decomposed_file=decomposed;
        this.calling_actor=calling_actor;
        pwd=Paths.get(filepath.getParent());
    }
    private String getFilename() { return filepath.getName().toString();  }
    public Boolean isFinished()
    {
        if (parsing) {return false;}
        return !decomposed_file.stillWaiting();
    }
    public void makeSubstitution(File filepath, String content)
    {
        decomposed_file.makeSubstitution(filepath,content);
    }
    private File inputFilenameToFilename(String input_filename)
    {
        String filename;
        if (input_filename.indexOf(".")>=0) { filename=input_filename; }
        else { filename=input_filename+".tex";}
        return pwd.resolve(Paths.get(filename)).toFile();
    }

    private Integer commentPosition(String line)
        /**
         * Return the position at which the line begins to be a comment 
         *
         * The comment part initiate at the first "%" which is not
         * preceded by a "\".
         *
         * If no comment, Return -1.
         *
         * There are some limitations. Something like \\% will be considered as the beginning of a comment, and the first \ will be kept.
         *
         * @return : a new String.
         */
    {
        if (line.length()==0) {return -1;}
        if (Character.toString(line.charAt(0)).equals("%")) 
        {
            return 0;
        }
        Pattern pattern=Pattern.compile("[^\\\\]%");
        Matcher m = pattern.matcher(line);
        if (!m.find()) { return -1; }
        return m.start()+1;
    }
    private String removeComment(String line)
        /**
         * Remove the comment on a line, but leave the % at the end.
         */
    {
        Integer start_comment = commentPosition(line);
        Boolean finish_by_newline=line.endsWith("\n");
        if (start_comment==-1)
        {
            return line;
        }
        if (start_comment==0)
        {
            if (finish_by_newline)
            {
                return "%\n";
            }
            else 
            {
                return "%";
            }
        }
        if (finish_by_newline)
        {
            return line.substring(0,start_comment)+"%\n";
        }
        else
        {
            return line.substring(0,start_comment)+"%";
        }
    }
    private void extractInput(String line)
        /**
         * Found the \input in the line and creates the corresponding blocks.
         * <p>
         * No return. It directly creates and manages the blocks.
         *
         */
    {
        int input_index = line.indexOf("\\input{");
        if (input_index<0) 
        { 
            decomposed_file.addString(line); 
        }
        else 
        { 
            decomposed_file.addString(line.substring(0,input_index));
            decomposed_file.closeBlock();

            int end_index=line.indexOf("}",input_index);
            String input_filename=line.substring(input_index+7,end_index);

            if (input_filename.endsWith(".tex"))
            {
                throw new BadInputException(input_filename+" : If your LaTeX code has to input a .tex file, you must not explicitly write '.tex'. See README.md");
            }
            if (StringUtils.stringCounter(input_filename,'.')>1)
            {
                throw new BadInputException(input_filename+" : Your filename must contain at most one dot. See README.md");
            }

            String input_macro = line.substring(input_index,end_index+1);

            decomposed_file.newBlock(input_filename);
            decomposed_file.addString(input_macro);
            calling_actor.sendRequest(inputFilenameToFilename(input_filename));

            // If \input{foo} is not followed by a \n 
            // we add a \n.
            // That seems to be the behaviour of TeX.
            // The following question is not really answered :
            // http://tex.stackexchange.com/questions/317361/how-does-input-adds-a-space
            if (!StringUtils.isNextCharacterNewline(line,end_index))
            {
                decomposed_file.addString("\n");
            }

            decomposed_file.closeBlock();
            String remain_line=line.substring(end_index+1,line.length());
            extractInput(remain_line);
        }
    }
    public void run() 
    {
        String line;
        try (
            InputStream fis = new FileInputStream(filepath.toString());
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            LineReader lr = new LineReader(br);
            )
        {
            parsing=true;
            while ((line = lr.readLine()) != null) 
            {
                String original = line;
                line = removeComment(line);
                extractInput(line);
             }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found : "+getFilename());
            decomposed_file.addString("\\huge FILE NOT FOUND : "+getFilename());
        }
        catch (IOException e)
        {
            System.out.println("IO Error on file "+getFilename());
            decomposed_file.addString("\\huge IO ERROR ON FILE : "+getFilename());
        }
        decomposed_file.closeBlock();
        parsing=false;

        // TWO SPECIAL CASES
        //
        // 1.  The tex file has no input.
        // 
        // 2. It received the last answer BEFORE to finish
        // parsing the last block. 
        //
        // In both cases, 'receive' function will not trigger the sending 
        // of the answer.
        if (decomposed_file.size()==1 || isFinished())
        {
            calling_actor.sendAnswer();
        }
    }
}
