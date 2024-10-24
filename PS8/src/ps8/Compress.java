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
                Character ch =  (char) c;
                int i = letters.indexOf(ch);
                if(i>-1)
                    frequencies.set(i, frequencies.get(i)+1);
                else
                {
                    letters.add(ch);
                    frequencies.add(1);
                }
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        for(int j=0; j<letters.size(); ++j)
            System.out.println(letters.get(j)+"  "+frequencies.get(j));

        System.out.println();
        System.out.println("Codes:");
        System.out.println();

        for(int j=0; j<letters.size(); ++j)
            Q.insert(new HuffmanNode(letters.get(j), frequencies.get(j), null, null));

        /*
          The example from class:

                        Q.insert(new HuffmanNode('a', 45, null, null));
                        Q.insert(new HuffmanNode('b', 20, null, null));
                        Q.insert(new HuffmanNode('c', 25, null, null));
                        Q.insert(new HuffmanNode('d', 10, null, null));
        */

        int n = Q.heapsize();
        for(int i=1; i<n; ++i) {
            HuffmanNode x = Q.removeMax();
            HuffmanNode y = Q.removeMax();
            HuffmanNode z = new HuffmanNode('z', x.getFrequency()
                                            +y.getFrequency(),x,y);
            // The label for the node is immaterial.
            Q.insert(z);
        }
        HuffmanNode r = Q.peek(); // The root of the heap.
        System.out.println(r);
        printLeaves(r); // The codes are in the leaves of the tree.
        ArrayList<HuffmanNode> elts = levelorderElements(r);
        System.out.println("Now do levels");
        for(HuffmanNode nd : elts)
            if(nd.isLeaf()) System.out.println(nd.getCharacter()+" "+nd.getCode());
    }

    /* Recursively descend to leaves, constructing code as we go. */
    public static void printLeaves(HuffmanNode r) {
        if(r == null) return;
        if((r.getLeft() == null) &&
                (r.getRight() == null)) {
            System.out.println(r.getCode()+" "+r);
            return;
        }
        if(r.getLeft() != null) {
            r.getLeft().setCode(r.getCode()+'0');
            printLeaves(r.getLeft());
        }
        if(r.getRight() != null) {
            r.getRight().setCode(r.getCode()+'1');
            printLeaves(r.getRight());
        }
    }


    public static ArrayList<HuffmanNode> levelorderElements(HuffmanNode r) {
        // return nodes in level order: root, then children of root, etc.
        if(r==null)
            return null;
        ArrayList<HuffmanNode> elts = new ArrayList<HuffmanNode>();

        // Fill in.

        return elts;
    }

}


