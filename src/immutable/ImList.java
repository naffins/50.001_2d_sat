package immutable;

import java.util.Iterator;

/**
 * General immutable list, not necessarily ordered
 */

public interface ImList<E> extends Iterable<E>{
	
	/**
	 * Add element e to the list.
	 * @param e: element to be added
	 * @return new immutable list
	 */
	public ImList<E> add(E e);
	
	/**
	 * Remove element e from list, if it exists.
	 * @param e: element to be removed
	 * @return new immutable list
	 */
	public ImList<E> remove(E e);
	
	/** 
	 * Return first element of immutable list
	 * @return first element
	 */
	public E first();
	
	/**
	 * Return all other elements of immutable list
	 * @return remaining immutable list
	 */
	public ImList<E> rest();
	
	/**
	 * Return length of immutable list
	 * @return length of immutable list
	 */
	public int length();
	
	/**
	 * Check whether immutable list is empty
	 * @return the boolean for that
	 */
	public boolean isEmpty();
	
	/**
	 * Return raw string representation of the first element
	 * @return the string representation
	 */
	public String print();
	
	/**
	 * Return string representation of entire list
	 * @return the string representation
	 */
	public String toString();
	
	/**
	 * Return an iterator to iterate through the elements
	 * @return iterator for elements
	 */
	public Iterator<E> iterator();
	
	/**
	 * Check whether this list is equal in VALUE to another list
	 * @param o: the object to be compared to
	 * @return whether both lists are the same
	 */
	public boolean equals(Object o);
	
}
