import java.util.*;

public class PriorityQueueFrontier extends Frontier {
    private PriorityQueue<Node> data;

    public PriorityQueueFrontier(Comparator<Node> c, Maze m) {
	data = new PriorityQueue<>(16, c);
	maze = m;
    }
    
    public void addH(Node n) {
	data.add(n);
    }

    public Node nextH() {
	return data.remove();
    }

    public boolean hasNext() {
	return data.size() > 0;
    }
    
    public static final Comparator<Node> ASTAR = new Comparator<Node>() {
	    public int compare(Node one, Node two) {
		return one.length - two.length + one.dist - two.dist;
	    }
	};
    public static final Comparator<Node> GREEDY = new Comparator<Node>() {
	    public int compare(Node one, Node two) {
		return one.dist - two.dist;
	    }
	};	
}
