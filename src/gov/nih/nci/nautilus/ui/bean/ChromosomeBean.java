package gov.nih.nci.nautilus.ui.bean;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * This bean is used by the UI to package Chromosome information
 * for view and selection.
 * 
 * @author BauerD
 * Mar 14, 2005
 * 
 */
public class ChromosomeBean {
	private Logger logger = Logger.getLogger(ChromosomeBean.class);
	private int chromosomeNumber;
	private List cytobands;
	/**
	 * Returns a Collection of CytobandLookup Objects
	 * @return
	 */	
	public List getCytobands() {
		return cytobands;
	}
	
	public void setCytobands(List cytobands) {
		this.cytobands = cytobands;
	}
	
	public void setChromosomeNumber(int chromosomeNumber) {
		this.chromosomeNumber = chromosomeNumber;
	}
	
	public int getChromosomeNumber() {
		return chromosomeNumber;
	}
	/**
	 * Adds a new equals method.  Compares the chromosome numbers
	 * and if they are the same returns true, else false.
	 * @param bean
	 * @return
	 */
	public boolean equals(ChromosomeBean bean) {
		if(bean.getChromosomeNumber()==chromosomeNumber) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Overrides the Object.toString() method. Returns a 
	 * String representation of the chromosomes identifying
	 * number.
	 *  
	 * @return
	 */
	public String toString() {
		return Integer.toString(chromosomeNumber);
	}
		
}
