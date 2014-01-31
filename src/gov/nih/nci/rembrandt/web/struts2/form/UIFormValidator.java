/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//import gov.nih.nci.caintegrator.application.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.util.MoreStringUtils;
import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

/**
 * @author BauerD 
 * Dec 15, 2004 
 * This class is used to validate input fields from the UI
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

public class UIFormValidator {
    private static Logger logger = Logger.getLogger(UIFormValidator.class);
    
    public static String FIELD_SEPARATOR = "|";
    
    public static List<String> validateLDAP(String username, String password,
            List<String> errors) {
    	
        AuthenticationManager am = null;
        boolean loggedIn = false;
        try {
            logger.debug("Testing Logging");            
            am = SecurityServiceProvider.getAuthenticationManager("rembrandt");
            loggedIn = am.login(username, password);

        } catch (CSException e) {
            logger.debug("loginFail");           
    
        }
        /**the following  if clause will only be used until the 
         * app is released as a backdoor for developers and non NIH
         * folks. Once the app is moved, this clause should also be removed.
         * -kevin rosso
         */
        if(username.equals("RBTuser") && password.equals("RBTpass")){
            loggedIn = true;
        }
        if(loggedIn) {
            logger.debug("loginSuccess");
        } else {
            logger.debug("loginFail"); 
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));
        }
        return errors;
    }
    
    public static List<String> validateFormFieldsWithRegion(String geneFile, String geneGroup, FormFile cloneListFile,
	        String cloneId, String sampleFile, String sampleGroup,
            List<String> errors){
    	if (geneGroup.equalsIgnoreCase("Upload") && geneFile != null
	            || cloneId.equalsIgnoreCase("Upload") && cloneListFile != null
	               || sampleGroup.equalsIgnoreCase("Upload") && sampleFile != null){
	       
	    	errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.region.uploadFormFiles"));
	    }	    
	    return errors;
	}
    public static List<String> validateGeneSymbolisNotEmpty(String geneSymbol,
            List<String> errors) {
	    
    	if (geneSymbol == null || geneSymbol.equals("") ) {
			errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.quicksearch.emptyGene"));
		}
		return errors;
		
	}

	public static List<String> validateQueryName(String queryName,
            List<String> errors) {
		if ((queryName == null || queryName.length() < 1)) {
			errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.queryname.no.error"));
		}
		else if (!MoreStringUtils.isURLSafe(queryName))
			errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.queryname.illegal.characters"));
		return errors;
	}
    
    public static List<String> validateAnalysisName(String analysisResultName,
            List<String> errors) {
    	
    	
        if (analysisResultName == null || analysisResultName.equals("")){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.analysisResultName.no.error"));
    
        }
        return errors;
    }
    
    public static List<String> validateSelectedGroups(String[] selectedGroups,
            List<String> errors){
    	
    	
    	
        if (selectedGroups == null || selectedGroups.length != 2){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.groups.more.error"));
        }
       

        return errors;
    }    
   
    
  public static List<String> validateSelectedGroups(String[] selectedGroups,  int num,
          List<String> errors){
	 
	  
        if (selectedGroups == null || selectedGroups.length < 2){
        	errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.groups.more.error"));
        }
       

        return errors;
    }
  
    public static List<String> validateSelectedGroup(String groupsOption, String[] selectedGroups,
            List<String> errors){
    	
    	
        if (groupsOption.equalsIgnoreCase("variousSamples") && selectedGroups == null){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.group.no.error"));
        }
       

        return errors;
    }

    public static List<String> validateSelectedGroup(String[] selectedGroups,
            List<String> errors){
    	
        if (selectedGroups == null || (selectedGroups != null && selectedGroups.length<2)){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.group.no.error"));
        }
       

        return errors;
    }

    public static List<String> validateSelectedOneGroup(String[] selectedGroups,
            List<String> errors){
    	if (selectedGroups == null || (selectedGroups != null && selectedGroups.length == 0)){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.group.no.error"));
        }
       

        return errors;
    }
    
    public static List<String> validateSelectedOnePlatform(String arrayPlatform, String snpArrayPlatform,
            List<String> errors){
    	
    	if( arrayPlatform == null ) arrayPlatform = "";
    	if( snpArrayPlatform == null ) snpArrayPlatform = "";
        if (arrayPlatform.equals("") &&  snpArrayPlatform.equals("")){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.platform.no.error"));
        }
       

        return errors;
    }

    public static List<String> validateSelectedOneAnnotation(String[] selectedAnnotations,
            List<String> errors){
    	
        if (selectedAnnotations == null || (selectedAnnotations != null && selectedAnnotations.length == 0)){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.annotation.no.error"));
        }
       

        return errors;
    }

    public static List<String> validateChromosomalRegion(String chrosomeNumber,
			String region, String cytobandRegionStart, String basePairStart,
			String basePairEnd,
            List<String> errors) {
      	
    	
		if (!chrosomeNumber.trim().equals("-1")) {
			if (region.trim().length() < 1) {
				Properties props = ApplicationContext.getLabelProperties();
				String v = props.getProperty("gov.nih.nci.nautilus.ui.struts.form.region.no.error");
				
				errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.region.no.error"));
			}
			else {
				if (region.trim().equalsIgnoreCase("cytoband")) {
					if (cytobandRegionStart.trim().length() < 1)
						errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.cytobandregion.no.error"));
				}
				if (region.trim().equalsIgnoreCase("basePairPosition")) {
					if ((basePairStart.trim().length() < 1)
							|| (basePairEnd.trim().length() < 1)) {
						errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.basePair.no.error"));
					} else {
						if (!isBasePairValid(basePairStart, basePairEnd)) {
							errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.basePair.incorrect.error"));
						}
					}

				}

			}

		}
		return errors;

	}
    
    public static List<String> validateGOClassification(String goClassification,
            List<String> errors) {
    	
        if (goClassification!= null  && goClassification.trim().length() > 0) {
            goClassification = goClassification.trim();
            if (goClassification.startsWith("GO:")) {
                String numberValue = goClassification.substring(goClassification.indexOf(":")+1);
                if (goClassification.length() == 10){
                    try {
                        int n = Integer.parseInt(numberValue);
                    } catch (NumberFormatException ne){
                        errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.go.numeric.error"));
                    }
                }else {
                    errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.go.length.error"));
                }
            }else {
               errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.go.startswith.error"));
            }
        }
    	return errors;
    }
    
    
    public static List<String> validateCopyNo(String copyNumber, String actualCopyNoType,String copyNo
    		, String copyNoType,
            List<String> errors) {
    	
        if (copyNumber != null && copyNumber.equalsIgnoreCase(actualCopyNoType)&& copyNo!= null  && copyNo.trim().length() > 0) {
        	copyNo = copyNo.trim();
        	try{
        		float n = Float.parseFloat(copyNo);
        	}
        	catch (NumberFormatException ne){
        		 errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.copyno.numeric.error"));
        	 
        	}
        }
    	return errors;
    }
    
    
    public static List<String> validateSegmentMean(String segmentMean, String actualSegmentMeanType, 
    		String segMean, String segMeanType,
            List<String> errors) {
    	
        if (segmentMean != null && segmentMean.equalsIgnoreCase(actualSegmentMeanType) && segMean != null  && segMean.trim().length() > 0) {
        	segMean = segMean.trim();
        	try{
        		float n = Float.parseFloat(segMean);
        	}
        	catch (NumberFormatException ne){
        		    
        		 errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.segmentMean.numeric.error"));
        	 
        	}
        }
    	return errors;
    }
    
    public static List<String> validateFoldChange(String regulationStatus, String actualRegulationStatus,
    		String foldChangeNo, String foldChangeType,
            List<String> errors) {
    	
        if (regulationStatus != null && regulationStatus.equalsIgnoreCase(actualRegulationStatus)&& foldChangeNo!= null  && foldChangeNo.trim().length() > 0) {
        	foldChangeNo = foldChangeNo.trim();
        	try{
        		float n = Float.parseFloat(foldChangeNo);
        	}
        	catch (NumberFormatException ne){
        		    
        		 errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.foldChangeno.numeric.error"));
        	 
        	}
        }
    	return errors;
    }
    
    public static List<String> validate(String geneGroup, String geneList, FormFile geneFile,
            List<String> errors) {
    	
        if (geneGroup!= null && geneGroup.trim().length() >= 1){
            if (geneList.trim().length() < 1 && geneFile == null){
                errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.geneGroup.no.error"));
            }
            
        }
        
        return errors;
    }
    
    public static List<String> validateTextFileType(FormFile formFile, String fileContents,
            List<String> errors) {
    	
    //Make sure the uploaded File is of type txt and MIME type is text/plain
        if(formFile != null  &&
          (!(formFile.getFileName().endsWith(".txt"))) &&
          (!(formFile.getContentType().equals("text/plain")))){
            errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.uploadFile.no.error"));
        }   
        
        return errors;
    }
    
    //Shan commented this

    public static List<String> validateXmlFileType(File formFile, List<String> errors) {
    	
        //Make sure the uploaded File is of type txt and MIME type is text/plain
            if(formFile != null  &&
              (!(formFile.getName().toLowerCase().endsWith(".xml")))/* &&
              (!(formFile.get.equals("text/xml")))*/){
                errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.importFile.no.error"));
            }   
            
            return errors;
        }

    public static List<String> validateCloneId(String cloneId, String cloneListSpecify, FormFile cloneListFile,
            List<String> errors) {
    	
         if (cloneId!= null && cloneId.trim().length() >= 1){
            if (cloneListSpecify.trim().length() < 1 && cloneListFile == null){
                errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.cloneid.no.error"));
            }
            
        }
    
		return errors;
    }
    
    public static List<String> validateSnpId(String snpId, String snpList, FormFile snpListFile,
            List<String> errors) {
    	
          if (snpId != null && snpId.trim().length() >= 1) {
            if (snpList.trim().length() < 1
                    && snpListFile == null) {
                errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.snpid.no.error"));
            }
        }
  
    	return errors;
    }
    
   
    /**
     * <p>Checks whether the string is ASCII 7 bit.</p>
     *
     * @param str  the string to check
     * @return false if the string contains a char that is greater than 128
     */
    public static boolean isAscii(String str){
        boolean flag = false;
        if(str != null){
            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i)>128){
                return false;
                }                   
            }
            flag = true;
        }
        return flag;
    }
    
    private static boolean isBasePairValid(String basePairStart,
			String basePairEnd) {

		int intBasePairStart;
		int intBasePairEnd;
		logger.debug("Start " + basePairStart + " End " + basePairEnd);
		try {
			intBasePairStart = Integer.parseInt(basePairStart);
			intBasePairEnd = Integer.parseInt(basePairEnd);

		} catch (NumberFormatException e) {
			return false;
		}

		if (intBasePairStart >= intBasePairEnd)
			return false;
		return true;
	}
    
    public static List<String> validateGeneSymbol(GeneValidator bean,
            List<String> errors) throws Exception{
    	
        String gene = bean.getGeneSymbol();
//      see if geneSymbol can't be found, if it cannot look for aliases
	    if(!DataValidator.isGeneSymbolFound(gene)){
			AllGeneAliasLookup[] allGeneAlias = DataValidator.searchGeneKeyWord(gene);
			// if there are aliases , set the array to be displayed in the form and return the showAlias warning
			if(allGeneAlias != null){
			    bean.setAllGeneAlias(allGeneAlias);
				for(int i =0; i < allGeneAlias.length ; i++){
					AllGeneAliasLookup alias = allGeneAlias[i];
					logger.debug(alias.getAlias()+"\t"+alias.getApprovedSymbol()+"\t"+alias.getApprovedName()+"\n");
					if(errors.isEmpty()){//add only one error message
						errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.quicksearch.showAlias"));
									
					}
				}
			}
			// if there are no aliases, we don't have record, so show noRecord error message
			else{
			    logger.debug("no aliases found \n");
                if(gene.indexOf("*")< 0){ //no wild cards
    			    errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.quicksearch.improveSearch"));
    							
                }
                else{
                  errors.add(ApplicationContext.getLabelProperties().getProperty("gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord"));

                }
			}
		}
	    //if gene Symbol can be found , execute query
		else{
		    logger.debug(gene+" found! \n");
      }
	    return errors;
    }
   
}
