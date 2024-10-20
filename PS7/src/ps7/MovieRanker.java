package ps7;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.Stack;
import java.util.ArrayList;
//import java.util.PriorityQueue;
//import java.util.Collections;

public class MovieRanker {

	/* TIME COMPLEXITY ANALYSIS:
	 * Initial Data Loading:
	 * 		* Simply uses an ArrayList linearly
	 * 		* Suppose there are n movie ratings in the input file
	 * 		* Total time O(n)
	 * Create the MaxHeap:
	 * 		* Initialized with the array-based constructor in MaxHeap
	 * 		* Internally uses buildheap()
	 * 		* Has a for loops with n/2 - 1 iterations (where n is the number of elements)
	 * 		* By itself, it runs in time O(n)
	 * 		* But each iteration calls siftdown():
	 * 			* While loop is the only structure leading to a non-constant time complexity
	 * 			* Worst case: minimum element at the root
	 * 			* Would have to travel all the way down the entire height of the tree
	 * 			* Height h = lg(n)
	 * 		* Total time O(n * lg(n))
	 * Processing the Query:
	 * 		* Suppose m = # of records, k = # min votes required
	 * 		* For loop runs m times
	 * 		* By itself, time O(m)
	 * 			* Suppose there are a maximum of x tied max ratings
	 * 			* While loop runs x times
	 * 			* By itself, time O(x)
	 * 				* removeMax() method runs in worst case time O(lg(n)) (after it swaps the last element
	 * 				  with the root, it might have to travel the entire height down)
	 * 				* addInAlphabeticalOrder() runs in worst case time O(x) (might have to 
	 * 				  add all the way to the end)
	 * 			* filterViaVotes() takes time O(x) (has to iterate through all the maxes)
	 * 			* ArrayList.addAll() runs in time O(x) (to add all the maxes)
	 * 			* ArrayList.clear() runs in time O(x) (to remove each max)
	 * 		* The while loop to remove extras takes worst case O(n), though very unlikely 
	 * 		  that there are as many maxes as the # of ratings (safe to ignore)
	 * 		* Subtotal time O(m * x^5 * lg(n))
	 * 		* But as stated above x is very unlikely to remotely approach n
	 * 		* Total time O(m * lg(n))
	 * */
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		File file = new File("ratings.tsv");

		ArrayList<MovieRating> rl = new ArrayList<MovieRating>();

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tkns = line.split("\\t");
				int votes = Integer.parseInt(tkns[0]);
				int rtg = Integer.parseInt(tkns[1]);

				MovieRating nr = new MovieRating(tkns[2], rtg, votes);

				rl.add(nr);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("(Time to read data: " + (System.currentTimeMillis()-startTime) + " ms)");

		int minVotes = 1;
		int numRecords = 1;
		assert minVotes==0: "pos minvotes";
		Scanner input = new Scanner(System.in);
		
		while (true) {
			System.out.println();
			System.out.print("Enter minimum vote threshold and number of records: ");
			minVotes = input.nextInt();
			numRecords = input.nextInt();
			if (minVotes * numRecords == 0)
				break;

			startTime = System.currentTimeMillis();

			// fill in code to process query
			
			// ---------------------------------------------------------------------------------
			/* Written by Krish A. Patel (10/19/2024) */
			
			MovieRating[] mrArr = new MovieRating[rl.size()];
			MaxHeap<MovieRating> heap = new MaxHeap<>(rl.toArray(mrArr), rl.size());
			ArrayList<MovieRating> maxes = new ArrayList<>();			// to handle ties
			ArrayList<MovieRating> leaderboard = new ArrayList<>();		// final list to display
			
			for (int r = 0; r < numRecords;)
			{
				// there can be ties in ratings
				MovieRating currMax = heap.peek();
				
				while (maxes.isEmpty() || maxes.getLast().getRating() == currMax.getRating())
				{
					currMax = heap.removeMax();
					// automatically sort them alphabetically since ratings are the same (max)
					addInAlphabeticalOrder(maxes, currMax);
					currMax = heap.peek();
				}
				
				filterViaVotes(maxes, minVotes);
				
				// if we found 2 maxes with at least the required min votes, we already got 2 records
				r += maxes.size();
				leaderboard.addAll(maxes);
				maxes.clear();
			}
			
			// remove extras in case there were more tied maxes than numRecords
			while (leaderboard.size() > numRecords)
				leaderboard.removeLast();

			System.out.println();
			for (MovieRating mr : leaderboard)
				System.out.println(mr);
			
			// ---------------------------------------------------------------------------------
			
			System.out.println("\n(Time to search: " + (System.currentTimeMillis() - startTime) + " ms)");
		}
		input.close();
		System.out.println("\nEnd of program: Thank of you for using MovieRanker");
	}
	
	/* Written by Krish A. Patel (10/19/2024) */
	
	public static void filterViaVotes(ArrayList<MovieRating> al, int minVotes)
	{
		for (int i = al.size() - 1; i >= 0; i--)
			if (al.get(i).getVotes() < minVotes)
				al.remove(i);
	}
	
	/* Written by Krish A. Patel (10/19/2024) */

	public static void addInAlphabeticalOrder(ArrayList<MovieRating> al, MovieRating mr)
	{
		if (al.isEmpty())
		{
			al.add(mr);
			return;
		}
				
		for (int i = 0; i < al.size(); i++)
		{
			// if the title to be added is alphabetically larger
			if (mr.getTitle().compareToIgnoreCase(al.get(i).getTitle()) <= 0)
			{
				al.add(i, mr);
				return;
			}
		}
		
		al.add(mr);
	}
}
