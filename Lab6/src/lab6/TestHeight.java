package lab6;

public class TestHeight {

    public static void main(String[] args) {
        BST<String, String> Dict = new BST<String, String>();

        // First insert strings in alphabetical order.

        System.out.println("Initial height= "+Dict.height());
        Dict.insert("act", "act");
        System.out.println("After one insertion height= "+Dict.height());
        Dict.insert("cat", "cat");
        Dict.insert("tac", "tac");
        System.out.println("After three insertions height= "+Dict.height());
        Dict.clear();

        // Now insert so tree is balanced; cat, act, tac

        System.out.println("\nInitial height= "+Dict.height());
        Dict.insert("cat", "cat");
        System.out.println("After one insertion height= "+Dict.height());
        Dict.insert("act", "act");
        Dict.insert("tac", "tac");
        System.out.println("After three insertions height= "+Dict.height());
        Dict.clear();
        
        System.out.println("\nInitial height= "+Dict.height());
        Dict.insert("cat", "cat");
        System.out.println("After one insertion height= "+Dict.height());
//        Dict.insert("act", "act");
        Dict.insert("tac", "tac");
        System.out.println("After three insertions height= "+Dict.height());
        Dict.insert("tac", "tac");
//        Dict.insert("sat", "sat");
        System.out.println("After five insertions height= "+Dict.height());
        System.out.println(Dict.levelOrder());
    }
}

