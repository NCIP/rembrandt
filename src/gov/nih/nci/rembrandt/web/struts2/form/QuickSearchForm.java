/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;
import gov.nih.nci.rembrandt.util.MoreStringUtils;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import gov.nih.nci.rembrandt.web.bean.LabelValueBean;




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

public class QuickSearchForm extends BaseForm implements GeneValidator{
	private String plot = null;
    private AllGeneAliasLookup[] allGeneAlias;
    private String quickSearchName = null;
	private String quickSearchType = null;
	private static Logger logger = Logger.getLogger(QuickSearchForm.class);
	
	private String groupName = null;
	private String groupNameCompare = null;
	private String baselineGroup = null;
	
	//private Collection<LabelValueBean> sampleGroupsList;
	private HashMap<String, String> sampleGroupsList;
	
	private List<String> quickSearchTypes = new ArrayList<String>();
	
	//public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	//TODO: Shan This needs to be called prior or during homepage loading
	public void reset(HttpServletRequest request) {
		GroupRetriever groupRetriever = new GroupRetriever();
		
		List<LabelValueBean> al = groupRetriever.getClinicalGroupsCollectionNoPath(request.getSession());
//		al.add(new LabelValueBean("all", ""));
		//al.addAll(groupRetriever.getClinicalGroupsCollectionNoPath(request.getSession()));
		
		//specifically remove only these values, not to effect the groupRetriever
		LabelValueBean tmp = new LabelValueBean("UNKNOWN", "UNKNOWN");
		al.remove(tmp);
		tmp = new LabelValueBean("ALL", "ALL");
		al.remove(tmp);
		tmp = new LabelValueBean("NON_TUMOR", "NON_TUMOR");
		al.remove(tmp);
		
		//struts1 to 2 migration
		sampleGroupsList = convertToHashMap(al);
	}
	
	protected HashMap<String, String> convertToHashMap(List<LabelValueBean> al) {
		HashMap<String, String> aMap = new HashMap<String, String>();
		for (LabelValueBean bean : al) {
			aMap.put(bean.getLabel(), bean.getValue());
		}
		
		return aMap;
	}
	
	public String getPlot() {
		return plot;
	}

	public void setPlot(String str) {
		plot = str;
	}

	public void setQuickSearchName(String str) {	    
	    String[] quickSearchNameStrings = str.split(":");
	    str = quickSearchNameStrings[0];
		this.quickSearchName = str.trim();
	}

	public String getQuickSearchName() {
		return this.quickSearchName;
	}

	public void setQuickSearchType(String str) {
		this.quickSearchType = str;
	}

	public String getQuickSearchType() {
		return this.quickSearchType;
	}
	public void setAllGeneAlias(AllGeneAliasLookup[] allGeneAlias){
	    this.allGeneAlias = allGeneAlias;
	    
	}
	public AllGeneAliasLookup[] getAllGeneAlias(){
	    return this.allGeneAlias;
	}
	
	//TODO: This needs be moved to action class or somewhere
	public List<String> validate(ActionMapping mapping,
			HttpServletRequest request) {
	    
		//ActionErrors errors = new ActionErrors();
		List<String> errors = new ArrayList<String>();
	    
		if(getPlot() != null && !getPlot().equals(CaIntegratorConstants.SAMPLE_KMPLOT) 
				&& getQuickSearchType() != null  &&
	    		getQuickSearchType().compareTo(RembrandtConstants.GENE_SYMBOL)==0){
		    
			errors = UIFormValidator.validateGeneSymbolisNotEmpty(quickSearchName, errors);
		    
		    if( getQuickSearchName() != null )
				setQuickSearchName(MoreStringUtils.cleanJavascriptAndSpecialChars(MoreStringUtils.specialCharacters, getQuickSearchName()));
		    
			try {
				errors = UIFormValidator.validateGeneSymbol(this, errors);
			} catch (Exception e) {
				logger.error(e);
			}
	    }
		return errors;

	}

    /* (non-Javadoc)
     * @see gov.nih.nci.nautilus.ui.struts.form.GeneValidator#setGeneSymbol(java.lang.String)
     */
    public void setGeneSymbol(String geneSymbol) {
        if(geneSymbol != null){
            geneSymbol = geneSymbol.trim();
        }
       this.quickSearchName = geneSymbol;
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.nautilus.ui.struts.form.GeneValidator#getGeneSymbol()
     */
    public String getGeneSymbol() {
        return this.quickSearchName;
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNameCompare() {
		return groupNameCompare;
	}

	public void setGroupNameCompare(String groupNameCompare) {
		this.groupNameCompare = groupNameCompare;
	}

	public HashMap<String, String> getSampleGroupsList() {
		return sampleGroupsList;
	}

	public void setSampleGroupsList(HashMap<String, String> sampleGroupsList) {
		this.sampleGroupsList = sampleGroupsList;
	}

	/*
	public Collection getSampleGroupsList() {
		return sampleGroupsList;
	}

	public void setSampleGroupsList(Collection sampleGroupsList) {
		this.sampleGroupsList = sampleGroupsList;
	}
*/
	public String getBaselineGroup() {
		return baselineGroup;
	}

	public void setBaselineGroup(String baselineGroup) {
		this.baselineGroup = baselineGroup;
	}
	
	public HashMap<String, String> getSampleGroupListWithExtra() {
		if (this.sampleGroupsList == null)
			return new HashMap<String, String>();
		
		this.sampleGroupsList.put("None", "none");
		this.sampleGroupsList.put("Rest of the Gliomas", "Rest of the Gliomas");
		
		return sampleGroupsList;
		
	}
	
	public List<String> getQuickSearchTypes() {
		if (this.quickSearchTypes.size() == 0)
			this.quickSearchTypes.add("Gene Keyword");
		
		return quickSearchTypes;
	}
}
