
// Generated by OJB SchemeGenerator

package gov.nih.nci.nautilus.data;

public class BiospecimenDim
{
  private Long biospecimenId;

  private Long patientDid;

  private String sampleId;

  private String specimenDesc;

  private String specimenName;

  private gov.nih.nci.nautilus.data.Patient aPatient;

  private java.util.Vector collArrayGenoAbnFact;

  private java.util.Vector collDifferentialExpressionSfact;

  public Long getBiospecimenId()
  {
     return this.biospecimenId;
  }
  public void setBiospecimenId(Long param)
  {
    this.biospecimenId = param;
  }


  public Long getPatientDid()
  {
     return this.patientDid;
  }
  public void setPatientDid(Long param)
  {
    this.patientDid = param;
  }


  public String getSampleId()
  {
     return this.sampleId;
  }
  public void setSampleId(String param)
  {
    this.sampleId = param;
  }


  public String getSpecimenDesc()
  {
     return this.specimenDesc;
  }
  public void setSpecimenDesc(String param)
  {
    this.specimenDesc = param;
  }


  public String getSpecimenName()
  {
     return this.specimenName;
  }
  public void setSpecimenName(String param)
  {
    this.specimenName = param;
  }


  public gov.nih.nci.nautilus.data.Patient getAPatient()
  {
     return this.aPatient;
  }
  public void setAPatient(gov.nih.nci.nautilus.data.Patient param)
  {
    this.aPatient = param;
  }


  public java.util.Vector getCollArrayGenoAbnFact()
  {
     return this.collArrayGenoAbnFact;
  }
  public void setCollArrayGenoAbnFact(java.util.Vector param)
  {
    this.collArrayGenoAbnFact = param;
  }


  public java.util.Vector getCollDifferentialExpressionSfact()
  {
     return this.collDifferentialExpressionSfact;
  }
  public void setCollDifferentialExpressionSfact(java.util.Vector param)
  {
    this.collDifferentialExpressionSfact = param;
  }


}

