package gov.nih.nci.nautilus.queryprocessing.cgh;

import gov.nih.nci.nautilus.query.Query;
import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;


import gov.nih.nci.nautilus.criteria.*;


import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;
import gov.nih.nci.nautilus.de.SNPIdentifierDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.QueryHandler;
import gov.nih.nci.nautilus.queryprocessing.DBEvent;
import gov.nih.nci.nautilus.queryprocessing.ThreadController;
import gov.nih.nci.nautilus.queryprocessing.ge.ChrRegionCriteriaHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.CloneProbePlatfromHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneExprQueryHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.GeneIDCriteriaHandler;
import gov.nih.nci.nautilus.data.SnpProbesetDim;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.data.CloneDim;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.ManageableCollection;
import org.apache.ojb.broker.accesslayer.OJBIterator;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

import java.util.*;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 20, 2004
 * Time: 3:14:46 PM
 * To change this template use Options | File Templates.
 */
public class CGHQueryHandler extends QueryHandler {

	//DiseaseOrGradeCriteria diseaseOrGradeCrit;
	//GeneIDCriteria geneIDCrit;
	//CopyNumberCriteria copyNumberCrit;
	//CGHReporterIDCriteria  regionCrit;
    //CGHReporterIDCriteria  snpCrit;
	//CloneOrProbeIDCriteria cloneOrProbeIDCrit;
	//SNPCriteria snpCrit;
	//AlleleFrequencyCriteria alleleFrequencyCrit;
    //AssayPlatformCriteria assayPlatformCrit;
    boolean includeCGH;
    boolean includeSNPs;
    private List eventList = Collections.synchronizedList(new ArrayList());

    private Collection allSNPProbesetIDs = Collections.synchronizedCollection(new HashSet());
    public ResultSet[] handle(Query query) throws Exception{
        ComparativeGenomicQuery cghQuery = (ComparativeGenomicQuery) query;

        PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        populateIncludeCGHAndSNPFlags(cghQuery.getAssayPlatformCriteria());

        if (cghQuery.getGeneIDCriteria() != null) {
            CGHReporterIDCriteria geneIDCrit = GeneIDCriteriaHandler.buildReporterIDCritForCGHQuery(cghQuery.getGeneIDCriteria(), includeSNPs, includeCGH, pb);
            assert(geneIDCrit != null);
            SelectHandler handler = new SelectHandler.GeneIDSelectHandler(geneIDCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }
        if (cghQuery.getRegionCriteria() != null) {
            CGHReporterIDCriteria  regionCrit = ChrRegionCriteriaHandler.buildCGHRegionCriteria(cghQuery.getRegionCriteria(), includeSNPs, includeCGH, pb);
            assert(regionCrit != null);
            SelectHandler handler = new SelectHandler.RegionSelectHandler(regionCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }

        if (cghQuery.getSNPCriteria() != null) {
            CGHReporterIDCriteria  snpCrit = buildSNPCriteria(cghQuery.getSNPCriteria(), pb);
            assert(snpCrit != null);
            SelectHandler handler = new SelectHandler.SNPSelectHandler(snpCrit, allSNPProbesetIDs);
            eventList.add(handler.getDbEvent());
            new Thread(handler).start();
        }


        if(cghQuery.getCloneOrProbeIDCriteria() != null) {
            throw new Exception (" Only BACClone will be implemented post Nautilus ");
        }

        ThreadController.sleepOnEvents(eventList);

        return new CGHFactHandler.SingleCGHFactHandler().executeSampleQuery(allSNPProbesetIDs, cghQuery);

    }

    private CGHReporterIDCriteria buildSNPCriteria(SNPCriteria c, PersistenceBroker pb) throws Exception {
        String snpType = getType(c);
        Collection inputIDs = new ArrayList();
        ReportQueryByCriteria snpProbeIDsQuery = null;

        for (Iterator iterator = c.getIdentifiers().iterator(); iterator.hasNext();)
             inputIDs.add(((SNPIdentifierDE) iterator.next()).getValueObject());

        String nameCol  = null;
        if (snpType.equals(SNPIdentifierDE.DBSNP)) {
           nameCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.DB_SNP_ID );
        }
        else if (snpType.equals(SNPIdentifierDE.SNP_PROBESET)) {
           nameCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.PROBESET_NAME);
        }

        Criteria sCrit = new Criteria();
        /*for (Iterator iterator = inputIDs.iterator(); iterator.hasNext();) {
            String id = (String) iterator.next();
            Criteria c1 = new Criteria();
            c1.addLike(nameCol, id + "%");
            sCrit.addOrCriteria(c1);
        }*/

        sCrit.addColumnIn(nameCol, inputIDs);
        String snpProbeIDCol = QueryHandler.getColumnNameForBean(pb, SnpProbesetDim.class.getName(), SnpProbesetDim.SNP_PROBESET_ID);

        snpProbeIDsQuery =  QueryFactory.newReportQuery(SnpProbesetDim.class, new String[] {snpProbeIDCol}, sCrit, true);
        CGHReporterIDCriteria reporterIDCrit = new CGHReporterIDCriteria ();
        if (includeSNPs) {
            reporterIDCrit.setSnpProbeIDsSubQuery(snpProbeIDsQuery);
        }
        if (includeCGH) {
           // TODO: Post Nautilus
        }
        return reporterIDCrit;
    }

    public static String getType(SNPCriteria snpCrit) {
        if (snpCrit != null ) {
            Collection snpIDs = snpCrit.getIdentifiers();
             if (snpIDs != null && snpIDs.size() > 0) {
                 SNPIdentifierDE obj =  (SNPIdentifierDE) snpIDs.iterator().next();
                 return obj.getSNPType();
             }
        }
        return "";
    }
    private void populateIncludeCGHAndSNPFlags(AssayPlatformCriteria assayPlatformCrit)
    throws Exception {
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
         }
        else throw new Exception("Array Platform can not be null");
    }

}
