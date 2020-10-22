package immutable;

/**
 * An immutable list interface
 * Designed for illustrating reasoning about immutable types
 * 
 * Copyright 2007 Daniel Jackson and MIT
 */

import java.util.Iterator;

public interface ImDoubleList extends Iterable<String> {
    /**
     * @param e
     *            element to add
     * @requires e != null
     * @return [e,e_0,...,e_n] where this list = [e_0,...,e_n]
     */
    public ImDoubleList add(Short sh, boolean bool);

    /**
     * Get first element of this list.
     * 
     * @requires this list is nonempty
     * @return e_0 where this list = [e_0,...,e_n]
     */
    public String first();

    /**
     * Get list of all elements of this list except for the first.
     * 
     * @requires this list is nonempty
     * @return [e_1,...,e_n] where this list = [e_0,...,e_n]
     */
    public ImDoubleList rest();

    /**
     * @requires e != null
     * @return exists i such that e_i.equals(e) where e_i is ith element of this
     */
    public boolean contains(String e);

    /**
     * @return number of elements in this
     */
    public int size();

    /**
     * @return true if this contains no elements
     */
    public boolean isEmpty();

    /**
     * see Iterable.iterator()
     */
    
    public String printer();
    
    public Iterator<String> iterator();
    
    public boolean equals(Object o);

}