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
//	      	    GeneIdentifierDE.GenBankAccessionNumber genbankAccessionNo = new GeneIdentifierDE.GenBankAccessionNumber();//TODO: add GenBankAccessionNumber
//	      	    GeneIdentifierDE.LocusLink locusLinkID = new GeneIdentifierDE.LocusLink(); // add GeneIdentifierDE.LocusLink
	      		geneResultset.setGeneSymbol(geneSymbol);
	      		// find out if it has a probeset or a clone associated with it
	      		//populate ReporterResultset with the approciate one
	      		ReporterResultset reporterResultset = new ReporterResultset();
	      		
	      		if(diffExpSfact.getProbesetName() != null && diffExpSfact.getCloneName() == null){
	      			DatumDE reporter = new DatumDE(DatumDE.PROBESET_ID,diffExpSfact.getProbesetName());
	      			reporterResultset.setReporter(reporter);
	      		}
	      		else{
	      			DatumDE reporter = new DatumDE(DatumDE.CLONE_ID,diffExpSfact.getProbesetName());
	      			reporterResultset.setReporter(reporter);
	      		}
	      		//find out the disease type associated with the diffExpSfact
	      		//populate the DiseaseResultset
	      		DiseaseResultset diseaseResultset = new DiseaseResultset();
	      		DiseaseNameDE disease = new DiseaseNameDE(diffExpSfact.getADiseaseTypeDim().getDiseaseType());
	      		diseaseResultset.setDieaseType(disease);
				//find out the biospecimenID associated with the diffExpSfact
				//populate the BiospecimenResuluset
				BioSpecimenResultset biospecimenResultset = new BioSpecimenResultset();
				BioSpecimenIdentifierDE biospecimenID = new BioSpecimenIdentifierDE(diffExpSfact.getABiospecimenDim().getSampleId());
	      		biospecimenResultset.setBiospecimen(biospecimenID);
	      		//add biospecimenresultset to disease etc ...
	      		diseaseResultset.addBioSpecimenResultset(biospecimenResultset);
	      		reporterResultset.addDiseaseResultset(diseaseResultset);
	      		geneResultset.addReporterResultset(reporterResultset);
	      		//add the reporter to geneResultset
	      		geneViewContainer.addGeneResultset(geneResultset);
	      	}
	      }

	}
	public static void main(String[] args) {
	}
}
