package gov.nih.nci.nautilus.struts.action;

import gov.nih.nci.nautilus.criteria.ArrayPlatformCriteria;
import gov.nih.nci.nautilus.criteria.Constants;
import gov.nih.nci.nautilus.criteria.GeneIDCriteria;
import gov.nih.nci.nautilus.de.ArrayPlatformDE;
import gov.nih.nci.nautilus.de.GeneIdentifierDE;
import gov.nih.nci.nautilus.query.GeneExpressionQuery;
import gov.nih.nci.nautilus.query.QueryManager;
import gov.nih.nci.nautilus.query.QueryType;
import gov.nih.nci.nautilus.resultset.Resultant;
import gov.nih.nci.nautilus.resultset.ResultsContainer;
import gov.nih.nci.nautilus.resultset.ResultsetManager;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.DiseaseGeneExprPlotResultset;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.GeneExprDiseasePlotContainer;
import gov.nih.nci.nautilus.resultset.geneExpressionPlot.ReporterFoldChangeValuesResultset;
import gov.nih.nci.nautilus.struts.form.QuickSearchForm;
import gov.nih.nci.nautilus.view.ViewFactory;
import gov.nih.nci.nautilus.view.ViewType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class QuickSearchAction extends DispatchAction {

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward Histogram(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
			ArrayList groups = new ArrayList();
			ArrayList probeSets = new ArrayList();
			ArrayList intensityValues = new ArrayList();
			ArrayList pValues = new ArrayList();
			HashMap plotData = new HashMap();
			
			
			
		   QuickSearchForm qsForm = (QuickSearchForm) form;	
		   String gene = qsForm.getQuickSearchName();
		   
		   GeneExpressionQuery geneQuery;
		   GeneIDCriteria geneCrit = new GeneIDCriteria();
	       geneCrit.setGeneIdentifier(new GeneIdentifierDE.GeneSymbol(gene));
	       geneQuery = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
	       geneQuery.setQueryName("GeneExpressionPlot");
	       geneQuery.setAssociatedView(ViewFactory.newView(ViewType.GENE_GROUP_SAMPLE_VIEW));
	       geneQuery.setGeneIDCrit(geneCrit);
	       geneQuery.setArrayPlatformCrit(new ArrayPlatformCriteria(new ArrayPlatformDE(Constants.AFFY_OLIGO_PLATFORM)));

	       Resultant resultant = ResultsetManager.executeGeneExpressPlotQuery(geneQuery);
	       if(resultant != null)	{
			ResultsContainer resultsContainer = resultant.getResultsContainer();
			//System.out.println("Associated ViewType/n"+resultant.getAssociatedView());
			if (resultsContainer instanceof GeneExprDiseasePlotContainer)	{
				GeneExprDiseasePlotContainer geneExprDiseasePlotContainer = (GeneExprDiseasePlotContainer) resultsContainer;

				DecimalFormat resultFormat = new DecimalFormat("0.00");	
				System.out.println("Gene:"+geneExprDiseasePlotContainer.getGeneSymbol());
		    	Collection diseases = geneExprDiseasePlotContainer.getDiseaseGeneExprPlotResultsets();
		    	StringBuffer header = new StringBuffer();
		        StringBuffer stringBuffer = new StringBuffer();
		        String label = null;
		    	
		    	header.append("Diseases\tReporter Name\tIntensity\tPvalue");
				System.out.println(header.toString());
		    	for (Iterator diseasesIterator = diseases.iterator(); diseasesIterator.hasNext();) {
		    		DiseaseGeneExprPlotResultset diseaseResultset = (DiseaseGeneExprPlotResultset)diseasesIterator.next();
		    		String diseaseName = diseaseResultset.getType().getValue().toString();
		    		stringBuffer.append(diseaseName+"\n");
		    		groups.add(diseaseName); // add the string to the array list
		    		
		    		Collection reporters = diseaseResultset.getReporterFoldChangeValuesResultsets(); //geneResultset.getReporterResultsets();
		    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
		    			ReporterFoldChangeValuesResultset reporterResultset = (ReporterFoldChangeValuesResultset)reporterIterator.next();
		        		String reporterName = reporterResultset.getReporter().getValue().toString();
		       			Double intensityValue = (Double)reporterResultset.getFoldChangeIntensity().getValue();
		       			Double pvalue = (Double)reporterResultset.getRatioPval().getValue();
		       			stringBuffer.append(reporterName+"\t"+resultFormat.format(intensityValue)+"\t"+resultFormat.format(pvalue)+"\n");
		       			//fill up our lists
		       			probeSets.add(reporterName);
		       			intensityValues.add(intensityValue);
		       			pValues.add(pvalue);
		    		}
		    	}
		    	System.out.println(stringBuffer.toString());	
			}
		}
		 
	       //error checking and add everything to the collection if cool, else forward to error
	       if(probeSets.size() == pValues.size() && intensityValues.size() == pValues.size())	{
	       	//got what we need, put it in the map
	       	plotData.put("groups", groups);
	       	plotData.put("probeSets", probeSets);
	       	plotData.put("intensityValues", intensityValues);
	       	plotData.put("pValues", pValues);
	       	plotData.put("gene", gene);
	       	
	       	//actually we can build the image map here and put that in the session
	       	
	       	//hand to session
	       	request.getSession().setAttribute("plotData", plotData);
			return mapping.findForward("histogram");	
	       }
	       else	{
	       	//something went horribly wrong
	       	// TODO: add some global error here
	       	return mapping.findForward("mismatch");
	       }
	     }
	
	public ActionForward KMPlot(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		// Dave's Code here		 
		
		return mapping.findForward("kmplot");			
	
	}
	

}
