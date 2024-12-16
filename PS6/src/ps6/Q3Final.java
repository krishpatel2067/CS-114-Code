package ps6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//import java.util.ArrayList;

/*
 * Identify English words with the most homophones.
 */
public class Q3Final {

	public static void main(String[] args) {
		BST<String, Pronunciation> PDict = new BST<String, Pronunciation>();
		File file = new File("cmudict.0.7a.txt");

		long startTime = System.currentTimeMillis();

		// Read in the cmu pronunciation dictionary.
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.substring(0, 3).equals(";;;"))
					continue; // skip comment lines
				Pronunciation p = new Pronunciation(line);
				
				if (p.getWord().length() == 15 || p.getWord().length() == 14)
					PDict.insert(p.getPhonemes(), p);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Pronunciation p : PDict.values()) {
			String word = p.getWord();

			if (word.length() != 15)
				continue;

			if (doesRepeat(word))
				continue;

			// remove letter
			ArrayList<String> words14 = removeEachLetter(word);
//			System.out.println(word);
//			print(removedPermutations);
//			System.out.println();

			// ACKNOWLEDGEMENT is the answer!!
			for (Pronunciation sameP : PDict.findAll(p.getPhonemes())) {
				int index = words14.indexOf(sameP.getWord());
				if (index != -1) {
					System.out.println(word + " -- " + words14.get(index));
					System.out.println(p.getPhonemes() + " -- " + sameP.getPhonemes());
					System.out.println();
				}
			}
		}

//		for (Pronunciation q : PDict.findAll(P.getPhonemes()))
//			System.out.println(q.getPhonemes() + ", " + q.getWord());

		long elapsed = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed time: " + elapsed);
	}

	public static boolean doesRepeat(String word) {
		// check repeat adjacent letters
		for (int i = 1; i < word.length(); i++) {
			char last = word.charAt(i - 1);
			char curr = word.charAt(i);

			if (last == curr)
				return true;
		}
		
		return false;
	}

	public static ArrayList<String> removeEachLetter(String str) {
		ArrayList<String> al = new ArrayList<String>();

		for (int i = 0; i < str.length(); i++) {
			String first = str.substring(0, i);
			String last = "";

			if (i + 1 < str.length()) {
				last = str.substring(i + 1);
			}

			al.add(first + last);
		}

		return al;
	}

	public static void print(String[] arr) {
		for (String s : arr)
			System.out.print(s + " ");
	}
}
