/* Starter code given by the professor */
package lab10;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class SortMovies {

	public static void main(String[] args) throws IOException {
		File ratings = new File("movies.tsv");
		ArrayList<MovieRecord> mr = new ArrayList<MovieRecord>();

		int count = 0;

		try {
			Scanner scanr = new Scanner(ratings);
			while (scanr.hasNextLine()) {
				String line = scanr.nextLine();
				String[] tokens = line.split("\\t"); // Input separated by tabs
				MovieRecord mrec = new MovieRecord(tokens[0], Integer.parseInt(tokens[1]));
				mr.add(mrec);
			}
			scanr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Number of rating records= " + mr.size());
		MovieRecord[] A = mr.toArray(new MovieRecord[mr.size()]);

		// Use the following auxilliary array for mergesort or counting sort.
		MovieRecord[] B = new MovieRecord[A.length];

		Sorts srts = new Sorts();

		/* Written by Krish A. Patel */
		// --------------------------------------------------------------------------------------

		// Insert code here for sorting array.
		int sampStart = (int) (B.length / 2.5);
		int sampEnd = (int) (B.length / 2.4999);
		
		sampStart = 0;
		sampEnd = 50;

		// counting sort
		long start = System.currentTimeMillis();
		countingsort(A, B, 100);
		long end = System.currentTimeMillis();

		System.out.print("Counting (STABLE) sort finished: ");
		System.out.println("Time = " + (end - start));
		prt(B, sampStart, sampEnd, "\t");
		System.out.println();

		// merge sort
		B = new MovieRecord[A.length];
		start = System.currentTimeMillis();
		srts.mergesort(A, B, 0, B.length - 1);
		end = System.currentTimeMillis();

		System.out.print("Merge (STABLE) sort finished: ");
		System.out.println("Time = " + (end - start));
		prt(B, sampStart, sampEnd, "\t");
		System.out.println();

		// heap sort
		B = A.clone();
		start = System.currentTimeMillis();
		srts.heapsort(B);
		end = System.currentTimeMillis();

		System.out.print("Heap (UNSTABLE) sort finished: ");
		System.out.println("Time = " + (end - start));
		prt(B, sampStart, sampEnd, "\t");
		System.out.println();
		
//      // quick sort (STACK OVERFLOW)
//      B = A.clone();
//      start = System.currentTimeMillis();
//      srts.quicksort(B);
//      end = System.currentTimeMillis();
//      
//      System.out.print("Quick sort finished: ");
//      System.out.println("Time = "+(end-start));
//      prt(B, sampStart, sampEnd, "\t");
//      System.out.println();
		
//		// hybrid quick sort (STACK OVERFLOW)
//		B = A.clone();
//		start = System.currentTimeMillis();
//		srts.hqsort(B, 0, B.length - 1);
//		end = System.currentTimeMillis();
//
//		System.out.print("Hybrid quick sort finished: ");
//		System.out.println("Time = " + (end - start));
//		prt(B, sampStart, sampEnd, "\t");
//		System.out.println();

		// Print out the sorted records.

		// --------------------------------------------------------------------------------------

		BufferedWriter output = new BufferedWriter(new FileWriter("smovies.tsv"));
		// Write the output to a file; depending on sort, output A or B.
		for (int i = 0; i < A.length; ++i)
			output.write(String.format("%s\n", A[i]));

		output.close();

	}

	static <T> void prt(T[] arr, int s, int e, String prefix) {
		System.out.println("Sample size: " + (e - s));

		for (int i = s; i < e; i++)
			System.out.println(prefix + arr[i]);
	}

	/*
	 * counting sort from the notes, specialized for MovieRecords. Assumes the
	 * ratings are between 0 and bound. The sorted output goes into B, an array that
	 * we create and pass in.
	 */
	static void countingsort(MovieRecord[] A, MovieRecord[] B, int bound) {
		int[] C = new int[bound + 1];
		for (int i = 0; i < bound + 1; ++i)
			C[i] = 0;
		for (int j = 0; j < A.length; ++j)
			C[A[j].getRating()]++;
		for (int i = 1; i < bound + 1; ++i)
			C[i] = C[i] + C[i - 1];
		for (int j = A.length - 1; j >= 0; --j) {
			B[C[A[j].getRating()] - 1] = A[j];
			--C[A[j].getRating()];
		}
	}
}
