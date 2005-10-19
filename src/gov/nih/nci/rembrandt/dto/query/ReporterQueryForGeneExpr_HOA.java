/**
 * 
 */
package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.query.Query;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

/**
 * @author sahnih
 *
 */
public class ReporterQueryForGeneExpr_HOA extends Query {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CloneOrProbeIDCriteria cloneOrProbeIDCriteria;
	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.query.Query#getQueryHandler()
	 */
	@Override
	public QueryHandler getQueryHandler() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.query.Query#getQueryType()
	 */
	@Override
	public QueryType getQueryType() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caintegrator.dto.query.Query#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Returns the cloneOrProbeIDCriteria.
	 */
	public CloneOrProbeIDCriteria getCloneOrProbeIDCriteria() {
		return cloneOrProbeIDCriteria;
	}

	/**
	 * @param cloneOrProbeIDCriteria The cloneOrProbeIDCriteria to set.
	 */
	public void setCloneOrProbeIDCriteria(
			CloneOrProbeIDCriteria cloneOrProbeIDCriteria) {
		this.cloneOrProbeIDCriteria = cloneOrProbeIDCriteria;
	}

}
