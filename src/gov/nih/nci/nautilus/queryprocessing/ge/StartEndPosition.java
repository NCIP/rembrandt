package gov.nih.nci.nautilus.queryprocessing.ge;

import gov.nih.nci.nautilus.de.BasePairPositionDE;
import gov.nih.nci.nautilus.de.ChromosomeNumberDE;

/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Oct 28, 2004
 * Time: 1:27:18 PM
 * To change this template use File | Settings | File Templates.
 */
final public class StartEndPosition {
    BasePairPositionDE startPosition;
    BasePairPositionDE endPosition;
    ChromosomeNumberDE chrNumber;

    public BasePairPositionDE getStartPosition() {
        return startPosition;
    }
    public BasePairPositionDE getEndPosition() {
        return endPosition;
    }
    public ChromosomeNumberDE getChrNumber() {
        return chrNumber;
    }

    public StartEndPosition(BasePairPositionDE startPosition, BasePairPositionDE endPosition, ChromosomeNumberDE chrNumber) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.chrNumber = chrNumber;
    }
}
