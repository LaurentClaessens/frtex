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

import frtex.utils.MultiFIFOMap;

public class MultiFIFOMapTest
{
    @Test
    public void getTwiceTheSame() 
    {
        MultiFIFOMap<String,String> cm = new MultiFIFOMap<String,String>();
        cm.add("foo","bar1");
        cm.add("foo","bar2");
        String s1 = cm.poll("foo");
        String s2 = cm.poll("foo");
        Assert.assertTrue( s1.equals("bar1") );
        Assert.assertTrue( s2.equals("bar2") );
    }
    @Test
    public void countingIsOk() 
    {
        MultiFIFOMap<String,String> cm = new MultiFIFOMap<String,String>();
        cm.add("foo","bar1");
        cm.add("foo","bar2");
        Assert.assertTrue(cm.count("foo")==2);
        cm.poll("foo");
        Assert.assertTrue(cm.count("foo")==1);
        cm.add("foo","bar2");
        Assert.assertTrue(cm.count("foo")==2);
        cm.poll("foo");
        Assert.assertTrue(cm.count("foo")==1);
    }
    @Test
    public void sizeIsOk() 
    {
        MultiFIFOMap<String,String> cm = new MultiFIFOMap<String,String>();
        cm.add("foo","bar1");
        cm.add("foo","bar2");
        Assert.assertTrue(cm.size()==2);
        cm.poll("foo");
        Assert.assertTrue(cm.size()==1);
        cm.add("cao","bao1");
        Assert.assertTrue(cm.size()==2);
        cm.poll("foo");
        cm.poll("cao");
        Assert.assertTrue(cm.size()==0);
    }
}
