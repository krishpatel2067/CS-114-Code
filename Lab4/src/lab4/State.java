package lab4;

/*
 * Define states to be used in solving the missionaries and
 * cannibals problem.
 */


class State {
	static int boatCap, nM, nC;
	
    int m; // missionaries on left bank
    int c; // cannibals on left bank
    int b; // 0 for boat on left bank, 1 for boat on the right
    State pred; // predecessor state

    State() {
        m = State.nM;
        c = State.nC;
        b = 0;
    }

    State(State s) {
        m = s.m;
        c = s.c;
        b = s.b;
    }

    State(int M, int C, int B) {
        m = M;
        c = C;
        b = B;
    }
    
    public static void setPeopleCount(int m, int c)
    {
    	State.nM = m;
    	State.nC = c;
    }
    
    public static void setBoatCapacity(int cap)
    {
    	State.boatCap = cap;
    }

    boolean legal()
    /* is this a legal state? */
    {
        if (m < 0 || m > State.nM || c < 0 || c > State.nC)
            return false;
        if (m > 0 && c > m) // can't have more cannibals on left...
            return false;
        if (State.nM - m > 0 && State.nC - c > State.nM - m) // ...or on right bank
            return false;
        return true;
    }

    boolean equiv(State s)
    /* Equivalent states? */
    {
        if(s.m == m && s.c == c && s.b == b)
            return true;
        else
            return false;
    }


    /**
     *
     * move M missionaries and C cannibals and return the resulting state
     */
    State move(int M, int C) {	
        State newState = new State(this);
        if (b == 0) {
            newState.m = m - M;
            newState.c = c - C;
            newState.b = 1;
        } else {
            newState.m = m + M;
            newState.c = c + C;
            newState.b = 0;
        }
        newState.pred = this;
        return newState;
    }

    public String toString()
    {
        return m + " " + c + " " + (b == 0 ? "Left" : "Right");
    }

}
