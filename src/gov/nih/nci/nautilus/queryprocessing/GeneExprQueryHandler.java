package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;


import gov.nih.nci.nautilus.criteria.FoldChangeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.GeneOntologyCriteria;
import gov.nih.nci.nautilus.criteria.PathwayCriteria;
import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;

import gov.nih.nci.nautilus.de.GeneIdentifierDE;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:14:46 PM
 * To change this template use Options | File Templates.
 */
public class GeneExprQueryHandler implements QueryHandler {
    FoldChangeCriteria foldCrit;
    GeneIDCriteria  geneIDCrit;
    RegionCriteria regionCrit;
	DiseaseOrGradeCriteria diseaseOrGradeCrit;
	CloneOrProbeIDCriteria cloneOrProbeIDCrit;
	GeneOntologyCriteria geneOntologyCrit;
	PathwayCriteria pathwayCrit;
	ArrayPlatformCriteria arrayPlatformCrit;


    public void handle(Query query) {
        GeneExpressionQuery geQuery = (GeneExpressionQuery) query;
        foldCrit = geQuery.getFoldChgCrit();
        geneIDCrit = geQuery.getGeneIDCrit();
        regionCrit = geQuery.getRegionCrit();
        diseaseOrGradeCrit = geQuery.getDiseaseOrGradeCriteria();
        cloneOrProbeIDCrit = geQuery.getCloneOrProbeIDCriteria();
        geneOntologyCrit = geQuery.getGeneOntologyCriteria();
        pathwayCrit = geQuery.getPathwayCriteria();
        arrayPlatformCrit = geQuery.getArrayPlatformCriteria();

        Collection geneIdDEs = geneIDCrit.getGeneIdentifiers();
        for (Iterator iterator = geneIdDEs.iterator(); iterator.hasNext();) {
            GeneIdentifierDE o = (GeneIdentifierDE) iterator.next();
            if (o instanceof GeneIdentifierDE.LocusLink) {
                System.out.println("LocuLink: " + o.getValueObject());
            }
        }
    }
}
