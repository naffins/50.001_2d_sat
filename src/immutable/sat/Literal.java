package immutable.sat;

public class Literal {
	
	private Short value = (short) 0;
	private boolean isPositive = true;
	
	/**
	 * Construct a Literal object, which merely comprises a Short and a boolean
	 * @param sh: variable id
	 * @param bool: required polarity
	 */
	public Literal(Short sh, boolean bool) {
		value = sh;
		isPositive = bool;
	}
	
	/**
	 * Return the negative of this literal
	 * @return the negative of this literal
	 */
	public Literal getNegative() {
		return new Literal(value,!isPositive);
	}
	
	/**
	 * Get variable id
	 * @return the variable id as a Short
	 */
	public Short value() {
		return value;
	}
	
	/**
	 * Get required variable polarity
	 * @return the required polarity as a boolean
	 */
	public boolean isPositive() {
		return isPositive;
	}
	
	/**
	 * Get String representation of variable
	 * @return representation as String
	 */
	public String print() {
		return isPositive? value.toString() : "-" + value.toString();
	}
	
	/**
	 * Function to print literal representation directly (different from print())
	 * @return String representation
	 */
	public String toString() {
		return "Literal[" + print() + "]";
	}
	
	/**
	 * Check for equivalence of 2 literals
	 */
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof Literal)) return false;
		Literal l = (Literal) o;
		
		// Confirm equality in value and polarity
		return l.value().equals(value) && l.isPositive()==isPositive;
	}
	
}
