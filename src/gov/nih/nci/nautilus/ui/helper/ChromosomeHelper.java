package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.ui.bean.ChromosomeBean;
import java.util.ArrayList;
import java.util.Collection;
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
	private static Collection chromosomes;
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
			ChromosomeNumberDE[] chromosomeDEs= LookupManager.getChromosomeDEs();
			for(int i = 0; i<chromosomeDEs.length;i++) {
				ChromosomeBean chromosome = new ChromosomeBean();
				chromosome.setChromosomeNumberDE((ChromosomeNumberDE)chromosomeDEs[i].getValue());
				CytobandDE[] cytobandDEs = LookupManager.getCytobandDEs(chromosomeDEs[i]);
				ArrayList cytobands = new ArrayList();
				for(int j = 0; j < cytobandDEs.length;j++) {
					cytobands.add(cytobandDEs[j]);
				}
				chromosome.setCytobands(cytobands);
				chromosomes.add(chromosome);
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
	public Collection getChromosomes() {
		return chromosomes;
	}
}
