import java.util.*;

public class QueueFrontier extends Frontier {
    private Queue<Node> data;

    public QueueFrontier(Maze m) {
	data = new ArrayDeque<>();
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
}
    
