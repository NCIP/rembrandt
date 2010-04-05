package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge.annotations;

import gov.nih.nci.rembrandt.dbbean.GenePathway;
import gov.nih.nci.rembrandt.dbbean.GeneOntology;

/**
 * User: Ram Bhattaru <BR>
 * Date: Feb 1, 2006 <BR>
 * Version: 1.0 <BR>
 */
public interface GeneDimension {
    Class getDimensionClass();
    String getGeneSymbolAttr();
    String getAttr();

    public class PathwayDimension implements GeneDimension {
        public Class getDimensionClass() {
            return GenePathway.class;
        }

        public String getGeneSymbolAttr() {
            return GenePathway.GENE_SYMBOL;
        }

        public String getAttr() {
            return GenePathway.PATHWAY_NAME;
        }
    }

    public class OntologyDimension implements GeneDimension {
        public Class getDimensionClass() {
            return GeneOntology.class;
        }

        public String getGeneSymbolAttr() {
            return GeneOntology.GENE_SYMBOL;
        }

        public String getAttr() {
            return GeneOntology.GO_ID;
        }
    }
}
