/*
 *  @author: SahniH
 *  Created on Nov 9, 2004
 *  @version $ Revision: 1.0 $
 * 
 *	The caBIO Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer 
 *	Institute, and so to the extent government employees are co-authors, any rights in such works 
 *	shall be subject to Title 17 of the United States Code, section 105.
 * 
 *	Redistribution and use in source and binary forms, with or without modification, are permitted 
 *	provided that the following conditions are met:
 *	 
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions 
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above 
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or 
 *	other materials provided with the distribution.
 * 
 *	2.  The end-user documentation included with the redistribution, if any, must include the 
 *	following acknowledgment:
 *	
 *	"This product includes software developed by the SAIC and the National Cancer 
 *	Institute."
 *	
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the 
 *	software itself, wherever such third-party acknowledgments normally appear.
 *	 
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or 
 *	promote products derived from this software.
 *	 
 *	4. This license does not authorize the incorporation of this software into any proprietary 
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either 
 *	NCI or SAIC-Frederick.
 *	 
 *	
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED 
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE 
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR 
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *	
 */
package gov.nih.nci.nautilus.resultset.geneExpressionPlot;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import gov.nih.nci.nautilus.de.DiseaseNameDE;
import gov.nih.nci.nautilus.resultset.gene.ReporterResultset;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public class DiseaseGeneExprPlotResultset {
	private DiseaseNameDE type = null;
	private SortedMap reporters = new TreeMap();
	/**
	 * @param disease
	 */
	public DiseaseGeneExprPlotResultset(DiseaseNameDE disease) {
		
		setType(disease);
	}
	/**
	 * @return Returns the type.
	 */
	public DiseaseNameDE getType() {
		return this.type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(DiseaseNameDE type) {
		this.type = type;
	}
	/**
	 * @param reporterResultset Adds reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
	public void addReporterFoldChangeValuesResultset(ReporterFoldChangeValuesResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.put(reporterResultset.getReporter().getValue().toString(), reporterResultset);
		}
	}
	/**
	 * @param reporterResultset Removes reporterResultset from this DiseaseGeneExprPlotResultset object.
	 */
	public void removeReporterFoldChangeValuesResultset(ReporterFoldChangeValuesResultset reporterResultset){
		if(reporterResultset != null && reporterResultset.getReporter() != null){
			reporters.remove(reporterResultset.getReporter().getValue().toString());
		}
	}
    /**
     * @param reporter
	 * @return reporterResultset Returns reporterResultset for this DiseaseGeneExprPlotResultset.
	 */
    public ReporterFoldChangeValuesResultset getReporterFoldChangeValuesResultset(String reporter){
    	if(reporter != null){
			return (ReporterFoldChangeValuesResultset) reporters.get(reporter);
		}
    		return null;
    }
	/**
	 * @return reporterResultset Returns reporterResultset to this DiseaseGeneExprPlotResultset object.
	 */
    public Collection getReporterFoldChangeValuesResultsets(){
    		return reporters.values();
    }
	/**
	 * @param none Removes all reporterResultset in this DiseaseGeneExprPlotResultset object.
	 */
    public void removeAllReporterFoldChangeValuesResultsets(){
    	reporters.clear();
    }
}
