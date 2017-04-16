import java.util.*;

public class Test {
    public static void main(String[] args) {
	MyLinkedList mll = new MyLinkedList();
	for (int i=0; i<10; i++)
	    mll.add(i);
	System.out.println(mll);
	mll.add(5, 10);
	mll.add(mll.size(), 10);
	System.out.println(mll);
	System.out.println(mll.indexOf(10));
	mll.remove(mll.indexOf(10));
	System.out.println(mll);
	System.out.println(mll.indexOf(10));
	mll.set(0, 10);
	System.out.println(mll);
	System.out.println(mll.indexOf(10));
	System.out.println(mll.indexOf(11));
    }
}
