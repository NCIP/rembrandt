package gov.nih.nci.nautilus.view;

import gov.nih.nci.nautilus.de.DomainElementClass;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:53:51 PM
 * To change this template use Options | File Templates.
 */
public class SampleCentricView extends View {
    private DomainElementClass[] validDEs;

    SampleCentricView() {
        validDEs = new DomainElementClass[]
              { DomainElementClass.PROBESET,
                DomainElementClass.EXPRFOLDCHANGE,
                DomainElementClass.DISEASE_NAME,
              };
    }

    DomainElementClass[] getValidElements() {
        return validDEs;
    }
}
