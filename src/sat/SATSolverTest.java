package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();


    public static void main(String args[]) {
    	try {
    		File cnfFile = new File(args[0]);
    		Scanner cnfReader = new Scanner(cnfFile);
    		boolean is_preamble = true;
    		int varCount = 0, clauseCount = 0;
    		int clauseIter = 0;
    		LinkedList<String> store = new LinkedList<String>();
    		ArrayList<Clause> clauses = new ArrayList<Clause>();
    		while(cnfReader.hasNextLine()) {
    			String out = cnfReader.nextLine();
    			if (is_preamble) {
    				// Ignore comment
    				if (out.charAt(0)=='c') continue;
    				// Process preamble string
    				String[] problemArgs = out.split(" ",0);
    				varCount = Integer.parseInt(problemArgs[2]);
    				clauseCount = Integer.parseInt(problemArgs[3]);
    				is_preamble = false;
    			}
    			else {
    				String[] parseLits = out.split(" ",0);
    				for (String e: parseLits) {
    					if (e.equals("0")) {
    						parseAndEmpty(store,clauses);
    						clauseIter++;
    					}
    					else {
    						store.add(e);
    					}
    					//System.out.println(e);
    				}
    			}
    		}
    		if ((store.size()>0) && (clauseIter<clauseCount)) parseAndEmpty(store,clauses);
    		cnfReader.close();
			
    		Clause[] clauseArray = new Clause[clauses.size()];
    		clauses.toArray(clauseArray);
    		
    		Formula f1 = makeFm(clauseArray);
    		System.out.println(f1);
    	}
    	// For in case file doesn't exist
    	catch (FileNotFoundException e) {
    		System.out.println("CNF file not found");
    	}
    	
    }
	
	// TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability
    
	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    private static void parseAndEmpty(LinkedList<String> store,ArrayList<Clause> clauses) {
    	Literal[] curLits = new Literal[store.size()];
    	int total = store.size();
    	for (int i=0;i<total;i++) {
    		String cur = store.removeFirst();
    		Literal curLit;
    		if (cur.charAt(0)!='-') curLit = PosLiteral.make(cur);
    		else curLit = PosLiteral.make(cur.substring(1)).getNegation();
    		curLits[i] = curLit;
    	}
    	clauses.add(makeCl(curLits));
    }
    
}