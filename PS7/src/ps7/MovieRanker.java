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
		while (true) {
			System.out.println();
			System.out.println("Enter minimum vote threshold and number of records:");
			minVotes = input.nextInt();
			numRecords = input.nextInt();
			if (minVotes * numRecords == 0)
				break;




			startTime = System.currentTimeMillis();

// fill in code to process query

			System.out.println("Time: " + (System.currentTimeMillis() - startTime) + " ms");
		}
		input.close();
	}
}
