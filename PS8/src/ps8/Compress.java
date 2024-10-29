package ps8;

/**
    Implement Huffman code compression for example in notes. Create a
    binary tree with nodes defined in HuffmanNode.java class.  We need
    a MinHeap; cheat by switching the comparator in HuffmanNode.java.
*/

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;

public class Compress {

	/*
	 * RESPONSE TO QUESTION # 2:
	 * Approximately sorting can be handled using "approximate min heaps" that have a buffer of
	 * k built into them. The heap property is modified so that each child does not HAVE to be 
	 * greater than its parent. As long as the ith smallest element (smallest element being considered 
	 * i = 0) is at an index j that is within a distance k from i, the new heap property is
	 * "satisfied." Of course, some sifting up and down will be necessary to ensure that the
	 * distance remains within k. Every time an element is added, the most that it will have
	 * to sift up is the height of the heap (lg(n)). Similarly, the most that the root will have
	 * to sift down is also (lg(n)). Other adjustments may need to do be done such as switching
	 * "cousins" around based on whether they fall below the desired range or above it, which
	 * can all be done using the equations to access a parent and child (which all take constant
	 * time O(1)). Thus, O(lg(n) + lg(n)) = O(lg(n)) is the worst case time for inserting each
	 * element. Inserting n elements means that the total worst case time is O(nlg(n)).
	 * 
	 * For extracting the approximate minimums, unlike with regular min heaps, the heap property
	 * is much more flexible now. Instead of having to shift elements around every time a min
	 * is extracted, we can just increment a variable like "minIndex" by one each time,
	 * effectively eliminating access to previously extracted mins and returning the next
	 * element in level order. Essentially, the internal ArrayList of the min heap would be
	 * created after extracting all of the elements. This would need constant time  O(1) since
	 * no re-modification of the heap is necessary.
	 * 
	 * Thus, the total time of the algorithm is just building the heap, which takes O(nlg(n)),
	 * which is similar to a regular min heap that maintains a strict heap property.
	 * */
	
	public static void main(String[] args) throws IOException {
		MaxHeap<HuffmanNode> Q = new MaxHeap<HuffmanNode>();

		// Enter data from example in class.

		FileReader inputStream = null;

		ArrayList<Character> letters = new ArrayList<Character>();
		ArrayList<Integer> frequencies = new ArrayList<Integer>();
		try {
			inputStream = new FileReader("WarAndPeace");

			int c;
			while ((c = inputStream.read()) != -1) {
				Character ch = (char) c;
				int i = letters.indexOf(ch);
				if (i > -1)
					frequencies.set(i, frequencies.get(i) + 1);
				else {
					letters.add(ch);
					frequencies.add(1);
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		for (int j = 0; j < letters.size(); ++j)
			System.out.println(letters.get(j) + "  " + frequencies.get(j));

		System.out.println();
		System.out.println("Codes:");
		System.out.println();

		for (int j = 0; j < letters.size(); ++j)
			Q.insert(new HuffmanNode(letters.get(j), frequencies.get(j), null, null));

//		 The example from class:

		/* Test code */
//		Q.insert(new HuffmanNode('a', 45, null, null));
//		Q.insert(new HuffmanNode('b', 20, null, null));
//		Q.insert(new HuffmanNode('c', 25, null, null));
//		Q.insert(new HuffmanNode('d', 10, null, null));
		
		int n = Q.heapsize();
		for (int i = 1; i < n; ++i) {
			HuffmanNode x = Q.removeMax();
//			System.out.print("Removed x: ");
//			Q.prt();
			HuffmanNode y = Q.removeMax();
//			System.out.print("Removed y: ");
//			Q.prt();
//			System.out.println("Adding node z for " + x.getCharacter() + " and " + y.getCharacter() + " with combined frequency of " + (x.getFrequency() + y.getFrequency()));
			HuffmanNode z1 = new HuffmanNode('z', x.getFrequency() + y.getFrequency(), x, y);
//			System.out.println(z1);
			// The label for the node is immaterial.
			Q.insert(z1);
//			Q.prt();
		}
		HuffmanNode r = Q.peek(); // The root of the heap.
		System.out.println(r);
		printLeaves(r); // The codes are in the leaves of the tree.
		ArrayList<HuffmanNode> elts = levelorderElements(r);
		
		System.out.println();
		System.out.println("Now do levels");
		int nBitsOrig = 0;
		int nBitsComp = 0;
		
		for (HuffmanNode nd : elts)
			if (nd.isLeaf())
			{
				int freq = frequencies.get(letters.indexOf(nd.getCharacter()));
				System.out.println(nd.getCharacter() + " " + nd.getCode());
				nBitsOrig += freq * 8;
				nBitsComp += freq * nd.getCode().length();
			}
		
		System.out.println();
		System.out.println("Calculating the sizes: ");
		System.out.println("Original: " + nBitsOrig/8 + " bytes");
		System.out.println("Compressed: " + nBitsComp/8 + " bytes");
		System.out.println("Compressed size is " + Math.round(((double)nBitsComp/nBitsOrig)*100) + "% of original.");
		
	}

	/* Recursively descend to leaves, constructing code as we go. */
	public static void printLeaves(HuffmanNode r) {
		if (r == null)
			return;
		if ((r.getLeft() == null) && (r.getRight() == null)) {
			System.out.println(r.getCode() + " " + r);
			return;
		}
		if (r.getLeft() != null) {
			r.getLeft().setCode(r.getCode() + '0');
			printLeaves(r.getLeft());
		}
		if (r.getRight() != null) {
			r.getRight().setCode(r.getCode() + '1');
			printLeaves(r.getRight());
		}
	}

	public static ArrayList<HuffmanNode> levelorderElements(HuffmanNode r) {
		// return nodes in level order: root, then children of root, etc.
		if (r == null)
			return null;
		ArrayList<HuffmanNode> elts = new ArrayList<HuffmanNode>();

		// Fill in.
		Queue<HuffmanNode> queue = new LinkedList<>();
		queue.add(r);

		while (queue.size() > 0) {
			HuffmanNode next = queue.poll();
			if (next == null) {
				continue;
			}

			elts.add(next);
			queue.add(next.getLeft());
			queue.add(next.getRight());
		}
		return elts;
	}

}
