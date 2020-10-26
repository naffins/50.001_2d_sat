package immutable;

import java.util.Iterator;

public class EmptyImList<E> implements ImList<E>{

	private int length = 0;
	
	public EmptyImList(){}
	
	public ImList<E> add(E e){
		return new NonEmptyImList<E>(e,this);
	}
	
	public ImList<E> remove(E e){
		return this;
	}
	
	public E first() {
		return null;
	}
	
	public ImList<E> rest() {
		return null;
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return true;
	}
	
	public String print() {
		return null;
	}
	
	public String toString() {
		return "ImList[]";
	}
	
	public Iterator<E> iterator(){
		return new ImListIterator<E>(this);
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		return o instanceof EmptyImList;
	}
	
}
