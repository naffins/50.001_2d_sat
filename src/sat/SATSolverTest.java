package sat;

import immutable.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import global.Quad;
import global.Trio;
import immutable.sat.*;
public class SATSolverTest {
	public static void main (String[] args) {
		try {
			
			// Open the CNF file
			File file = new File(args[0]);
			Scanner fileScanner = new Scanner(file);
			
			// Parse the file to get the formula (list of clauses), list of counters of literals,
			// minimum-length clause in the formula and the number of variables
			Quad<Formula,LiteralCounterList,Clause,Integer> parseResult = parse(fileScanner);
			
			// Solve the formula and get time required
			System.out.println("SAT solver starts!!!");
			long started = System.nanoTime();
			ImList<Literal> result = SATSolver.solveOverall(parseResult.a, parseResult.b, parseResult.c, new EmptyImList<Literal>(), parseResult.d);
			long time = System.nanoTime();
			long timeTaken= time-started;
			System.out.println("Time:" + timeTaken/1000000.0 + "ms");
			System.out.println(result==null?"not satisfiable":"satisfiable");
			
			if (result!=null) {
				try {
					FileWriter fileWriter = new FileWriter("satisfying_result.txt");
					fileWriter.write(result.toString());
					fileWriter.close();
					System.out.println("Successfully written solution to \"satisfying_result.txt\" in current directory");
				} catch (IOException f) {
					System.out.println("Could not write solution to file");
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("CNF file not found");
		}
	}
	
	// Parse the file
	public static Quad<Formula,LiteralCounterList,Clause,Integer> parse(Scanner s) {
		
		// Boolean for whether the file with "p" for problem definition has been found
		boolean foundProblem = false;
		
		// Integer to indicate which token in the line with "p" has "p"
		int problemCharPos = 0;
		
		// Number of variables
		int varCount = 0;
		
		// Iterate through lines
		while (s.hasNextLine()) {
			
			// Segment lines by spaces
			String[] curStrs = s.nextLine().split(" ");
			for (int i=0;i<curStrs.length;i++) {
				
				// Ignore comments
				if (curStrs[i].equals("c")) break;
				
				// Once we find "p" for problem definition, exit both loops
				if (curStrs[i].equals("p")) {
					foundProblem = true;
					problemCharPos = i;
					break;
				}
			}
			
			// Before we exit this outer loop, get the number of variables
			if (foundProblem) {
				for (int i=problemCharPos+1;i<curStrs.length;i++) {
					
					// Check whether the token is a valid integer
					if (isStringANumber(curStrs[i])) {
						varCount = Integer.parseInt(curStrs[i]);
						break;
					}
				}
				break;
			}
		}
		
		// Create an array to store the number of literals of each polarity in the entire formula
		int[][] counts = new int[varCount][2];
		
		// Create an array to store all the literals found in the problem
		LinkedList<Literal> litsList = new LinkedList<Literal>();
		
		// Iterate through all remaining tokens of the problem statement, and convert those that are numbers into literals (including 0)
		// and add them to the list
		while(s.hasNext()) {
			String curStr = s.next();
			if (isStringANumber(curStr)) litsList.add(stringToLiteral(curStr));
		}
		
		// Create variables to store the formula, the current clause being processed and the minimum-length clause
		Formula f = new EmptyFormula();
		Clause cur = new EmptyClause();
		Clause minClause = null;
		
		// Also create a variable to store the minimum clause length found so far
		int minSize = Integer.MAX_VALUE;
		for (Literal l:litsList) {
			
			// If we reach the clause delimiter, dump the currently-processed clause to the formula and start a new one
			if (l.value()==(short)0) {
				f = f.add(cur);
				
				// Update the minimum-length clause if necessary
				if (cur.length()<minSize) {
					minSize = cur.length();
					minClause = cur;
				}
				
				// Update the literal counting array as needed. Note that the clause class already takes care of duplicates.
				for (Literal l1:cur) {
					counts[l1.value()-1][l1.isPositive()?0:1] += 1;
				}
				cur = new EmptyClause();
				continue;					
			}
			
			// Add the current literal to the current clause
			cur = cur.add(l);
		}
		
		// If the current clause is still non-empty without any concluding delimiter, dump it to the formula too
		if (!cur.isEmpty()) f = f.add(cur);
		
		// Create the LiteralCounterList
		LiteralCounterList lcList = new EmptyLiteralCounterList();
		for (int i=varCount;i>0;i--) {
			lcList = lcList.add(new LiteralCounter((short)i,(short)counts[i-1][0],(short)counts[i-1][1]));
		}
		
		// Return the required outputs
		return new Quad<Formula,LiteralCounterList,Clause,Integer>(f,lcList,minClause,varCount);
	}
	
	// Convert a string for an integer to a literal
	public static Literal stringToLiteral(String s) {
		boolean isPositive = s.charAt(0)!='-';
		return new Literal(Short.parseShort(isPositive?s:s.substring(1)),isPositive);
	}
	
	// Check whether a string contains an integer, i.e. is all numbers except for at most 1 '-' at the start
	public static boolean isStringANumber(String s) {
		if (s==null) return false;
		if (s.length()==0) return false;
		if (s.charAt(0)=='-') {
			if (s.length()==1) return false;
			for (char c:s.substring(1).toCharArray()) if (c<'0' || c>'9') return false;
			return true;
		}
		for (char c:s.substring(0).toCharArray()) if (c<'0' || c>'9') return false;
		return true;
	}
	
}
