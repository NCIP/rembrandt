package gov.nih.nci.rembrandt.web.bean;


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
	
	public Collection getUserListNames(ListType listType){
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
