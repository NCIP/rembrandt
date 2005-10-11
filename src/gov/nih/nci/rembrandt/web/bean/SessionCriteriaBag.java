package gov.nih.nci.rembrandt.web.bean;

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

import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Criteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SNPCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author sahnih
 *
 */
public class SessionCriteriaBag implements Serializable {
	/*
	 * This class stores the various sample, gene,  or reporter critria objects that a user
	 * have created during his or her  session.
	 * These are the criteria that the user creates and later includes with 
	 * other queries such as ClassComprision, PCA, GeneExpression etc.   
	 * Each criteria type is stored where key=user definedcriteria  name and value=some Criteria object
	 * that was created by the user.
	 *  
	 * */
	/**
	 * Criteria Types supported by the SessionCriteriaBag
	 */
	public enum CriteriaType {GeneIDCriteriaType, SNPCriteriaType, SampleCriteriaType, CloneOrProbeIDCriteriaType};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Holds the user defined name as key  and GeneIDCriteria Object as the value
	 */
	private Map geneIDCriteriaMap = new TreeMap();
	
	/**
	 *  Holds the user defined name as key   and CloneOrProbeIDCriteria as the value 
	 */
	private Map cloneOrProbeIDCriteriaMap = new TreeMap();
	
	/**
	 *  Holds the user defined name as key   and SNPCriteria as the value 
	 */
	private Map sNPCriteriaMap = new TreeMap();

	/**
	 *  Holds the user defined name as key  and Samplecriteria objects as the value 
	 */	
	private Map sampleCriteriaMap = new TreeMap();
	
	public Collection getCriteriaCollection(CriteriaType criteriaType){
		Collection myCollection = null;
		switch (criteriaType){
		case GeneIDCriteriaType:
			myCollection = geneIDCriteriaMap.values();
			break;
		case SNPCriteriaType:
			myCollection = sNPCriteriaMap.values();
			break;
		case SampleCriteriaType:
			myCollection = sampleCriteriaMap.values();
			break;
		case CloneOrProbeIDCriteriaType:
			myCollection = cloneOrProbeIDCriteriaMap.values();
			break;
		}
		return myCollection;
	}
	
	public Collection getCriteriaNames(CriteriaType criteriaType){
		Collection myCollection = null;
		switch (criteriaType){
		case GeneIDCriteriaType:
			myCollection = geneIDCriteriaMap.keySet();
			break;
		case SNPCriteriaType:
			myCollection = sNPCriteriaMap.keySet();
			break;
		case SampleCriteriaType:
			myCollection = sampleCriteriaMap.keySet();
			break;
		case CloneOrProbeIDCriteriaType:
			myCollection = cloneOrProbeIDCriteriaMap.keySet();
			break;
		}
		return myCollection;
	}
	
	@SuppressWarnings("unchecked")
	public void putCriteria(CriteriaType criteriaType, String criteriaName, Criteria criteria) {
		if (criteriaType != null && criteriaName != null && criteria != null && criteria.isEmpty() == false) {
			switch (criteriaType){
			case GeneIDCriteriaType:
				if(criteria instanceof GeneIDCriteria){
					geneIDCriteriaMap.put(criteriaName, (GeneIDCriteria) criteria);
				}
				break;
			case SNPCriteriaType:
				if(criteria instanceof SNPCriteria){
					sNPCriteriaMap.put(criteriaName, (SNPCriteria) criteria);
				}
				break;
			case SampleCriteriaType:
				if(criteria instanceof SampleCriteria){
					sampleCriteriaMap.put(criteriaName, (SampleCriteria) criteria);
				}
				break;
			case CloneOrProbeIDCriteriaType:
				if(criteria instanceof CloneOrProbeIDCriteria){
					cloneOrProbeIDCriteriaMap.put(criteriaName, (CloneOrProbeIDCriteria) criteria);
				}
				break;
			}
		}
	}
	
	public void removeCriteria (CriteriaType criteriaType, String criteriaName) {
		if (criteriaName != null) {
			switch (criteriaType){
			case GeneIDCriteriaType:
				geneIDCriteriaMap.remove(criteriaName);
				break;
			case SNPCriteriaType:
				sNPCriteriaMap.remove(criteriaName);
				break;
			case SampleCriteriaType:
				sampleCriteriaMap.remove(criteriaName);
				break;
			case CloneOrProbeIDCriteriaType:
				cloneOrProbeIDCriteriaMap.remove(criteriaName);
				break;
			}
		}
	}
	
	private Criteria getCriteria (CriteriaType criteriaType, String criteriaName) {
		if (criteriaName != null) {
			switch (criteriaType){
			case GeneIDCriteriaType:
				return (GeneIDCriteria) geneIDCriteriaMap.get(criteriaName);
			case SNPCriteriaType:
				return (SNPCriteria) sNPCriteriaMap.get(criteriaName);
			case SampleCriteriaType:
				return (SampleCriteria) sampleCriteriaMap.get(criteriaName);
			case CloneOrProbeIDCriteriaType:
				return (CloneOrProbeIDCriteria) cloneOrProbeIDCriteriaMap.get(criteriaName);
			}
		}
		return null;
	}

	public void removeAllCriterias(CriteriaType criteriaType) {
			switch (criteriaType){
			case GeneIDCriteriaType:
				geneIDCriteriaMap.clear();
				break;
			case SNPCriteriaType:
				sNPCriteriaMap.clear();
				break;
			case SampleCriteriaType:
				sampleCriteriaMap.clear();
				break;
			case CloneOrProbeIDCriteriaType:
				cloneOrProbeIDCriteriaMap.clear();
				break;
			}
	}
	public SNPCriteria getSNPCriteria(String sNPCriteriaName) {
		return (SNPCriteria) getCriteria (CriteriaType.SNPCriteriaType, sNPCriteriaName);
	}
	
	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria(String cloneOrProbeIDCriteriaName) {
		return (CloneOrProbeIDCriteria) getCriteria (CriteriaType.CloneOrProbeIDCriteriaType, cloneOrProbeIDCriteriaName);
	}
	
	public SampleCriteria getSampleCriteria(String sampleCriteriaName) {
		return (SampleCriteria) getCriteria (CriteriaType.SampleCriteriaType, sampleCriteriaName);
	}
	
	public GeneIDCriteria getGeneCriteria(String geneIDCriteriaName) {
		return  (GeneIDCriteria) getCriteria (CriteriaType.GeneIDCriteriaType, geneIDCriteriaName);
	}


}
