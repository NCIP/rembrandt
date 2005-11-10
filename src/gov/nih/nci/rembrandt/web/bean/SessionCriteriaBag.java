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
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author sahnih
 *
 */
public class SessionCriteriaBag implements Serializable {
	/*
	 * This class stores the various sample, gene,  or reporter DE objects that a user
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
	public enum ListType {GeneIdentifierSet, SNPIdentifierSet, SampleIdentifierSet, CloneProbeSetIdentifierSet};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Holds the user defined name as key  and GeneID Object as the value
	 */
	private Map<String,ArrayList<GeneIdentifierDE>> geneIDMap = new TreeMap<String,ArrayList<GeneIdentifierDE>>();
	
	/**
	 *  Holds the user defined name as key   and CloneOrProbeID as the value 
	 */
	private Map<String,ArrayList<CloneIdentifierDE>> cloneOrProbeIDMap = new TreeMap<String,ArrayList<CloneIdentifierDE>>();
	
	/**
	 *  Holds the user defined name as key   and SNP as the value 
	 */
	private Map<String,ArrayList<SNPIdentifierDE>> sNPMap = new TreeMap<String,ArrayList<SNPIdentifierDE>>();

	/**
	 *  Holds the user defined name as key  and Sample objects as the value 
	 */	
	private Map<String,ArrayList<SampleIDDE>> sampleMap = new TreeMap<String,ArrayList<SampleIDDE>>();
	
	public Collection getUserLists(ListType listType){
		Collection collection = null;
		switch (listType){
		case GeneIdentifierSet:
			collection = geneIDMap.values();
			break;
		case SNPIdentifierSet:
			collection = sNPMap.values();
			break;
		case SampleIdentifierSet:
			collection = sampleMap.values();
			break;
		case CloneProbeSetIdentifierSet:
			collection = cloneOrProbeIDMap.values();
			break;
		}
		return collection;
	}
	
	public Collection getUsetListNames(ListType listType){
		Collection myCollection = null;
		switch (listType){
		case GeneIdentifierSet:
			myCollection = geneIDMap.keySet();
			break;
		case SNPIdentifierSet:
			myCollection = sNPMap.keySet();
			break;
		case SampleIdentifierSet:
			myCollection = sampleMap.keySet();
			break;
		case CloneProbeSetIdentifierSet:
			myCollection = cloneOrProbeIDMap.keySet();
			break;
		}
		return myCollection;
	}
	
	public void putUserList(ListType listType, String listName, List listOfDEs) throws ClassCastException{
		try {
			if (listOfDEs != null && !listOfDEs.isEmpty()) {
				ArrayList arrayList = new ArrayList(listOfDEs);
				switch (listType){
				case GeneIdentifierSet:
						geneIDMap.put(listName, arrayList);
					break;
				case SNPIdentifierSet:
						sNPMap.put(listName, arrayList);
					break;
				case SampleIdentifierSet:
						sampleMap.put(listName, arrayList);
					break;
				case CloneProbeSetIdentifierSet:
						cloneOrProbeIDMap.put(listName, arrayList);
					break;
				}
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void removeUserList (ListType listType, String listName) {
		if (listName != null) {
			switch (listType){
			case GeneIdentifierSet:
				geneIDMap.remove(listName);
				break;
			case SNPIdentifierSet:
				sNPMap.remove(listName);
				break;
			case SampleIdentifierSet:
				sampleMap.remove(listName);
				break;
			case CloneProbeSetIdentifierSet:
				cloneOrProbeIDMap.remove(listName);
				break;
			}
		}
	}
	public List getUserList (ListType listType, String listName) {
		if (listName != null) {
			switch (listType){
			case GeneIdentifierSet:{
				ArrayList<GeneIdentifierDE> newList = geneIDMap.get(listName);
					if(newList != null){
						return (List) newList.clone();
					}
				}
				break;
			case SNPIdentifierSet:{
				ArrayList<SNPIdentifierDE> newList = sNPMap.get(listName);
					if(newList != null){
						return (List) newList.clone();
					}
				}
			case SampleIdentifierSet:{
					ArrayList<SampleIDDE> newList = sampleMap.get(listName);
						if(newList != null){
							return (List) newList.clone();
						}
					}
			case CloneProbeSetIdentifierSet:{
					ArrayList<CloneIdentifierDE> newList = cloneOrProbeIDMap.get(listName);
						if(newList != null){
							return (List) newList.clone();
						}
					}
			}
		}
		return null;
	}
	
	public void removeAllFromUserList(ListType listType) {
			switch (listType){
			case GeneIdentifierSet:
				geneIDMap.clear();
				break;
			case SNPIdentifierSet:
				sNPMap.clear();
				break;
			case SampleIdentifierSet:
				sampleMap.clear();
				break;
			case CloneProbeSetIdentifierSet:
				cloneOrProbeIDMap.clear();
				break;
			}
	}
	public SNPCriteria getSNPCriteria(String listName) {
		SNPCriteria criteria = new SNPCriteria();
		criteria.setSNPIdentifiers(getUserList (ListType.SNPIdentifierSet, listName));
		return criteria;
	}
	
	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria(String listName) {
		CloneOrProbeIDCriteria criteria = new CloneOrProbeIDCriteria();
		criteria.setIdentifiers(getUserList (ListType.CloneProbeSetIdentifierSet, listName));
		return criteria;
	}
	
	public SampleCriteria getSampleCriteria(String listName) {
		SampleCriteria criteria = new SampleCriteria();
		criteria.setSampleIDs(getUserList (ListType.SampleIdentifierSet, listName));
		return criteria ;
	}
	
	public GeneIDCriteria getGeneCriteria(String listName) {
		GeneIDCriteria criteria = new GeneIDCriteria();
		criteria.setGeneIdentifiers(getUserList (ListType.GeneIdentifierSet, listName));
		return criteria ;
	}


}
