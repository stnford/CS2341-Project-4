public class SeparateChainingHashST<Key, Value> implements HashTable<Key, Value> {
    private int N; // number of key-value pairs
    private int M; // hash table size
    private SequentialSearchST<Key, Value>[] st;
    private int comparisons; // Tracks search cost

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value value) {
        st[hash(key)].put(key, value);
    }

    public Value get(Key key) {
        comparisons = 0;
        return st[hash(key)].getWithCost(key, this);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void resetComparisons() {
        comparisons = 0;
    }

    public int getComparisons() {
        return comparisons;
    }

    public void incrementComparisons() {
        comparisons++;
    }
}