package gov.nih.nci.rembrandt.web.factory;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.service.findings.FindingsFactory;
import gov.nih.nci.rembrandt.cache.CacheManagerDelegate;
import gov.nih.nci.rembrandt.cache.ConvenientCache;
import gov.nih.nci.rembrandt.dto.query.ClassComparisonQueryDTOImpl;
import gov.nih.nci.rembrandt.service.findings.RembrandtFindingsFactory;

public class ApplicationFactory{

	public static QueryDTO newQueryDTO(QueryType queryType) {
		if (queryType == QueryType.CLASS_COMPARISON_QUERY) {              
            return new ClassComparisonQueryDTOImpl();
        }else {
        	return null;
        }
	}
	
	public static ConvenientCache getCacheManager() {
		return CacheManagerDelegate.getInstance();
	}
	
	public static FindingsFactory getFindingsFactory() {
		return new RembrandtFindingsFactory();
	}

}
