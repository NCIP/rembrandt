package gov.nih.nci.nautilus.test;

import gov.nih.nci.nautilus.data.DifferentialExpressionSfact;
import gov.nih.nci.nautilus.data.ProbesetDim;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.queryprocessing.CommonFactHandler;
import gov.nih.nci.nautilus.queryprocessing.ge.FoldChangeCriteriaHandler;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import java.util.Collection;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Jan 18, 2005
 * Time: 5:02:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class OJBSubSelectTest extends QueryTest{
    public static class GeneExpressionSubSelect extends OJBSubSelectTest {
          public void testGeneExprQuery() {
            GeneExpressionQuery q = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
                 q.setQueryName("Test Gene Query");
                 q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
                 //q.setGeneIDCrit(geneIDCrit);

                 //q.setGeneOntologyCrit(ontologyCrit);
                q.setRegionCrit(regionCrit);
                //q.setPathwayCrit(pathwayCrit);

                //q.setGeneOntologyCrit(ontologyCrit);
                //q.setRegionCrit(regionCrit);
                //q.setPathwayCrit(pathwayCrit);


                q.setArrayPlatformCrit(allPlatformCrit);
               //q.setPlatCriteria(affyOligoPlatformCrit);
               //q.setPlatCriteria(cdnaPlatformCrit);

                //q.setCloneOrProbeIDCrit(cloneCrit);
                //q.setCloneProbeCrit(probeCrit);
                //q.setDiseaseOrGradeCrit(diseaseCrit);
                  //q.setSampleIDCrit(sampleCrit);
                  q.setFoldChgCrit(foldCrit);

                try {

                    PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
                    final Criteria sampleCrit = new Criteria();


                    CommonFactHandler.addDiseaseCriteria(q, DifferentialExpressionSfact.class, _BROKER, sampleCrit);
                    FoldChangeCriteriaHandler.addFoldChangeCriteria(q, DifferentialExpressionSfact.class, _BROKER, sampleCrit);
                    CommonFactHandler.addSampleIDCriteria(q, DifferentialExpressionSfact.class, sampleCrit);

                    Criteria probeSubCrit = new Criteria();
                    probeSubCrit.addEqualTo(ProbesetDim.CHROMOSOME, "6");
                    ReportQueryByCriteria r = QueryFactory.newReportQuery(ProbesetDim.class, probeSubCrit, true);
                    r.setAttributes(new String[] {ProbesetDim.PROBESET_ID} );
                    sampleCrit.addIn(DifferentialExpressionSfact.PROBESET_ID, r);

                    org.apache.ojb.broker.query.Query sampleQuery =
                          QueryFactory.newQuery(DifferentialExpressionSfact.class, sampleCrit, false);
                    Collection exprObjects =  _BROKER.getCollectionByQuery(sampleQuery );
                    System.out.println("LENGTH: " + exprObjects.size());
                    _BROKER.close();


                } catch(Throwable t) {
                    System.out.println("ERROR*****");
                    t.printStackTrace();
                }
            }
        }
}
