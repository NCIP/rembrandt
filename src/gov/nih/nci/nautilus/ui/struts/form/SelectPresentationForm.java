
package gov.nih.nci.nautilus.ui.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import java.util.*;
import gov.nih.nci.nautilus.query.*;
import gov.nih.nci.nautilus.constants.*;
import gov.nih.nci.nautilus.view.*;
import gov.nih.nci.nautilus.de.*;


public class SelectPresentationForm extends BaseForm {

	private static Logger logger = Logger
			.getLogger(SelectPresentationForm.class);

	// --------------------------------------------------------- Instance
	// Variables
	private String method;

	private String currentView;

	private String[] ListFrom;

	private String[] ListTo;

	private Viewable selectedView;

	private DomainElementClass[] selectedElements;

	private ArrayList viewPresentList;

	// --------------------------------------------------------- Methods
	public SelectPresentationForm() {
		super();
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
		return errors;
	}

	/**
	 * Method reset. Reset all properties to their default values.
	 * 
	 * @param ActionMapping
	 *            mapping used to select this instance.
	 * @param HttpServletRequest
	 *            request The servlet request we are processing.
	 */

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		method = "";
		selectedView = getSelectedView(request);
		currentView = initialView(); // Default View is sample
		viewPresentList = getPresentationList();

	}

	private Viewable getSelectedView(HttpServletRequest request) {

		// Set, Reset the correct currentView, default is sample view
		QueryCollection queryCollect = (QueryCollection) request.getSession()
				.getAttribute(NautilusConstants.QUERY_KEY);
		Viewable thisView = ViewFactory.newView(ViewType.CLINICAL_VIEW); // Default
																		 // View
																		 // if
																		 // no
																		 // View
																		 // set

		if (queryCollect != null) {
			if (queryCollect.hasCompoundQuery()) {
				// Get View from Query Collection that user selected
				thisView = queryCollect.getCompoundQuery().getAssociatedView();
			} else
				logger
						.debug("SelectPresentationForm - QueryCollection does not have a compoundQuery");
		} else
			logger.debug("SelectPresentationForm - Query Collection is null");
		return thisView;
	}

	private String initialView() {

		String currentView = "sample"; // Default View is sample

		try {
			if (selectedView instanceof GeneExprSampleView)
				currentView = "geneSample";
			if (selectedView instanceof GeneExprDiseaseView)
				currentView = "geneDisease";
			else if (selectedView instanceof CopyNumberSampleView)
				currentView = "copynumber";
			else if (selectedView instanceof ClinicalSampleView)
				currentView = "sample";
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}

		return currentView;

	}

	private ArrayList getPresentationList() {

		ArrayList presentationList = new ArrayList();

		DomainElementClass[] domainElements = ((View) this.selectedView)
				.getValidElements();
		selectedElements = domainElements;
		for (int i = 0; i < domainElements.length; i++) {
			presentationList.add(new LabelValueBean(domainElements[i]
					.getLabel(), String.valueOf(i)));
		}
		return presentationList;

	}

	//	private ArrayList getGenePresentList(){
	//		
	//		ArrayList geneList = new ArrayList();
	//		geneList.add( new LabelValueBean( "Gene Symbol", "genesymbol" ) );
	//		geneList.add( new LabelValueBean( "Gene Name/Description", "genename" )
	// );
	//		geneList.add( new LabelValueBean( "Accession", "accession" ) );
	//		geneList.add( new LabelValueBean( "Locuslink ID", "locuslinkid" ) );
	//		geneList.add( new LabelValueBean( "Cytoband", "cytoband" ) );
	//		geneList.add( new LabelValueBean( "Physical Chromosomal map(Start/End)",
	// "chromosomalmap" ) );
	//		geneList.add( new LabelValueBean( "Pathway", "pathway" ) );
	//		geneList.add( new LabelValueBean( "GO ID", "goid" ) );
	//		geneList.add( new LabelValueBean( "GO Description", "godescription" ) );
	//		geneList.add( new LabelValueBean( "Reporter ID(Probeset/Image Clone ID",
	// "reporterid" ) );
	//		
	//		return geneList;
	//		
	//	}
	//
	//	private ArrayList getCopyPresentList(){
	//		
	//		ArrayList copyList = new ArrayList();
	//		copyList.add( new LabelValueBean( "SNP Probeset ID", "snpprobeset" ) );
	//		copyList.add( new LabelValueBean( "DBSNP ID", "dbsnpid" ) );
	//		copyList.add( new LabelValueBean( "TSC ID", "tscid" ) );
	//		copyList.add( new LabelValueBean( "Allele Frequency", "allelefreq" ) );
	//		copyList.add( new LabelValueBean( "Associated gene(s)", "assocgenes" ) );
	//		copyList.add( new LabelValueBean( "Cytoband", "cytoband" ) );
	//		copyList.add( new LabelValueBean( "Physical Chromosomal position",
	// "physicalchrpos" ) );
	//		
	//		return copyList;
	//		
	//	}
	//	
	//	private ArrayList getSamplePresentList(){
	//		
	//		ArrayList sampleList = new ArrayList();
	//		sampleList.add( new LabelValueBean( "Disease", "disease" ) );
	//		sampleList.add( new LabelValueBean( "Grade", "grade" ) );
	//		sampleList.add( new LabelValueBean( "Survival Range", "survival" ) );
	//		sampleList.add( new LabelValueBean( "Gender", "gender" ) );
	//		sampleList.add( new LabelValueBean( "Age at Diagnosis", "age" ) );
	//		sampleList.add( new LabelValueBean( "Generating Institute",
	// "geninstitute" ) );
	//		sampleList.add( new LabelValueBean( "Occurance", "occurance" ) );
	//		sampleList.add( new LabelValueBean( "gexpdata", "gexpdata" ) );
	//		sampleList.add( new LabelValueBean( "copynumdata", "Copy Number data" )
	// );
	//		
	//		return sampleList;
	//		
	//	}

	public String getMethod() {

		return "";
	}

	public void setMethod(String method) {

		this.method = method;
	}

	/**
	 * Returns the sampleListFrom.
	 * 
	 * @return String
	 */
	public String[] getListFrom() {

		return ListFrom;
	}

	/**
	 * Set the sampleListFrom.
	 * 
	 * @param sampleList
	 *            The sampleList to set
	 */
	public void setListFrom(String[] listFrom) {
		this.ListFrom = listFrom;

	}

	/**
	 * Returns the sampleListFrom.
	 * 
	 * @return String
	 */
	public String[] getListTo() {

		return ListTo;
	}

	/**
	 * Set the sampleListFrom.
	 * 
	 * @param sampleList
	 *            The sampleList to set
	 */
	public void setListTo(String[] listTo) {
		this.ListTo = listTo;

	}

	public ArrayList getViewPresentList() {
		return viewPresentList;
	}

	public String getCurrentView() {
		return currentView;
	}

	public DomainElementClass[] getSelectedElements() {
		return this.selectedElements;
	}

}