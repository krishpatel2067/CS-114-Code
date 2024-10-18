/*
 * RecursiveMultiply
 * Krish A. Patel (kap225)
 * 9/9/2024
*/

package lab1;

import java.util.Scanner;

public class RecursiveMultiply {

	public static int recCalls = 0;
	
	/* Efficiency Summary:
	  	1024 * 1024
		even/odd: (11 calls)
		3 parts: (7 calls)
		4 parts: (6 calls)
		
		200 000 000 * 200 000 000
		even/odd: (28 calls)
		3 parts: (18 calls)
		4 parts: (14 calls)
	*/
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter a positive integer: ");
		int a = sc.nextInt();
		System.out.print("Enter another positive integer: ");
		int b = sc.nextInt();
				
		long product = multiply(a, b);
		
		System.out.println(a + " * " + b + " = " + product);
		System.out.println("[Number of recursive calls: " + recCalls + "]");
		sc.close();
	}
	
	// version 2
	// no stack overflow even without 1 GB setting! :)
	// test 1024 * 1024 has 11 recursive calls
	// 200 000 000 * 200 000 000 has 28 recursive calls
//	public static long multiply(int a, int b)
//	{
//		System.out.println("Using multiply version 2");

//		// 2 * 3 = 2 + 2 * 2 = 2 + 2 + 2 * 1 = 2 + 2 + 2
//		// 5 * 10 = 5 + 5 + ... + 5
//			// 5 * 5 
//		// add `a` to itself `b` times
//		// but split cases for odd & even `b`
//		
//		recCalls++;
//		
//		if (b == 1)
//			return a;
//		
//		if (b % 2 == 0)
//		{
//			long halfProduct = multiply(a, b / 2);
//			return halfProduct + halfProduct;
//		}
//		else
//		{
//			long halfProduct = multiply(a, (b - 1) / 2);
//			return halfProduct + halfProduct + a;
//		}
//	}
	
	// version 3 (3 diff parts)
	// test 1024 * 1024 has 7 recursive calls
	// 200 000 000 * 200 000 000 has 18 recursive calls
//	public static long multiply(int a, int b)
//	{
//		System.out.println("Using multiply version 3");

//		// 5 * 9 = 5 * 3 + 5 * 3 + 5 * 3
//		// 5 * 8 = 5 * 2 + 5 * 2 + 5 * 2 + 5 * 2
//		// 5 * 7 = 5 * 2 + 5 * 2 + 5 * 2 + 5 * 1
//		
//		recCalls++;
//		
//		if (b == 0)
//			return 0;
//		else if (b == 1)
//			return a;
//		else if (b == 2)
//			return a + a;
//		
//		long thirdProduct = multiply(a, b / 3);
//		
//		if (b % 3 == 0)
//		{
//			// a third of the product
//			return thirdProduct + thirdProduct + thirdProduct;
//		}
//		else if (b % 3 == 2)
//		{
//			return thirdProduct + thirdProduct + thirdProduct + a + a;
//		}
//		else // if (b % 3 == 1)
//		{
//			return thirdProduct + thirdProduct + thirdProduct + a;			
//		}
//	}

	// version 4 (4 diff parts)
	// test 1024 * 1024 has 6 recursive calls
	// 200 000 000 * 200 000 000 has 14 recursive calls
	public static long multiply(int a, int b)
	{
		System.out.println("Using multiply version 4");
		// (r 0) 7 * 8 = 7 * 2 + 7 * 2 + 7 * 2 + 7 * 2
		// (r 3) 7 * 7 = 7 * 1 + 7 * 1 + 7 * 1 + 7 * 1 + 7 * 3
		// (r 2) 7 * 6 = 7 * 1 + 7 * 1 + 7 * 1 + 7 * 1 + 7 * 2
		// (r 1) 7 * 5 = 7 * 1 + 7 * 1 + 7 * 1 + 7 * 1 + 7 * 1
		
		recCalls++;
		
		if (b == 0)
			return 0;
		else if (b == 1)
			return a;
		else if (b == 2)
			return a + a;
		else if (b == 3)
			return a + a + a;
		
		long fourthProduct = multiply(a, b / 4);
		
		if (b % 4 == 0)
		{
			return fourthProduct + fourthProduct + fourthProduct + fourthProduct;
		}
		else if (b % 4 == 3)
		{
			// a third of the product
			return fourthProduct + fourthProduct + fourthProduct + fourthProduct + a + a + a;
		}
		else if (b % 4 == 2)
		{
			return fourthProduct + fourthProduct + fourthProduct + fourthProduct + a + a;
		}
		else // if (b % 4 == 1)
		{
			return fourthProduct + fourthProduct + fourthProduct + fourthProduct + a;			
		}
	}
}
