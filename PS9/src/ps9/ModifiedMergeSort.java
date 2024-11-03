package ps9;

public class ModifiedMergeSort {

	public static void main(String[] args) {

	}

	public static <T extends Comparable<T>> void mergeSort(T[] A, T[] temp, int l, int r, int k)
	{		
		if (l >= r)
			return;
		
		int newL = l;
		
		// if k = 3, L = 8, then i runs from 1 to 3
		for (int i = 1; i <= A.length / k + 1; i++)
		{
			int currMid = Math.min(i * (A.length / k), r);
			mergeSort(A, temp, newL, currMid, k);
			newL = currMid + 1;
		}
		
		for (int i = l; i <= r; i++)
			temp[i] = A[i];
		
		int i1 = l;
		int i2 = mid + 1;
		
		for (int curr = l; curr <= r; curr++)
		{
			if (i1 > mid)
				A[curr] = temp[i2++];
			else if (i2 > r)
				A[curr] = temp[i1++];
			else if (temp[i1].compareTo(temp[i2]) < 0)
				A[curr] = temp[i1++];
			else
				A[curr] = temp[i2++];
		}
			
	}
}
