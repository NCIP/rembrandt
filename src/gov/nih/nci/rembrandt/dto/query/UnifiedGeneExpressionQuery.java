package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.critieria.AllGenesCriteria;
import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneOntologyCriteria;
import gov.nih.nci.caintegrator.dto.critieria.PathwayCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;
import gov.nih.nci.caintegrator.dto.de.GeneOntologyDE;
import gov.nih.nci.caintegrator.dto.de.PathwayDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.GeneExprQueryHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.UnifiedGeneExprQueryHandler;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class UnifiedGeneExpressionQuery extends Query implements Serializable,Cloneable{
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */

	private Logger logger = Logger.getLogger(UnifiedGeneExpressionQuery.class);

	private GeneIDCriteria geneIDCrit;

	private FoldChangeCriteria foldChgCrit;

	private QueryHandler HANDLER;

	public QueryHandler getQueryHandler() throws Exception {
		return (HANDLER == null) ? new UnifiedGeneExprQueryHandler() : HANDLER;
	}

	public QueryType getQueryType() throws Exception {
		return QueryType.UNIFIED_GENE_EXPR_QUERY_TYPE;
	}

	public UnifiedGeneExpressionQuery() {
		super();
	}

    public String toString() {
        return null;
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


	public FoldChangeCriteria getFoldChgCrit() {
		return foldChgCrit;
	}

	public void setFoldChgCrit(FoldChangeCriteria foldChgCrit) {
		this.foldChgCrit = foldChgCrit;
	}


	/**
	 * Overrides the protected Object.clone() method exposing it as public.
	 * It performs a 2 tier copy, that is, it does a memcopy of the instance
	 * and then sets all the non-primitive data fields to clones of themselves.
	 * 
	 * @return -A minimum 2 deep copy of this object.
	 */
	public Object clone() {
		UnifiedGeneExpressionQuery myClone = null;
		myClone = (UnifiedGeneExpressionQuery) super.clone();
        if(foldChgCrit != null){
		myClone.foldChgCrit = (FoldChangeCriteria) foldChgCrit.clone();
        }
        if(geneIDCrit != null){
		myClone.geneIDCrit = (GeneIDCriteria) geneIDCrit.clone();
        }
		return myClone;
	}

	class Handler {
	}
}
