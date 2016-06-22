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
import java.util.ArrayList;
import java.util.HashMap;

public class CountingMap<K,V>
/**
 * A map which counts the number of "occurrences" of a key.
 * 
 * This map does not keep two different values for the same key, but remember that
 * you added two times the same key. Only the last value is kept.
 *
 * EXAMPLE
 *
 * ```java
 * CountingMap<String,String> cm = new CountingMap<String,String>();
 * cm.put("foo","bar1");
 * cm.put("foo","bar2");
 * cm.get("foo");
 * cm.remove("foo");
 * cm.get("foo");
 * cm.remove("foo");
 * cm.get("foo");
 * cm.remove("foo");
 * ```
 * The two first `get` will both return "bar2" (not "bar1" !), the third 'remove' will raise a NullPointerException and "bar1" is lost.
 */
{
    private Map<K,V> key_to_value;
    private Map<K,Integer> key_to_counter;

    public CountingMap()
    {
        key_to_value=new HashMap<K,V>();
        key_to_counter=new HashMap<K,Integer>();
    }

    public V get(K key) { return key_to_value.get(key); }
    public void put(K key,V value) 
    { 
        key_to_value.put(key,value);
        if (key_to_counter.containsKey(key))
        {
            key_to_counter.put(key, key_to_counter.get(key)+1  );
        }
        else { key_to_counter.put(key,1);
        }
    }
    public void remove(K key) 
    { 
        key_to_counter.put(key, key_to_counter.get(key)-1  );
        if (key_to_counter.get(key)==0)
        {
            key_to_counter.remove(key);
            key_to_value.remove(key);
        }
    }
    public Integer count(K key) { return key_to_counter.get(key); }
}
