/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset.copynumber;

import gov.nih.nci.caintegrator.dto.de.BioSpecimenIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.Groupable;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
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

public class CopyNumberGeneBasedResultsContainer1 implements ResultsContainer{
	public static final String NO_GENE_SYMBOL = "zzzzzzzzzzzzzz";
	protected SortedMap genes = new TreeMap();
	protected SortedMap<String,SortedSet<BioSpecimenIdentifierDE>> groupsLabels = new TreeMap<String,SortedSet<BioSpecimenIdentifierDE>>();
	protected Set allReporterNames = new HashSet();
	/**
	 * @return Returns the groupsLabels.
	 */
	public Collection<String> getGroupsLabels() {
		return  this.groupsLabels.keySet();
	}

	/**
	 * @param geneResultset Adds geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void addGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.put(geneResultset.getGeneSymbol().getValue().toString(), geneResultset);
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.addAll(geneResultset.getReporterNames());
			}
		}
		else if(geneResultset != null && geneResultset.isAnonymousGene() == true){
			genes.put(NO_GENE_SYMBOL, geneResultset);
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.addAll(geneResultset.getReporterNames());
			}
		}
	}
	/**
	 * @param geneResultset Removes geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
	public void removeGeneResultset(GeneResultset geneResultset){
		if(geneResultset != null && geneResultset.getGeneSymbol() != null){
			genes.remove(geneResultset.getGeneSymbol().toString());
			if (geneResultset.getReporterNames() != null) {
				allReporterNames.removeAll(geneResultset.getReporterNames());
			}
		}
	}
	/**
	 * @return geneResultset Returns geneResultset to this GeneExprSingleViewResultsContainer object.
	 */
    public Collection getGeneResultsets(){
    		return genes.values();
    }
    /**
     * @param geneSymbol
	 * @return geneResultset Returns geneResultset to this geneSymbol.
	 */
    public GeneResultset getGeneResultset(String geneSymbol){
    	if(geneSymbol != null){
			return (GeneResultset) genes.get(geneSymbol);
		}
        return (GeneResultset) genes.get(NO_GENE_SYMBOL);
    }
    /**
     * @param geneSymbol
	 * @return reporterResultset Returns reporterResultset for this geneSymbol.
	 */
    public Collection getRepoterResultsets(String geneSymbol){
    	if(geneSymbol != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
			return geneResultset.getReporterResultsets();
		}
    		return null;
    }
    /**
     * @param reporterName
	 * @return reporterResultset Returns reporterResultset for this geneSymbol.
	 */
    public ReporterResultset getReporterResultset(String reporterName){
    	if(reporterName != null  && allReporterNames.contains(reporterName)){
    		Collection geneResultsets = genes.values();
    		for(Object obj :geneResultsets){
    			if(obj instanceof GeneResultset){
    				GeneResultset geneResultset = (GeneResultset) obj;
    				if(geneResultset.getRepoterResultset(reporterName)!= null){
    					return geneResultset.getRepoterResultset(reporterName);
    				}
    			}
    		}
		}
    		return null;
    }
	/**
	 * @param none Removes all geneResultset in this GeneExprSingleViewResultsContainer object.
	 */
    public void removeAllGeneResultset(){
    	genes.clear();
    	allReporterNames.clear();
    }
    /**
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getGroupByResultsets(String geneSymbol,String reporterName){
    	if(geneSymbol!= null && reporterName != null){
    		GeneResultset geneResultset = (GeneResultset) genes.get(geneSymbol);
    		ReporterResultset reporterResultset = (ReporterResultset) geneResultset.getRepoterResultset(reporterName);
			return reporterResultset.getGroupByResultsets();
		}
    		return null;
    }
    /**
     * @param geneSymbol,reporterName
	 * @return groupResultset Returns groupResultset for this reporterName & geneSymbol.
	 */
    public Collection getFilteredGroupByResultsets(String[] groupLabels, boolean isShow){
    	Collection geneResults = new ArrayList();
    	
    	Collection geneCollection = genes.values();
    	if(isShow){ //get all geneResultant objects that are in the collection
    		for (Iterator geneIterator = geneCollection.iterator(); geneIterator.hasNext();) {
 	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		Collection reporters = geneResultset.getReporterResultsets();
    		
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		for (int i = 0; i < groupLabels.length ; i++) {
	    	        	String label = (String) groupLabels[i];
	    	        	Groupable groupableResultset = reporterResultset.getGroupByResultset(label);
	    	        	if(groupableResultset != null){
	    	        		geneResults.add(geneResultset);
	    	        	}
	        		}
	    		}
    		}
	    	  
    	}
       	else{//return everything besides the ones that are in the collection
    		geneResults.addAll(geneCollection);
       		for (Iterator geneIterator = geneCollection.iterator(); geneIterator.hasNext();) {
 	    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
	    		Collection reporters = geneResultset.getReporterResultsets();
    		
	    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
	        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
	        		for (int i = 0; i < groupLabels.length ; i++) {
	    	        	String label = (String) groupLabels[i];
	    	        	Groupable groupableResultset = reporterResultset.getGroupByResultset(label);
	    	        	if(groupableResultset != null){
	    	        		geneResults.remove(geneResultset);
	    	        	}
	        		}
	    		}
    		}
    	}
    	return geneResults;
    }    
	/**
	 * @param diseaseType
	 */
	public void addDiseaseTypes(String diseaseType) {
		groupsLabels.put(diseaseType,null);
		
	}
	//getPaginatedGeneResultsets(int start, int count) – returns gene resultant from start to start+count
    public Collection  getPaginatedGeneResultsets(int start, int count){
    	Set keys = genes.keySet();
    	String[] geneNames = (String[]) keys.toArray(new String[genes.size()]);
    	Collection geneResults = new ArrayList();
    	int size = count;
    	if( geneNames.length < count) {
    		size = geneNames.length;
    	}
    	for (int i = start; i < size; i++ ){
    		geneResults.add(genes.get(geneNames[i]));
    	}
    	return geneResults;
    }
//  getFilteredGeneResultsets( GeneIdentifierDE.GeneSymbol[] geneSymbols, boolean isShow ) – returns gene resultant Select to show/hide genes listed
    public Collection  getFilteredGeneResultsets( GeneIdentifierDE.GeneSymbol[] geneSymbols, boolean isShow ){
    	Collection geneResults = new ArrayList();
    	if(isShow){ //get all geneResultant objects that are in the collection
	    	for (int i = 0; i < geneSymbols.length; i++ ){
	    		String geneSymbol = geneSymbols[i].getValue().toString();
	    		if(genes.containsKey(geneSymbol)){
	    			geneResults.add(genes.get(geneSymbol));
	    		}
	    	}
    	}
       	else{//return everything besides the ones that are in the collection
       		Set symbols = genes.keySet();
	    	for (int i = 0; i < geneSymbols.length; i++ ){
	    		String geneSymbol = geneSymbols[i].getValue().toString();
	    		if(genes.containsKey(geneSymbol)){
	    			symbols.remove((String) genes.get(geneSymbol));
	    		}
	    	}
	    	Collection geneKeys = new ArrayList();
	    	for(Iterator symbolsIterator = symbols.iterator(); symbolsIterator.hasNext();){
	    		String symbol = (String) symbolsIterator.next();
	    		geneKeys.add(new GeneIdentifierDE.GeneSymbol(symbol));
	    	}
	    	geneResults = getFilteredGeneResultsets((GeneIdentifierDE.GeneSymbol[])geneKeys.toArray(new GeneIdentifierDE.GeneSymbol[geneKeys.size()]),true);
    	}
    	return geneResults;
    }
	/**
	 * @return Returns the allReporterNames.
	 */
	public List getAllReporterNames() {
		List list = new ArrayList();
		list.addAll(allReporterNames);
		return list;
	}

	/**
	 * @param groupsLabels The groupsLabels to set.
	 */
	public void addBiospecimensToGroups(String groupLabel, BioSpecimenIdentifierDE biospecimenId) {
		if(groupLabel == null){
			groupLabel = RembrandtConstants.UNASSIGNED;
		}
		if(biospecimenId != null){
			SortedSet<BioSpecimenIdentifierDE> biospecimenLabels = null;
			if(groupsLabels.containsKey(groupLabel)){
				biospecimenLabels =  (SortedSet) groupsLabels.get(groupLabel);
			}
			else { ///key does not exsist
				biospecimenLabels = new TreeSet<BioSpecimenIdentifierDE>();			
			}
			biospecimenLabels.add(biospecimenId);
			groupsLabels.put(groupLabel,biospecimenLabels);
		}
	}
}
