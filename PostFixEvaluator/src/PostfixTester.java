
import java.util.EmptyStackException;
import java.util.Scanner;
import java.lang.NumberFormatException;

/**
 * Demonstrates the use of a stack to evaluate post fix expressions.
 * pg41
 * @author Jake Tran
 */

public class PostfixTester 
{

	/**
	 * Reads and evaluates multiple post fix expressions.
	 */
	public static void main(String[] args) 
	{
		String expression, again;
		int result;
		
		Scanner in = new Scanner(System.in);
		
		// Intro text
		System.out.println("Postfix Expression Evaluator = J. Tran");
		System.out.println("Enter a valid post-fix expression one token at a time with"
				+ "a space between each token (e.g 5 4 + 3 2 1 - + *)");
		System.out.println("Each token must be an integer or an operator (+,-,*,/,%,^, etc.)");
		
		do
		{
			PostfixEvaluator evaluator = new PostfixEvaluator();
			
			System.out.print("Postfix expression: ");
			expression = in.nextLine();
			
			try
			{
				result = evaluator.evaluate(expression);
				System.out.println("Result: " + result);
			}
			catch (NumberFormatException e)
			{
				System.out.println("Error: Please enter only numbers and operators");
			}
			catch (TooManyOperandsException e)
			{
				System.out.println("Error: Too many operands exception");
			}
			catch (EmptyStackException e) // Catches if there aren't enough operands, popping from empty stack 
			{
				System.out.println();
				System.out.println("Error: Insufficient operands for " + evaluator.getToken());
			}
			
			System.out.print("Evaluate another expression [Y/N]? ");
			again = in.nextLine();
			System.out.println();
		}
		while (again.equalsIgnoreCase("y"));

		System.out.println("Thank you!");
	}
	
	

}
