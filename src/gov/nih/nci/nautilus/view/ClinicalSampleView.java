package gov.nih.nci.nautilus.view;

import gov.nih.nci.nautilus.de.DomainElementClass;

/**
 * @author BhattarR
 */
public class ClinicalSampleView extends View {
    private DomainElementClass[] validDEs;

    ClinicalSampleView() {
        validDEs = new DomainElementClass[]
              { DomainElementClass.DISEASE_NAME,
        		//TODO: All List here
              };
    }

    public DomainElementClass[] getValidElements() {
        return validDEs;
    }
}
