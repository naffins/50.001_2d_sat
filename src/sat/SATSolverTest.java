package sat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import immutable.sat.*;

public class SATSolverTest {


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
    			if ((out.isEmpty())||out.equals("\n")) continue;
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
			
    		Formula f = new Formula();
    		
    		//for (Clause c:clauses) System.out.println(c);
    		for (Clause c:clauses) f = f.addClause(c);
    		
    		System.out.println("Executing");
    		
    		System.out.println(SATSolver.solve(f));
    		System.out.println("Executed");
    		//Clause[] clauseArray = new Clause[clauses.size()];
    		//clauses.toArray(clauseArray);
    	}
    	// For in case file doesn't exist
    	catch (FileNotFoundException e) {
    		System.out.println("CNF file not found");
    	}
    }
    
    public static void parseAndEmpty(LinkedList<String> store, ArrayList<Clause> clauses) {
    	int total = store.size();
    	Clause newClause = new EmptyClause();
    	for (int i=0;i<total;i++) {
    		String cur = store.removeFirst();
    		boolean is_positive = !(cur.charAt(0)=='-');
    		Short value = is_positive? Short.parseShort(cur):Short.parseShort(cur.substring(1));
    		newClause = newClause.add(value, is_positive);
    	}
    	clauses.add(newClause);
    }
    
}