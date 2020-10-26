package immutable.sat;

/**
 * Immutable representation of a counter for
 * positive and negative copies of a literal
 * @author naffins
 *
 */
public class LiteralCounter {

	private Short value = 0;
	private Short positiveCount = 0;
	private Short negativeCount = 0;
	
	/**
	 * Constructor to initialize counter for specific value
	 * @param val: the literal variable id
	 * @param posCount: number of positive instances of the variable id
	 * @param negCount: number of negative instances of the variable id
	 */
	public LiteralCounter(Short val, Short posCount, Short negCount) {
		value = val;
		positiveCount = posCount;
		negativeCount = negCount;
	}
	
	/**
	 * Return variable id
	 * @return variable id
	 */
	public Short value() {
		return value;
	}
	
	public Short counter(boolean isPositive) {
		return isPositive? positiveCount:negativeCount;
	}
	
	/**
	 * Return a new LiteralCounter after deducting counters for only 1 polarity
	 * @param isPositive: boolean for whether to deduct from positive counter
	 * @param qty: amount to deduct from selected counter
	 * @return a new LiteralCounter
	 */
	public LiteralCounter deduct(boolean isPositive, Short qty) {
		if (isPositive) {
			if (positiveCount.equals(qty) && negativeCount.equals((short)0)) return null;
			return new LiteralCounter(value,(short)(positiveCount-qty),negativeCount);
		}
		else {
			if (positiveCount.equals((short)0) && negativeCount.equals(qty)) return null;
			return new LiteralCounter(value,positiveCount,(short)(negativeCount-qty));
		}
	}
	
	/**
	 * Return a boolean for whether the literal is pure for the selected polarity
	 * @param isPositive: boolean for whether the positive literal is chosen
	 * @return the boolean
	 */
	public boolean isOnlyPure(boolean isPositive) {
		if (isPositive) return (!positiveCount.equals((short)0)) && negativeCount.equals((short)0);
		return positiveCount.equals((short)0) && (!negativeCount.equals((short)0));
	}
	
	/**
	 * Return variable id as a String
	 * @return variable id String
	 */
	public String print() {
		return value.toString();
	}
	
	/**
	 * Output string representation of the LiteralCounter as printed
	 */
	public String toString() {
		return "LiteralCounter[value=" + value.toString() + ", positiveCount=" + positiveCount.toString() + ", negativeCount=" + negativeCount.toString() + "]";
	}
	
	/**
	 * Check if 2 LiteralCounters are equal in variable id and counters
	 */
	public boolean equals(Object o) {
		if (!(o instanceof LiteralCounter)) return false;
		LiteralCounter other = (LiteralCounter) o;
		return other.value()==value && other.counter(true)==positiveCount && other.counter(false)==negativeCount;
	}
}
