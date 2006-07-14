package gov.nih.nci.rembrandt.web.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.apache.log4j.Logger;

import gov.nih.nci.caintegrator.application.lists.ListSubType;
import gov.nih.nci.caintegrator.application.lists.ListType;
import gov.nih.nci.caintegrator.application.lists.ListValidator;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SNPIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.rembrandt.queryservice.validation.DataValidator;
import gov.nih.nci.rembrandt.service.findings.strategies.StrategyHelper;

public class RembrandtListValidator implements ListValidator{
    private static Logger logger = Logger.getLogger(RembrandtListValidator.class);
	private ListType listType;
	private ListSubType listSubType;
	private List<String> unvalidatedList;
	private List<String> validatedList = new ArrayList<String>();
	private List<String> invalidList = new ArrayList<String>();;
	public RembrandtListValidator(){};
	
	public RembrandtListValidator(ListSubType listSubType, ListType listType, List<String> unvalidatedList) {
		super();
		this.listSubType = listSubType;
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
	}
	public RembrandtListValidator(ListType listType, List<String> unvalidatedList) {
		super();
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
	}
	
    public List getValidList(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {
    	validatedList.clear();
    	invalidList.clear();
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return getValidList();
    }

    public List getInvalidList(ListType listType, List<String> unvalidatedList) throws OperationNotSupportedException {
    	validatedList.clear();
    	invalidList.clear();
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return getInvalidList();
    }
    public List getValidList(ListType listType, ListSubType listSubType, List<String> unvalidatedList) throws OperationNotSupportedException {
    	validatedList.clear();
    	invalidList.clear();
		this.listSubType = listSubType;
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return getValidList();
    }

    public List getInvalidList(ListType listType, ListSubType listSubType, List<String> unvalidatedList) throws OperationNotSupportedException {
    	validatedList.clear();
    	invalidList.clear();
		this.listSubType = listSubType;
		this.listType = listType;
		this.unvalidatedList = unvalidatedList;
        return getInvalidList();
    }

	public List getValidList() throws OperationNotSupportedException {
		//check if this has been run before
		if(validatedList.isEmpty() && invalidList.isEmpty()){
			switch(listType){
			case PatientDID:
				try {
					Collection<SampleIDDE> samples = ListConvertor.convertToSampleIDDEs(unvalidatedList);
					samples = DataValidator.validateSampleIds(samples);
					validatedList = new ArrayList<String>();
					validatedList.addAll(StrategyHelper.extractSamples(samples));
					} catch (OperationNotSupportedException e) {
						validatedList.clear();
		    			logger.error("Error in getValidList");
		    			logger.error(e.getMessage());
		    			throw e;
					} catch (Exception e) {
						validatedList.clear();
		    			logger.error("Error in getValidList");
		    			logger.error(e.getMessage());
		    			throw new OperationNotSupportedException(e.getMessage());
					}
					break;
			}
			if(listSubType != null){
				switch(listSubType){
					case GENBANK_ACCESSION_NUMBER:
					case GENESYMBOL:
					case LOCUS_LINK:
						try {
						Collection<GeneIdentifierDE> genes = ListConvertor.convertToGeneIdentifierDE(unvalidatedList, listSubType);
						genes = DataValidator.validateGenes(genes);
						validatedList = new ArrayList<String>();
						validatedList.addAll(StrategyHelper.extractGenes(genes));
						} catch (OperationNotSupportedException e) {
							validatedList.clear();
			    			logger.error("Error in getValidList");
			    			logger.error(e.getMessage());
			    			throw e;
						} catch (Exception e) {
							validatedList.clear();
			    			logger.error("Error in getValidList");
			    			logger.error(e.getMessage());
			    			throw new OperationNotSupportedException(e.getMessage());
						}
					break;
					case PROBE_SET:
					case IMAGE_CLONE:
						try {
							Collection<CloneIdentifierDE> reporters = ListConvertor.convertToCloneIdentifierDE(unvalidatedList, listSubType);
							reporters = DataValidator.validateReporters(reporters);
							validatedList = new ArrayList<String>();
							validatedList.addAll(StrategyHelper.extractReporters(reporters));
							} catch (OperationNotSupportedException e) {
								validatedList.clear();
				    			logger.error("Error in getValidList");
				    			logger.error(e.getMessage());
				    			throw e;
							} catch (Exception e) {
								validatedList.clear();
				    			logger.error("Error in getValidList");
				    			logger.error(e.getMessage());
				    			throw new OperationNotSupportedException(e.getMessage());
							}
					break;
					case DBSNP:
					case SNP_PROBESET:
						try{
						Collection<SNPIdentifierDE> reporters = ListConvertor.convertToSNPIdentifierDE(unvalidatedList, listSubType);
						reporters = DataValidator.validateSNPReporters(reporters);
						validatedList = new ArrayList<String>();
						validatedList.addAll(StrategyHelper.extractSNPReporters(reporters));
						} catch (OperationNotSupportedException e) {
							validatedList.clear();
			    			logger.error("Error in getValidList");
			    			logger.error(e.getMessage());
			    			throw e;
						} catch (Exception e) {
							validatedList.clear();
			    			logger.error("Error in getValidList");
			    			logger.error(e.getMessage());
			    			throw new OperationNotSupportedException(e.getMessage());
						}
					break;
				}
			}
		}
		//return null;
		return validatedList;
	}

	public List getInvalidList() throws OperationNotSupportedException  {
		invalidList.addAll(unvalidatedList);
		try {
			invalidList.removeAll(getValidList());
		} catch (OperationNotSupportedException e) {
			invalidList.clear();
			throw e;
		}
		return invalidList;
	}

}
