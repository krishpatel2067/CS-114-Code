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
	 * The most number of times recursion has to be called is
	 * L = ceil(log_k(n)) = ceil(lg(n)/lg(k)) times (this can be seen by drawing out a tree-like
	 * diagram with the array and sub-array lengths at each "node"). 
	 * 
	 * At each level of that tree,
	 * the sub-array length (left - right + 1) is n/k^L. 
	 * */
	
	public static void main(String[] args) {
		Integer[] arr = {17, 19, 7, 4, 5};
		Integer[] temp = arr.clone();

		System.out.println(prt(arr, 0, arr.length - 1));
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
		
		int L = r - l + 1;
		int remainder = L % k;
		int greatestMul = L - remainder;
		int nEl = L / k;
		int newl = l;
		int mid;
		
		Integer[] indices;
		
		// use regular merge sort
		if (L < k)
		{
			indices = new Integer[2];
			mid = (l + r) / 2;
			indices[0] = l;
			indices[1] = mid + 1;
			System.out.println(tab + "_m(A, t, " + l + ", " + mid + ", k=" + k + ") " + prt(A, l, mid));
			System.out.println(tab + "_m(A, t, " + (mid + 1) + ", " + r + ", k=" + k + ") " + prt(A, mid + 1, r));
			mergeSort(A, temp, l, mid, k, tab + "\t");
			mergeSort(A, temp, mid + 1, r, k, tab + "\t");
		}
		// use modified merge sort
		else
		{
			indices = new Integer[k];
			for (int i = 0; i < k; i++)
			{
				// the last ones house L / k + 1 more element
				if (i >= k - remainder)
					mid = newl + nEl;
				// the first ones house L / k elements
				else
					mid = newl + nEl - 1;
				
				System.out.println(tab + "m(A, t, " + newl + ", " + mid + ", k=" + k + ") " + prt(A, newl, mid));
				mergeSort(A, temp, newl, mid, k, tab + "\t");
				indices[i] = newl;
				newl = mid + 1;
			}
		}
		
		for (int i = l; i <= r; i++)
			temp[i] = A[i];
		
		// replaced by indices arr
//		int i1 = l;
//		int i2 = mid + 1;
		
		Integer[] indicesCopy = indices.clone();
		
		System.out.println(tab + "indicesCopy: " + prt(indicesCopy, 0, -1));
		
		for (int curr = l; curr <= r; curr++)
		{
			System.out.println("\n" + tab + "curr = " + curr);
			// find the minimum from the k arrays
			int minI = 0;
			int minIndex = indices[0];
			
			// maybe the first one is exhausted (-1), so keep going until you find non-exhausted
			while (minIndex == -1)
			{
				minI++;
				minIndex = indices[minI];
			}
			
			T min = temp[minIndex];
			
			for (int i = 0; i < indices.length; i++)
			{	
				// check if exhausted
				if (i < indices.length - 1)
				{
					if (indices[i] >= indicesCopy[i + 1])
					{
						indices[i] = -1;
						
						if (i == minI)
							minIndex = -1;
					}
				}
				else
				{
					if (indices[i] > r)
					{
						indices[i] = -1;
						if (i == minI)
							minIndex = -1;
					}
				}
				
				if (indices[i] == -1)
				{
//					System.out.println(tab + "-1 index: " + i);
				}
				
				// min checking
//				System.out.println(tab + "checking: " + temp[indices[i]] + " & " + min);
				if ((indices[i] != -1 && temp[indices[i]].compareTo(min) < 0) || (indices[i] != -1 && minIndex == -1))
				{
					System.out.println(tab + "changing");
					minI = i;
					minIndex = indices[i];
					min = temp[minIndex];
				}
			}
			
			System.out.println(tab + "indices now " + prt(indices, 0, -1));
//			System.out.println(tab + "len " + indices.length);
//			System.out.println(tab + "minI " + minI);
			System.out.println(tab + "minIndex " + indices[minI]);
			A[curr] = temp[indices[minI]++];
			System.out.println(tab + "A now: " + prt(A, 0, -1));
		
//			if (i1 > mid)
//				A[curr] = temp[i2++];
//			else if (i2 > r)
//				A[curr] = temp[i1++];
//			else if (temp[i1].compareTo(temp[i2]) < 0)
//				A[curr] = temp[i1++];
//			else
//				A[curr] = temp[i2++];
		}
			
	}
	
	public static <T extends Comparable<T>> String prt(T[] arr, int l, int r)
	{
		if (r == -1)
			r = arr.length - 1;
		String str = "[";
		
		for (int i = l; i <= r; i++)
		{
			str += arr[i] + ", ";
		}
		
		return str + "]";
	}
}
