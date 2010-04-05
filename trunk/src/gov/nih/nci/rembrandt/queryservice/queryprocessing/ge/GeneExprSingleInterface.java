package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.rembrandt.queryservice.resultset.ClinicalResultSet;

public interface GeneExprSingleInterface extends ClinicalResultSet{


	public abstract String getDiseaseType();

	public abstract void setDiseaseType(String diseaseType);

	public abstract Double getExpressionRatio();

	public abstract void setExpressionRatio(Double expressionRatio);

	public abstract String getGeneSymbol();

	public abstract void setGeneSymbol(String geneSymbol);

	public abstract Double getNormalIntensity();

	public abstract void setNormalIntensity(Double normalIntensity);

	public abstract Double getSampleIntensity();

	public abstract void setSampleIntensity(Double sampleIntensity);
	
	public abstract void setSpecimenName(String specimenName);
	
	public abstract String getSpecimenName();

}