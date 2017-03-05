import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 *
 */
public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = queue.iterator();
        int printed = 0;

        while (iterator.hasNext() && printed < k){
            System.out.println(iterator.next());
            printed ++;
        }
    }
}
