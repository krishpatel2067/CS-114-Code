/*
 * InsertBlanks for PS2
 * Krish A. Patel
 * Due: 9/18/2024
 * */

package ps2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InsertBlanks {
	
	/*
	 * INSTURCTIONS:
	 * Uncomment lines with TRACK_ALGORITHM at the end to see the process in the console.
	 * */
	
	public static ArrayList<String> words = new ArrayList<String>();

	public static void main(String[] args) {
		
		String DELIMITER = " ";
		
		String gettysburg;
		String briarRose;
		
		// all the try-catch blocks in case file can't be found
		// -----------------------------------------------------------------
		// words
		try {
			File wordsTxt = new File("words.txt");
			Scanner wordsSc = new Scanner(wordsTxt);
			
			while (wordsSc.hasNextLine())
			{
				words.add(wordsSc.nextLine());
			}
									
			wordsSc.close();
		} catch (FileNotFoundException e) {
			System.out.println("`words.txt` file not found");
			return;
		}
		
		// Gettysburg
		try {
			File gettysburgTxt = new File("Gettysburg.txt");
			Scanner gettysburgSc = new Scanner(gettysburgTxt);
			
			gettysburg = gettysburgSc.nextLine();
			
			gettysburgSc.close();
		} catch (FileNotFoundException e) {
			System.out.println("`gettysburg.txt` file not found");
			return;
		}
		
		// BriarRose
		try {
			File briarRoseTxt = new File("BriarRose.txt");
			Scanner briarRoseSc = new Scanner(briarRoseTxt);
			
			briarRose = briarRoseSc.nextLine();
			
			briarRoseSc.close();
		} catch (FileNotFoundException e) {
			System.out.println("`BriarRose.txt` file not found");
			return;
		}
		
		// -------------------------------------------------------------------------
		
		ArrayList<String> wordsWithBlanks = splitIntoWords(gettysburg);
		
		System.out.println("Result of inserting blanks in `gettysburg.txt`: ");
		
		for (String word : wordsWithBlanks)
			System.out.print(word + DELIMITER);
		
		wordsWithBlanks = splitIntoWords("ouncewabblyscruffyorbitalrecusemarblertelfordstangrampiaster");
		
		System.out.println("\n\nResult of inserting blanks in `BriarRose.txt`: ");
		
		for (String word : wordsWithBlanks)
			System.out.print(word + DELIMITER);
	}
	
	// returns split words, or none if they aren't words, in an ArrayList
	public static ArrayList<String> splitIntoWords(String str)
	{
		// the below is most likely not necessary?
//		if (str == "")
//		{
//			return new ArrayList<String>();
//		}
		
		// i controls where to put "test spaces"
		for (int i = 1; i < str.length(); i++)
		{
			String potentialWord1 = str.substring(0, i);
			String potentialWord2 = str.substring(i);
			
//			System.out.println(potentialWord1 + " | " + potentialWord2);	// TRACK_ALGORITHM
			
			if (words.contains(potentialWord1))
			{
				if (words.contains(potentialWord2))
				{
					return new ArrayList<String>(Arrays.asList(potentialWord1, potentialWord2));
				}
				else
				{
//					System.out.println("--------------------");				// TRACK_ALGORITHM
					ArrayList<String> tempResult = splitIntoWords(potentialWord2);
					
					if (tempResult.size() != 0)
					{
						ArrayList<String> result = new ArrayList<String>();
						result.add(potentialWord1);
						result.addAll(tempResult);
						return result;
					}
				}
			}
		}
		
		// base case
		// after reaching the end of `str,` no possible spacing results in both group of letters being words
		return new ArrayList<String>();
	}

}
