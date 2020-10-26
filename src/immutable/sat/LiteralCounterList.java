package immutable.sat;

import java.util.Iterator;
import java.util.LinkedList;

import immutable.ImList;
import immutable.ImListIterator;

/**
 * Immutable sorted list of LiteralCounters
 * @author naffins
 *
 */
public abstract class LiteralCounterList implements ImList<LiteralCounter> {
	
	/**
	 * Add new LiteralCounter
	 */
	public abstract LiteralCounterList add(LiteralCounter lc);
	
	/**
	 * Remove LiteralCounter if one with its variable id exists
	 */
	public abstract LiteralCounterList remove(LiteralCounter lc);
	
	/**
	 * Deduct counter for only 1 literal - this deletes the literal if both of its counters reach 0
	 * @param value: variable id
	 * @param qty: quantity to deduct
	 * @param isPositive: whether to deduct from positive counter
	 * @return updated LiteralCounterList
	 */
	public abstract LiteralCounterList deduct(Short value, Short qty, boolean isPositive);
	
	/**
	 * Deduct literal counts for multiple literals
	 * @param lcList: list of literals with deducting values
	 * @return updated LiteralCounterList
	 */
	public abstract LiteralCounterList deductMultiple(LinkedList<LiteralCounter> lcList);
	
	/**
	 * Return a boolean for whether a counter for said variable id still exists
	 * @param var: LiteralCounter with variable id to check for
	 * @return check result
	 */
	public abstract boolean contains(LiteralCounter lc);
	
	/**
	 * Get list of all pure literals in the list
	 * @return LinkedList of all pure literals in list
	 */
	public abstract LinkedList<LiteralCounter> getPures();
	
	/**
	 * Return the current LiteralCounter
	 */
	public abstract LiteralCounter first();
	
	/**
	 * Return the remaining list
	 */
	public abstract LiteralCounterList rest();
	
	public abstract int length();
	
	public abstract boolean isEmpty();
	
	/**
	 * Return string representation for current variable id
	 */
	public abstract String print();
	
	/**
	 * Return string representation for LiteralCounterList
	 */
	public abstract String toString();
	
	public Iterator<LiteralCounter> iterator(){
		return new ImListIterator<LiteralCounter>(this);
	}
	
	/**
	 * Check equality by checking constituent LiteralCounter value equality
	 */
	public abstract boolean equals(Object o);
	
}
