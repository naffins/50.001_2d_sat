package sat;

/**
 * Class to simply indicate where the solver branches during DPLL end-phase
 * @author naffins
 *
 */
public class DebugDecisionVar {
	int iter = 0;
	boolean isPositive = true;
	
	/**
	 * Create a var indicating branching route, to be included in an ImList
	 * @param a: current step (starting from 0)
	 * @param b: whether the positive or negative of the chosen literal was used for branching
	 */
	public DebugDecisionVar(int a, boolean b) {
		iter = a;
		isPositive = b;
	}
	
	/**
	 * Print branching choice, to be printed as part of the ImList
	 */
	public String toString() {
		String rt = String.format("%05d", iter);
		rt += isPositive? "T":"F";
		return rt;
	}
}
