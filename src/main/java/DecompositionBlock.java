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

class DecompositionBlock
/**
 * Represent a block of LaTeX code that one fills.
 */
{
    private StringBuilder block_builder;
    private Boolean is_open;
    private Boolean is_attached;
    private String text;

    DecompositionBlock()
    {
        block_builder = new StringBuilder();
        is_open=true;
        is_attached=false;
        text=null;
    }

    public Boolean isOpen() {return is_open;}
    public Boolean isAttached() {return is_attached;}
    public void close() {is_open=false;}
    public void setAttached() {is_attached=true;}
    public void addString(String s) { block_builder.append(s); }
    public void setText(String s) 
        /**
         * Force the text contained in the block.
         *
         * <p>
         * Usually the text of a block is the one contained in block_builder. The String setted by setText overrides that one.
         *
         * @see getText()
         */
    { 
        text=s; 
    }
    public String getText() 
    {
        if (text!=null) {return text;}
        return block_builder.toString(); 
    }
}
