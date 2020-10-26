package sat;

import java.io.IOException;
import java.util.LinkedList;

import global.Trio;
import global.Quad;
import immutable.EmptyImList;
import immutable.ImList;
import immutable.sat.Clause;
import immutable.sat.Formula;
import immutable.sat.Literal;
import immutable.sat.LiteralCounter;
import immutable.sat.LiteralCounterList;

public class SATSolver {
	
	public static ImList<Literal> solveOverall(Formula f, LiteralCounterList lcList, Clause minClause, ImList<Literal> env, int varCount){
		
		// Get a minimum set of literals needed to satisfy SAT problem, if it exists
		ImList<Literal> ans = solve(f, lcList, minClause, env, varCount);
		
		// If no set of literals to satisfy problem exist, return null
		if (ans==null) return ans;
		
		// Save the literals existing within the set in an array
		Literal[] lits = new Literal[varCount];
		for (Literal lit:ans) {
			lits[lit.value()-1] = lit;
		}
		
		// Place the literals in the array into a new list; if some literals are missing, allocate true by default
		ImList<Literal> processedAns = new EmptyImList<Literal>();
		for (int i=varCount-1;i>-1;i--) {
			if (lits[i]==null) {
				processedAns = processedAns.add(new Literal((short)(i+1),true));
				continue;
			}
			processedAns = processedAns.add(lits[i]);
		}
		return processedAns;
	}

	public static ImList<Literal> solve(Formula f, LiteralCounterList lcList, Clause minClause, ImList<Literal> env, int varCount){
		
		// If formula is empty (and hence no smallest clause exists), problem is satisfied
		if (f.isEmpty()) return env;
		
		// If our smallest clause is empty, problem is NOT satisfied
		if (minClause.isEmpty()) return null;
		
		// Create variables to store updated formula (list of clauses), LiteralCounterList (list of counters for literals in the formula),
		// minimum clause (which can not exist, in which case it will be null), and environment (a list of already-assigned literals)
		Formula curFormula = f;
		LiteralCounterList curLcList = lcList;
		Clause curMinClause = minClause;
		ImList<Literal> newEnv = env;
		
		// Unit clause elimination: keep eliminating the smallest clause as long as it is a unit clause
		while(curMinClause!=null) {
			
			// If the smallest clause is an empty clause, problem is not satisfied
			if (curMinClause.isEmpty()) return null;
			
			// If smallest clause is non-unit, break out of the loop: we won't find any more unit clauses without branching
			if (curMinClause.length()>1) break;
			
			// Get the literal needed to satisfy the current unit clause
			Literal litToSubstitute = curMinClause.first();
			
			// Apply the literal assignment, and get the new formula, LiteralCounterList, minimum-length clause and environment, and update
			Quad<Formula,LiteralCounterList,Clause,ImList<Literal>> substituteReturn = substitute(litToSubstitute,curFormula,curLcList,curMinClause,newEnv,varCount);
			curFormula = substituteReturn.a;
			curLcList = substituteReturn.b;
			curMinClause = substituteReturn.c;
			newEnv = substituteReturn.d;
		}
		
		// If our minimum clause is non-existent, our formula is empty and we have satisfied the problem
		if (curMinClause==null) return newEnv;
		
		// From the list of counters of literals in the formula, get all pure literals
		LinkedList<LiteralCounter> puresList = curLcList.getPures();
		
		// Pure literals elimination: while pure literals exist, continue to eliminate them
		while (puresList.size()>0) {
			
			// Get the first pure literal found, and create the corresponding literal for application/substitution
			LiteralCounter lcToRemove = puresList.get(0);
			Literal litToSubstitute = new Literal(lcToRemove.value(),lcToRemove.isOnlyPure(true));
			
			// Again, update the 4 values
			Quad<Formula,LiteralCounterList,Clause,ImList<Literal>> substituteReturn = substitute(litToSubstitute,curFormula,curLcList,curMinClause,newEnv,varCount);
			curFormula = substituteReturn.a;
			curLcList = substituteReturn.b;
			curMinClause = substituteReturn.c;
			newEnv = substituteReturn.d;
			
			// Note that the elimination of clauses with pure literals can make previously non-pure literals pure, so we keep recalculating
			// the list of pure literals
			puresList = curLcList.getPures();
			
		}
		
		// If our minimum clause is non-existent, our formula is empty and we have satisfied the problem
		if (curMinClause==null) return newEnv;
		
		// Get the literal of the current minimum clause - we will use this for branching
		Literal litToSubstitute = curMinClause.first();
		
		// Create new variables for the 4 values: this will allow us to save the post-unit clause and pure literals elimination results,
		// and branch based on them
		// Here we branch using the literal itself
		Quad<Formula,LiteralCounterList,Clause,ImList<Literal>> substituteReturn = substitute(litToSubstitute,curFormula,curLcList,curMinClause,newEnv,varCount);
		Formula finFormula = substituteReturn.a;
		LiteralCounterList finLcList = substituteReturn.b;
		Clause finMinClause = substituteReturn.c;
		ImList<Literal> finEnv = substituteReturn.d;
		
		// Get the results of applying the literal itself
		ImList<Literal> ansEnv = solve(finFormula,finLcList,finMinClause,finEnv,varCount);
		
		// If problem is satisfied with this branch, return the resulting environment
		if (ansEnv!=null) return ansEnv;
		
		// Otherwise, repeat but with the negation of the literal
		litToSubstitute = curMinClause.first().getNegative();
		substituteReturn = substitute(litToSubstitute,curFormula,curLcList,curMinClause,newEnv,varCount);
		finFormula = substituteReturn.a;
		finLcList = substituteReturn.b;
		finMinClause = substituteReturn.c;
		finEnv = substituteReturn.d;
		
		// This time, if the problem can't be satisfied then it won't overall for the environment up to this recursion level
		return solve(finFormula,finLcList,finMinClause,finEnv,varCount);
		
	}
	
	
	// Apply a certain literal to the entire formula, and update the list of counters of literals in the formula, formula and environment,
	// and return the minimum-length clause
	public static Quad<Formula,LiteralCounterList,Clause,ImList<Literal>> substitute(Literal literalToSubstitute,
			Formula f, LiteralCounterList lcList, Clause minClause, ImList<Literal> env, int varCount){
		
		// Variable to save the new environment
		ImList<Literal> newEnv = env.add(literalToSubstitute);
		
		// Apply the literal to the formula, and get
		// (1) New formula
		// (2) List of removed literals
		// (3) New minimum clause
		Trio<Formula,LinkedList<Literal>,Clause> substituteReturn = f.eliminate(literalToSubstitute);
		
		// Create array to count how many literals of each value and polarity were removed
		int[][] deductArray = new int[varCount][2];
		for (Literal l: substituteReturn.b) {
			deductArray[l.value()-1][l.isPositive()?0:1] += 1;
		}
		
		// Create list to store literal counters indicating how many literals of each value and polarity were removed (if any)
		LinkedList<LiteralCounter> deductLcArray = new LinkedList<LiteralCounter>();
		for (int i=0;i<varCount;i++) {
			if (deductArray[i][0]!=0 || deductArray[i][1]!=0) {
				deductLcArray.add(new LiteralCounter((short)(i+1),(short)deductArray[i][0],(short)deductArray[i][1]));
			}
		}
		
		// Return the required answers, the new LiteralCounterList being derived by using deductLcArray to deduct from multiple counters
		return new Quad<Formula,LiteralCounterList,Clause,ImList<Literal>>(substituteReturn.a,lcList.deductMultiple(deductLcArray),substituteReturn.c,newEnv);
	}
}
