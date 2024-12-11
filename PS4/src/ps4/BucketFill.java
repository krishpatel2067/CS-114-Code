/*
 * BucketFill (aka "The Jug Problem")
 * Updated 10/1/2024
 * Due 10/2/2024
 * Krish A. Patel
 * CS 114 H03
 * */

package ps4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class BucketFill {
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter c1 c2 c3 d: ");
		int c1 = sc.nextInt();
		int c2 = sc.nextInt();
		int c3 = sc.nextInt();
		int d = sc.nextInt();
		sc.close();
		
		// BucketState: a collection of buckets & a state manager
		BucketState initState = new BucketState(
				new Bucket(c1, c1), 
				new Bucket(c2), 
				new Bucket(c3), 
				d);
		run(initState);
		System.out.println("Finished");
	}

	public static void run(BucketState initState)
	{
		ArrayList<BucketState> visited = new ArrayList<>();
		Deque<BucketState> candidates = new ArrayDeque<>();
		Deque<BucketState> sequence = new ArrayDeque<>();
		
		// kick-start with the default bucket
		candidates.add(initState);
		
		while (!candidates.isEmpty())
		{
			BucketState candidate = candidates.pollFirst();
						
			// have we reached our goal?
			if (candidate.isIdeal())
			{
				// latest states go in first, oldest last
				for (BucketState c = candidate; c != null; c = c.getPrevious())
					sequence.offerLast(c);
							
				// oldest states come out firt, latest states last
				for (BucketState b = sequence.pollLast(); b != null; b = sequence.pollLast())
					System.out.println(b);
				
				break;
			}
			
			// generate candidates by trying every possible pour
			// pourer bucket pours into a target bucket
			// only constrains: targets can't be full, pourers can't be empty
			ArrayList<Bucket> possibleTargets = candidate.getAllNotFull();
			ArrayList<Bucket> possiblePourers = candidate.getAllNotEmpty();
			
			// iterate through all the possibilitiess
			for (Bucket pourer : possiblePourers)
				for (Bucket target : possibleTargets)
				{
					// a bucket can't pour into itself
					if (pourer.equals(target))
						continue;
					
					boolean isLegal = candidate.isLegal(pourer, target);
					
					if (isLegal) {
						BucketState nextState = candidate.pourInto(pourer, target);
						// avoid repeat states
						if (!visited.contains(nextState))
						{
							candidates.add(nextState);
							visited.add(nextState);
						}
					} else
						continue;
					
					
				}
		}
	}
}
