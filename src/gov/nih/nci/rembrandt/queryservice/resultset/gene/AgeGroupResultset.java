package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;

/**
 * @author SahniH
 * Date: Oct 12, 2004
 * 
 */
public class AgeGroupResultset extends ViewByGroupResultset {

	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#getType()
	 */
	public DomainElement getType() {
		// TODO Auto-generated method stub
		return (DatumDE) getAgeGroup();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#setType(gov.nih.nci.nautilus.de.DomainElement)
	 */
	public void setType(DomainElement type) throws Exception {
        if (! (type instanceof DatumDE) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + type);
        setAgeGroup((DatumDE)type);
	}
	/**
	 * @param ageGroup
	 */
	public AgeGroupResultset(DatumDE ageGroup) {		
		setAgeGroup(ageGroup);
	}
	/**
	 * @return Returns the ageGroup.
	 */
	public DatumDE getAgeGroup() {
		return (DatumDE) type;
	}
	/**
	 * @param ageGroup The ageGroup to set.
	 */
	public void setAgeGroup(DatumDE ageGroup) {
		if(ageGroup != null){
	         type = ageGroup;
			 }
		}
}
