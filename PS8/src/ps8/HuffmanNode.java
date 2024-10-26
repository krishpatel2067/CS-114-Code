/* Starter code given by the professor */
package ps8;

/** Node for constructing binary tree in Huffman algorithm. */

public class HuffmanNode implements Comparable<HuffmanNode> {
	private char c; // The character,
	// private double f; // the character's frequency.
	private int f; // the character's frequency.
	private HuffmanNode left;
	private HuffmanNode right;
	private String code; // This will contain the optimal binary code.

	HuffmanNode(char ch, int fr, HuffmanNode l, HuffmanNode r) {
		c = ch;
		f = fr;
		left = l;
		right = r;
		code = "";
	}

	public char getCharacter() {
		return c;
	}

	public int getFrequency() {
		return f;
	}

	public HuffmanNode getLeft() {
		return left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public void setCharacter(char ch) {
		c = ch;
	}

	public void setFrequency(int fr) {
		f = fr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String c) {
		code = c;
	}

	public boolean isLeaf() {
		return ((left == null) && (right == null));
	}

	public String toString() {
		String s = Character.toString(c) + " " + Integer.toString(f);
		return s;
	}

	public int compareTo(HuffmanNode h) {
		if (this.f - h.f < 0)
			return 1;
		else if (this.f - h.f > 0)
			return -1;
		else
			return 0;
	}
}
