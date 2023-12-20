package ru.spbstu.telematics.java;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMultiKeyMapTest {
    static final Integer I1 = 1;
    static final Integer I2 = 2;
    static final Integer I3 = 3;
    static final Integer I4 = 4;

    public String[] getSampleValues() {
        return new String[]{
                "2A", "2B", "2C",
        };
    }

    @SuppressWarnings("unchecked")
    private MyMultiKey<Integer>[] getMultiKeyKeys() {
        return new MyMultiKey[]{
                new MyMultiKey<>(I1, I2),
                new MyMultiKey<>(I2, I3),
                new MyMultiKey<>(I3, I4),
        };
    }

    @Test
    public void testMultiKeyGet() {
        final MyMultiKey<Integer>[] keys = getMultiKeyKeys();
        final String[] values = getSampleValues();
        final MyMultiKeyMap<Integer, String> myMap = new MyMultiKeyMap<>();
        final MultiKeyMap<Integer, String> map = new MultiKeyMap<>();
        for (int i = 0; i < keys.length; i++) {
            myMap.put(keys[i], values[i]);
            map.put(keys[i].getKey(0), keys[i].getKey(1), values[i]);
        }

        for (int i = 0; i < keys.length; i++) {
            final MyMultiKey<Integer> key = keys[i];
            final String value = values[i];

            assertEquals(value, myMap.get(key.getKey(0), key.getKey(1)));
            assertEquals(value, map.get(key.getKey(0), key.getKey(1)));

            assertNull(myMap.get(null, key.getKey(1)));
            assertNull(map.get(null, key.getKey(1)));

            assertNull(myMap.get(key.getKey(0), null));
            assertNull(map.get(key.getKey(0), null));

            assertNull(myMap.get(null, null));
            assertNull(map.get(null, null));
        }
    }

    @Test
    public void testMultiKeyContainsKey() {
        final MyMultiKey<Integer>[] keys = getMultiKeyKeys();
        final String[] values = getSampleValues();
        final MyMultiKeyMap<Integer, String> myMap = new MyMultiKeyMap<>();
        final MultiKeyMap<Integer, String> map = new MultiKeyMap<>();
        for (int i = 0; i < keys.length; i++) {
            myMap.put(keys[i], values[i]);
            map.put(keys[i].getKey(0), keys[i].getKey(1), values[i]);
        }

        for (final MyMultiKey<Integer> key : keys) {
            assertTrue(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertTrue(map.containsKey(key.getKey(0), key.getKey(1)));

            assertFalse(myMap.containsKey(null, key.getKey(1)));
            assertFalse(map.containsKey(null, key.getKey(1)));

            assertFalse(myMap.containsKey(key.getKey(0), null));
            assertFalse(map.containsKey(key.getKey(0), null));

            assertFalse(myMap.containsKey(null, null));
            assertFalse(map.containsKey(null, null));
        }
    }

    @Test
    public void testMultiKeyPut() {
        final MyMultiKey<Integer>[] keys = getMultiKeyKeys();
        final String[] values = getSampleValues();

        for (int i = 0; i < keys.length; i++) {
            final MyMultiKeyMap<Integer, String> myMap = new MyMultiKeyMap<>();
            final MultiKeyMap<Integer, String> map = new MultiKeyMap<>();

            final MyMultiKey<Integer> key = keys[i];
            final String value = values[i];

            myMap.put(key.getKey(0), key.getKey(1), value);
            map.put(key.getKey(0), key.getKey(1), value);

            assertEquals(1, myMap.size());
            assertEquals(1, map.size());

            assertEquals(value, myMap.get(key.getKey(0), key.getKey(1)));
            assertEquals(value, map.get(key.getKey(0), key.getKey(1)));

            assertTrue(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertTrue(map.containsKey(key.getKey(0), key.getKey(1)));

            assertTrue(myMap.containsKey(new MyMultiKey<>(key.getKey(0), key.getKey(1))));
            assertTrue(map.containsKey(new MultiKey<>(key.getKey(0), key.getKey(1))));

            myMap.put(key.getKey(0), key.getKey(1), null);
            map.put(key.getKey(0), key.getKey(1), null);

            assertEquals(1, myMap.size());
            assertEquals(1, map.size());

            assertNull(myMap.get(key.getKey(0), key.getKey(1)));
            assertNull(map.get(key.getKey(0), key.getKey(1)));

            assertTrue(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertTrue(map.containsKey(key.getKey(0), key.getKey(1)));
        }
    }

    @Test
    public void testMultiKeyPutWithNullKey() {
        final MyMultiKeyMap<String, String> myMap = new MyMultiKeyMap<>();
        final MultiKeyMap<String, String> map = new MultiKeyMap<>();

        myMap.put("a", null, "value1");
        map.put("a", null, "value1");
        
        myMap.put("b", null, "value2");
        map.put("b", null, "value2");
        
        myMap.put("c", null, "value3");
        map.put("c", null, "value3");
        
        myMap.put("a", "z", "value4");
        map.put("a", "z", "value4");
        
        myMap.put("a", null, "value5");
        map.put("a", null, "value5");
        
        myMap.put(null, "a", "value6");
        map.put(null, "a", "value6");
        
        myMap.put(null, null, "value7");
        map.put(null, null, "value7");

        assertEquals(6, myMap.size());
        assertEquals(6, map.size());
        
        assertEquals("value5", myMap.get("a", null));
        assertEquals("value5", map.get("a", null));
        
        assertEquals("value4", myMap.get("a", "z"));
        assertEquals("value4", map.get("a", "z"));
        
        assertEquals("value6", myMap.get(null, "a"));
        assertEquals("value6", map.get(null, "a"));
    }

    @Test
    public void testMultiKeyRemove() {
        final MyMultiKey<Integer>[] keys = getMultiKeyKeys();
        final String[] values = getSampleValues();

        final MyMultiKeyMap<Integer, String> myMap = new MyMultiKeyMap<>();
        final MultiKeyMap<Integer, String> map = new MultiKeyMap<>();
        
        for (int i = 0; i < keys.length; i++) {
            myMap.put(keys[i], values[i]);
            map.put(keys[i].getKey(0), keys[i].getKey(1), values[i]);
        }

        for (int i = 0; i < keys.length; i++) {
            final int size = myMap.size();

            final MyMultiKey<Integer> key = keys[i];
            final String value = values[i];

            assertTrue(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertTrue(map.containsKey(key.getKey(0), key.getKey(1)));
            
            assertEquals(value, myMap.removeMultiKey(key.getKey(0), key.getKey(1)));
            assertEquals(value, map.removeMultiKey(key.getKey(0), key.getKey(1)));
            
            assertFalse(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertFalse(map.containsKey(key.getKey(0), key.getKey(1)));
            
            assertEquals(size - 1, myMap.size());
            assertEquals(size - 1, map.size());
            
            assertNull(myMap.removeMultiKey(key.getKey(0), key.getKey(1)));
            assertNull(map.removeMultiKey(key.getKey(0), key.getKey(1)));
            
            assertFalse(myMap.containsKey(key.getKey(0), key.getKey(1)));
            assertFalse(map.containsKey(key.getKey(0), key.getKey(1)));
        }
    }

    @Test
    public void testIterator() {
        final MyMultiKey<Integer>[] keys = getMultiKeyKeys();
        final String[] values = getSampleValues();
        final MyMultiKeyMap<Integer, String> myMap = new MyMultiKeyMap<>();

        for (int i = 0; i < keys.length; i++) {
            myMap.put(keys[i], values[i]);
        }

        int count = 0;
        for (MyMap.Entry<MyMultiKey<? extends Integer>, String> x : myMap) {
            assertTrue(myMap.containsKey(x.getKey()));
            assertEquals(myMap.get(x.getKey()), x.getValue());
            count++;
        }
        assertEquals(myMap.size(), count);
    }
}