package gov.nih.nci.rembrandt.queryservice.resultset.annotation;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.critieria.DiseaseOrGradeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.SampleCriteria;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;
import gov.nih.nci.rembrandt.queryservice.ResultsetManager;
import gov.nih.nci.rembrandt.queryservice.resultset.DimensionalViewContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.Resultant;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneExprSingleViewResultsContainer;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.GeneResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GeneExprAnnotationService {
	private static Logger logger = Logger.getLogger(ClinicalDataValidator.class);	

	public static List<GeneResultset> getAnnotationsForGeneSymbols(List<String> geneSymbols) throws Exception{
		List<GeneIdentifierDE.GeneSymbol> geneIdentifierDEs = new ArrayList<GeneIdentifierDE.GeneSymbol>();
		if(geneSymbols != null){
			for(String geneSymbol:geneSymbols){
				geneIdentifierDEs.add(new GeneIdentifierDE.GeneSymbol(geneSymbol));
			}
			return getAnnotationsForGeneIdentifierDE(geneIdentifierDEs);
		}
		return null;
	}

	public static List<GeneResultset> getAnnotationsForGeneIdentifierDE(List<GeneIdentifierDE.GeneSymbol> genes) throws Exception{
		GeneExpressionQuery geneExpressionQuery = createQuery();
		List<GeneResultset> geneResultsetOrderedList = new ArrayList<GeneResultset>();
		 if(geneExpressionQuery != null  && genes != null){
			 GeneIDCriteria geneIDCriteria = new GeneIDCriteria();
			 geneIDCriteria.setGeneIdentifiers(genes);
			 geneExpressionQuery.setGeneIDCrit(geneIDCriteria);
			 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
			  if(geneExprResultsContainer!=null) {
					for(GeneIdentifierDE geneID: genes){
						String geneSymbol ;
						geneSymbol = geneID.getValueObject();
						geneResultsetOrderedList.add(geneExprResultsContainer.getGeneResultset(geneSymbol));
					}
					return geneResultsetOrderedList;
				}
		 }
		return null;
	}
	public static List<ReporterResultset> getAnnotationsForReporters(List<String> reporterIDs) throws Exception{
		List<CloneIdentifierDE> reporterDEs = new ArrayList<CloneIdentifierDE>();
		if( reporterIDs != null){
				for(String reporterID:reporterIDs){
					if(reporterID != null  && reporterID.indexOf("IMAGE")>0){
						reporterDEs.add(new CloneIdentifierDE.IMAGEClone(reporterID));
					}
					else{
						reporterDEs.add(new CloneIdentifierDE.ProbesetID(reporterID));
					}
				}
		}
		return getAnnotationsForCloneIdentifierDEs(reporterDEs);
	}
	public static List<ReporterResultset> getAnnotationsForCloneIdentifierDEs(List<CloneIdentifierDE> reporters) throws Exception{
			GeneExpressionQuery geneExpressionQuery = createQuery();
			List<ReporterResultset> reporterResultsetOrderedList = new ArrayList<ReporterResultset>();
	
			 if(geneExpressionQuery != null  && reporters != null){
				 CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
				 reporterCriteria.setIdentifiers(reporters);
				 geneExpressionQuery.setCloneOrProbeIDCrit(reporterCriteria);
				 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
				  if(geneExprResultsContainer!=null) {
						for(CloneIdentifierDE reporter: reporters){
							String reporterID = reporter.getValueObject();
							reporterResultsetOrderedList.add(geneExprResultsContainer.getReporterResultset(reporterID));
						}
						return reporterResultsetOrderedList;
					}
			 }
		return null;
	}
	public static ReporterResultset getAnnotationsForReporter(String reporterID) throws Exception{
		if(reporterID != null){
			CloneIdentifierDE cloneIdentifierDE = null ;
			if(reporterID != null  && reporterID.indexOf("IMAGE")>0){
				cloneIdentifierDE = new CloneIdentifierDE.IMAGEClone(reporterID);
			}
			else{
				cloneIdentifierDE = new CloneIdentifierDE.ProbesetID(reporterID);
			}
		return getAnnotationsForCloneIdentifierDE(cloneIdentifierDE);
		}
		return null;
	}
	public static ReporterResultset getAnnotationsForCloneIdentifierDE(CloneIdentifierDE reporter) throws Exception{
		GeneExpressionQuery geneExpressionQuery = createQuery();
		//List<ReporterResultset> reporterResultsetOrderedList = new ArrayList<ReporterResultset>();

		 if(geneExpressionQuery != null  && reporter != null ){
			 CloneOrProbeIDCriteria reporterCriteria = new CloneOrProbeIDCriteria();
			 reporterCriteria.setCloneIdentifier(reporter);
			 geneExpressionQuery.setCloneOrProbeIDCrit(reporterCriteria);
			 GeneExprResultsContainer geneExprResultsContainer = executeGeneExprSampleQuery(geneExpressionQuery);
			  if(geneExprResultsContainer!=null) {
						return geneExprResultsContainer.getReporterResultset(reporter.getValueObject());

				}
		 }
		return null;
	}
	private static GeneExpressionQuery createQuery(){

//		create a GeneExpressionQuery to contrain by GeneIds and Disease 
		 GeneExpressionQuery geneExpressionQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
		 geneExpressionQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
		 ArrayPlatformCriteria arrayPlatformCrit = new ArrayPlatformCriteria();
		 arrayPlatformCrit.setPlatform(new ArrayPlatformDE(Constants.ALL_PLATFROM));
		 geneExpressionQuery.setArrayPlatformCrit(arrayPlatformCrit);
		 //DiseaseOrGradeCriteria diseaseCrit = new DiseaseOrGradeCriteria();
		 //diseaseCrit.setDisease(new DiseaseNameDE(RembrandtConstants.ALL));
		 SampleCriteria sampleCrit = new SampleCriteria();
		 sampleCrit.setSampleID(new SampleIDDE("HF1220"));
		 return geneExpressionQuery;
	}
	private static GeneExprResultsContainer executeGeneExprSampleQuery(GeneExpressionQuery geneExpressionQuery) throws Exception{
		Resultant resultant = null;
		GeneExprSingleViewResultsContainer geneExprSingleViewResultsContainer = null;
		try {
			CompoundQuery compoundQuery = new CompoundQuery(geneExpressionQuery);
			resultant = ResultsetManager.executeCompoundQuery(compoundQuery);
 		}
 		catch (Throwable t)	{
 			logger.error("Error Executing the query/n"+ t.getMessage());
 			throw new Exception("Error executing geneExpressionQuery/n"+t.getMessage());
 		}
  		if(resultant != null) {      

  			if(resultant.getResultsContainer() instanceof DimensionalViewContainer) {
  				DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer)resultant.getResultsContainer();
  				geneExprSingleViewResultsContainer = dimensionalViewContainer.getGeneExprSingleViewContainer();
  			}
	 	}
  		return geneExprSingleViewResultsContainer;

	}
}
