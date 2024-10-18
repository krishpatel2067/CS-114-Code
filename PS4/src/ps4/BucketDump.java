package ps4;

public class BucketDump {
	private int level;
	private int capacity;
	
	BucketDump(int capacity)
	{
		this.capacity = capacity;
	}
	
	BucketDump(int capacity, int level)
	{
		this.level = level;
		this.capacity = capacity;
	}
	
//	public void pourInto(Bucket other)
//	{		
//		// either transfer until main bucket empty or target bucket full, whichever is first
//		int transferAmount = Math.min(other.untilFull(), this.level);
//		this.level -= transferAmount;
//		other.level += transferAmount;
//	}
	
	public boolean equals(BucketDump other)
	{
		return other.level == this.level && other.capacity == this.capacity;
	}
	
	public boolean isFull()
	{
		return this.untilFull() <= 0;
	}
	
	public boolean isEmpty()
	{
		return this.level <= 0;
	}
	
	public int untilFull()
	{
		return this.capacity - this.level;
	}
	
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String toString()
	{
		return "Bucket[" + this.level + "/" + this.capacity + "]";
	}
}
