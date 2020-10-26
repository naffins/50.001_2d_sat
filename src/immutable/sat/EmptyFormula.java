package immutable.sat;

import java.util.Iterator;
import java.util.LinkedList;

import global.Trio;
import immutable.ImListIterator;

public class EmptyFormula extends Formula{
	
	private int length = 0;
	
	public EmptyFormula() {}
	
	public Formula add(Clause c) {
		return new NonEmptyFormula(c,this);
	}
	
	public Formula remove(Clause c) {
		return this;
	}
	
	public Trio<Formula,LinkedList<Literal>,Clause> eliminate(Literal l){
		return null;
	}
	
	public Clause first() {
		return null;
	}
	
	public Formula rest() {
		return null;
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
		return "Formula[]";
	}
	
	public Iterator<Clause> iterator(){
		return new ImListIterator<Clause>(this);
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		return o instanceof EmptyFormula;
	}
	
}
