package gov.nih.nci.nautilus.ui.struts.form;

import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private String geneSymbol;
	private int upFold = 3;
	private int downFold = 3;
    private XYSeriesCollection censorDataset;
    private XYSeriesCollection lineDataset;
    
	
private String chartTitle = null;
    private ArrayList folds = new ArrayList();
       
    private static Logger logger = Logger.getLogger(KMDataSetForm.class);
  
    /**
	 * This method is called by the ceWolf chart tag to create the data for the
	 * plot.
	 */
	public Object produceDataset(Map params) throws DatasetProduceException {
		 HashMap hashMap = (HashMap)params;
          if(((Boolean)(hashMap.get("censusPlot"))).booleanValue()) {
          	    return censorDataset;
         }else{
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
	 * @return Returns the geneSymbol.
	 */
	public String getGeneSymbol() {
		return geneSymbol;
	}

	/**
	 * @param geneSymbol
	 *            The geneSymbol to set.
	 */
	public void setGeneSymbol(String geneSymbol) {
		this.geneSymbol = geneSymbol;
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
		if(folds.isEmpty()) {
            for(int i = 2;i<11;i++) {
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
        chartTitle = "Kaplan-Meier Survival Plot for Samples with Differential "+geneSymbol+" Gene Expression";
		return chartTitle;
    }
	/**
	 * @param censorDataset The censorDataset to set.
	 */
	public void setCensorDataset(XYSeriesCollection censorDataset) {
		this.censorDataset = censorDataset;
	}
	/**
	 * @param lineDataset The lineDataset to set.
	 */
	public void setLineDataset(XYSeriesCollection lineDataset) {
		this.lineDataset = lineDataset;
	}
}