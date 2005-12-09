package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;
import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;

/**
 * @author BauerD 
 * Dec 15, 2004 
 * This class is used to validate input fields from the UI
 *  
 */
public class UIFormValidator {
    private static Logger logger = Logger.getLogger(UIFormValidator.class);
    
    public static ActionErrors validateLDAP(String username, String password,
            ActionErrors errors) {
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
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
                "gov.nih.nci.nautilus.ui.struts.form.invalidLogin.error"));
        }
        return errors;
    }
    
    public static ActionErrors validateFormFieldsWithRegion(FormFile geneFile, String geneGroup, FormFile cloneListFile,
	        String cloneId, FormFile sampleFile, String sampleGroup, ActionErrors errors){
	    if (geneGroup.equalsIgnoreCase("Upload") && geneFile != null
	            || cloneId.equalsIgnoreCase("Upload") && cloneListFile != null
	               || sampleGroup.equalsIgnoreCase("Upload") && sampleFile != null){
	        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"gov.nih.nci.nautilus.ui.struts.form.region.uploadFormFiles"));
	    }	    
	    return errors;
	}
    public static ActionErrors validateGeneSymbolisNotEmpty(String geneSymbol,
			ActionErrors errors) {
	    
	    if (geneSymbol == null || geneSymbol.equals("") ) {
			errors
					.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(
									"gov.nih.nci.nautilus.ui.struts.form.quicksearch.emptyGene"));
		}
		return errors;
		/*
		 * else { try { Collection results = LookupManager.getGeneSymbols();
		 * }catch(Exception e){ e.printStackTrace(); } }
		 */
	}

	public static ActionErrors validateQueryName(String queryName,
			ActionErrors errors) {
		if ((queryName == null || queryName.length() < 1)) {
			errors.add("queryName", new ActionError(
					"gov.nih.nci.nautilus.ui.struts.form.queryname.no.error"));
		}
		return errors;
	}
    
    public static ActionErrors validateAnalysisName(String analysisResultName,
            ActionErrors errors) {
        if (analysisResultName.equals("")){
            errors.add("analysisResultName", new ActionError(
            "gov.nih.nci.nautilus.ui.struts.form.analysisResultName.no.error"));
    
        }
        return errors;
    }
    
    public static ActionErrors validateSelectedGroups(String[] selectedGroups, ActionErrors errors){
        if (selectedGroups == null || selectedGroups.length != 2){
            errors.add("selectedGroups", new ActionError(
                    "gov.nih.nci.nautilus.ui.struts.form.groups.more.error"));
        }
       

        return errors;
    }
    
    public static ActionErrors validateSelectedGroup(String groupsOption, String[] selectedGroups, ActionErrors errors){
        if (groupsOption.equalsIgnoreCase("variousSamples") && selectedGroups == null){
            errors.add("selectedGroups", new ActionError(
                    "gov.nih.nci.nautilus.ui.struts.form.group.no.error"));
        }
       

        return errors;
    }

	public static ActionErrors validateChromosomalRegion(String chrosomeNumber,
			String region, String cytobandRegionStart, String basePairStart,
			String basePairEnd, ActionErrors errors) {
		if (chrosomeNumber.trim().length() > 0) {
			if (region.trim().length() < 1)
				errors.add("chromosomeNumber", new ActionError(
						"gov.nih.nci.nautilus.ui.struts.form.region.no.error"));
			else {
				if (region.trim().equalsIgnoreCase("cytoband")) {
					if (cytobandRegionStart.trim().length() < 1)
						errors
								.add(
										"cytobandRegion",
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.form.cytobandregion.no.error"));
				}
				if (region.trim().equalsIgnoreCase("basePairPosition")) {
					if ((basePairStart.trim().length() < 1)
							|| (basePairEnd.trim().length() < 1)) {
						errors
								.add(
										"basePairEnd",
										new ActionError(
												"gov.nih.nci.nautilus.ui.struts.form.basePair.no.error"));
					} else {
						if (!isBasePairValid(basePairStart, basePairEnd)) {
							errors
									.add(
											"basePairEnd",
											new ActionError(
													"gov.nih.nci.nautilus.ui.struts.form.basePair.incorrect.error"));
						}
					}

				}

			}

		}
		return errors;

	}
    
    public static ActionErrors validateGOClassification(String goClassification, ActionErrors errors) {
        if (goClassification!= null  && goClassification.trim().length() > 0) {
            goClassification = goClassification.trim();
            if (goClassification.startsWith("GO:")) {
                String numberValue = goClassification.substring(goClassification.indexOf(":")+1);
                if (goClassification.length() == 10){
                    try {
                        int n = Integer.parseInt(numberValue);
                    } catch (NumberFormatException ne){
                        errors
                                .add(
                                        "goClassification",
                                        new ActionError(
                                                "gov.nih.nci.nautilus.ui.struts.form.go.numeric.error"));
                    }
                }else {
                    errors
                            .add(
                                    "goClassification",
                                    new ActionError(
                                            "gov.nih.nci.nautilus.ui.struts.form.go.length.error"));
                }
            }else {
                errors
                        .add(
                                "goClassification",
                                new ActionError(
                                        "gov.nih.nci.nautilus.ui.struts.form.go.startswith.error"));
            }
        }
    	return errors;
    }
    
    
    public static ActionErrors validateCopyNo(String copyNumber, String copyNo, String copyNoType, ActionErrors errors) {
        if (copyNumber != null && copyNo!= null  && copyNo.trim().length() > 0) {
        	copyNo = copyNo.trim();
        	try{
        		int n = Integer.parseInt(copyNo);
        	}
        	catch (NumberFormatException ne){
        		    
        		 errors.add(copyNoType,new ActionError("gov.nih.nci.nautilus.ui.struts.form.copyno.numeric.error"));
        	 
        	}
        }
    	return errors;
    }
    
    public static ActionErrors validate(String geneGroup, String geneList, FormFile geneFile, ActionErrors errors) {
        if (geneGroup!= null && geneGroup.trim().length() >= 1){
            if (geneList.trim().length() < 1 && geneFile == null){
                errors.add("geneGroup", new ActionError("gov.nih.nci.nautilus.ui.struts.form.geneGroup.no.error"));
            }
            
        }
        
        return errors;
    }
    
    public static ActionErrors validateTextFileType(FormFile formFile, String fileContents, ActionErrors errors) {
    //Make sure the uploaded File is of type txt and MIME type is text/plain
        if(formFile != null  &&
          (!(formFile.getFileName().endsWith(".txt"))) &&
          (!(formFile.getContentType().equals("text/plain")))){
            errors.add(fileContents, new ActionError(
                            "gov.nih.nci.nautilus.ui.struts.form.uploadFile.no.error"));
        }   
        
        return errors;
    }

	public static ActionErrors validateCloneId(String cloneId, String cloneListSpecify, FormFile cloneListFile, ActionErrors errors) {
         if (cloneId!= null && cloneId.trim().length() >= 1){
            if (cloneListSpecify.trim().length() < 1 && cloneListFile == null){
                errors.add("cloneId",new ActionError(
                                "gov.nih.nci.nautilus.ui.struts.form.cloneid.no.error"));
            }
            
        }
    
		return errors;
    }
    
    public static ActionErrors validateSnpId(String snpId, String snpList, FormFile snpListFile, ActionErrors errors) {
          if (snpId != null && snpId.trim().length() >= 1) {
            if (snpList.trim().length() < 1
                    && snpListFile == null) {
                errors.add("snpId", new ActionError(
                        "gov.nih.nci.nautilus.ui.struts.form.snpid.no.error"));
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
    
    public static ActionErrors validateGeneSymbol(GeneValidator bean, ActionErrors errors) throws Exception{
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
						errors
						   .add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError(
										"gov.nih.nci.nautilus.ui.struts.form.quicksearch.showAlias",
										gene));
					}
				}
			}
			// if there are no aliases, we don't have record, so show noRecord error message
			else{
			    logger.debug("no aliases found \n");
                if(gene.indexOf("*")< 0){ //no wild cards
    			    errors
    				   .add(
    						ActionErrors.GLOBAL_ERROR,
    						new ActionError(
    								"gov.nih.nci.nautilus.ui.struts.form.quicksearch.improveSearch",
    								"Gene Symbol/Keyword", gene));
                }
                else{
                    errors
                   .add(
                        ActionErrors.GLOBAL_ERROR,
                        new ActionError(
                                "gov.nih.nci.nautilus.ui.struts.form.quicksearch.noRecord",
                                "Gene Symbol/Keyword", gene));

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