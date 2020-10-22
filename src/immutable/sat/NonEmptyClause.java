package immutable.sat;

import java.util.Iterator;
import immutable.ImDoubleListIterator;

public class NonEmptyClause extends Clause {
	
	private Short value;
	private boolean isPositive;
	private Clause rest;
	private int size;
    /**
     * @param e
     *            element to add
     * @requires e != null
     * @return [e,e_0,...,e_n] where this list = [e_0,...,e_n]
     */
	public NonEmptyClause(Short sh, boolean bool, Clause rt) {
		value = sh;
		isPositive = bool;
		rest = rt;
		size = rt.size()+1;
	}
	
    public Clause add(Short sh, boolean bool) {
    	assert sh != null : "NonEmptyClause.add(null,bool)";
    	return new NonEmptyClause(sh,bool,this);
    }

    /**
     * Get first element of this list.
     * 
     * @requires this list is nonempty
     * @return e_0 where this list = [e_0,...,e_n]
     */
    public String first() {
    	return printer();
    }
    public Clause rest() {
    	return rest;
    }
    public Clause eliminate(String e, String inv_e) {
    	String st_value = printer();
    	if (st_value.equals(e)) return null;
    	if (st_value.equals(inv_e)) return rest;
    	if (rest instanceof EmptyClause) return this;
    	Clause new_rest = rest.eliminate(e,inv_e);
    	if (rest==new_rest) return this;
    	if (new_rest==null) return null;
    	return new NonEmptyClause(value,isPositive,new_rest);
    }
    public boolean contains(String e){
    	String st_value = printer();
    	if (st_value.equals(e)) return true;
    	else return rest.contains(e);
    }
    public int size() {
    	return size;
    }
    public boolean isEmpty() {
    	return false;
    }
    
    public String printer() {
    	if (isPositive) return value.toString();
    	return "-" + value.toString();
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof NonEmptyClause)) return false;
        NonEmptyClause eq = (NonEmptyClause) o;
        return toString().equals(eq.toString());
    }
    
    public String toString() {
        String out = "Clause[";
        Clause cur = this;
        for (int i=size();i>0;i--) {
        	out += cur.printer();
        	if (i!=1) out += ",";
        	cur = cur.rest();
        }
        out += "]";
        return out;
    }
    
    public Iterator<String> iterator(){
    	return new ImDoubleListIterator(this);
    }

}