package gov.nih.nci.nautilus.queryprocessing;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Sep 8, 2004
 * Time: 6:02:45 PM
 * To change this template use Options | File Templates.
 */
abstract public class DBEvent extends EventObject {
    public boolean completed ;

    final public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completedStatus) {
        this.completed = completedStatus;
    }
    public DBEvent(Object source) {
        super(source);
        completed = false;
    }

    public final static class RegionRetrieveEvent extends DBEvent {
        private final static String REGION_CRIT_EVENT = "RegionRetrieveEvent";
        public RegionRetrieveEvent() {
            super(REGION_CRIT_EVENT) ;
        }
    }
    public final static class OntologyRetrieveEvent extends DBEvent {
        private final static String ONTOLOGY_CRIT_EVENT = "OntologyRetrieveEvent";
        public OntologyRetrieveEvent() {
            super(ONTOLOGY_CRIT_EVENT) ;
        }
    }
    public final static class ProbeIDCloneIDRetrieveEvent extends DBEvent {
        private final static String PROBEID_CLONEID_CRIT_EVENT = "ProbeIDCloneIDRetrieveEvent";
        public ProbeIDCloneIDRetrieveEvent() {
            super(PROBEID_CLONEID_CRIT_EVENT);
        }
    }

    public final static class GeneIDRetrieveEvent extends DBEvent {
        private final static String GENE_ID_CRIT_EVENT = "GeneIDRetrieveEvent";
        public GeneIDRetrieveEvent() {
            super(GENE_ID_CRIT_EVENT  );
        }
    }

    public final static class FactRetrieveEvent extends DBEvent {
        private String threadID;
        private final static String FACT_EVENT = "FactRetrieveEvent";

        public String getThreadID() {
            return threadID;
        }

        public FactRetrieveEvent(String dynamicThreadID) {
            super(FACT_EVENT );
            threadID = dynamicThreadID;
        }
    }

}
