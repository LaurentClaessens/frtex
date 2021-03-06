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

package frtex.utils;

/**
 * # LARGE
 *
 * text
 */

public class StringUtils
{
    public static int stringCounter(String s, char c)
        /**
         * Cout how many times 'c' appears in 's'.
         */
    {
        int counter = 0;
        for( int i=0; i<s.length(); i++ ) 
        {
            if( s.charAt(i)==c ) { counter++; } 
        }
        return counter;
    }
    public static Boolean isNextCharacterNewline(String s,int start)
        /**
         * Says if from position `start`, the next (non-space) character is a newline.
         *
         * In `Foo   \n` you get True when the `start` argument is 2 or more.
         */
    {
        String test=s.substring( start+1,s.length()  ).replace(" ","").replace("   ","");      // The second one is a TAB (*)
        return test.startsWith("\n");
    }
}



// (*) Who on Earth use TABs in a LaTeX code ?
