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

import actors.impl.latex.LatexCode;

public class LatexTest
{
    @Test
    public void test_content() throws InterruptedException
    {
        System.out.println("DÉPART DU TEST LATEX");

        LatexCode latex_code = new LatexCode("src/test/java/latex/tex_files/test.tex");
        String answer = latex_code.getExplicitCode();
        System.out.println(answer);

        System.out.println("Le fil principal LATEX se relance");
    }
}
