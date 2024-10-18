package lab4;

import java.util.ArrayList;
import java.util.Scanner;

public class SubsetsDump {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a string to find the subset of: ");
		String str = sc.nextLine();
		sc.close();
		
		// each letter printed 2 ^ (length - index + 1)
		ArrayList<String> prev = new ArrayList<>();
		
		for (int start = 0; start < str.length(); start++)
		{
			System.out.println(str.substring(start, start + 1));
			
			for (int end = start + 1; end < str.length(); end++)
			{
				System.out.println(str.substring(start, end + 1));
			}
		}
		
//		for (int start = 0; start < str.length(); start++)
//		{
////			System.out.println("outside: " + str.substring(start, start + 1));
//			String s = str.substring(start, start + 1);
//			if (!prev.contains(s))
//			{
//				System.out.println(s);
//				prev.add(s);
//			}
//			
//			for (int inc = 1; inc < str.length(); inc++)
//			{
//				for (int end = start + 1; end < str.length(); end += inc)
//				{
////					System.out.println("end: " + str.substring(start, end + 1));
//					s = str.substring(start, end + 1);
//					if (!prev.contains(s))
//					{
//						System.out.println(s);
//						prev.add(s);
//					}
//				
////	//				if (end == str.length() - 1)
//					if (end - start >= 2)
//					{
//						for (int i = str.length() - 2; i >= 1 && i > start; i--)
//						{
////							System.out.println("inside " + str.substring(start, i) + str.substring(i + 1));
//							s = str.substring(start, i) + str.substring(i + 1);
//							if (!prev.contains(s))
//							{
//								System.out.println(s);
//								prev.add(s);
//							}
//						}
//					}
//				}
//			}
//		}
	}

	public static void run(String str)
	{
		System.out.println(str);
		
		if (str.length() > 1)
			run(str);
	}
}
