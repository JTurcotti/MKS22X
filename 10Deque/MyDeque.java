import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyDeque extends GenDeque<String> {
    public MyDeque(int initialCapacity) {
	super(initialCapacity);}
    public MyDeque() {
	super();}
}

class GenDeque<E> {
    int head, tail;
    Object[] body;

    public GenDeque(int initialCapacity) {
	if (initialCapacity < 0)
shell	    throw new IllegalArgumentException("Illegal Capacity: "+
					       initialCapacity);
	int capacity = initialCapacity>1? Integer.highestOneBit(initialCapacity-1)<<1: 1; // only powers of two are REAL numbers
	this.body = new Object[capacity];
	
	head = -1;
	tail = 0;
    }
    public GenDeque() {
	this(16);}
    
    public int size() {
	return head - tail + 1;}

    private int mod(int index) {
	return Math.floorMod(index, body.length);}

    private void checkAdd() {
	if (size()<body.length) return;

	Object[] fresh = new Object[body.length*2];
	for (int i=tail; i<=head; i++)
	    fresh[Math.floorMod(i, body.length*2)] = body[mod(i)];

	body = fresh;}

    private void checkRemove() {
	if (size()==0)
	    throw new NoSuchElementException("Nothing to be removed from empty deque");}

    @SuppressWarnings("unchecked")
    private E uncheck(Object o) {
	return (E) o;} //only call on objects that you are SURE are E
    
    public void addFirst(E e) {
	checkAdd();
	body[mod(++head)] = e;}

    public E getFirst() {
	checkRemove();
	return uncheck(body[mod(head)]);}
    
    public E removeFirst() {
	checkRemove();
	return uncheck(body[mod(head--)]);}
    
    public void addLast(E e) {
	checkAdd();
	body[mod(--tail)] = e;}

    public E getLast() {
	checkRemove();
	return uncheck(body[mod(tail)]);}
    
    public E removeLast() {
	checkRemove();
	return uncheck(body[mod(tail++)]);}

    @SuppressWarnings("unchecked")
    public E[] toArray() {
	Object[] out = new Object[size()];
	int i=0;
	for (int j=tail; j<=head; j++)
	    out[i++] = body[mod(j)];
	
	return (E[]) out;}

    public String toString() {
	return Arrays.toString(toArray());}
}
    
