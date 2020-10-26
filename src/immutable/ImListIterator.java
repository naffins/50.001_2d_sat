package immutable;

import java.util.Iterator;

/**
 * Iterator for ImList
 * @author I copied this from whoever wrote the original one in the given starter code, so...
 *
 * @param <E>: Type of object to iterate through
 */
public class ImListIterator<E> implements Iterator<E>{
	
	/**
	 * ImList of remaining elements
	 */
	ImList<E> rest;
	
	/**
	 * Constructor from ImList
	 * @param sauce: the original ImList
	 */
	public ImListIterator(ImList<E> sauce){
		rest = sauce;
	}
	
	/**
	 * Check if any other elements still exist
	 */
	public boolean hasNext() {
		return !rest.isEmpty();
	}
	
	/**
	 * Pour out the next element if it exists
	 */
	public E next() {
		if (!hasNext()) return null;
		E next = rest.first();
		rest = rest.rest();
		return next;
	}
}
