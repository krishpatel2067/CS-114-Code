package ps9;

public class ModifiedMergeSort {

	public static void main(String[] args) {
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7};
		Integer[] temp = {1, 2};
		mergeSort(arr, temp, 0, 6, 3);
	}

	public static <T extends Comparable<T>> void mergeSort(T[] A, T[] temp, int l, int r, int k)
	{		
		if (l >= r)
			return;
		
		int L = r - l + 1;
		int newL = l;
		int currMid = 0;
		int inc = Math.max(L / k + 1, 1);
		
		if (L % k == 0) 
			inc = 1;
		
		int[] indices;
		
		if (L < k) {
			int mid = (l + r) / 2;
			
			indices = new int[2];
			System.out.println("_mergeSort(A, temp, " + l +", " + (mid - 1) + ", k);");
			System.out.println("_mergeSort(A, temp, " + mid +", " + r + ", k);");
			indices[0] = l;
			indices[1] = mid;
//			mergeSort(A, temp, l, mid - 1, k);
//			mergeSort(A, temp, mid, r, k);
		} 
		else 
		{
			indices = new int[k];
			// if k = 3, L = 8, then i runs from 1 to 3
			for (int i = 0; i < k; i++)
			{
				// so it doesn't index out of bounds
				currMid = Math.min(currMid + inc, r);
				
				// so no element is missed at the end
				if (i == k -1)
					currMid = r + 1;
				
				System.out.println("mergeSort(A, temp, " + newL +", " + (currMid - 1) + ", k);");
//				mergeSort(A, temp, newL, currMid - 1, k);
				indices[i] = newL;
				newL = currMid;
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
