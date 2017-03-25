import java.util.ArrayList;
import java.util.List;
import java.util.BitSet;

public class QueenBoard {
    private boolean populated;
    final List<Long> solutions;
    final QueenMaskSet masks;
    public final int size;
    
    public QueenBoard(int size) {
	if (size<=0) throw new IllegalArgumentException("Board size must be >=1");
	this.size = size;
	solutions = new ArrayList<>();
	masks = new QueenMaskSet(size);
    }

    public void countSolutions() {
	recursiveCount(0, new BitSet(), 0L);
	populated = true;
    }

    public void solve() {
	recursiveFind(0, new BitSet(), 0L);
    }
    
    public String toString(int num) {
	if (num>getSolutionCount()) throw new IllegalStateException("too many solutions requested");
	String out = "";
	for (int i=0; i<num; i++)
	    out += (longToString(solutions.get(i), size) + "\n");
	return out;
    }

    public String toString() {
	return toString(1);
    }
    
    public int getSolutionCount() {
	if (!populated && solutions.size()==0) return -1;
	return solutions.size();
    }
    
    //only valid through 16x16    
    long queenValue(long queens, int col, int row) {
	long root = 1;
	for(int i=0; i<col; i++) root*=size;
	return queens+root*row;
    }

    boolean recursiveFind(int col, BitSet threats, long queens) {
	if (col==size) {
	    solutions.add(queens);
	    return true;
	}
	for (int row=0; row<size; row++) {
	    if (!threats.get(col+row*size)) {
		BitSet newThreats = ((BitSet) threats.clone());
		newThreats.or(masks.get(col, row));
		if (recursiveFind(col+1, newThreats, queenValue(queens, col, row)))
		    return true;
	    }
	}
	return false;
    }
   
    int recursiveCount(int col, BitSet threats, long queens) {
	if (col==size) {
	    solutions.add(queens);
	    return 1;
	}
	int count = 0;
	for (int row=0; row<size; row++)
	    if (!threats.get(col+row*size)) {
		BitSet newThreats = ((BitSet) threats.clone());
		newThreats.or(masks.get(col, row));
		count+=recursiveCount(col+1, newThreats, queenValue(queens, col, row));
	    }
	return count;
    }

    public static String longToString(long queens, int size) {
	String out = "";
	for (int row=0; row<size; row++) {
	    int col = Math.toIntExact(queens % size);
	    for (int i=0; i<size; i++) out += i==col? "X": "_";
	    if (row+1!=size) out+="\n";
	    queens /= size;
	}
	return out;
    }
    
    static long time() {return System.currentTimeMillis();}
    
    public static void main(String[] args) {
	int size = Integer.parseInt(args[0]);

	long time = time();
	QueenBoard qb = new QueenBoard(size);
	System.out.println(time() - time + "ms to initialize");
	
	time = time();
	qb.solve();
	System.out.println(qb);
	System.out.println(time() - time + "ms to find one solution");
	
	time = time();
	qb.countSolutions();
	System.out.println(time() - time + "ms to find all " + qb.getSolutionCount());
    }
}
