package immutable.sat;

import java.util.LinkedList;

public class NonEmptyClause extends Clause{
	
	private int length = 0;
	private Clause rest = null;
	private Literal lit = null;
	
	public NonEmptyClause(Literal l, Clause rt) {
		length = rt.length()+1;
		rest = rt;
		lit = l;
	}
	
	public Clause add(Literal l) {
		// If variable id is less than that of first element
		if (l.value()<lit.value()) return new NonEmptyClause(l,this);
		
		// If literal already exists as the first element
		if (l.equals(lit)) return this;
		
		// If literal exists as the first element, but with different sign, delete clause
		if (l.value().equals(lit.value())) return null;
		
		// If rest is empty
		if (rest.isEmpty()) return new NonEmptyClause(lit,new NonEmptyClause(l,new EmptyClause()));
		
		// Add to rest
		Clause new_rest = rest.add(l);
		
		// If nothing changed, return this clause
		if (new_rest==rest) return this;
		
		// If rest clause gets deleted, delete this one too
		if (new_rest==null) return null;
		
		// Otherwise we have a new clause with a modified rest
		return new NonEmptyClause(lit,new_rest);
	}
	
	public Clause remove(Literal l) {
		
		// If value is smaller then it doesn't exist
		if (l.value()<lit.value()) return this;
		
		// If literal already exists as the first element, then delete this clause
		if (l.equals(lit)) return null;
				
		// If literal exists as the first element, but with different sign, delete just the literal
		if (l.value().equals(lit.value())) return rest;
		
		// If rest is empty, then the literal doesn't exist
		if (rest.isEmpty()) return this;
		
		// Remove from rest
		Clause new_rest = rest.remove(l);
		
		// If nothing changed, return this clause
		if (new_rest==rest) return this;
				
		// If rest clause gets deleted, delete this one too
		if (new_rest==null) return null;
				
		// Otherwise we have a new clause with a modified rest
		return new NonEmptyClause(lit,new_rest);
	}
	
	public boolean contains(Literal l) {
		if (l.equals(lit)) return true;
		if (l.value().equals(lit.value())) return false;
		if (l.value()<lit.value()) return false;
		return rest.contains(l);
	}
	
	public Literal first() {
		return lit;
	}
	
	public Clause rest() {
		return rest;
	}
	
	public LinkedList<Literal> contents(){
		LinkedList<Literal> contents = new LinkedList<Literal>();
		Clause current = this;
		while (!current.isEmpty()) {
			contents.add(current.first());
			current = current.rest();
		}
		return contents;
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public String print() {
		return lit.print();
	}
	
	public String toString() {
		String rt = "Clause[";
		Clause current = this;
		while (!current.isEmpty()) {
			rt += current.print();
			current = current.rest();
			if (!current.isEmpty()) rt += ",";
		}
		rt += "]";
		return rt;
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof NonEmptyClause)) return false;
		NonEmptyClause c = (NonEmptyClause) o;
		if (!c.lit.equals(lit)) return false;
		return rest.equals(c.rest());
	}
	
}
