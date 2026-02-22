// Look for WRITE YOUR CODE to write your code

import java.util.*;

public class Exercise {
  public static void main(String[] args) {
    new Exercise();
  }

  public Exercise() {
//    String[] name1 = {"Tom", "George", "Peter", "Jean", "Jane"};
//    String[] name2 = {"Tom", "George", "Michael", "Michelle", "Daniel"};
//    String[] name3 = {"Tom", "Peter"};
    Scanner input = new Scanner(System.in);
    String[] name1 = new String[5];
    String[] name2 = new String[5];
    String[] name3 = new String[2];
    System.out.print("Enter five strings for array name1 separated by space: ");
    for (int i = 0; i < 5; i++) {
      name1[i] = input.next();
    }

    System.out.print("Enter five strings for array name2 separated by space: ");
    for (int i = 0; i < 5; i++) {
      name2[i] = input.next();
    }

    System.out.print("Enter two strings for array name3 separated by space: ");
    for (int i = 0; i < 2; i++) {
      name3[i] = input.next();
    }

    MyHashSet<String> set1 = new MyHashSet<>(name1);
    MyHashSet<String> set2 = new MyHashSet<>(name2);
    System.out.println("set1: " + set1);
    System.out.println("set2: " + set2);
    set1.addAll(set2);
    System.out.println("After addAll: " + sortedSet(set1) + "\n");

    set1 = new MyHashSet<>(name1);
    set2 = new MyHashSet<>(name2);
    System.out.println("set1: " + set1);
    System.out.println("set2: " + set2);
    set1.removeAll(set2);
    System.out.println("After removeAll: " + sortedSet(set1) + "\n");

    set1 = new MyHashSet<>(name1);
    set2 = new MyHashSet<>(name2);
    System.out.println("set1: " + set1);
    System.out.println("set2: " + set2);
    set1.retainAll(set2);
    System.out.println("After retainAll: " + sortedSet(set1) + "\n");

    set1 = new MyHashSet<>(name1);
    set2 = new MyHashSet<>(name2);
    System.out.println("set1: " + set1);
    System.out.println("set2: " + set2);
    set1.retainAll(set2);
    System.out.println("set1 contains all set2? " + set1.containsAll(set2) + "\n");

    set1 = new MyHashSet<>(name1);
    set2 = new MyHashSet<>(name3);
    System.out.println("set1: " + set1);
    System.out.println("set2: " + set2);
    System.out.println("set1 contains all set2? " + set1.containsAll(set2) + "\n");

    Object[] name4 = set1.toArray();
    System.out.print("name4: ");
    Arrays.sort(name4);
    for (Object e: name4) {
      System.out.print(e + " ");
    }

    String[] name5 = new String[set1.size()];
    String[] name6 = set1.toArray(name5);
    System.out.print("\nname6: ");
    Arrays.sort(name6);
    for (Object e: name6) {
      System.out.print(e + " ");
    }
  }

  private static TreeSet<String> sortedSet(MyHashSet<String> set) {
    TreeSet<String> treeSet = new TreeSet<>();
    for (String s: set)
      treeSet.add(s);
    return treeSet;
  }


  public static class MyHashSet<E> implements Collection<E> {
    // Define the default hash table size. Must be a power of 2
    private static int DEFAULT_INITIAL_CAPACITY = 4;

    // Define the maximum hash table size. 1 << 30 is same as 2^30
    private static int MAXIMUM_CAPACITY = 1 << 30;

    // Current hash table capacity. Capacity is a power of 2
    private int capacity;

    // Define default load factor
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

    // Specify a load factor threshold used in the hash table
    private float loadFactorThreshold;

    // The number of elements in the set
    private int size = 0;

    // Hash table is an array with each cell that is a linked list
    private LinkedList<E>[] table;

    /** Construct a set with the default capacity and load factor */
    public MyHashSet() {
      this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a set with the specified initial capacity and
     * default load factor */
    public MyHashSet(int initialCapacity) {
      this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a set with the specified initial capacity
     * and load factor */
    public MyHashSet(int initialCapacity, float loadFactorThreshold) {
      if (initialCapacity > MAXIMUM_CAPACITY)
        this.capacity = MAXIMUM_CAPACITY;
      else
        this.capacity = trimToPowerOf2(initialCapacity);

      this.loadFactorThreshold = loadFactorThreshold;
      table = new LinkedList[capacity];
    }

    @Override /** Remove all elements from this set */
    public void clear() {
      size = 0;
      removeElements();
    }

    @Override /** Return true if the element is in the set */
    public boolean contains(Object e) {
      int bucketIndex = hash(e.hashCode());
      if (table[bucketIndex] != null) {
        LinkedList<E> bucket = table[bucketIndex];
        for (E element: bucket)
          if (element.equals(e))
            return true;
      }

      return false;
    }

    @Override /** Add an element to the set */
    public boolean add(E e) {
      if (contains(e)) // Duplicate element not stored
        return false;

      if (size + 1> capacity * loadFactorThreshold) {
        if (capacity == MAXIMUM_CAPACITY)
          throw new RuntimeException("Exceeding maximum capacity");

        rehash();
      }

      int bucketIndex = hash(e.hashCode());

      // Create a linked list for the bucket if it is not created
      if (table[bucketIndex] == null) {
        table[bucketIndex] = new LinkedList<E>();
      }

      // Add e to hashTable[index]
      table[bucketIndex].add(e);

      size++; // Increase size

      return true;
    }

    @Override /** Remove the element from the set */
    public boolean remove(Object e) {
      if (!contains(e))
        return false;

      int bucketIndex = hash(e.hashCode());

      // Create a linked list for the bucket if it is not created
      if (table[bucketIndex] != null) {
        LinkedList<E> bucket = table[bucketIndex];
        for (E element: bucket)
          if (e.equals(element)) {
            bucket.remove(element);
            break;
          }
      }

      size--; // Decrease size

      return true;
    }

    @Override /** Return true if the set contains no elements */
    public boolean isEmpty() {
      return size == 0;
    }

    @Override /** Return the number of elements in the set */
    public int size() {
      return size;
    }

    @Override /** Return an iterator for the elements in this set */
    public java.util.Iterator<E> iterator() {
      return new MyHashSetIterator(this);
    }

    /** Inner class for iterator */
    private class MyHashSetIterator implements java.util.Iterator<E> {
      // Store the elements in a list
      private java.util.ArrayList<E> list;
      private int current = 0; // Point to the current element in list
      private MyHashSet<E> set;

      /** Create a list from the set */
      public MyHashSetIterator(MyHashSet<E> set) {
        this.set = set;
        list = setToList();
      }

      @Override /** Next element for traversing? */
      public boolean hasNext() {
        if (current < list.size())
          return true;

        return false;
      }

      @Override /** Get current element and move cursor to the next */
      public E next() {
        return list.get(current++);
      }

      @Override /** Remove the current element and refresh the list */
      public void remove() {
        // Delete the current element from the hash set
        set.remove(list.get(current));
        list.remove(current); // Remove current element from the list
      }
    }

    /** Hash function */
    private int hash(int hashCode) {
      return supplementalHash(hashCode) & (capacity - 1);
    }

    /** Ensure the hashing is evenly distributed */
    private static int supplementalHash(int h) {
      h ^= (h >>> 20) ^ (h >>> 12);
      return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /** Return a power of 2 for initialCapacity */
    private int trimToPowerOf2(int initialCapacity) {
      int capacity = 1;
      while (capacity < initialCapacity) {
        capacity <<= 1;
      }

      return capacity;
    }

    /** Remove all e from each bucket */
    private void removeElements() {
      for (int i = 0; i < capacity; i++) {
        if (table[i] != null) {
          table[i].clear();
        }
      }
    }

    /** Rehash the set */
    private void rehash() {
      java.util.ArrayList<E> list = setToList(); // Copy to a list
      capacity <<= 1; // Double capacity
      table = new LinkedList[capacity]; // Create a new hash table
      size = 0; // Reset size

      for (E element: list) {
        add(element); // Add from the old table to the new table
      }
    }

    /** Copy elements in the hash set to an array list */
    private java.util.ArrayList<E> setToList() {
      java.util.ArrayList<E> list = new java.util.ArrayList<>();

      for (int i = 0; i < capacity; i++) {
        if (table[i] != null) {
          for (E e: table[i]) {
            list.add(e);
          }
        }
      }

      return list;
    }

    @Override
    public String toString() {
      java.util.ArrayList<E> list = setToList();
      StringBuilder builder = new StringBuilder("[");

      // Add the elements except the last one to the string builder
      for (int i = 0; i < list.size() - 1; i++) {
        builder.append(list.get(i) + ", ");
      }

      // Add the last element in the list to the string builder
      if (list.size() == 0)
        builder.append("]");
      else
        builder.append(list.get(list.size() - 1) + "]");

      return builder.toString();
    }

    // New constructor
    public MyHashSet(Collection<E> collection) {
      this();
      for (E e: collection) {
        this.add(e);
      }
    }

    // New constructor
    public MyHashSet(E[] list) {
      this();
      for (E e: list) {
        this.add(e);
      }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      // Parse through all objects (e) in c
      for (Object e : c) {
        // If this() (that is, the set that this method belongs to) DOES NOT
        // contain e, return false
        if (!contains(e))
          return false;
      }
      // Otherwise, return true.  All elements of c are in this().
      return true;
    }

    /** Adds the elements in c to this hash set.
     * Returns true if this hash set changed as a result of the call */
    @Override
    public boolean addAll(Collection<? extends E> c) {
      // Initialize changed as false
      boolean changed = false;
      // Parse through all elements (e) in c, and add them to this()
      for (E e : c) {
        if (add(e))
          // If e was added to this(), set changed to true.
          changed = true;
      }
      // Return whether this() was changed or not.
      return changed;
    }

    /** Removes all the elements in c from this hash set.
     * Returns true if this hash set changed as a result of the call */
    @Override
    public boolean removeAll(Collection<?> c) {
      // Initialize changed to false
      boolean changed = false;
      // Parse through all elements (e) in c.
      for (Object e : c) {
        // Try to remove e from c.
        if (remove(e))
          // If e was removed from c, set changed to true.
          changed = true;
      }
      // Return whether this() was changed or not.
      return changed;
    }

    /** Retains the elements in this hash set that are also in c.
     * Returns true if this hash set changed as a result of the call */
    @Override
    public boolean retainAll(Collection<?> c) {
      // Initialize changed to false
      boolean changed = false;
      /*
       Create a new iterator, it, and set it equal to this().  We do this
       because we are about to possibly remove elements from this(), so
       we don't want to touch this() while we're modifying it.  If we iterate
       through it instead, we can freely remove entries from this() without
       worrying about losing track of entities.
      */
      Iterator<E> it = iterator();
      // While there are elements in it (again, originally identical to this())
      // parse through all elements.
      while (it.hasNext()) {
        // Set e to the next element in it -- on first-run, this will be element
        // 0 in the list.
        E e = it.next();
        // If c DOES NOT contain e, remove e from this(), and set changed to true.
        if (!c.contains(e)) {
          remove(e);
          changed = true;
        }
      }
      // Return whether this() was changed or not.
      return changed;
    }

    @Override
    public Object[] toArray() {
      // Return an object array representation of the set.  Note that this
      // is NOT type-safe, and is not best practice in modern Java.
      return setToList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
      /*
       Return a typed array representation of the set.  Preferred over
       the preceding public Object[] toArray() because this is both
       type-safe and doesn't require casting.
      */
      return setToList().toArray(a);
    }
  }
}
