package ps9;

public class ModifiedMergeSort {

	public static void main(String[] args) {
		Integer[] arr = {1, 2};
		Integer[] temp = {1, 2};
		mergeSort(arr, temp, 0, arr.length - 1, 3);
	}

	public static <T extends Comparable<T>> void mergeSort(T[] A, T[] temp, int l, int r, int k)
	{		
		if (l >= r)
			return;
		
		int L = r - l + 1;
		int remainder = L % k;
		int greatestMul = L - remainder;
		int nEl = L / k;
		int newl = l;
		int mid;
		
		// use regular merge sort
		if (L < k)
		{
			mid = (l + r) / 2;
			System.out.println("_m(A, t, " + l + ", " + mid + ", k=" + k + ")");
			System.out.println("_m(A, t, " + (mid + 1) + ", " + r + ", k=" + k + ")");
		}
		// use modified merge sort
		else
		{
			for (int i = 0; i < k; i++)
			{
				// the last ones house L / k + 1 more element
				if (i == k - remainder)
					mid = newl + nEl;
				// the first ones house L / k elements
				else
					mid = newl + nEl - 1;
				
				System.out.println("m(A, t, " + newl + ", " + mid + ", k=" + k + ")");
	//			mergeSort(A, temp, newl, mid, k);
				newl = mid + 1;
			}
		}
		
//		for (int i = l; i <= r; i++)
//			temp[i] = A[i];
//		
//		int i1 = l;
//		int i2 = mid + 1;
		
		
		
//		for (int curr = l; curr <= r; curr++)
//		{
//			if (i1 > mid)
//				A[curr] = temp[i2++];
//			else if (i2 > r)
//				A[curr] = temp[i1++];
//			else if (temp[i1].compareTo(temp[i2]) < 0)
//				A[curr] = temp[i1++];
//			else
//				A[curr] = temp[i2++];
//		}
			
	}
}
