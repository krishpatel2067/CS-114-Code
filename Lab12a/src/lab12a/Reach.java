package lab12a;

import java.util.LinkedList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collections;

public class Reach {

    static int dd;
    static final int UNVISITED = 0;
    static final int VISITED = 1;
    static final int PATH = 2;

    public static void main(String[] args) {

        int n;

        /*
         * We don't know in advance the size of the graph, so let's keep
         * track of the edges as we read them in; we will add them to the
         * graph later.
         */

        ArrayList<Integer> From = new ArrayList<Integer>();
        ArrayList<Integer> To = new ArrayList<Integer>();

        int max = 0; // Maximum vertex number so we know
                     // how large a graph to build.
        File file = new File("web.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextInt()) {
                int src = scanner.nextInt();
                int dst = scanner.nextInt();
                if(src>max) max=src;
                if(dst>max) max=dst;
                From.add(src);
                To.add(dst);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("max= "+max+" From size= "+From.size());
        Graphl graph = new Graphl(max+1);

        n = max;
        for(int i=0; i<From.size(); ++i) {
            graph.setEdge(From.get(i), To.get(i),1);
        }
        System.out.println("Built graph");

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Enter source and destination: ");
            int s = input.nextInt();
            int d = input.nextInt();
            if((s<0) || (d<0)) break;
            
            
            
            // Add code to determine if d is reachable from s.
            /* Krish A. Patel */
            resetBFS(graph, s);
            boolean pathExists = false;
            int dist = -1;
            
            LinkedList<Integer> list = new LinkedList<>();
        	list.addLast(s);
        	graph.setMark(s, VISITED);
        	
        	while (list.size() > 0 && !pathExists)
        	{
//        		System.out.println(list);
        		int curr = list.removeFirst();
        		
        		if (curr == d)
        		{
        			pathExists = true;
        			dist = graph.getMark(curr);
//        			System.out.println("curr matches d");
        			break;
        		}
        		
        		for (int v = graph.first(curr); v < n && !pathExists; v = graph.next(curr, v))
        		{
        			if (v == d)
        			{
    					pathExists = true;
    					dist = graph.getMark(curr);
        			}
        			
//        			System.out.println("\tCurr: " + curr);
//        			System.out.print("\t" + graph.getMark(v));
//        			System.out.println();
        			
        			if (graph.getMark(v) == UNVISITED)
        			{
        				graph.setMark(v, curr);
        				graph.setEdge(v, curr, 1);		// make this bidirectional
        				list.addLast(v);
//        				System.out.println("\t" + list);
        			}
        		}
        	}
            
            if (pathExists)
            {
            	// trace back the path
            	LinkedList<Integer> pathList = new LinkedList<>();
            	LinkedList<Integer> list2 = new LinkedList<>();
            	list2.addLast(d);
            	pathList.addFirst(d);
            	boolean pathFound = false;
            	
            	while (list2.size() > 0 && !pathFound)
            	{
            		int curr = list2.removeFirst();
            		int currMark = graph.getMark(curr);
            		
//            		System.out.println("curr: " + curr);
//            		System.out.println("currMark: " + currMark);
            		
            		for (int v : graph.neighbors(curr))
            		{
            			if (pathFound)
            				break;
            			
            			// if the current node's previous node matches this one
            			// it's one step towards the source
//            			System.out.println("\t" + v);
            			
            			if (v == s)
            				pathFound = true;
            			
            			if (currMark == v)
            			{
            				if (!pathList.contains(v))
            					pathList.addFirst(v);
            				list2.addLast(v);
            			}
            		}
            	}
            	
            	System.out.println("A path exists from " + s + " to " + d + " with a distance of " + dist);
            	System.out.println("The path is " + pathList);
            }
            else
            	System.out.println("A path DOES NOT exist from " + s + " to " + d);
        }
        input.close();
    }

    public static void resetBFS(Graphl graph, int start)
    {
    	LinkedList<Integer> list = new LinkedList<>();
    	list.addLast(start);
    	graph.setMark(start, UNVISITED);
    	
    	while (list.size() > 0)
    	{
    		int curr = list.removeFirst();
    		
    		for (int v = graph.first(curr); v < graph.n(); v = graph.next(start, v))
    		{
    			if (graph.getMark(v) != UNVISITED)
    			{
    				graph.setMark(v, UNVISITED);
    				list.addLast(v);
    			}
    		}
    	}
    }
    
}
