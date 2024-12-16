package ps6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.ArrayList;

/*
 * Identify English words with the most homophones.
 */
public class Q8Final {

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

				if (p.getWord().length() == 3)
					PDict.insert(p.getPhonemes(), p);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int count = 0;

		for (Pronunciation p : PDict.values()) {
			// iterate through all the same pronunciation words
			for (Pronunciation p2 : PDict.findAll(p.getPhonemes())) {
				if (!p.getWord().equals(p2.getWord())) {
					System.out.println(p.getWord() + " " + p2.getWord() + " " + count);
					count++;
				}
			}

		}

		System.out.println("Count: " + count);

		long elapsed = System.currentTimeMillis() - startTime;
		System.out.println("Elapsed time: " + elapsed);
	}
}
