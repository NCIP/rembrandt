package gov.nih.nci.rembrandt.web.test;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

public class TestEVS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String serverURL = "http://cbioqa101.nci.nih.gov:49080/cacore31/http/remoteService";
		//#serverURL = http://cbioapp101.nci.nih.gov:49080/cacore31/http/remoteService
		//#serverURL = http://cabio.nci.nih.gov/cacore31/http/remoteService

		EVSQuery evsQuery = new EVSQueryImpl();
		String theSource = "*";
		String term = "biological_process";
		
//		evsQuery.searchMetaThesaurus(term, 200, theSource, false, false, false);
		
		ApplicationService appService = ApplicationService.getRemoteInstance(serverURL);
		List results = null;
		
		evsQuery.getTree("GO", term, true, false, 0, 1, null);
		/*		
				vn = GO
				roles = null
				csn = biological processes
				dir = false
				isaflag = false
				depthLevel = -1
				attributes = 0
		*/	
		try {
			results =(List) appService.evsSearch(evsQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DefaultMutableTreeNode dn = (DefaultMutableTreeNode)results.get(0);
		DescLogicConcept theUserObject = (DescLogicConcept) dn.getUserObject();
		System.out.println(theUserObject.getName() + " : " + theUserObject.getCode());
		System.out.println(results);

	}

}
