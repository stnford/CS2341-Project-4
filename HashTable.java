public interface HashTable<Key, Value> {
    void put(Key key, Value value);
    Value get(Key key);
    boolean contains(Key key);
    void resetComparisons();
    int getComparisons();
}