/*
 * Created on Nov 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.lookup;

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CytobandLookup extends Lookup{
	public abstract Long getCbEndPos();

	public abstract Long getCbStart();

	public abstract String getChromosome();

	public abstract String getCytoband();

	public abstract Long getCytobandPositionId();

	public abstract String getOrganism();
	
	public String getChrCytoband();
}