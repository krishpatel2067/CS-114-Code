package ps4;

import java.util.ArrayList;

public class BucketStateDump {
	private Bucket[] buckets;
	private Bucket b1, b2, b3;
	private int idealLevel;
	private BucketStateDump previous;
	
//	BucketState(int cap1, int cap2, int cap3, int idealLvl)
//	{
//		b1 = new Bucket(cap1);
//		b2 = new Bucket(cap2);
//		b3 = new Bucket(cap3);
//		
//		buckets = new Bucket[] {b1, b2, b3};
//		
//		this.idealLevel = idealLvl;
//	}
	
	BucketStateDump(Bucket b1, Bucket b2, Bucket b3, int idealLvl)
	{
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.idealLevel = idealLvl;
		this.buckets = new Bucket[] { b1, b2, b3 };
	}
	
	BucketStateDump(Bucket[] caps, int idealLvl)
	{
		this(caps[0], caps[1], caps[2], idealLvl);
	}
	
	public BucketStateDump getPrevious() {
		return previous;
	}
	
//	public BucketState nextBucketState(BucketState next)
//	{
//		next.previous = this;
//		return next;
//	}
	
	public BucketStateDump pourInto(Bucket pourer, Bucket target)
	{
		BucketStateDump next;
		int transferAmount = Math.min(target.untilFull(), pourer.getLevel());
		Bucket[] result = new Bucket[3];
		boolean matchedPourer = false;
		boolean matchedTarget = false;
		
		for (int i = 0; i < buckets.length; i++)
		{
			Bucket b = this.buckets[i];
			
			if (b.equals(pourer))
			{
				result[i] = new Bucket(pourer.getCapacity(), pourer.getLevel() - transferAmount);
				matchedPourer = true;
			} 
			else if (b.equals(target))
			{
				result[i] = new Bucket(target.getCapacity(), target.getLevel() + transferAmount);
				matchedTarget = true;
			}
			else
			{
				result[i] = b;
			}
		}
		
		if (!matchedPourer)
			System.err.println("Pourer not found in BucketState");
		
		if (!matchedTarget)
			System.err.println("Target not found in BucketState");
		
		next = new BucketStateDump(result, this.idealLevel);
		next.previous = this;
		return next;
	}
	
	public boolean equals(BucketStateDump other)
	{
		return other.b1.getLevel() == this.b1.getLevel() &&
				other.b2.getLevel() == this.b2.getLevel() &&
				other.b3.getLevel() == this.b3.getLevel() &&
				other.idealLevel == this.idealLevel;
	}
	
	public ArrayList<Bucket> getAllNotFull()
	{
		ArrayList<Bucket> notFull = new ArrayList<>();
		
		for (Bucket b : this.buckets)
		{
			if (!b.isFull())
			{
				notFull.add(b);
			}
		}
		
		return notFull;
	}
	
	public ArrayList<Bucket> getAllNotEmpty()
	{
		ArrayList<Bucket> notEmtpy = new ArrayList<>();
		
		for (Bucket b : this.buckets)
		{
			if (!b.isEmpty())
			{
				notEmtpy.add(b);
			}
		}
		
		return notEmtpy;
	}
	
	public boolean isIdeal()
	{	
		if (this.b1.getLevel() == this.idealLevel ||
			   this.b2.getLevel() == this.idealLevel ||
			   this.b3.getLevel() == this.idealLevel)
		{
			System.out.println("IDEAL! " + this);
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return "BucketState[" + 
			   this.b1.toString() + ", " +
			   this.b2.toString() + ", " +
			   this.b3.toString() + ", " +
			   "Ideal Level: " + this.idealLevel + "]";
	}
}