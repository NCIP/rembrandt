package gov.nih.nci.rembrandt.queryservice;

import gov.nih.nci.caintegrator.dto.query.ResultSetInterface;
import gov.nih.nci.rembrandt.dto.query.Query;


public interface QueryHandlerInterface {

	public ResultSetInterface[] handle(Query query) throws Exception ;

}