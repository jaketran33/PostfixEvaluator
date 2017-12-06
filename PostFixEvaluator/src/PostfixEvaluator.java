


import java.util.Stack;
import java.util.Scanner;
import java.util.EmptyStackException;

/**
 * Represents an integer evaluator of postfix expressions. Assumes
 * the operands are constants
 * @author Jake Tran
 */

public class PostfixEvaluator
{
	private final static char ADD = '+';
	private final static char SUBTRACT = '-';
	private final static char MULTIPLY = '*';
	private final static char DIVIDE = '/';
	private final static char MODULUS = '%';
	private final static char POWER = '^';
	private final static char UNARY_MINUS = '~';
	private final static char FACTORIAL = '!';
	private final static char GREATER_THAN ='>';
	private final static char LESS_THAN = '<';
	private final static char EQUAL = '=';
	private final static char AND = '&';
	private final static char OR = '|';
	private final static char TERTIARY = '?';
	
	private Stack<Integer> stack;
	private String token;
	
	/**
	 * Sets up this evaluator by creating a new stack
	 */
	public PostfixEvaluator()
	{
		stack = new Stack<Integer>();
	}
	
	/**
	 * Evaluates the specified postfix expression. If an operand is encountered, it is pushed onto the stack.
	 * If an operator is encountered, two operands are popped, the operation is evaluated,
	 * and the result is pushed onto the stack.
	 * @param expr string representation of a postfix expression
	 * @return value of the given expression
	 */
	public int evaluate(String expr) throws TooManyOperandsException, EmptyStackException
	{
		int op1, op2, op3, result = 0;
		Scanner parser = new Scanner(expr);
		while (parser.hasNext())
		{
			token = parser.next();
			
			if (isOperator(token))
			{
				op2 = (stack.pop()).intValue();
				op1 = (stack.pop()).intValue();
				result = evaluateOperator(token.charAt(0), op1, op2, 0);
				stack.push(new Integer(result));
			}
			else if (isUnaryOperator(token))
			{
				op2 = 0;
				op1 = (stack.pop()).intValue();
				result = evaluateOperator(token.charAt(0), op1, op2, 0);
				stack.push(new Integer(result));
			}
			else if (isTertiaryOperator(token))
			{
				op3 = (stack.pop()).intValue();
				op2 = (stack.pop()).intValue();
				op1 = (stack.pop()).intValue();
				result = evaluateOperator(token.charAt(0), op1, op2, op3);
				stack.push(new Integer(result));
			}
			else
				stack.push(new Integer(Integer.parseInt(token)));
			
		}
		
		if (stack.size() > 1)
		{
			throw new TooManyOperandsException();
		}
		
		return result;
	}
	
	/**
	 * Determines if the specified token is an operator.
	 * @param token is the token to be evaluated
	 * @return true if token is operator
	 */
	private boolean isOperator(String token)
	{
		return ( token.equals("+") || token.equals("-") ||
				token.equals("*") || token.equals("/") ||
				token.equals("%") || token.equals("^") ||
				token.equals(">") || token.equals("<") ||
				token.equals("=") || token.equals("&") ||
				token.equals("|") );
	}
	
	/**
	 * Determines if the specified token is a unary operator.
	 * @param token is the token to be evaluated
	 * @return true if token is a unary operator
	 */
	private boolean isUnaryOperator(String token)
	{
		return ( token.equals("~") || token.equals("!"));
	}
	
	private boolean isTertiaryOperator(String token)
	{
		return token.equals("?");
	}
	
	/**
	 * Performs integer evaluation on a single expression consisting of the specified operators and operands.
	 * @param operation to be performed
	 * @param op1 the first operand
	 * @param op2 the second operand
	 * @return value of the expression
	 */
	private int evaluateOperator(char operation, int op1, int op2, int op3)
	{
		int result = 0;
		switch (operation)
		{
		case ADD:
			result = op1 + op2;
			break;
		case SUBTRACT:
			result = op1 - op2;
			break;
		case MULTIPLY:
			result = op1 * op2;
			break;
		case DIVIDE:
			result = op1 / op2;
			break;
		case MODULUS:
			result = op1 % op2;
			break;
		case POWER:
			result = (int) Math.pow((double)op1, (double)op2);
			break;
		case UNARY_MINUS:
			result = op1 * (-1);
			break;
		case FACTORIAL:
			result = factorial(op1);
			break;
		case GREATER_THAN:
			result = evalRelationalOp(operation, op1, op2);
			break;
		case LESS_THAN:
			result = evalRelationalOp(operation, op1, op2);
			break;
		case EQUAL:
			result = evalRelationalOp(operation, op1, op2);
			break;
		case AND:
			result = evalBoolOp(operation, op1, op2);
			break;
		case OR:
			result = evalBoolOp(operation, op1, op2);
			break;
		case TERTIARY:
			result = evalTertiaryOp(operation, op1, op2, op3);
			break;
		}
		return result;
	}
	
	/**
	 * Returns the factorial of the number
	 * @param number to be factored
	 * @return result of factorial
	 */
	private int factorial(int n)
	{
		if (n == 0) {
            return 1;
        }
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
	}
	
	/**
	 * Takes two operands and evaluates them with the given boolean operator.
	 * @param operator
	 * @param op1
	 * @param op2
	 * @return 1 if boolean expression is true, 0 otherwise
	 */
	private int evalRelationalOp(char operation, int op1, int op2)
	{
		boolean result;
		if (operation == '=')
		{
			result = op1 == op2;
		} else if (operation == '>') {
			result = op1 > op2;
		} else {
			result = op1 < op2;
		}
		
		if (result == true)
			return 1;
		else
			return 0;
	}
	
	/**
	 * Evaluates two operands based on the boolean operator given.
	 * @param operation
	 * @param op1
	 * @param op2
	 * @return 1 if boolean expression is true, 0 otherwise.
	 */
	private int evalBoolOp(char operation, int op1, int op2)
	{
		boolean result = false;
		if (operation == '&') {
			if ((op1 != 0) && (op2 != 0)) {
				result = true;
			}
		} else if (operation == '|') {
			if ((op1 != 0) || (op2 != 0)) {
				result = true;
			}
		} else {
			result = false;
		}
		
		if (result == true)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Evaluates a tertiary operation.
	 * @param operation
	 * @param op1
	 * @param op2
	 * @param op3
	 * @return if op1 doesn't equal zero, return op2, otherwise return op3.
	 */
	private int evalTertiaryOp(char operation, int op1, int op2, int op3)
	{
		int result = 0;
		if (op1 != 0)
			result = op2;
		else
			result = op3;
		
		return result;
	}
	
	public String getToken(){ return token; }
	
}

















