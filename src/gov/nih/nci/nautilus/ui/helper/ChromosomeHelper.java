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
			//Drop a place holder in the 0th position
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
		String chromoString = cytoband.getChromosome();
		ChromosomeBean bean = new ChromosomeBean();
		bean.setChromosome(chromoString);
		//Check to see if the Chromosome has already been placed in the List
		int test = chromosomes.indexOf(bean);
		if(test!=-1) {
			//It has, so get the Chromosome and it's list of Cytobands
			bean = (ChromosomeBean)chromosomes.get(test);
			cytobands = bean.getCytobands();
			//add the new cytoband to the list
			cytobands.add(cytoband);
			//set the list back in the bean
			bean.setCytobands(cytobands);
		}else {
			//It doesn't have the Chromosome already
			//so that means this is the first cytoband for this Chromosome
			//create the List for the cytobands
			cytobands = new ArrayList();
			//add the new cytoband
			cytobands.add(cytoband);
			//set the list into the bean
			bean.setCytobands(cytobands);
			//Try to set the bean in it's proper place in the list
			int i;
			try {
				//Can I parse the chomosome number into an int
				i = Integer.parseInt(chromoString);
			}catch(NumberFormatException nfe) {
				//No, this is a sex chromosome
				//try to place it in the 22nd location.
				i=22;
				//Is the 22nd position empty?
				if(chromosomes.get(22)!=null) {
					//No! 22nd location is full, use 23rd
					i = 23;
				}
			}
			//Place the Chromosome where it goes, in order in the list
			chromosomes.add(i, bean);
		}
	}
}
