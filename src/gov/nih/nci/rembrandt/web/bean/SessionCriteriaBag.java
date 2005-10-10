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
	
	public Collection getSNPCriteriaCollection(){
		return sNPCriteriaMap.values();
	}	
	
	public Collection getSNPCriteriaNames() {
		return sNPCriteriaMap.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public void putSNPCriteria(String snpCriteriaName, SNPCriteria sNPCriteria) {
		if (snpCriteriaName != null && sNPCriteria != null && sNPCriteria.isEmpty() == false) {
			sNPCriteriaMap.put(snpCriteriaName, sNPCriteria);
		}
	}
	
	public void removeSNPCriteria(String sNPCriteriaName) {
		if (sNPCriteriaName != null) {
			sNPCriteriaMap.remove(sNPCriteriaName);
		}
	}
	
	public SNPCriteria getSNP(String snpCriteriaName) {
		if (snpCriteriaName != null) {
			return (SNPCriteria) sNPCriteriaMap.get(snpCriteriaName);
		}
		return null;
	}
	public void removeAllSNPCriterias() {
		sNPCriteriaMap.clear();
	}
	public Collection getCloneOrProbeIDCriteriaCollection() {
		return cloneOrProbeIDCriteriaMap.values();
	}
	
	public Collection getCloneOrProbeIDCriteriaNames() {
		return cloneOrProbeIDCriteriaMap.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public void putCloneOrProbeIDCriteria(String cloneOrProbeIDCriteriaName, CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
		if (cloneOrProbeIDCriteriaName != null && cloneOrProbeIDCriteria != null && cloneOrProbeIDCriteria.isEmpty() == false) {
			cloneOrProbeIDCriteriaMap.put(cloneOrProbeIDCriteriaName, cloneOrProbeIDCriteria);
		}
	}
	
	public void removeCloneOrProbeIDCriteria(String cloneOrProbeIDCriteria) {
		if (cloneOrProbeIDCriteria != null) {
			cloneOrProbeIDCriteriaMap.remove(cloneOrProbeIDCriteria);
		}
	}
	
	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria(String cloneOrProbeIDCriteriaName) {
		if (cloneOrProbeIDCriteriaName != null) {
			return (CloneOrProbeIDCriteria) cloneOrProbeIDCriteriaMap.get(cloneOrProbeIDCriteriaName);
		}
		return null;
	}
	
	public void removeAllCloneOrProbeIDCriterias() {
		cloneOrProbeIDCriteriaMap.clear();
	}
	
	public Collection getSampleCriteriaCollection() {
		return sampleCriteriaMap.values();
	}
	
	public Collection getSampleCriteriaNames() {
		return sampleCriteriaMap.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public void putSampleCriteria(String sampleCriteriaName, SampleCriteria sampleCriteria) {
		if (sampleCriteriaName != null && sampleCriteria != null && sampleCriteria.isEmpty() == false) {
			sampleCriteriaMap.put(sampleCriteriaName, sampleCriteria);
		}
	}
	
	public SampleCriteria getSampleCriteria(String sampleCriteriaName) {
		if (sampleCriteriaName != null) {
			return (SampleCriteria) sampleCriteriaMap.get(sampleCriteriaName);
		}
		return null;
	}
	public void removeSampleCriteria(String sampleCriteriaName) {
		if (sampleCriteriaName != null) {
			sampleCriteriaMap.remove(sampleCriteriaName);
		}
	}
	
	public Collection getGeneIDCriteriaCollection() {
		return geneIDCriteriaMap.values();
	}

	public Collection getGeneIDCriteriaNames() {
		return geneIDCriteriaMap.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public void putGeneIDCriteria(String geneIDCriteriaName, GeneIDCriteria geneIDCriteria) {
		if (geneIDCriteriaName != null && geneIDCriteria != null && geneIDCriteria.isEmpty() == false) {
			geneIDCriteriaMap.put(geneIDCriteriaName, geneIDCriteria);
		}
	}
	
	public GeneIDCriteria getGeneCriteria(String geneIDCriteriaName) {
		if (geneIDCriteriaName != null) {
			return (GeneIDCriteria) geneIDCriteriaMap.get(geneIDCriteriaName);
		}
		return null;
	}
	public void removeGeneCriteria(String geneIDCriteriaName) {
		if (geneIDCriteriaName != null) {
			geneIDCriteriaMap.remove(geneIDCriteriaName);
		}
	}

}
