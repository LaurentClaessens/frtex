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

import java.io.BufferedReader;

/** 
 * This is a wrapper for BufferedReader for which a `readLine` returns a line including a "\n" when the end of file is not reached.
 *
 * ### EXAMPLE
 *
 * For a buffer containing
 *
 * ```
 * A
 * B
 * ```
 * It returns first A\n then B.
 *
 */

public class LineReader implements AutoCloseable
{
    private BufferedReader buffer;
    private String current_line;
    private String next;

    public LineReader(BufferedReader br) throws  java.io.IOException
    {
        buffer=br;
        next=br.readLine();
    }
    public String readLine() throws  java.io.IOException
    {
        current_line=next;
        if (current_line==null)
        {
            return null;
        }
        next=buffer.readLine();
        if (next==null)
        {
            return current_line;  // In this case, 'current_line' is the last line and then there are no \n
        }
        return current_line+"\n";
    }
    public void close() throws  java.io.IOException
    {
        buffer.close();
    }
}


