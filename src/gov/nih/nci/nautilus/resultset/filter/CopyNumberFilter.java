package gov.nih.nci.nautilus.resultset.filter;

import gov.nih.nci.nautilus.query.OperatorType;
import gov.nih.nci.nautilus.resultset.DimensionalViewContainer;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.copynumber.CopyNumberSingleViewResultsContainer;
import gov.nih.nci.nautilus.resultset.sample.SampleResultset;
import gov.nih.nci.nautilus.resultset.sample.SampleViewResultsContainer;
import gov.nih.nci.nautilus.view.ViewType.CopyNumberSampleView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CopyNumberFilter {

    public static Resultant filterCopyNumber(Resultant resultant, Integer consecutiveNocalls, Integer percentNoCall, OperatorType operator) {
            if (resultant != null && resultant.getAssociatedView().equals(CopyNumberSampleView.COPYNUMBER_GROUP_SAMPLE_VIEW)){
                ResultsContainer resultsContainer = resultant.getResultsContainer();
                Collection sampleIDs = new ArrayList();
                //Get the samples from the resultset object
                if(resultsContainer!= null){
                    Collection samples = null;
                    if(resultsContainer instanceof DimensionalViewContainer){               
                        DimensionalViewContainer dimensionalViewContainer = (DimensionalViewContainer) resultsContainer;
                        SampleViewResultsContainer sampleViewContainer = dimensionalViewContainer.getSampleViewResultsContainer();
                        samples = sampleViewContainer.getBioSpecimenResultsets();
                    }else if (resultsContainer instanceof SampleViewResultsContainer){
        
                        SampleViewResultsContainer sampleViewContainer = (SampleViewResultsContainer) resultsContainer;
                        samples = sampleViewContainer.getBioSpecimenResultsets();
                    }
                    if(samples != null && samples.size() > 0){
                        for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
        
                            SampleResultset sampleResultset =  (SampleResultset)sampleIterator.next();
                            if(isFilteredForConsectiveNocalls(sampleResultset, consecutiveNocalls)){
                        }
                     }

               }

            }
        }
        return null;
    }

    private static boolean isFilteredForConsectiveNocalls(SampleResultset sampleResultset, Integer consecutiveNocalls) {
       if(sampleResultset.getCopyNumberSingleViewResultsContainer() != null){
           CopyNumberSingleViewResultsContainer copyNumberContainer = sampleResultset.getCopyNumberSingleViewResultsContainer();
       }
        return false;
    }
    
}   
    
 
