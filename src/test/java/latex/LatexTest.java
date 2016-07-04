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
    public Boolean isTexOk(String filename,String expected,Boolean doWrite)
    {
        LatexCode latex_code = new LatexCode(filename);
        String answer = latex_code.getExplicitCode();
        Path expected_path=Paths.get(expected);
        Path directory=expected_path.getParent();
        Path obtained_path = directory.resolve(Paths.get("obtained_result.tex"));
        try
        (  
            PrintWriter out = new PrintWriter(obtained_path.toString());
        ) {
                out.println( answer ); 
        }
        catch (FileNotFoundException e) {}
        if (doWrite)
        {
            try(  PrintWriter out = new PrintWriter(expected)  ) { out.println( answer ); } catch (FileNotFoundException e) {}
        }
        
        List<String> lines;
        String expected_content;
        try
        {
            lines = Files.readAllLines( expected_path , StandardCharsets.UTF_8);
            expected_content = String.join("\n",lines);
            return expected_content.equals(answer);
        }
        catch(IOException e){System.out.println("Your expected test file does not exist ?");}
        return false;
    }
    public Boolean isTexOk(String filename,String expected)
    {
        return isTexOk(filename,expected,false);
    }
    @Test
    public void mazheTest() throws InterruptedException
    {
        System.out.println("MAZHE TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/mazhe_tex_test/mazhe.tex","src/test/java/latex/mazhe_tex_test/expected_result.tex"  )  );
    }
    @Test
    public void ecm4Test() throws InterruptedException
    {
        System.out.println("ECM 4 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm4_tex_test/ecm4.tex","src/test/java/latex/ecm4_tex_test/expected_result.tex" )  );
    }
    @Test
    public void ecm6Test() throws InterruptedException
    /**
     * This test is merely a pure LaTeX test. When inputing a [.pstricks](https://github.com/LaurentClaessens/pstricks) file I encounter two problems
     * - \@ifundefined does not work inside \text  (for some reasons) and the LaTeX code produced by frtex from [mazhe](https://github.com/LaurentClaessens/mazhe)  is not compilable.
     * - there were too many \write open, once again because of a malfunction of \@ifundefined.
     *
     *   The common solution is to use 
     *   ```latex
     *   \ifthenelse{ \isundefined{...}  } {...}{...}
     *   ```
     */
    {
        System.out.println("ECM 6 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm6_tex_test/ecm6.tex","src/test/java/latex/ecm6_tex_test/expected_result.tex" )  );
    }
    @Test
    public void ecm5Test() throws InterruptedException
    {
        System.out.println("ECM 5 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm5_tex_test/ecm5.tex","src/test/java/latex/ecm5_tex_test/expected_result.tex"  )  );
    }
    @Test
    public void MultipleInputTest() throws InterruptedException
    {
        System.out.println("MULTIPLE INPUT TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/multiple_input_tex_test/test.tex","src/test/java/latex/multiple_input_tex_test/expected_result.tex"  )  );
    }
    @Test
    public void ecm1Test() throws InterruptedException
    {
        System.out.println("ECM 1 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm1_tex_test/ecm1.tex","src/test/java/latex/ecm1_tex_test/expected_result.tex"  )  );
    }
    @Test
    public void ecm3Test() throws InterruptedException
    {
        System.out.println("ECM 3 TEST");
        Assert.assertTrue( isTexOk(  "src/test/java/latex/ecm3_tex_test/ecm3.tex","src/test/java/latex/ecm3_tex_test/expected_result.tex" )  );
    }
    //*/
}
