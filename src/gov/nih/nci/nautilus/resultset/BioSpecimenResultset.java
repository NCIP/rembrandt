/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset;
import gov.nih.nci.nautilus.de.*;

/**
 * @author SahniH
 *
 This class encapulates a sample Id and foldchange or copy number values..
 */
public class BioSpecimenResultset {
	private BioSpecimenDE biospecimen;
	private Float folgChangeValue;
	private Integer copyNumberValue;
	/**
	 * @return Returns the biospecimen.
	 */
	public String getBiospecimen() {
		return biospecimen.getValueObject();
	}
	/**
	 * @param biospecimen The biospecimen to set.
	 */
	public void setBiospecimen(String biospecimen) {
		this.biospecimen.setValueObject(biospecimen);
	}
	/**
	 * @return Returns the folgChangeValue.
	 */
	public Float getFolgChangeValue() {
		return folgChangeValue;
	}
	/**
	 * @param folgChangeValue The folgChangeValue to set.
	 */
	public void setFolgChangeValue(Float folgChangeValue) {
		this.folgChangeValue = folgChangeValue;
	}
	public Integer getCopyNumberValue() {
		return copyNumberValue;
	}
	/**
	 * @param copyNumberValue The copyNumberValue to set.
	 */
	public void setCopyNumberValue(Integer copyNumberValue) {
		this.copyNumberValue = copyNumberValue;
	}
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the copyNumberValue.
	 */
}
