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
