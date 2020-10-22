package immutable.sat;

import java.util.Iterator;

import immutable.ImDoubleListIterator;

public class EmptyEnvironment extends Environment {
	private int size = 0;
	public EmptyEnvironment() {}
	
	public Environment add(Short sh, boolean bool) {
		return new NonEmptyEnvironment(sh,bool,this);
	}
	
	public String first() {
		assert false: "EmptyEnvironment.first";
		return null;
	}
	
	public Environment rest() {
		assert false: "EmptyEnvironment.rest";
		return null;
	}
	
	public boolean contains(String e) {
		return false;
	}
	
	public boolean contains(Short sh) {
		return false;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return true;
	}
	
	public String printer() {
		assert false: "EmptyEnvironment.printer";
		return null;
	}
	
	public Iterator<String> iterator() {
		return new ImDoubleListIterator(this);
	}
	
	public boolean equals(Object o) {
		return o instanceof EmptyEnvironment;
	}
	
	public String toString() {
		return "Environment[]";
	}
	
}
