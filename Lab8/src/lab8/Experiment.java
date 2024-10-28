package lab8;

import java.util.ArrayList;
import java.text.NumberFormat;

/* Program sorts many arrays using one of the "Mystery" sorts
 * sort0-sort4.
 */

public class Experiment {
	static int REP = 100; // how many times to repeat experiment

	public static void main(String[] args) {

		int s = 0; // try sort(s,.)
		testSort(0); 			// heap sort (rule of elimination)
		testSort(1); 			// insertion sort (shuffled takes MUCH longer)
        testSort(3);			// selection sort (way slower than #0 even for sorted; sorted times growing by 4x every 2x input size)
        testSort(2);			// quick sort (instant stack overflow!)
		System.out.println("Finished");
	}

	public static void testSort(int s) {
		System.out.println("Testing sort " + s);
		MysterySorts srts = new MysterySorts();
		long start, now, elapsed;
		int n;
		int n0 = 10000;
		Integer[] A;

		// test sorted array over many array sizes
		System.out.println("Sorted");
		for (int epoch = 1; epoch <= 5; epoch++) {
			n = n0;
			n *= epoch;
			A = new Integer[n];

			start = System.currentTimeMillis();
			for (int rep = 0; rep < REP; ++rep) {
				for (int j = 0; j < n; ++j)
					A[j] = j;
				srts.sort(s, A);
			}
			now = System.currentTimeMillis();
			elapsed = now - start;
			System.out.println("\t[n = " + n + "] " + elapsed);
			System.out.println("\t\tAvg: " + (double) elapsed / REP);
		}

		// test shuffled array over many array sizes
		System.out.println("Shuffled");
		for (int epoch = 1; epoch <= 5; epoch++) {
			n = n0;
			n *= epoch;
			A = new Integer[n];

			start = System.currentTimeMillis();
			for (int rep = 0; rep < REP; ++rep) {
				for (int j = 0; j < n; ++j)
					A[j] = j;
				srts.shuffleArray(A);
				srts.sort(s, A);
			}
			now = System.currentTimeMillis();
			elapsed = now - start;
			System.out.println("\t[n = " + n + "] " + elapsed);
			System.out.println("\t\tAvg: " + (double) elapsed / REP);
		}

		n = n0;
		A = new Integer[n];

		// first try already sorted
		start = System.currentTimeMillis();
		for (int rep = 0; rep < REP; ++rep) {
			for (int j = 0; j < n; ++j)
				A[j] = j;
			srts.sort(s, A);
		}
		now = System.currentTimeMillis();
		elapsed = now - start;
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Sorted short array time: " + elapsed);
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Sorted short array avg time: " + (double) elapsed / REP);

		// now do longer arrays
		n = 2 * n0;
		A = new Integer[n];

		start = System.currentTimeMillis();
		for (int rep = 0; rep < REP; ++rep) {
			// srts.shuffleArray(A);
			for (int j = 0; j < n; ++j)
				A[j] = j;
			srts.sort(s, A);
		}
		now = System.currentTimeMillis();
		elapsed = now - start;
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Sorted long array time: " + elapsed);
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Sorted long array avg time: " + (double) elapsed / REP);

		// now shuffled
		n = n0;
		A = new Integer[n];

		start = System.currentTimeMillis();
		for (int rep = 0; rep < REP; ++rep) {
			for (int j = 0; j < n; ++j)
				A[j] = j;
			srts.shuffleArray(A);
			srts.sort(s, A);
		}
		now = System.currentTimeMillis();
		elapsed = now - start;
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Shuffled short array time: " + elapsed);
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Shuffled short array avg time: " + (double) elapsed / REP);

		// now do longer arrays
		n = 2 * n0;
		A = new Integer[n];

		start = System.currentTimeMillis();
		for (int rep = 0; rep < REP; ++rep) {
			for (int j = 0; j < n; ++j)
				A[j] = j;
			srts.shuffleArray(A);
			srts.sort(s, A);
		}
		now = System.currentTimeMillis();
		elapsed = now - start;
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Shuffled long array time: " + elapsed);
//		System.out.print("[n = " + n + "] ");
//		System.out.println("Shuffled long array avg time: " + (double) elapsed / REP);
		System.out.println();
	}
}
