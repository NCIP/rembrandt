package gov.nih.nci.nautilus.ui.bean;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import gov.nih.nci.nautilus.de.ChromosomeNumberDE;

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
	private ChromosomeNumberDE myChromosome;
	private Collection cytobands;
		
	public Collection getCytobands() {
		return cytobands;
	}
	
	public void setCytobands(Collection cytobands) {
		this.cytobands = cytobands;
	}
	
	public ChromosomeNumberDE getChromosomeNumberDE() {
		return myChromosome;
	}
	
	public void setChromosomeNumberDE(ChromosomeNumberDE chromosome) {
		this.myChromosome = chromosome;
	}
	
	public String getChromosomeNumber() {
		String chromosomeNumber = null;
		try {
			chromosomeNumber = (String)myChromosome.getValueObject();
			return chromosomeNumber;
		}catch(ClassCastException cce) {
			logger.error("ChromosomeNumberDE is not a String");
			logger.error(cce);
		}catch(NullPointerException npe) {
			logger.error("There is no ChromosomeNumberDE set yet");
			logger.error(npe);
		}
		return chromosomeNumber;
	}
}
