package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.CloneOrProbeIDCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.data.CloneAccession;
import gov.nih.nci.nautilus.data.CloneDim;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.CloneIdentifierDE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * @author BhattarR
 */
public class CloneProbePlatfromHandler {

    static  GEReporterIDCriteria buildCloneProbePlatformCriteria(CloneOrProbeIDCriteria cloneOrProbeCrit,  ArrayPlatformCriteria platCrit) throws Exception{
        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
        _BROKER.clearCache();

        GEReporterIDCriteria idsCriteria = new GEReporterIDCriteria();

        if (cloneOrProbeCrit != null ) {
            String probeType = getType(cloneOrProbeCrit);
            Collection inputCloneOrProbeDEs = cloneOrProbeCrit.getIdentifiers();
            if (inputCloneOrProbeDEs != null) {
                // convert ProbeNames/CloneNames into ProbesetIDs/CloneIDs
                ReportQueryByCriteria inputIDQuery = getCloneOrProbeIDsFromNameDEs(inputCloneOrProbeDEs, probeType, _BROKER);

                ArrayPlatformDE platObj = platCrit.getPlatform();
                if (platObj.getValueObject().equalsIgnoreCase(Constants.ALL_PLATFROM)) {
                    // use both cloneIDs and ProbesetIDs
                     if (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                        // this means inputIDs represent probesetIDs
                        ReportQueryByCriteria accessionQuery = getAccnsForProbeIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria cloneIDsQuery = getCloneIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(inputIDQuery);
                        idsCriteria.setCloneIDsSubQuery(cloneIDsQuery);
                     }
                     else if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE) ||
                             probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                        // this means inputIDs represent cloneIDs
                        ReportQueryByCriteria accessionQuery = getAccnsForCloneIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria probeIDsQuery = getProbeIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(probeIDsQuery);
                        idsCriteria.setCloneIDsSubQuery(inputIDQuery);
                     }
                }
                else if (platObj.getValueObject().equalsIgnoreCase(Constants.AFFY_OLIGO_PLATFORM)) {
                    if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE)||
                            probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                        /* this means inputIDs represent cloneIDs.  So convert these cloneIDs to
                        ProbesetIDs by going through GeneSymbol */
                        ReportQueryByCriteria accessionQuery = getAccnsForCloneIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria  probeIDsQuery = getProbeIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(probeIDsQuery);
                    }
                    else if  (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                         /* means inputIDs represent probeIDs  Since platform requested was OLIGO(Affy),
                            this means only probeIDs should be in the final result set */
                        idsCriteria.setProbeIDsSubQuery(inputIDQuery);
                    }
                }
                else if (platObj.getValueObject().equalsIgnoreCase(Constants.CDNA_ARRAY_PLATFORM)) {
                     if (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                        /* this means inputIDs represent probesetIDs.  So convert probesetID to
                         cloneIDs by going through GeneSymbol */
                        ReportQueryByCriteria accessionQuery = getAccnsForProbeIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria cloneIDsQuery = getCloneIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setCloneIDsSubQuery(cloneIDsQuery);
                    }
                     else if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE)||
                            probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                         /* means inputIDs represent cloneIDs  Since platform requested was cDNA,
                            this means only cloneIDs should be in the final result set */
                          idsCriteria.setCloneIDsSubQuery(inputIDQuery);
                     }
                }

                //idCrit.addOrCriteria(idsCriteria.handle());
            }
        }
        _BROKER.close();
         return idsCriteria;
    }

    /*  This method converts ProbesetNames/CloneNames into thier respective IDs
    */
    public static ReportQueryByCriteria getCloneOrProbeIDsFromNameDEs(Collection probeOrCloneDEs, String probeType, PersistenceBroker _BROKER) throws Exception {
        Collection deNames = new ArrayList();
        org.apache.ojb.broker.query.ReportQueryByCriteria IDsQuery = null;
        for (Iterator iterator = probeOrCloneDEs.iterator(); iterator.hasNext();)
              deNames.add(((CloneIdentifierDE) iterator.next()).getValueObject());

        String nameCol = null;
        String idCol = null;
        if (probeType.equals(CloneIdentifierDE.PROBE_SET)) {
           nameCol = GeneExprQueryHandler.getColumnName(_BROKER, CloneIdentifierDE.ProbesetID.class.getName());
           idCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
           Criteria c = new Criteria();
           c.addColumnIn(nameCol, deNames);
           IDsQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {idCol}, c, true);
        }
        else if (probeType.equals(CloneIdentifierDE.IMAGE_CLONE) || probeType.equals(CloneIdentifierDE.BAC_CLONE) ) {
            nameCol = GeneExprQueryHandler.getColumnName(_BROKER, CloneIdentifierDE.IMAGEClone.class.getName());
            idCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, CloneDim.class.getName(), CloneDim.CLONE_ID);
            Criteria c = new Criteria();
            c.addColumnIn(nameCol, deNames);
            IDsQuery  =  QueryFactory.newReportQuery(CloneDim.class, new String[] {idCol}, c, true);
        }
        return IDsQuery ;
    }
    private static ReportQueryByCriteria  getCloneIDsFor(ReportQueryByCriteria  accnQuery, PersistenceBroker _BROKER) throws Exception {
        String cloneIDCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, CloneDim.class.getName(), CloneDim.CLONE_ID);
        return getCloneOrProbeIDsFor(accnQuery, cloneIDCol, CloneAccession.class, CloneAccession.ACCESSION_NUMBER, _BROKER);
    }
    private static ReportQueryByCriteria getProbeIDsFor(ReportQueryByCriteria accnQuery, PersistenceBroker _BROKER) throws Exception {
        String probeIDCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
        return getCloneOrProbeIDsFor(accnQuery, probeIDCol, ProbesetDim.class, ProbesetDim.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria  getCloneOrProbeIDsFor(ReportQueryByCriteria accnQuery, String cloneOrProbeIDCol, Class classToSearch, String fieldToSearch, PersistenceBroker _BROKER) throws Exception {

        Criteria clonOrProbeIDCrit = new Criteria();
        clonOrProbeIDCrit.addIn(fieldToSearch,  accnQuery);
        org.apache.ojb.broker.query.ReportQueryByCriteria cloneOrProbeIDSQuery =
                QueryFactory.newReportQuery(classToSearch, new String[] {cloneOrProbeIDCol}, clonOrProbeIDCrit , true);
        return cloneOrProbeIDSQuery;
    }

    private static ReportQueryByCriteria getAccnsForProbeIDs(ReportQueryByCriteria probeIDSubQuery, PersistenceBroker _BROKER) throws Exception {
        return getAccnForCloneOrProbesetIDs(probeIDSubQuery, ProbesetDim.PROBESET_ID, ProbesetDim.class, ProbesetDim.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria getAccnsForCloneIDs(ReportQueryByCriteria cloneIDSubQuery, PersistenceBroker _BROKER) throws Exception {
        return getAccnForCloneOrProbesetIDs(cloneIDSubQuery, CloneDim.CLONE_ID, CloneAccession.class, CloneAccession.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria getAccnForCloneOrProbesetIDs(ReportQueryByCriteria probeOrCloneIDSubQuery, String probeOrCloneIDAttrName, Class classToSearch, String fieldToSearch, PersistenceBroker _BROKER) throws Exception {
        String accnCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, classToSearch.getName(), fieldToSearch);
        Criteria cloneOrProbeCriteria = new Criteria();
        cloneOrProbeCriteria.addIn(probeOrCloneIDAttrName, probeOrCloneIDSubQuery);
        org.apache.ojb.broker.query.ReportQueryByCriteria accessionQuery =
                QueryFactory.newReportQuery(classToSearch, new String[] {accnCol}, cloneOrProbeCriteria , true);
        return accessionQuery;
    }

    public static String getType(CloneOrProbeIDCriteria cloneProbeCrit ) {
        if (cloneProbeCrit != null ) {
            Collection cloneIDorProbeIDs = cloneProbeCrit.getIdentifiers();
             if (cloneIDorProbeIDs != null && cloneIDorProbeIDs.size() > 0) {
                 CloneIdentifierDE obj =  (CloneIdentifierDE ) cloneIDorProbeIDs.iterator().next();
                 return obj.getCloneIDType();
             }
        }
        return "";
    }

}
