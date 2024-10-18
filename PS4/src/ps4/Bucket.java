package ps4;

public class Bucket {
	private int level;
	private int capacity;
	
	Bucket(int capacity)
	{
		this.level = 0;
		this.capacity = capacity;
	}
	
	Bucket(int capacity, int level)
	{
		this.level = level;
		this.capacity = capacity;
	}
	
	public boolean equals(Bucket other)
	{
		return other.capacity == this.capacity;
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
