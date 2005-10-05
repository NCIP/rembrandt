package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;

/**
 * @author BhattarR
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
