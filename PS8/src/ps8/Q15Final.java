/* Code provided by professor. Only some specific parts are written by Krish A. Patel. */
package ps8;

/**
    Implement Huffman code compression for example in notes. Create a
    binary tree with nodes defined in HuffmanNode.java class.  We need
    a MinHeap; cheat by switching the comparator in HuffmanNode.java.
*/

import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.IOException;

public class Q15Final {

	public static void main(String[] args) throws IOException {
		MaxHeap<HuffmanNode> Q = new MaxHeap<HuffmanNode>();

		// Enter data from example in class.
		// a 25, b 22, c 11, d 6, e 11, f 16, g 9
		String str = "";

		// a
		for (int i = 0; i < 25; i++)
			str += "a";

		// b
		for (int i = 0; i < 22; i++)
			str += "b";

		// c
		for (int i = 0; i < 11; i++)
			str += "c";

		// d
		for (int i = 0; i < 6; i++)
			str += "d";

		// e
		for (int i = 0; i < 11; i++)
			str += "e";

		// f
		for (int i = 0; i < 16; i++)
			str += "f";

		// f
		for (int i = 0; i < 9; i++)
			str += "g";

		// a 25, b 22, c 11, d 6, e 11, f 16, g 9
		ArrayList<Character> letters = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
		ArrayList<Integer> frequencies = new ArrayList<>(Arrays.asList(25, 22, 11, 6, 11, 16, 9));

		for (int j = 0; j < letters.size(); ++j)
			System.out.println(letters.get(j) + "  " + frequencies.get(j));

		System.out.println();
		System.out.println("Codes:");
		System.out.println();

		for (int j = 0; j < letters.size(); ++j)
			Q.insert(new HuffmanNode(letters.get(j), frequencies.get(j), null, null));

		int n = Q.heapsize();
		for (int i = 1; i < n; ++i) {
			HuffmanNode x = Q.removeMax();
			HuffmanNode y = Q.removeMax();
			HuffmanNode z1 = new HuffmanNode('z', x.getFrequency() + y.getFrequency(), x, y);
			Q.insert(z1);
		}
		HuffmanNode r = Q.peek(); // The root of the heap.
		System.out.println(r);
		printLeaves(r); // The codes are in the leaves of the tree.
		ArrayList<HuffmanNode> elts = levelorderElements(r);

		System.out.println();
		System.out.println("Now do levels");

		/* Written by Krish A. Patel */
		int nBitsOrig = 0;
		int nBitsComp = 0;
		/* ------------------------- */

		for (HuffmanNode nd : elts)
			if (nd.isLeaf()) {
				System.out.println(nd.getCharacter() + " " + nd.getCode());
				/* Written by Krish A. Patel */
				int freq = frequencies.get(letters.indexOf(nd.getCharacter()));
				nBitsOrig += freq * 8;
				nBitsComp += freq * nd.getCode().length();
				/* ------------------------- */
			}

		/* Written by Krish A. Patel */
		System.out.println();
		System.out.println("Approximate sizes: ");
		System.out.println("\tOriginal: " + nBitsOrig + " bits (" + nBitsOrig / 8 + " bytes)");
		System.out.println("\tCompressed: " + nBitsComp + " bits (" + nBitsComp / 8 + " bytes)");
		System.out
				.println("Compressed size is " + Math.round(((double) nBitsComp / nBitsOrig) * 100) + "% of original.");

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
		/* Written by Krish A. Patel */
		Queue<HuffmanNode> queue = new LinkedList<>();
		queue.add(r);

		while (queue.size() > 0) {
			HuffmanNode next = queue.poll();
			if (next == null)
				continue;

			elts.add(next);
			queue.add(next.getLeft());
			queue.add(next.getRight());
		}
		return elts;
	}

}
