package gov.nih.nci.rembrandt.queryservice.resultset.geneExpressionPlot;

import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.rembrandt.queryservice.resultset.ResultsContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SahniH
 * Date: Nov 9, 2004
 * 
 */
public class GeneExprDiseasePlotContainer implements ResultsContainer{
	private GeneIdentifierDE.GeneSymbol geneSymbol;
	private Map diseases = new HashMap(); 
	
	public void addDiseaseGeneExprPlotResultset(DiseaseGeneExprPlotResultset diseaseGeneExprPlotResultset){
		if(diseaseGeneExprPlotResultset != null && diseaseGeneExprPlotResultset.getType() != null){
			diseases.put(diseaseGeneExprPlotResultset.getType().getValue().toString(), diseaseGeneExprPlotResultset);
		}
	}
	/**
	 * @param diseaseGeneExprPlotResultset Removes diseaseGeneExprPlotResultset to this ReporterResultset object.
	 */
	public void removeDiseaseGeneExprPlotResultset(DiseaseGeneExprPlotResultset diseaseGeneExprPlotResultset){
		if(diseaseGeneExprPlotResultset != null && diseaseGeneExprPlotResultset.getType() != null){
			diseases.remove(diseaseGeneExprPlotResultset.getType().getValue().toString());
		}
	}
    /**
     * @param disease
	 * @return diseaseGeneExprPlotResultset Returns reporterResultset for this ReporterResultset.
	 */
    public DiseaseGeneExprPlotResultset getDiseaseGeneExprPlotResultset(String diseaseType){
    	if(diseaseType != null){
			return (DiseaseGeneExprPlotResultset) diseases.get(diseaseType);
		}
    		return null;
    }
	/**
	 * @return Collection Returns collection of diseaseGeneExprPlotResultsets to this ReporterResultset object.
	 */
    public Collection getDiseaseGeneExprPlotResultsets(){
    		return diseases.values();
    }
	/**
	 * @param none Removes all diseaseGeneExprPlotResultset in this ReporterResultset object.
	 */
    public void removeAllDiseaseGeneExprPlotResultset(){
    	diseases.clear();
    }
	/**
	 * @return Returns the geneSymbol.
	 */
	public GeneIdentifierDE.GeneSymbol getGeneSymbol() {
		return this.geneSymbol;
	}
	/**
	 * @param geneSymbol The geneSymbol to set.
	 */
	public void setGeneSymbol(GeneIdentifierDE.GeneSymbol geneSymbol) {
		this.geneSymbol = geneSymbol;
	}
}
