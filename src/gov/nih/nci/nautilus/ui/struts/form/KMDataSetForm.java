package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.jfree.data.XYSeriesCollection;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;

public class KMDataSetForm extends ActionForm implements DatasetProducer,
		Serializable {

	private GeneExpressionQuery geneQuery;

	private String method;

	private String geneOrCytoband;

	private String plotType;

	private int upFold = 2;

	private int downFold = 2;

	private XYSeriesCollection censorDataset;

	private XYSeriesCollection lineDataset;

	private String selectedReporter;

	private List reporters = new ArrayList();;

	private String chartTitle = "";//NautilusConstants.GENE_EXP_KMPLOT;

	private String changeType = "";

	private String upOrAmplified = "";

	private String downOrDeleted = "";
	
	private String upVsDownPvalue = null;
    private String upVsIntPvalue = null;
    private String downVsIntPvalue = null;
    private String upVsRestPvalue = null;
    private String downVsRestPvalue = null;
    private String intVsRestPvalue = null;
    private String upSampleCount = null;
    private String downSampleCount = null;
    private String intSampleCount = null;
    private String allSampleCount = null;

	private ArrayList folds = new ArrayList();

	private static Logger logger = Logger.getLogger(KMDataSetForm.class);

	public KMDataSetForm() {

	}

	public KMDataSetForm(String _plotType) {
		setPlotType(_plotType);
	}

	/**
	 * This method is called by the ceWolf chart tag to create the data for the
	 * plot.
	 */

	public Object produceDataset(Map params) throws DatasetProduceException {
		HashMap hashMap = (HashMap) params;
		if (((Boolean) (hashMap.get("censusPlot"))).booleanValue()) {
			return censorDataset;
		} else {
			return lineDataset;
		}
	}

	/***************************************************************************
	 *  
	 */
	public boolean hasExpired(Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime()) > 5000;
	}

	public String getProducerId() {
		return "KMDataSetForm";
	}

	/**
	 * @return Returns the geneOrCytoband.
	 */
	public String getGeneOrCytoband() {
		return geneOrCytoband;
	}

	/**
	 * @param geneOrCytoband
	 *            The geneOrCytoband to set.
	 */
	public void setGeneOrCytoband(String geneSymbol) {
		this.geneOrCytoband = geneSymbol;
	}

	/**
	 * @return Returns the geneFolds.
	 */
	public int getUpFold() {
		return upFold;
	}

	/**
	 * @param geneFolds
	 *            The geneFolds to set.
	 */
	public void setUpFold(int upFolds) {
		this.upFold = upFolds;
	}

	/**
	 * @return Returns the method.
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            The method to set.
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return Returns the folds.
	 */
	public ArrayList getFolds() {
		if (folds.isEmpty()) {
			for (int i = 2; i < 11; i++) {
				folds.add(new Integer(i));
			}
		}
		return folds;
	}

	/**
	 * @return Returns the downFold.
	 */
	public int getDownFold() {
		return downFold;
	}

	/**
	 * @param downFold
	 *            The downFold to set.
	 */
	public void setDownFold(int downFold) {
		this.downFold = downFold;
	}

	/**
	 * @return Returns the chartTitle.
	 */
	public String getChartTitle() {
		if (getPlotType().equals(NautilusConstants.GENE_EXP_KMPLOT)) {
			chartTitle = "Kaplan-Meier Survival Plot for Samples with Differential "
					+ geneOrCytoband + " Gene Expression";
		}
		if (getPlotType().equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
			chartTitle = "Kaplan-Meier Survival Plot for Samples with Copy Number Analysis for "
					+ geneOrCytoband;
		}
		return chartTitle;
	}

	/**
	 * @param censorDataset
	 *            The censorDataset to set.
	 */
	public void setCensorDataset(XYSeriesCollection censorDataset) {
		this.censorDataset = censorDataset;
	}

	/**
	 * @param lineDataset
	 *            The lineDataset to set.
	 */
	public void setLineDataset(XYSeriesCollection lineDataset) {
		this.lineDataset = lineDataset;
	}

	/**
	 * @return Returns the reporters.
	 */
	public List getReporters() {
		return reporters;
	}

	/**
	 * @param reporters
	 *            The reporters to set.
	 */
	public void setReporters(List reporters) {
		this.reporters = reporters;
	}

	/**
	 * @return Returns the selectedReporter.
	 */
	public String getSelectedReporter() {
		return selectedReporter;
	}

	/**
	 * @param selectedReporter
	 *            The selectedReporter to set.
	 */
	public void setSelectedReporter(String selectedReporter) {
		this.selectedReporter = selectedReporter;
	}

	/**
	 * @return Returns the plotType.
	 */
	public String getPlotType() {
		return plotType;
	}

	/**
	 * @param plotType
	 *            The plotType to set.
	 */
	public void setPlotType(String plotType) {
		if(plotType != null &&
				( plotType.equals(NautilusConstants.GENE_EXP_KMPLOT) ||
				plotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT))){
		this.plotType = plotType;
		}
	}

	/**
	 * @return Returns the changeType.
	 */
	public String getChangeType() {
		if (getPlotType().equals(NautilusConstants.GENE_EXP_KMPLOT)) {
			changeType = "Folds";
		}
		if (getPlotType().equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
			changeType = "Copies";
		}
		return changeType;
	}

	/**
	 * @return Returns the downOrDeleted.
	 */
	public String getDownOrDeleted() {
		if (getPlotType().equals(NautilusConstants.GENE_EXP_KMPLOT)) {
			downOrDeleted = "Down-Regulated";
		}
		if (getPlotType().equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
			downOrDeleted = "Deleted";
		}
		return downOrDeleted;
	}

	/**
	 * @return Returns the upOrAmplified.
	 */
	public String getUpOrAmplified() {
		if (getPlotType().equals(NautilusConstants.GENE_EXP_KMPLOT)) {
			upOrAmplified = "Up-Regulated";
		}
		if (getPlotType().equals(NautilusConstants.COPY_NUMBER_KMPLOT)) {
			upOrAmplified = "Amplified";
		}
		return upOrAmplified;
	}


    
	/**
	 * @return Returns the allSampleCount.
	 */
	public String getAllSampleCount() {
		return allSampleCount;
	}
	/**
	 * @param allSampleCount The allSampleCount to set.
	 */
	public void setAllSampleCount(String allSampleCount) {
		this.allSampleCount = allSampleCount;
	}
	/**
	 * @return Returns the downSampleCount.
	 */
	public String getDownSampleCount() {
		return downSampleCount;
	}
	/**
	 * @param downSampleCount The downSampleCount to set.
	 */
	public void setDownSampleCount(String downSampleCount) {
		this.downSampleCount = downSampleCount;
	}
	/**
	 * @return Returns the downVsIntPvalue.
	 */
	public String getDownVsIntPvalue() {
		return downVsIntPvalue;
	}
	/**
	 * @param downVsIntPvalue The downVsIntPvalue to set.
	 */
	public void setDownVsIntPvalue(String downVsIntPvalue) {
		this.downVsIntPvalue = downVsIntPvalue;
	}
	/**
	 * @return Returns the downVsRestPvalue.
	 */
	public String getDownVsRestPvalue() {
		return downVsRestPvalue;
	}
	/**
	 * @param downVsRestPvalue The downVsRestPvalue to set.
	 */
	public void setDownVsRestPvalue(String downVsRestPvalue) {
		this.downVsRestPvalue = downVsRestPvalue;
	}
	/**
	 * @return Returns the intSampleCount.
	 */
	public String getIntSampleCount() {
		return intSampleCount;
	}
	/**
	 * @param intSampleCount The intSampleCount to set.
	 */
	public void setIntSampleCount(String intSampleCount) {
		this.intSampleCount = intSampleCount;
	}
	/**
	 * @return Returns the intVsRestPvalue.
	 */
	public String getIntVsRestPvalue() {
		return intVsRestPvalue;
	}
	/**
	 * @param intVsRestPvalue The intVsRestPvalue to set.
	 */
	public void setIntVsRestPvalue(String intVsRestPvalue) {
		this.intVsRestPvalue = intVsRestPvalue;
	}
	/**
	 * @return Returns the upSampleCount.
	 */
	public String getUpSampleCount() {
		return upSampleCount;
	}
	/**
	 * @param upSampleCount The upSampleCount to set.
	 */
	public void setUpSampleCount(String upSampleCount) {
		this.upSampleCount = upSampleCount;
	}
	/**
	 * @return Returns the upVsDownPvalue.
	 */
	public String getUpVsDownPvalue() {
		return upVsDownPvalue;
	}
	/**
	 * @param upVsDownPvalue The upVsDownPvalue to set.
	 */
	public void setUpVsDownPvalue(String upVsDownPvalue) {
		this.upVsDownPvalue = upVsDownPvalue;
	}
	/**
	 * @return Returns the upVsIntPvalue.
	 */
	public String getUpVsIntPvalue() {
		return upVsIntPvalue;
	}
	/**
	 * @param upVsIntPvalue The upVsIntPvalue to set.
	 */
	public void setUpVsIntPvalue(String upVsIntPvalue) {
		this.upVsIntPvalue = upVsIntPvalue;
	}
	/**
	 * @return Returns the upVsRestPvalue.
	 */
	public String getUpVsRestPvalue() {
		return upVsRestPvalue;
	}
	/**
	 * @param upVsRestPvalue The upVsRestPvalue to set.
	 */
	public void setUpVsRestPvalue(String upVsRestPvalue) {
		this.upVsRestPvalue = upVsRestPvalue;
	}
	/**
	 * @param downOrDeleted The downOrDeleted to set.
	 */
	public void setDownOrDeleted(String downOrDeleted) {
		this.downOrDeleted = downOrDeleted;
	}
}