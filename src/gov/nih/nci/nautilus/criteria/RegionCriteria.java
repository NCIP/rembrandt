package gov.nih.nci.nautilus.criteria;

import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;
import gov.nih.nci.nautilus.de.CytobandDE;
/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 11, 2004
 * Time: 4:30:50 PM
 * To change this template use Options | File Templates.
 */
public class RegionCriteria extends Criteria {
    private CytobandDE cytoband;
    private ChromosomeNumberDE chromNumber;
    private BasePairPositionDE.StartPosition start;
    private BasePairPositionDE.EndPosition end;
    private boolean empty = true;

    public boolean isValid() {
        //TODO:  DO we need to add any more validation here?

        /* if cytoband is specified, then chromosomeNumber, start and end positions
          should not be specified */
        if (cytoband != null && (end != null || start != null) ) return false;

        // if specified, both start & end posistions together should be specified
        if ((end == null && start != null) || (end != null && start == null))
            return false;
        else {
             //  Chromosome Number is not null
             if (chromNumber == null) return false;

            //  Start Position should be less than End Position
             if (end.getValueObject().intValue() <  start.getValueObject().intValue())
                    return false;
        }
        return true;
    }

    public CytobandDE getCytoband() {
           return cytoband;
     }

    public void setCttoband(CytobandDE cytoband) {
       //assert(cytoband != null);
	   if(cytoband != null){
         this.cytoband = cytoband;
		 }
    }

    public BasePairPositionDE.StartPosition getStart() {
       return start;
    }

    public void setStart(BasePairPositionDE.StartPosition start) {
       this.start = start;
    }

    public BasePairPositionDE.EndPosition getEnd() {
       return end;
    }

   public void setEnd(BasePairPositionDE.EndPosition end) {
       this.end = end;
   }

    public ChromosomeNumberDE getChromNumber() {
        return chromNumber;
    }
    public void setChromNumber(ChromosomeNumberDE chromNumber) {
        this.chromNumber = chromNumber;
    }
}
