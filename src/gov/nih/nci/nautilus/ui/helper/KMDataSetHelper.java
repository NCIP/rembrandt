/*
 * Created on Mar 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.ui.helper;

import gov.nih.nci.nautilus.constants.NautilusConstants;
import gov.nih.nci.nautilus.ui.graph.kaplanMeier.KMGraphGenerator;
import gov.nih.nci.nautilus.ui.struts.form.KMDataSetForm;


import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;


/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KMDataSetHelper {
    
    

	public static KMDataSetForm populateKMDataSetForm(KMGraphGenerator _generator,String _plotType, KMDataSetForm _kmForm) {
		if(_plotType != null &&_generator != null &&_kmForm != null){
			
		    NumberFormat numberFormatter;
		    numberFormatter = NumberFormat.getNumberInstance();
            numberFormatter.setMaximumFractionDigits(5);
		    
			_kmForm.setPlotType(_plotType);
			//_kmForm.setGeneOrCytoband(getKmResultsContainer().getGeneSymbol().getValue().toString() );
    		_kmForm.setCensorDataset(_generator.getCensorDataseries());
    		_kmForm.setLineDataset(_generator.getLineDataseries());
    		_kmForm.setAllSampleCount(_generator.getAllSampleCount().toString());
    		_kmForm.setUpSampleCount(_generator.getUpSampleCount().toString());
    		_kmForm.setDownSampleCount(_generator.getDownSampleCount().toString());
    		_kmForm.setIntSampleCount(_generator.getIntSampleCount().toString());
    		_kmForm.setUpVsDownPvalue(numberFormatter.format(_generator.getUpVsDownPvalue()));
    		_kmForm.setUpVsIntPvalue(numberFormatter.format(_generator.getUpVsIntPvalue()));
    		_kmForm.setDownVsIntPvalue(numberFormatter.format(_generator.getDownVsIntPvalue()));
    		_kmForm.setUpVsRestPvalue(numberFormatter.format(_generator.getUpVsRest()));
    		_kmForm.setDownVsRestPvalue(numberFormatter.format(_generator.getDownVsRest()));
    		_kmForm.setIntVsRestPvalue(numberFormatter.format(_generator.getIntVsRest()));
    		
		}
		return _kmForm;
	}
	
    public static KMDataSetForm populateReporters(List _reporters, String _plotType, KMDataSetForm _kmForm){
		List reporters = new ArrayList();
		if( _reporters != null && _plotType!= null && _kmForm != null){
			reporters = _reporters;
			if (_plotType.equals(NautilusConstants.GENE_EXP_KMPLOT)){
				reporters.add(0,NautilusConstants.GRAPH_DEFAULT);
			}
            if (_plotType.equals(NautilusConstants.COPY_NUMBER_KMPLOT)){
                reporters.add(0," ");

            }
            _kmForm.setReporters(reporters);
		}
		return _kmForm;		
	}
}
