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

		//System.out.println("Reading in took: "+(System.currentTimeMillis()-startTime));

		int minVotes = 1;
		int numRecords = 1;
		assert minVotes==0: "pos minvotes";
		Scanner input = new Scanner(System.in);
		
		// test addInAlphabeticalOrder()
//		if (true)
//		{
//			ArrayList<MovieRating> maxes = new ArrayList<>();
//			addInAlphabeticalOrder(maxes, rl.removeFirst());
//			System.out.println(maxes);
//			addInAlphabeticalOrder(maxes, rl.removeFirst());
//			System.out.println(maxes);
//			addInAlphabeticalOrder(maxes, rl.removeFirst());
//			System.out.println(maxes);
//			addInAlphabeticalOrder(maxes, rl.removeFirst());
//			System.out.println(maxes);
//			addInAlphabeticalOrder(maxes, rl.removeFirst());
//			System.out.println(maxes);
//			return;
//		}
		
		while (true) {
			System.out.println();
			System.out.println("Enter minimum vote threshold and number of records:");
			minVotes = input.nextInt();
			numRecords = input.nextInt();
			if (minVotes * numRecords == 0)
				break;

			startTime = System.currentTimeMillis();

			// fill in code to process query
			MaxHeap<MovieRating> heap = new MaxHeap<>(rl.toArray(new MovieRating[rl.size()]), rl.size());
			
			for (int r = 0; r < numRecords;)
			{
				// there can be ties in ratings
				ArrayList<MovieRating> maxes = new ArrayList<>();
				MovieRating currMax = heap.peek();
				
				while (maxes.isEmpty() || maxes.getLast().getRating() == currMax.getRating())
				{
					currMax = heap.removeMax();
					// automatically sort them alphabetically since ratings are the same (max)
					addInAlphabeticalOrder(maxes, currMax);
					currMax = heap.peek();
				}
				
				// if we found 2 maxes, we already got 2 records
				r += maxes.size();
			}

			System.out.println("Time: " + (System.currentTimeMillis() - startTime) + " ms");
		}
		input.close();
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
