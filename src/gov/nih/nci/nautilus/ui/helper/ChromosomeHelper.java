package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.lookup.CytobandLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.ui.bean.ChromosomeBean;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is intended to provide a single interface to the LookupManager
 * when getting collective information regarding the entire set of Chromosomes
 * and their associated information.  More helper methods will be added as the 
 * Chromosome object expands or as the need arises. 
 * 
 * @author BauerD
 * Mar 14, 2005
 */
public class ChromosomeHelper {
	private static Logger logger = Logger.getLogger(ChromosomeHelper.class);
	private static List chromosomes;
	private static ChromosomeHelper instance;
	
	//Create the singleton instance
	static {
		instance = new ChromosomeHelper();
	}
	/**
	 * Creates the ChromosomeHelper and generates the ChromosomeBean collection
	 *
	 */
	private ChromosomeHelper() {
		try {
			chromosomes = new ArrayList();
			chromosomes.add(0,new ChromosomeBean());
			CytobandLookup[] cytobandLookups = LookupManager.getCytobandPositions();
			for(int i = 0; i<cytobandLookups.length;i++) {
				addCytoband(cytobandLookups[i]);
			}
		}catch(Exception e) {
			chromosomes = null;
			logger.error("Unable to create ChromosomeBeans from the LookupManager");
			logger.error(e);
		}
	}
	/**
	 * Returns the SingletonInstance
	 * @return
	 */
	public static ChromosomeHelper getInstance() {
		return instance;
	}
	/**
	 * Returns the Collection of ChromosomeBeans for the UI
	 * @return
	 */
	public List getChromosomes() {
		return chromosomes;
	}
	/**
	 * checks the current list of Chromosomes and then adds the cytoband to
	 * either the created or existing ChromosomeBean
	 * @param cytoband
	 * @return
	 */
	private void addCytoband(CytobandLookup cytoband) throws Exception {
		List cytobands;
		String chromoNumString = cytoband.getChromosome();
		int chromoNum = Integer.parseInt(chromoNumString);
		ChromosomeBean bean = new ChromosomeBean();
		int test = chromosomes.indexOf(bean);
		if(test!=-1) {
			bean = (ChromosomeBean)chromosomes.get(test);
			cytobands = bean.getCytobands();
			cytobands.add(cytoband);
			bean.setCytobands(cytobands);
		}else {
			cytobands = new ArrayList();
			cytobands.add(cytoband);
			bean.setCytobands(cytobands);
			chromosomes.add(chromoNum, bean);
		}
	}
}
