package gov.nih.nci.nautilus.query;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.criteria.*;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
import gov.nih.nci.nautilus.de.GeneOntologyDE;
import gov.nih.nci.nautilus.de.PathwayDE;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExprQueryHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class GeneExpressionQuery extends Query {

    private AllGenesCriteria allGenes;
    private static Logger logger = Logger.getLogger(GeneExpressionQuery.class);
    private GeneIDCriteria geneIDCrit;
    private SampleCriteria sampleIDCrit;
    private RegionCriteria regionCrit;
    private FoldChangeCriteria foldChgCrit;
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;
	private GeneOntologyCriteria geneOntologyCriteria;
	private PathwayCriteria pathwayCriteria;
	private ArrayPlatformCriteria arrayPlatformCriteria;
    private QueryHandler HANDLER;

    public QueryHandler getQueryHandler() throws Exception  {
        return (HANDLER == null) ? new GeneExprQueryHandler() : HANDLER;
    }
	public QueryType getQueryType() throws Exception {
		return QueryType.GENE_EXPR_QUERY_TYPE;
	}

    public GeneExpressionQuery() {
        super();
    }
    public String toString(){
		ResourceBundle labels = null;
		String OutStr = "<B>Gene Expression Query</B>";
		OutStr += "<BR><B class='otherBold'>Query Name: </b>" + this.getQueryName();


	try {

	    labels = ResourceBundle.getBundle(NautilusConstants.APPLICATION_RESOURCES, Locale.US);
	
		FoldChangeCriteria thisFoldChangeCrit = this.getFoldChgCrit();
			
		if ((thisFoldChangeCrit != null) && !thisFoldChangeCrit.isEmpty() && labels != null) {
			String thisCriteria = thisFoldChangeCrit.getClass().getName();
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			Collection foldChangeObjects = thisFoldChangeCrit.getFoldChangeObjects();		
			
			for (Iterator iter = foldChangeObjects.iterator(); iter.hasNext();) {
				DomainElement de = (DomainElement) iter.next();
				String thisDomainElement = de.getClass().getName();
				OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
			}
		}
		else {
		 logger.debug("Fold Change Criteria is empty or Application Resources file is missing");
		}
		GeneIDCriteria thisGeneIDCrit = this.getGeneIDCrit();
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
		
		SampleCriteria thisSampleIDCrit = this.getSampleIDCrit();
		
		if ((thisSampleIDCrit != null) && !thisSampleIDCrit.isEmpty() && labels != null ) { 
			String thisCriteria = thisSampleIDCrit.getClass().getName();
			
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
			Collection sampleIDObjects = thisSampleIDCrit.getSampleIDs();
			int count = 0;
			for (Iterator iter = sampleIDObjects.iterator(); iter.hasNext() && count < 5;) {
				count++;
				DomainElement de = (DomainElement) iter.next();
				String thisDomainElement = de.getClass().getName();
				OutStr += "<BR>&nbsp;&nbsp;" + labels.getString(thisDomainElement.substring(thisDomainElement.lastIndexOf(".")+1)) +": "+de.getValue();
			}
			if(sampleIDObjects != null && sampleIDObjects.size()> 5){
			OutStr +="<BR>&nbsp;&nbsp;...";
			}
		}
		else logger.debug("Sample ID Criteria is empty or Application Resources file is missing");
		
			RegionCriteria thisRegionCrit = this.getRegionCrit();
			if ((thisRegionCrit != null) && !thisRegionCrit.isEmpty() && labels != null) { 
				String thisCriteria = thisRegionCrit.getClass().getName();
				logger.debug("thisCriteria is :"+ thisCriteria);
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
			   }
		
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
		  } // end of DiseaseOrGradeCriteria  
		
		
		// starting cloneorProbeCriteria
				
		CloneOrProbeIDCriteria thisCloneOrProbeCriteria = this.getCloneOrProbeIDCriteria();		 
		if((thisCloneOrProbeCriteria != null) && !thisCloneOrProbeCriteria.isEmpty() && labels != null){		   
		  	String thisCriteria = thisCloneOrProbeCriteria.getClass().getName();			
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
		    Collection cloneColl = thisCloneOrProbeCriteria.getIdentifiers();
		    Iterator iter = cloneColl.iterator();
		    int count = 0;
		    while(iter.hasNext() && count < 5){
		    	count ++;
			    CloneIdentifierDE cloneIdentifierDE = (CloneIdentifierDE)iter.next();
				String cloneStr = cloneIdentifierDE.getClass().getName();
			    OutStr += "<BR>&nbsp;&nbsp;"+labels.getString(cloneStr.substring(cloneStr.lastIndexOf(".")+1))+": "+cloneIdentifierDE.getValue()+"";
			  }			 
			if(cloneColl != null && cloneColl.size()> 5){
				OutStr +="<BR>&nbsp;&nbsp;...";
				} 
		    }
		else{
		    logger.debug("Clone or Probe Criteria is empty or Application Resources file is missing.");
		}// end of  cloneorProbeCriteria
		
		//starting of GeneOntologyCriteria
	   GeneOntologyCriteria thisGeneOntologyCriteria = this.getGeneOntologyCriteria();
	   if((thisGeneOntologyCriteria != null) && !thisGeneOntologyCriteria.isEmpty() && labels != null){
	    Collection goColl = thisGeneOntologyCriteria.getGOIdentifiers();
		Iterator iter = goColl.iterator();
		while(iter.hasNext()){
		  GeneOntologyDE geneOntologyDE = (GeneOntologyDE)iter.next();
		  String goStr = geneOntologyDE.getClass().getName();
		  OutStr += "<BR>"+labels.getString(goStr.substring(goStr.lastIndexOf(".")+1))+": "+geneOntologyDE.getValue()+"";
		  }	        
	   }
	   else {
	       logger.debug("GeneOntolgoy Criteria is empty or Application Resources file is missing.");
	       }// end of GeneOntologyCriteria

		   
		   // starting PathwayCriteria
		PathwayCriteria thisPathwayCriteria = this.getPathwayCriteria();
	
		if ((thisPathwayCriteria != null) && !thisPathwayCriteria.isEmpty() && labels != null) { 

			String thisCriteria = thisPathwayCriteria.getClass().getName();			
			OutStr += "<BR><B class='otherBold'>"+labels.getString(thisCriteria.substring(thisCriteria.lastIndexOf(".")+1))+ "</B>";
		    Collection pathwayColl = thisPathwayCriteria.getPathwayNames();
			Iterator iter = pathwayColl.iterator();
			while(iter.hasNext()){
			  PathwayDE  pathwayDE = (PathwayDE)iter.next();
			  String pathwayStr = pathwayDE.getClass().getName();		      
		      OutStr += "<BR>&nbsp;&nbsp;"+pathwayDE.getValue();
		       }	 	   
		   }
		else{
		    logger.debug("PathwayCriteria is empty or Application Resources file is missing");
		  
         }
		 
		
      /* TODO: Prashant Can you please fix this commented code.  I have changed the
        PathwayCriteria class definition So the below code needs to be readjusted
        */
	  /*
        // start of pathway criteria
	  PathwayCriteria thisPathwayCriteria = this.getPathwayCriteria();
	  if(!thisPathwayCriteria.isEmpty() && labels != null){
	    PathwayDE pathwayDE = thisPathwayCriteria.getPathwayNames();
		String pathwayStr = pathwayDE.getClass().getName();
		OutStr += "<BR>"+labels.getString(pathwayStr.substring(pathwayStr.lastIndexOf(".")+1))+": "+pathwayDE.getValue()+"";
		   }
		else{
		   logger.debug("PathwayCriteria is empty or Application Resources file is missing.");
	  	 }  // end of PathwayCriteria
	 */

        
	// start of ArrayPlatformCriteria
	 ArrayPlatformCriteria thisArrayPlatformCriteria = this.getArrayPlatformCriteria();
	 if((thisArrayPlatformCriteria != null) && !thisArrayPlatformCriteria.isEmpty()&& labels != null){
	   ArrayPlatformDE arrayPlatformDE = thisArrayPlatformCriteria.getPlatform();
	   String arrayPlatformStr = arrayPlatformDE.getClass().getName();
	   OutStr += "<BR><B class='otherBold'>"+labels.getString(arrayPlatformStr.substring(arrayPlatformStr.lastIndexOf(".")+1))+"</B>";
	   OutStr += "<BR>&nbsp;&nbsp;"+arrayPlatformDE.getValue()+" ";
		   }
	 else{
	     logger.debug("ArrayPlatformCriteria is empty or Application Resources file is missing.");
	  	 }  // end of PathwayCriteria
	}// end of try
	catch (Exception ie) {
		logger.error("Error in ResourceBundle - ");
		logger.error(ie);
        ie.printStackTrace();
	}

		OutStr += "<BR><BR>";
    	return OutStr;
    }

    public AllGenesCriteria getAllGenes() {
        return allGenes;
    }
    public void setAllGenes(AllGenesCriteria allGenes) {
        this.allGenes = allGenes;
    }
    public GeneIDCriteria getGeneIDCrit() {
        return geneIDCrit;
    }

    public void setGeneIDCrit(GeneIDCriteria geneIDCrit) {
        this.geneIDCrit = geneIDCrit;
    }
    
    public SampleCriteria getSampleIDCrit() {
        return sampleIDCrit;
    }

    public void setSampleIDCrit(SampleCriteria sampleIDCrit) {
        this.sampleIDCrit = sampleIDCrit;
    }

    public RegionCriteria getRegionCrit() {
        return regionCrit;
    }

    public void setRegionCrit(RegionCriteria regionCrit) {
        this.regionCrit = regionCrit;
    }

    public FoldChangeCriteria getFoldChgCrit() {
        return foldChgCrit;
    }

    public void setFoldChgCrit(FoldChangeCriteria foldChgCrit) {
        this.foldChgCrit = foldChgCrit;
    }


	 public CloneOrProbeIDCriteria  getCloneOrProbeIDCriteria() {
        return cloneOrProbeIDCriteria;
    }

    public void setCloneOrProbeIDCrit(CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
        this.cloneOrProbeIDCriteria = cloneOrProbeIDCriteria;
    }

	public GeneOntologyCriteria  getGeneOntologyCriteria() {
        return geneOntologyCriteria;
    }

    public void setGeneOntologyCrit(GeneOntologyCriteria geneOntologyCriteria) {
        this.geneOntologyCriteria = geneOntologyCriteria;
    }

	public PathwayCriteria  getPathwayCriteria() {
        return pathwayCriteria;
    }

    public void setPathwayCrit(PathwayCriteria pathwayCriteria) {
        this.pathwayCriteria = pathwayCriteria;
    }

	public ArrayPlatformCriteria  getArrayPlatformCriteria() {
        return arrayPlatformCriteria;
    }

    public void setArrayPlatformCrit(ArrayPlatformCriteria arrayPlatformCriteria) {
        this.arrayPlatformCriteria = arrayPlatformCriteria;
    }
    class Handler {
    }
}
