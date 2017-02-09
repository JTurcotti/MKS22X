import java.util.LinkedList;
import java.util.List;
import java.util.BitSet;

public class QueenBoard {
    final List<Long> solutions;
    final QueenMaskSet masks;
    public final int size;
    
    public QueenBoard(int size) {
	this.size = size;
	solutions = new LinkedList<>();
	masks = new QueenMaskSet(size);
	recursiveCount(0, new BitSet(), 0L);
    }

    public int getCount() {
	return solutions.size();
    }
    
    long queenValue(long queens, int col, int row) {
	long root = 1;
	for(int i=0; i<col; i++) root*=size;
	return queens+root*row;
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

    public static void main(String[] args) {
	QueenBoard qb = new QueenBoard(Integer.parseInt(args[0]));
	System.out.println(qb.getCount());
    }
}
