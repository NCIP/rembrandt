/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import java.util.ArrayList;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public class ReporterDimension {
      ArrayList<String> locusLinks;
      ArrayList<String> accessions;
      String reporterName;

    public ReporterDimension(ArrayList<String> locusLinks, ArrayList<String> accns, String reporterName) {
        this.locusLinks = locusLinks;
        this.accessions = accns;
        this.reporterName = reporterName;
    }

    public ArrayList<String> getLocusLinks() {
        return locusLinks;
    }

    public void setLocusLinks(ArrayList<String> locusLinks) {
        this.locusLinks = locusLinks;
    }

    public ArrayList<String> getAccessions() {
        return accessions;
    }

    public void setAccessions(ArrayList<String> accessions) {
        this.accessions = accessions;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
}
