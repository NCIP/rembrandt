package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.lookup.CytobandLookup;
import gov.nih.nci.nautilus.lookup.LookupManager;
import gov.nih.nci.nautilus.ui.bean.ChromosomeBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.log4j.Logger;

/**
 * This class is intended to provide a single interface to the LookupManager
 * when getting collective information regarding the entire set of Chromosomes
 * and their associated information.  More helper methods will be added as the 
 * Chromosome object expands or as the need arises. 
 * 
 * @author BauerD, RossoK
 * Mar 14, 2005
 */
public class ChromosomeHelper implements Factory{
	private Logger logger = Logger.getLogger(ChromosomeHelper.class);
	private static ChromosomeHelper instance;
	private List chromosomes = LazyList.decorate(new ArrayList(), this);;
	
	
	
	//Create the singleton instance
	static {
		instance = new ChromosomeHelper();
	}
	/**
	 * Creates the ChromosomeHelper and generates the ChromosomeBean collection
	 *
	 */
	private ChromosomeHelper() {
	
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
		if(chromosomes!=null) {
			if(chromosomes.isEmpty()) {
				getCytobands();
			}
		}
		return chromosomes;
	}
	/**
	 * Helper method, returns a specific ChromosomeBean based on the 
	 * given chromosome name. Returns null if it is not in the chromosomes
	 * list.
	 * @param chromosomeName
	 * @return
	 */
	public ChromosomeBean getChromosomeBean(String chromosomeName) {
		int index = chromosomes.indexOf(new ChromosomeBean(chromosomeName));
		if(index>-1) {
			return (ChromosomeBean)chromosomes.get(index);
		}else {
			return null;
		}
	}
	/**
	 * Helper method, returns a specific ChromosomeBean based on the 
	 * chromosomeIndex. Returns null if it is not in the chromosomes
	 * list.
	 * @param chromosomeIndex
	 * @return
	 */
	public ChromosomeBean getChromosomeBean(int chromosomeIndex) {
		return (ChromosomeBean)chromosomes.get(chromosomeIndex);
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
		int i;
		try {
			//Can I parse the chomosome number into an int
			i = Integer.parseInt(chromoString)-1;
		}catch(NumberFormatException nfe) {
			//No, this is a sex chromosome
			//try to place it in the 23rd location.
			i=22;
			//Get the ChromosomeBean at the 23rd location
			ChromosomeBean testBean = (ChromosomeBean)chromosomes.get(i);
			//Is it the right ChromosomeBean, in that it is the chromosome we are looking for
			if(!testBean.equals(new ChromosomeBean(chromoString))&&!testBean.equals(new ChromosomeBean("0"))) {
				//No! 23rd location is full, use 24th
				i = 23;
			}
		}
		ChromosomeBean bean = (ChromosomeBean)chromosomes.get(i);
		bean.setChromosome(chromoString);
		bean.setValue(i);
		cytobands = bean.getCytobands();
		if(cytobands == null) {
			cytobands = new ArrayList();
		}
		//add the new cytoband to the list
		cytobands.add(cytoband);
		//set the list back in the bean
		bean.setCytobands((ArrayList)cytobands);
	}

	private void getCytobands() {
		try {
			//Drop a place holder in the 0th position
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
     * Required for the LazyList
     */
    public Object create() {
       //Just a nonsense Chromosome as a place holder
       return new ChromosomeBean("0");
    }
}
