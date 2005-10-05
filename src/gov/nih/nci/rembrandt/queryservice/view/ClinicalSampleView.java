package gov.nih.nci.rembrandt.queryservice.view;

import gov.nih.nci.caintegrator.dto.de.DomainElementClass;

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
