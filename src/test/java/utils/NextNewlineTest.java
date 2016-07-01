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

import frtex.utils.StringUtils;

public class NextNewlineTest
{
    @Test
    public void simpleTest() 
    {
        System.out.println("SIMPLE TEST");
        String s1="Bar\n     bla";
        String s2="Bar     bla";
        String s3="Bar    \n bla";
        String s4="Bar i       \n bla";     // In this one, there is a TAB
        String s5="Bar        \n bla";     // In this one, there is a TAB
        Assert.assertTrue(  StringUtils.isNextCharacterNewline(s1,2)  );
        Assert.assertFalse(  StringUtils.isNextCharacterNewline(s2,2)  );
        Assert.assertTrue(  StringUtils.isNextCharacterNewline(s3,2)  );
        Assert.assertFalse(  StringUtils.isNextCharacterNewline(s4,2)  );
        Assert.assertTrue(  StringUtils.isNextCharacterNewline(s5,2)  );
    }
}

