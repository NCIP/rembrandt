package gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot;

import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.SampleIDDE;
import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierSampleInfo;
import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.caintegrator.util.MathUtil;
import gov.nih.nci.rembrandt.queryservice.resultset.gene.ReporterResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleViewResultsContainer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author Himanso
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
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
	public KaplanMeierSampleInfo[] getMeanKMPlotSamples() {
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
				Double value = 0.0;
				if(sample.getMeanReporterValue()!= null){
				value = (Double) sample.getMeanReporterValue();
				}
				KaplanMeierSampleInfo kmSampleInfo = new KaplanMeierSampleInfo(sample.getSampleIDDE().getValueObject(),time.intValue(), censor.intValue(), value.doubleValue());
				kmSampleInfoArray.add(kmSampleInfo);
			}
		}
		return (KaplanMeierSampleInfo[]) kmSampleInfoArray.toArray(new KaplanMeierSampleInfo[kmSampleInfoArray.size()]);
	}
	public KaplanMeierSampleInfo[] getMedianKMPlotSamples() {
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
				Double value = 0.0;
				if(sample.getMedianReporterValue()!= null){
				value = (Double) sample.getMedianReporterValue();
				}
				KaplanMeierSampleInfo kmSampleInfo = new KaplanMeierSampleInfo(sample.getSampleIDDE().getValueObject(),time.intValue(), censor.intValue(), value.doubleValue());
				kmSampleInfoArray.add(kmSampleInfo);
			}
		}
		return (KaplanMeierSampleInfo[]) kmSampleInfoArray.toArray(new KaplanMeierSampleInfo[kmSampleInfoArray.size()]);
	}
	public KaplanMeierSampleInfo[] getRestOfSummaryKMPlotSamples(Collection<SampleIDDE> excludeSampleIds) {
	    List<KaplanMeierSampleInfo> kmSampleInfoArray = new ArrayList<KaplanMeierSampleInfo>();
	    //Get all SampleIDDEs
	    Collection<SampleResultset> samples = this.getSampleResultsets();
	    Collection<SampleIDDE>sampleIds = new ArrayList<SampleIDDE>();
	    for (SampleResultset sampleResultset: samples){
	    	sampleIds.add(sampleResultset.getSampleIDDE());
	    }
	    //remove the sampleIDs in the "excludeSampleIds" from "sampleIds" list
		//This will help us get the plot for the rest of the samples
		Set<SampleIDDE> restofSampleSet = new HashSet<SampleIDDE>();
		restofSampleSet.addAll(sampleIds);
		if(excludeSampleIds!=null)	{
			restofSampleSet.removeAll(excludeSampleIds);
		}
		
        //Clear the Previous collection
	    for (Iterator sampleIterator = samples.iterator(); sampleIterator.hasNext();) {
			SampleKaplanMeierPlotResultset sample = (SampleKaplanMeierPlotResultset) sampleIterator.next();
			if(sample != null &&
					sample.getSampleIDDE()!= null &&
					sample.getSurvivalLength()!= null &&
					sample.getCensor()!= null &&
					sample.getCensor().getValue() != null  &&
					// make sure its part of the restOfSampleSet 
					restofSampleSet.contains(sample.getSampleIDDE())){
				Long time = sample.getSurvivalLength();
				Integer censor = new Integer((sample.getCensor().getValue().toString()));
				Double value = 0.0;
				if(sample.getMeanReporterValue()!= null){
				value = (Double) sample.getMeanReporterValue();
				}
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
	@SuppressWarnings("unchecked")
	public List getAssociatedReporters(){
		Set reporterNames = new HashSet();
	    Collection<SampleResultset> samples = this.getSampleResultsets();
	    for(SampleResultset sampleResultset:samples ){
	    	SampleKaplanMeierPlotResultset kmSample = (SampleKaplanMeierPlotResultset)sampleResultset;
	    	reporterNames.addAll(kmSample.getReporterNames());
	    }
	    
	    return new ArrayList(reporterNames);
	}
	@SuppressWarnings("unchecked")
	 public List getAssociatedSNPReportersSortedByPosition(){
	  Set reporters = new HashSet();
	  SortedMap positionReporterMap = new TreeMap();
	     Collection<SampleResultset> samples = this.getSampleResultsets();
	     for(SampleResultset sampleResultset:samples ){
	      SampleKaplanMeierPlotResultset kmSample = (SampleKaplanMeierPlotResultset)sampleResultset;
	      reporters.addAll(kmSample.getReporterResultsets());
	     }     
	     for(Object reporter:reporters){
	      ReporterResultset reporterResultset = (ReporterResultset)reporter;
	      if(reporterResultset.getStartPhysicalLocation()!= null  && reporterResultset.getReporter()!= null){
	       Long startPosition = reporterResultset.getStartPhysicalLocation().getValueObject();
	       String reporterName = reporterResultset.getReporter().getValue().toString();
	       positionReporterMap.put(startPosition,reporterName);
	      }
	     }     
	     return new ArrayList(positionReporterMap.values());
	 }
	@SuppressWarnings("unchecked")
	 public List getAssociatedGEReportersSortedByMeanIntensity(){
	  Set reporters = new HashSet();
	  Map<String,List<Double>> intensityReporterMap = new HashMap<String,List<Double>>();
	  SortedMap <Double,String> meanIntensityMap = new TreeMap<Double, String>();
	     Collection<SampleResultset> samples = this.getSampleResultsets();
	     for(SampleResultset sampleResultset:samples ){
	      SampleKaplanMeierPlotResultset kmSample = (SampleKaplanMeierPlotResultset)sampleResultset;
	      reporters.addAll(kmSample.getReporterResultsets());	      
	     }     
	     for(Object reporter:reporters){
	      ReporterResultset reporterResultset = (ReporterResultset)reporter;
	      if(reporterResultset.getIntensityValue()!= null  && reporterResultset.getReporter()!= null){
		       Double intensity = (Double) reporterResultset.getIntensityValue().getValue();
		       String reporterName = reporterResultset.getReporter().getValue().toString();
		       List<Double> intensityList = intensityReporterMap.get(reporterName);
		       if(intensityList == null){
		    	   intensityList = new ArrayList<Double>();
		       }
		       if(intensity!= null){
		    	   intensityList.add(MathUtil.getLog2(intensity));
		       }
		       intensityReporterMap.put(reporterName, intensityList);
	      }	      
	     }    
	     for(String reporter: intensityReporterMap.keySet()){
	    	 Double mean = getMeanReporterValue(intensityReporterMap.get(reporter));
	    	 meanIntensityMap.put(mean, reporter);
	     }
	     Set intensitySet = meanIntensityMap.keySet();
	     List<Double> intensityList = new ArrayList<Double>(intensitySet);
	     //reverse the list so the highest intensity is at the top
	     Collections.reverse(intensityList);
	     List reporterList = new ArrayList();
	     for(Double intensity:intensityList){
	    	 reporterList.add(meanIntensityMap.get(intensity));
	     }
	     //Make labels verbose
	     // For the first one
	     if ( reporterList != null && !reporterList.isEmpty() ) {
		     String firstReporter = (String) reporterList.get(0);
		     String lastReporter = (String) reporterList.get(reporterList.size()-1);
		     firstReporter = firstReporter.concat(CaIntegratorConstants.HIGHEST_GEOMETRIC_MEAN_INTENSITY);
		     lastReporter = lastReporter.concat(CaIntegratorConstants.LOWEST_GEOMETRIC_MEAN_INTENSITY);
		     reporterList.remove(0);
		     reporterList.add(0,firstReporter);
		     reporterList.remove(reporterList.size()-1);
		     reporterList.add(reporterList.size(),lastReporter);
		     return reporterList;
	     }
	     return null;
	 }
	/**
	 * @return mean of all reporters
	 */
    private Double getMeanReporterValue(List<Double> intensityList){
    Double mean = null;    
	int numberOfSamples = intensityList.size();
	if(numberOfSamples > 0){
		double values = 0.0;
		for (Double intensityValue: intensityList) {
			values += intensityValue;
		}
		mean = new Double (values / numberOfSamples);		
	}
	return MathUtil.getAntiLog2(mean);
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
