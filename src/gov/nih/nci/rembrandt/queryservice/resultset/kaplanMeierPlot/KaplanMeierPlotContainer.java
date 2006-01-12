package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierSampleInfo;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Himanso
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class KaplanMeierPlotContainer extends SampleViewResultsContainer {
	private static Logger logger = Logger.getLogger(KaplanMeierPlotContainer.class);

    private GeneIdentifierDE.GeneSymbol geneSymbol;
    private CytobandDE cytobandDE;

	/**
	 * @return Returns the geneSymbol.
	 */
	public GeneIdentifierDE.GeneSymbol getGeneSymbol() {
		return this.geneSymbol;
	}

	/**
	 * @param geneSymbol
	 *            The geneSymbol to set.
	 */
	public void setGeneSymbol(GeneIdentifierDE.GeneSymbol geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
	public KaplanMeierSampleInfo[] getSummaryKMPlotSamples() {
	    List<KaplanMeierSampleInfo> kmSampleInfoArray = new ArrayList<KaplanMeierSampleInfo>(); 
	    Collection samples = this.getSampleResultsets();
        //Clear the Previous collection
	    for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset) sampleIterator.next();
			if(sample != null &&
					sample.getSampleIDDE()!= null &&
					sample.getSurvivalLength()!= null &&
					sample.getCensor()!= null &&
					sample.getCensor().getValue() != null){
				Long time = sample.getSurvivalLength();
				Integer censor = new Integer((sample.getCensor().getValue().toString()));
				Double value = (Double) sample.getSummaryReporterFoldChange().getValue();
				KaplanMeierSampleInfo kmSampleInfo = new KaplanMeierSampleInfo(sample.getSampleIDDE().getValueObject(),time.intValue(), censor.intValue(), value.doubleValue());
				kmSampleInfoArray.add(kmSampleInfo);
			}
		}
		return (KaplanMeierSampleInfo[]) kmSampleInfoArray.toArray(new KaplanMeierSampleInfo[kmSampleInfoArray.size()]);
	}
	public KaplanMeierSampleInfo[] getKMPlotSamplesForReporter(String reporterName){
	    List<KaplanMeierSampleInfo> kmSampleInfoArray = new ArrayList<KaplanMeierSampleInfo>(); 
	    Collection samples = this.getSampleResultsets();
	    for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset) sampleIterator.next();
			if(sample != null &&
					sample.getSampleIDDE()!= null &&
					sample.getSurvivalLength()!= null &&
					sample.getCensor()!= null &&
					sample.getCensor().getValue() != null){
			ReporterResultset reporterResultset = sample.getReporterResultset(reporterName);
				if (reporterResultset != null && reporterResultset.getValue() != null){
					Long time = sample.getSurvivalLength();
					Integer censor = new Integer((sample.getCensor().getValue().toString()));
					DatumDE datumDE = reporterResultset.getValue();
	                Double value = (Double) datumDE.getValue();
	                KaplanMeierSampleInfo kmSampleInfo = new KaplanMeierSampleInfo(sample.getSampleIDDE().getValueObject(),time.intValue(), censor.intValue(), value.doubleValue());
					kmSampleInfoArray.add(kmSampleInfo);
				}
			}
		}
		return (KaplanMeierSampleInfo[]) kmSampleInfoArray.toArray(new KaplanMeierSampleInfo[kmSampleInfoArray.size()]);
	}
	public List getAssociatedReporters(){
		List reporterNames = Collections.EMPTY_LIST;
	    Collection samples = this.getSampleResultsets();
	    
	    if(!samples.isEmpty()){
	    	Iterator sampleIterator = samples.iterator();	    	
	    	SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset) sampleIterator.next();
            reporterNames = sample.getReporterNames();
	    }
	    return reporterNames;
	}

	/**
	 * @return Returns the cytobandDE.
	 */
	public CytobandDE getCytobandDE() {
		return cytobandDE;
	}
	/**
	 * @param cytobandDE The cytobandDE to set.
	 */
	public void setCytobandDE(CytobandDE cytobandDE) {
		this.cytobandDE = cytobandDE;
	}
//	Collection<SampleKaplanMeierPlotResultset> getSampleKaplanMeierPlotResultsets(){
//		Collection<SampleKaplanMeierPlotResultset> sampleKMPlotResultsetCollection = new ArrayList<SampleKaplanMeierPlotResultset>();
//		sampleKMPlotResultsetCollection.addAll((Collection<? extends SampleKaplanMeierPlotResultset>) this.getSampleResultsets());
//		return sampleKMPlotResultsetCollection;
//	}
	
}