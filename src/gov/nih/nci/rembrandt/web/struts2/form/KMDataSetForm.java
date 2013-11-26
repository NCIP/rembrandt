/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.struts2.form;

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

//import org.apache.struts.action.ActionForm;
//import org.apache.struts.util.LabelValueBean;



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

public class KMDataSetForm implements Serializable {

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
    
    private ArrayList<Double> copyNumberDownFolds = new ArrayList<Double>();
    
    private ArrayList algorithms = new ArrayList();  

	private Integer numberOfPlots = null;

	private String selectedDataset;

	//JB: Added in support of GF Tracker #23351 - Ability to Hide/Display Groups on KM Plot
    private String[] selectedItems = {"All Group Samples", "Up-Regulated", "Down-Regulated", "Intermediate"}; 
    private String[] items = {"All Group Samples", "Up-Regulated", "Down-Regulated", "Intermediate"}; 
	//JB End: Added in support of GF Tracker #23351 - Ability to Hide/Display Groups on KM Plot
    
    private String reporterSelection = RembrandtConstants.REPORTER_SELECTION_AFFY;
    
	private static Logger logger = Logger.getLogger(KMDataSetForm.class);

	public KMDataSetForm() {
        algorithms.add(RembrandtConstants.REPORTER_SELECTION_AFFY);
        algorithms.add(RembrandtConstants.REPORTER_SELECTION_UNI);
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
     * @return Returns the copyNumberDownFolds.
     */
    public ArrayList<Double> getCopyNumberDownFolds() {
        if (copyNumberDownFolds.isEmpty()) {
            copyNumberDownFolds.add(0.1);
            copyNumberDownFolds.add(0.2);
            copyNumberDownFolds.add(0.3);
            copyNumberDownFolds.add(0.5);
            copyNumberDownFolds.add(1.0);
            copyNumberDownFolds.add(1.8);
            copyNumberDownFolds.add(2.0);                    
        }
        return copyNumberDownFolds;
    }
    
    /**
     * @return Returns the copyNumberUpFolds.
     */
    public ArrayList<Double> getCopyNumberUpFolds() {
		if (folds.isEmpty()) {
			folds.add(2.0);
			folds.add(2.2);
			for (int i = 3; i < 11; i++) {
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
		if (getPlotType().equals(CaIntegratorConstants.SAMPLE_KMPLOT)) {
			chartTitle = "Kaplan-Meier Survival Plot for selected Sample Groups";
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
						.equals(CaIntegratorConstants.COPY_NUMBER_KMPLOT)|| plotType
						.equals(CaIntegratorConstants.SAMPLE_KMPLOT))) {
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
		if (getPlotType().equals(CaIntegratorConstants.SAMPLE_KMPLOT)) {
			changeType = "Folds";
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
		if (getPlotType().equals(CaIntegratorConstants.SAMPLE_KMPLOT)) {
			downOrDeleted = "Down-Regulated";
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
		if (getPlotType().equals(CaIntegratorConstants.SAMPLE_KMPLOT)) {
			upOrAmplified = "Up-Regulated";
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
     * @return Returns the reporterSelection.
     */
    public String getReporterSelection() {
        return reporterSelection;
    }

    /**
     * @param reporterSelection The reporterSelection to set.
     */
    public void setReporterSelection(String algorithm) {
        this.reporterSelection = algorithm;
    }

    /**
     * @return Returns the algorithms.
     */
    public ArrayList getAlgorithms() {          
        return algorithms;
    }

    
	//JB: Added in support of GF Tracker #23351 - Ability to Hide/Display Groups on KM Plot
    /**
     * @return Returns the selected plot options.
     */
    public String[] getItems() { 
      return this.items; 
    } 
    
    /**
     * @return Returns the selected plot options.
     */
    public String[] getSelectedItems() { 
      return this.selectedItems; 
    } 
    
    /**
     * @param selectedItems The selected plot options.
     */
    public void setSelectedItems(String[] selectedItems) { 
      this.selectedItems = selectedItems; 
    }
	//JB End: Added in support of GF Tracker #23351 - Ability to Hide/Display Groups on KM Plot

}
