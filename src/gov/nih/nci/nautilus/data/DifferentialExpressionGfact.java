
// Generated by OJB SchemeGenerator

package gov.nih.nci.nautilus.data;

public class DifferentialExpressionGfact
{

    //public final static String DEG_ID = "degId";
    public final static String CLONE_ID = "cloneId";
    public final static String PROBESET_ID= "probesetId";
     public final static String DISEASE_TYPE = "diseaseType";
    //public final static String CLONE_NAME = "cloneName";
    //public final static String PROBE_NAME = "probesetName";
    //public final static String GENE_SYMBOL = "geneSymbol";
    //public final static String EXPRESSION_RATIO = "expressionRatio";

  private Long cloneId;
  private Long datasetId;
  private Long degId;
 private Long diseaseTypeId;
  private Double expressionRatio;
  private Long expPlatformId;

 private String geneSymbol;
  private Long institutionId;
  private Double normalIntensity;
  private Long probesetId;
 private Double ratioPval;
  private Double sampleIntensity;

  private Long timecourseId;
    private String probesetName;
    private String cloneName;
     public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    private String diseaseType;

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
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

  public Long getCloneId()
  {
     return this.cloneId;
  }
  public void setCloneId(Long param)
  {
    this.cloneId = param;
  }


  public Long getDatasetId()
  {
     return this.datasetId;
  }
  public void setDatasetId(Long param)
  {
    this.datasetId = param;
  }


  public Long getDegId()
  {
     return this.degId;
  }
  public void setDegId(Long param)
  {
    this.degId = param;
  }


  public Long getDiseaseTypeId()
  {
     return this.diseaseTypeId;
  }
  public void setDiseaseTypeId(Long param)
  {
    this.diseaseTypeId = param;
  }


  public Double getExpressionRatio()
  {
     return this.expressionRatio;
  }
  public void setExpressionRatio(Double param)
  {
    this.expressionRatio = param;
  }


  public Long getExpPlatformId()
  {
     return this.expPlatformId;
  }
  public void setExpPlatformId(Long param)
  {
    this.expPlatformId = param;
  }




  public Long getInstitutionId()
  {
     return this.institutionId;
  }
  public void setInstitutionId(Long param)
  {
    this.institutionId = param;
  }


  public Double getNormalIntensity()
  {
     return this.normalIntensity;
  }
  public void setNormalIntensity(Double param)
  {
    this.normalIntensity = param;
  }


  public Long getProbesetId()
  {
     return this.probesetId;
  }
  public void setProbesetId(Long param)
  {
    this.probesetId = param;
  }


  public Double getRatioPval()
  {
     return this.ratioPval;
  }
  public void setRatioPval(Double param)
  {
    this.ratioPval = param;
  }


  public Double getSampleGIntensity()
  {
     return this.sampleIntensity;
  }
  public void setSampleGIntensity(Double param)
  {
    this.sampleIntensity = param;
  }

  public Long getTimecourseId()
  {
     return this.timecourseId;
  }
  public void setTimecourseId(Long param)
  {
    this.timecourseId = param;
  }


}

