package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
 */


public class DiseaseGroupResultset implements Groupable, FoldChange{
	private DiseaseNameDE diseaseType = null;
	private DatumDE foldChangeRatioValue = null;
	private DatumDE foldChangeIntensity = null;
	private DatumDE foldChangeNonTumorIntensity = null;
	private DatumDE ratioPval = null;
	/**
	 * @return Returns the diseaseType.
	 */
	public DiseaseNameDE getDiseaseType() {
		return this.diseaseType;
	}
	/**
	 * @param diseaseType The diseaseType to set.
	 */
	public void setDiseaseType(DiseaseNameDE diseaseType) {
		this.diseaseType = diseaseType;
	}
	/**
	 * @param disease
	 */
	public DiseaseGroupResultset(DiseaseNameDE diseaseType) {		
		setDiseaseType(diseaseType);
	}

	/**
	 * @return Returns the foldChangeRatioValue.
	 */
	public DatumDE getFoldChangeRatioValue() {
		return this.foldChangeRatioValue;
	}
	/**
	 * @param foldChangeRatioValue The foldChangeRatioValue to set.
	 */
	public void setFoldChangeRatioValue(DatumDE foldChangeRatioValue) {
		this.foldChangeRatioValue = foldChangeRatioValue;
	}
	/**
	 * @return Returns the ratioPval.
	 */
	public DatumDE getRatioPval() {
		return this.ratioPval;
	}
	/**
	 * @param ratioPval The ratioPval to set.
	 */
	public void setRatioPval(DatumDE ratioPval) {
		this.ratioPval = ratioPval;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#getType()
	 */
	public DomainElement getType() {
		return (DiseaseNameDE) getDiseaseType();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#setType(gov.nih.nci.nautilus.de.DomainElement)
	 */
	public void setType(DomainElement type) throws Exception {
        if (! (type instanceof DiseaseNameDE) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + type);
        setDiseaseType((DiseaseNameDE)type);
	}


	/**
	 * @return Returns the foldChangeIntensity.
	 */
	public DatumDE getFoldChangeIntensity() {
		return foldChangeIntensity;
	}
	/**
	 * @param foldChangeIntensity The foldChangeIntensity to set.
	 */
	public void setFoldChangeIntensity(DatumDE foldChangeIntensity) {
		this.foldChangeIntensity = foldChangeIntensity;
	}
	/**
	 * @return Returns the foldChangeNonTumorIntensity.
	 */
	public DatumDE getFoldChangeNonTumorIntensity() {
		return foldChangeNonTumorIntensity;
	}
	/**
	 * @param foldChangeNonTumorIntensity The foldChangeNonTumorIntensity to set.
	 */
	public void setFoldChangeNonTumorIntensity(
			DatumDE foldChangeNonTumorIntensity) {
		this.foldChangeNonTumorIntensity = foldChangeNonTumorIntensity;
	}
}
