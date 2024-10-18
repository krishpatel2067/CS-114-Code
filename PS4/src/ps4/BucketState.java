package ps4;

import java.util.ArrayList;

public class BucketState {
	private Bucket[] buckets;
	private Bucket b1, b2, b3;
	private int idealLevel;
	private BucketState previous;
	private boolean bucket3Filled = false;
	
	BucketState(Bucket b1, Bucket b2, Bucket b3, int idealLvl)
	{
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.idealLevel = idealLvl;
		this.buckets = new Bucket[] { b1, b2, b3 };
	}
	
	BucketState(Bucket[] caps, int idealLvl)
	{
		this(caps[0], caps[1], caps[2], idealLvl);
	}
	
	public BucketState getPrevious() {
		return previous;
	}
	
	public BucketState pourInto(Bucket pourer, Bucket target)
	{
		BucketState next;
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
			
			if (target.equals(b))
			{
				this.bucket3Filled = true;
			}
		}
		
		if (!matchedPourer)
			System.err.println("Pourer not found in BucketState");
		
		if (!matchedTarget)
			System.err.println("Target not found in BucketState");
		
		next = new BucketState(result, this.idealLevel);
		next.previous = this;
		return next;
	}
	
	public ArrayList<Bucket> getAllNotFull()
	{
		ArrayList<Bucket> notFull = new ArrayList<>();
		
		for (Bucket b : this.buckets)
			if (!b.isFull())
				notFull.add(b);
		
		return notFull;
	}
	
	public ArrayList<Bucket> getAllNotEmpty()
	{
		ArrayList<Bucket> notEmtpy = new ArrayList<>();
		
		for (Bucket b : this.buckets)
			if (!b.isEmpty())
				notEmtpy.add(b);
		
		return notEmtpy;
	}
	
	public boolean isIdeal()
	{	
		if (this.b1.getLevel() == this.idealLevel ||
			this.b2.getLevel() == this.idealLevel ||
			this.b3.getLevel() == this.idealLevel)
				return true;
		return false;
	}
	
	public boolean isLegal(Bucket pourer, Bucket target)
	{
		if (bucket3Filled)
		{
			if (pourer.equals(b3))
			{
				int transferAmount = Math.min(target.untilFull(), pourer.getLevel());
				
				if (transferAmount == pourer.getCapacity())
				{
					return false;					
				}
			}
		}
		
		return true;
	}
	
	public String toString()
	{
		return "BucketState[" + 
			   this.b1 + ", " +
			   this.b2 + ", " +
			   this.b3 + ", " +
			   "Ideal Level: " + this.idealLevel + "]";
	}
}