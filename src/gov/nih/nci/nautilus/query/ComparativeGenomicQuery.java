package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.de.AlleleFrequencyDE;
import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ComparativeGenomicQuery extends Query {
    
    private static Logger logger = Logger.getLogger(ComparativeGenomicQuery.class);
    private GeneIDCriteria geneIDCriteria;
    private CopyNumberCriteria copyNumberCriteria;
	private RegionCriteria regionCriteria;	
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;
	private SNPCriteria snpCriteria;
	private AlleleFrequencyCriteria alleleFrequencyCriteria;
	private AssayPlatformCriteria assayPlatformCriteria;
    private QueryHandler HANDLER;

    public QueryHandler getQueryHandler() throws Exception  {
        return (HANDLER == null) ? new gov.nih.nci.nautilus.queryprocessing.cgh.CGHQueryHandler() : HANDLER;
    }
	public QueryType getQueryType() throws Exception {
		return QueryType.CGH_QUERY_TYPE;
	}

    public ComparativeGenomicQuery() {
        super();
    }
    public String toString(){
		ResourceBundle labels = null;
		String OutStr = "<B>Comparative Genomic Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>" + this.getQueryName();


	try {

	    labels = ResourceBundle.getBundle(NautilusConstants.APPLICATION_RESOURCES, Locale.US);
	  
	    // starting DiseaseOrGradeCriteria
		DiseaseOrGradeCriteria thisDiseaseCrit = this.getDiseaseOrGradeCriteria();	
		if ((thisDiseaseCrit != null) && !thisDiseaseCrit.isEmpty() && labels != null) { 
		    Collection diseaseColl = thisDiseaseCrit.getDiseases();
			String thisCriteria = thisDiseaseCrit.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+"</B><BR>";

			Iterator iter = diseaseColl.iterator();
			while(iter.hasNext()){
			  DiseaseNameDE  diseaseDE = (DiseaseNameDE)iter.next();
			  OutStr += "&nbsp;&nbsp;"+((String) diseaseDE.getValue())+" ";
			   }	 	   
		   }
		else{
		    logger.debug("Disease Criteria is empty or Application Resources file is missing");
		  } //end of DiseaseOrGradeCriteria
		
		 
		 // starting CopyNumberCriteria
		CopyNumberCriteria thisCopyNumberCrit = this.getCopyNumberCriteria();	
			
		if ((thisCopyNumberCrit != null) && !thisCopyNumberCrit.isEmpty() && labels != null) {
		    logger.debug(" I am in the CopyNumberCriteria");
			String thisCriteria = thisCopyNumberCrit.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			Collection copyNoObjects = thisCopyNumberCrit.getCopyNummbers();
			
			for (Iterator iter = copyNoObjects.iterator(); iter.hasNext();) {
				DomainElement de = (DomainElement) iter.next();
				String thisDomainElement = de.getClass().getName();
				OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
			}
		}
		else {
		    logger.debug("Copy Number Criteria is empty or Application Resources file is missing");
           }  // end of CopyNumberCriteria
		   
		GeneIDCriteria thisGeneIDCrit = this.getGeneIDCriteria();
		if ((thisGeneIDCrit != null) && !thisGeneIDCrit.isEmpty() && labels != null) { 
			String thisCriteria = thisGeneIDCrit.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			Collection geneIDObjects = thisGeneIDCrit.getGeneIdentifiers();
			int count = 0;
			for (Iterator iter = geneIDObjects.iterator(); iter.hasNext() && count < 5;) {
				count++;
				DomainElement de = (DomainElement) iter.next();
				String thisDomainElement = de.getClass().getName();
				OutStr += "<BR>&nbsp;&nbsp;" + labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
			}
			if(geneIDObjects != null && geneIDObjects.size()> 5){
			OutStr +="<BR>&nbsp;&nbsp;...";
			}
		}
		else logger.debug("Gene ID Criteria is empty or Application Resources file is missing");


			// starting RegionCriteria
			RegionCriteria thisRegionCrit = this.getRegionCriteria();
			if ((thisRegionCrit != null) &&!thisRegionCrit.isEmpty() && labels != null) { 
				String thisCriteria = thisRegionCrit.getClass().getName();
				OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+"</B>";
				DomainElement cytoBandDE  = thisRegionCrit.getCytoband();

				DomainElement chromosomeDE  = thisRegionCrit.getChromNumber();
				DomainElement chrStartDE  = thisRegionCrit.getStart();
				DomainElement chrEndDE  = thisRegionCrit.getEnd();
				
				if (chromosomeDE != null) {
					String chromosomeDEStr = chromosomeDE.getClass().getName();
					OutStr += "<BR>&nbsp;&nbsp;"+ labels.getString(chromosomeDEStr.substring(chromosomeDEStr.lastIndexOf(".")+1)) +": "+chromosomeDE.getValue();

					if (cytoBandDE != null) {
						String cytoBandStr = cytoBandDE.getClass().getName();
						OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(cytoBandStr.substring(cytoBandStr.lastIndexOf(".")+1)) +": "+cytoBandDE.getValue();
					}else{
						if (chrStartDE != null && chrEndDE != null) {
							String chrStartDEStr = chrStartDE.getClass().getName();
							String chrEndDEStr = chrEndDE.getClass().getName();
							OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(chrStartDEStr.substring(chrStartDEStr.lastIndexOf(".")+1, chrStartDEStr.lastIndexOf("$")))+"(kb)";
							OutStr += "<BR>&nbsp;&nbsp;&nbsp;" + labels.getString(chrStartDEStr.substring(chrStartDEStr.lastIndexOf(".")+1)) +": "+chrStartDE.getValue();
							OutStr += "<BR>&nbsp;&nbsp;&nbsp;" + labels.getString(chrEndDEStr.substring(chrEndDEStr.lastIndexOf(".")+1)) +": "+chrEndDE.getValue();
						}
					}
				}
			 }
			else {
			    logger.debug("Region Criteria is empty or Application Resources file is missing");
			    }// end of RegionCriteria
			
		   // starting cloneorProbeCriteria
				
		   CloneOrProbeIDCriteria thisCloneOrProbeCriteria = this.getCloneOrProbeIDCriteria();		 
		   if((thisCloneOrProbeCriteria != null) && !thisCloneOrProbeCriteria.isEmpty() && labels != null){		   
			  	String thisCriteria = thisCloneOrProbeCriteria.getClass().getName();			
				OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			    Collection cloneColl = thisCloneOrProbeCriteria.getIdentifiers();
			    Iterator iter = cloneColl.iterator();
			    int count =0;
			    while(iter.hasNext() && count > 5){
				    CloneIdentifierDE cloneIdentifierDE = (CloneIdentifierDE)iter.next();
					String cloneStr = cloneIdentifierDE.getClass().getName();
				    OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(cloneStr.substring(cloneStr.lastIndexOf(".")+1))+": "+cloneIdentifierDE.getValue()+"";
				  }
			    if(cloneColl != null && cloneColl.size()> 5){
					OutStr +="<BR>&nbsp;and&nbsp;...";
					} 
			    }
			else{
			    logger.debug("Clone or Probe Criteria is empty or Application Resources file is missing.");
			}// end of  cloneorProbeCriteria
			
						
			 // starting snpCriteria: 				
		   SNPCriteria thisSNPCriteria = this.getSNPCriteria();		 
		   if((thisSNPCriteria != null ) && !thisSNPCriteria.isEmpty() && labels != null){	
		        String thisCriteria = thisSNPCriteria.getClass().getName();			
				OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			    Collection cloneColl = thisSNPCriteria.getIdentifiers();
			    Iterator iter = cloneColl.iterator();
			    int count = 0;
			    while(iter.hasNext() && count < 5){
			    	count ++;
				    SNPIdentifierDE snpIdentifierDE = (SNPIdentifierDE)iter.next();
					String snpIdStr = snpIdentifierDE.getClass().getName();
				    OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(snpIdStr.substring(snpIdStr.lastIndexOf(".")+1))+": "+snpIdentifierDE.getValue()+"";
					 }		
			    if(cloneColl != null && cloneColl.size()> 5){
			    	OutStr +="<BR>&nbsp;&nbsp;...";
					} 
			    }	
		      	else{
		      	  logger.debug("SNP Criteria is empty or Application Resources file is missing.");
			}// end of  cloneorProbeCriteria
			
			
			 // starting AlleleFrequencyCriteria: 
			 				
		   AlleleFrequencyCriteria thisAlleleFrequencyCriteria = this.getAlleleFrequencyCriteria();		 
		   if((thisAlleleFrequencyCriteria != null ) && !thisAlleleFrequencyCriteria.isEmpty() && labels != null){	
		      	AlleleFrequencyDE alleleFrequencyDE = thisAlleleFrequencyCriteria.getAlleleFrequencyDE();				
				String alleleStr = alleleFrequencyDE.getClass().getName();

				OutStr += "<BR><B class='otherBold'>"+labels.getString(alleleStr.substring(alleleStr.lastIndexOf(".")+1))+"</B>";
				OutStr += "<BR>&nbsp;&nbsp;"+alleleFrequencyDE.getValue();
				  	   
			    }
			else{
			    logger.debug("SNP Criteria is empty or Application Resources file is missing.");
			}// end of  AlleleFrequencyCriteria
			
			// starting AssayPlatformCriteria
		   AssayPlatformCriteria thisAssayPlatformCriteria = this.getAssayPlatformCriteria();
		    if((thisAssayPlatformCriteria != null) && !thisAssayPlatformCriteria.isEmpty() && labels != null){	
			  AssayPlatformDE assayPlatformDE = thisAssayPlatformCriteria.getAssayPlatformDE();
			  String assayStr = assayPlatformDE.getClass().getName();
			  OutStr += "<BR><B class='otherBold'>"+labels.getString(assayStr.substring(assayStr.lastIndexOf(".")+1))+"</B>";
			  OutStr += "<BR>&nbsp;&nbsp;" + assayPlatformDE.getValue();
			
		   }
		  else{
		      logger.debug("AssayPlatform Criteria is empty or Application Resources file is missing.");
		    } 
		}// end of try
	catch (Exception ie) {
		logger.error("Error in ResourceBundle in CGH query - ");
		logger.error(ie);
	}

		OutStr += "<BR><BR>";
    	return OutStr;
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
	
	public AssayPlatformCriteria getAssayPlatformCriteria() {
        return assayPlatformCriteria;
    }

    public void setAssayPlatformCrit(AssayPlatformCriteria assayPlatformCriteria) {
        this.assayPlatformCriteria = assayPlatformCriteria;
    }
    class Handler {
    }
}
