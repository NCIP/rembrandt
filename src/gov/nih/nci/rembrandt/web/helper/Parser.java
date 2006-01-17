package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.dto.query.OperatorType;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.Queriable;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.util.Stack;
import java.util.Vector;

import org.apache.log4j.Logger;
/**
 * @author Prashant Shah
 * @version Sept 26, 2004
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
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
