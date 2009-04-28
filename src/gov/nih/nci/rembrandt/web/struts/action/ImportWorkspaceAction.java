package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.application.lists.ListItem;
import gov.nih.nci.caintegrator.application.lists.UserList;
import gov.nih.nci.caintegrator.application.lists.UserListBeanHelper;
import gov.nih.nci.caintegrator.application.workspace.WorkspaceList;
import gov.nih.nci.rembrandt.util.RembrandtConstants;
import gov.nih.nci.rembrandt.web.ajax.WorkspaceHelper;
import gov.nih.nci.rembrandt.web.struts.form.ImportWorkspaceForm;

import java.io.StringReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.exolab.castor.xml.Unmarshaller;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        
        String trees = "";
        HttpSession session = request.getSession();
        StringReader reader = new StringReader( importWorkspaceForm.getXmlDoc() );
        WorkspaceList unMarshalledList = (WorkspaceList)Unmarshaller.unmarshal(WorkspaceList.class, reader);
		UserListBeanHelper userListBeanHelper = new UserListBeanHelper(request.getSession().getId());

		JSONArray jsonArray = WorkspaceHelper.generateJSONArray( session );
        
        String currentDate = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.FULL ).format(Calendar.getInstance().getTime() );

		JSONObject importFolder = new JSONObject();
		importFolder = setupLeaf( "importFolder", false, false, "" );
		importFolder.put("txt", "Imported on " + currentDate );
		
		JSONArray importFolderItems = new JSONArray();
		
		// means this XML file holds just one selection
		if ( unMarshalledList.getName().equals( "Export Folder"))
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
		importFolder.put("items", importFolderItems);

		JSONObject root = (JSONObject) jsonArray.get(0);
		JSONArray rootItems = (JSONArray) root.get("items");
		rootItems.add( rootItems.size()-1, importFolder );		// insert before trash
		
		trees = jsonArray.toString();
		session.setAttribute(RembrandtConstants.OLIST_STRUCT, trees);
		WorkspaceHelper.saveWorkspace( session.getId(), request, session );
		

        
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
      
}