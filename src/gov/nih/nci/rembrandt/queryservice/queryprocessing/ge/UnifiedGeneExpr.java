package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultSet;

import java.util.ArrayList;



/**
 * @author BhattarR
 */
abstract public class UnifiedGeneExpr implements ResultSet{
    private String diseaseType;
    private Double expressionRatio;
    private String unifiedGeneID;
    private String geneSymbol;
    private Double normalIntensity;
    private Double sampleIntensity;

    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUnifiedGeneID() {
        return unifiedGeneID;
    }

    public void setUnifiedGeneID(String unifiedGeneID) {
        this.unifiedGeneID = unifiedGeneID;
    }


    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }
    public Double getExpressionRatio() {
        return expressionRatio;
    }

    public void setExpressionRatio(Double expressionRatio) {
        this.expressionRatio = expressionRatio;
    }
    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public Double getNormalIntensity() {
        return normalIntensity;
    }

    public void setNormalIntensity(Double normalIntensity) {
        this.normalIntensity = normalIntensity;
    }

    public Double getSampleIntensity() {
        return sampleIntensity;
    }

    public void setSampleIntensity(Double sampleIntensity) {
        this.sampleIntensity = sampleIntensity;
    }

    final public static class UnifiedGeneExprSingle extends UnifiedGeneExpr {
        private String sampleId;


        public String getSampleId() {
            return sampleId;
        }

        public void setSampleId(String sampleId) {
            this.sampleId = sampleId;
        }
    }

    final public static class UnifiedGeneExprGroup extends UnifiedGeneExpr {
        private Double ratioPval;

        private Double standardDeviationRatio;

        public Double getStandardDeviation() {
            return standardDeviationRatio;
        }
        public void setStandardDeviation(Double standardDeviationRatio) {
            this.standardDeviationRatio = standardDeviationRatio;
        }
       public Double getRatioPval() {
            return ratioPval;
        }
        public void setRatioPval(Double ratioPval) {
            this.ratioPval = ratioPval;
        }
    }
 }
