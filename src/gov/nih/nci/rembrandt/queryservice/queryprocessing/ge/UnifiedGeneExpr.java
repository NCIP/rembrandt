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

    final public static class UnifiedGeneExprSingle extends UnifiedGeneExpr implements ClinicalResultSet{
        private String sampleId;
        private String ageGroup;
        private Long biospecimenId;
        private String genderCode;
        private String survivalLengthRange;
        
        public String getSampleId() {
            return sampleId;
        }

        public void setSampleId(String sampleId) {
            this.sampleId = sampleId;
        }

		/**
		 * @return Returns the ageGroup.
		 */
		public String getAgeGroup() {
			return ageGroup;
		}

		/**
		 * @param ageGroup The ageGroup to set.
		 */
		public void setAgeGroup(String ageGroup) {
			this.ageGroup = ageGroup;
		}

		/**
		 * @return Returns the biospecimenId.
		 */
		public Long getBiospecimenId() {
			return biospecimenId;
		}

		/**
		 * @param biospecimenId The biospecimenId to set.
		 */
		public void setBiospecimenId(Long biospecimenId) {
			this.biospecimenId = biospecimenId;
		}

		/**
		 * @return Returns the genderCode.
		 */
		public String getGenderCode() {
			return genderCode;
		}

		/**
		 * @param genderCode The genderCode to set.
		 */
		public void setGenderCode(String genderCode) {
			this.genderCode = genderCode;
		}

		/**
		 * @return Returns the survivalLengthRange.
		 */
		public String getSurvivalLengthRange() {
			return survivalLengthRange;
		}

		/**
		 * @param survivalLengthRange The survivalLengthRange to set.
		 */
		public void setSurvivalLengthRange(String survivalLengthRange) {
			this.survivalLengthRange = survivalLengthRange;
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
