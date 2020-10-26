package immutable.sat;

import java.util.LinkedList;

public class EmptyLiteralCounterList extends LiteralCounterList{
	
	private int length = 0;
	
	public EmptyLiteralCounterList() {}
	
	public LiteralCounterList add(LiteralCounter lc) {
		return new NonEmptyLiteralCounterList(lc,this);
	}
	
	public LiteralCounterList remove(LiteralCounter lc) {
		return this;
	}
	
	public LiteralCounterList deduct(Short value, Short qty, boolean isPositive) {
		return this;
	}
	
	public LiteralCounterList deductMultiple(LinkedList<LiteralCounter> lcList) {
		return this;
	}
	
	public boolean contains(LiteralCounter lc) {
		return false;
	}
	
	public LinkedList<LiteralCounter> getPures(){
		return new LinkedList<LiteralCounter>();
	}
	
	public LiteralCounter first() {
		return null;
	}
	
	public LiteralCounterList rest() {
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
		return "LiteralCounterList[]";
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		return o instanceof EmptyLiteralCounterList;
	}
	
}
