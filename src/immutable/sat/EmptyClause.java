package immutable.sat;

import java.util.LinkedList;

public class EmptyClause extends Clause{
	
	private int length = 0;
	
	public EmptyClause() {}
	
	public Clause add(Literal l) {
		return new NonEmptyClause(l,this);
	}
	
	public Clause remove(Literal l) {
		return this;
	}
	
	public boolean contains(Literal l) {
		return false;
	}
	
	public Literal first() {
		return null;
	}
	
	public Clause rest() {
		return null;
	}
	
	public LinkedList<Literal> contents(){
		return new LinkedList<Literal>();
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return true;
	}
	
	public String print() {
		return null;
	}
	
	public String toString() {
		return "Clause[]";
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		return o instanceof EmptyClause;
	}
	
}
