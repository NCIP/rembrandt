package gov.nih.nci.nautilus.parser;

import java.util.*;
import gov.nih.nci.nautilus.query.*;
/**
 * @author Prashant Shah
 * @version Sept 26, 2004
 */
public class Parser {
    
    private Vector allTokens = null;
    private int currentIndex = 0;
    
    public Stack stack = new Stack();

    /**
     * @param text The string to be recognized.
     */
    public Parser(Vector tokenOfVectors) {
		allTokens = tokenOfVectors;
        if (allTokens.size() < 1) 
        	error("Please select a query to execute");
    }
    
    public CompoundQuery getCompundQuery() {
    	return (CompoundQuery) stack.pop();
    }

    /**
     * expression()
     */
    public boolean expression() {
        if (!term1())
            return false;
        while (orOperator()) {
            if (!term1())
                error("Error in expression after 'OR' operator");
            buildCompoundQuery(2, 1, 3);
        }
        return true;
    }

    /**
     */
    private boolean term1() {
        if (!term2())
            return false;
        while (andOperator()) {
            if (!term2())
                error("No term after 'AND' operator");
            buildCompoundQuery(2, 1, 3);
        }

        return true;
    }

	private boolean term2() {
		if (!factor())
			return false;

		while (notOperator()) {
			if (!factor())
				error("No term after 'NOT' operator");
			buildCompoundQuery(2, 1, 3);
		}
		return true;
	}
    /**
     */
    private boolean factor() {
        if (isQueriable())
            return true;
        if (symbol("(")) {
            stack.pop();
            if (!expression())
                error("Error in parenthesized expression");
            if (!symbol(")"))
                error("Unclosed parenthetical expression");
            stack.pop();
            return true;
        }
        return false;
    }

    /**
     */
    private boolean orOperator() {
        return symbol("OR");
    }

    /**
     */
    private boolean andOperator() {
        return symbol("AND");
    }

	/**
	 */
	private boolean notOperator() {
		return symbol("NOT");
	}

    /**
     */
    private boolean isQueriable() {

		if (currentIndex < allTokens.size()) {
			
			Object thisToken = allTokens.elementAt(currentIndex);
			if (thisToken instanceof Queriable) {
			
				System.out.println("Query Token "+ ((Query) thisToken).getQueryName());
				stack.push(thisToken);
				currentIndex += 1;
				return true;
			}
		}
		return false;
    }

    /**
     * Tests whether the next token is the expected symbol. If it is,
     * the token is consumed, otherwise it is not.
     * 
     * @return <code>true</code> if the next token is the expected symbol.
     */
    private boolean symbol(String expectedSymbol) {
        return nextTokenMatches(expectedSymbol);
    }

    /**
     * If the next Token has the expected type, it is used as the
     * value of a new (childless) CompundQuery node, and that node
     * is then pushed onto the stack. If the next Token does not
     * have the expected type, this method effectively does nothing.
     * 
     * @param type The expected type of the next token.
     * @return <code>true</code> if the next token has the expected type.
     */
    private boolean nextTokenMatches(String token) {

		if (currentIndex < allTokens.size()) {
			
			Object thisToken = allTokens.elementAt(currentIndex);
			if (thisToken instanceof String) {
			
				if (((String) thisToken).equalsIgnoreCase(token)) {
					// TO DO For Operator Types convert to correct types before pushing to stack!!
					
					if (((String) thisToken).equalsIgnoreCase("AND") || ((String) thisToken).equalsIgnoreCase("OR") || 
					((String) thisToken).equalsIgnoreCase("NOT") || ((String) thisToken).equalsIgnoreCase("PRB")) {

						OperatorType thisOperator=null;
						if (((String) thisToken).equalsIgnoreCase("AND")) thisOperator = OperatorType.AND;
						else if (((String) thisToken).equalsIgnoreCase("OR")) thisOperator = OperatorType.OR;
						else if (((String) thisToken).equalsIgnoreCase("NOT")) thisOperator = OperatorType.NOT;
						else if (((String) thisToken).equalsIgnoreCase("PRB")) thisOperator = OperatorType.PROJECT_RESULTS_BY;
						stack.push(thisOperator);
					}else {		
						stack.push(thisToken);
					}
					currentIndex += 1;
					return true;
				}
			}
		}
		return false;
    }

    /**
     * Replaces the top three objects on the stack with a CompundQuery.
     * The three elements are numbered in the order they were put on the
     * stack.<br>
     * <b>Example:</b> If elements had been put onto the stack in the
     * order x, y, z, then 1 refers to x, 2 to y, and 3 to z. The call
     * <code>buildCompoundQuery(2, 1, 3)</code> would result in y at the root,
     * x as the left child, and z as the right child.
     * 
     * @param rootIndex Which of the three stack elements (1 = leftmost element,
     *             2 = middle element, 3 = rightmost element) to use as the
     *             value of the root of the CompundQuery.
     * @param leftIndex Which of the three stack elements to use as the left
     *             subtree of the CompundQuery.
     * @param rightIndex Which of the three stack elements to use as the right
     *              subtree of the CompundQuery.
     */
    private void buildCompoundQuery(int rootIndex, int leftIndex, int rightIndex) {
               
        OperatorType rootValue = (OperatorType) getStackItem(rootIndex);
        Queriable leftChild = (Queriable) getStackItem(leftIndex);
        Queriable rightChild = (Queriable) getStackItem(rightIndex);
        
        
        stack.pop();
        stack.pop();
        stack.pop();
        
        stack.push(new CompoundQuery(rootValue, leftChild, rightChild));
    }
    
    /**
     * 
     * @param n Which of the top three elements of the stack, counting
     *          3 as the top element, to return.
     * @return One of the top three elements of the stack.
     */
    private Object getStackItem(int n) {
        return (Object) stack.get(stack.size() - 4 + n);
    }

    /**
     * Utility routine to throw a <code>LogoParseException</code> with the
     * given message.
     * @param message The text to put in the <code>LogoParseException</code>.
     */
    private void error(String message) {
        System.out.println(message);;
    }
}
