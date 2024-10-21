package ps7;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.Stack;
import java.util.ArrayList;
import java.util.PriorityQueue;
//import java.util.Collections;

public class MovieRanker {

	/* TIME COMPLEXITY ANALYSIS:
	 * Initial Data Loading:
	 * 		* Simply uses an ArrayList linearly
	 * 		* Suppose there are n movie ratings in the input file
	 * 		* Total time O(n)
	 * Processing the Query (along with creating the PriorityQueue):
	 * 		* Iterating through all n MovieRatings in the ArrayList takes time O(n)
	 * 			* Adding records into the PriorityQueue takes worst time lg(m) as each time
	 * 			  it might have to traverse the entire height of the heap.
	 * 			* Each time an item is polled, it takes worst time lg(m) if the item has to be 
	 * 			  sifted down the entire height.
	 * 		  	* Each time a new item is inserted, it may have to travel the entire height of
	 * 		  	  the heap up, so the worst time is lg(m).
	 * 		* Iterating over the heap to display all the items takes O(m) time.
	 * 		* The total time is:
	 * 				O( n(lg(m) + lg(m) + lg(m)) + O(m)
	 * 				O( 3nlg(m) ) + O(m)
	 * 				O(nlg(m)) + O(m)
	 * 				O(nlg(m)) (since a linear grows slower than a linear times a logarithm,
	 * 						   AND m is much smaller than n)
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
			PriorityQueue<MovieRating> minHeap = new PriorityQueue<>();
			
			for (MovieRating mr : rl)
			{
				// only consider MovieRatings with enough votes
				if (mr.getVotes() >= minVotes)
				{
					// if the minHeap isn't the desirable size yet
					if (minHeap.size() < numRecords)
					{
						minHeap.add(mr);
					}
					// if it has numRecords elements
					else
					{
						MovieRating min = minHeap.peek();
						
						/* keep replacing the min with MovieRatings "greater" than it 
						 * to get a pool of maxes */
						if (min.compareTo(mr) < 0)
						{
							minHeap.poll();
							minHeap.add(mr);
						}
					}
				}
			}
			
			String str = "";
			
			while (minHeap.size() > 0)
			{
				str = minHeap.poll() + "\n" + str;
			}
			
			System.out.println(str);
			
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
