package ps3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Q17Final {

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

			for (int i = 0; wordsScanner.hasNext(); i++) {
				String word = wordsScanner.nextLine();
				if (word.length() == 8)
					allowedWords.add(word);
			}

			wordsScanner.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getStackTrace());
		}

		// run program
		// (StrArr allowedWords, bool doDefult, bool doCache, bool doLengthFilter, bool
		// doCacheLengthFilter)
		run(allowedWords, false, false, false, true);
	}

	public static void run(ArrayList<String> allowedWords, boolean doDef, boolean doCache, boolean doLenFil,
			boolean doCacheLenFil) {
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
		if (doCacheLenFil) {
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

	public static ArrayList<String> mostAnagrams_Cache_LengthFilter(ArrayList<String> allowedWords) {
		ArrayList<String> mostAnagrams = new ArrayList<>();
		Map<String, String> anagramCache = new HashMap<String, String>(); // <word, sortedWord>
		int maxAnagrams = 0;

		for (int i = 0; i < allowedWords.size() - 1; i++) {
			String sortedWord_i;
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			int targetLength = allowedWords.get(i).length();

			// check cache for pre-sorted word (from the inner loop from any previous outer
			// iteration)
			if (anagramCache.containsKey(allowedWords.get(i)))
				sortedWord_i = anagramCache.get(allowedWords.get(i));
			else
				sortedWord_i = sortLetters(allowedWords.get(i));

			anagrams.add(allowedWords.get(i));

			for (int j = i + 1; j < allowedWords.size(); j++) {
				// for some reason, putting this in the loop condition doesn't work...
				if (!(allowedWords.get(j).length() == targetLength))
					continue;

				String sortedWord_j;

				// get cache if it exists, otherwise sort letters & store in the cache
				if (anagramCache.containsKey(allowedWords.get(j)))
					sortedWord_j = anagramCache.get(allowedWords.get(j));
				else {
					sortedWord_j = sortLetters(allowedWords.get(j));
					anagramCache.put(allowedWords.get(j), sortedWord_j);
				}

				if (sortedWord_i.equals(sortedWord_j)) {
					anagrams.add(allowedWords.get(j));
					anagramCount++;
				}
			}

			if (anagramCount > maxAnagrams) {
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}

		return mostAnagrams;
	}

	public static ArrayList<String> mostAnagrams_Cache(String[] allowedWords) {
		ArrayList<String> mostAnagrams = new ArrayList<>();
		Map<String, String> anagramCache = new HashMap<String, String>(); // <word, sortedWord>
		int maxAnagrams = 0;

		for (int i = 0; i < allowedWords.length - 1; i++) {
			String sortedWord_i;
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;

			// check cache for pre-sorted word (from the inner loop from any previous outer
			// iteration)
			if (anagramCache.containsKey(allowedWords[i]))
				sortedWord_i = anagramCache.get(allowedWords[i]);
			else
				sortedWord_i = sortLetters(allowedWords[i]);

			anagrams.add(allowedWords[i]);

			for (int j = i + 1; j < allowedWords.length; j++) {
				String sortedWord_j;

				// get cache if it exists, otherwise sort letters & store in the cache
				if (anagramCache.containsKey(allowedWords[j]))
					sortedWord_j = anagramCache.get(allowedWords[j]);
				else {
					sortedWord_j = sortLetters(allowedWords[j]);
					anagramCache.put(allowedWords[j], sortedWord_j);
				}

				if (sortedWord_i.equals(sortedWord_j)) {
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}

			if (anagramCount > maxAnagrams) {
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}

		return mostAnagrams;
	}

	public static ArrayList<String> mostAnagrams_LengthFilter(String[] allowedWords) {
		ArrayList<String> mostAnagrams = new ArrayList<>();
		int maxAnagrams = 0;

		for (int i = 0; i < allowedWords.length - 1; i++) {
			String sortedWord_i = sortLetters(allowedWords[i]);
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;
			int targetLength = allowedWords[i].length();

			anagrams.add(allowedWords[i]);

			for (int j = i + 1; j < allowedWords.length; j++) {
				// for some reason, putting this in the loop condition doesn't work...
				if (!(allowedWords[j].length() == targetLength))
					continue;

				String sortedWord_j = sortLetters(allowedWords[j]);

				if (sortedWord_i.equals(sortedWord_j)) {
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}

			if (anagramCount > maxAnagrams) {
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}

		return mostAnagrams;
	}

	public static ArrayList<String> mostAnagrams_Default(String[] allowedWords) {
		ArrayList<String> mostAnagrams = new ArrayList<>();
		int maxAnagrams = 0;

		for (int i = 0; i < allowedWords.length - 1; i++) {
			String sortedWord_i = sortLetters(allowedWords[i]);
			ArrayList<String> anagrams = new ArrayList<String>();
			int anagramCount = 1;

			anagrams.add(allowedWords[i]);

			for (int j = i + 1; j < allowedWords.length; j++) {
				String sortedWord_j = sortLetters(allowedWords[j]);

				if (sortedWord_i.equals(sortedWord_j)) {
					anagrams.add(allowedWords[j]);
					anagramCount++;
				}
			}

			if (anagramCount > maxAnagrams) {
				maxAnagrams = anagramCount;
				mostAnagrams = anagrams;
			}
		}

		return mostAnagrams;
	}

	public static String sortLetters(String s) {
		char[] arr = s.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}
}
