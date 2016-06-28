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
    public Boolean isTexOk(String filename,String expected)
    {
        LatexCode latex_code = new LatexCode(filename);
        String answer = latex_code.getExplicitCode();

        // Uncomment that line to record the answer in 'expected_test.tex'.
        // try(  PrintWriter out = new PrintWriter(expected)  ) { out.println( answer ); } catch (FileNotFoundException e) {}

        String expected_path=expected;
        
        List<String> lines;
        String expected_content;
        try
        {
            lines = Files.readAllLines(Paths.get( expected_path  ), StandardCharsets.UTF_8);
            expected_content = String.join("\n",lines);
            return expected_content.equals(answer);
        }
        catch(IOException e){System.out.println("Your expected test file does not exist ?");}
        return false;
        
    }
    @Test
    public void simpleTest() throws InterruptedException
    {
        System.out.println("SIMPLE TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/simple_tex_test/test.tex","src/test/java/latex/simple_tex_test/expected_test.tex"  )  );
    }
    @Test
    public void MultipleInputTest() throws InterruptedException
    {
        System.out.println("MULTIPLE INPUT TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/multiple_input_tex_test/test.tex","src/test/java/latex/multiple_input_tex_test/expected_test.tex"  )  );
    }
    //@Test
    public void mazheTest() throws InterruptedException
    {
        System.out.println("MAZHE TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/mazhe_tex_test/mazhe.tex","src/test/java/latex/mazhe_tex_test/expected_test.tex"  )  );
    }
    @Test
    public void ecmOneTest() throws InterruptedException
    {
        System.out.println("ECM 1 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm1_tex_test/ecm1.tex","src/test/java/latex/ecm1_tex_test/expected_test.tex"  )  );
    }
    @Test
    public void ecmTwoTest() throws InterruptedException
    {
        System.out.println("ECM 2 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm2_tex_test/ecm2.tex","src/test/java/latex/ecm2_tex_test/expected_test.tex"  )  );
    }
}
