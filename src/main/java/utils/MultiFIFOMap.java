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

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MultiFIFOMap<K,V>
/**
 * A map which counts the number of "occurrences" of a key.
 * 
 * Each key is associated with a FIFO list.
 *
 * EXAMPLE
 *
 * ```java
 * MultiFIFOMap<Strirg,String> cm = new MultiFIFOMap<String,String>();
 * cm.put("foo","bar1");
 * cm.put("foo","bar2");
 * cm.get("foo");       (1)
 * cm.get("foo");       (2)
 * cm.remove("foo");
 * cm.get("foo");       (3)
 * cm.remove("foo");
 * cm.get("foo");       (4)
 * ```
 * (1) and (2) are "bar1"  (3) is "bar2". (4) is a NullPointerException.
 */
{
    private Map<K, LinkedList<V>  > key_to_values;
    private Integer _size;

    public MultiFIFOMap()
    {
        key_to_values = new HashMap<K, LinkedList<V>   >();
        _size=0;
    }

    public synchronized V poll(K k) 
        /**
         * Poll the first element of the FIFO queue associated with the key `k`
         */
    {
        _size--;
        V answer = key_to_values.get(k).poll();
        if (key_to_values.get(k).size()==0)
        {
            key_to_values.remove(k);
        }
        return answer;
    }
    public synchronized void add(K k,V v) 
        /**
         * Add the value `v` at the end of the FIFO queue associated with the key `k`.
         */
    { 
        if (key_to_values.containsKey(k))
        {
            key_to_values.get(k).add(v);
        }
        else
        {
            LinkedList<V> ll = new LinkedList<V>();
            ll.add(v);
            key_to_values.put(k,ll);
        }
        _size++;
    }
    public Integer size() 
        /**
         * Return the number of keys, counting the multiplicity.
         *
         */
    { return _size; }
    public Integer count(K k)
        /**
         * Return the number of values associated with the key `k`.
         */
    {return key_to_values.get(k).size();}
    public Set<K> keySet()
    {
        return key_to_values.keySet();
    }
}
