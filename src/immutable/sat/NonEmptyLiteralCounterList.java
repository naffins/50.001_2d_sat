package immutable.sat;

import java.util.LinkedList;

public class NonEmptyLiteralCounterList extends LiteralCounterList{

	private int length = 0;
	private LiteralCounter lc = null;
	private LiteralCounterList rest = null;
	
	public NonEmptyLiteralCounterList(LiteralCounter newLc, LiteralCounterList rt) {
		lc = newLc;
		rest = rt;
		length = rt.length() + 1;
	}
	
	public LiteralCounterList add(LiteralCounter newLc) {
		
		// If variable id is lower than current one
		if (newLc.value()<lc.value()) return new NonEmptyLiteralCounterList(newLc,this);
		
		// If variable id matches
		if (newLc.value().equals(lc.value())) {
			LiteralCounter updatedLc = new LiteralCounter(lc.value(),(short)(lc.counter(true)+newLc.counter(true)),(short)(lc.counter(false)+newLc.counter(false)));
			return new NonEmptyLiteralCounterList(updatedLc,rest);
		}
		
		// If variable id is higher but rest is empty
		if (rest.isEmpty()) return new NonEmptyLiteralCounterList(lc,new NonEmptyLiteralCounterList(newLc, rest));
		
		// Otherwise
		LiteralCounterList new_rest = rest.add(newLc);
		if (new_rest==rest) return this;
		return new NonEmptyLiteralCounterList(lc,new_rest);
		
	}
	
	public LiteralCounterList remove(LiteralCounter newLc) {
		
		// If variable id is lower than current one
		if (newLc.value()<lc.value()) return this;
				
		// If variable id matches
		if (newLc.value().equals(lc.value())) return rest;
				
		// If variable id is higher but rest is empty
		if (rest.isEmpty()) return this;
				
		// Otherwise
		LiteralCounterList new_rest = rest.remove(newLc);
		if (new_rest==rest) return this;
		return new NonEmptyLiteralCounterList(lc,new_rest);
	}
	
	public LiteralCounterList deduct(Short value, Short qty, boolean isPositive) {
		
		// If variable id is lower than current one
		if (value<lc.value()) return this;
		
		// If variable id matches
		if (lc.value().equals(value)) {
			LiteralCounter newLc = lc.deduct(isPositive, qty);
			if (newLc==null) return rest;
			return new NonEmptyLiteralCounterList(newLc,rest);
		}
		
		// If variable id is higher but rest is empty
		if (rest.isEmpty()) return this;
		
		// Otherwise
		LiteralCounterList new_rest = rest.deduct(value, qty, isPositive);
		if (new_rest==rest) return this;
		return new NonEmptyLiteralCounterList(lc,new_rest);
		
	}
	
	public LiteralCounterList deductMultiple(LinkedList<LiteralCounter> lcList) {
		LiteralCounterList returnList = this;
		while (lcList.size()>0) {
			LiteralCounter cur = lcList.removeFirst();
			if (cur.counter(true)!=(short)0) returnList = returnList.deduct(cur.value(), cur.counter(true), true);
			if (cur.counter(false)!=(short)0) returnList = returnList.deduct(cur.value(), cur.counter(false), false);
		}
		return returnList;
	}
	
	public boolean contains(LiteralCounter newLc) {
		if (newLc.value().equals(lc.value())) return true;
		return rest.contains(newLc);
	}
	
	public LinkedList<LiteralCounter> getPures(){
		LinkedList<LiteralCounter> pures = new LinkedList<LiteralCounter>();
		LiteralCounterList curList = this;
		while(!(curList.isEmpty())) {
			if (curList.first().isOnlyPure(true)||curList.first().isOnlyPure(false)) pures.add(curList.first());
			curList = curList.rest();
		}
		return pures;
	}
	
	public LiteralCounter first() {
		return lc;
	}
	
	public LiteralCounterList rest() {
		return rest;
	}
	
	public int length() {
		return length;
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public String print() {
		return lc.toString();
	}
	
	public String toString() {
		String rt = "LiteralCounterList[";
		LiteralCounterList cur = this;
		while (!cur.isEmpty()) {
			rt += cur.print();
			cur = cur.rest();
			if (!cur.isEmpty()) rt += ",\n\t\t   ";
		}
		rt += "]";
		return rt;
	}
	
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof NonEmptyLiteralCounterList)) return false;
		NonEmptyLiteralCounterList other = (NonEmptyLiteralCounterList) o;
		if (lc!=other.first()) return false;
		return rest.equals(other.rest());
	}
	
}
