package gov.nih.nci.nautilus.queryprocessing;

import gov.nih.nci.nautilus.resultset.ResultSet;

import java.util.ArrayList;
import java.math.BigDecimal;



/**
 * Created by IntelliJ IDEA.
 * User: Ram
 * Date: Oct 6, 2004
 * Time: 4:14:02 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class GeneExpr implements ResultSet {
    private Long cloneId;
    private Long diseaseTypeId;
    private String diseaseType;
    private Double expressionRatio;
    private String probesetName;
    private String cloneName;
    private String geneSymbol;
    private Double normalIntensity;
    private Double sampleIntensity;
    private Long probesetId;
    private Long timecourseId;
    private GeneExpr.Annotaion annotation;



    public GeneExpr.Annotaion getAnnotation() {
        return annotation;
    }

    public void setAnnotation(GeneExpr.Annotaion annotation) {
        this.annotation= annotation;
    }

    public String getLocusLink() {
        return locusLink;
    }

    public void setLocusLink(String locusLink) {
        this.locusLink = locusLink;
    }

    private String locusLink;

    public Long getCloneId() {
        return cloneId;
    }

    public void setCloneId(Long cloneId) {
        this.cloneId = cloneId;
    }

    public Long getDiseaseTypeId() {
        return diseaseTypeId;
    }

    public void setDiseaseTypeId(Long diseaseTypeId) {
        this.diseaseTypeId = diseaseTypeId;
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
    public String getProbesetName() {
        return probesetName;
    }

    public void setProbesetName(String probesetName) {
        this.probesetName = probesetName;
    }

    public String getCloneName() {
        return cloneName;
    }

    public void setCloneName(String cloneName) {
        this.cloneName = cloneName;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    /*

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }
    */
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

    public Long getProbesetId() {
        return probesetId;
    }

    public void setProbesetId(Long probesetId) {
        this.probesetId = probesetId;
    }

    public Long getTimecourseId() {
        return timecourseId;
    }

    public void setTimecourseId(Long timecourseId) {
        this.timecourseId = timecourseId;
    }

    final public static class GeneExprSingle extends GeneExpr {
        private Long agentId;
        private String ageGroup;
        private Long biospecimenId;
        private Long desId;
        private Long diseaseHistoryId;
        private String survivalLengthRange;

        private Long treatmentHistoryId;
        private String cytoband;
        private String genderCode;

        public Long getAgentId() {
            return agentId;
        }

        public void setAgentId(Long agentId) {
            this.agentId = agentId;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public Long getBiospecimenId() {
            return biospecimenId;
        }

        public void setBiospecimenId(Long biospecimenId) {
            this.biospecimenId = biospecimenId;
        }

        public Long getDesId() {
            return desId;
        }

        public void setDesId(Long desId) {
            this.desId = desId;
        }

        public Long getDiseaseHistoryId() {
            return diseaseHistoryId;
        }

        public void setDiseaseHistoryId(Long diseaseHistoryId) {
            this.diseaseHistoryId = diseaseHistoryId;
        }

        public String getSurvivalLengthRange() {
            return survivalLengthRange;
        }

        public void setSurvivalLengthRange(String survivalLengthRange) {
            this.survivalLengthRange = survivalLengthRange;
        }

        public Long getTreatmentHistoryId() {
            return treatmentHistoryId;
        }

        public void setTreatmentHistoryId(Long treatmentHistoryId) {
            this.treatmentHistoryId = treatmentHistoryId;
        }

        public String getCytoband() {
            return cytoband;
        }

        public void setCytoband(String cytoband) {
            this.cytoband = cytoband;
        }

        public String getGenderCode() {
            return genderCode;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }
    }

    final public static class GeneExprGroup extends GeneExpr {
        private Double ratioPval;
        private Long degId;
        public Long getDegId() {
            return degId;
        }
        public void setDegId(Long degId) {
            this.degId = degId;
        }
        public Double getRatioPval() {
            return ratioPval;
        }
        public void setRatioPval(Double ratioPval) {
            this.ratioPval = ratioPval;
        }
    }
    public abstract static class Annotaion {
        ArrayList locusLinks;
        ArrayList accessions;
        Long ID;
        protected Annotaion() { }
        public Annotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            this.locusLinks = locusLinks;
            this.accessions = accessions;
            this.ID = ID;
        }
    }

    public static class ProbeAnnotaion extends Annotaion{
        public ProbeAnnotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            super(locusLinks, accessions, ID);
        }
    }
    public static class CloneAnnotaion extends Annotaion{
        public CloneAnnotaion(ArrayList locusLinks, ArrayList accessions, Long ID) {
            super(locusLinks, accessions, ID);
        }
    }
}
