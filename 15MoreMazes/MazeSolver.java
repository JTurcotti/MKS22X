import java.util.*;

class ClSet extends HashSet<Location> {
    Maze maze;
    public ClSet(Maze maze) {
	super();
	this.maze = maze;
    }

    @Override
    public boolean add(Location l) {
	return add(l, true);
    }
    
    public boolean add(Location l, boolean b) {
	super.add(l);
	if (b) maze.maze[l.x][l.y] = '.';
	return true;
    }
    
}
public class MazeSolver {
    private Maze maze;
    private ClSet closed;
    private Frontier front;
    private boolean animate;

    public MazeSolver(String filename, boolean animate) {
	maze = new Maze(filename);
	this.animate = animate;
    }

    public MazeSolver(String filename) {
	this(filename, false);
    }

    public void solve(int style) {
	initSet();
	initFrontier(style);
	Node n = search();
	while(n!=null) {
	    maze.maze[n.pos.x][n.pos.y] = '@';
	    n = n.prev;
	}
	maze.maze[maze.start.x][maze.start.y] = 'S';
	maze.maze[maze.end.x][maze.end.y] = 'E';
	for (int r = 0; r<maze.maxRows; r++)
	    for (int c = 0; c<maze.maxCols; c++)
		if (maze.maze[r][c] == '.' || maze.maze[r][c] == '?')
		    maze.maze[r][c] = ' ';
    }

    public void solve(){
	solve(1);
    }
	
    private void initSet() {
	closed = new ClSet(maze);
	for (int x = 0; x<maze.maxRows; x++)
	    for (int y = 0; y<maze.maxCols; y++)
		if (maze.maze[x][y]=='#')
		    closed.add(new Location(x, y), false);
	closed.add(maze.start);
    }

    private void initFrontier(int style) {
	switch (style) {
	case 0:
	    front = new StackFrontier(maze);
	    break;
	case 1:
	    front = new QueueFrontier(maze);
	    break;
	case 2:
	    front = new PriorityQueueFrontier(PriorityQueueFrontier.GREEDY, maze);
	    break;
	case 3:
	    front = new PriorityQueueFrontier(PriorityQueueFrontier.ASTAR, maze);
	    break;
	}
    }
	
    private boolean valid(Location l) {
	return l.within(maze) && !closed.contains(l);
    }
    
    private Node search() {
	front.add(new Node(maze.start, maze.end));

	while (front.hasNext()) {
	    Node n = front.next();
	    for (int i = 0; i<4; i++) {
		Location l = new Location(n.pos.x + (i>1? 0: 1 - 2*i),
					  n.pos.y + (i>1? 1-2*(i%2): 0));
		if (valid(l)) {
		    closed.add(l);
		    Node m = new Node(n, n.length+1, l, l.distanceTo(maze.end));
		    if (l.equals(maze.end))
			return m;
		    front.add(m);
		}
		if (animate)
		    pr(maze.toString(0));
	    }
	}
	return null;
    }

    @Override
    public String toString() {
	return maze.toString();
    }

    public static void main(String[] args) {
	MazeSolver ms = new MazeSolver(args[1], false);
	ms.solve(Integer.parseInt(args[0]));
	System.out.println(ms.maze.toString(0));
    }

    private static void pr(Object s) {
	System.out.println(s);
    }
}	
