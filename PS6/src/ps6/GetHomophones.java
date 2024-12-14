package ps6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetHomophones {
	
	/*
	 * Running time in terms of n:
	 * 
	 * UALDictionary:
	 * 		* Each element gets added at the end of the internal list, so O(1) for insertion per element.
	 * 			* So, initialization takes O(n) for n elements.
	 * 		* To process a query, UALDictionary uses linear search, so it takes O(n) to search.
	 * OALDictionary:
	 * 		* Each element added gets placed so that the internal list remains sorted.
	 * 			* So, each time an element is inserted, it has to iterate until it finds an existing element greater than
	 * 			  that which to insert, taking O(m) for each element, where m is the length of the internal list.
	 * 			* Meaning, for n elements, it takes O(mn) or O(n^2) (since m grows with n).
	 * 		* To process a query, it takes O(lg(n)) because it uses binary search by default.
	 * BST:
	 * 		* Each element added gets placed so as to maintain an "order."
	 * 		* But this is done in a binary structure (sort of like binary search).
	 * 		* So, to insert each element, it takes O(lg(m)), where m is the number of existing nodes.
	 * 		* Then, for n elements it takes O(nlg(m)), or O(nlg(n)). 
	 * 		* To process a query, it takes O(lg(n)) because it is essentially binary search.
	 * 
	 * Summary:
	 * 		* BST & OALDictionary have similar query processing times.
	 * 		* But OALDictionary is considerably slower to initialize due to a polynomial time complexity, compared to linear and logarithmic for the others.
	 * 		* BST is the overall winner.
	 * */
	
	/*
	 * Exercise:
	 * Worst case running times for:
	 * 		(1) Unordered ArrayList
	 * 			* insert: O(1) (always insert at the end regardless of how many elements)
	 * 			* find: O(n) (need to find the element, which might be all the way at the end)
	 * 			* remove: O(n) (similar to find, just with a remove operation which takes O(1) time)
	 * 		(2) Ordered ArrayList
	 * 			* insert: O(n) (might be the largest number each time, having to traverse the entire list)
	 * 			* find: lg(n) (binary search possible with sorted values)
	 * 			* remove: lg(n) (similar to find, just with a remove operation which takes O(1) time)
	 * */
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a valid English word to find the homophones of: ");
		String query = input.next().toUpperCase();
		input.close();
		
		System.out.println();
		
		// medium speed overall (fastest initialization!)
		runUALDictionary("cmudict.0.7a.txt", query);	
		
		// slowest (by far)! (291789 ms init, 1 ms main, 291790 ms elapsed)
		// runOALDictionary("cmudict.0.7a.txt", query); 
		
		// fastest overall (fastest query, fast initialization!)
		runBST("cmudict.0.7a.txt", query);			
		
		System.out.println("Finished");
	}
	
	/* Different run functions for each Dictionary implemention. */
	
	public static void runUALDictionary(String fileName, String query)
	{
		long t0 = System.currentTimeMillis();
		UALDictionary<String, Pronunciation> storage = new UALDictionary<>();
		String queryPho = initStorage(fileName, storage, query);
		long tInit = System.currentTimeMillis();
						
		System.out.println("Homophones of "+query+" (\""+queryPho+" \") for UALDictionary:");
		
		for (Pronunciation match : storage.findAll(queryPho))
			if (!match.getWord().equals(query))
				System.out.println("\t" + match.getWord());
		
		long tElapsed = System.currentTimeMillis();
		
		System.out.println();
		System.out.println("Time Summary for UALDictionary: ");
		System.out.println("\tInitialization: " + (tInit - t0) + " ms");
		System.out.println("\tMain algorithm: " + (tElapsed - tInit) + " ms");
		System.out.println("\tElapsed: " + (tElapsed - t0) + " ms");
		System.out.println();
	}
	
	public static void runOALDictionary(String fileName, String query)
	{
		long t0 = System.currentTimeMillis();
		OALDictionary<String, Pronunciation> storage = new OALDictionary<>();
		String queryPho = initStorage(fileName, storage, query);
		long tInit = System.currentTimeMillis();
						
		System.out.println("Homophones of "+query+" (\""+queryPho+" \") for OALDictionary:");
		
		for (Pronunciation match : storage.findAll(queryPho))
			if (!match.getWord().equals(query))
				System.out.println("\t" + match.getWord());
		
		long tElapsed = System.currentTimeMillis();
		
		System.out.println();
		System.out.println("Time Summary for OALDictionary: ");
		System.out.println("\tInitialization: " + (tInit - t0) + " ms");
		System.out.println("\tMain algorithm: " + (tElapsed - tInit) + " ms");
		System.out.println("\tElapsed: " + (tElapsed - t0) + " ms");
		System.out.println();
	}
	
	public static void runBST(String fileName, String query)
	{
		long t0 = System.currentTimeMillis();
		BST<String, Pronunciation> storage = new BST<>();
		String queryPho = initStorage(fileName, storage, query);
		long tInit = System.currentTimeMillis();
						
		System.out.println("Homophones of "+query+" (\""+queryPho+" \") for BST:");
		
		for (Pronunciation match : storage.findAll(queryPho))
			if (!match.getWord().equals(query))
				System.out.println("\t" + match.getWord());
		
		long tElapsed = System.currentTimeMillis();
		
		System.out.println();
		System.out.println("Time Summary for BST: ");
		System.out.println("\tInitialization: " + (tInit - t0) + " ms");
		System.out.println("\tMain algorithm: " + (tElapsed - tInit) + " ms");
		System.out.println("\tElapsed: " + (tElapsed - t0) + " ms");
		System.out.println();
	}
	
	/* Fills up the respect Dictionary implementation AND returns the phonemes of the query
	 * to require just one iteration for both. */
	private static <DictType extends Dictionary<String, Pronunciation>> String initStorage
		(String fileName, DictType storage, String query)
	{
		String queryPho = null;
		File f = new File(fileName);
		
		try {
			Scanner fileSc = new Scanner(f);
			int count = 1;
			
			while (fileSc.hasNextLine())
			{
				String line = fileSc.nextLine();
				if (line.substring(0, 3).equals(";;;")) continue;		// skip comments
				
				Pronunciation p = new Pronunciation(line);
				// uncomment to track the progress (esp for the slow OALDictionary initialization)
//				System.out.println("Inserting ["+(count++)+"]" + p.getWord() + "...");
				storage.insert(p.getPhonemes(), p);
				
				if (queryPho == null && p.getWord().equals(query))
					queryPho = p.getPhonemes();
			}
			
			fileSc.close();
			
			return queryPho;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "";
	}

}
