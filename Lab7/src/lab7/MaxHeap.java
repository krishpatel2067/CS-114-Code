package lab7;

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

    
	public ArrayList<E> GreaterOrEqual(E k) {
		ArrayList<E> elements = new ArrayList<E>();
		return GreaterOrEqualHelper(k, 0, elements);
		// returns array list of all elements with priority >=priority
		// of k starting at position 0 (the root)
	}

	/* Method code by Krish A. Patel */
	/*
	 * TIME COMPLEXITY:
	 * The worst time complexity is O(m) because k <= min of the heap, causing all of the 
	 * elements to have to visited once. This is essentially pre-order traversal (equivalent 
	 * to traversing the internal ArrayList linearly), so this would result in the worst time 
	 * of O(n + m) = O(m) (since m <= n).
	 * 
	 * The best case is O(m) if k >= max since none or just one node (root) has to be visited,
	 * but m elements have to be added to the returning ArrayList.
	 * 
	 * The average case is O(m) since the best and worst cases are the same.
	 * 
	 * This is an order-optimal algorithm because we can't do better than this since m
	 * elements have to be inserted into the returning ArrayList.
	 * */
	
	/*
	 * TIME COMPLEXITY HAD IT BEEN A BST:
	 * The worst time complexity would've been O(m) again because k could be <= min, making
	 * us iterate all of the elements.
	 * 
	 * The best case would be O(lg(n)) if k >= max. We would still need to check all the right
	 * children until we reach the bottom of the tree (i.e. traverse the height of the tree,
	 * which is lg(n)). 
	 * 
	 * The average case is O((n + lg(n)) / 2) = O(n).
	 * */
	public ArrayList<E> GreaterOrEqualHelper(E k, int pos, ArrayList<E> l) {
		// key does not have to exist in the heap
		if (pos >= Heap.size())
		{
//			System.out.println("Exceeded bounds");
			return l;
		}
			
		E curr = Heap.get(pos);
		
//		System.out.println("Checking " + curr);
		if (curr.compareTo(k) < 0)
		{
//			System.out.println(curr + " is less than k");
			return l;
		}
			
		// if it got here, curr >= k
		l.add(curr);
//		System.out.println("After adding curr " + l);
		
		if (!isLeaf(pos))
		{
			GreaterOrEqualHelper(k, leftchild(pos), l);
			GreaterOrEqualHelper(k, rightchild(pos), l);
		}
		return l;
	}
    
    /** Insert val into heap */
    public void insert(E val) {
        //System.out.println("Inserting "+val);
        //prt();
        //System.out.println("Size= "+n);
        Heap.add(val); // Start at end of heap
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
