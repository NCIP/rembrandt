/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.Criteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.Viewable;
import gov.nih.nci.caintegrator.exceptions.ValidationException;
import gov.nih.nci.caintegrator.query.Validatable;
import gov.nih.nci.rembrandt.queryservice.QueryHandlerInterface;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;



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

abstract public class Query implements Queriable, Serializable, Cloneable, Validatable{
	/**
	 * IMPORTANT! This class has a clone method! This requires that any new data
	 * field that is added to this class also be cloneable and be added to clone
	 * calls in the clone method.If you do not do this, you will not seperate 
	 * the references of at least one data field when we generate a copy of this
	 * object.This means that if the data field ever changes in one copy or the 
	 * other it will affect both instances... this will be hell to track down if
	 * you aren't ultra familiar with the code base, so add those methods now!
	 * 
	 * (Not necesary for primitives.)
	 */
	
    private static Logger logger = Logger.getLogger(Query.class);
    
    //This attribute required for caching mechanism
    private String sessionId = null;

	private String queryName;
	//Added so that we can still distinguish where we have added a result set
	//and that can know what result set we applied
	private String appliedResultSet;

	private Viewable associatedView;
    
    protected DiseaseOrGradeCriteria diseaseOrGradeCriteria;
    
    protected InstitutionCriteria institutionCriteria;

    protected SampleCriteria sampleIDCrit;

	public abstract QueryHandlerInterface getQueryHandler() throws Exception;
    
    public abstract QueryType getQueryType() throws Exception;

    public abstract String toString();
    
    public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
		return diseaseOrGradeCriteria;
	}

	public void setDiseaseOrGradeCrit(DiseaseOrGradeCriteria diseaseOrGradeCriteria) {
		this.diseaseOrGradeCriteria = diseaseOrGradeCriteria;
	}

	public void setDiseaseOrGradeCriteria(DiseaseOrGradeCriteria diseaseOrGradeCriteria) {
		this.diseaseOrGradeCriteria = diseaseOrGradeCriteria;
	}

	public SampleCriteria getSampleIDCrit() {
		return sampleIDCrit;
	}

	public void setSampleIDCrit(SampleCriteria sampleIDCrit) {
		this.sampleIDCrit = sampleIDCrit;
	}
	
	//TODO: The following method checks if a given Query is empty
	public boolean isEmpty() {
		try {

			String currObjectName = this.getClass().getName();
			Class currClass = Class.forName(currObjectName);
			Method[] allPublicmethods = currClass.getMethods();

			for (int i = 0; i < allPublicmethods.length; i++) {
				Method currMethod = allPublicmethods[i];
				String currMethodString = currMethod.getName();
				if ((currMethodString.toUpperCase().startsWith("GET"))
						&& (currMethod.getModifiers() == Modifier.PUBLIC)) {
					Object[] objArray = null;
					Object thisObj = currMethod.invoke(this, objArray);

					if (thisObj != null) {
						if (Criteria.class.isInstance(thisObj)) {
							Criteria thisCriteria = (Criteria) thisObj;
							if (!thisCriteria.isEmpty()) {
								return false;
							}
						}
					}
				}
			}

		} catch (Throwable e) {
			logger.error(e);
       	}
		return true;
	}


	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Viewable getAssociatedView() {
		return associatedView;
	}

	public void setAssociatedView(Viewable associatedViewObj) {
		this.associatedView = associatedViewObj;
	}

	public Query[] getAssociatiedQueries() {
		Query[] queries = { this };
		return queries;
	}
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Object clone() {
    	Query myClone = null;
    	try {
    		myClone = (Query)super.clone();
            if(associatedView != null){
                myClone.associatedView = (Viewable)associatedView.clone();
            }
            if(diseaseOrGradeCriteria != null){
                myClone.diseaseOrGradeCriteria = (DiseaseOrGradeCriteria)diseaseOrGradeCriteria.clone();
            }
            if(sampleIDCrit != null){
                myClone.sampleIDCrit = (SampleCriteria)sampleIDCrit.clone();
            }
    	}catch(CloneNotSupportedException cnse) {
        		/*
        		 * This is meaningless as it will still perform
        		 * the memcopy, and then let you know that
        		 * the object did not implement the Cloneable inteface.
        		 * Kind of a stupid implementation if you ask me...
        		 * -D Bauer
        		 */
        }
       	return myClone;
    }
	/**
	 * @return Returns the appliedResultSet.
	 */
	public String getAppliedResultSet() {
		return appliedResultSet;
	}
	/**
	 * @param appliedResultSet The appliedResultSet to set.
	 */
	public void setAppliedResultSet(String appliedResultSet) {
		this.appliedResultSet = appliedResultSet;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.query.Validatable#validate()
	 */
	public boolean validate() throws ValidationException {
		// TODO Auto-generated method stub
		return true;
	}

	public InstitutionCriteria getInstitutionCriteria() {
		return institutionCriteria;
	}

	public void setInstitutionCriteria(InstitutionCriteria institutionCriteria) {
		this.institutionCriteria = institutionCriteria;
	}

}
