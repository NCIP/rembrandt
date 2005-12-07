/*
 * Created on Mar 8, 2005
 *  The only purpose of this class is to keep track of the
 *  number of temp reports generated.  It is intended to be
 *  created for every session and stored in the sessionCache
 *  where it can be retrieved and used to get a uniqueName for
 *  any temp reports.
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * @author David
 *
 */
public class SessionTempReportCounter implements Serializable {
	private static Logger logger = Logger.getLogger(SessionTempReportCounter.class);
	private int numberOfTempReports = 0;
	private int numberOfTempReporters = 0;
	/**
	 * Returns a unique name for a new tempReport
	 * @return
	 */
	public String getNewTempReportName() {
		String name = RembrandtConstants.TEMP_RESULTS+numberOfTempReports;
		logger.debug("Creating a new Temp Result Set name: "+name);
		numberOfTempReports++;
		return name;
	}
	public String getNewTempReporterName() {
		String name = RembrandtConstants.TEMP_REPORTER_GROUP+numberOfTempReporters;
		logger.debug("Creating a new Temp Resulter Group name: "+name);
		numberOfTempReporters++;
		return name;
	}
}
