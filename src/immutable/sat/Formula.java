package immutable.sat;

import java.util.Iterator;
import java.util.LinkedList;

import global.Trio;
import immutable.ImList;

public abstract class Formula implements ImList<Clause>{
	
	public abstract Formula add(Clause c);
	
	public abstract Formula remove(Clause c);
	
	/**
	 * Remove both positive and negative l from list of clauses 
	 * @param l: literal with variable id to remove
	 * @return Pair containing new formula and corresponding smallest clause
	 */
	public abstract Trio<Formula,LinkedList<Literal>,Clause> eliminate(Literal l);
	
	public abstract Clause first();
	
	public abstract Formula rest();
	
	public abstract int length();
	
	public abstract boolean isEmpty();
	
	public abstract String print();
	
	public abstract String toString();
	
	public abstract Iterator<Clause> iterator();
	
	public abstract boolean equals(Object o);
	
}
