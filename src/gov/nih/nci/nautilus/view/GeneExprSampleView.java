package gov.nih.nci.nautilus.view;

import gov.nih.nci.nautilus.de.DomainElementClass;

/**
 * @author BhattarR
 */
public class GeneExprSampleView extends View {
	private GroupType groupType = GroupType.DISEASE_TYPE_GROUP; //always defalut to disease
    /**
	 * @return Returns the groupType.
	 */
	public GroupType getGroupType() {
		return groupType;
	}
	/**
	 * @param groupType The groupType to set.
	 */
	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}
	private static DomainElementClass[] validDEs
            = new DomainElementClass[]
              { DomainElementClass.LOCUS_LINK,
                DomainElementClass.GENBANK_ACCESSION_NUMBER,
                DomainElementClass.CHROMOSOME_NUMBER,
                DomainElementClass.PATHWAY,
              };

    public DomainElementClass[] getValidElements() {
        return validDEs;
    }
}
