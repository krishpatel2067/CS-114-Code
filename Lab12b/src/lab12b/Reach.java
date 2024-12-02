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
			 * Written by Krish A. Patel 11/25/2024
			 */
			// mark all as UNVISITED
			boolean pathExists = false;
			int dist = -1;

			System.out.println("Distance from " + s + " to " + d + ": " + getDistanceUsingBFS(graph, s, d));
			
			LinkedList<Integer> list = new LinkedList<>();
			list.addLast(s);
			graph.setMark(s, VISITED);

			while (list.size() > 0 && !pathExists) {
				int curr = list.removeFirst();

				if (curr == d) {
					pathExists = true;
					dist = graph.getMark(curr);
					break;
				}

				for (int v = graph.first(curr); v < n && !pathExists; v = graph.next(curr, v)) {
					if (v == d) {
						pathExists = true;
						dist = graph.getMark(curr);
					}

					if (graph.getMark(v) == UNVISITED) {
						graph.setMark(v, curr);
						graph.setEdge(v, curr, 1); // make this bidirectional
						list.addLast(v);
					}
				}
			}

			if (pathExists) {
				// trace back the path
				LinkedList<Integer> pathList = new LinkedList<>();
				boolean pathFound = false;

				list = new LinkedList<>();
				list.addLast(d);
				pathList.addFirst(d);

				while (list.size() > 0 && !pathFound) {
					int curr = list.removeFirst();
					int currMark = graph.getMark(curr);

					// iterate over all the neighbors to include "last" neighbors too
					// preferably the "last" neighbors, but no such method
					for (int v : graph.neighbors(curr)) {
						if (pathFound)
							break;

						// if the current node's previous node matches this one
						// it's one step towards the source
						if (v == s)
							pathFound = true;

						if (currMark == v) {
							if (!pathList.contains(v))
								pathList.addFirst(v);
							list.addLast(v);
						}
					}
				}

				System.out.println("A path exists from " + s + " to " + d + " with a distance of " + dist);
				System.out.println("Distance: " + (pathList.size() - 1));
				System.out.println("Path: " + pathList);
			} else
				System.out.println("A path DOES NOT exist from " + s + " to " + d);

			resetUsingBFS(graph, s);
			System.out.println();
		}
		input.close();
	}
	
	/*
	 * Written by Krish A. Patel 12/2/2024
	 * Precondition: a path exists from `start` to `end`
	 * */
	public static int getDistanceUsingBFS(Graphl graph, int start, int end)
	{
		if (start == end)
			return 0;
		
		resetUsingBFS(graph, start);
		
		LinkedList<Integer> list = new LinkedList<>();
		int distance = -1;
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
		
		resetUsingBFS(graph, start);
		return distance - 1;
	}

	/*
	 * Written by Krish A. Patel 11/25/2024
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
