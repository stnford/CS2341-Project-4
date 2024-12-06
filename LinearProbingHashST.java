public class LinearProbingHashST<Key, Value> implements HashTable<Key, Value> {
    private int M; // table size
    private int N; // number of key-value pairs
    private Key[] keys;
    private Value[] values;
    private int comparisons;

    public LinearProbingHashST(int capacity) {
        M = capacity;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value value) {
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    public Value get(Key key) {
        comparisons = 0;
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            comparisons++;
            if (keys[i].equals(key)){
                return values[i];
            }
        }
        return null;
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

    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }
        keys = temp.keys;
        values = temp.values;
        M = temp.M;
    }
}