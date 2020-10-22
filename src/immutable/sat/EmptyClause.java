package immutable.sat;

import java.util.Iterator;
import immutable.ImDoubleListIterator;

public class EmptyClause extends Clause {
    /**
     * @param e
     *            element to add
     * @requires e != null
     * @return [e,e_0,...,e_n] where this list = [e_0,...,e_n]
     */
	public EmptyClause() {}
	
    public Clause add(Short sh, boolean bool) {
    	assert sh != null : "EmptyClause.add(null,bool)";
    	return new NonEmptyClause(sh,bool,this);
    }

    /**
     * Get first element of this list.
     * 
     * @requires this list is nonempty
     * @return e_0 where this list = [e_0,...,e_n]
     */
    public String first() {
    	assert false : "EmptyClause.first";
    	return null;
    }
    public Clause rest() {
    	assert false : "EmptyClause.first";
    	return null;
    }
    public Clause eliminate(String e, String inv_e) {
    	assert false : "EmptyClause.eliminate";
    	return null;
    }
    public boolean contains(String e){
    	return false;
    }
    public int size() {
    	return 0;
    }
    public boolean isEmpty() {
    	return true;
    }
    
    public String printer() {
    	assert false : "EmptyClause.printer";
    	return null;
    }
    
    public boolean equals(Object o) {
        return o instanceof EmptyClause;
    }
    
    public String toString() {
        return "Clause[]";
    }
    
    public Iterator<String> iterator(){
    	return new ImDoubleListIterator(this);
    }

}