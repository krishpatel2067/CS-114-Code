package playground;

public class Playground {

	public static void main(String[] args) {
		
		int[] arr = {9, 8, 15, 7, 61, 5, 2, 10};
		
		quickSort(arr, 0, arr.length - 1);
		
		for (int i : arr)
			System.out.print(i + " ");
	}

	private static void quickSort(int[] arr, int start, int end) {
		if (end <= start) return;
		
		int pivIndx = partition(arr, start, end);
		
		quickSort(arr, start, pivIndx - 1);
		quickSort(arr, pivIndx + 1, end);
	}

	private static int partition(int[] arr, int start, int end) {
		int pivIndx = end;
		int pivot = arr[end];
		int i = start - 1;
		
		for (int j = start; j < pivIndx; j++)
		{
			if (arr[j] < pivot)
			{
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		
		i++;
		arr[pivIndx] = arr[i];
		arr[i] = pivot;

		return i;
	}

}
