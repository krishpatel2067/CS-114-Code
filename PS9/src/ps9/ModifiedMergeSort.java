/*
 * Krish A. Patel
 * Updated 11/5/2024
 * Due 11/6/2024
 * */

/*
 * There are many print statements that are commented out. They are usual for debugging
 * purposes and to follow the algorithm step-by-step. The parameter `tab` helps indent
 * the console once every recursive level for better readability.
 * 
 * The algorithm looks a bit complex but the print statements and different test inputs 
 * help a LOT (I know they did when I wrote it in 3-4 hours!).
 * */
package ps9;

public class ModifiedMergeSort {

	/* (1) TIME COMPLEXITY ANALYSIS
	 * Merge-sorting with k sub-arrays is much less efficient than traditional merge sort.
	 * 
	 * First, instead of calling merge sort twice recursively, now it is k times. Each sub-array
	 * is approximately n/k elements. The total number of times recursion has to be called is
	 * the number of nodes in a k-way tree, described by (k^L - 1)/(k-1), where L is the number
	 * of levels in the tree, given by ceil(log_k(n)). Thus, recursion has to be called
	 * (k^log_k(n) - 1)/(k-1) = (n-1)/(k-1). 
	 * 
	 * The for loop that copies elements from A to temp runs n/k times.
	 * 
	 * The for loop that runs from the left index to the right index runs n/k times. Inside the 
	 * for loop, the while loop that has to decide the index of the minimum element may run 
	 * at most k times. The inner for loop runs at most k times (for k indices during the merging
	 * process). The total time for the outer for loop is O((n/k)(k) + k) = O(n + k).
	 *
	 * Each recursion takes O(k + (n/k) + (n+k)) = O((n/k) + n + 2k) = O((n/k) + n + k).
	 * The total time by all recursions is O((n-1)/(k-1) * [k + (n/k) + (n+k)]). This simplifies
	 * to O(n^2/k^2 + n^2/k + n). The term n^2/k^2 is of lower order than n^2/k, so it can be
	 * disregarded. Thus, the simplified version is O(n^2/k + n).
	 * 
	 * If k << n, then the time complexity is roughly O(n^2). Clearly, this is worse than
	 * the traditional merge sort time of O(nlg(n)). However, this is assuming that k is variable,
	 * which requires a loop for finding the minimum instead of a single comparison. If k were 
	 * fixed like k = 3, then there may be slight (but not worthwhile) efficiency gains over 
	 * k = 2 (traditional) merge sort. Nonetheless, k-way merge sort is inefficient.
	 * 
	 * (2) RED JUGS & BLUE JUGS
	 * 
	 * (A) An algorithm that runs in theta(n^2) is the following:
	 * Start with one red jug, compare it with the FIRST blue jug. If the red jug pours exactly
	 * enough water to fill the blue jug without overflowing, then set aside the blue jug.
	 * If not, move to the next blue jug, and repeat the process for all the blue jugs. 
	 * By the end, there must be one blue jug set aside (precondition of the problem is that
	 * there is exactly 1 blue jug of equal capacity of every red jug). Checking n blue jugs for 
	 * every red jug (not stopping the process even when the match has been found early) takes theta(n) time. 
	 * So, checking n blue jugs for all n red jugs takes theta(n^2) time.
	 * 
	 * (B) It was proven on the lecture slides that the minimum number of pair-wise comparisons
	 * over an array of length n is omega(nlg(n)). In this case, because we are making pair-wise 
	 * comparisons between a red jug and a blue jug (to see whether they are of equal capacity),
	 * the lower bound for this algorithm is omega(nlg(n)). 
	 * 
	 * (C) A randomized algorithm (as opposed to the deterministic one in 2A) is the following:
	 * Start with one red jug, compare it with any random blue jug. If there is a match, the 
	 * algorithm is finished. If not, set aside the unmatched blue jug. Repeat this process on
	 * the remaining blue jugs for this same red jug until a match has been found. When the time
	 * the last blue jug is checked, there will have to have been a match. This algorithm
	 * does not always check all the blue jugs (only does so in the worst case, and may in fact
	 * check just one blue jug for every red jug). This means that the average number of
	 * comparisons is O(nlg(n)).
	 * 
	 * OPTIONAL EXERCISE:
	 * The i'th smallest element can be extracted from an array using quick selection, which
	 * has an average time of O(n). The selection algorithm can be modified to return the index
	 * j of the selected element as well. Then, the element at index j and the element at index
	 * i can be swapped, so that the i'th smallest element ends up correctly in index i. This
	 * would not place the other swapped element outside of the range k because if the smallest
	 * element (i = 0) was within range, then placing any other element at that location j
	 * will at most keep the range the same. 
	 * 
	 * As an example, extract the smallest element using quick selection, store index i = 0 and j.
	 * Swap elements at i and j. Repeat with the 2nd smallest element (i = 1, j = any valid index
	 * not 1). Do this for all the elements. Because swapping is a constant-time operation, the
	 * only thing adding to the time complexity is the quick selection of all elements, which
	 * is on average O(n).
	 * */
	
	public static void main(String[] args) {
		Integer[] arr = {17, 19, 7, 4, 5};
		Integer[] temp = arr.clone();

//		System.out.println(prt(arr, 0, arr.length - 1));
		mergeSort(arr, temp, 0, arr.length - 1, 3, "");
		
		for (Integer i : arr)
		{
			System.out.print(i + " ");
		}
	}

	public static <T extends Comparable<T>> void mergeSort(T[] A, T[] temp, int l, int r, int k, String tab)
	{			
		if (l >= r)
			return;
		
		int L = r - l + 1;		// current sub-array length
		int remainder = L % k;	// remainder - useful for splitting into evenly-sized sub-arrays
		int nEl = L / k;		// next sub-array number of elements
		int newl = l;			// "new l"
		int mid;				// midpoint
		
		// indices that start at the start of each sub-array during merging
		Integer[] indices;
		
		// use regular merge sort
		if (L < k)
		{
			indices = new Integer[2];
			
			// these are set just like regular merge sort
			mid = (l + r) / 2;
			indices[0] = l;
			indices[1] = mid + 1;
			
			// _m to differentiate between regular & modified merge sort
//			System.out.println(tab + "_m(A, t, " + l + ", " + mid + ", k=" + k + ") " + prt(A, l, mid));
//			System.out.println(tab + "_m(A, t, " + (mid + 1) + ", " + r + ", k=" + k + ") " + prt(A, mid + 1, r));
			mergeSort(A, temp, l, mid, k, tab + "\t");
			mergeSort(A, temp, mid + 1, r, k, tab + "\t");
		}
		// use modified merge sort
		else
		{
			indices = new Integer[k];
			for (int i = 0; i < k; i++)
			{
				// the last few sub-arrays one house L / k + 1 elements
				// e.g. [L=14,k=5] --> [2, 3, 3, 3, 3]
				if (i >= k - remainder)
					mid = newl + nEl;
				// the first ones house L / k elements
				else
					mid = newl + nEl - 1;
				
//				System.out.println(tab + "m(A, t, " + newl + ", " + mid + ", k=" + k + ") " + prt(A, newl, mid));
				// repeated recursion k times
				mergeSort(A, temp, newl, mid, k, tab + "\t");
				indices[i] = newl;
				newl = mid + 1;
			}
		}
		
		for (int i = l; i <= r; i++)
			temp[i] = A[i];
		
		// to store an un-changing backup
		Integer[] indicesCopy = indices.clone();
		
//		System.out.println(tab + "indicesCopy: " + prt(indicesCopy, 0, -1));
		
		for (int curr = l; curr <= r; curr++)
		{
//			System.out.println("\n" + tab + "curr = " + curr);
			// find the minimum from the k arrays
			int minI = 0;						// index in indices[] of index of minimum in temp[]
			int minIndex = indices[0];			// index of minimum in temp[]
			
			// maybe the first one is exhausted (-1), so keep going until you find non-exhausted
			while (minIndex == -1)
			{
				minI++;
				minIndex = indices[minI];
			}
			
			T min = temp[minIndex];
			
			for (int i = 0; i < indices.length; i++)
			{	
				// if there's at least one index after
				if (i < indices.length - 1)
				{
					// check if exhausted (indices trespassing into next sub-array)
					if (indices[i] >= indicesCopy[i + 1])
					{
						indices[i] = -1;			// represent as -1 index
						
						if (i == minI)				// update minIndex too
							minIndex = -1;
					}
				}
				// same thing, but for last element
				else
				{
					if (indices[i] > r)
					{
						indices[i] = -1;
						if (i == minI)
							minIndex = -1;
					}
				}
				
//				if (indices[i] == -1)
//				{
//					System.out.println(tab + "-1 index: " + i);
//				}
				
				// min checking
//				System.out.println(tab + "checking: " + temp[indices[i]] + " & " + min);
				// either curr index not exhausted & new min found
				// OR minIndex is -1 (from a now-exhausted index)
				if ((indices[i] != -1 && temp[indices[i]].compareTo(min) < 0) || (indices[i] != -1 && minIndex == -1))
				{
					System.out.println(tab + "changing");
					minI = i;
					minIndex = indices[i];
					min = temp[minIndex];
				}
			}
			
//			System.out.println(tab + "indices now " + prt(indices, 0, -1));
//			System.out.println(tab + "len " + indices.length);
//			System.out.println(tab + "minI " + minI);
//			System.out.println(tab + "minIndex " + indices[minI]);
			
			// increment each index used (just like regular merge sort)
			A[curr] = temp[indices[minI]++];
//			
//			System.out.println(tab + "A now: " + prt(A, 0, -1));
		}
			
	}
	
	// to print arrays conveniently (mainly for debugging & following the algorithm - no sorting functionality affected from this
	public static <T extends Comparable<T>> String prt(T[] arr, int l, int r)
	{	
		r = r == -1 ? arr.length - 1 : r;
		String str = "[";
		
		for (int i = l; i <= r; i++)
			str += arr[i] + ", ";
		
		return str + "]";
	}
}
