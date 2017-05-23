import java.util.*;

public class StackFrontier extends Frontier{
    private Stack<Node> data;

    public StackFrontier(Maze m) {
	data = new Stack<>();
	maze = m;
    }
	
    public void addH(Node n) {
	data.push(n);
    }

    public Node nextH() {
	return data.pop();
    }

    public boolean hasNext() {
	return data.size() > 0;
    }
}
