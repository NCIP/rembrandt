package gov.nih.nci.nautilus.criteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Jan 26, 2003
 * Time: 11:42:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllGenesCriteria extends Criteria {
    public boolean isAllGenes() {
        return allGenes;
    }

    public void setAllGenes(boolean allGenes) {
        this.allGenes = allGenes;
    }

    boolean allGenes;

    public boolean isValid() {
        return true;
    }
}
