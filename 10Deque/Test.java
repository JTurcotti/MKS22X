import java.util.*;

public class Test {
    public static void main(String[] args) {
	MyDeque md = new MyDeque();
	for(int i = 0; i<20; md.addFirst("apple")) {i++;}
	System.out.println(" size: " + md.size());
	for (int i=0; i<100; md.addLast(md.removeFirst())) {i++;}
	System.out.println(" size: " + md.size());
	while (true)
	    try {
		md.removeFirst();
		md.removeLast();
	    } catch (NoSuchElementException e) {
		break;
	    }
	md.addFirst("banana");
	for (int i=0; i<16; md.addFirst(md.getFirst() + "na")) {i++;}
	System.out.println(" size: " + md.size());
	for (int i=0; i<8; md.addLast(md.getLast() + "na")) {i++;}
	System.out.println(" size: " + md.size());
    }
}
