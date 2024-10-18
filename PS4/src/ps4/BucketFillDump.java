package ps4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import ps4.Bucket;
import ps4.BucketState;

public class BucketFillDump {
	
	HashMap<int[], Boolean> memory = new HashMap<>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter c1 c2 c3 d: ");
		int c1 = sc.nextInt();
		int c2 = sc.nextInt();
		int c3 = sc.nextInt();
		int d = sc.nextInt();
		
		sc.close();
		// jagged array of buckets
//		int[][] buckets = new int[][] {
//			new int[c1],
//			new int[c2],
//			new int[c3]
//		};
		Bucket b1 = new Bucket(c1, c1);
		Bucket b2 = new Bucket(c2);
		Bucket b3 = new Bucket(c3);
		
		BucketState buckets = new BucketState(b1, b2, b3, d);
//		Deque<BucketState> visited = new ArrayDeque<>();	
//		BucketState buckets1 = new BucketState(b1, b2, b3, d);
//		
//		visited.add(buckets);
//		System.out.println(buckets.equals(buckets1));
//		System.out.println(visited.contains(buckets));
		
//		int[] bucket1 = new int[c1];
//		int[] bucket2 = new int[c2];
//		int[] bucket3 = new int[c3];
		
//		int[] bucket1 = buckets[0];
		
		// fill up bucket1 to the max
//		for (int i = 0; i < c1; i++)
//			bucket1[i] = 1;
//		
//		sub(bucket1, 5);
		
//		for (int i : bucket1)
//			System.out.print(i + " ");
//		
//		System.out.println(level(bucket1));
//		System.out.println(untilFull(bucket1));
		
//		add(bucket1, 3);
//		
//		for (int i : bucket1)
//			System.out.print(i + " ");
//		
//		System.out.println(level(bucket1));
//		System.out.println(untilFull(bucket1));
		
		run(buckets);
	}

	// main algorithm
	// decide which buckets can be main and / or target
	// iterate through all the possibilities
	// keep track of bucket levels to avoid repeats
	// but how to get the minimum?
	public static void run(BucketState buckets)
	{
		ArrayList<BucketState> visited = new ArrayList<>();
		Deque<BucketState> candidates = new ArrayDeque<>();
		Deque<BucketState> sequence = new ArrayDeque<>();
		
		candidates.add(buckets);
		
		while (!candidates.isEmpty())
		{
			BucketState candidate = candidates.pollFirst();
			
			System.out.println(candidates.size());
			
			if (candidate.isIdeal())
			{
				for (BucketState c = candidate; c != null; c = c.getPrevious())
				{
					sequence.offerLast(c);
				}
				
				System.out.println("Found solution!");
				
				for (BucketState b = sequence.pollLast(); b != null; b = sequence.pollLast())
				{
					System.out.println(b);
				}
				break;
			}
			
			// generate candidates
			ArrayList<Bucket> possibleTargets = candidate.getAllNotFull();
			ArrayList<Bucket> possiblePourers = candidate.getAllNotEmpty();
			
			for (Bucket pourer : possiblePourers)
			{
				for (Bucket target : possibleTargets)
				{
					if (pourer.equals(target))
						continue;
					
					BucketState nextState = candidate.pourInto(pourer, target);
					
					if (!visited.contains(nextState))
					{
						System.out.println("Visited " + visited);
//						System.out.println("Pouring " + pourer + " into " + target);
						candidates.add(nextState);
						visited.add(nextState);
					}
					else
					{
						System.out.println("Duplicate");
					}
				}
			}
			
		}
	}
	
//	public static boolean contains(ArrayDeque<BucketState> deq, BucketState el)
//	{
//		for (BucketState b : deq)
//	}
//	
	// main algorithm
//	public static PriorityQueue<int[]> bucketFill(int[][] buckets, int d)
//	{
//		// c1 = 20, c2 = 5, c3 = 3, d = 4
//		// (20, 0, 0) --> either (15, 5, 0) or (17, 0, 3)
//		
//		// base case
//		int[] levels = new int[] { level(buckets[0]), level(buckets[1]), level(buckets[2]) };
//		
//		if (levels[0] == d || levels[1] == d || levels[2] == d)
//			System.out.println(levels[0] + " " + levels[1] + " " + levels[2]);
//			return new PriorityQueue<>();
//		
//		// decide which buckets can be main and / or target
//		// iterate through all the possibilities
//		// keep track of bucket levels to avoid repeats
//		// but how to get the minimum?
//
////		ArrayDeque<>
////			
////		while ()
////		{
////			
////		}
//		
//		System.out.println("There is no solution to this problem.");
//	}
	
	// fill targetBucket with mainBucket
	public static void fillBucket(int[] mainBucket, int[] targetBucket)
	{
		int untilTargetFull = untilFull(targetBucket);
		
		// either transfer until main bucket empty or target bucket full, whichever is first
		int transfer = Math.min(untilTargetFull, level(mainBucket));
		
		sub(mainBucket, transfer);
		add(targetBucket, transfer);
	}
	
	public static void add(int[] bucket, int amount)
	{
		if (amount > untilFull(bucket))
		{
			System.out.println("Cannot exceed capacity");
			return;
		}
		
		int lastIndex = level(bucket) - 1;		// last index of a 1 (filled unit)
		
		for (int i = lastIndex + 1; i < (lastIndex + 1) + amount; i++)
		{
			bucket[i] = 1;
		}
	}
	
	public static void sub(int[] bucket, int amount)
	{
		if (amount > level(bucket))
		{
			System.out.println("Amount cannot exceed level");
			return;
		}
		
		int lastIndex = level(bucket) - 1;		// last index of a 1 (filled unit)
		
		for (int i = lastIndex; i >= (lastIndex + 1) - amount; i--)
		{
			bucket[i] = 0;
		}
	}
	
	public static int level(int[] bucket)
	{
		int level = 0;
		
		for (int i : bucket)
			if (i == 1)
				level ++;
		
		return level;
	}
	
	// how much more until the bucket is full
	public static int untilFull(int[] bucket)
	{
		return bucket.length - level(bucket);
	}
}
