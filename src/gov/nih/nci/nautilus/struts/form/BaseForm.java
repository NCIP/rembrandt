// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Bad file descriptor))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl
package gov.nih.nci.nautilus.struts.form;



import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.lookup.LookupManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;





/** 
 * GeneExpressionForm.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 08-11-2004
 * 
 * XDoclet definition:
 * @struts:form name="geneExpressionForm"
 */
public class BaseForm extends ActionForm {
    
    private static Logger logger = Logger.getLogger(BaseForm.class);
		
	// Collections used for Lookup values.  
	private ArrayList diseaseType;
	private ArrayList geneTypeColl;
    private String method;
	

	// --------------------------------------------------------- Methods
	public BaseForm(){
			
		// Create Lookups for Gene Expression screens 
		setLookups();

	}

	protected boolean isBasePairValid(String basePairStart, String basePairEnd) {

		int intBasePairStart;
		int intBasePairEnd;
		logger.debug("Start "+basePairStart+" End "+basePairEnd);
		try {
			intBasePairStart = Integer.parseInt(basePairStart);
			intBasePairEnd = Integer.parseInt(basePairEnd);
			
		}
		catch (NumberFormatException e) {
			return false;
		}
		
		if (intBasePairStart >= intBasePairEnd) return false;
		return true;
	}
	
	public void setLookups() {

		diseaseType = new ArrayList();
		geneTypeColl = new ArrayList();
	
		// These are hardcoded but will come from DB
		diseaseType.add( new LabelValueBean( "All", "ALL" ) );
		diseaseType.add( new LabelValueBean( "Astrocytic", "ASTROCYTOMA" ) );
		diseaseType.add( new LabelValueBean( "Oligodendroglial", "OLIG" ) );
		diseaseType.add( new LabelValueBean( "Mixed gliomas", "MIXED" ) );
		diseaseType.add( new LabelValueBean( "Glioblastoma", "GBM" ));

		//diseaseType.add( new LabelValueBean( "Ependymal cell", "Ependymal cell" ) );
		//diseaseType.add( new LabelValueBean( "Neuroepithelial", "Neuroepithelial" ) );
		//diseaseType.add( new LabelValueBean( "Choroid Plexus", "Choroid Plexus" ) );
		//diseaseType.add( new LabelValueBean( "Neuronal and mixed neuronal-glial", "neuronal-glial" ) );
		//diseaseType.add( new LabelValueBean( "Pineal Parenchyma", "Pineal Parenchyma" ));
		//diseaseType.add( new LabelValueBean( "Embryonal", "Embryonal" ));

		
		//geneTypeColl.add( new LabelValueBean( "All Genes", "allgenes" ) );
		geneTypeColl.add( new LabelValueBean( "Name/Symbol", "genesymbol" ) );
		geneTypeColl.add( new LabelValueBean( "Locus Link Id", "genelocus" ) );
		geneTypeColl.add( new LabelValueBean( "GenBank AccNo.", "genbankno" ) );

	}

// Getter methods for Gene Expression Lookup
	public ArrayList getDiseaseType() 
	{ return diseaseType; }

	public ArrayList getGeneTypeColl() 
	{ return geneTypeColl; }

	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Collection getChromosomeValue(){

			Collection returnColl = new ArrayList();
			ChromosomeNumberDE[] chromosomes;
			try {
				chromosomes = LookupManager.getChromosomeDEs();
				TreeSet chrNum = new TreeSet();
				TreeSet chrStr = new TreeSet();

				if(chromosomes != null){
					for(int i =0; i < chromosomes.length; i++){

						try {
							chrNum.add(new Integer(chromosomes[i].getValueObject()));
						}catch(NumberFormatException ex){
							chrStr.add(chromosomes[i].getValueObject());
						}

					}
				}
				returnColl.add(new LabelValueBean("",""));
				for (Iterator iter = chrNum.iterator(); iter.hasNext();) {
					String idxValue = ((Integer)iter.next()).toString(); 
					returnColl.add(new LabelValueBean(idxValue, idxValue));
				}
				for (Iterator iter = chrStr.iterator(); iter.hasNext();) {
					String idxValue = (String) iter.next();
					returnColl.add(new LabelValueBean(idxValue, idxValue));
				}
			} catch (Exception e) {
			    logger.error("Error reading Chromosome values from table:");
			    logger.error(e);
			}
			return returnColl;

		}

	public HashMap getCytoBandForChr(){
		
			Collection chrValues = this.getChromosomeValue();
			HashMap cytobandCollections = new HashMap();
			
			for (Iterator chrVal = chrValues.iterator(); chrVal.hasNext();) {
				LabelValueBean lbl = (LabelValueBean) chrVal.next();
				String thisChromosome = lbl.getValue();
				if (thisChromosome.length() > 0){
					try {
						CytobandDE[] cytobands = LookupManager.getCytobandDEs(new ChromosomeNumberDE(thisChromosome));
						String cytoString = "";
						for (int cytoIndex = 1; cytoIndex < cytobands.length; cytoIndex++) {
							if (cytoString.length() > 0) cytoString += ",";
							cytoString += '"'+cytobands[cytoIndex].getValueObject()+'"';
						}
						cytobandCollections.put(thisChromosome,cytoString);
					
					}catch(Exception ex){
					    logger.error("Error reading Cytobands from table");
					    logger.error(ex);
					}
				}
			}
			return cytobandCollections;
	}
	/**
     * <p>Checks whether the string is ASCII 7 bit.</p>
     *
     * @param str  the string to check
     * @return false if the string contains a char that is greater than 128
     */
	public boolean isAscii(String str){
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
}
