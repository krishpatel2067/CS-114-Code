package ps5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindSubsetFromScrambledDump {

	private static ArrayList<String> words = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a scrambled string whose English word subsets to find (max length 9): ");
		String scrambled = sc.nextLine();
		sc.close();
		
		if (scrambled.length() > 9)
		{
			System.out.println("Input too long. Rerun the program & try a shorter word.");
			return;
		}
				
		File wordsTxt = new File("words.txt");
		
		try {
			sc = new Scanner(wordsTxt);
			while (sc.hasNextLine())
				words.add(sc.nextLine());
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		run(scrambled);
	}
	
	public static void run(String scrambled)
	{
		scrambled = scrambled.toLowerCase();
		ArrayList<Character> scrChars = new ArrayList<>();
		ArrayList<String> matched = new ArrayList<>();
		int maxMatchLen = 0;
		
		scrChars = toCharArrayList(scrambled);
			
		for (String word : words)
		{
			boolean matches = true;
			
			for (char c : word.toCharArray())
			{
				int index = scrChars.lastIndexOf(c);
				
				if (index != -1)
				{
					scrChars.remove(index);
				}
				else
				{
					matches = false;
					break;
				}
			}
			
			scrChars = toCharArrayList(scrambled);
			
			if (matches)
			{
				int wordLen = word.length();
				
				if (wordLen > maxMatchLen)
				{
					matched.clear();
					matched.add(word);
					maxMatchLen = wordLen;
				}
				else if (wordLen == maxMatchLen)
				{
					matched.add(word);
				}
			}
				
		}
		
		for (String word : matched)
		{
			System.out.println(word);
		}
	}
	
	public static ArrayList<Character> toCharArrayList(String str)
	{
		ArrayList<Character> al = new ArrayList<>();
		for (char c : str.toCharArray())
			al.add(c);
		return al;
	}

//	public static void run(String scrambled)
//	{
//		ArrayList<String> subsets = getSubsets(scrambled);
//		ArrayList<String> maxLenWordsFound = new ArrayList<>();
//		int maxLenFound = 0;
//		
//		for (String sub : subsets)
//		{
//			if (words.contains(sub))
//			{
//				int subLen = sub.length();
//				
//				if (subLen > maxLenFound)
//				{
//					maxLenFound = subLen;
//					maxLenWordsFound.clear();
//				}
//				
//				if (subLen == maxLenFound)
//				{
//					maxLenWordsFound.add(sub);
//				}
//			}
//		}
//		
//		System.out.println(maxLenWordsFound);
//	}
//	
//	public static ArrayList<String> getSubsets(String word, int desiredLen)
//	{
//		ArrayList<String> result = new ArrayList<>();
//		
//		for (int i = 0; i < desiredLen; i++)
//		{
//			for (int start = 0; start < word.length(); start++)
//			{
//				
//			}
//		}
//		
//		return result;
//	}
//	
//	public static ArrayList<String> subsets(int i)
//	{
//		if (i <= 0)
//			return;
//		
//		
//	}
	
}
