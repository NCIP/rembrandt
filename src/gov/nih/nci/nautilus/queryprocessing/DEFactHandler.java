package gov.nih.nci.nautilus.queryprocessing;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.PersistenceBroker;

import java.util.*;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.DifferentialExpressionGfact;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Sep 26, 2004
 * Time: 3:58:42 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class DEFactHandler {
    Class sfactClass = DifferentialExpressionSfact.class;

    abstract Map executeSampleQuery(Criteria sampleCriteria, PersistenceBroker _BROKER);

    static class SampleDEFactHandler extends DEFactHandler {
        Map executeSampleQuery(Criteria sampleCrit, PersistenceBroker _BROKER) {
            String fieldName = DifferentialExpressionSfact.BIOSPECIMEN_ID ;
            sampleCrit.addOrderBy(fieldName);
            org.apache.ojb.broker.query.Query sampleQuery = QueryFactory.newQuery(
                DifferentialExpressionSfact.class, sampleCrit, true); //TODO: true/false
            /*
            sampleQuery.setAttributes(new String[] {
                    DifferentialExpressionSfact.DES_ID,
                    DifferentialExpressionSfact.PROBE_NAME,
                    DifferentialExpressionSfact.CLONE_NAME,
                    DifferentialExpressionSfact.PROBESET_ID,
                    DifferentialExpressionSfact.tyCLONE_ID,
                    DifferentialExpressionSfact.GENE_SYMBOL,
                    DifferentialExpressionSfact.BIOSPECIMEN_ID,
                    DifferentialExpressionSfact.EXPRESSION_RATIO
            } );
            */
            Collection exprObjects =   _BROKER.getCollectionByQuery(sampleQuery );
            assert(exprObjects != null);

            Map geneExprObjects = new HashMap();

            int count = 0;
           HashSet probeIDS = new HashSet();
           HashSet cloneIDs = new HashSet();
            for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
                DifferentialExpressionSfact exprObj = (DifferentialExpressionSfact) iterator.next();
                geneExprObjects.put(exprObj.getDesId(), exprObj);
                if (exprObj.getProbesetId() != null) {
                  // System.out.println("ProbesetID: " + exprObj.getProbesetId() + " :Exp Value: "
                    //            + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol() );
                    probeIDS.add(exprObj.getProbesetId());
                }
                if ( exprObj.getCloneId() != null) {
                   // System.out.println("CloneID: " + exprObj.getCloneId()+ " :Exp Value: "
                     //           + exprObj.getExpressionRatio() + "  GeneSymbol: " + exprObj.getGeneSymbol());
                    cloneIDs.add(exprObj.getCloneId() );
                }

                 ++count;
            }
           System.out.println("Total Number Of Samples: " + count);
           StringBuffer p = new StringBuffer();
           for (Iterator iterator = probeIDS.iterator(); iterator.hasNext();) {
               Long aLong = (Long) iterator.next();
               p.append(aLong.toString() + ",");
           }
           System.out.println("ProbeIDs");
           System.out.println(p.toString());
           StringBuffer c = new StringBuffer();
           for (Iterator iterator = cloneIDs.iterator(); iterator.hasNext();) {
               Long aLong = (Long) iterator.next();
               c.append(aLong.toString() + ",");
           }
           System.out.println("cloneIDs");
           System.out.println(c.toString());

            return geneExprObjects;
        }
    }

    static class GroupDEFactHanlder extends DEFactHandler {
        Map executeSampleQuery(Criteria sampleCriteria,  PersistenceBroker _BROKER) {
            //TODO:
            return null;
        }
    }


}
