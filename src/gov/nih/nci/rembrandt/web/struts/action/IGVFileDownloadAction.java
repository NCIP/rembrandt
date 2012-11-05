package gov.nih.nci.rembrandt.web.struts.action;

import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
/**
 * This action is associated with the refine_tile.jsp tile and is mapped
 * for buttons on the page.  This is basicly the UI mechanism for creating
 * and running a compound query.
 * 
 * @author BauerD
 * Feb 15, 2005
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

public class IGVFileDownloadAction extends DispatchAction{
    private static Logger logger = Logger.getLogger(IGVFileDownloadAction.class);

	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward igvFileDownload(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		String igvFileName = request.getParameter("igv")!=null ? (String) request.getParameter("igv") : null;	
		String cnFileName = request.getParameter("cn")!=null ? (String) request.getParameter("cn") : null;	
		String clFileName = request.getParameter("cl")!=null ? (String) request.getParameter("cl") : null;	
		String sessionId = request.getParameter("uid")!=null ? (String) request.getParameter("uid") : null;	
		String fileName = null;
		String filePath = null;
		String contextType = "application/x-download";
		String[] names = null;
		if(igvFileName != null){
			names = igvFileName.split("-");
		}else if(cnFileName != null){
			names = cnFileName.split("-");
			contextType = "application/txt";
		}else if(clFileName != null){
			fileName = clFileName;
			filePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	    	if(filePath != null){
	    		filePath = filePath + File.separator;
	    	}
			contextType = "application/txt";
		}
		
    	if(names != null){
    		sessionId = names[0];
    		fileName = names[1];
    		if(fileName.contains(".idx")){
    			fileName = fileName.substring(0,fileName.lastIndexOf(".idx"));
    		}
	    	filePath = System.getProperty("gov.nih.nci.rembrandt.data_directory");
	    	if(filePath != null){
	    		filePath = filePath + File.separator + sessionId+File.separator;
	    	}
    	}
		if (fileName == null && filePath == null){
			logger.info("File Id not available from URL.");
			return null;
		}
        try
        {
        	File file = new File(filePath, fileName);
        	BufferedInputStream bis =  new BufferedInputStream(new FileInputStream(file));
        	
            // set a non-standard content type to force brower to open Save As dialog
            response.setContentType(contextType);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            ServletOutputStream sos = response.getOutputStream();

			byte[] buf = new byte[1024 * 10]; // ~= 10KB
			for (int len = -1; (len = bis.read(buf)) != -1; ){
				sos.write(buf, 0, len);
			}
		    response.setContentLength((int)file.length());
		        
		    sos.flush(); // let the Servlet container handle closing of this ServletOutputStream
		    bis.close();
        }
        catch(Exception e)	{
        	logger.error("Error occured: " + e.getMessage());
		}
        logger.info(fileName + " was downloaded.");
        return null;
	}
	
	/**
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward igvFileDownloadFromGP(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		String igvFileName = request.getParameter("gp")!=null ? (String) request.getParameter("gp") : null;	
		String encodedUser = request.getParameter("u")!=null ? (String) request.getParameter("u") : null;
		String fileName = null;
		String contextType = null;
		String[] names = null;
		String jobNumber = null;
		String rembrandtUser = null;
		
		contextType = "application/txt";
		
    	if(igvFileName != null){
    		names = igvFileName.split("/");
    		if(names != null){
	    		jobNumber = names[0];
	    		fileName = names[1];
    		}
    	}

    	try
        {
        	String gpUrl = System.getProperty("gov.nih.nci.caintegrator.gp.server");
        	gpUrl = gpUrl + "gp/jobResults/" + igvFileName;
        	URL gctFile = new URL(gpUrl);
//        	URL gctFile = new URL("http://ncias-d757-v.nci.nih.gov:8080/gp/jobResults/1080/wwwrf.gct");
    		
    		URLConnection conn = gctFile.openConnection();
			//UserCredentials credentials = (UserCredentials)request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
			
			//String rembrandtUser  = (String)request.getSession().getAttribute("gpUserId");

    		if(encodedUser == null) {
    			rembrandtUser = "RBTuser";
    		}else{
    			rembrandtUser =  new String(Base64.decodeBase64(encodedUser)); 
    		}
    		String password = System.getProperty("gov.nih.nci.caintegrator.gp.publicuser.password");
    		String loginPassword = rembrandtUser + ":" + password; 
    		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes()); 
    		conn.setRequestProperty ("Authorization", "Basic " + encoded); 
    		
        	
            // set a non-standard content type to force brower to open Save As dialog
            response.setContentType(contextType);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            ServletOutputStream sos = response.getOutputStream();
            
            BufferedInputStream bis =  new BufferedInputStream( conn.getInputStream() );
            
            byte[] buf = new byte[1024 * 10]; // ~= 10KB
    	    int j;
    	    int i = 0;
    	    int totlength = 0;
    	    
    	    for (int len = -1; (len = bis.read(buf)) != -1; ){
				sos.write(buf, 0, len);
    	    	totlength = totlength + buf.length;
			}
    	    response.setContentLength((int)totlength);
			
		    sos.flush(); // let the Servlet container handle closing of this ServletOutputStream
		    bis.close();
        }
        catch(Exception e)	{
        	logger.error("Error occured: " + e.getMessage());
		}
        logger.info(fileName + " was downloaded.");
        return null;
	}
      
}
