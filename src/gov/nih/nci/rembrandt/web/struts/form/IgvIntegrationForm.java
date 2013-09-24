/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.rembrandt.web.helper.GroupRetriever;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class IgvIntegrationForm extends GpIntegrationForm {
	private String snpArrayPlatform;
	private String snpAnalysis;
	private String [] annotations ;
	private String [] selectedAnnotations ;
	private List annotationsList;

	public String getSnpArrayPlatform() {
		return snpArrayPlatform;
	}

	public void setSnpArrayPlatform(String snpArrayPlatform) {
		this.snpArrayPlatform = snpArrayPlatform;
	}

	public String getSnpAnalysis() {
		return snpAnalysis;
	}

	public void setSnpAnalysis(String snpAnalysis) {
		this.snpAnalysis = snpAnalysis;
	}

	public String[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(String[] annotations) {
		this.annotations = annotations;
	}

	public List getAnnotationsList() {
		return annotationsList;
	}

	public void setAnnotationsList(List annotationsList) {
		this.annotationsList = annotationsList;
	}

	public String[] getSelectedAnnotations() {
		return selectedAnnotations;
	}

	public void setSelectedAnnotations(String[] selectedAnnotations) {
		this.selectedAnnotations = selectedAnnotations;
	}
	
	   /**
	    * Method validate
	    * 
	    * @param ActionMapping
	    *            mapping
	    * @param HttpServletRequest
	    *            request
	    * @return ActionErrors
	    */
	   public ActionErrors validate(ActionMapping mapping,
	           HttpServletRequest request) {

	       ActionErrors errors = new ActionErrors();

	      // Validate group field
	       errors = UIFormValidator.validateSelectedOneGroup(selectedGroups, errors);
	       
	       if( getArrayPlatform() != null && getArrayPlatform().equals("on")) {
	    	   setArrayPlatform( ArrayPlatformType.AFFY_OLIGO_PLATFORM.toString() );
	       }
	       if( getSnpArrayPlatform() != null && getSnpArrayPlatform().equals("on")) {
	    	   setSnpArrayPlatform( ArrayPlatformType.AFFY_100K_SNP_ARRAY.toString() );
	       }

	       // Validate platform field
	       errors = UIFormValidator.validateSelectedOnePlatform(getArrayPlatform(), getSnpArrayPlatform(), errors);

	       
	       // Validate annotations field
//	       errors = UIFormValidator.validateSelectedOneAnnotation(selectedAnnotations, errors);
	       
	      
	       return errors;
	   }
	   
	   public void reset(ActionMapping mapping, HttpServletRequest request) {            
	        super.reset(mapping, request);
	        
	        /*setup the defined Disease query names and the list of samples selected from a Resultset*/
	       GroupRetriever groupRetriever = new GroupRetriever();
	        setAnnotationsList(groupRetriever.getClinicalGroupsCollection(request.getSession()));         
	    
	  }
	

}
