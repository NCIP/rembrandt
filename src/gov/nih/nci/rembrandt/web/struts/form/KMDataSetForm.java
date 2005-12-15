package gov.nih.nci.rembrandt.web.struts.form;

import gov.nih.nci.caintegrator.ui.graphing.data.kaplanmeier.KaplanMeierStoredData;
import gov.nih.nci.caintegrator.util.CaIntegratorConstants;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class KMDataSetForm extends ActionForm implements Serializable {

	private GeneExpressionQuery geneQuery;
	
	private KaplanMeierStoredData storedData = null;

	private String method;

	private String geneOrCytoband;

	private String plotType;

	private String selectedReporter;

	private List reporters = new ArrayList();;

	private String chartTitle = "";// RembrandtConstants.GENE_EXP_KMPLOT;

	private String changeType = "";
	
	private double upFold = 2;
	
	private double downFold = 2;

	private String upOrAmplified = "Up Regulated";

	private String downOrDeleted = "Down Regulated";

	private boolean plotVisible = false;

	private ArrayList<Double> folds = new ArrayList<Double>();
    
    private ArrayList algorithms = new ArrayList();  

	private Integer numberOfPlots = null;

	private String selectedDataset;
    
    private String algorithm = "default";
    
	private static Logger logger = Logger.getLogger(KMDataSetForm.class);

	public KMDataSetForm() {
        algorithms.add("default");
        algorithms.add("unified");
	}

	public KMDataSetForm(String _plotType) {
		setPlotType(_plotType);
	}

	/*********************************************
	 * 
	 * 
	 */
	public boolean hasExpired(Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime()) > 5000;
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
	public double getUpFold() {
		return this.upFold;
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
	public ArrayList<Double> getFolds() {
		if (folds.isEmpty()) {
			for (int i = 2; i < 11; i++) {
				folds.add(new Double(i));
			}
		}
		return folds;
	}

	/**
	 * @return Returns the downFold.
	 */
	public double getDownFold() {
		return this.downFold;
	}

	/**
	 * @return Returns the chartTitle.
	 */
	public String getChartTitle() {
		if (getPlotType().equals(CaIntegratorConstants.GENE_EXP_KMPLOT)) {
			chartTitle = "Kaplan-Meier Survival Plot for Samples with Differential "
					+ geneOrCytoband + " Gene Expression";
		}
		if (getPlotType().equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
			chartTitle = "Kaplan-Meier Survival Plot for Samples with Copy Number Analysis for "
					+ geneOrCytoband;
		}
		return chartTitle;
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
		if (plotType != null
				&& (plotType.equals(CaIntegratorConstants.GENE_EXP_KMPLOT) || plotType
						.equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT))) {
			this.plotType = plotType;
		}
	}

	/**
	 * @return Returns the changeType.
	 */
	public String getChangeType() {
		if (getPlotType().equals(CaIntegratorConstants.GENE_EXP_KMPLOT)) {
			changeType = "Folds";
		}
		if (getPlotType().equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
			changeType = "Copies";
		}
		return changeType;
	}

	/**
	 * @return Returns the downOrDeleted.
	 */
	public String getDownOrDeleted() {
		if (getPlotType().equals(CaIntegratorConstants.GENE_EXP_KMPLOT)) {
			downOrDeleted = "Down-Regulated";
		}
		if (getPlotType().equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
			downOrDeleted = "Deleted";
		}
		return downOrDeleted;
	}

	/**
	 * @return Returns the upOrAmplified.
	 */
	public String getUpOrAmplified() {
		if (getPlotType().equals(CaIntegratorConstants.GENE_EXP_KMPLOT)) {
			upOrAmplified = "Up-Regulated";
		}
		if (getPlotType().equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)) {
			upOrAmplified = "Amplified";
		}
		return upOrAmplified;
	}

	/**
	 * @param downOrDeleted
	 *            The downOrDeleted to set.
	 */
	public void setDownOrDeleted(String downOrDeleted) {
		this.downOrDeleted = downOrDeleted;
	}

	public boolean isPlotVisible() {
		return plotVisible;
	}

	public void setPlotVisible(boolean plotVisible) {
		this.plotVisible = plotVisible;
	}

	/**
	 * @return Returns the numberOfPlots.
	 */
	public Integer getNumberOfPlots() {
		return numberOfPlots;
	}

	/**
	 * @param numberOfPlots
	 *            The numberOfPlots to set.
	 */
	public void setNumberOfPlots(Integer numberOfPlots) {
		this.numberOfPlots = numberOfPlots;
	}

	/**
	 * @return Returns the selectedDataset.
	 */
	public String getSelectedDataset() {
		return selectedDataset;
	}

	/**
	 * @param selectedDataset
	 *            The selectedDataset to set.
	 */
	public void setSelectedDataset(String selectedDataset) {
		this.selectedDataset = selectedDataset;
	}

	/**
	 * @return Returns the storedData.
	 */
	public KaplanMeierStoredData getStoredData() {
		return storedData;
	}

	/**
	 * @param storedData The storedData to set.
	 */
	public void setStoredData(KaplanMeierStoredData storedData) {
		this.storedData = storedData;
	}

	/**
	 * @param downFold The downFold to set.
	 */
	public void setDownFold(double downFold) {
		this.downFold = downFold;
	}

	/**
	 * @param upFold The upFold to set.
	 */
	public void setUpFold(double upFold) {
		this.upFold = upFold;
	}

	/**
	 * @param upOrAmplified The upOrAmplified to set.
	 */
	public void setUpOrAmplified(String upOrAmplified) {
		this.upOrAmplified = upOrAmplified;
	}

    /**
     * @return Returns the algorithm.
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm The algorithm to set.
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @return Returns the algorithms.
     */
    public ArrayList getAlgorithms() {          
        return algorithms;
    }

   
}