package immutable.sat;

import java.util.Iterator;
import java.util.LinkedList;

import immutable.ImList;
import immutable.ImListIterator;

/**
 * Immutable sorted list of Literals
 * @author naffins
 *
 */
public abstract class Clause implements ImList<Literal>{
	
	/**
	 * Add literal to clause in a way that preserves order. Note that this ignores any duplicate literals, and immediately returns a null
	 * pointer if the negation of an existing literal is added
	 */
	public abstract Clause add(Literal l);
	
	/**
	 * Return a null pointer if literal exists in clause, or only the clause without negative of the literal if it exists.
	 * Note that this is sign-dependent, though it DOES something regardless of whether the sign matches.
	 */
	public abstract Clause remove(Literal l);
	
	/**
	 * Check whether sign-dependent literal exists in clause.
	 */
	public abstract boolean contains(Literal l);
	
	public abstract Literal first();
	
	public abstract Clause rest();
	
	/**
	 * Pour out your heart's, sorry, this list's literals content into a LinkedList.
	 * Useful for updating literals availability table.
	 * @return a LinkedList
	 */
	public abstract LinkedList<Literal> contents();
	
	public abstract int length();
	
	public abstract boolean isEmpty();
	
	/**
	 * Return string representation for literal content
	 */
	public abstract String print();
	
	public abstract String toString();
	
	/**
	 * Return an iterator for the literals.
	 */
	public Iterator<Literal> iterator() {
		return new ImListIterator<Literal>(this);
	}
	
	public abstract boolean equals(Object o);
	
}
