package gov.nih.nci.nautilus.ui.bean;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

/**
 * This bean is used by the UI to package Chromosome information
 * for view and selection.
 * 
 * @author BauerD
 * Mar 14, 2005
 * 
 */
public class ChromosomeBean extends LabelValueBean{
	private Logger logger = Logger.getLogger(ChromosomeBean.class);
	private String chromosome= "";
	private int value = 0;
	private ArrayList cytobands = new ArrayList();
		
	//Default Constructor
	public ChromosomeBean() {
		super(null,null);
	}
	
	public ChromosomeBean(String chromosome) {
		super(null,null);
		setChromosome(chromosome);
	}
	/**
	 * Returns a Collection of CytobandLookup Objects
	 * @return
	 */	
	public ArrayList getCytobands() {
		return cytobands;
	}
	
	public void setCytobands(ArrayList cytobands) {
		this.cytobands = cytobands;
	}
	
	public void setChromosome(String chromosome) {
		super.setLabel(chromosome);
		this.chromosome = chromosome;
	}
	
	public String getChromosome() {
		return chromosome;
	}
	/**
	 * Adds a new equals method.  Compares the chromosome numbers
	 * and if they are the same returns true, else false.
	 * @param bean
	 * @return
	 */
	public boolean equals(Object bean) {
		try{
			ChromosomeBean myBean = (ChromosomeBean)bean;
			if(bean!=null) {
				String test = myBean.getChromosome();
				if(this.chromosome.equals(test)) {
					return true;
				}
			}	
		}catch(ClassCastException cce){
			logger.error(cce);
		}
		return false;
	}
	/**
	 * Overrides the Object.toString() method. Returns a 
	 * String representation of the chromosomes identifying
	 * number.
	 *  
	 * @return
	 */
	public String toString() {
		return this.chromosome;
	}
	
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return Integer.toString(value);
	}
	
	
	/**
	 * @param value The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
