import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first = null;
    private Node<Item> last = null;

    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {

        checkNull(item);

        size++;

        Node<Item> newNode = new Node<>(item);

        if (first == null) {
            // very first element
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        }
    }

    public void addLast(Item item) {
        checkNull(item);

        size++;

        Node<Item> newNode = new Node<>(item);

        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            newNode.previous = last;
            last.next = newNode;
            last = newNode;
        }
    }

    public Item removeFirst() {
        checkEmpty(first);

        size--;

        Item item = first.item;
        first = first.next;

        if (first != null) {
            first.previous = null;
        }
        else {
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        checkEmpty(last);

        size--;

        Item item = last.item;
        last = last.previous;

        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }

        return item;
    }

    private void checkEmpty(Node<Item> node) {
        if (node == null) {
            throw new NoSuchElementException("No such element.");
        }
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException("Item can't be null.");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<>(this);
    }

    private static class DequeIterator<T> implements Iterator<T> {

        private Node<T> current;

        DequeIterator(Deque<T> deque) {
            current = deque.first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException("No more elements.");
            }
            T item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }


    private static class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> previous;

        Node(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    ", previous=" + previous +
                    '}';
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addLast("eins");
        deque.removeFirst();
        deque.removeFirst();
        deque.addLast("zwei");
        deque.addLast("drei");
        deque.addFirst("null");
        deque.isEmpty();

        System.out.println(deque.size());

        for (String item : deque) {
            System.out.println(item);
        }
    }
}
