package cs330;
import java.util.Scanner;

import org.junit.Test;

public class Calculator {

	public static void main( String[] args ) {
		try {
			compute( args[1] );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String compute( String program ) {
		String result = "";
		
		int openIndex = 0;
		int endIndex = 0;
		String operation = "";
		for ( int index = 0; index < program.length(); index++ ) {
			char nextChar = program.charAt( index );
			if ( nextChar == '(' ) {
				openIndex = index;
				operation = "";
			} else if ( nextChar == ')' ) {
				endIndex = index;
				if( !balanced( program ) )
					return "The program is unbalanced.";
				solve ( operation );
			} else {
				operation += nextChar;
			}
		}
		
		return result;
	}
	
	private static boolean balanced( String program ) {
		int nest = 0;
		
		for ( int index = 0; index < program.length( ); index++ ) {
			char nextChar = program.charAt(index);
			if ( nextChar == '(' ) {
				nest++;
			} else {
				nest--;
				if ( nest < 0 ) {
					return false;
				}
			}
		}
		
		if ( nest == 0 ) {
			return true;
		} else {
			return false;
		}
	}

	private static String solve( String operation ) {
		String result = "";
		
		Scanner scanner = new Scanner( operation );
		
		double leftOperand;
		double rightOperand;
		if ( scanner.hasNextDouble( ) && scanner.hasNextDouble( ) ) {
			leftOperand = scanner.nextDouble( );
			rightOperand = scanner.nextDouble( );
		} else {
			scanner.close();
			return "Couldn't solve the operation.";
		}
		
		if ( operation.contains( "+" ))
			result = String.valueOf( leftOperand + rightOperand );
		else if ( operation.contains( "-" ))
			result = String.valueOf( leftOperand - rightOperand );
		else if ( operation.contains( "*" ))
			result = String.valueOf( leftOperand * rightOperand );
		else if ( operation.contains( "/" ))
			result = String.valueOf( leftOperand / rightOperand );
		
		scanner.close( );
		
		return result;
	}
	
	@Test
	public void testCalculator( ) throws Exception {
		try {
			String add0 = "(+ 2 2)";
			String sub0 = "(- 2 1)";
			String mult0 = "(* 2 3)";
			String div0 = "(/ 2 2)";
			String prog0 = "(+ (* 2 3) (- 1 1))";
			String prog1 = "+()()";
			String prog2 = "+())(";
			
			assert (solve(add0).equals("4")) : "Addition is broken.";
			assert (solve(sub0).equals("1")) : "Subtraction is broken.";
			assert (solve(mult0).equals("6")) : "Multiplication is broken.";
			assert (solve(div0).equals("1")) : "Division is broken.";
			assert (compute(prog0).equals(6)) : "Computation is broken.";
			assert (compute(prog1).equals("Couldn't solve the operation.")) : "Program is broken.";
			assert (compute(prog2).equals("The program is unbalanced.")) : "Program is broken.";
		} catch ( Exception e ) {
			System.out.println( "It doesn't work!" );
		}
		System.out.println( "It works!" );
	}

}
