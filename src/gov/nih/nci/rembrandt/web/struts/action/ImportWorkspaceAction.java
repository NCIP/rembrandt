package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.application.lists.ListItem;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.workspace.WorkspaceList;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.CopyNumberDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.cache.RembrandtPresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClinicalDataQuery;
import gov.nih.nci.rembrandt.dto.query.ComparativeGenomicQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.ajax.WorkspaceHelper;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;
import gov.nih.nci.rembrandt.web.struts.form.ClinicalDataForm;
import gov.nih.nci.rembrandt.web.struts.form.ComparativeGenomicForm;
import gov.nih.nci.rembrandt.web.struts.form.GeneExpressionForm;
import gov.nih.nci.rembrandt.web.struts.form.ImportWorkspaceForm;
import gov.nih.nci.rembrandt.web.struts.form.ImportWorkspaceForm.FileTypes;
import gov.nih.nci.rembrandt.workspace.WorkspaceQuery;

import java.io.StringReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xml.sax.InputSource;


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

public class ImportWorkspaceAction extends Action{
    private static Logger logger = Logger.getLogger(ImportWorkspaceAction.class);
    private static RembrandtPresentationTierCache _cacheManager = ApplicationFactory.getPresentationTierCache();
    
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
        
        ImportWorkspaceForm importWorkspaceForm = (ImportWorkspaceForm) form;
        
        FileTypes importFileType = importWorkspaceForm.getFileTypeEnum();
        
        String trees = "";
        JSONArray jsonArray = null;
        HttpSession session = request.getSession();
        StringReader reader = new StringReader( importWorkspaceForm.getXmlDoc() );
		UserListBeanHelper userListBeanHelper = new UserListBeanHelper(request.getSession().getId());
		SessionQueryBag queryBag = _cacheManager.getSessionQueryBag(session.getId());

        String currentDate = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.FULL ).format(Calendar.getInstance().getTime() );

		JSONObject importFolder = new JSONObject();
		importFolder = setupLeaf( "importFolder", false, false, "" );
		importFolder.put("txt", "Imported on " + currentDate );
		
		JSONArray importFolderItems = new JSONArray();
		
		try
		{
			if ( importFileType.equals( FileTypes.LIST ))		
			{
				jsonArray = WorkspaceHelper.generateListJSONArray( session );
	
				WorkspaceList unMarshalledList = (WorkspaceList)Unmarshaller.unmarshal(WorkspaceList.class, reader);
				
				// means this XML file holds just one selection
				if ( unMarshalledList.getName().equals( "_Export _Folder"))
				{
					Iterator iterator = unMarshalledList.iterator();
					importFolderItems = updateImportList(userListBeanHelper, iterator);
				}
				else  // means it is a folder which might hold many elements as a tree
				{
					JSONObject topFolder = new JSONObject();
					topFolder = setupLeaf( unMarshalledList.getName(), false, false, "" );
					JSONArray topFolderItems = new JSONArray();
					
					Iterator iterator = unMarshalledList.iterator();
					
					topFolderItems = updateImportList(userListBeanHelper, iterator);		
					topFolder.put("items", topFolderItems);
					importFolderItems.add(topFolder);
					
				}
			}
			else
			{
				jsonArray = WorkspaceHelper.generateQueryJSONArray( session );
	
				InputSource is = new InputSource(getClass().getClassLoader().getResource("castor_query.xml").getPath());
				Mapping castorMapping = new Mapping();
				castorMapping.loadMapping(is);
				
				Unmarshaller unmar = new Unmarshaller(WorkspaceQuery.class);
				unmar.setMapping( castorMapping );

				WorkspaceQuery unMarshalledQuery = (WorkspaceQuery)unmar.unmarshal(reader);
				
				// means this XML file holds just one selection
				if ( unMarshalledQuery.getName().equals( "_Export _Folder"))
				{
					Iterator iterator = unMarshalledQuery.iterator();
					importFolderItems = updateImportQuery(request, queryBag, iterator);
				}
				else  // means it is a folder which might hold many elements as a tree
				{
					JSONObject topFolder = new JSONObject();
					topFolder = setupLeaf( unMarshalledQuery.getName(), false, false, "" );
					JSONArray topFolderItems = new JSONArray();
					
					Iterator iterator = unMarshalledQuery.iterator();
					
					topFolderItems = updateImportQuery(request, queryBag, iterator);		
					topFolder.put("items", topFolderItems);
					importFolderItems.add(topFolder);
					
				}
			}
		}
		catch( Exception e )
		{
			ActionErrors errors = new ActionErrors();
			errors.add( "Upload Error", new ActionError( "gov.nih.nci.nautilus.ui.struts.form.importFile.malformed.error", importWorkspaceForm.getImportFileName() ) );
			saveErrors( request, errors );
			return mapping.findForward("failure");
		}
		importFolder.put("items", importFolderItems);

		JSONObject root = (JSONObject) jsonArray.get(0);
		JSONArray rootItems = (JSONArray) root.get("items");
		rootItems.add( rootItems.size()-1, importFolder );		// insert before trash
		
		trees = jsonArray.toString();
		if ( importFileType.equals( FileTypes.LIST ))
		{
			session.setAttribute(RembrandtConstants.OLIST_STRUCT, trees);
		}
		else
		{
			session.setAttribute(RembrandtConstants.OQUERY_STRUCT, trees);
		}
			
		WorkspaceHelper.saveWorkspace( session );
		

        
        return mapping.findForward("success");
	}

	private JSONObject setupLeaf( String idName, boolean editable, boolean acceptDrop, String toolTip) {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("id", idName ); 
		jsonObject.put("editable", editable);
		jsonObject.put("txt", idName );
		jsonObject.put("acceptdrop", acceptDrop);
		
		if ( ! toolTip.equals( "" )){
			jsonObject.put("tooltip", toolTip );
		}
		
		return jsonObject;
	}

	private JSONArray updateImportList(UserListBeanHelper userListBeanHelper, Iterator<UserList> iterator) {
		UserList ul = null;
		WorkspaceList wl = null;
		JSONArray folderItems = new JSONArray();
		while ( iterator.hasNext() ) {
			Object obj = iterator.next();
			
			if ( obj instanceof WorkspaceList ) // obj is an instance of WorkspaceList which means it is a folder. This means it needs a recursive call.
			{
				wl = (WorkspaceList)obj;
				JSONObject wsFolder = new JSONObject();
				wsFolder = setupLeaf( wl.getName(), false, false, "" );
				
				JSONArray wsFolderItems = new JSONArray();
				
				wsFolderItems = updateImportList( userListBeanHelper, wl.getFolderList().iterator() );
				wsFolder.put("items", wsFolderItems);
				
				folderItems.add(wsFolder);

			}
			else 
			{
				ul = (UserList)obj;
				JSONObject theList = new JSONObject();
				theList = setupLeaf( userListBeanHelper.getUserListBean().checkListName(ul.getName()), false, false, ul.getListOrigin() + " " + ul.getListType() + " (" + ul.getItemCount() + ")" );

				folderItems.add(theList);
				removeId( ul );
				userListBeanHelper.addList(ul);
				
			}

		}
		
		return folderItems;
	}
	
	private JSONArray updateImportQuery(HttpServletRequest request, SessionQueryBag queryBag, Iterator<Query> iterator) {
		Query uq = null;
		WorkspaceQuery wq = null;
		JSONArray folderItems = new JSONArray();
		while ( iterator.hasNext() ) {
			Object obj = iterator.next();
			
			if ( obj instanceof WorkspaceQuery ) // obj is an instance of WorkspaceQuery which means it is a folder. This means it needs a recursive call.
			{
				wq = (WorkspaceQuery)obj;
				JSONObject wsFolder = new JSONObject();
				wsFolder = setupLeaf( wq.getName(), false, false, "" );
				
				JSONArray wsFolderItems = new JSONArray();
				
				wsFolderItems = updateImportQuery( request, queryBag, wq.getFolderList().iterator() );
				wsFolder.put("items", wsFolderItems);
				
				folderItems.add(wsFolder);

			}
			else 
			{
				uq = (Query)obj;
				JSONObject theQuery = new JSONObject();
				uq.setQueryName( queryBag.checkQueryName( uq.getQueryName() ) );
				theQuery = setupLeaf( uq.getQueryName(), false, false, uq.toString() );

				folderItems.add(theQuery);
				ActionForm form = null;
				if ( uq instanceof GeneExpressionQuery )
					form = createGeneExpressionForm( request, (GeneExpressionQuery)uq );
				else if ( uq instanceof ComparativeGenomicQuery )
					form = createComparativeGenomicForm( request, (ComparativeGenomicQuery)uq );
				else if ( uq instanceof ClinicalDataQuery )
					form = createClinicalDataForm( request, (ClinicalDataQuery)uq );
				
				queryBag.putQuery( uq, form );
			}

		}
		
		return folderItems;
	}
	
	
	private void removeId( UserList ul )
	{
		// need to be set to null so that DB will create a new List & List Item records.
		ul.setId(null);	
		for ( ListItem li : ul.getListItems() )
		{
			li.setId( null );  // need to be set to null so that DB will create a new List Item record.
			li.setListId( null );
		}
	}
	
	private GeneExpressionForm createGeneExpressionForm( HttpServletRequest request, GeneExpressionQuery geneExpQuery)
	{
		GeneExpressionForm geneExpressionForm = new GeneExpressionForm();
		geneExpressionForm.reset(new ActionMapping(), request);
		
		geneExpressionForm.setQueryName(geneExpQuery.getQueryName());
		
		if ( ! geneExpQuery.isAllGenesQuery() ) {
			geneExpressionForm.setGeneIDCriteria( geneExpQuery.getGeneIDCrit() );
			geneExpressionForm.getAllGenesCriteria().setAllGenes( false );
			geneExpressionForm.setGeneList( geneExpQuery.getGeneIDCrit().getGeneIdentifiers() );
		}
		else
			geneExpressionForm.getAllGenesCriteria().setAllGenes( true );
		
		geneExpressionForm.setSampleCriteria( geneExpQuery.getSampleIDCrit() );
		if ( geneExpQuery.getSampleIDCrit() != null ) {
			geneExpressionForm.setSampleFile(geneExpQuery.getSampleIDCrit().getSampleFile() );
			geneExpressionForm.setSampleGroup(geneExpQuery.getSampleIDCrit().getSampleGroup() );
			
			// for a single typed Sample ID
			if ( geneExpQuery.getSampleIDCrit().getSampleGroup() != null && geneExpQuery.getSampleIDCrit().getSampleGroup().equals( "Specify")) {
				Iterator iterator = geneExpQuery.getSampleIDCrit().getSampleIDs().iterator();
				StringBuffer sBuffer = new StringBuffer();
				while ( iterator.hasNext() ) {
					sBuffer.append( ( (SampleIDDE)iterator.next() ).getValueObject() );
				}
				
				geneExpressionForm.setSampleList(sBuffer.toString() );
			}
			if ( geneExpQuery.getSampleIDCrit().getExcludeResections() != null && geneExpQuery.getSampleIDCrit().getExcludeResections()== true) {
				geneExpressionForm.setExcludeResections(true);
			}
				
		}
		geneExpressionForm.setCloneOrProbeIDCriteria( geneExpQuery.getCloneOrProbeIDCriteria() );
		
		if ( geneExpQuery.getCloneOrProbeIDCriteria() != null ) {
			Iterator iterator = geneExpQuery.getCloneOrProbeIDCriteria().getIdentifiers().iterator();
			while ( iterator.hasNext() ) {
				CloneIdentifierDE cde = (CloneIdentifierDE)iterator.next();
				
				if ( cde.getCloneIDType().equals(CloneIdentifierDE.IMAGE_CLONE )) {
					geneExpressionForm.setCloneList( "IMAGE Id");
				}
				else if ( cde.getCloneIDType().equals(CloneIdentifierDE.PROBE_SET )) {
					geneExpressionForm.setCloneList( "Probe Set Id");
				}
				geneExpressionForm.setCloneListSpecify(cde.getValueObject());
			}
		}
			
		
		geneExpressionForm.setFoldChangeCriteria(geneExpQuery.getFoldChgCrit());
		geneExpressionForm.setRegionCriteria(geneExpQuery.getRegionCrit());
		if ( geneExpQuery.getRegionCrit() != null )
			geneExpressionForm.setRegion(geneExpQuery.getRegionCrit().getRegion() );
		geneExpressionForm.setDiseaseOrGradeCriteria(geneExpQuery.getDiseaseOrGradeCriteria());
		geneExpressionForm.setTumorType(geneExpQuery.getDiseaseOrGradeCriteria() );
		geneExpressionForm.setGeneOntologyCriteria(geneExpQuery.getGeneOntologyCriteria());
		if ( geneExpQuery.getGeneOntologyCriteria() != null ) {
			Iterator iterator = geneExpQuery.getGeneOntologyCriteria().getGOIdentifiers().iterator();
			StringBuffer gBuffer = new StringBuffer();
			
			while ( iterator.hasNext()) {
				gBuffer.append( ( (GeneOntologyDE)iterator.next() ).getValueObject() + "\n" );
			}
			geneExpressionForm.setGoClassification(gBuffer.toString());
		}
			
		geneExpressionForm.setPathwayCriteria(geneExpQuery.getPathwayCriteria());
		if ( geneExpQuery.getPathwayCriteria() != null ) {
			Iterator iterator = geneExpQuery.getPathwayCriteria().getPathwayNames().iterator();
			StringBuffer pBuffer = new StringBuffer();
			
			while ( iterator.hasNext()) {
				pBuffer.append( ( (PathwayDE)iterator.next() ).getValueObject() + "\n" );
			}
			geneExpressionForm.setPathways(pBuffer.toString());
		}
		geneExpressionForm.setArrayPlatformCriteria(geneExpQuery.getArrayPlatformCriteria());
		geneExpressionForm.setArrayPlatform( (String)geneExpQuery.getArrayPlatformCriteria().getPlatform().getValue() );
		
		if( geneExpQuery.getRegionCrit() != null ) {
			geneExpressionForm.setChromosomeNumber( geneExpQuery.getRegionCrit().getChromNumber() );
			geneExpressionForm.setCytobandRegionStart( geneExpQuery.getRegionCrit().getStartCytoband() );
			geneExpressionForm.setCytobandRegionEnd( geneExpQuery.getRegionCrit().getEndCytoband() );
			geneExpressionForm.setBasePairStart( geneExpQuery.getRegionCrit().getStart() );
			geneExpressionForm.setBasePairEnd( geneExpQuery.getRegionCrit().getEnd() );
		}
		
		if ( geneExpQuery.getFoldChgCrit() != null ) {
			Iterator iterator = geneExpQuery.getFoldChgCrit().getFoldChangeObjects().iterator();
			while ( iterator.hasNext() ) {
				ExprFoldChangeDE exprFoldChangeDE = (ExprFoldChangeDE)iterator.next();
				
				if ( exprFoldChangeDE.getRegulationType().equals(ExprFoldChangeDE.UP_REGULATION ))
					geneExpressionForm.setFoldChangeValueUp( exprFoldChangeDE );
				else if ( exprFoldChangeDE.getRegulationType().equals(ExprFoldChangeDE.DOWN_REGULATION ))
					geneExpressionForm.setFoldChangeValueDown( exprFoldChangeDE );	
				else if ( exprFoldChangeDE.getRegulationType().equals(ExprFoldChangeDE.UNCHANGED_REGULATION_UPPER_LIMIT ))
					geneExpressionForm.setFoldChangeValueUnchangeTo( exprFoldChangeDE );	
				else if ( exprFoldChangeDE.getRegulationType().equals(ExprFoldChangeDE.UNCHANGED_REGULATION_DOWN_LIMIT ))
					geneExpressionForm.setFoldChangeValueUnchangeFrom( exprFoldChangeDE );	
				
			}
			geneExpressionForm.setRegulationStatus(geneExpQuery.getFoldChgCrit().getRegulationStatus() );
		}
		
		return geneExpressionForm;
	}
	
	private ComparativeGenomicForm createComparativeGenomicForm( HttpServletRequest request, ComparativeGenomicQuery cghQuery)
	{
		ComparativeGenomicForm comparativeGenomicForm = new ComparativeGenomicForm();
		comparativeGenomicForm.reset(new ActionMapping(), request);
		
		comparativeGenomicForm.setQueryName(cghQuery.getQueryName());
		
		if ( ! cghQuery.isAllGenesQuery() ) {
			comparativeGenomicForm.setGeneIDCriteria( cghQuery.getGeneIDCriteria() );
			comparativeGenomicForm.getAllGenesCriteria().setAllGenes( false );
			comparativeGenomicForm.setGeneList( cghQuery.getGeneIDCriteria().getGeneIdentifiers() );
		}
		else
			comparativeGenomicForm.getAllGenesCriteria().setAllGenes( true );
		
		comparativeGenomicForm.setSampleCriteria( cghQuery.getSampleIDCrit() );
		if ( cghQuery.getSampleIDCrit() != null ) {
			comparativeGenomicForm.setSampleFile(cghQuery.getSampleIDCrit().getSampleFile() );
			comparativeGenomicForm.setSampleGroup(cghQuery.getSampleIDCrit().getSampleGroup() );
			
			// for a single typed Sample ID
			if ( cghQuery.getSampleIDCrit().getSampleGroup() != null && cghQuery.getSampleIDCrit().getSampleGroup().equals( "Specify")) {
				Iterator iterator = cghQuery.getSampleIDCrit().getSampleIDs().iterator();
				StringBuffer sBuffer = new StringBuffer();
				while ( iterator.hasNext() ) {
					sBuffer.append( ( (SampleIDDE)iterator.next() ).getValueObject() );
				}
				
				comparativeGenomicForm.setSampleList(sBuffer.toString() );
			}
			
			comparativeGenomicForm.setSpecimenType(cghQuery.getSampleIDCrit().getSpecimenTypeValue() );
		}
		
		comparativeGenomicForm.setRegionCriteria(cghQuery.getRegionCriteria());
		
		if( cghQuery.getRegionCriteria() != null ) {
			comparativeGenomicForm.setRegion(cghQuery.getRegionCriteria().getRegion() );
			comparativeGenomicForm.setChromosomeNumber( cghQuery.getRegionCriteria().getChromNumber() );
			comparativeGenomicForm.setCytobandRegionStart( cghQuery.getRegionCriteria().getStartCytoband() );
			comparativeGenomicForm.setCytobandRegionEnd( cghQuery.getRegionCriteria().getEndCytoband() );
			comparativeGenomicForm.setBasePairStart( cghQuery.getRegionCriteria().getStart() );
			comparativeGenomicForm.setBasePairEnd( cghQuery.getRegionCriteria().getEnd() );
		}
		
		comparativeGenomicForm.setDiseaseOrGradeCriteria(cghQuery.getDiseaseOrGradeCriteria());
		comparativeGenomicForm.setTumorType(cghQuery.getDiseaseOrGradeCriteria() );
		
		comparativeGenomicForm.setSNPCriteria(cghQuery.getSNPCriteria() );
		if ( cghQuery.getSNPCriteria() != null )
		{
			comparativeGenomicForm.setSnpId(cghQuery.getSNPCriteria().getSnpId());
			
			// single option selected
			if ( cghQuery.getSNPCriteria().getSnpId().equals( "specify" )) {
				SNPIdentifierDE snpIde = (SNPIdentifierDE)( (ArrayList)cghQuery.getSNPCriteria().getIdentifiers() ).get(0);
				comparativeGenomicForm.setSnpList( snpIde.getSNPType() );
				comparativeGenomicForm.setSnpListSpecify( snpIde.getValueObject() ) ;
			}
				
		}
		
		if ( cghQuery.getCopyNumberCriteria() != null ) {
			Iterator iterator = cghQuery.getCopyNumberCriteria().getCopyNummbers().iterator();
			while ( iterator.hasNext() ) {
				CopyNumberDE copyNumberDE = (CopyNumberDE)iterator.next();
				
				if ( copyNumberDE.getCGHType().equals(copyNumberDE.AMPLIFICATION ))
					comparativeGenomicForm.setCnAmplified( copyNumberDE );
				else if ( copyNumberDE.getCGHType().equals(copyNumberDE.DELETION ))
					comparativeGenomicForm.setCnDeleted( copyNumberDE );
				else if ( copyNumberDE.getCGHType().equals(copyNumberDE.UNCHANGED_COPYNUMBER_UPPER_LIMIT ))
					comparativeGenomicForm.setCnUnchangeTo( copyNumberDE );
				else if ( copyNumberDE.getCGHType().equals(copyNumberDE.UNCHANGED_COPYNUMBER_DOWN_LIMIT ))
					comparativeGenomicForm.setCnUnchangeFrom( copyNumberDE );
			}
			comparativeGenomicForm.setCopyNumber(cghQuery.getCopyNumberCriteria().getCopyNumber() );
		}

		return comparativeGenomicForm;
		
	}

	private ClinicalDataForm createClinicalDataForm( HttpServletRequest request, ClinicalDataQuery clinicalDataQuery)
	{
		ClinicalDataForm clinicalDataForm = new ClinicalDataForm();
		clinicalDataForm.reset(new ActionMapping(), request);
		
		clinicalDataForm.setQueryName(clinicalDataQuery.getQueryName());
		
		clinicalDataForm.setSampleCriteria( clinicalDataQuery.getSampleIDCrit() );
		if ( clinicalDataQuery.getSampleIDCrit() != null ) {
			clinicalDataForm.setSampleFile(clinicalDataQuery.getSampleIDCrit().getSampleFile() );
			clinicalDataForm.setSampleGroup(clinicalDataQuery.getSampleIDCrit().getSampleGroup() );
			
			// for a single typed Sample ID
			if ( clinicalDataQuery.getSampleIDCrit().getSampleGroup() != null && clinicalDataQuery.getSampleIDCrit().getSampleGroup().equals( "Specify")) {
				Iterator iterator = clinicalDataQuery.getSampleIDCrit().getSampleIDs().iterator();
				StringBuffer sBuffer = new StringBuffer();
				while ( iterator.hasNext() ) {
					sBuffer.append( ( (SampleIDDE)iterator.next() ).getValueObject() );
				}
				
				clinicalDataForm.setSampleList(sBuffer.toString() );
			}
		}
		
		clinicalDataForm.setDiseaseOrGradeCriteria(clinicalDataQuery.getDiseaseOrGradeCriteria());
		clinicalDataForm.setTumorType(clinicalDataQuery.getDiseaseOrGradeCriteria() );
		
		clinicalDataForm.setSurvivalCriteria(clinicalDataQuery.getSurvivalCriteria() );
		clinicalDataForm.setAgeCriteria(clinicalDataQuery.getAgeCriteria() );
		clinicalDataForm.setGenderCriteria(clinicalDataQuery.getGenderCriteria() );
		clinicalDataForm.setRaceCriteria(clinicalDataQuery.getRaceCriteria() );
		clinicalDataForm.setOnStudyChemoAgentCriteria( clinicalDataQuery.getOnStudyChemoAgentCriteria());
		clinicalDataForm.setOnStudyRadiationTherapyCriteria(clinicalDataQuery.getOnStudyRadiationTherapyCriteria());
		clinicalDataForm.setOnStudySurgeryOutcomeCriteria(clinicalDataQuery.getOnStudySurgeryOutcomeCriteria());
		clinicalDataForm.setOnStudySurgeryTitleCriteria(clinicalDataQuery.getOnStudySurgeryTitleCriteria());
		clinicalDataForm.setRadiationTherapyCriteria(clinicalDataQuery.getRadiationTherapyCriteria() );
		clinicalDataForm.setChemoAgentCriteria(clinicalDataQuery.getChemoAgentCriteria());
		clinicalDataForm.setPriorSurgeryTitleCriteria(clinicalDataQuery.getPriorSurgeryTitleCriteria());
		clinicalDataForm.setKarnofskyCriteria(clinicalDataQuery.getKarnofskyCriteria());
		clinicalDataForm.setLanskyCriteria(clinicalDataQuery.getLanskyCriteria());
		clinicalDataForm.setMriCriteria(clinicalDataQuery.getMriCriteria());
		clinicalDataForm.setNeuroExamCriteria(clinicalDataQuery.getNeuroExamCriteria());
		
		return clinicalDataForm;
		
	}
		
} 