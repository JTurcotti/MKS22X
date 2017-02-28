import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class KnightBoard {
    private final int rows;
    private final int cols;
    private final int base;
    public int[][] data;
    private static int[][] moves = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

    
    public KnightBoard(int size) {
	this(size, size);
    }

    public KnightBoard(int rows, int cols) {
	data = new int[rows][cols];
	this.rows = rows;
	this.cols = cols;
	base = Math.max(rows, cols);
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
	    if (X>=0 && X< rows && Y>=0 && Y<cols && data[X][Y]==0)
		count++;
	}
	return count;
    }
	    
    
    int[] listMoves(int x, int y) {
	int[] outMoves = new int[8];
	for (int i=0; i<8; i++) {
	    int X = x+moves[i][0];
	    int Y = y+moves[i][1];
	    if (X>=0 && X<rows && Y>=0 && Y<cols && data[X][Y]==0)
		outMoves[i] = base*base*countFrom(X, Y)+base*Y+X;
	}
	return(sort(outMoves));
    }
	

    private boolean place(int x, int y, int num) {
	if (num>rows*cols) return true;

	int[] moves = listMoves(x, y);
	for (int move: moves) if (move!=0) {
		int X = move%base;
		int Y = (move%(base*base)/base);
		data[X][Y] = num;
		if (place(X, Y, num+1)) return true;
		data[X][Y] = 0;
	    }
	return false;
    }

    public boolean solve() {
	int x = (rows-1)/2;
	int y = (cols-1)/2;
	while (x>0) {
	    while (y<=(cols-1)/2) {
		data[x][y] = 1;
		if (place(x, y, 2)) return valid();
		data[x][y] = 0;
		y++;
	    }
	    y = --x;
	}
	return false;
    }
    public boolean solveFast() {return solve();}

    private boolean validKnightMove(int x1, int y1, int x2, int y2) {
	int dxy = (x2-x1) * (y2-y1);
	return dxy==2 || dxy==-2;
    }

	

    public String toString() {
	String out = "";
	int len = Integer.toString(rows*cols).length()+1;
	for (int i=0; i<rows*cols; i++) {
	    String dat = Integer.toString(data[i/cols][i%cols]);
	    while (dat.length()<len) dat = " " + dat;
	    out+=dat;
	    if ((i+1)%cols==0) out+= "\n";
	}
	return out;
    }

    public boolean valid() {
	for (int[] row: data)
	    for (int point: row)
		if (point==0)
		    return false;
	return true;
    }

    public static long[] test(int max) {
	List<String> failures = new ArrayList<>();
	List<String> needy = new ArrayList<>();
	
	long[] tests = new long[max];
	long time = time();
	for (int size=1; size<=max; size++) {
	    //if (size==49 || size==53 || size ==59 || size==63 || size == 76 || size==84 || size==87 || size == 89) continue;
	    time = time();
	    boolean solved = false;
	    try {
		KnightBoard kb = new KnightBoard(size);
		solved = kb.solve();
	    } catch (StackOverflowError soe) {
		System.out.println("Stack memory limit reached at " + size);
		break;
	    }
	    long elapsed = time()-time;
	    if (solved) {
		System.out.println("solved " + size + " in " + elapsed + "ms");
		if (elapsed > 20) needy.add("size: " + size + "; time: " + elapsed + "ms");
	    }
	    else {
		System.out.println("failed " + size + " in " + elapsed + "ms");
		failures.add("size: " + size + "; time: " + elapsed + "ms");
	    }
	    tests[size-1] = elapsed;
	}
	System.out.println("\n\nCases that took an unusally large amount of time:");
	for (String needer: needy) System.out.println(needer);
	System.out.println("\nCases that failed altogether to produce a solution:");
	for (String failure: failures) System.out.println(failure);
	return tests;
    }
    
    public static void main(String[] args) {
	test(Integer.parseInt(args[0]));
    }
    
    static long time() {return System.currentTimeMillis();}
}

