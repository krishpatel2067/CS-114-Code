package exercises;

public class TestBST {

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
