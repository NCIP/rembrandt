/*
 * Created on Sep 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.resultset.gene;
import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.de.DomainElement;
/**
 * @author SahniH
 *
 * This class encapulates a collection of DieaseResultset objects.
 */
public class DiseaseTypeResultset extends ViewByGroupResultset{
		/**
		 * @param disease
		 */
		public DiseaseTypeResultset(DiseaseNameDE diseaseType) {		
			setDiseaseType(diseaseType);
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
		 * @return Returns the diseaseType.
		 */
		public DiseaseNameDE getDiseaseType() {
			return (DiseaseNameDE) type;
		}
		/**
		 * @param diseaseType The diseaseType to set.
		 */
		public void setDiseaseType(DiseaseNameDE diseaseType) {
			if(diseaseType != null){
		         type = diseaseType;
				 }
			}
}
