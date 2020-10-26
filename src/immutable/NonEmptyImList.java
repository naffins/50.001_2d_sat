package immutable;

import java.util.Iterator;

public class NonEmptyImList<E> implements ImList<E> {
	
	private int length = 0;
	private E value = null;
	private ImList<E> rest = null;
	
	public NonEmptyImList(E val, ImList<E> rt) {
		value = val;
		rest = rt;
		length = rt.length() + 1;
	}
	
	public ImList<E> add(E e){
		return new NonEmptyImList<E>(e,this);
	}
	
	public ImList<E> remove(E e){
		
		// If value is equivalent to e
		if (e.equals(value)) return rest;
		
		// If rest is empty
		if (rest.isEmpty()) return this;
		
		ImList<E> newRest = rest.remove(e);
		if (newRest==rest) return this;
		
		return new NonEmptyImList<E>(value,newRest);
	}
	
	public E first() {
		return value;
	}
	
	public ImList<E> rest(){
		return rest;
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public String print() {
		return value.toString();
	}
	
	public String toString() {
		String rt = "ImList[";
		ImList<E> cur = this;
		while(!cur.isEmpty()) {
			rt += cur.print();
			cur = cur.rest();
			if (!cur.isEmpty()) rt += ",\n       ";
		}
		rt += "]";
		return rt;
	}
	
	public Iterator<E> iterator() {
		return new ImListIterator<E>(this);
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof NonEmptyImList)) return false;
		ImList other = (ImList) o;
		if (!other.first().equals(value)) return false;
		return rest.equals(other.rest());
	}
	
}
