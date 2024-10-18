package ps1;

import java.util.Scanner;

public class SumOfPrevious {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the length of the array: ");
		int len = sc.nextInt();
		System.out.print("Enter the array, each element separated by a space: ");
		int[] arr = new int[len];
		
		for (int i = 0; i < len; i++)
			arr[i] = sc.nextInt();
		
		boolean result = hasSumOfPrevious(arr, len - 1);
		System.out.print(result);
		
		sc.close();
	}
	
	// number at `i` - is this a sum of any two previous numbers?
	public static boolean hasSumOfPrevious(int[] arr, int i)
	{
		// 3 8 2 7 4 -> true
		if (i <= 0)
			return false;
				
		for (int j = 0; j < i; j++)
		{
			
			// starting at j covers the case where the addends are the same number
			for (int k = j; k < i; k++)
			{
				if (arr[j] + arr[k] == arr[i])
					return true;
			}
		}
		
		return hasSumOfPrevious(arr, i - 1);
	}
}
