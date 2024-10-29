package ps8;
import java.util.ArrayList;

/** Max-heap implementation; from C. Shaffer,"Data Structures and Algorithm
    Analysis in Java" */

public class MaxHeap<E extends Comparable<E>> {
    private ArrayList<E> Heap;
    private int n; // Number of things in heap

    /** Constructor for initially empty heap */
    public MaxHeap() {
        Heap = new ArrayList<E>();
        n = 0;
    }

    /** Constructor supporting preloading of heap contents */
    public MaxHeap(E[] h, int num) {
        Heap = new ArrayList<E>(num);
        n = num;
        buildheap();
    }

    /** @return Current size of the heap */
    public int heapsize() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    /** @return True if pos a leaf position, false otherwise */
    public boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }

    /** @return Position for left child of pos */
    public int leftchild(int pos) {
        assert pos < n / 2 : "Position has no left child";
        return 2 * pos + 1;
    }

    /** @return Position for right child of pos */
    public int rightchild(int pos) {
        assert pos < (n - 1) / 2 : "Position has no right child";
        return 2 * pos + 2;
    }

    /** @return Position for parent */
    public int parent(int pos) {
        assert pos > 0 : "Position has no parent";
        return (pos - 1) / 2;
    }

    /** Insert val into heap */
    public void insert(E val) {
        //System.out.println("Inserting "+val);
        //prt();
        //System.out.println("Size= "+n);
        Heap.add(val); // Start at end of heap
        swap(n, Heap.size() - 1);
        int curr = n;
        n++;
        // Now sift up until curr's parent's key > curr's key
        while ((curr != 0)
                && (Heap.get(curr).compareTo(Heap.get(parent(curr))) > 0)) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
    }

    /** Heapify contents of Heap */
    public void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--)
            siftdown(i);
    }

    /** Put element in its correct place */
    private void siftdown(int pos) {
        assert (pos >= 0) && (pos < n) : "Illegal heap position";
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && (Heap.get(j).compareTo(Heap.get(j + 1)) < 0))
                j++; // j is now index of child with greater value
            if (Heap.get(pos).compareTo(Heap.get(j)) >= 0)
                return;
            swap(pos, j);
            pos = j; // Move down
        }
    }

    /** Remove and return maximum value */
    public E removeMax() {
        assert n > 0 : "Removing from empty heap";
        swap(0, --n); // Swap maximum with last value
        if (n != 0) // Not on last element
            siftdown(0); // Put new heap root val in correct place
        return Heap.get(n);
    }

    /** Return (but do not remove) maximum value */
    public E peek() {
        if (n > 0) // Not empty
            return Heap.get(0);
        else
            return null;
    }


    /** Remove and return element at specified position */
    public E remove(int pos) {
        assert (pos >= 0) && (pos < n) : "Illegal heap position";
        if (pos == (n - 1))
            n--; // Last element, no work to be done
        else {
            swap(pos, --n); // Swap with last value
            // If we just swapped in a big value, push it up
            while ((pos > 0)
                    && (Heap.get(pos).compareTo(Heap.get(parent(pos))) > 0)) {
                swap(pos, parent(pos));
                pos = parent(pos);
            }
            if (n != 0)
                siftdown(pos); // If it is little, push down
        }
        return Heap.get(n);
    }

    private void swap(int i, int j) {
        E temp = Heap.get(j);
        Heap.set(j, Heap.get(i));
        Heap.set(i, temp);
    }
    public void prt() {
        for(int i=0; i<n; ++i)
            System.out.print(Heap.get(i)+" ");
        System.out.println();
    }
}
