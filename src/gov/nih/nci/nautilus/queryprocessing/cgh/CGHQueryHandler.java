package gov.nih.nci.nautilus.queryprocessing.cgh;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;


import gov.nih.nci.nautilus.criteria.*;


import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.ChrRegionCriteriaHandler;
import gov.nih.nci.nautilus.data.SNPProbesetDim;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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
	//CloneOrProbeIDCriteria cloneOrProbeIDCrit;
	SNPCriteria snpCrit;
	//AlleleFrequencyCriteria alleleFrequencyCrit;
    AssayPlatformCriteria assayPlatformCrit;
    boolean includeCGH;
    boolean includeSNPs;

    private Collection allSNPProbesetIDs = Collections.synchronizedCollection(new HashSet());
    public ResultSet[] handle(Query query) throws Exception{
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) query;

        diseaseOrGradeCrit = cghQuery.getDiseaseOrGradeCriteria();
        geneIDCrit = cghQuery.getGeneIDCriteria();
        copyNumberCrit = cghQuery.getCopyNumberCriteria();
        regionCrit = cghQuery.getRegionCriteria();
        snpCrit = cghQuery.getSNPCriteria();
        assayPlatformCrit = cghQuery.getAssayPlatformCriteria();


        populateIncludeCGHAndSNPFlags();
        PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        ChrRegionCriteriaHandler.StartEndPosition posObj = ChrRegionCriteriaHandler.getPositionObject(regionCrit, pb);


        String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SNPProbesetDim.class.getName(), SNPProbesetDim.SNP_PROBESET_ID);
        String positionCol = QueryHandler.getColumnNameForBean(pb, SNPProbesetDim.class.getName(), SNPProbesetDim.PHYSICAL_POSITION);
        String chrCol = QueryHandler.getColumnNameForBean(pb, SNPProbesetDim.class.getName(), SNPProbesetDim.CHROMOSOME);

        Criteria c = new Criteria();
        c.addColumnEqualTo(chrCol, posObj.getChrNumber().getValueObject());
        c.addGreaterOrEqualThan(positionCol, new Long(posObj.getStartPosition().getValueObject().longValue()));
        c.addLessOrEqualThan(positionCol, new Long(posObj.getEndPosition().getValueObject().longValue()));

        ReportQueryByCriteria snpProbeIDQuery = QueryFactory.newReportQuery(SNPProbesetDim.class, new String[] {snpProbeIDCol}, c, true );

        return null;

    }

    private void populateIncludeCGHAndSNPFlags() throws Exception {
        if (assayPlatformCrit != null && assayPlatformCrit.getAssayPlatformDE() != null) {
            AssayPlatformDE platform = assayPlatformCrit.getAssayPlatformDE();
            if (platform.getValueObject().equalsIgnoreCase(Constants.AFFY_100K_SNP_ARRAY)) {
                includeSNPs = true;
            }
            /* TODO: Next release
            if (platform.getValueObject().equalsIgnoreCase(Constants.ARRAY_CGH)) {
                includeCGH = true;
            }
           */
            else throw new Exception("This Platform not currently not supported: " );
         } else throw new Exception("Array Platform can not be null");
    }

}
