

import java.util.Stack;

/**
 * Throws an exception if a post-fix evaluator's stack ends up with more than one element.
 * Expression had too many operands.
 * @author Jake Tran
 *
 */


public class TooManyOperandsException extends Exception
{	
	public TooManyOperandsException() {
		super();
	}
}
