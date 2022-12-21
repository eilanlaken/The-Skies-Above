// 
// Decompiled by Procyon v0.5.36
// 

package com.fos.game.engine.core.graphics.ui.text.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import regexodus.Category;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CaseInsensitiveIntMap implements Iterable<CaseInsensitiveIntMap.Entry>
{
    public int size;
    protected String[] keyTable;
    protected int[] valueTable;
    protected float loadFactor;
    protected int threshold;
    protected int shift;
    protected int mask;
    protected long hashMultiplier;
    protected transient Entries entries1;
    protected transient Entries entries2;
    protected transient Values values1;
    protected transient Values values2;
    protected transient Keys keys1;
    protected transient Keys keys2;
    
    public static int tableSize(final int capacity, final float loadFactor) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be >= 0: " + capacity);
        }
        final int tableSize = 1 << -Integer.numberOfLeadingZeros(Math.max(2, (int)Math.ceil(capacity / loadFactor)) - 1);
        if (tableSize > 1073741824 || tableSize < 0) {
            throw new IllegalArgumentException("The required capacity is too large: " + capacity);
        }
        return tableSize;
    }
    
    public CaseInsensitiveIntMap() {
        this(51, 0.6f);
    }
    
    public CaseInsensitiveIntMap(final int initialCapacity) {
        this(initialCapacity, 0.6f);
    }
    
    public CaseInsensitiveIntMap(final int initialCapacity, final float loadFactor) {
        this.hashMultiplier = -7046029254386353131L;
        if (loadFactor <= 0.0f || loadFactor >= 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and < 1: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        final int tableSize = tableSize(initialCapacity, loadFactor);
        this.threshold = (int)(tableSize * loadFactor);
        this.mask = tableSize - 1;
        this.shift = Long.numberOfLeadingZeros(this.mask);
        this.keyTable = new String[tableSize];
        this.valueTable = new int[tableSize];
    }
    
    public CaseInsensitiveIntMap(final CaseInsensitiveIntMap map) {
        this((int)(map.keyTable.length * map.loadFactor), map.loadFactor);
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        System.arraycopy(map.valueTable, 0, this.valueTable, 0, map.valueTable.length);
        this.size = map.size;
    }
    
    protected int place(final String item) {
        int h;
        for (int n = h = item.length(), i = 0; i < n; ++i) {
            h = (1003 * h ^ Category.caseFold(item.charAt(i)));
        }
        return (int)(h * this.hashMultiplier >>> this.shift);
    }
    
    int locateKey(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }
        final String[] keyTable = this.keyTable;
        int i = this.place(key);
        while (true) {
            final String other = keyTable[i];
            if (other == null) {
                return ~i;
            }
            if (other.equalsIgnoreCase(key)) {
                return i;
            }
            i = (i + 1 & this.mask);
        }
    }
    
    public void put(final String key, final int value) {
        int i = this.locateKey(key);
        if (i >= 0) {
            this.valueTable[i] = value;
            return;
        }
        i ^= -1;
        this.keyTable[i] = key;
        this.valueTable[i] = value;
        if (++this.size >= this.threshold) {
            this.resize(this.keyTable.length << 1);
        }
    }
    
    public int put(final String key, final int value, final int defaultValue) {
        int i = this.locateKey(key);
        if (i >= 0) {
            final int oldValue = this.valueTable[i];
            this.valueTable[i] = value;
            return oldValue;
        }
        i ^= -1;
        this.keyTable[i] = key;
        this.valueTable[i] = value;
        if (++this.size >= this.threshold) {
            this.resize(this.keyTable.length << 1);
        }
        return defaultValue;
    }
    
    public void putAll(final CaseInsensitiveIntMap map) {
        this.ensureCapacity(map.size);
        final String[] keyTable = map.keyTable;
        final int[] valueTable = map.valueTable;
        for (int i = 0, n = keyTable.length; i < n; ++i) {
            final String key = keyTable[i];
            if (key != null) {
                this.put(key, valueTable[i]);
            }
        }
    }
    
    private void putResize(final String key, final int value) {
        String[] keyTable;
        int i;
        for (keyTable = this.keyTable, i = this.place(key); keyTable[i] != null; i = (i + 1 & this.mask)) {}
        keyTable[i] = key;
        this.valueTable[i] = value;
    }
    
    public int get(final String key, final int defaultValue) {
        final int i = this.locateKey(key);
        return (i < 0) ? defaultValue : this.valueTable[i];
    }
    
    public int getAndIncrement(final String key, final int defaultValue, final int increment) {
        int i = this.locateKey(key);
        if (i >= 0) {
            final int oldValue = this.valueTable[i];
            final int[] valueTable = this.valueTable;
            final int n = i;
            valueTable[n] += increment;
            return oldValue;
        }
        i ^= -1;
        this.keyTable[i] = key;
        this.valueTable[i] = defaultValue + increment;
        if (++this.size >= this.threshold) {
            this.resize(this.keyTable.length << 1);
        }
        return defaultValue;
    }
    
    public int remove(String key, final int defaultValue) {
        int i = this.locateKey(key);
        if (i < 0) {
            return defaultValue;
        }
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        final int oldValue = valueTable[i];
        for (int mask = this.mask, next = i + 1 & mask; (key = keyTable[next]) != null; next = (next + 1 & mask)) {
            final int placement = this.place(key);
            if ((next - placement & mask) > (i - placement & mask)) {
                keyTable[i] = key;
                valueTable[i] = valueTable[next];
                i = next;
            }
        }
        keyTable[i] = null;
        --this.size;
        return oldValue;
    }
    
    public boolean notEmpty() {
        return this.size > 0;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void shrink(final int maximumCapacity) {
        if (maximumCapacity < 0) {
            throw new IllegalArgumentException("maximumCapacity must be >= 0: " + maximumCapacity);
        }
        final int tableSize = tableSize(maximumCapacity, this.loadFactor);
        if (this.keyTable.length > tableSize) {
            this.resize(tableSize);
        }
    }
    
    public void clear(final int maximumCapacity) {
        final int tableSize = tableSize(maximumCapacity, this.loadFactor);
        if (this.keyTable.length <= tableSize) {
            this.clear();
            return;
        }
        this.size = 0;
        this.resize(tableSize);
    }
    
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        Arrays.fill(this.keyTable, null);
    }
    
    public boolean containsValue(final int value) {
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        for (int i = valueTable.length - 1; i >= 0; --i) {
            if (keyTable[i] != null && valueTable[i] == value) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsKey(final String key) {
        return this.locateKey(key) >= 0;
    }
    
    public String findKey(final int value) {
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        for (int i = valueTable.length - 1; i >= 0; --i) {
            final String key = keyTable[i];
            if (key != null && valueTable[i] == value) {
                return key;
            }
        }
        return null;
    }
    
    public void ensureCapacity(final int additionalCapacity) {
        final int tableSize = tableSize(this.size + additionalCapacity, this.loadFactor);
        if (this.keyTable.length < tableSize) {
            this.resize(tableSize);
        }
    }
    
    final void resize(final int newSize) {
        final int oldCapacity = this.keyTable.length;
        this.threshold = (int)(newSize * this.loadFactor);
        this.mask = newSize - 1;
        this.shift = Long.numberOfLeadingZeros(this.mask);
        this.hashMultiplier *= ((long)(this.size + this.size) ^ 0xF1357AEA2E62A9C5L);
        final String[] oldKeyTable = this.keyTable;
        final int[] oldValueTable = this.valueTable;
        this.keyTable = new String[newSize];
        this.valueTable = new int[newSize];
        if (this.size > 0) {
            for (int i = 0; i < oldCapacity; ++i) {
                final String key = oldKeyTable[i];
                if (key != null) {
                    this.putResize(key, oldValueTable[i]);
                }
            }
        }
    }
    
    @Override
    public int hashCode() {
        int h = this.size;
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        for (int i = 0, n = keyTable.length; i < n; ++i) {
            final String key = keyTable[i];
            if (key != null) {
                h += key.hashCode() + valueTable[i];
            }
        }
        return h;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CaseInsensitiveIntMap)) {
            return false;
        }
        final CaseInsensitiveIntMap other = (CaseInsensitiveIntMap)obj;
        if (other.size != this.size) {
            return false;
        }
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        for (int i = 0, n = keyTable.length; i < n; ++i) {
            final String key = keyTable[i];
            if (key != null) {
                final int otherValue = other.get(key, 0);
                if (otherValue == 0 && !other.containsKey(key)) {
                    return false;
                }
                if (otherValue != valueTable[i]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public String toString(final String separator) {
        return this.toString(separator, false);
    }
    
    @Override
    public String toString() {
        return this.toString(", ", true);
    }
    
    private String toString(final String separator, final boolean braces) {
        if (this.size == 0) {
            return braces ? "{}" : "";
        }
        final StringBuilder buffer = new StringBuilder(32);
        if (braces) {
            buffer.append('{');
        }
        final String[] keyTable = this.keyTable;
        final int[] valueTable = this.valueTable;
        int i = keyTable.length;
        while (i-- > 0) {
            final String key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
            break;
        }
        while (i-- > 0) {
            final String key = keyTable[i];
            if (key == null) {
                continue;
            }
            buffer.append(separator);
            buffer.append(key);
            buffer.append('=');
            buffer.append(valueTable[i]);
        }
        if (braces) {
            buffer.append('}');
        }
        return buffer.toString();
    }
    
    @Override
    public Entries iterator() {
        return this.entries();
    }
    
    public Entries entries() {
        if (this.entries1 == null) {
            this.entries1 = new Entries(this);
            this.entries2 = new Entries(this);
        }
        if (!this.entries1.valid) {
            this.entries1.reset();
            this.entries1.valid = true;
            this.entries2.valid = false;
            return this.entries1;
        }
        this.entries2.reset();
        this.entries2.valid = true;
        this.entries1.valid = false;
        return this.entries2;
    }
    
    public Values values() {
        if (this.values1 == null) {
            this.values1 = new Values(this);
            this.values2 = new Values(this);
        }
        if (!this.values1.valid) {
            this.values1.reset();
            this.values1.valid = true;
            this.values2.valid = false;
            return this.values1;
        }
        this.values2.reset();
        this.values2.valid = true;
        this.values1.valid = false;
        return this.values2;
    }
    
    public Keys keys() {
        if (this.keys1 == null) {
            this.keys1 = new Keys(this);
            this.keys2 = new Keys(this);
        }
        if (!this.keys1.valid) {
            this.keys1.reset();
            this.keys1.valid = true;
            this.keys2.valid = false;
            return this.keys1;
        }
        this.keys2.reset();
        this.keys2.valid = true;
        this.keys1.valid = false;
        return this.keys2;
    }
    
    public static class Entry
    {
        public String key;
        public int value;
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    
    private static class MapIterator
    {
        public boolean hasNext;
        final CaseInsensitiveIntMap map;
        int nextIndex;
        int currentIndex;
        boolean valid;
        
        public MapIterator(final CaseInsensitiveIntMap map) {
            this.valid = true;
            this.map = map;
            this.reset();
        }
        
        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            this.findNextIndex();
        }
        
        void findNextIndex() {
            final String[] keyTable = this.map.keyTable;
            final int n = keyTable.length;
            while (++this.nextIndex < n) {
                if (keyTable[this.nextIndex] != null) {
                    this.hasNext = true;
                    return;
                }
            }
            this.hasNext = false;
        }
        
        public void remove() {
            int i = this.currentIndex;
            if (i < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            final String[] keyTable = this.map.keyTable;
            final int[] valueTable = this.map.valueTable;
            String key;
            for (int mask = this.map.mask, next = i + 1 & mask; (key = keyTable[next]) != null; next = (next + 1 & mask)) {
                final int placement = this.map.place(key);
                if ((next - placement & mask) > (i - placement & mask)) {
                    keyTable[i] = key;
                    valueTable[i] = valueTable[next];
                    i = next;
                }
            }
            keyTable[i] = null;
            final CaseInsensitiveIntMap map = this.map;
            --map.size;
            if (i != this.currentIndex) {
                --this.nextIndex;
            }
            this.currentIndex = -1;
        }
    }
    
    public static class Entries extends MapIterator implements Iterable<Entry>, Iterator<Entry>
    {
        Entry entry;
        
        public Entries(final CaseInsensitiveIntMap map) {
            super(map);
            this.entry = new Entry();
        }
        
        @Override
        public Entry next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            final String[] keyTable = this.map.keyTable;
            this.entry.key = keyTable[this.nextIndex];
            this.entry.value = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return this.entry;
        }
        
        @Override
        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }
        
        @Override
        public Entries iterator() {
            return this;
        }
    }
    
    public static class Values extends MapIterator
    {
        public Values(final CaseInsensitiveIntMap map) {
            super(map);
        }
        
        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }
        
        public int next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            final int value = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return value;
        }
        
        public Values iterator() {
            return this;
        }
        
        public IntArray toArray() {
            final IntArray array = new IntArray(true, this.map.size);
            while (this.hasNext) {
                array.add(this.next());
            }
            return array;
        }
        
        public IntArray toArray(final IntArray array) {
            while (this.hasNext) {
                array.add(this.next());
            }
            return array;
        }
    }
    
    public static class Keys extends MapIterator implements Iterable<String>, Iterator<String>
    {
        public Keys(final CaseInsensitiveIntMap map) {
            super(map);
        }
        
        @Override
        public boolean hasNext() {
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            return this.hasNext;
        }
        
        @Override
        public String next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            if (!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }
            final String key = this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return key;
        }
        
        @Override
        public Keys iterator() {
            return this;
        }
        
        public Array<String> toArray() {
            return this.toArray((Array<String>)new Array(true, this.map.size, (Class)String.class));
        }
        
        public Array<String> toArray(final Array<String> array) {
            while (this.hasNext) {
                array.add((String) this.next());
            }
            return array;
        }
    }
}
