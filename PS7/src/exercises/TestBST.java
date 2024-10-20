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
	 * # 3
	 * 		To find all the elements with a key of >= a target key k, we can use a custom
	 * 		version of level order traversal, which can be achieved in O(n) time. The
	 * 		traversal ends when it finds the element. Due to how MaxHeaps are structured, it
	 * 		is guaranteed that all keys >= k are at the same level or above where the target
	 * 		key is found. Therefore, after exiting the traversal, we just have to remove the
	 * 		siblings of k with a key < k. The remaining elements are all with keys >= k.
	 * 		At the worst case, we might need to filter out all of the node's siblings if they
	 * 		all have keys < k. The number of nodes in a full level is given by 2 ^ L, where
	 * 		L = floor(lg(n)), where n is the number of elements in the heap. So, the time to
	 * 		filter out the siblings is approximately O(n). Therefore, the total time to get 
	 * 		all elements with keys >= k is the time of the level order traversal plus 
	 * 		that of filtering out invalid siblings: O(n) + O(n) = O(2n) = O(n).
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
