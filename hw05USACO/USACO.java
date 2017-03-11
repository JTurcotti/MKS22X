import java.util.*;
import java.io.*;

public class USACO {
	    
    public int silver(String filename) {
	Field grass = new Field(filename);
	return grass.solve();
    }

    public int bronze(String filename) {
	Lake pond = new Lake(filename);
	return pond.volume();
    }
}

class Lake {
    int[][] data;
    int rows, cols, elev, digs;
    
    void dig(int r, int c, int diff) {
	int maxHeight = 0;
	for (int i=0; i<3; i++)
	    for (int j=0; j<3; j++) {
		if (data[r+i][c+j]>maxHeight)
		    maxHeight = data[r+i][c+j];
	    }
	int finalHeight = maxHeight - diff;
	for (int i=0; i<3; i++)
	    for (int j=0; j<3; j++) {
		if (data[r+i][c+j]>finalHeight)
		    data[r+i][c+j]=finalHeight;
	    }
    }

    int volume() {
	int volume = 0;
	for (int row=0; row<rows; row++)
	    for (int col=0; col<cols; col++)
		if (data[row][col]<elev)
		    volume += elev - data[row][col];
	return volume * 72 * 72;
    }

    public Lake(String filename) {
	Scanner in = null;
	try {
	    in = new Scanner(new File(filename));
	} catch (Exception e) {
	    return;
	}
	String[] line = in.nextLine().split(" ");
	rows = Integer.parseInt(line[0]);
	cols = Integer.parseInt(line[1]);
	elev = Integer.parseInt(line[2]);
	digs = Integer.parseInt(line[3]);

	data = new int[rows][cols];

	for (int row=0; row<rows; row++) {
	    line = in.nextLine().split(" ");
	    for (int col=0; col<cols; col++)
		data[row][col] = Integer.parseInt(line[col]);
	}

	for (int dig=0; dig<digs; dig++) {
	    line = in.nextLine().split(" ");
	    dig(Integer.parseInt(line[0])-1,
		Integer.parseInt(line[1])-1,
		Integer.parseInt(line[2]));
	}
    }
}



class Field {
    int[][] data;
    int rows, cols, moves, endRow, endCol, start;

    void inc(int row, int col, int val) {
	if (row<0 || row>=rows || col<0 || col>=cols || data[row][col]==-1)
	    return;
	else 
	    data[row][col] += val;
    }

    public Field(String filename) {
	try {
	    Scanner in = new Scanner(new File(filename));
	} catch (Exception e) {
	    return;
	}
	String[] init = in.nextLine().split(" ");
	rows = Integer.parseInt(init[0]);
	cols = Integer.parseInt(init[1]);
	moves = Integer.parseInt(init[2]);

	data = new int[rows][cols];
	for (int row=0; row<rows; row++) {
	    String line = in.nextLine();
	    for (int col=0; col<cols; col++)
		if (line.charAt(col)=='*')
		    data[row][col] = -1;
	}
	String[] fin = in.nextLine().split(" ");
	int startRow = Integer.parseInt(fin[0])-1;
	int startCol = Integer.parseInt(fin[1])-1;
	data[startRow][startCol] = 1;
	start = (startRow+startCol)%2;
	endRow = Integer.parseInt(fin[2])-1;
	endCol = Integer.parseInt(fin[3])-1;

    }

    void propogate() {
	for (int row=0; row<rows; row++)
	    for (int col=(row%2==start)?0:1; col<cols; col+=2) {
		int dat = data[row][col];
		if (dat<=0) continue;
		inc(row+1, col, dat);
		inc(row-1, col, dat);
		inc(row, col+1, dat);
		inc(row, col-1, dat);
		data[row][col] = 0;
	    }
	start = 1 -start;
    }

    int solve() {
	while (moves-->0)
	    propogate();
	return data[endRow][endCol];
    }

    //remaining methods for debugging only

    int loudSolve() {
	int movesMade = 0;
	while (moves-->0) {
	    propogate();
	    System.out.println("Moves made so far: " + ++movesMade);
	    System.out.println(moves);
	    System.out.println(this + "\n\n");
	}
	return data[endRow][endCol];
    }

    public String toString() {
	String out = "";
	for (int row=0; row<rows; row++) {
	    for (int col=0; col<cols; col++) {
		String me = Integer.toString(data[row][col]);
		if (me.length()==1) me+="  ";
		if (me.length()==2) me+=" ";
		out+=" " + me;
	    }
	    out+="\n";
	}
	return out;
    }
}
