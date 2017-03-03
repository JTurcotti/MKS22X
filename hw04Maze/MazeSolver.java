import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MazeSolver {
    public static void main(String[] args) throws Exception {
	Grid grid = new Grid("maze1.txt");
	grid.solve();
	System.out.println(grid);
    }
}
	

class Grid {
    Cell[][] data;
    public int width;
    public int height;

    Cell end;
    Cell start;
    List<Cell> path;
    List<Cell> pendingQueue;
    List<Cell> currentQueue;

    public Grid(String filename) throws Exception {
	Scanner in = new Scanner(new File(filename));
	height = 0;
	width = 0;
	while(in.hasNextLine()) {
	    String line = in.nextLine();
	    if (width != 0) {
		if (width != line.length())
		    throw new IllegalArgumentException("All rows must be same length");
	    } else width = line.length();
	    height++;
	}

	path = new ArrayList<>((width+height)/2);
	pendingQueue = new LinkedList<>();
	currentQueue = new LinkedList<>();

	in = new Scanner(new File(filename));
	data = new Cell[width][height];
	boolean containsE = false;
	boolean containsS = false;
	for (int row=0; row<height; row++) {
	    String line = in.nextLine();
	    for (int column=0; column<width; column++) {
		char apple = line.charAt(column);
		if (apple=='#') data[column][row] = (new Cell(column, row, this)).setBlock();
		else if (apple=='S') {
		    start = new Cell(column, row, this);
		    data[column][row] = start;
		    pendingQueue.add(start);
		}
		else if (apple=='E') {
		    end = new Cell(column, row, this);
		    data[column][row] = end;
		}
		else data[column][row] = new Cell(column, row, this);
		boolean currentE = (apple == 'E');
		boolean currentS = (apple == 'S');
		if (currentE && containsE || currentS && containsS)
		    throw new IllegalArgumentException("No more than one start or finish");
		containsE = containsE || currentE;
		containsS = containsS || currentS;
	    }
	}
	if (!containsE || !containsS)
	    throw new IllegalArgumentException("No less than one start or finish");
	System.out.println("Initiliazed: " + this);
    }

    
    Cell getFreeCell(int x, int y) {
	if (x<0 || x>=width || y<0 || y>=height) 
	    return null;
	Cell cell = data[x][y];
	if (cell.former != null) return null;
	else return cell;
    }

    void queue(Cell cell) {
	pendingQueue.add(cell);
    }

    void solve() {
	while (end.former == null) {
	    currentQueue = pendingQueue;
	    pendingQueue = new LinkedList<>();
	    for (Cell queued: currentQueue)
		queued.propogate();
	}
	Cell current = end;
	while (current != Cell.GENESIS) {
	    System.out.println(current);
	    path.add(current);
	    current = current.former;
	}
    }

    public String toString() {
        String out = "";
        for (int row=0; row<height; row++) {
            for (int column=0; column<width; column++) {
		Cell current = data[column][row];
		char repr;
		if (current == start)
		    repr = 'S';
		else if (current == end)
		    repr = 'E';
		else if (path.contains(current))
		    repr = '@';
		else if (current.former==Cell.GENESIS)
		    repr = '#';
		else
		    repr = ' ';
		out+=repr;
	    }
            out+='\n';
        }
        return out;
    }

    public String toStringDebug() {
        String out = "";
        for (int row=0; row<height; row++) {
            for (int column=0; column<width; column++) {
		Cell current = data[column][row];
		char repr;
		if (current == start)
		    repr = 'S';
		else if (current == end)
		    repr = 'E';
		else if (path.contains(current))
		    repr = '@';
		else if (current.former==Cell.GENESIS)
		    repr = '#';
		else if (current.former!=null) {

		}		    
		else
		    repr = ' ';
		out+=repr;
	    }
            out+='\n';
        }
        return out;
    }
}

class Cell {
    int x;
    int y;
    Cell former;
    Grid grid;
    static int[][] deltas
	= new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static final Cell GENESIS
	= new Cell(-1, -1, null);
    public Cell(int x, int y, Grid grid) {
	this.x = x;
	this.y = y;
	this.grid = grid;
	former = null;
    }
    
    Cell setBlock() {
	former = GENESIS;
	return this;
    }
    
    void adopt(Cell cell) {
	cell.former = this;
    }

    void propogate() {
	for (int[] delta: deltas) {
	    Cell child = grid.getFreeCell(x+delta[0], y+delta[1]);
	    if (child != null) {
		adopt(child);
		grid.queue(child);
	    }
	}
    }

    public String toString() {
	String out = "Cell at: ";
	out+= x + ", ";
	out+= y + " pointing to ";
	if (former == null) {
	    out+= "null";
	} else if (former == GENESIS) {
	    out+= "genesis";
	} else
	    out += former.x + ", " + former.y;
	return out;
    }
}
