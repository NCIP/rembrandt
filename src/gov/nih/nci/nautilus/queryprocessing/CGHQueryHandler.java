package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.criteria.AlleleFrequencyCriteria;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.CopyNumberCriteria;
import gov.nih.nci.nautilus.criteria.DiseaseOrGradeCriteria;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.criteria.RegionCriteria;
import gov.nih.nci.nautilus.criteria.SNPCriteria;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.resultset.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:14:46 PM
 * To change this template use Options | File Templates.
 */
public class CGHQueryHandler extends QueryHandler {

	DiseaseOrGradeCriteria diseaseOrGradeCrit;
	GeneIDCriteria geneIDCrit;
	CopyNumberCriteria copyNumberCrit;
	RegionCriteria regionCrit;
	CloneOrProbeIDCriteria cloneOrProbeIDCrit;
	SNPCriteria snpCrit;
	AlleleFrequencyCriteria alleleFrequencyCrit;
    AssayPlatformCriteria assayPlatformCrit;

    public ResultSet[] handle(Query query) {
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) query;

        diseaseOrGradeCrit = cghQuery.getDiseaseOrGradeCriteria();
        geneIDCrit = cghQuery.getGeneIDCriteria();
        copyNumberCrit = cghQuery.getCopyNumberCriteria();
        regionCrit = cghQuery.getRegionCriteria();
        cloneOrProbeIDCrit = cghQuery.getCloneOrProbeIDCriteria();
        snpCrit = cghQuery.getSNPCriteria();
        alleleFrequencyCrit = cghQuery.getAlleleFrequencyCriteria();
        assayPlatformCrit = cghQuery.getAssayPlatformCriteria();

/*
        if (cghQuery.getGeneIDCriteria() != null && cghQuery.getGeneIDCriteria().getGeneIdentifiers().size() > 0) {
            geneIDCrit = GeneIDCriteriaHandler.buildGeneIDCriteria(cghQuery.getGeneIDCriteria());
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allProbeIDS, allCloneIDS, _BROKER);
            factEventList.add(handler.getDbEvent());
            new Thread(tg, handler).start();
        }

*/      //if (cghQuery.get)
        return null;
    }
}
