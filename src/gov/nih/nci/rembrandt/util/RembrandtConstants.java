package gov.nih.nci.rembrandt.util;

import java.io.Serializable;

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


public final class RembrandtConstants {

    //Specifies the location in the webapp classes directory
    //to find ApplicationResources.properties
    public static final String  APPLICATION_RESOURCES = "ApplicationResources";
    //Specifies where in the webapps classes directory to find
 	//log4j.properties
 	public static final String LOGGING_PROPERTIES = "log4j.properties";

    public static final String CACHE_PROPERTIES = "rembrandtCache.properties";

	public static final String SESSION_QUERY_BAG_KEY = "nautilus.queryMap";
	
	public static final String REMBRANDT_USER_LIST_BEAN_KEY = "nautilus.rembrandtUserListBean";

	public static final String RESULTSET_KEY = "geneViewResultSet";

	public static final String VALID_QUERY_TYPES_KEY = "nautilus.request.validQuerytypes";

	

	public static final String ASTRO = "ASTROCYTOMA";

	public static final String LOGGER = "gov.nih.nci.nautilus";

	public static final String JSP_LOGGER = "gov.nih.nci.nautilus.jsp";

	public static final int MAX_FILEFORM_COUNT = 40000;
	
	public static final String REPORT_BEAN = "reportBean";
	
	public static final String REPORT_XML = "reportXML";
	
	public static final String TEMP_RESULTS = "Rembrandt_results ";
	
	public static final String DEFAULT_XSLT_FILENAME ="report.xsl";
	
	public static final String DEFAULT_PATHWAY_XSLT_FILENAME ="pathwayFormat.xsl";
	
	public static final String DEFAULT_PATHWAY_DESC_XSLT_FILENAME ="pathwayDescFormat.xsl";
	
	public static final String DEFAULT_GENE_XSLT_FILENAME ="geneFormat.xsl";	
	
	public static final String DEFAULT_XSLT_CSV_FILENAME ="csv.xsl";
	
	public static final String XSLT_FILE_NAME ="xsltFileName";
	
	public static final String FILTER_PARAM_MAP ="filterParamMap";
	
	public static final String PREVIEW_RESULTS = "previewResults";
	
	public static final String REPORT_COUNTER = "reportCounter";
	
   // public static final String GRAPH_DEFAULT = "Default";
    
   // public static final String GENE_EXP_KMPLOT ="GE_KM_PLOT";
    
  // public static final String COPY_NUMBER_KMPLOT = "COPY_NUM_KM_PLOT";
    
    public static final String GENE_SYMBOL = "Gene Keyword";
    
    public static final String CYTOBAND = "Cytoband";
    
    public static final String SNP_PROBESET_ID = "SNP Probe set ID";
    
    public static final  String DE_BEAN_FILE_NAME ="/deToBeanAttrMappings.xml";

    // this max Sample ID count is a temp solution  This will be made more dynamic for 1.0
    public static final int ALL_GENES_MAX_SAMPLE_COUNT = 22;
    
    public static final String FILTER_REPORT_SUFFIX = " filter report";
    
    public static final String SHOW_ALL_VALUES_SUFFIX = " show all values report";
    
    public static final String STANDARD_GENE_EXP_REGULATION = "2";
    
    public static final String ALL_GENES_COPY_NUMBER_REGULATION = "10";
    
    public static final String ALL_GENES_GENE_EXP_REGULATION = "4";
    //The maximum number of samples that can be applied to All Gene Reports
	public static final int MAX_ALL_GENE_SAMPLE_SET = 20;
	
	public static final String SESSION_CRITERIA_BAG_KEY = "rembrandt.criteriaMap";
	
	public static final String JMS_PROPERTIES = "jms.properties";
	
	public static enum GraphType{KaplanMeierGeneExpression, KaplanMeierCopyNumber, GeneExrpression};
    
    public static final String[] FOLD_CHANGE_DEFAULTS = {"2","3","4","5","6","7","8","9","10"};
	
    public static final String USER_PREFERENCES = "userPreferences";
    
    public static final String USER_CREDENTIALS = "UserCredentials";
    
    public static final String USER_LISTS = "userLists";

    public static final String WEB_GENOMEAPP_PROPERTIES = "webGenome.properties";
	
    public static final String TEMP_REPORTER_GROUP = "Reporter_Group";
    
    public static final String ALL_GLIOMA = "ALL GLIOMA";
    
    public static final String NON_TUMOR = "NON_TUMOR";
    
    public static final String UNKNOWN = "UNKNOWN";
    public static final String UNCLASSIFIED = "UNCLASSIFIED";
    
	public static final String ALL = "ALL";
	
	public static final String PVALUE = "p-value";
    
	public static final String REPORTER_SELECTION_AFFY = "Affymetrix";
    public static final String REPORTER_SELECTION_UNI = "Unified Gene";
	public static final String SESSION_TEMP_FOLDER_PATH = "SessionTempFolderPath";

}
