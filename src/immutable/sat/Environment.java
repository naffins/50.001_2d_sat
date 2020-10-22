package immutable.sat;

import java.util.Iterator;

import immutable.*;

public abstract class Environment implements ImDoubleList{
	
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
    public abstract Environment rest();
    
    public abstract Environment add(Short sh, boolean bool);

    /**
     * @requires e != null
     * @return exists i such that e_i.equals(e) where e_i is ith element of this
     */
    public abstract boolean contains(String e);
    public abstract boolean contains(Short sh);

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
    
    public Iterator<String> iterator(){
    	return new ImDoubleListIterator(this);
    }
    
    public abstract boolean equals(Object o);
    
    public abstract String toString();
}
