/*
 * Created on Sep 14, 2004
 *
 */
package gov.nih.nci.nautilus.queryprocessing;
import gov.nih.nci.nautilus.resultset.*;
import gov.nih.nci.nautilus.data.*;
import gov.nih.nci.nautilus.de.*;

import java.util.*;

/**
 * @author SahniH
 *
 * This class takes a DifferentialExpressionSfact and DifferentialExpressionGfact object and helps create a GeneCentricViewHandler or a SampleCentricViewHandler classes.
 */
public class ResultsetProcessor {
	public GeneViewContainer geneViewContainer = new GeneViewContainer();
	public void createGeneView(Collection differentialExpressionSfact){
		if(differentialExpressionSfact != null && differentialExpressionSfact.size()>0) {
		Iterator iter = differentialExpressionSfact.iterator();
	      while (iter.hasNext()){
	      	DifferentialExpressionSfact diffExpSfact = (DifferentialExpressionSfact) iter.next();
	      	if (diffExpSfact != null){
	      		//get the gene accessesion number for this record
	      		//check if the gene exsists in the GeneViewContainer, otherwise add a new one.
	      		GeneResultset geneResultset = geneViewContainer.getGeneResultsets(diffExpSfact.getGeneSymbol());
	      		if(geneResultset == null){ // no record found
	      			geneResultset = new GeneResultset();
	      		}
	      		GeneIdentifierDE.GeneSymbol geneSymbol = new GeneIdentifierDE.GeneSymbol(diffExpSfact.getGeneSymbol());
	      	    GeneIdentifierDE.GenBankAccessionNumber genbankAccessionNo = new GeneIdentifierDE.GenBankAccessionNumber(diffExpSfact.getGeneSymbol()); //TODO: add GenBankAccessionNumber
//	      	    GeneIdentifierDE.LocusLink locusLinkID = new GeneIdentifierDE.LocusLink(); // add GeneIdentifierDE.LocusLink
	      		geneResultset.setGeneSymbol(geneSymbol);
	      		geneResultset.setGenbankAccessionNo(genbankAccessionNo);
	      		// find out if it has a probeset or a clone associated with it
	      		//populate ReporterResultset with the approciate one
	      		ReporterResultset reporterResultset = new ReporterResultset();
	      		//TODO: TEMP PROBESET ID, NEED to SWITCH IT to PROBESET NAME or CLONE NAME
	      		if(diffExpSfact.getProbesetId() != null ){
	      			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,diffExpSfact.getProbesetId());
	      			reporterResultset.setReporter(reporter);
	      		}
	      		/**
	      		if(diffExpSfact.getProbesetName() != null && diffExpSfact.getCloneName() == null){
	      			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,diffExpSfact.getProbesetName());
	      			reporterResultset.setReporter(reporter);
	      		}
	      		else if(diffExpSfact.getProbesetName() == null && diffExpSfact.getCloneName() != null){
	      			DatumDE reporter = new DatumDE(DatumDE.CLONE_ID,diffExpSfact.getCloneName());
	      			reporterResultset.setReporter(reporter);
	      		}
	      		**/
	      		//find out the disease type associated with the diffExpSfact
	      		//populate the DiseaseResultset
	      		DiseaseResultset diseaseResultset = new DiseaseResultset();
	      		//TODO: TEMP DISEASETYPE ID, NEED to SWITCH IT to DISEASE NAME 
	      		//DiseaseNameDE disease = new DiseaseNameDE(diffExpSfact.getADiseaseTypeDim().getDiseaseType());
	      		DiseaseNameDE disease = new DiseaseNameDE(diffExpSfact.getDiseaseTypeId().toString());
	      		diseaseResultset.setDieaseType(disease);
				//find out the biospecimenID associated with the diffExpSfact
				//populate the BiospecimenResuluset
	      		//TODO: TEMP BIOSPECIMEN ID, NEED to SWITCH to SAMPLE_NAME
				BioSpecimenResultset biospecimenResultset = new BioSpecimenResultset();
				//BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(diffExpSfact.getABiospecimenDim().getSampleId());
				BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(diffExpSfact.getBiospecimenId().toString());
	      		biospecimenResultset.setBiospecimen(biospecimenID);
	      		DatumDE foldchange = new DatumDE(DatumDE.FOLD_CHANGE,diffExpSfact.getSampleIntensity());
	      		//add biospecimenresultset to disease etc ...
	      		diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
	      		reporterResultset.addDiseaseResultset(diseaseResultset);
	      		geneResultset.addReporterResultset(reporterResultset);
	      		//add the reporter to geneResultset
	      		geneViewContainer.addGeneResultset(geneResultset);
	      	}
	       }
	      }

	}
	/**
	 * @return Returns the geneViewContainer.
	 */
	public GeneViewContainer getGeneViewContainer() {
		return geneViewContainer;
	}
	public static void main(String[] args) {
	}
}
