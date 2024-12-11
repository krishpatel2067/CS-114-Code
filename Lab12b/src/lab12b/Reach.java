/* Starter code given by professor */
package lab12b;

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Reach {

	static final int UNVISITED = -1;
	static final int VISITED = 1;
	static int recCount = 0;

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
			unvisitAll(graph, s);			// mark all as UNVISITED
			DFS(graph, s, d);
			System.out.println("Distance (according to DFS): " + recCount);
			unvisitAll(graph, s);
			
			int dist = getDistanceUsingBFS(graph, s, d);

			unvisitAll(graph, s);

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
	
	static void DFS(Graph G, int start, int end) {
		recCount++;
		
		G.setMark(start, VISITED);
		for (int w = G.first(start); w < G.n(); w = G.next(start, w))
		{
			if (G.getMark(w) != VISITED)
				DFS(G, w, end);
			
			if (w == end)
				break;
		}
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
				distance = graph.getMark(curr);
			}
			
			for (int v = graph.first(curr); v < graph.n() && !reachedEnd; v = graph.next(curr, v))
			{
				if (graph.getMark(v) == UNVISITED) {
					if (v == end)
					{
						reachedEnd = true;
						distance = graph.getMark(curr) + 1;
					}
					
					graph.setMark(v, graph.getMark(curr) + 1);
					list.add(v);
				}
			}
		}
		
		return distance - 1;
	}

	/*
	 * Written by Krish A. Patel 12/2/2024
	 */
	// reset the graph by marking all nodes as UNVISITED
	public static void unvisitAll(Graphl graph, int start) {
		// this method works because it guarantees visiting (or UNvisiting) all nodes
		for (int i = 0; i < graph.Mark.length; i++)
			graph.setMark(i, UNVISITED);
		
		// BFS keeps skipping node 48256 so it messes up the distance when doing:
		/*
		 * s: 200000 d: 400000 (d = 14 - correct)
		 * s: 29387 d: 1728    (d = 9 - correct)
		 * s: 200000 d: 400000 (d = 15 - incorrect)
		 * */
//		LinkedList<Integer> list = new LinkedList<>();
//		list.addLast(start);
//		graph.setMark(start, UNVISITED);
		
//		while (list.size() > 0) {
//			int curr = list.removeFirst();
////			
////			if (curr == 48256)
////				System.out.println("CURR == 48256 " + graph.getMark(curr));
////			graph.setMark(curr, UNVISITED);
//			
//			for (int v = graph.first(curr); v < graph.n(); v = graph.next(curr, v)) {
//				
//				
//				if (graph.getMark(v) != UNVISITED) {
//					if (v == 597923 || v == 223236 || v == 48256)
//						System.out.println("Mark of " + v + " = " + graph.getMark(v) + " " + (graph.getMark(v) != UNVISITED));
//					list.addLast(v);
//				}
//				graph.setMark(v, UNVISITED);
//			}
//		}
	}

}
