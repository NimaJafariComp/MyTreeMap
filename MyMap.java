
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface MyMap<K extends Comparable<K>, V> {
    void clear ();

    boolean isEmpty();

    int size();

    V put (K key, V value);

    V get (K key);                               //NEED (no traverse)

    boolean containsKey (K key);  //O(log n)      //NEED (no traverse)

    boolean containsVal (V value); //O(n)         //NEED

    Collection<V> values(); // holds all values  //NEED

    Set<K> keys(); // holds all keys, but we don't need collection cuz keys are numbers //NEED

    V remove (K key); //removing the key and the mapping that key represented //NEED
}
