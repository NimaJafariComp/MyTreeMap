

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyTreeMap<K extends Comparable<K>, V> implements MyMap<K , V> {
    private Node<K, V> root;
    private int size;

    public MyTreeMap() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) // no kys in tree
            return false;
        return root.nodeContainsKey(key);
    }

    @Override
    public boolean containsVal(V value) {
        if (root == null)
            return false;
        return root.nodeCanFindValue(value);
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> results = new ArrayList<>();
        if (root != null)
            root.addAllValues(results);
        return results;
    }

    @Override
    public Set<K> keys() {
        HashSet<K> results = new HashSet<>();
        if (root != null)
            root.addAllKeys(results);
        return results;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        if (size == 0) { // nothing there
            root = new Node<>(key, value);
            size = 1;
            return null;
        } else {
            AtomicBoolean addedSomething = new AtomicBoolean(false);
            V original = root.put(key, value, addedSomething);
            if (addedSomething.get())
                size++;
            return original;
        }
    }

    @Override
    public V get(K key) {
        if (root == null) // no kys in tree
            return null;
        return root.nodeGet(key);
    }

    @Override
    public V remove(K key) {
        if (root == null)
            return null;

        AtomicBoolean somethingRemoved = new AtomicBoolean(false);
        V retValue = root.removeMap(key, somethingRemoved);

        if (somethingRemoved.get()) {
            size--;
            if (size == 0)
                root = null;
        }
        return retValue;
    }
}

