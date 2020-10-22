package sat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
        rt = solveModDPLL(formula.getClauses(),rt,null);
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
    private static Environment solve(ImList<Clause> clauses, Environment env) {
    	if (clauses.isEmpty()) return env;
    	for (Clause c: clauses) if (c.isEmpty()) return null;
    	
    	Clause least = null;
    	
    	int min_size = Integer.MAX_VALUE;
    	HashMap<Short,PosNeg> lits_left = new HashMap<Short,PosNeg>();
    	for (Clause c: clauses) {
    		String[] lits = reverseParseClauseContent(c.toString());
    		if (lits.length<min_size) {
    			min_size = lits.length;
    			least = c;
    		}
    		for (String lit:lits) {
    			boolean isPositive = lit.charAt(0)!='-';
    			Short val = !isPositive ? Short.parseShort(lit.substring(1)):Short.parseShort(lit);
    			if (!(lits_left.containsKey(val))) lits_left.put(val,new PosNeg());
    			if (isPositive) lits_left.get(val).positive = true;
    			else lits_left.get(val).negative = true;
    		}
    	}
    	ArrayList<String> pure_lits = new ArrayList<String>();
    	Iterator<Short> lits_left_keys = lits_left.keySet().iterator();
    	while(lits_left_keys.hasNext()) {
    		Short key = lits_left_keys.next();
    		if (lits_left.get(key).check()) continue;
    		if (lits_left.get(key).positive) pure_lits.add(key.toString());
    		else pure_lits.add("-"+key.toString());
    	}
    	ImList<Clause> new_clauses = clauses;
    	Environment new_env = env;
    	for (String s:pure_lits) {
    		substituteReturn ans = substitute(new_clauses,new_env,s);
    		new_clauses = ans.clauses;
    		new_env = ans.env;
    		least = ans.sel_clause;
    	}
    	if (new_clauses.isEmpty()) return new_env;
    	
    	String to_decide = least.first();
    	substituteReturn ans = substitute(new_clauses,new_env,to_decide);
    	Environment rt = solve(ans.clauses,ans.env);
    	if (least.size()==1) return rt;
    	if (rt!=null) return rt;
    	to_decide = to_decide.charAt(0)!='-'? "-" + to_decide : to_decide.substring(1);
    	ans = substitute(new_clauses,new_env,to_decide);
    	return solve(ans.clauses,ans.env);
    }
    
    private static Environment solveModDPLL(ImList<Clause> clauses, Environment env, Clause least) {
    	if (clauses.isEmpty()) return env;
    	if (least instanceof Clause && least.isEmpty()) return null;
    	Clause leastt = least;
    	if (leastt==null) {
    		int min_size = Integer.MAX_VALUE;
    		for (Clause c:clauses) {
    			if (c.size()<min_size) {
    				leastt=c;
    				min_size = c.size();
    			}
    		}
    	}
    	String to_decide = leastt.first();
    	substituteReturn ans = substitute(clauses,env,to_decide);
    	Environment rt = solveModDPLL(ans.clauses,ans.env,ans.sel_clause);
    	if (leastt.size()==1) return rt;
    	if (rt!=null) return rt;
    	to_decide = to_decide.charAt(0)!='-'? "-" + to_decide : to_decide.substring(1);
    	ans = substitute(clauses,env,to_decide);
    	return solveModDPLL(ans.clauses,ans.env,ans.sel_clause);
    }
    
    private static substituteReturn substitute(ImList<Clause> clauses, Environment env, String to_eliminate) {
    	String to_eliminate_inv = to_eliminate.charAt(0)=='-'? to_eliminate.substring(1) : "-" + to_eliminate;
    	ImList<Clause> new_clauses = new EmptyImList<Clause>();
    	int min_size = Integer.MAX_VALUE;
    	Clause least = null;
    	for (Clause c:clauses) {
    		Clause new_c = c.eliminate(to_eliminate, to_eliminate_inv);
    		if (new_c==null) continue;
    		new_clauses = new_clauses.add(new_c);
    		if (new_c.size()<min_size) {
    			min_size = new_c.size();
    			least = new_c;
    		}
    	}
    	boolean isPositive = to_eliminate.charAt(0)!='-';
    	Short val = !isPositive? Short.parseShort(to_eliminate.substring(1)) : Short.parseShort(to_eliminate);
    	substituteReturn rt = new substituteReturn(new_clauses,env.add(val, isPositive),least);
    	return rt;
    }
    
    private static String[] reverseParseClauseContent(String s) {
    	return s.substring(7,s.length()-1).split(",",0);
    }
    
    

}

final class substituteReturn{
	public ImList<Clause> clauses = null;
	public Environment env = null;
	public Clause sel_clause = null;
	
	public substituteReturn(ImList<Clause> a,Environment b,Clause c) {
		clauses = a;
		env = b;
		sel_clause = c;
	}
}

final class PosNeg{
	public boolean positive = false;
	public boolean negative = false;
	public boolean check() {return positive && negative;}
}
