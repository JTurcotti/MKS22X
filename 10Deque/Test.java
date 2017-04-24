public class Test {
    MyDeque md = new MyDeque();
    for(int i = 0; i<20; md.addFirst(i++)) {}
    for (int i=0; i<100; md.addLast(md.removeFirst())) {i++;}
    while (true)
	try {
	    md.removeFirst();
	    md.removeLast();
	} catch (NoSuchElementException e) {
	    break;
	}
	
