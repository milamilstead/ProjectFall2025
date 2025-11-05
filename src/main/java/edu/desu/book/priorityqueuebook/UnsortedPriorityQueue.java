package edu.desu.book.priorityqueuebook;

//import edu.desu.book.listsiterators.Entry;
//import edu.desu.book.listsiterators.LinkedPositionalList;
//import edu.desu.book.listsiterators.Position;
import edu.desu.book.listsiterators.*;
import java.util.Comparator;

/** An implementation of a priority queue with an unsorted list. */
public class UnsortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    /** primary collection of priority queue entries */
    // OLD here
    //private PositionalList<LinkedPositionalList.Entry<K,V>> list = new LinkedPositionalList<>();
    // NEW fix here
    private LinkedPositionalList<Entry<K,V>> list = new LinkedPositionalList<>();

    /** Creates an empty priority queue based on the natural ordering of its keys. */
    public UnsortedPriorityQueue() { super(); }

    /** Creates an empty priority queue using the given comparator to order keys. */
    public UnsortedPriorityQueue(Comparator<K> comp) { super(comp); }

    /** Returns the Position of an entry having minimal key. */
    private Position<Entry<K,V>> findMin() {    // only called when nonempty
        Position<Entry<K,V>> small = list.first();
        /* Old Code - Bad
        for (Position<LinkedPositionalList.Entry<K,V>> walk : list.positions())
            if (compare(walk.getElement(), small.getElement()) < 0)
                small = walk;      // found position with smaller key
        */
        // New Code - Good
        for (Position<Entry<K,V>> walk : list.positions())
            if (compare(walk.getElement(), small.getElement()) < 0)
                small = walk;      // found position with smaller key
        return small;
    }

    /** Inserts a key-value pair and returns the entry created. */
    public Entry<K,V> insert(K key, V value) {
        Entry<K,V> newest = createEntry(key, value);
        list.addLast(newest);
        return newest;
    }

    /** Returns (but does not remove) an entry with minimal key. */
    public Entry<K,V> min() {
        if (list.isEmpty()) return null;
        return findMin().getElement();
    }

    /** Removes and returns an entry with minimal key. */
    public Entry<K,V> removeMin() {
        if (list.isEmpty()) return null;
        return list.remove(findMin());
    }

    /** Returns the number of items in the priority queue. */
    public int size() { return list.size(); }
}

