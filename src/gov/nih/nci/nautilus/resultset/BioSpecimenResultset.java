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
	private BioSpecimenIdentifierDE biospecimen = null;
	private DatumDE folgChangeValue = null;
	private DatumDE copyNumberValue = null;
	/**
	 * @return Returns the biospecimen.
	 */
	public String getBiospecimen() {
		return biospecimen.getValueObject();
	}
	/**
	 * @param biospecimen The biospecimen to set.
	 */
	public void setBiospecimen(BioSpecimenIdentifierDE biospecimen) {
		this.biospecimen = biospecimen;
	}
	/**
	 * @return Returns the folgChangeValue.
	 */
	public DatumDE getFolgChangeValue() {
		return folgChangeValue;
	}
	/**
	 * @param folgChangeValue The folgChangeValue to set.
	 */
	public void setFolgChangeValue(DatumDE folgChangeValue) {
		this.folgChangeValue = folgChangeValue;
	}
	public DatumDE getCopyNumberValue() {
		return copyNumberValue;
	}
	/**
	 * @param copyNumberValue The copyNumberValue to set.
	 */
	public void setCopyNumberValue(DatumDE copyNumberValue) {
		this.copyNumberValue = copyNumberValue;
	}
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the copyNumberValue.
	 */
}
