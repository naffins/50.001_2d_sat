package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;
import java.io.File;
import java.io.FileNotFoundException;
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
    		while(cnfReader.hasNextLine()) {
    			String out = cnfReader.nextLine();
    			System.out.println(out);
    		}
    		cnfReader.close();
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
    
    
    
}