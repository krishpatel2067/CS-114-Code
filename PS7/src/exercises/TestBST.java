package exercises;

public class TestBST {

	/* EXERCISE ANSWERS
	 * # 2
	 * 		Let's say there are (n - 1) nodes already inserted. Inserting the nth node in the
	 * 		worst case means that that node is the minimum, and after it is placed at the root 
	 * 		node (which is supposed to be the max), it has to sift down all the way to the 
	 * 		bottom of the tree. This means that it would have to traverse the entire height of
	 * 		the tree, when is given by lg(n) for complete binary trees, which is what MaxHeaps
	 * 		are. So, the insert time is Ω(lg(n)) for one element. There is no way to achieve 
	 * 		a better time, hence an "Ω" and not an "O." But to insert n elements,
	 * 		it would take that same amount of time for each one, causing the total time to
	 * 		be Ω(nlg(n)).
	 * 
	 * */
	
	public static void main(String[] args) {
		BST<String, String> tree = new BST<>();
		
		String[] nodes = { "H", "E", "K", "B", "F", "I", "L", "A", "G" };
		
		for (String s : nodes)
		{
			tree.insert(s, s);
		}
		
		System.out.println("Inorder: " + tree.values());
		System.out.println("Level order: " + tree.valuesLevel());
	}

}
