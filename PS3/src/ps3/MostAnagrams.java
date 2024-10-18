package ps3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MostAnagrams {

	/* ASYMPTOTIC ANALYSIS:
	 * Chosen algorithm: Cache + Length Filter
	 * 		
	 * 		* The number of allowed lines is denoted by n.
	 * 		* All sequential statements & conditionals without loops inside  
	 * 		  will run for some constant time c_i.
	 * 			* They can be ignored for asymptotic (large n) analysis.
	 * 		* The outer loop runs (n - 1) times (skipping the last element).
	 * 		* The inner loop runs (n - i) times.
	 * 		* The algorithm with a known big O is Arrays.sort() [or just sort()] with Llog(L).
	 * 			* L is the length of the word.
	 * 		* The inner loop uses sort() once.
	 * 		* The outer loop uses sort() once.
	 * 		* The result: sum[i = 0 -> n - 2]( Llog(L) + sum[j = i -> n - 1](Llog(L)) )
	 * 			* It ends up being a summation of Llog(L), so CLlog(L) for some constant C.
	 * 			* Since L does not approach large values (limited by length of words),
	 * 		 	  only n can be taken into account: Cnlog(n)
	 * 			* Thus, asymptotically: big_O(Cnlog(n)) = big_O(nlog(n))
	 * 		* The result is the same as the Arrays.sort() big O of nlog(n).
	 * */
	
	/* [EXTRA] RUDIMENTARY TESTING RESULTS:
	 * Time Efficiency Relative Ranking (best to worst):
	 * 		Cached + Length Filter	
	 * 		Length Filter
	 * 		Cached	
	 * 		Default
	 * 
	 * Execution time in seconds
	 * Efficiency Factor = (Default / Cache + Length Filter)
	 * 		n = 25,000 lines:
	 * 			Default: 21.525
	 * 			Cache: 5.331
	 * 			Length Filter: 3.570
	 * 			Cache + Length Filter: 1.921
	 * 			Efficiency Factor: 11.205
	 * 		n = 15,000 lines:
	 * 			Default: 7.545
	 * 			Cache: 1.876
	 * 			Length Filter: 1330
	 * 			Cache + Length Filter: 0.576
	 * 			Efficiency Factor: 13.099
	 * 		n = 8,000 lines:
	 * 			Default: 2.245
	 * 			Cached: 0.473
	 * 			Length Filter: 0.340
	 * 			Cache + Length Filter: 0.180
	 * 			Efficiency Factor: 12.472
	 * Average Efficiency Factor: 12.259
	 * 
	 * Fun fact: Cache + Length Filter at n = 25,000 lines is 
	 * still faster than Default at n = 8,000 lines!
	 * 
	 * Conclusions:
	 * 		* If execution time is of utmost priority, Cache + Length Filter is the best algorithm.
	 * 			* But it requires more memory for caching.
	 * 		* If memory AND execution time are important, Length Filter is the best all-rounder.
	 * 		* Default and Cache are NOT recommendable for either time or memory priorities.
	 * */
	
	static String WORDS_TXT_PATH = "wordsPS3.txt";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("How many lines to read from `words.txt`? ");
//		int maxLines = sc.nextInt();
		sc.close();
		
		// word bank (depending on input)
		// originally an array (changed for Quiz 4)
		ArrayList<String> allowedWords = new ArrayList<String>();
		
		try {
			File wordsTxt = new File(WORDS_TXT_PATH);
			Scanner wordsScanner = new Scanner(wordsTxt);
			
//			for (int i = 0; i < maxLines && wordsScanner.hasNext(); i++)
//				allowedWords[i] = wordsScanner.nextLine();
			for (int i = 0; wordsScanner.hasNext(); i++)
			{
				String word = wordsScanner.nextLine();
				if (word.length() == 4)
					allowedWords.add(word);
			}
			
			wordsScanner.close();
		
		} catch (FileNotFoundException e) { System.out.println(e.getStackTrace()); }
		
		// run program
		// (StrArr allowedWords, bool doDefult, bool doCache, bool doLengthFilter, bool doCacheLengthFilter)
		run(allowedWords, false, false, false, true);
	}
	
	public static void run(ArrayList<String> allowedWords, boolean doDef, boolean doCache, 
			boolean doLenFil, boolean doCacheLenFil)
	{
		long t0, t1;
		ArrayList<String> anagrams;
			
		System.out.println("\nTest mostAnagrams() method version(s):\n");
		
//		// default version
//		if (doDef)
//		{
//			System.out.println("Running default version...");
//			
//			t0 = System.currentTimeMillis();
//			anagrams = mostAnagrams_Default(allowedWords);
//			t1 = System.currentTimeMillis();
//			
//			System.out.print("\nResult from default version:\n");
//			System.out.print("\tMax # of anagrams: " + anagrams.size() + "\n\tAnagrams: ");
//			System.out.println(anagrams);
//			System.out.println("\tExecution time (ms): " + (t1 - t0));
//			System.out.println();
//		}
//		
//		// cache version
//		if (doCache)
//		{
//			System.out.println("Running cache version...");
//			
//			t0 = System.currentTimeMillis();
//			anagrams = mostAnagrams_Cache(allowedWords);
//			t1 = System.currentTimeMillis();
//			
//			System.out.print("\nResult from cache version:\n");
//			System.out.print("\tMax # of anagrams: " + anagrams.size() + "\n\tAnagrams: ");
//			System.out.println(anagrams);
//			System.out.println("\tExecution time (ms): " + (t1 - t0));
//			System.out.println();
//		}
//		
//		// length filter version
//		if (doLenFil)
//		{
//			System.out.println("Running length filter version...");
//			
//			t0 = System.currentTimeMillis();
//			anagrams = mostAnagrams_LengthFilter(allowedWords);
//			t1 = System.currentTimeMillis();
//			
//			System.out.print("\nResult from length filter version:\n");
//			System.out.print("\tMax # of anagrams: " + anagrams.size() + "\n\tAnagrams: ");
//			System.out.println(anagrams);
//			System.out.println("\tExecution time (ms): " + (t1 - t0));
//			System.out.println();
//		}
		
		// cache + length filter version
		if (doCacheLenFil)
		{
			System.out.println("Running cache + length filter version...");
			
			t0 = System.currentTimeMillis();
			anagrams = mostAnagrams_Cache_LengthFilter(allowedWords);
			t1 = System.currentTimeMillis();
			
			System.out.print("\nResult from cache + length filter version:\n");
			System.out.print("\tMax # of anagrams: " + anagrams.size() + "\n\tAnagrams: ");
			System.out.println(anagrams);
			System.out.println("\tExecution time (ms): " + (t1 - t0));
			System.out.println();
		}
	}
	
	public static ArrayList<String> mostAnagrams_Cache_LengthFilter(ArrayList<String> allowedWords)
	{
		ArrayList<String> mostAnagrams = new ArrayList<>();
		Map<String, String> anagramCache = new HashMap<String, String>();	// <word, sortedWord>
		int maxAnagrams = 0;
		
		for (int i = 0; i < allowedWords.size() - 1; i++)
		{	
			String sortedWord_i;
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			int targetLength = allowedWords.get(i).length();
			
			// check cache for pre-sorted word (from the inner loop from any previous outer iteration)
			if (anagramCache.containsKey(allowedWords.get(i)))
				sortedWord_i = anagramCache.get(allowedWords.get(i));
			else
				sortedWord_i = sortLetters(allowedWords.get(i));
			
			anagrams.add(allowedWords.get(i));
			
			for (int j = i + 1; j < allowedWords.size(); j++)
			{
				// for some reason, putting this in the loop condition doesn't work...
				if (!(allowedWords.get(j).length() == targetLength))
					continue;
				
				String sortedWord_j;
				
				// get cache if it exists, otherwise sort letters & store in the cache
				if (anagramCache.containsKey(allowedWords.get(j)))
					sortedWord_j = anagramCache.get(allowedWords.get(j));
				else
				{
					sortedWord_j = sortLetters(allowedWords.get(j));
					anagramCache.put(allowedWords.get(j), sortedWord_j);
				}
				
				if (sortedWord_i.equals(sortedWord_j))
				{
					anagrams.add(allowedWords.get(j));
					anagramCount++;
				}
			}
			
			if (anagramCount > maxAnagrams)
			{
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}
		
		return mostAnagrams;
	}
	
	public static ArrayList<String> mostAnagrams_Cache(String[] allowedWords)
	{
		ArrayList<String> mostAnagrams = new ArrayList<>();
		Map<String, String> anagramCache = new HashMap<String, String>();	// <word, sortedWord>
		int maxAnagrams = 0;
		
		for (int i = 0; i < allowedWords.length - 1; i++)
		{	
			String sortedWord_i;
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			
			// check cache for pre-sorted word (from the inner loop from any previous outer iteration)
			if (anagramCache.containsKey(allowedWords[i]))
				sortedWord_i = anagramCache.get(allowedWords[i]);
			else
				sortedWord_i = sortLetters(allowedWords[i]);
			
			anagrams.add(allowedWords[i]);
			
			for (int j = i + 1; j < allowedWords.length; j++)
			{
				String sortedWord_j;
				
				// get cache if it exists, otherwise sort letters & store in the cache
				if (anagramCache.containsKey(allowedWords[j]))
					sortedWord_j = anagramCache.get(allowedWords[j]);
				else
				{
					sortedWord_j = sortLetters(allowedWords[j]);
					anagramCache.put(allowedWords[j], sortedWord_j);
				}
				
				if (sortedWord_i.equals(sortedWord_j))
				{
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}
			
			if (anagramCount > maxAnagrams)
			{
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}
		
		return mostAnagrams;
	}
	
	public static ArrayList<String> mostAnagrams_LengthFilter(String[] allowedWords)
	{
		ArrayList<String> mostAnagrams = new ArrayList<>();
		int maxAnagrams = 0;
		
		for (int i = 0; i < allowedWords.length - 1; i++)
		{	
			String sortedWord_i = sortLetters(allowedWords[i]);
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			int targetLength = allowedWords[i].length();
			
			anagrams.add(allowedWords[i]);
			
			for (int j = i + 1; j < allowedWords.length; j++)
			{
				// for some reason, putting this in the loop condition doesn't work...
				if (!(allowedWords[j].length() == targetLength))
					continue;
				
				String sortedWord_j = sortLetters(allowedWords[j]);
				
				if (sortedWord_i.equals(sortedWord_j))
				{
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}
			
			if (anagramCount > maxAnagrams)
			{
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}
		
		return mostAnagrams;
	}
	
	public static ArrayList<String> mostAnagrams_Default(String[] allowedWords)
	{
		ArrayList<String> mostAnagrams = new ArrayList<>();
		int maxAnagrams = 0;
		
		for (int i = 0; i < allowedWords.length - 1; i++)
		{	
			String sortedWord_i = sortLetters(allowedWords[i]);
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			
			anagrams.add(allowedWords[i]);
			
			for (int j = i + 1; j < allowedWords.length; j++)
			{
				String sortedWord_j = sortLetters(allowedWords[j]);
				
				if (sortedWord_i.equals(sortedWord_j))
				{
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}
			
			if (anagramCount > maxAnagrams)
			{
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}
		
		return mostAnagrams;
	}

	public static String sortLetters(String s)
	{
		char[] arr = s.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}
}
