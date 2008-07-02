/**
 * 
 */
package gov.nih.nci.rembrandt.download.caarray.test;

import gov.nih.nci.caarray.domain.file.FileType;
import gov.nih.nci.caintegrator.application.download.DownloadStatus;
import gov.nih.nci.caintegrator.application.download.DownloadTask;
import gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager;
import gov.nih.nci.caintegrator.application.download.caarray.RembrandtCaArrayFileDownloadManager;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author sahnih
 *
 */
public class CaArrayFileDownloadTest extends TestCase {
	private List<String> specimenList = new ArrayList<String>();
	private FileType type = FileType.AFFYMETRIX_CEL;
	private final String session ="XYZ";
	private final String taskId ="123";
	private final String zipFileName ="TestcaArrayDownload.zip";
	private String caarrayUrl;
	private String experimentName;
	private String username;
	private String password;
	private CaArrayFileDownloadManager rbtCaArrayFileDownloadManager;
	/**
	 * @param name
	 */
	public CaArrayFileDownloadTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		specimenList.add("	E05004	");
		specimenList.add("	E05012	");
		specimenList.add("	E07733	");
		specimenList.add("	E08021	");
		specimenList.add("	E09137	");
		specimenList.add("	E09138	");
		specimenList.add("	E09139	");
		specimenList.add("	E09167	");
		specimenList.add("	E09176	");
		specimenList.add("	E09192	");
		specimenList.add("	E09214	");
		specimenList.add("	E09231	");
		specimenList.add("	E09233	");
		specimenList.add("	E09238	");
		specimenList.add("	E09262	");
		System.setProperty(RembrandtCaArrayFileDownloadManager.SERVER_URL,"http://array.nci.nih.gov:8080");
		System.setProperty(RembrandtCaArrayFileDownloadManager.EXPERIMENT_NAME,"Rembrandt");
		rbtCaArrayFileDownloadManager = RembrandtCaArrayFileDownloadManager.getInstance();
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#CaArrayFileDownloadManager(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	public final void testCaArrayFileDownloadManager() {
		assertTrue(rbtCaArrayFileDownloadManager!= null);
	}

	/**
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#executeDownloadStrategy(java.lang.String, java.lang.String, java.lang.String, java.util.List, gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileType)}.
	 */
	public final void testExecuteDownloadStrategy() {
		assertTrue(rbtCaArrayFileDownloadManager!= null);
		rbtCaArrayFileDownloadManager.executeDownloadStrategy(session, taskId, zipFileName, specimenList, type);
		//Get back a DownloadTask object
		DownloadTask downloadTask = rbtCaArrayFileDownloadManager.getSessionDownload( session,  taskId);
		assertTrue(downloadTask!= null);
		DownloadStatus currentStatus = downloadTask.getDownloadStatus();
		System.out.println("Status:"+ currentStatus);
		assertTrue(currentStatus != DownloadStatus.Error);

		
		while(downloadTask.getDownloadStatus() != DownloadStatus.Completed  ||
				downloadTask.getDownloadStatus() != DownloadStatus.Error){
			downloadTask = rbtCaArrayFileDownloadManager.getSessionDownload( session,  taskId);
			if(!currentStatus.equals(downloadTask.getDownloadStatus())){
				currentStatus = downloadTask.getDownloadStatus();
				System.out.println("Status:"+ currentStatus);
			}
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Status:"+ currentStatus);
		assertTrue(currentStatus == DownloadStatus.Completed);
		assertFalse(currentStatus == DownloadStatus.Error);

		
	}

	/**
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#getAllSessionDownloads(java.lang.String)}.
	 */
	public final void testGetAllSessionDownloads() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#getSessionDownload(java.lang.String, java.lang.String)}.
	 */
	public final void testGetSessionDownload() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#setStatusInCache(java.lang.String, java.lang.String, gov.nih.nci.caintegrator.application.download.DownloadStatus)}.
	 */
	public final void testSetStatusInCache() {
		fail("Not yet implemented"); // TODO
	}

}
