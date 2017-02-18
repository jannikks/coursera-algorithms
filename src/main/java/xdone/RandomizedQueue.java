import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int length;
    private Item[] items;

    public RandomizedQueue() {
        this.size = 0;
        this.length = 1;
        this.items = (Item[]) new Object[length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }

        items[size] = item;
        size++;
        if (length == size) {
            contractQueue();
        }
    }

    public Item dequeue() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(size);
        Item result = items[randomIndex];
        items[randomIndex] = items[size-1];

        items[size-1] = null;
        size--;
        if (length / 4 == size) {
            expandQueue();
        }
        return result;
    }

    private void contractQueue() {
        length *= 2;
        Item[] biggerQueue = (Item[]) new Object[length];
        System.arraycopy(items, 0, biggerQueue, 0, size());
        this.items = biggerQueue;
    }

    private void expandQueue() {
        length /= 2;
        Item[] smallerQueue = (Item[]) new Object[length];
        System.arraycopy(items, 0, smallerQueue, 0, size());
        this.items = smallerQueue;
    }

    public Item sample() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(size)];
    }

    private void print(){
        System.out.println(size + " " + Arrays.toString(items));
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<>(this);
    }

    private static class RandomizedQueueIterator<T> implements Iterator<T> {

        private int elementsLeft;
        private final T[] copiedItems;

        RandomizedQueueIterator(RandomizedQueue<T> queue) {

            copiedItems = (T[]) new Object[queue.size()];
            System.arraycopy(queue.items, 0, copiedItems, 0, queue.size());
            StdRandom.shuffle(copiedItems);

            elementsLeft = copiedItems.length;
        }

        @Override
        public boolean hasNext() {
            return elementsLeft != 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            --elementsLeft;
            return copiedItems[elementsLeft];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        System.out.println(queue.sample());
        queue.print();
    }
}
