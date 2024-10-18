package lab4;

import java.util.ArrayList;

public class Subsets {

	public static void main(String[] args) 
	{
		String str = "abcd";

		run(str);
	}
	
	public static void run(String str)
	{
		ArrayList<String> al = subsets(str, "", 0, str);
		
		// weed out repeats
		for (int i = al.size() - 1; i >= 0; i--)
		{
			String el = al.get(i);
			
			if (al.subList(0, i).contains(el))
//			if (false)
			{
				al.remove(i);
			}
			else
			{
//				al.set(i, reverse(el));
				System.out.println(al.get(i));
			}
		}
				
	}
	
	// pl = parent letter
	// lvl - recursion level
	public static ArrayList<String> subsets(String str, String pl, int lvl, String fullStr)
	{
		System.out.println(lvl + " " + str);
//		String pl = lvl > 0 ? fullStr.substring(lvl - 1, lvl) : "";
		String fl = str.substring(0, 1);
		ArrayList<String> al = new ArrayList<String>();

		if (str.length() <= 1)
		{
			al.add(str);
			al.add(pl + str);
		}
		else
		{
			for (int i = 1; i < str.length(); i++)
			{
				String param1 = str.substring(i);
//				String param2 = fl;
				ArrayList<String> result = subsets(param1, fl, lvl + 1, fullStr);
				String parentStr = "";
				String parent;
				String last;
				
				System.out.println("returned to " + lvl + " " + str);
				al.addAll(result);
				last = al.getLast();
				System.out.println(result);
				
				// run lvl times
				for (int j = 0; j < lvl; j++)
				{
					parent = fullStr.substring(lvl - j - 1, lvl - j);
					System.out.println(parent + fl);
					
					if (j > 0)
					{			
						System.out.println("ran");
						al.add(parentStr + fl);
						System.out.println(parent + parentStr + fl);
					}
					al.add(parentStr + parent + fl);
					parentStr += parent;
				}
				
				System.out.println("after adding gp + fl " + al);
				
				al.add(fl);
				System.out.println("before adding pl " + al);
				al.add(pl + last);
				System.out.println("after adding pl " + al);
//				al.add(pl + fl);
			}
		}
		System.out.println();
		
		return al;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// fullStr - abcd
	// running str - c->b->a (e.g.)
//	public static ArrayList<String> subsets(String str, String parentLetter)
//	{
//		ArrayList<String> al = new ArrayList<String>();
////		String str = fullStr.substring(i);
//		String parentLetter = (i > 0) ? str.substring(i - 1, i) : "";
//		System.out.println("parentLetter: " + parentLetter);
//		System.out.println("fullStr: " + str);
//		System.out.println("str: " + str);
//		System.out.println("____________________________");
//		
//		if (str.length() <= 1 || i >= str.length())
//		{	
//			al.add(str);
//			al.add(parentLetter + str);
//			System.out.println("Returning!");
//			System.out.println("-------------");
//		}
//		else
//		{		
//			for (int j = 0; j < str.length(); j++)
//			{			
//				System.out.println(str + " J = " + j);
////				String sub = str.substring(j);
//				ArrayList<String> result = subsets(str, i + j);
//				
////				System.out.println("fullStr " + str);
////				System.out.println("sub " + sub);
////				System.out.println(result);
//				
//				System.out.println("result before " + result);
//				al.addAll(result);
//				System.out.println("result after " + result);
//				// append first letter to the last element & create a new element
////				System.out.println("before appending: " + result.get(result.size() - 1));
//				al.add(parentLetter + al.get(al.size() - 1));
////				System.out.println("after appending: " + al.get(al.size() - 1));
//				System.out.println();
//			}
//			
//			
//		}
//		
//		return al;
//	}
	
	public static String reverse(String s)
	{
		String result = "";
		
		for (int i = s.length() - 1; i >= 0; i--)
			result = result.concat(s.substring(i, i + 1));
		
		return result;
	}
}
