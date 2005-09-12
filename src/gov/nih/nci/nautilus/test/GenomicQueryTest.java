package gov.nih.nci.nautilus.test;

import gov.nih.nci.nautilus.query.ComparativeGenomicQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.criteria.AssayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.de.AssayPlatformDE;
import gov.nih.nci.nautilus.resultset.ResultSet;
import gov.nih.nci.nautilus.queryprocessing.QueryProcessor;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: May 4, 2005
 * Time: 6:44:25 AM
 * To change this template use File | Settings | File Templates.
 */
 public class GenomicQueryTest extends QueryTest {
       public void testCGHExprQuery() {
        ComparativeGenomicQuery q = (ComparativeGenomicQuery) QueryManager.createQuery(QueryType.CGH_QUERY_TYPE);
             q.setQueryName("Test CGH Query");
             q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
             //q.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
            q.setGeneIDCrit(geneIDCrit);
            //q.setGeneOntologyCrit(ontologyCrit);
            //q.setRegionCrit(regionCrit);
            //q.setPathwayCrit(pathwayCrit);

            AssayPlatformCriteria crit = new AssayPlatformCriteria();
            crit.setAssayPlatformDE(new AssayPlatformDE(Constants.AFFY_100K_SNP_ARRAY));
            q.setAssayPlatformCrit(crit);
            //q.setRegionCrit(regionCrit);
            //q.setSNPCrit(snpCrit);
            //q.setAllGenesCrit(allGenesCriteria);
            //q.setDiseaseOrGradeCrit(diseaseCrit);
            //q.setCopyNumberCrit(copyNumberCrit);
            q.setSampleIDCrit(sampleCrit);
            try {
                //ResultSet[] cghObjects = QueryManager.executeQuery(q);
                ResultSet[] cghObjects = QueryProcessor.execute(q);
                //print(geneExprObjects);
                //testResultset(geneExprObjects);
                System.out.println("Size: " + cghObjects.length);
                for (int i = 0; i < cghObjects.length; i++) {
                    gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber cghObject =
                            (gov.nih.nci.nautilus.queryprocessing.cgh.CopyNumber) cghObjects[i];
                    System.out.println("SampleID: " + cghObject.getSampleId() + " || Copy Number: "
                    + cghObject.getCopyNumber() + " || SNPProbesetName: " + cghObject.getSnpProbesetName()
                    + " || Chromosome: " + cghObject.getCytoband() );
                    if (cghObject.getAnnotations() != null)
                    System.out.println( "Annotation GeneSymbols: " +
                            cghObject.getAnnotations().getGeneSymbols()+
                       "  LocusLinks: " + cghObject.getAnnotations().getLocusLinkIDs() +
                    "  Accessions Numbers: " + cghObject.getAnnotations().getAccessionNumbers());
                }
            } catch(Throwable t ) {
                t.printStackTrace();
            }

        }
}

