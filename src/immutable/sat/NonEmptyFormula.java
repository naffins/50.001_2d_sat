package immutable.sat;

import java.util.Iterator;
import java.util.LinkedList;

import global.Trio;
import immutable.ImListIterator;

public class NonEmptyFormula extends Formula {
	
	private int length = 0;
	private Clause value = null;
	private Formula rest = null;
	
	public NonEmptyFormula(Clause c, Formula f) {
		value = c;
		rest = f;
		length = f.length() + 1;
	}
	
	public Formula add(Clause c) {
		return new NonEmptyFormula(c,this);
	}
	
	public Formula remove(Clause c) {
		if (value.equals(c)) return rest;
		
		if (rest.isEmpty()) return this;
		
		Formula new_rest = rest.remove(c);
		if (new_rest==rest) return this;
		return new NonEmptyFormula(value,new_rest);
	}
	
	public Trio<Formula,LinkedList<Literal>,Clause> eliminate(Literal l) {
		Clause minC = null;
		int minSize = Integer.MAX_VALUE;
		Formula rtF = new EmptyFormula();
		LinkedList<Literal> removedLiterals = new LinkedList<Literal>();
		
		for (Clause c:this) {
			Clause newC = c.remove(l);
			if (newC==null) {
				for (Literal removedL:c) {
					removedLiterals.add(removedL);
				}
				continue;
			}
			rtF = rtF.add(newC);
			if (newC!=c) removedLiterals.add(l.getNegative());
			if (newC.length()<minSize) {
				minSize = newC.length();
				minC = newC;
			}
		}
		
		return new Trio<Formula,LinkedList<Literal>,Clause>(rtF,removedLiterals,minC);
	}
	
	public Clause first() {
		return value;
	}
	
	public Formula rest() {
		return rest;
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public String print() {
		return value.toString();
	}
	
	public String toString() {
		String rt = "Formula[";
		Formula f = this;
		while (!f.isEmpty()) {
			rt += f.print();
			f = f.rest();
			if (!f.isEmpty()) rt += ",\n\t";
		}
		rt += "]";
		return rt;
	}
	
	public Iterator<Clause> iterator(){
		return new ImListIterator<Clause>(this);
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof NonEmptyClause)) return false;
		NonEmptyFormula f = (NonEmptyFormula) o;
		if (!value.equals(f.first())) return false;
		return rest.equals(f.rest());
	}
	
}
