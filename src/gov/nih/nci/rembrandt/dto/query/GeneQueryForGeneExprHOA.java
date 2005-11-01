package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

public class GeneQueryForGeneExprHOA extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GeneIDCriteria geneIDCriteria;
	@Override
	public QueryHandler getQueryHandler() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryType getQueryType() throws Exception {
		return QueryType.GENE_QUERY_FOR_HOA;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Returns the geneIDCriteria.
	 */
	public GeneIDCriteria getGeneIDCriteria() {
		return geneIDCriteria;
	}

	/**
	 * @param geneIDCriteria The geneIDCriteria to set.
	 */
	public void setGeneIDCriteria(GeneIDCriteria geneIDCriteria) {
		this.geneIDCriteria = geneIDCriteria;
	}

}
