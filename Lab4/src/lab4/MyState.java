package lab4;

public class MyState {
	int nM, nC;			// total missionaries & cannibals
	int nlM, nlC;		// on the left bank
	int capB;			// capacity of boat
	int onB;			// on boat
	
	MyState(int nlM, int nlC, int onB, int nM, int nC)
	{
		this.nlM = nlM;
		this.nlC = nlC;
		this.nM = nM;
		this.nC = nC;
		this.onB = onB;
	}
	
	public MyState nextState(int moveM, int moveC)
	{	
		return new MyState(this.nlM - moveM, this.nlC - moveC, moveM + moveC, this.nM, this.nC);
	}
	
	public int getMissionariesOnLeft()
	{
		return this.nlM;
	}
	
	public int getCannibalsOnLeft()
	{
		return this.nlC;
	}
	
	public int getMissionariesOnRight()
	{
		return this.nM - this.nlM;
	}
	
	public int getCannibalsOnRight()
	{
		return this.nC - this.nlC;
	}
	
	public boolean isLegal()
	{
		// n can't be negative on either side 
		if (this.nlM < 0 || this.nlC < 0 || this.nM - this.nlM < 0 || this.nC - this.nlC < 0)
		{
			return false;
		}
		
		// n cannibals can't exceed n missionaries on left
		if (this.nlC > this.nlM)
		{
			return false;
		}
		
		// n cannibals can't exceed n missionaries on right
		if ((this.nC - nlC) > (this.nM - this.nlM))
		{
			return false;
		}
		
		return true;
	}
}
