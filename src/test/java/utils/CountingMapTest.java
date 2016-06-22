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

import frtex.utils.CountingMap;

public class CountingMapTest
{
    @Test
    public void getTwiceTheSame() 
    {
        CountingMap<String,String> cm = new CountingMap<String,String>();
        cm.put("foo","bar1");
        cm.put("foo","bar2");
        String s1 = cm.get("foo");
        cm.remove("foo");
        String s2 = cm.get("foo");
        Assert.assertTrue(  s1.equals(s2)  );
        Assert.assertTrue(  s1.equals("bar2")  );
    }
    @Test
    public void countingIsOk() 
    {
        CountingMap<String,String> cm = new CountingMap<String,String>();
        cm.put("foo","bar1");
        cm.put("foo","bar2");
        Assert.assertTrue(cm.count("foo")==2);
        cm.remove("foo");
        Assert.assertTrue(cm.count("foo")==1);
        cm.put("foo","bar2");
        Assert.assertTrue(cm.count("foo")==2);
        cm.remove("foo");
        Assert.assertTrue(cm.count("foo")==1);
    }
}
