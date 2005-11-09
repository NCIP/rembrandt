package gov.nih.nci.rembrandt.web.factory;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.service.findings.FindingsFactory;
import gov.nih.nci.rembrandt.cache.BusinessTierCache;
import gov.nih.nci.rembrandt.cache.BusinessCacheManager;
import gov.nih.nci.rembrandt.cache.PresentationCacheManager;
import gov.nih.nci.rembrandt.cache.PresentationTierCache;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQueryDTOImpl;
import gov.nih.nci.rembrandt.dto.query.PrincipalComponentAnalysisQueryDTOImpl;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;

public class ApplicationFactory{

	public static QueryDTO newQueryDTO(QueryType queryType) {
		if (queryType == QueryType.CLASS_COMPARISON_QUERY) {              
            return new ClassComparisonQueryDTOImpl();
        }else if (queryType == QueryType.PCA_QUERY) {              
            return new PrincipalComponentAnalysisQueryDTOImpl();
        }else {
        	return null;
        }
	}
	public static PresentationTierCache getPresentationTierCache() {
		return PresentationCacheManager.getInstance();
	}
	
	public static BusinessTierCache getBusinessTierCache() {
		return BusinessCacheManager.getInstance();
	}
	
	public static FindingsFactory getFindingsFactory() {
		return new RembrandtFindingsFactory();
	}

}
