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
            
            resetBFS(graph, s);
            
            // Add code to determine if d is reachable from s.
            int curr = s;
            boolean pathExists = false;
            
            LinkedList<Integer> neighbors = new LinkedList<>();
            
            neighbors.addAll((Collection<? extends Integer>) graph.neighbors(s));
            
            while (neighbors.size() > 0)
            {
            	int v = neighbors.pollFirst();
            	
            	if (v == d)
            	{
            		pathExists = true;
            		break;
            	}
            	else
            	{
            		neighbors.addAll((Collection<? extends Integer>) graph.neighbors(v));
            	}
            }
            
            System.out.println(neighbors);
            
            if (pathExists)
            	System.out.println("A path exists from " + s + " to " + d);
            else
            	System.out.println("A path DOES NOT exist from " + s + " to " + d);
        }
        input.close();
    }

    public static void resetBFS(Graphl graph, int start)
    {
    	LinkedList<Integer> list = new LinkedList<>();
    	list.addLast(start);
    	graph.setMark(start, 0);
    	
    	while (list.size() > 0)
    	{
    		int curr = list.removeFirst();
    		
    		for (int v = graph.first(curr); v < graph.n(); v = graph.next(start, v))
    		{
    			if (graph.getMark(v) != 0)
    			{
    				graph.setMark(v, 0);
    				list.addLast(v);
    			}
    		}
    	}
    }
    
}
