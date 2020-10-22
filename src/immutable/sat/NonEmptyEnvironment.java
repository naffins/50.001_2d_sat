package immutable.sat;

import java.util.Iterator;

import immutable.ImDoubleListIterator;

public class NonEmptyEnvironment extends Environment {
	private int size = 0;
	private Short value = null;
	private boolean isPositive = true;
	private Environment rest = null;
	
	public NonEmptyEnvironment(Short sh, boolean bool, Environment rt) {
		value = sh;
		isPositive = bool;
		rest = rt;
	}
	
	public String first() {
		String rt = isPositive? Short.toString(value) : "-" + Short.toString(value);
		return rt;
	}
	
	public Environment rest() {
		return rest;
	}
	
	public boolean contains(String e) {
		Short search = e.charAt(0)=='-'? Short.parseShort(e.substring(1)) : Short.parseShort(e);
		return contains(search);
	}
	
	public boolean contains(Short search) {
		if ((short)search<(short)value) return false;
		if (search.equals(value)) return true;
		return rest.contains(search);
	}
	
	public Environment add(Short sh, boolean bool) {
		if ((short)sh<(short)value) return new NonEmptyEnvironment(sh,bool,this);
		if (rest instanceof EmptyEnvironment) return new NonEmptyEnvironment(value,isPositive,new NonEmptyEnvironment(sh,bool,rest));
		return new NonEmptyEnvironment(value,isPositive,rest.add(sh, bool));
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public String printer() {
    	String ret = isPositive? Short.toString(value):"-" + Short.toString(value);
    	return ret;
    }
	
	public Iterator<String> iterator(){
    	return new ImDoubleListIterator(this);
    }
	
	public boolean equals(Object o) {
    	if (!(o instanceof NonEmptyEnvironment)) return false;
    	NonEmptyEnvironment eq = (NonEmptyEnvironment) o;
    	return toString().equals(eq.toString());
    }
	
	public String toString() {
		String rt = "Environment[";
		Environment cur = this;
		while (!(cur.isEmpty())) {
			rt += cur.printer();
			cur = this.rest;
		}
		rt += "]";
		return rt;
	}
	
}
