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
    public final static class PathwayRetrieveEvent extends DBEvent {
        private final static String PATHWAY_CRIT_EVENT = "PathwayRetrieveEvent";
        public PathwayRetrieveEvent() {
            super(PATHWAY_CRIT_EVENT) ;
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

    public final static class SNPRetrieveEvent extends DBEvent {
        private final static String SNP_ID_CRIT_EVENT = "SNPIDRetrieveEvent";
        public SNPRetrieveEvent() {
            super(SNP_ID_CRIT_EVENT);
        }
    }
    public final static class SubQueryEvent extends DBEvent {
        private final static String SUB_QUERY_EVENT = "SubQueryEvent";
        private String threadID;
        public SubQueryEvent(String dynamicThreadID) {
            super(SUB_QUERY_EVENT);
            threadID = dynamicThreadID;
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
    public final static class AnnotationRetrieveEvent extends DBEvent {
        private String threadID;
        private final static String ANNOTATION_EVENT = "AnnotationRetrieveEvent";

        public String getThreadID() {
            return threadID;
        }

        public AnnotationRetrieveEvent (String dynamicThreadID) {
            super(ANNOTATION_EVENT);
            threadID = dynamicThreadID;
        }
    }

    //clinical
    public final static class SurvivalRangeRetrieveEvent extends DBEvent {
            private final static String SURVIVAL_CRIT_EVENT = "SurvivalRangeRetrieveEvent ";
            public SurvivalRangeRetrieveEvent () {
                super(SURVIVAL_CRIT_EVENT);
            }
    }
    public final static class AgeRangeRetrieveEvent extends DBEvent {
            private final static String AGE_CRIT_EVENT = "AgeRangeRetrieveEvent";
            public AgeRangeRetrieveEvent () {
                super(AGE_CRIT_EVENT);
            }
    }
    public final static class GenderRetrieveEvent extends DBEvent {
            private final static String AGE_CRIT_EVENT = "GenderRetrieveEvent";
            public GenderRetrieveEvent () {
                super(AGE_CRIT_EVENT);
            }
    }

}
