public class Node {
    public Node prev;
    public int length;
    public Location pos;
    public int dist;

    public Node(Location start, Location end) {
	this.length = 0;
	this.pos = start;
	this.dist = start.distanceTo(end);
    }
    
    public Node(Node prev, int length, Location pos, int dist) {
	this.prev = prev;
	this.length = length;
	this.pos = pos;
	this.dist = dist;
    }

    
}
