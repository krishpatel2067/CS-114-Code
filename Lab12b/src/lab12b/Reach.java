/* Starter code given by professor */
package lab12b;

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Reach {

	static int dd;
	static final int UNVISITED = 0;
	static final int VISITED = 1;
	static final int PATH = 2;

	public static void main(String[] args) {

		int n;

		/*
		 * We don't know in advance the size of the graph, so let's keep track of the
		 * edges as we read them in; we will add them to the graph later.
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
				if (src > max)
					max = src;
				if (dst > max)
					max = dst;
				From.add(src);
				To.add(dst);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("max= " + max + " From size= " + From.size());
		Graphl graph = new Graphl(max + 1);

		n = max;
		for (int i = 0; i < From.size(); ++i) {
			graph.setEdge(From.get(i), To.get(i), 1);
		}
		System.out.println("Built graph");

		Scanner input = new Scanner(System.in);
		System.out.println();

		while (true) {
			System.out.print("Enter source and destination: ");
			int s = input.nextInt();
			int d = input.nextInt();
			if ((s < 0) || (d < 0))
				break;

			// Add code to determine if d is reachable from s.
			// -----------------------------------------------------------------------------
			/*
			 * Written by Krish A. Patel 12/2/2024
			 */
			resetUsingBFS(graph, s);			// mark all as UNVISITED
			int dist = getDistanceUsingBFS(graph, s, d);

			resetUsingBFS(graph, s);

			// if the path exists
			if (dist >= 0) {
				// trace back the path
				LinkedList<Integer> pathList = getPath(graph, s, d);

				System.out.println("Distance from " + s + " to " + d + ": " + dist);
//				System.out.println("Distance: " + (pathList.size() - 1));
				System.out.println("Path: " + pathList);
			} else
				System.out.println("A path DOES NOT exist from " + s + " to " + d);

			System.out.println();
		}
		input.close();
	}
	
	/*
	 * Written by Krish A. Patel 12/2/2024
	 * Precondition: a path exists from `start` to `end`
	 * */
	// returns a path from start to end
	public static LinkedList<Integer> getPath(Graphl graph, int start, int end)
	{
		markPath(graph, start, end);
		LinkedList<Integer> pathList = new LinkedList<>();
		boolean reachedStart = false;
		int curr = end;
		
		pathList.addFirst(end);

		while (!reachedStart) {
			// each node points to the predecessor
			if (curr == start)
				reachedStart = true;
			else {
				curr = graph.getMark(curr);
				pathList.addFirst(curr);
			}
		}
		
		return pathList;
	}
	
	/*
	 * Written by Krish A. Patel 12/2/2024
	 * Precondition: a path exists from `start` to `end`
	 * */
	public static void markPath(Graphl graph, int start, int end)
	{
		LinkedList<Integer> list = new LinkedList<>();
		boolean reachedEnd = false;
		
		list.add(start);
		
		while (list.size() > 0 && !reachedEnd)
		{
			int curr = list.pollFirst();
			
			if (curr == end)
			{
				reachedEnd = true;
			}
			
			for (int v = graph.first(curr); v < graph.n() && !reachedEnd; v = graph.next(curr, v))
			{
				if (graph.getMark(v) == UNVISITED) {
					graph.setMark(v, curr);
					list.add(v);
				}
				
				if (v == end)
				{
					reachedEnd = true;
				}
			}
		}
		
	}
	
	/*
	 * Written by Krish A. Patel 12/2/2024
	 * */
	// gets the distance (node-to-node) between `start` & `end` or -1 if no path exists
	public static int getDistanceUsingBFS(Graphl graph, int start, int end)
	{
		if (start == end)
			return 0;
		
		LinkedList<Integer> list = new LinkedList<>();
		int distance = 0;
		boolean reachedEnd = false;
		
		list.add(start);
		graph.setMark(start, 1);
		
		while (list.size() > 0 && !reachedEnd)
		{
			int curr = list.pollFirst();
			
			if (curr == end)
			{
				reachedEnd = true;
			}
			
			for (int v = graph.first(curr); v < graph.n() && !reachedEnd; v = graph.next(curr, v))
			{
				if (graph.getMark(v) == UNVISITED) {
					graph.setMark(v, graph.getMark(curr) + 1);
					list.add(v);
				}
				
				if (v == end)
				{
					reachedEnd = true;
					distance = graph.getMark(v);
				}
			}
		}
		
		return distance - 1;
	}

	/*
	 * Written by Krish A. Patel 12/2/2024
	 */
	public static void resetUsingBFS(Graphl graph, int start) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addLast(start);
		graph.setMark(start, UNVISITED);

		while (list.size() > 0) {
			int curr = list.removeFirst();

			for (int v = graph.first(curr); v < graph.n(); v = graph.next(curr, v)) {
				if (graph.getMark(v) != UNVISITED) {
					graph.setMark(v, UNVISITED);
					list.addLast(v);
				}
			}
		}
	}

}
