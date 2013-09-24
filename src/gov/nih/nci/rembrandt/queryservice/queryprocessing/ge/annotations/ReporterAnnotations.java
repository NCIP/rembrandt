/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class ReporterAnnotations {
    String reporterName;
    String geneSymbol;
    ArrayList goIDS;
    ArrayList pathways;
    ArrayList locusLinks;
    ArrayList accessions;

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public ArrayList getGoIDS() {
        return goIDS;
    }

    public void setGoIDS(ArrayList goIDS) {
        this.goIDS = goIDS;
    }

    public ArrayList getPathways() {
        return pathways;
    }

    public void setPathways(ArrayList pathways) {
    	HashSet hs = new HashSet(pathways);
    	this.pathways = new ArrayList(hs);
    }

    public ArrayList getLocusLinks() {
        return locusLinks;
    }

    public void setLocusLinks(ArrayList locusLinks) {
        this.locusLinks = locusLinks;
    }

    public ArrayList getAccessions() {
        return accessions;
    }

    public void setAccessions(ArrayList accessions) {
        this.accessions = accessions;
    }

}
