package sat;

import immutable.EmptyImList;
import immutable.NonEmptyImList;
import immutable.ImList;
import immutable.sat.Clause;
import immutable.sat.EmptyEnvironment;
import immutable.sat.Environment;
import immutable.sat.Formula;
/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        Environment rt = new EmptyEnvironment();
        rt = solve(formula.getClauses(),rt,0);
        return rt;
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env,int i) {
    	System.out.println(i);
    	if (clauses.isEmpty()) return env;
    	int min_size = Integer.MAX_VALUE;
    	Clause sel = null;
        for (Clause c:clauses) {
        	if (c.size()==0) return null;
        	if (c.size()<min_size) {
        		min_size = c.size();
        		sel = c;
        	}
        }
        String to_eliminate = sel.first();
        Environment new_env = env.add(to_eliminate.charAt(0)!='-'?Short.parseShort(to_eliminate):
		Short.parseShort(to_eliminate.substring(1)),
		to_eliminate.charAt(0)!='-');
        ImList<Clause> new_clauses = substitute(clauses,to_eliminate);
        Environment result = solve(new_clauses,new_env,i+1);
        if (sel.size()==1) {
        	return result;
        }
        if (result!=null) return result;
        to_eliminate = to_eliminate.charAt(0)=='-'? to_eliminate.substring(1) : "-" + to_eliminate;
        new_env = env.add(to_eliminate.charAt(0)!='-'?Short.parseShort(to_eliminate):
        	Short.parseShort(to_eliminate.substring(1)),
    		to_eliminate.charAt(0)!='-');
        new_clauses = substitute(clauses,to_eliminate);
        return solve(new_clauses,new_env,i+1);
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses, String to_eliminate) {
        ImList<Clause> new_clauses = new EmptyImList<Clause>();
        String to_eliminate_inv = to_eliminate.charAt(0)=='-'? to_eliminate.substring(1) : "-" + to_eliminate;
        for (Clause c:clauses) {
        	Clause c_new = c.eliminate(to_eliminate, to_eliminate_inv);
        	if (c_new!=null) new_clauses = new_clauses.add(c_new);
        }
        return new_clauses;
    }

}
