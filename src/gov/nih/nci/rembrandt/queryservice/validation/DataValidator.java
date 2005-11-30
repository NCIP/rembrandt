package gov.nih.nci.rembrandt.queryservice.validation;

import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.dbbean.AllGeneAlias;
import gov.nih.nci.rembrandt.dbbean.CloneDim;
import gov.nih.nci.rembrandt.dbbean.GEPatientData;
import gov.nih.nci.rembrandt.dbbean.GeneLlAccSnp;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;
import gov.nih.nci.rembrandt.dto.lookup.AllGeneAliasLookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;

/**
 * This class provide validation functionalty for Genes, Reporters and Samples
 * 
 * @author SahniH
 */
public class DataValidator{
    private static Logger logger = Logger.getLogger(DataValidator.class);

	
	
	/**
	 * Performs the actual lookup query.  Gets the application
	 * PersistanceBroker and then passes to the 
	 * @param bean the lookup class 
	 * @param crit the criteria for the lookup
	 * @return the collection of lookup values
	 * @throws Exception
	 */
	 

    /*private static void getAllGeneAlias() throws Exception{
    	Criteria crit = new Criteria();
		Collection allGeneAlias = executeQuery(AllGeneAlias.class, (Criteria)crit,LookupManager.ALLGENEALIAS,true);
		geneSymbols =  new HashSet();
		for (Iterator iterator = allGeneAlias.iterator(); iterator.hasNext();) {
			AllGeneAlias geneAlias = (AllGeneAlias) iterator.next();
			geneSymbols.add(geneAlias.getApprovedSymbol().trim());
		 }
    }*/
    public static boolean isGeneSymbolFound(String geneSymbol) throws Exception{

    	if(geneSymbol != null  ){
            
            if(geneSymbol.indexOf("*")!= -1 || geneSymbol.indexOf("%") != -1){
                return false;         //make sure your not checking for wildcards
            }
            try {
        	//Create a Criteria for Approved Symbol
            Criteria approvedSymbolCrit = new Criteria();
            approvedSymbolCrit.addLike("upper(approvedSymbol)",geneSymbol.toUpperCase());
            Collection geneCollection;
	    		
	    			geneCollection = QueryExecuter.executeQuery(AllGeneAlias.class, approvedSymbolCrit,QueryExecuter.NO_CACHE,true);

		    		if(geneCollection != null && geneCollection.size() == 1){
		            	return true;
		            }
	    		} catch (Exception e) {
	    			logger.error("Error in geneCollection when searching for "+geneSymbol);
	    			logger.error(e.getMessage());
	    			return false;
	    		}
    	}
    	return false;
    }
    public static Collection<SampleIDDE> validateSampleIds(Collection<SampleIDDE> sampleIds) throws Exception{
    	Collection<SampleIDDE> validSampleList = new ArrayList<SampleIDDE>();
    	if(sampleIds != null  ){
            

            try {
        	//Create a Criteria for Approved Symbol
           
            Collection<String> values = new ArrayList<String>();
            for (SampleIDDE sampleId : sampleIds){
                if(sampleId.getValueObject().indexOf("*")!= -1 || sampleId.getValueObject().indexOf("%") != -1){
                    throw new Exception("Sample Id"+ sampleId+ "contains * or %");         //make sure your not checking for wildcards
                }
                values.add(sampleId.getValueObject().toUpperCase());
            }


            Collection sampleCollection;
            Criteria sampleCrit = new Criteria();
            sampleCrit.addIn("upper(sampleId)",values);	
            sampleCollection = QueryExecuter.executeQuery(GEPatientData.class, sampleCrit,QueryExecuter.NO_CACHE,true);
            	if(sampleCollection != null){
            		 for (Object obj : sampleCollection){
            			 if(obj instanceof GEPatientData){
            				 GEPatientData pateintData = (GEPatientData) obj;
            				 validSampleList.add(new SampleIDDE(pateintData.getSampleId()));
            			 }
            		 }
            	}

	    		} catch (Exception e) {
	    			logger.error("Error in validateSampleIds");
	    			logger.error(e.getMessage());
	    			throw e;
	    		}
    	}
    	return validSampleList;
    }
    public static Collection<CloneIdentifierDE> validateReporters(Collection<CloneIdentifierDE> reporterIds) throws Exception{
    	List<CloneIdentifierDE> validList = new ArrayList<CloneIdentifierDE>();
	   	String type = null;
        Collection collection = null;
    	if(reporterIds != null  ){


            try {
        	//Create a Criteria for Approved Symbol
            Criteria crit = new Criteria();
            Collection<String> values = new ArrayList<String>();
            for (CloneIdentifierDE reporterId : reporterIds){
            	type = reporterId.getCloneIDType();
            	values.add(reporterId.getValueObject().toUpperCase());
            	}

            if(type != null && type.equals(CloneIdentifierDE.IMAGE_CLONE)){
            	crit.addIn("upper(cloneName)",values);
            	collection = QueryExecuter.executeQuery(CloneDim.class, crit,QueryExecuter.NO_CACHE,true);
            }
            else if (type != null && type.equals(CloneIdentifierDE.PROBE_SET)){
            	crit.addIn("upper(probesetName)",values);
            	collection = QueryExecuter.executeQuery(ProbesetDim.class, crit,QueryExecuter.NO_CACHE,true);
            }
            	if(collection != null){
            		 for (Object obj : collection){
            			 if(obj instanceof CloneDim){
            				 CloneDim reporter = (CloneDim) obj;
            				 validList.add(new CloneIdentifierDE.IMAGEClone(reporter.getCloneName()));
            			 }
            			 else if(obj instanceof ProbesetDim){
            				 ProbesetDim reporter = (ProbesetDim) obj;
            				 validList.add(new CloneIdentifierDE.ProbesetID(reporter.getProbesetName()));
            			 }
            		 }
            	}

	    		} catch (Exception e) {
	    			logger.error("Error in validateReporters");
	    			logger.error(e.getMessage());
	    			throw e;
	    		}
    	}
    	return validList;
    }
    public static Collection<GeneIdentifierDE> validateGenes(Collection<GeneIdentifierDE> geneList) throws Exception{
    	Collection<GeneIdentifierDE> validList = new ArrayList<GeneIdentifierDE>();
	   	String type = null;
        Collection collection = null;
    	if(geneList != null  ){


            try {
        	//Create a Criteria for Approved Symbol
            Criteria crit = new Criteria();
            Collection<String> values = new ArrayList<String>();
            for (GeneIdentifierDE geneID : geneList){
            	type = geneID.getGeneIDType();
            	values.add(geneID.getValueObject().toUpperCase());
            }

            if(type != null && type.equals(GeneIdentifierDE.GENESYMBOL)){
            	crit.addIn("upper(approvedSymbol)",values);
            	collection = QueryExecuter.executeQuery(AllGeneAlias.class, crit,QueryExecuter.NO_CACHE,true);
            }
            else if (type != null && type.equals(GeneIdentifierDE.GENBANK_ACCESSION_NUMBER)){
            	crit.addIn("upper(accession)",values);            	 
            	collection = QueryExecuter.executeQuery(GeneLlAccSnp.class, crit,QueryExecuter.NO_CACHE,true);
            }
            else if (type != null && type.equals(GeneIdentifierDE.LOCUS_LINK)){
            	crit.addIn("upper(llId)",values);            	 
            	collection = QueryExecuter.executeQuery(GeneLlAccSnp.class, crit,QueryExecuter.NO_CACHE,true);
            }
            	if(collection != null){
            		 for (Object obj : collection){
            			 if(obj instanceof AllGeneAliasLookup){
            				 AllGeneAliasLookup gene = (AllGeneAliasLookup) obj;
            				 validList.add(new GeneIdentifierDE.GeneSymbol(gene.getApprovedSymbol()));
            			 }
            			
            			 else if(obj instanceof GeneLlAccSnp  && type.equals(GeneIdentifierDE.GENBANK_ACCESSION_NUMBER)){
            				 GeneLlAccSnp reporter = (GeneLlAccSnp) obj;
            				 validList.add(new GeneIdentifierDE.GenBankAccessionNumber(reporter.getAccession()));
            			 }
            			 else if(obj instanceof GeneLlAccSnp  && type.equals(GeneIdentifierDE.LOCUS_LINK)){
            				 GeneLlAccSnp reporter = (GeneLlAccSnp) obj;
            				 validList.add(new GeneIdentifierDE.LocusLink(reporter.getLlId()));
            			 }
            		
            		 }
            	}

	    		} catch (Exception e) {
	    			logger.error("Error in validateReporters");
	    			logger.error(e.getMessage());
	    			throw e;
	    		}
    	}
    	return validList;
    }
	@SuppressWarnings("unchecked")
	public static AllGeneAliasLookup[] searchGeneKeyWord(String geneKeyWord){
    	if(geneKeyWord != null){
            try {
                logger.debug("inside searchGeneKeyWord");
		    	//Create a Criteria for Approved Symbol
		        Criteria approvedSymbolCrit = new Criteria();
		        approvedSymbolCrit.addLike("upper(approvedSymbol)",geneKeyWord.toUpperCase());
		        //Create a Criteria for Alias
		        Criteria aliasCrit = new Criteria();
		        aliasCrit.addLike("upper(alias)",geneKeyWord.toUpperCase());
		        //Create a Criteria for Approved Name
		        Criteria approvedNameCrit = new Criteria();
		        approvedNameCrit.addLike("upper(approvedName)",geneKeyWord.toUpperCase());
		        
		        //Or the three
		        approvedSymbolCrit.addOrCriteria(approvedNameCrit);
		        approvedSymbolCrit.addOrCriteria(aliasCrit);
		        Collection<AllGeneAliasLookup> allGeneAlias;
				
					allGeneAlias = QueryExecuter.executeQuery(AllGeneAlias.class, approvedSymbolCrit,QueryExecuter.NO_CACHE,true);
		
				if(allGeneAlias != null && allGeneAlias.size() > 0){
		        	return (AllGeneAliasLookup[]) allGeneAlias.toArray(new AllGeneAliasLookup[allGeneAlias.size()]);
		        }
			return null;
			} catch (Exception e) {
				logger.error("Error in AllGeneAliasLookup when searching for "+geneKeyWord);
				logger.error(e.getMessage());
				return null;
			}
    	}
		return null;
    }
}
