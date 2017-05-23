public abstract class Frontier {
    void add(Node n) {
	addH(n);
	maze.maze[n.pos.x][n.pos.y] = '?';
    };
    abstract void addH(Node n);
    Node next() {
	Node n = nextH();
	maze.maze[n.pos.x][n.pos.y] = ' ';
	return n;
    };
    abstract Node nextH();
    abstract boolean hasNext();

    Maze maze;
    
}
