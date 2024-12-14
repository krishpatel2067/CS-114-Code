/*
 * FindSubsetFromScrambled (PS5, CS114 H03)
 * Krish A. Patel
 * Due on 10/9/2024
 * */

package ps5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FindSubsetFromScrambled {

	private static ArrayList<String> words = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a scrambled string whose English word subsets to find (max length 9): ");
		String scrambled = sc.nextLine();
		sc.close();

		if (scrambled.length() > 9) {
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
			e.printStackTrace();
		}

		run(scrambled);
	}

	public static void run(String scrambled) {
		// standardize just in case
		scrambled = scrambled.toLowerCase();

		ArrayList<Character> scrChars = new ArrayList<>(); // scrambled chars
		ArrayList<String> wordsMatched = new ArrayList<>(); // all matched words
		int maxMatchLen = 0;

		scrChars = toCharArrayList(scrambled);

		for (String word : words) {
			boolean doesMatch = true;

			for (char c : word.toCharArray()) {
				int index = scrChars.lastIndexOf(c);

				// if a char was found in the scrambled word, then remove it
				// (to handle duplicates)
				if (index != -1)
					scrChars.remove(index);

				// every char has to match otherwise no need to check the rest of the word
				else {
					doesMatch = false;
					break;
				}
			}

			// reset scrambled
			scrChars = toCharArrayList(scrambled);

			// if there was a match
			if (doesMatch) {
				int wordLen = word.length();

				// only add it if length >= current max
				// and only clear the matched if the max len is increased
				if (wordLen > maxMatchLen) {
					wordsMatched.clear();
					wordsMatched.add(word);
					maxMatchLen = wordLen;
				} else if (wordLen == maxMatchLen) {
					wordsMatched.add(word);
				}
			}

		}

		// display the results
		for (String word : wordsMatched)
			System.out.println(word);
	}

	// turn a string into an ArrayList of chars
	public static ArrayList<Character> toCharArrayList(String str) {
		ArrayList<Character> al = new ArrayList<>();
		for (char c : str.toCharArray())
			al.add(c);
		return al;
	}

}
