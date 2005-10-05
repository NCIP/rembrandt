/*
 *  @author: SahniH
 *  Created on Sep 24, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
package gov.nih.nci.caintegrator.dto.query;

import java.io.Serializable;


/**
 * @author SahniH,BauerD
 * Date: Sep 24, 2004
 * 
 */
public class OperatorType implements Serializable,Cloneable{
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
    private String operatorType;
    public final static Intersection AND = new Intersection();
    public final static Union OR = new Union ();
    public final static Difference NOT = new Difference ();
    public final static ProjectResultsBy PROJECT_RESULTS_BY = new ProjectResultsBy ();
    
    private OperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
    public final static class Intersection extends OperatorType {
    	Intersection () {
             super("AND");
         }
    }
    public final static class Union extends OperatorType {
    	Union () {
            super("OR");
        }
    }
    public final static class Difference extends OperatorType {
    	Difference () {
            super("NOT");
        }
    }
    public final static class ProjectResultsBy extends OperatorType {
    	ProjectResultsBy () {
            super("PROJECT_RESULTS_BY");
        }
    }
	/**
	 * @return Returns the operatorType.
	 */
	public String getOperatorType() {
		return this.operatorType;
	}
	/**
	 * @param operatorType The operatorType to set.
	 */
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		OperatorType myClone = null;
		try {
			myClone = (OperatorType)super.clone();
		} catch (CloneNotSupportedException e) {
			//This will never happen
		}
		return myClone;
	}
    
    public boolean equals(Object obj){
        boolean returnType = false;
        if(obj instanceof OperatorType){
            if(obj instanceof Intersection && this instanceof Intersection){
                returnType = true;
            }
            else if(obj instanceof ProjectResultsBy && this instanceof ProjectResultsBy){
                returnType = true;
            }
            else if(obj instanceof Union && this instanceof Union){
                returnType = true;
            }
            else if(obj instanceof Difference && this instanceof Difference){
                returnType = true;
            }
        }
        return returnType;
    }
}
