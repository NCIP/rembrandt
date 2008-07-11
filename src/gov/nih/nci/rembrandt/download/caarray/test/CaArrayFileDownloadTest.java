/**
 * 
 */
package gov.nih.nci.rembrandt.download.caarray.test;

import gov.nih.nci.caarray.domain.file.FileType;
import gov.nih.nci.caintegrator.application.download.DownloadStatus;
import gov.nih.nci.caintegrator.application.download.DownloadTask;
import gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager;
import gov.nih.nci.rembrandt.download.caarray.RembrandtCaArrayFileDownloadManager;
import gov.nih.nci.rembrandt.web.factory.ApplicationFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import junit.framework.TestCase;

/**
 * @author sahnih
 *
 */
public class CaArrayFileDownloadTest extends TestCase {
	private List<String> specimenList = null;
	private FileType type = FileType.AFFYMETRIX_CEL;
	private final String session ="XYZ";
	private final String taskId ="123";
	private final String zipFileName ="TestcaArrayDownload.zip";
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
		specimenList = new ArrayList<String>();
		specimenList.add("	E05004_T	");
		specimenList.add("	E05012_B	");
		specimenList.add("	E07733_T	");
		specimenList.add("	E08021_T	");
		specimenList.add("	E09137_B	");
		specimenList.add("	E09138_T	");
//		specimenList.add("	E09139	");
//		specimenList.add("	E09167	");
//		specimenList.add("	E09176	");
//		specimenList.add("	E09192	");
//		specimenList.add("	E09214	");
//		specimenList.add("	E09231	");
//		specimenList.add("	E09233	");
//		specimenList.add("	E09238	");
//		specimenList.add("	E09262	");
		System.setProperty(RembrandtCaArrayFileDownloadManager.SERVER_URL,"http://array.nci.nih.gov:8080");
		System.setProperty(RembrandtCaArrayFileDownloadManager.EXPERIMENT_NAME,"Rembrandt");
		System.setProperty(RembrandtCaArrayFileDownloadManager.INPUT_DIR,"c:/caArrayDownloadTest12");
		System.setProperty(RembrandtCaArrayFileDownloadManager.OUTPUT_ZIP_DIR,"c:/caArrayDownloadTest13");
		System.setProperty(RembrandtCaArrayFileDownloadManager.DIR_IN_ZIP,"rembrandt");
		System.setProperty(RembrandtCaArrayFileDownloadManager.ZIP_FILE_URL,"http://localhost:8080/rembrandt/log");
		rbtCaArrayFileDownloadManager = RembrandtCaArrayFileDownloadManager.getInstance();
		rbtCaArrayFileDownloadManager.setBusinessCacheManager(ApplicationFactory.getBusinessTierCache());
		
//		<!--  Bean for thread pool -->
//		<bean id="taskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
//	  		<property name="corePoolSize" value="5" />
//	  		<property name="maxPoolSize" value="100" />
//	  		<property name="queueCapacity" value="400" />
//		</bean>
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(100);
		taskExecutor.setQueueCapacity(400);
		taskExecutor.initialize();
		rbtCaArrayFileDownloadManager.setTaskExecutor(taskExecutor);
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
	 * Test method for {@link gov.nih.nci.caintegrator.application.download.caarray.CaArrayFileDownloadManager#executeDownloadStrategy(java.lang.String, java.lang.String, java.lang.String, java.util.List, gov.nih.nci.rembrandt.download.caarray.CaArrayFileType)}.
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

		
		while(true){
			downloadTask = rbtCaArrayFileDownloadManager.getSessionDownload( session,  taskId);
			//System.out.println("ElapsedTime:"+downloadTask.getElapsedTime());
			if(!currentStatus.equals(downloadTask.getDownloadStatus())){
				currentStatus = downloadTask.getDownloadStatus();
				System.out.println("Status:"+ currentStatus);
			}
			if(downloadTask.getDownloadStatus().equals(DownloadStatus.Completed)  ||
				(downloadTask.getDownloadStatus().equals(DownloadStatus.Error))){
				break;
			}
			 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Final Status:"+ currentStatus);
        System.out.println("Total processing time for all files took " + downloadTask.getElapsedTime() + " second(s) or "+ downloadTask.getElapsedTime()/60+" minute(s).");
		assertTrue(currentStatus == DownloadStatus.Completed);
		System.out.println("Zip file URL:"+downloadTask.getZipFileURL());
		System.out.println("Zip file Size:"+downloadTask.getZipFileSize());
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
