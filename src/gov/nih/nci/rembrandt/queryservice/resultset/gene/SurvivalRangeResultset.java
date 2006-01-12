package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;

/**
 * @author SahniH
 * Date: Oct 12, 2004
 * 
 */
public class SurvivalRangeResultset extends ViewByGroupResultset {

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#getType()
	 */
	public DomainElement getType() {
		// TODO Auto-generated method stub
		return (DatumDE) getSurvivalRange();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#setType(gov.nih.nci.nautilus.de.DomainElement)
	 */
	public void setType(DomainElement type) throws Exception {
        if (! (type instanceof DatumDE) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + type);
        setSurvivalRange((DatumDE)type);
	}
	/**
	 * @param ageGroup
	 */
	public SurvivalRangeResultset(DatumDE ageGroup) {		
		setSurvivalRange(ageGroup);
	}
	/**
	 * @return Returns the survivalRange.
	 */
	public DatumDE getSurvivalRange() {
		return (DatumDE) type;
	}
	/**
	 * @param survivalRange The survivalRange to set.
	 */
	public void setSurvivalRange(DatumDE survivalRange) {
		if(survivalRange != null){
	         type = survivalRange;
			 }
		}
}
