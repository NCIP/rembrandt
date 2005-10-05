package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.caintegrator.dto.query.Queriable;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.util.Stack;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 * @author Prashant Shah
 * @version Sept 26, 2004
 */
public class Parser {
    
    private static Logger logger = Logger.getLogger(RembrandtConstants.LOGGER);
    private Vector allTokens = null;
    private int currentIndex = 0;
    
    public Stack stack = new Stack();

    /**
     * @param text The string to be recognized.
     */
    public Parser(Vector tokenOfVectors) throws Exception {
		allTokens = tokenOfVectors;
        if (allTokens.size() < 1) 
			throw new Exception ( "Please input a query to execute");
    }
    
    public CompoundQuery getCompoundQuery() throws Exception {
    	
		CompoundQuery thisCompoundQuery=null;
		try {
			Object stackObj = stack.peek();
			if (stackObj instanceof CompoundQuery) {
				thisCompoundQuery = (CompoundQuery) stack.pop();
			}else
			throw new Exception("No Compound Query exists");
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
    	
    	return thisCompoundQuery;
    }

    /**
     * expression()
     */
    public boolean expression()throws Exception {
        if (!term1())
            return false;
        while (prbOperator()) {
            if (!term1())
				throw new Exception ( "Error in expression after 'Project Results By' operator.  Need a query after 'Project Results By' operator");
            buildCompoundQuery(2, 1, 3);
        }
        return true;
    }

	private boolean term1() throws Exception {
		if (!term2())
			return false;
		while (orOperator()) {
			if (!term1())
				throw new Exception ( "Error in expression after 'OR' operator.  Need a query after 'OR' operator");
			buildCompoundQuery(2, 1, 3);
		}

		return true;
	}

    /**
     */
    private boolean term2() throws Exception {
        if (!term3())
            return false;
        while (andOperator()) {
            if (!term3())
				throw new Exception ( "Error in expression after 'AND' operator.  Need a query after 'AND' operator");
            buildCompoundQuery(2, 1, 3);
        }

        return true;
    }

	private boolean term3()throws Exception {
		if (!factor())
			return false;

		while (notOperator()) {
			if (!factor())
				throw new Exception ( "Error in expression after 'NOT' operator.  Need a query after 'NOT' operator");
			buildCompoundQuery(2, 1, 3);
		}
		return true;
	}
    /**
     */
    private boolean factor() throws Exception {
        if (isQueriable())
            return true;
        if (symbol("(")) {
            stack.pop();
            if (!expression())
				throw new Exception ( "Error in parenthesized expression !!");
            if (!symbol(")"))
				throw new Exception ( "Unclosed parenthetical expression !!");
            stack.pop();
            return true;
        }
        return false;
    }

	/**
	 */
	private boolean prbOperator() {
		return symbol("PRB");
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
			
			    logger.debug("Query Token "+ ((Query) thisToken).getQueryName());
				stack.push(thisToken);
				currentIndex += 1;
				return true;
			}
		}
		return false;
    }

    /**
     */
    private boolean symbol(String expectedSymbol) {
        return nextTokenMatches(expectedSymbol);
    }

    /**
     */
    private boolean nextTokenMatches(String token) {

		if (currentIndex < allTokens.size()) {
			
			Object thisToken = allTokens.elementAt(currentIndex);
			if (thisToken instanceof String) {
			
				if (((String) thisToken).equalsIgnoreCase(token)) {
					// For Operator Types convert to correct types before pushing to stack!!
					
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
     */
    private void buildCompoundQuery(int rootIndex, int leftIndex, int rightIndex) throws Exception{
               
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

}
