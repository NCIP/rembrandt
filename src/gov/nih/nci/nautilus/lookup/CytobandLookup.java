/*
 * Created on Nov 12, 2004
 */
package gov.nih.nci.nautilus.lookup;

/**
 * @author Himanso
 *
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