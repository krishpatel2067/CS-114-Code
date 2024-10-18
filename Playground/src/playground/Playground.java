package playground;

import java.util.ArrayList;
import java.util.Arrays;

public class Playground {

	public static void main(String[] args) {
//		double val = ipow(2, 5);
		
//		ArrayList<Integer> i = new ArrayList<Integer>(Arrays.asList(1, 2, 3));		
//		ArrayList<Integer> j = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
//		
//		j.addAll(i);
//		
//		for (int n : j)
//		{
//			System.out.println(n);
//		}
		
		// test change
		// test change 2
		// test change 3
		// test change 4
		
		Integer myInt = 2;
		integer(myInt);
		System.out.println(myInt);
	}
	
	public static void integer(Integer i)
	{
		i++;
	}
	
	public static void recursive(int i)
	{
		if (i == 0)
			return;
		
		System.out.println(i);
		recursive(--i);
	}
	
	public static double lpow(double x, int n) {
		double y = x;
		for (int i = 1; i < n; i++)
			y *= x;
		return y;
	}
	
	public static double rpow(double x, int n) {
		if (n == 0)
			return 1.0;
		return x * rpow(x, n - 1);
	}
	
	public static double ipow(double base, int exp) {
		double result = 1.0;
		while (exp != 0) {
			if ((exp & 1) == 1)
				System.out.println("If statement triggered: result: " + result + " base: " + base);
				result *= base;
			System.out.println("Before: " + exp);
			exp >>= 1;
			System.out.println("After: " + exp);
			base *= base;
		}
		return result;
	}

}
