public class SequentialSearchST<Key, Value> {
    private Node first;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public void put(Key key, Value value) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
    }

    public Value getWithCost(Key key, SeparateChainingHashST<Key, Value> tracker) {
        for (Node x = first; x != null; x = x.next) {
            tracker.incrementComparisons();
            if (key.equals(x.key)) return x.value;
        }
        return null;
    }
}