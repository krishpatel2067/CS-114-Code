/* Starter code given by professor */
package lab12b;

import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Q9Final {

	static final int UNVISITED = -1;
	static final int VISITED = 1;

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
			System.out.print("Enter source & dist: ");
			int s = input.nextInt();
			int dist = input.nextInt();
			if ((s < 0))
				break;

			// Add code to determine if d is reachable from s.
			// -----------------------------------------------------------------------------
			/*
			 * Written by Krish A. Patel 12/11/2024
			 */
			
			unvisitAll(graph, s); // mark all as UNVISITED
			// [572647, 230784]
			int count = BFSToTargetDist(graph, s, dist);
//			int count = vertices11.size();
			
			System.out.println(count);
//			System.out.println(vertices11);
			
//			for (int v = 0; v < graph.n(); v++) {
//				if (v != s) {
//					unvisitAll(graph, s); // mark all as UNVISITED
//					int dist = getDistanceUsingBFS(graph, s, v);
//					unvisitAll(graph, s);
//					
//					if (dist == 11) {
//						count++;
////						vertices11.add(v);
//					}
//				}
//			}
			
			System.out.println();

			System.out.println();
		}
		input.close();
	}

	public static int BFSToTargetDist(Graph G, int start, int targetDist) {
		LinkedList<Integer> Q = new LinkedList<Integer>();
		Q.addLast(start);
		G.setMark(start, 1);
//		int d = 0;
		int count = 0;
		
//		ArrayList<Integer> vertices11 = new ArrayList<>();
		
		while (Q.size() > 0) { // Process each vertex on Q
//			++d;
			int v = Q.removeFirst();
			
//			System.out.println("v " + v);
			
			if (G.getMark(v) - 1 == targetDist) {
				count++;
//				vertices11.add(v);
			}
			
			for (int w = G.first(v); w < G.n(); w = G.next(v, w)) {
//				System.out.println("\tw " + w);
				if (G.getMark(w) == UNVISITED) { // Put neighbors on Q
					G.setMark(w, G.getMark(v) + 1);
					Q.addLast(w);
					
//					if (G.getMark(w) - 1 == targetDist && !vertices11.contains(w)) {
//						vertices11.add(w);
//					}
				}
			}
		}
		return count;
	}

	/*
	 * Written by Krish A. Patel 12/2/2024 Precondition: a path exists from `start`
	 * to `end`
	 */
	// returns a path from start to end
	public static LinkedList<Integer> getPath(Graphl graph, int start, int end) {
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
	 * Written by Krish A. Patel 12/2/2024 Precondition: a path exists from `start`
	 * to `end`
	 */
	public static void markPath(Graphl graph, int start, int end) {
		LinkedList<Integer> list = new LinkedList<>();
		boolean reachedEnd = false;

		list.add(start);

		while (list.size() > 0 && !reachedEnd) {
			int curr = list.pollFirst();

			if (curr == end) {
				reachedEnd = true;
			}

			for (int v = graph.first(curr); v < graph.n() && !reachedEnd; v = graph.next(curr, v)) {
				if (graph.getMark(v) == UNVISITED) {
					graph.setMark(v, curr);
					list.add(v);
				}

				if (v == end) {
					reachedEnd = true;
				}
			}
		}

	}

	/*
	 * Written by Krish A. Patel 12/2/2024
	 */
	// gets the distance (node-to-node) between `start` & `end` or -1 if no path
	// exists
	public static int getDistanceUsingBFS(Graphl graph, int start, int end) {
		if (start == end)
			return 0;

		LinkedList<Integer> list = new LinkedList<>();
		int distance = 0;
		boolean reachedEnd = false;

		list.add(start);
		graph.setMark(start, 1);

		while (list.size() > 0 && !reachedEnd) {
			int curr = list.pollFirst();

			if (curr == end) {
				reachedEnd = true;
				distance = graph.getMark(curr);
			}

			for (int v = graph.first(curr); v < graph.n() && !reachedEnd; v = graph.next(curr, v)) {
				if (graph.getMark(v) == UNVISITED) {
					if (v == end) {
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
		 * s: 200000 d: 400000 (d = 14 - correct) s: 29387 d: 1728 (d = 9 - correct) s:
		 * 200000 d: 400000 (d = 15 - incorrect)
		 */
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
