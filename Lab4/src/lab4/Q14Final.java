package lab4;

import java.util.ArrayList;
import java.util.Stack;
import java.util.ArrayDeque;

public class Q14Final {
	
    /**
     * Solve the "missionaries and cannibals" problem. Start with 3 of each on
     * left bank, get them all across without ever having missionaries
     * outnumbered by cannibals on either bank. Boat can carry at most
     * 2 people.
     * In this version we use a dequeue as a queue.
     */
    public static void main(String args[]) {
    	int nC = 5, nM = 5, capB = 3;
    	
    	System.out.println(nM + " missionaries, " + nC + " cannibals, " + capB  + " boat capacity");
    	run(nM, nC, capB);
    }

    public static void run(int nC, int nM, int capB)
    {
//    	ArrayDeque<State> dq = new ArrayDeque<State>();
    	Stack<State> dq = new Stack<State>();
        ArrayList<State> visited = new ArrayList<State>();
        ArrayDeque<State> seq = new ArrayDeque<State>();
 
        int numberTrips = -1;
        State.setPeopleCount(nC, nM);
        State.setBoatCapacity(capB);
        
        State start = new State(nC, nC, 0);
        dq.addLast(start); // using deque as queue
        visited.add(start);
        
        int printCount = 0;
        
        while (! dq.isEmpty()) {
            //State next = dq.removeFirst(); // dequeue
            State next = dq.removeFirst(); // dequeue
            //System.out.println("expanding "+next);
            if (next.m == 0 && next.c == 0 && next.b == 1) { // Achieved goal!
                for (State x = next; x != null; x = x.pred) {
                    seq.offerLast(x);
                }
                numberTrips = seq.size()-1;
                State s = seq.pollLast();
                while(s != null) {
                    System.out.print("[" + ++printCount + "] (" + s.m + "M " + s.c + "C | " + (State.nM - s.m) + "M " + (State.nC - s.c) + "C) ");
                    System.out.println(s);
                    s = seq.pollLast();
                }
                break;
            }
            // generate all possible next states;
            // i represents number of missionaries in boat, j number of cannibals
            for (int i = 0; i <= nM - 1; i++)
                for (int j = 0; i + j <= nC; j++) {
                    if (i == 0 && j == 0)
                        continue; // can't have empty boat
                    if (i + j > capB)
                    	continue;
                    
                    State p = next.move(i, j);
                    
                    if (!p.legal())
                        continue;
                    
                    //System.out.println(p);
                    boolean beenHere = false;
                    for(State s : visited) {
                        if (s.equiv(p)) {
                            beenHere = true;
                            break;
                        }
                    }
                    if(beenHere)
                        continue;
                    dq.addLast(p); // enqueue
                    visited.add(p);
                    //System.out.println(p);
                }
        }
        
        System.out.println("It required " + numberTrips + " crossings");
    }
}
