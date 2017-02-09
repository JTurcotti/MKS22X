import java.util.BitSet;

public class QueenMaskSet {
    //parallel masks
    BitSet[] queenMasks;

    public final int size;

    public BitSet get(int i) {
	return queenMasks[i];
    }
    public BitSet get(int r, int c) {
	if (r>=size||c>=size) throw new IndexOutOfBoundsException(Integer.toString(r>=size? r: c));
	return get(size*c+r);
    }

    public QueenMaskSet(int size) {
	this.size = size;
	queenMasks = new BitSet[size*size];
	for (int i=0; i<(size*size); i++) {
	    queenMasks[i] = new BitSet(size*size);
	    diagonalMask(i);
	    parallelMask(i);
	}
    }
    
    void diagonalMask(int pos) {
	int forward = pos%size - pos/size;
	int backward = pos%size + pos/size;

	BitSet forwardMask = new BitSet(size*size);
	BitSet backwardMask = new BitSet(size*size);
	
	int start;
	
	start = forward>=0? forward: -1*size*forward;
	for (int i=0; i<(size-Math.abs(forward)); i++)
	    forwardMask.set((size+1)*i+start);
	start = backward<(size)? backward: size*(backward-(size-2))-1;
	for (int i=0; i<(size-Math.abs(backward-(size-1))); i++)
	    backwardMask.set(((size-1)*i)+start);
	queenMasks[pos].or(forwardMask);
	queenMasks[pos].or(backwardMask);
    }

    void parallelMask(int pos) {
	BitSet horizontalMask = new BitSet(size*size);
	BitSet verticalMask = new BitSet(size*size);

	int start;

	start = size*(pos/size);
	for (int i=0; i<size; i++)
	    horizontalMask.set(start+i);
	start = pos%size;
	for (int i=0; i<size; i++)
	    verticalMask.set(start+i*size);
	queenMasks[pos].or(horizontalMask);
	queenMasks[pos].or(verticalMask);
    }

    //following methods for testing only
    public static String toString(BitSet bs, int size) {
	String out = "";
	for (int i=0; i<(size*size); i++) {
	    if (bs.get(i)) out+="X";
	    else out+="_";
	    if (i%size==size-1) out+="\n";
	}
	return out;
    }
    
    public static void main(String[] args) {
	QueenMaskSet q = new QueenMaskSet(20);
	System.out.println(toString(q.get(19, 19), 20));
    }
}
	       
    
    
		 

    
		

    
