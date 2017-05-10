import java.util.*;

public class MyHeap extends PriorityQueue<Integer> {
    public MyHeap() {
	super();}

    public MyHeap(final boolean max) {
	this(new Comparator<Integer>() {
		public int compare(Integer one, Integer two) {
		    return max? two.compareTo(one): one.compareTo(two);}
	    });
    }

    public MyHeap(Comparator<Integer> c) {
	super(16, c);
    }
}
