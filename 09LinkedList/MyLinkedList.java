import java.util.Collection;

public class MyLinkedList<E> { 
    Node head;
    int size;

    public int size() {return size;}

    public MyLinkedList() {
	head = new Node(null, null, null);
	clear();
    }

    public MyLinkedList(Collection<? extends E> values) {
	this();
	for (E value: values)
	    add(value);
    }

    public String toString() {
	Node current = head.next;
        if (current == head)
            return "[]";

        StringBuilder out = new StringBuilder();
        out.append('[');

        for (;; current = current.next) {
            out.append(current.value);
            if (current.next == head)
                return out.append(']').toString();
            out.append(", ");
        }
    }
    
    private Node getNode(int index) {
	if (index<0 || index>=size)
	    throw new IndexOutOfBoundsException(index + " in size " + size);
	else {
	    Node current = head;
	    if (index<size/2)
		while (index-->=0)
		    current = current.next;
	    else
		while (index++<size)
		    current = current.prev;
	    return current;
	}
    }

    public boolean add(E e) {
	head.addBefore(new Node(e));
	size++;
	return true;
    }

    public void add(int index, E element) {
	Node n = index==size? head: getNode(index);
	n.addBefore(new Node(element));
	size++;
    }

    public boolean addAll(Collection<? extends E> C) {
	for (E e: C)
	    add(e);
    }

    public boolean addAll(int index, Collection<? extends E> C) {
	int i=index;
	for (E e: C)
	    add(i++, e);
    }

    public void addFirst(E e) {
	head.addAfter(e);
    }

    public void addLast(E e) {
	head.addBefore(e);
    }

    public void clear() {
	head.next = head;
	head.prev = head;
	size = 0;
    }

    public Object clone() {
	MyLinkedList n = new MyLinkedList();
	for (Node current = head.next; current!=head; current = current.next)
	    n.add(current);
	return n;
    }
			  
    public E remove(int index) {
	Node n = getNode(index);
	n.next.prev = n.prev;
	n.prev.next = n.next;
	size--;
	return n.value;
    }
    
    public E get(int index) {
	Node n = getNode(index);
	return n.value;
    }

    public E set(int index, E value) {
	Node n = getNode(index);
	E old = n.value;
	n.value = value;
	return old;
    }

    public int indexOf(E value) {
	Node current = head.next;
	for (int i=0; current!=head; i++) {
	    E e = current.value;
	    if (value==null? e==null: e.equals(value))
		return i;
	    current = current.next;
	}
	return -1;
    }

    public Object[] toArray() {
	Object[] arr = new Object[size];
	Node current = head.next;
	for (int i=0; current!=head; current = current.next)
	    arr[i++] = current.value;
	return arr;
    }
    
    class Node {
	E value;
	Node prev;
	Node next;
	
	private Node(E value, Node prev, Node next) {
	    this.value = value;
	    this.prev = prev;
	    this.next = next;
	}

	private Node(E value) {
	    this(value, null, null);
	}

	void addAfter(Node fresh) {
	    next.prev = fresh;
	    fresh.next = next;
	    fresh.prev = this;
	    next = fresh;
	}

	void addBefore(Node fresh) {
	    prev.next = fresh;
	    fresh.prev = prev;
	    fresh.next = this;
	    prev = fresh;
	}
    }
}
	    
