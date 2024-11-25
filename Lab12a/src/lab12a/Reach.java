package lab12a;

import java.util.LinkedList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
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


            // Add code to determine if d is reachable from s.
            ArrayList<Integer> visited = new ArrayList<>();
            int curr = s;
            boolean pathExists = true;
            
            while (curr != d)
            {
            	if (visited.contains(curr))
            	{
            		pathExists = false;
            		break;
            	}
            	
            	visited.add(curr);
            	int index = From.indexOf(curr);
            	curr = To.get(index);
            }
            
            visited.add(curr);
            
            if (pathExists)
            	System.out.println("A path exists from " + s + " to " + d);
            else
            	System.out.println("A path DOES NOT exist from " + s + " to " + d);
        }
        input.close();
    }

}
