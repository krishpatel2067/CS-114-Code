package lab7;
import java.util.ArrayList;

public class GEHeap {

    public static void main(String[] args) {
        MaxHeap<Integer> H = new MaxHeap<Integer>();
        H.insert(4);
        H.insert(9);
        H.insert(1);
        H.insert(15);
        H.insert(5);
        H.insert(12);
        H.insert(7);
        H.insert(2);
        H.insert(13);
        H.insert(22);
        H.insert(3);
        H.insert(6);
        H.insert(11);
        H.insert(8);
        H.insert(14);
        System.out.println("Heap contents:");
        H.prt();
        System.out.println("\nElements >= 6:");
        ArrayList<Integer> gte = H.GreaterOrEqual(6);
        for(Integer i : gte)
          System.out.println(i);
    }
}
