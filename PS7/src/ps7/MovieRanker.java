package ps7;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

public class MovieRanker {

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
				
			System.out.println("\n(Time to search: " + (System.currentTimeMillis() - startTime) + " ms)");
		}
		input.close();
		System.out.println("\nEnd of program: Thank of you for using MovieRanker");
	}
	
	public static void filterViaVotes(ArrayList<MovieRating> al, int minVotes)
	{
		for (int i = al.size() - 1; i >= 0; i--)
			if (al.get(i).getVotes() < minVotes)
				al.remove(i);
	}
	
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
