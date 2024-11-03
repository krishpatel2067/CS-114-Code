package ps9;

public class VanillaMergeSort {
	
	public static void main(String args[])
	{
		Integer[] arr = { 7, 3, 5, 8, 2, 9, 1 };
		Integer[] temp = arr.clone();
		
		mergeSort(arr, temp, 0, arr.length - 1);
		
		for (Integer i : arr)
			System.out.print(i + " ");
	}
	
	/* given by professor */
	public static <T extends Comparable<T>> void mergeSort(T[] A, T[] temp, int l, int r)
	{		
		if (l >= r)
			return;
		
		int mid = (l + r) / 2;
		mergeSort(A, temp, l, mid);
		mergeSort(A, temp, mid + 1, r);
		
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
