package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;

import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.CGHQueryHandler;

import java.util.*;
import gov.nih.nci.nautilus.de.*;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:46:14 PM
 * To change this template use Options | File Templates.
 */
public class ComparativeGenomicQuery extends Query {

    private DiseaseOrGradeCriteria diseaseOrGradeCriteria;
    private GeneIDCriteria geneIDCriteria;
    private CopyNumberCriteria copyNumberCriteria;
	private RegionCriteria regionCriteria;	
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;
	private SNPCriteria snpCriteria;
	private AlleleFrequencyCriteria alleleFrequencyCriteria;
	private AssayPlatformCriteria assayPlatformCriteria;
	
	
    private QueryHandler HANDLER;

    public QueryHandler getQueryHandler() throws Exception  {
        return (HANDLER == null) ? new CGHQueryHandler() : HANDLER;
    }
	public QueryType getQueryType() throws Exception {
		return QueryType.CGH_QUERY_TYPE;
	}

    public ComparativeGenomicQuery() {
        super();
    }

 
    public String toString(){
    	

		ResourceBundle labels = null;
		String OutStr = "<B>CGH  Query</B>";
		OutStr += "<BR>Query: " + this.getQueryName();


	try {

		labels = ResourceBundle.getBundle("gov.nih.nci.nautilus.struts.ApplicationResources", Locale.US);
	  
	    // starting DiseaseOrGradeCriteria
		DiseaseOrGradeCriteria thisDiseaseCrit = this.getDiseaseOrGradeCriteria();	
		if (!thisDiseaseCrit.isEmpty() && labels != null) { 
		    Collection diseaseColl = thisDiseaseCrit.getDiseases();
			Iterator iter = diseaseColl.iterator();
			while(iter.hasNext()){
			  DiseaseNameDE  diseaseDE = (DiseaseNameDE)iter.next();
			  String diseaseStr = diseaseDE.getClass().getName();		      
		      OutStr += "<BR>"+labels.getString(diseaseStr.substring(diseaseStr.lastIndexOf(".")+1))+": "+diseaseDE.getValue()+"";
		       }	 	   
		   }
		else{
		   System.out.println("Disease Criteria is empty or Application Resources file is missing");
		  } //end of DiseaseOrGradeCriteria
		
		 
		 // starting CopyNumberCriteria
		CopyNumberCriteria thisCopyNumberCrit = this.getCopyNumberCriteria();	
		System.out.println("thisCopyNumberCrit.isEmpty():"+thisCopyNumberCrit.isEmpty());		
		if (!thisCopyNumberCrit.isEmpty() && labels != null) {
		    System.out.println(" I am in the CopyNumberCriteria");
			String thisCriteria = thisCopyNumberCrit.getClass().getName();
			OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			Collection copyNoObjects = thisCopyNumberCrit.getCopyNummbers();
			
			for (Iterator iter = copyNoObjects.iterator(); iter.hasNext();) {
				DomainElement de = (DomainElement) iter.next();
				String thisDomainElement = de.getClass().getName();
				OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
			}
		}
		else {
		   System.out.println("Copy Number Criteria is empty or Application Resources file is missing");
           }  // end of CopyNumberCriteria
		   
		   
            // starting GeneIDCriteria
			GeneIDCriteria thisGeneIDCrit = this.getGeneIDCriteria();
			if (!thisGeneIDCrit.isEmpty() && labels != null) { 
				String thisCriteria = thisGeneIDCrit.getClass().getName();
				OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
				Collection geneIDObjects = thisGeneIDCrit.getGeneIdentifiers();
			
				for (Iterator iter = geneIDObjects.iterator(); iter.hasNext();) {
					DomainElement de = (DomainElement) iter.next();
					String thisDomainElement = de.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;" + labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
				}
			}
			else {
			  System.out.println("Gene ID Criteria is empty or Application Resources file is missing");
               }// end of GeneIDCriteria
			   
			   
			// starting RegionCriteria
			RegionCriteria thisRegionCrit = this.getRegionCriteria();
			if (!thisRegionCrit.isEmpty() && labels != null) { 
				String thisCriteria = thisRegionCrit.getClass().getName();
				OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+"</B>";
				DomainElement cytoBandDE  = thisRegionCrit.getCytoband();

				DomainElement chromosomeDE  = thisRegionCrit.getChromNumber();
				DomainElement chrStartDE  = thisRegionCrit.getStart();
				DomainElement chrEndDE  = thisRegionCrit.getEnd();
				
				if (cytoBandDE != null) {
					String cytoBandStr = cytoBandDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(cytoBandStr.substring(cytoBandStr.lastIndexOf(".")+1)) +": "+cytoBandDE.getValue();
				}
				
				else {
					String chromosomeDEStr = chromosomeDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"+ labels.getString(chromosomeDEStr.substring(chromosomeDEStr.lastIndexOf(".")+1)) +": "+chromosomeDE.getValue();

					if (chrStartDE != null && chrEndDE != null) {
						String chrStartDEStr = chrStartDE.getClass().getName();
						String chrEndDEStr = chrEndDE.getClass().getName();
						OutStr += "<BR><B>"+labels.getString(chrStartDEStr.substring(chrStartDEStr.lastIndexOf(".")+1, chrStartDEStr.lastIndexOf("$")))+"(kb)</B>";
						OutStr += "<BR>&nbsp;&nbsp;" + labels.getString(chrStartDEStr.substring(chrStartDEStr.lastIndexOf(".")+1)) +": "+chrStartDE.getValue();
						OutStr += "<BR>&nbsp;&nbsp;" + labels.getString(chrEndDEStr.substring(chrEndDEStr.lastIndexOf(".")+1)) +": "+chrEndDE.getValue();
					}
		         }
			 }
			else {
			     System.out.println("Region Criteria is empty or Application Resources file is missing");
			    }// end of RegionCriteria
			
		   // starting cloneorProbeCriteria
				
		   CloneOrProbeIDCriteria thisCloneOrProbeCriteria = this.getCloneOrProbeIDCriteria();		 
		   if(!thisCloneOrProbeCriteria.isEmpty() && labels != null){		   
			  	String thisCriteria = thisCloneOrProbeCriteria.getClass().getName();			
				OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			    Collection cloneColl = thisCloneOrProbeCriteria.getIdentifiers();
			    Iterator iter = cloneColl.iterator();
			    while(iter.hasNext()){
				    CloneIdentifierDE cloneIdentifierDE = (CloneIdentifierDE)iter.next();
					String cloneStr = cloneIdentifierDE.getClass().getName();
				    OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(cloneStr.substring(cloneStr.lastIndexOf(".")+1))+": "+cloneIdentifierDE.getValue()+"";
				  }			   
			    }
			else{
			  System.out.println("Clone or Probe Criteria is empty or Application Resources file is missing.");
			}// end of  cloneorProbeCriteria
			
						
			 // starting snpCriteria: 				
		   SNPCriteria thisSNPCriteria = this.getSNPCriteria();		 
		   if(!thisSNPCriteria.isEmpty() && labels != null){	
		        String thisCriteria = thisSNPCriteria.getClass().getName();			
				OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			    Collection cloneColl = thisSNPCriteria.getIdentifiers();
			    Iterator iter = cloneColl.iterator();
			    while(iter.hasNext()){
				    SNPIdentifierDE snpIdentifierDE = (SNPIdentifierDE)iter.next();
					String snpIdStr = snpIdentifierDE.getClass().getName();
				    OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(snpIdStr.substring(snpIdStr.lastIndexOf(".")+1))+": "+snpIdentifierDE.getValue()+"";
					 }			   
			    }	
		      	else{
			  System.out.println("SNP Criteria is empty or Application Resources file is missing.");
			}// end of  cloneorProbeCriteria
			
			 // starting snpCriteria: 				
		   AlleleFrequencyCriteria thisAlleleFrequencyCriteria = this.getAlleleFrequencyCriteria();		 
		   if(!thisAlleleFrequencyCriteria.isEmpty() && labels != null){	
		        String thisCriteria = thisAlleleFrequencyCriteria.getClass().getName();	
				//OutStr += "<BR><B>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
				AlleleFrequencyDE alleleFrequencyDE = thisAlleleFrequencyCriteria.getAlleleFrequencyDE();				
				String alleleStr = alleleFrequencyDE.getClass().getName();
				OutStr += "<BR>"+labels.getString(alleleStr.substring(alleleStr.lastIndexOf(".")+1))+": "+alleleFrequencyDE.getValue()+"";
				  	   
			    }
			else{
			  System.out.println("SNP Criteria is empty or Application Resources file is missing.");
			}// end of  cloneorProbeCriteria
			
		}// end of try
	catch (Exception ie) {
		ie.printStackTrace();
		System.out.println("Error in ResourceBundle - " + ie.getMessage());
	}

		OutStr += "<BR><BR>";
    	return OutStr;
    }
    
	 public DiseaseOrGradeCriteria getDiseaseOrGradeCriteria() {
        return diseaseOrGradeCriteria;
    }

    public void setDiseaseOrGradeCrit(DiseaseOrGradeCriteria diseaseOrGradeCriteria) {
        this.diseaseOrGradeCriteria = diseaseOrGradeCriteria;
    }
		
    public GeneIDCriteria getGeneIDCriteria() {
        return geneIDCriteria;
    }

    public void setGeneIDCrit(GeneIDCriteria geneIDCriteria) {
        this.geneIDCriteria = geneIDCriteria;
    }

    public RegionCriteria getRegionCriteria() {
        return regionCriteria;
    }

    public void setRegionCrit(RegionCriteria regionCriteria) {
        this.regionCriteria = regionCriteria;
    }

    public CopyNumberCriteria getCopyNumberCriteria() {
        return copyNumberCriteria;
    }

    public void setCopyNumberCrit(CopyNumberCriteria copyNumberCriteria) {
        this.copyNumberCriteria = copyNumberCriteria;
    }

	
	 public CloneOrProbeIDCriteria  getCloneOrProbeIDCriteria() {
        return cloneOrProbeIDCriteria;
    }

    public void setCloneOrProbeIDCrit(CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
        this.cloneOrProbeIDCriteria = cloneOrProbeIDCriteria;
    }
	
	public SNPCriteria  getSNPCriteria() {
        return snpCriteria;
    }

    public void setSNPCrit(SNPCriteria snpCriteria) {
        this.snpCriteria = snpCriteria;
    }
	
	public AlleleFrequencyCriteria  getAlleleFrequencyCriteria() {
        return alleleFrequencyCriteria;
    }

    public void setAlleleFrequencyCrit(AlleleFrequencyCriteria alleleFrequencyCriteria) {
        this.alleleFrequencyCriteria = alleleFrequencyCriteria;
    }
	
	public AssayPlatformCriteria  getAssayPlatformCriteria() {
        return assayPlatformCriteria;
    }

    public void seAssayPlatformCrit(AssayPlatformCriteria assayPlatformCriteria) {
        this.assayPlatformCriteria = assayPlatformCriteria;
    }
    class Handler {
    }
}
