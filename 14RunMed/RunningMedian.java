import java.util.NoSuchElementException;

public class RunningMedian {
    MyHeap left;
    MyHeap right;
    double median;
    
    public RunningMedian() {
	left = new MyHeap(true);
	right = new MyHeap(false);
	median = 0;
    }

    public void add(int n) {
	if (n > median)
	    right.add(n);
	else
	    left.add(n);
	if (right.size() > 1 + left.size())
	    left.add(right.remove());
	else if (left.size() > 1 + right.size())
	    right.add(left.remove());
	if (right.size()==left.size())
	    median = 0.5 * (right.peek() + left.peek());
	else if (right.size() > left.size())
	    median = right.peek();
	else
	    median = left.peek();
    }

    public double getMedian() {
	if (right.size()==0 && left.size()==0)
	    throw new NoSuchElementException();
	return median;
    }

    public static void main(String[] args) {
	RunningMedian pizza = new RunningMedian();
	for (String arg: args)
	    pizza.add(Integer.parseInt(arg));
	System.out.println(pizza.getMedian());
    }
}

    
