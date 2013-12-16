/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.web.bean.ReportBean;

import java.util.HashMap;

//import org.apache.struts.action.ActionErrors;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionMessage;

/**
 * @author bauerd
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

public class ReportGeneratorForm extends BaseForm {
	
	private String queryName = "";
	private String prbQueryName = "";
	private ReportBean reportBean;
    private CompoundQuery requestQuery;
    private String resultSetName = "";
    private String xsltFileName = "";
    //General Filter values to be used by the XSLT for pagenation and other
    //general XSL functions for the report
    private HashMap<String,String> filterParams = new HashMap<String,String>();
    private String filter_value1 = "";
    private String filter_value2 = "";
    private String filter_value3 = "";
    private String filter_value4 = "";
    private String filter_value5 = "";
    private String filter_value6 = "";
    //this is used to specify the particular element that we will be filtering on
    private String filter_element = "";
    //this is the type of filter that will be applied
    private String filter_type = "";
    //this is the string of values that will be used by the filter_type
    //it needs to be parsed so that it can be later used by the filter
    private String filter_string = "";
    //this is the list of sampleIds that the user can select from the report page
    private String[] samples;
    
    private String showSampleSelect = "";
    
    private String allowShowAllValues = "true";
    private String showAllValues = "";
    
    private String queryDetails = "";
        
    private String reportView = "";


	/**
	 * 
	 * @return Returns the query.
	 */
	public CompoundQuery getRequestQuery() {
		return requestQuery;
	}
	
    /**
	 * @param query The query to set.
	 */
	public void setRequestQuery(CompoundQuery query) {
		this.requestQuery = query;
	}
	/**
	 * @return Returns the reportBean.
	 */
	public ReportBean getReportBean() {
		return reportBean;
	}
	/**
	 * @param reportBean The reportBean to set.
	 */
	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}
	/**
	 * @return Returns the queryName.
	 */
	public String getQueryName() {
		return queryName;
	}
	/**
	 * @param queryName The queryName to set.
	 */
	public void setQueryName(String queryName) {
		this.queryName =queryName;
		this.resultSetName =queryName;
	}
	/**
	 * @return Returns the resultSetName.
	 */
	public String getResultSetName() {
		return resultSetName;
	}
	/**
	 * @param resultSetName The resultSetName to set.
	 */
	public void setResultSetName(String resultSetName) {
		this.resultSetName = resultSetName;
		this.queryName = resultSetName;
	}
	
	/**
	 * @return Returns the xsltFileName.
	 */
	public String getXsltFileName() {
		return xsltFileName;
	}
	/**
	 * @param xsltFileName The xsltFileName to set.
	 */
	public void setXsltFileName(String xsltFileName) {
		this.xsltFileName = xsltFileName;
	}
	/**
	 * @return Returns the filter_value1.
	 */
	public String getFilter_value1() {
		return filter_value1;
	}
	/**
	 * @param filter_value1 The filter_value1 to set.
	 */
	public void setFilter_value1(String filter_value1) {
		this.filter_value1 = filter_value1;
		filterParams.put("filter_value1",filter_value1);
		
	}
	/**
	 * @return Returns the filter_value2.
	 */
	public String getFilter_value2() {
		return filter_value2;
	}
	/**
	 * @param filter_value2 The filter_value2 to set.
	 */
	public void setFilter_value2(String filter_value2) {
		this.filter_value2 = filter_value2;
		filterParams.put("filter_value2",filter_value2);
	}
	/**
	 * @return Returns the filter_value3.
	 */
	public String getFilter_value3() {
		return filter_value3;
	}
	/**
	 * @param filter_value3 The filter_value3 to set.
	 */
	public void setFilter_value3(String filter_value3) {
		this.filter_value3 = filter_value3;
		filterParams.put("filter_value3",filter_value3);
	}
	/**
	 * @return Returns the filter_value4.
	 */
	public String getFilter_value4() {
		return filter_value4;
	}
	/**
	 * @param filter_value4 The filter_value4 to set.
	 */
	public void setFilter_value4(String filter_value4) {
		this.filter_value4 = filter_value4;
		filterParams.put("filter_value4",filter_value4);
		
	}
	/**
	 * @return Returns the filter_value5.
	 */
	public String getFilter_value5() {
		return filter_value5;
	}
	/**
	 * @param filter_value5 The filter_value5 to set.
	 */
	public void setFilter_value5(String filter_value5) {
		this.filter_value5 = filter_value5;
		filterParams.put("filter_value5",filter_value5);
		
	}
	/**
	 * @return Returns the filter_value6.
	 */
	public String getFilter_value6() {
		return filter_value6;
	}
	/**
	 * @param filter_value6 The filter_value6 to set.
	 */
	public void setFilter_value6(String filter_value6) {
		this.filter_value6 = filter_value6;
		filterParams.put("filter_value6",filter_value6);
		
	}
	/**
	 * @return Returns the filterParams.
	 */
	public HashMap<String,String> getFilterParams() {
		return filterParams;
	}
	/**
	 * @param filterParams The filterParams to set.
	 */
	public void setFilterParams(HashMap<String,String> filterParams) {
		this.filterParams = filterParams;
	}
	/**
	 * @return Returns the samples.
	 */
	public String[] getSamples() {
		return samples;
	}
	/**
	 * @param samples The samples to set.
	 */
	public void setSamples(String[] samples) {
		this.samples = samples;
	}
		
	/**
	 * @return Returns the pbQueryName.
	 */
	public String getPrbQueryName() {
		return prbQueryName;
	}
	/**
	 * @param pbQueryName The pbQueryName to set.
	 */
	public void setPrbQueryName(String prbQueryName) {
		this.prbQueryName = prbQueryName;
	}
	/**
	 * @return Returns the filter_element.
	 */
	public String getFilter_element() {
		
		return filter_element;
	}
	/**
	 * @return Returns the filter_string.
	 */
	public String getFilter_string() {
		return filter_string;
	}
	/**
	 * @return Returns the filter_type.
	 */
	public String getFilter_type() {
		return filter_type;
	}
	/**
	 * @param 
	 */
	public void setShowSampleSelect(String showSampleSelect) {
		filterParams.put("showSampleSelect",showSampleSelect);
		this.showSampleSelect = showSampleSelect;
	}
	/**
	 * @return Returns the filter_type.
	 */
	public String getShowSampleSelect() {
		return showSampleSelect;
	}
	
	/**
	 * @param filter_element The filter_element to set.
	 */
	public void setFilter_element(String filter_element) {
		filterParams.put("filter_element",filter_element);
		this.filter_element = filter_element;
	}
	/**
	 * @param filter_string The filter_string to set.
	 */
	public void setFilter_string(String filter_string) {
		filterParams.put("filter_string",filter_string);
		this.filter_string = filter_string;
	}
	/**
	 * @param filter_type The filter_type to set.
	 */
	public void setFilter_type(String filter_type) {
		filterParams.put("filter_type",filter_type);
		this.filter_type = filter_type;
	}
	/**
	 * @return Returns the allowShowAllValues.
	 */
	public String getAllowShowAllValues() {
		return allowShowAllValues;
	}
	/**
	 * @param allowShowAllValues The allowShowAllValues to set.
	 */
	public void setAllowShowAllValues(String allowShowAllValues) {
		filterParams.put("allowShowAllValues",allowShowAllValues);
		this.allowShowAllValues = allowShowAllValues;
	}
	
	public String getQueryDetails() {
		return queryDetails;
	}
	public void setQueryDetails(String queryDetails) {
		filterParams.put("queryDetails",queryDetails);
		this.queryDetails = queryDetails;
	}
	
	public String getReportView() {
		return reportView;
	}
	public void setReportView(String reportView) {
		filterParams.put("reportView",reportView);
		this.reportView = reportView;
	}
	
	public String getShowAllValues() {
		return showAllValues;
	}
	public void setShowAllValues(String showAllValues) {
		filterParams.put("showAllValues",showAllValues);
		this.showAllValues = showAllValues;
	}
}
