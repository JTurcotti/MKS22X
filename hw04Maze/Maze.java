import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Maze {
    Cell[][] data;
    public int width;
    public int height;

    boolean animate;
    public void setAnimate(boolean b) {animate = b;}

    Cell end;
    Cell start;
    List<Cell> path;
    List<Cell> pendingQueue;
    List<Cell> currentQueue;

    public Maze(String filename) {
	
	animate = false;
	Scanner in;
	try {
	    in = new Scanner(new File(filename));
	} catch (Exception e) {
	    throw new IllegalArgumentException("Please provide valid/existant file path");
	}
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


	try {
	    in = new Scanner(new File(filename));
	} catch (Exception e) {
	    throw new IllegalArgumentException("Please provide valid/existant file path");
	}
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

    public boolean solve() {
	while (end.former == null) {
	    

	    
	    currentQueue = pendingQueue;
	    pendingQueue = new LinkedList<>();
	    for (Cell queued: currentQueue) {
		if (animate) {
		    System.out.println("\033[2J\033[1;1H"+toStringDebug());
		    wait(20/currentQueue.size());
		}
		queued.propogate();
	    }
	    if (pendingQueue.size()==0) return false;
	}
	Cell current = end;
	while (current != start) {
	    path.add(current);
	    current = current.former;
	}
	return true;
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
		Cell former = current.former;
		char repr;
		if (current == start)
		    repr = 'S';
		else if (current == end)
		    repr = 'E';
		else if (path.contains(current))
		    repr = '@';
		else if (former==Cell.GENESIS)
		    repr = '#';
		else if (former!=null) {
		    switch (Integer.toString(current.x-former.x)+Integer.toString(current.y-former.y)) {
		    case "10":
			repr = '<';
			break;
		    case "-10":
			repr = '>';
			break;
		    case "01":
			repr = '^';
			break;
		    case "0-1":
			repr = 'v';
			break;
		    default:
			throw new IllegalStateException("Non-adjacent pointers illegal");
		    }
		}		    
		else
		    repr = ' ';
		out+=repr;
	    }
            out+='\n';
        }
        return out;
    }
    private void wait(int millis){ 
	try {
	    Thread.sleep(millis);
	}
	catch (InterruptedException e) {
	}
    }
    public void clearTerminal(){
        //erase terminal, go to top left of screen.
        System.out.println("\033[2J\033[1;1H");
    }

}

class Cell {
    int x;
    int y;
    Cell former;
    Maze grid;
    static int[][] deltas
	= new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static final Cell GENESIS
	= new Cell(-1, -1, null);
    public Cell(int x, int y, Maze grid) {
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
