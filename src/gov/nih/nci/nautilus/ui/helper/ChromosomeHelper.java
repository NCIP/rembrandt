package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.ui.bean.ChromosomeBean;
import java.util.ArrayList;
import java.util.Collection;


import org.apache.log4j.Logger;

/**
 * @author BauerD
 * Mar 14, 2005
 * 
 */
public class ChromosomeHelper {
	private static Logger logger = Logger.getLogger(ChromosomeHelper.class);
	private static Collection chromosomes;
	private static ChromosomeHelper instance;
	
	//Create the singleton instance
	static {
		instance = new ChromosomeHelper();
	}
	
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
	
	public static ChromosomeHelper getInstance() {
		return instance;
	}
	
	public Collection getChromosomes() {
		return chromosomes;
	}
}
