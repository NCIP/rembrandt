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

/**
 * @author Himanso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KMDataSetHelper {

	public static KMDataSetForm populateKMDataSetForm(KMGraphGenerator _generator,String _plotType, KMDataSetForm _kmForm) {
		if(_plotType != null &&_generator != null &&_kmForm != null){
			
			_kmForm.setPlotType(_plotType);
			//_kmForm.setGeneOrCytoband(getKmResultsContainer().getGeneSymbol().getValue().toString() );
    		_kmForm.setCensorDataset(_generator.getCensorDataseries());
    		_kmForm.setLineDataset(_generator.getLineDataseries());
    		_kmForm.setAllSampleCount(_generator.getAllSampleCount().toString());
    		_kmForm.setUpSampleCount(_generator.getUpSampleCount().toString());
    		_kmForm.setDownSampleCount(_generator.getDownSampleCount().toString());
    		_kmForm.setIntSampleCount(_generator.getIntSampleCount().toString());
    		_kmForm.setUpVsDownPvalue(_generator.getUpVsDownPvalue().toString());
    		_kmForm.setUpVsIntPvalue(_generator.getUpVsIntPvalue().toString());
    		_kmForm.setDownVsIntPvalue(_generator.getDownVsIntPvalue().toString());
    		_kmForm.setUpVsRestPvalue(_generator.getUpVsRest().toString());
    		_kmForm.setDownVsRestPvalue(_generator.getDownVsRest().toString());
    		_kmForm.setIntVsRestPvalue(_generator.getIntVsRest().toString());
    		
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
