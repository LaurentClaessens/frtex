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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import frtex.LatexCode;

public class LatexTest
{
    @Test
    public void test_simple() throws InterruptedException
    {
        LatexCode latex_code = new LatexCode("src/test/java/latex/simple_tex_test/test.tex");
        String answer = latex_code.getExplicitCode();
        String expected_path="src/test/java/latex/simple_tex_test/expected_test.tex";
        
        List<String> lines;
        String expected_content;
        try
        {
            lines = Files.readAllLines(Paths.get( expected_path  ), StandardCharsets.UTF_8);
            expected_content = String.join("\n",lines);
            Assert.assertTrue( expected_content.equals(answer) );
        }
        catch(IOException e){}

        
        // For the record, the following line writes the result in the file. And also, there is a simpler alternative from FileUtils.
        //try(  PrintWriter out = new PrintWriter("src/test/java/latex/simple_tex_test/expected_test.tex" )  ) { out.println( answer ); } catch (FileNotFoundException e) {}
        //String expected_content = readFileToString(new File("src/test/java/latex/simple_tex_test/expected_test.tex"),Charset.UTF_8);
    }
}
