/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.test;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import gov.nih.nci.caintegrator.enumeration.ArrayPlatformType;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations.AnnotationHandler;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations.ReporterAnnotations;
import gov.nih.nci.rembrandt.util.ApplicationContext;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;

import java.util.*;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.Query;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class AnnotationsHandlerTest extends TestCase {
    List reporters;
    protected void setUp() throws Exception {
        ApplicationContext.init();

        /* populate reporters for testing testgetGeneSymbolsForReportersQuery() */
        final PersistenceBroker pb = PersistenceBrokerFactory.defaultPersistenceBroker();
        pb.clearCache();
        Query q = QueryFactory.newReportQuery(ProbesetDim.class,new String[] {ProbesetDim.PROBESET_NAME}, null, true);
        q.setStartAtIndex(0);
        q.setEndAtIndex(8000);
        reporters = new ArrayList();
        Iterator iter =  pb.getReportQueryIteratorByQuery(q);
        while (iter.hasNext()) {
           Object[] geneAttrs = (Object[]) iter.next();
           reporters.add((String)geneAttrs[0]);
        }
    }

    public void testgetGeneSymbolsFor() throws Exception{
        AnnotationHandler h = new AnnotationHandler();
        Map results = AnnotationHandler.getGeneSymbolsFor(reporters, ArrayPlatformType.AFFY_OLIGO_PLATFORM);

       // loop through and print all reporters and associated gene symbols
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String r =  (String) iterator.next();
            System.out.println("REPORTER:" + r + "    GENE_SYMBOL:" + results.get(r));
        }

    }

     public void testgetAllAnnotationsFor() throws Exception{
         Map<String, ReporterAnnotations> results = AnnotationHandler.getAllAnnotationsFor(reporters, ArrayPlatformType.AFFY_OLIGO_PLATFORM);

        // loop through and print all reporters and associated gene symbols
        for (Iterator iterator = reporters.iterator(); iterator.hasNext();) {
            String r =  (String) iterator.next();
            ReporterAnnotations ra = results.get(r);

            // NOTE: any of the below annotations could be null.  So please check for null
            // before accessing these annotations (such as accns, locuslinks, goID, pathways
            ArrayList accns = ra.getAccessions();
            ArrayList locusLinks = ra.getLocusLinks();
            ArrayList goIDs = ra.getGoIDS();
            ArrayList pathways = ra.getPathways();


            System.out.print("REPORTER:" + r);
            System.out.print("  GENE_SYMBOL:" + ra.getGeneSymbol());

            System.out.println("\n***** BEGIN ACCESSIONS: *************");
            if (accns != null) {
                for (Iterator iterator1 = accns.iterator(); iterator1.hasNext();) {
                    Object o =  iterator1.next();
                    System.out.println(" " + o);

                }
            }
            System.out.println("***** END ACCESSIONS: *************\n");


            System.out.println("***** BEGIN LOCUSLINKS: *************");
            if (locusLinks != null) {
                for (Iterator iterator1 = locusLinks.iterator(); iterator1.hasNext();) {
                    Object o =  iterator1.next();
                    System.out.println(" " + o);

                }
            }
            System.out.println("***** END LOCUSLINKS: *************\n");

            System.out.println("***** BEGIN GOIDS: *************");
            if (goIDs != null) {
                for (Iterator iterator1 = goIDs.iterator(); iterator1.hasNext();) {
                    Object o =  iterator1.next();
                    System.out.println(" " + o);

                }
            }
            System.out.println("***** END GOIDS: *************\n");

            System.out.println("***** BEGIN PATHWAYS: *************");
            if (pathways != null) {
                for (Iterator iterator1 = pathways.iterator(); iterator1.hasNext();) {
                    Object o =  iterator1.next();
                    System.out.println(" " + o);

                }
            }
            System.out.println("***** END PATHWAYS: *************\n");
        }
     }

    public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(AnnotationsHandlerTest.class));
        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }

}
