package gov.nih.nci.nautilus.criteria;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Jan 26, 2003
 * Time: 11:42:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllGenesCriteria extends Criteria {
    /**
     * @param allGenes
     */
    public AllGenesCriteria(boolean allGenes) {
        super();
        setAllGenes(allGenes);
    }
    public boolean isAllGenes() {
        return allGenes;
    }

    public void setAllGenes(boolean allGenes) {
        this.allGenes = allGenes;
       }

    boolean allGenes = false;

    public boolean isValid() {
        return true;
    }

    //TODO: The followig method checks if a given Criteria is empty
    public boolean isEmpty() {
        if (allGenes)
           return false;
        else
           return true;
    }
}
