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
	    throw new IllegalArgumentException("Illegal Capacity: "+
					       initialCapacity);
	this.body = new Object[initialCapacity];
	
	head = -1;
	tail = 0;
    }
    public GenDeque() {
	this(16);}
    
    public int size() {
	return head - tail + 1;}

    private int mod(int index) {
	return mod(index, body.length);}
    private int mod(int a, int b) {
	return a<0? (a%b)+b: a%b;}

    private void checkAdd() {
	if (size()<body.length) return;

	Object[] fresh = new Object[body.length*2];
	for (int i=tail; i<=head; i++)
	    fresh[mod(i, body.length*2)] = body[mod(i)];

	body = fresh;
    }

    private void checkRemove() {
	if (size()==0)
	    throw new NoSuchElementException("Nothing to be removed from empty deque");}
					   
    public void addFirst(E e) {
	checkAdd();
	body[mod(++head)] = e;}
    
    @SuppressWarnings("unchecked")
    public E getFirst() {
	checkRemove();
	return (E) body[mod(head)];}
    
    @SuppressWarnings("unchecked")
    public E removeFirst() {
	checkRemove();
	return (E) body[mod(head--)];}
    
    public void addLast(E e) {
	checkAdd();
	body[mod(--tail)] = e;}

    @SuppressWarnings("unchecked")
    public E getLast() {
	checkRemove();
	return (E) body[mod(tail)];}
    
    @SuppressWarnings("unchecked")
    public E removeLast() {
	checkRemove();
	return (E) body[mod(tail++)];}

    @SuppressWarnings("unchecked")
    public E[] toArray() {
	Object[] out = new Object[size()];
	int i=0;
	for (int j=tail; j<=head; j++)
	    out[i++] = body[mod(j)];
	
	return (E[]) out;
    }
}
