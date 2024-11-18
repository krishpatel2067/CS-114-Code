/* Given by professor
 * Modified by Krish A. Patel
 * 11/18/2024
 * */

/*
 * Lab11 starter code; description in Lab11.pdf.
 */
package lab11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RandomGraph {

	public static Graphm createGraph(int n) {
		double p = 0.06; // probability that an edge is present
		long seed = 123; // pseudo-random number generator seed
		Random rng = new Random(seed);
		Graphm graph = new Graphm(n); // use the adjacency matrix implementation

		// create a random graph; each edge present with probability p
		for (int i = 0; i < n; ++i) {
			graph.setMark(i, 0);
			for (int j = i + 1; j < n; ++j) {
				double u = rng.nextDouble();
				if (u < p) {
					graph.setEdge(i, j, 1);
					graph.setEdge(j, i, 1);
//					System.out.println("setedge " + i + ", " + j);
				}
			}
		}

		return graph;
	}

	public static void main(String[] args) {

		int n = 30; // number of vertices
		Graphm graph = createGraph(n);

		System.out.println("Adjacency matrix:");
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (graph.isEdge(i, j))
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}

//		graphTraverse(graph);
//		BFS(graph, 1);
//		marks(graph);

		int maxDist = 0; // aka diameter

		System.out.println("Distances:");
		for (int i = 0; i < n; ++i) {
			BFS(graph, i); // marks all the distances from vertex i
//			marks(graph);
			int[] distances = graph.Mark;
			graph = createGraph(n);
			for (int j = 0; j < n; ++j) {
				int dist = (distances[j] - 1);

				if (dist > maxDist)
					maxDist = dist;

				System.out.print((dist == -1 ? "x" : dist) + " ");
			}
			System.out.println();
		}

		graph = createGraph(n);

		int components = 0;
		ArrayList<Integer> vertices = new ArrayList<>();

		for (int i = 0; i < n; i++)
			vertices.add(i);

		System.out.println("Diameter: " + maxDist);

		while (!vertices.isEmpty()) {
			ArrayList<Integer> visited = componentsBFS(graph, vertices.get(0));

			for (Integer v : visited) {
				int index = vertices.indexOf(v);
				if (index != -1)
					vertices.remove(index);
			}
			components++;
		}
		System.out.println("Number of connected components: " + components);
		System.out.println("Finished");
	}

	static void marks(Graphm graph) {
		System.out.print("marks: ");
		for (int i : graph.Mark) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	/** Depth first search */
	static void DFS(Graph G, int v) {
		PreVisit(G, v); // Take appropriate action
		G.setMark(v, 1);
		for (int w = G.first(v); w < G.n(); w = G.next(v, w))
			if (G.getMark(w) == 0)
				DFS(G, w);
		PostVisit(G, v); // Take appropriate action
	}

	/** Breadth first (queue-based) search */
	static void BFS(Graph G, int start) {
		LinkedList<Integer> Q = new LinkedList<Integer>();
		Q.addLast(start);
		G.setMark(start, 1);
		int d = 0;
		while (Q.size() > 0) { // Process each vertex on Q
			++d;
			int v = Q.removeFirst();
			PreVisit(G, v); // Take appropriate action
			for (int w = G.first(v); w < G.n(); w = G.next(v, w))
				if (G.getMark(w) == 0) { // Put neighbors on Q
					G.setMark(w, G.getMark(v) + 1);
					Q.addLast(w);
				}
			PostVisit(G, v); // Take appropriate action
		}
	}

	static ArrayList<Integer> componentsBFS(Graph G, int start) {
		ArrayList<Integer> visited = new ArrayList<>();

		LinkedList<Integer> Q = new LinkedList<Integer>();
		Q.addLast(start);
		G.setMark(start, 1);
		int d = 0;
		while (Q.size() > 0) { // Process each vertex on Q
			++d;
			int v = Q.removeFirst();
			PreVisit(G, v); // Take appropriate action
			visited.add(v);
			for (int w = G.first(v); w < G.n(); w = G.next(v, w))
				if (G.getMark(w) == 0) { // Put neighbors on Q
					G.setMark(w, G.getMark(v) + 1);
					Q.addLast(w);
					visited.add(w);
				}
			PostVisit(G, v); // Take appropriate action
		}

		return visited;
	}

//	static int getDistanceBFS(Graph G, int start, int target) {
//		LinkedList<Integer> Q = new LinkedList<Integer>();
////		System.out.println("target: " + target);
//		Q.addLast(start);
//		G.setMark(start, 1);
//		int d = 0;
//		while (Q.size() > 0) { // Process each vertex on Q
//			++d;
//			int v = Q.removeFirst();
//			if (v == target) {
////				System.out.println("distance between " + start + " and " + target + " = " + (d));
//				return d;
//			}
////			PreVisit(G, v); // Take appropriate action
//			for (int w = G.first(v); w < G.n(); w = G.next(v, w)) {
//				if (w == target) {
////					System.out.println("distance between " + start + " and " + target + " = " + (d));
//					return d;
//				}
//				if (G.getMark(w) == 0) { // Put neighbors on Q
//					G.setMark(w, G.getMark(v) + 1);
//					Q.addLast(w);
//				}
////				PostVisit(G, v); // Take appropriate action
//			}
//		}
//		return 0;
//	}

	static void PreVisit(Graph G, int v) {
//		System.out.println("entering " + v);
	}

	static void PostVisit(Graph G, int v) {
//		System.out.println("leaving " + v);
	}

	static void graphTraverse(Graph G) {
		int v;
		for (v = 0; v < G.n(); v++)
			G.setMark(v, 0); // Initialize; 0 means "unvisited"
		for (v = 0; v < G.n(); v++) {
			if (G.getMark(v) == 0) {
				BFS(G, v);
				// DFS(G, v);
			}
		}
	}

}
