import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class KnightBoard {
    private final int size;
    public int[][] data;
    private static int[][] moves = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

    
    public KnightBoard(int size) {
	data = new int[size][size];
	this.size = size;
    }

    public static int[] sort(int[] data) {
	for (int i=0; i<data.length; i++) {
	    int j=i;
	    while (j>0 && data[j]<data[j-1]) {
		int temp = data[j];
		data[j] = data[j-1];
		data[j-1] = temp;
		j--;
	    }
	}
	return data;
    }
    
    
    int countFrom(int x, int y) {
	int count = 0;
	for (int i=0; i<8; i++) {
	    int X = x+moves[i][0];
	    int Y = y+moves[i][1];
	    if (X>=0 && X<size && Y>=0 && Y<size && data[X][Y]==0)
		count++;
	}
	return count;
    }
	    
    
    int[] listMoves(int x, int y) {
	int[] outMoves = new int[8];
	for (int i=0; i<8; i++) {
	    int X = x+moves[i][0];
	    int Y = y+moves[i][1];
	    if (X>=0 && X<size && Y>=0 && Y<size && data[X][Y]==0)
		outMoves[i] = size*size*countFrom(X, Y)+size*Y+X;
	}
	return(sort(outMoves));
    }
	

    private boolean place(int x, int y, int num) {
	if (num>size*size) return true;

	int[] moves = listMoves(x, y);
	for (int move: moves) if (move!=0) {
		int X = move%size;
		int Y = (move%(size*size)/size);
		data[X][Y] = num;
		if (place(X, Y, num+1)) return true;
		data[X][y] = 0;
	    }
	return false;
    }

    public boolean solve() {
	int x = (size-1)/2;
	int y = (size-1)/2;
	while (x>0) {
	    while (y<=(size-1)/2) {
		data[x][y] = 1;
		if (place(x, y, 2)) return true;
		data[x][y] = 0;
		y++;
	    }
	    y = --x;
	}
	return false;
    }
    /* Doesnt work yet
    public boolean solveClosed() {
	int x1 = (size-1)/2;
	int y1 = (size-1)/2;
	int x = x1;
	int y = y1;
	while (x>0) {
	    while (y<=(size-1)/2) {
		data[x][y] = 1;
		if (place(x, y, 2) && validKnightMove(x1, y1, x, y)) return true;
		data[x][y] = 0;
		y++;
	    }
	    y = --x;
	}
	return false;
    }

    */
    
    private boolean validKnightMove(int x1, int y1, int x2, int y2) {
	int dx = x2-x1;
	int dy = y2-y1;
	return Math.abs(dx*dy)==2;
    }

    public String toString() {
	String out = "";
	int len = Integer.toString(size*size).length()+1;
	for (int i=0; i<size*size; i++) {
	    String dat = Integer.toString(data[i/size][i%size]);
	    while (dat.length()<len) dat = " " + dat;
	    out+=dat;
	    if ((i+1)%size==0) out+= "\n";
	}
	return out;
    }

    public static long[] test(int max) {
	long[] tests = new long[max];
	long time = time();
	for (int size=1; size<=max; size++) {
	    time = time();
	    KnightBoard kb = new KnightBoard(size);
	    boolean solved = kb.solve();
	    long elapsed = time()-time;
	    if (solved)
		System.out.println("solved " + size + " in " + elapsed + "ms");
	    else
		System.out.println("failed " + size + " in " + elapsed + "ms");
	    tests[size-1] = elapsed;
	}
	return tests;
    }

    public static void main(String[] args) {
	test(Integer.parseInt(args[0]));
    }

    static long time() {return System.currentTimeMillis();}
}


    
		
		

    
