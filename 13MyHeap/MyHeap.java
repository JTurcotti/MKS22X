import java.util.*;

public class MyHeap extends PriorityQueue<String> {
    public MyHeap() {
	super();}

    public MyHeap(boolean max) {
	this(new Comparator<String>() {
		public int compare(String one, String two) {
		    return max? one.compareTo(two): two.compareTo(one);}
	    });
    }

    public MyHeap(Comparator<String> c) {
	super(16, c);
    }
}
