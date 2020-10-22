package immutable.sat;

/**
 * An immutable list interface
 * Designed for illustrating reasoning about immutable types
 * 
 * Copyright 2007 Daniel Jackson and MIT
 */

import java.util.Iterator;
import immutable.*;

public abstract class Clause implements ImDoubleList {
    /**
     * @param e
     *            element to add
     * @requires e != null
     * @return [e,e_0,...,e_n] where this list = [e_0,...,e_n]
     */
    public abstract Clause add(Short sh, boolean bool);

    /**
     * Get first element of this list.
     * 
     * @requires this list is nonempty
     * @return e_0 where this list = [e_0,...,e_n]
     */
    public abstract String first();

    /**
     * Get list of all elements of this list except for the first.
     * 
     * @requires this list is nonempty
     * @return [e_1,...,e_n] where this list = [e_0,...,e_n]
     */
    public abstract Clause rest();

    /**
     * Remove the first occurrence of an element from the list, if present.
     * @param inv_e 
     * 
     * @requires e != null
     * @return [e0,..,e_{i-1], e_{i+1},..,e_n] where i is the minimum index such
     *         that e_i.equals(e); if no such i, then returns [e_0,..,e_n]
     *         unchanged.
     */
    public abstract Clause eliminate(String e, String inv_e);

    /**
     * @requires e != null
     * @return exists i such that e_i.equals(e) where e_i is ith element of this
     */
    public abstract boolean contains(String e);

    /**
     * @return number of elements in this
     */
    public abstract int size();

    /**
     * @return true if this contains no elements
     */
    public abstract boolean isEmpty();

    /**
     * see Iterable.iterator()
     */
    
    public abstract String printer();
    
    public abstract Iterator<String> iterator();
    
    public abstract boolean equals(Object o);

}