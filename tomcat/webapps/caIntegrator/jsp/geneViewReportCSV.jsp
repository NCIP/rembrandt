<%@ page language="java" %>
<%@ page import="
gov.nih.nci.nautilus.criteria.*,
gov.nih.nci.nautilus.de.*,
gov.nih.nci.nautilus.query.GeneExpressionQuery,
gov.nih.nci.nautilus.query.QueryManager,
gov.nih.nci.nautilus.query.QueryType,
gov.nih.nci.nautilus.view.ViewFactory,
gov.nih.nci.nautilus.view.ViewType,
gov.nih.nci.nautilus.queryprocessing.ge.GeneExpr,
gov.nih.nci.nautilus.resultset.ResultsetProcessor,
gov.nih.nci.nautilus.resultset.*,
java.text.DecimalFormat,
java.util.*,
%>
<%!

	 DecimalFormat resultFormat = new DecimalFormat("0.00");
     FoldChangeCriteria foldCrit;
     GeneIDCriteria  geneIDCrit;
     GeneOntologyCriteria ontologyCrit;
     PathwayCriteria pathwayCrit;
     RegionCriteria regionCrit;
     CloneOrProbeIDCriteria cloneCrit;
     CloneOrProbeIDCriteria probeCrit;
     ArrayPlatformCriteria allPlatformCrit;
     ArrayPlatformCriteria affyOligoPlatformCrit;
     ArrayPlatformCriteria cdnaPlatformCrit;
 
     ResultSet[] geneExprObjects;
     

%>
<%
	//generate the CSV
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition", "attachment; filename=report.csv");
	
	// get the ResultSet[] from the session
    geneExprObjects = ( ResultSet[] ) (session.getAttribute("geneViewResultSet"));
 
if(geneExprObjects != null && geneExprObjects.length > 0) {      
    
	System.out.println("GeneExprObjs: " + geneExprObjects.length);


    	gov.nih.nci.nautilus.resultset.ResultsetProcessor resultsetProc = new gov.nih.nci.nautilus.resultset.ResultsetProcessor();
    	resultsetProc.handleGeneView(geneExprObjects, GroupType.DISEASE_TYPE_GROUP);
    	GeneViewContainer geneViewContainer = resultsetProc.getGeneViewContainer();
    	Collection genes = geneViewContainer.getGeneResultsets();
    	Collection labels = geneViewContainer.getGroupsLabels();
    	Collection sampleIds = null;
    	StringBuffer header = new StringBuffer();
    	StringBuffer sampleNames = new StringBuffer();
        StringBuffer stringBuffer = new StringBuffer();
    	//get group size (as Disease or Agegroup )from label.size
        System.out.println("GroupSize= "+labels.size());
        for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        	String label = (String) labelIterator.next();
        	System.out.println(label);
        	sampleIds = geneViewContainer.getBiospecimenLabels(label); 
        	//For each group get the number of samples in it from sampleIds.size()
            System.out.println("SampleSize= "+sampleIds.size());
           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
           		System.out.println(sampleIdIterator.next()); 
           	}
           	 
    	}
        //set up the header for the table
    	header.append("Gene,Reporter,");
    	sampleNames.append("Name,Name,");
	   
    	for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        	String label = (String) labelIterator.next();
        	sampleIds = geneViewContainer.getBiospecimenLabels(label); 
        	for(int l=0; l<sampleIds.size(); l++)
        	{
				header.append(label.substring(0,3)+","); //remove this for table
        	}
        	//header.append("|"+label+","); //remove this for table       	
	           	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	            	sampleNames.append(sampleIdIterator.next()+","); 
	            	//header.append(",");
	           	}
           	//header.deleteCharAt(header.lastIndexOf(","));
    	}
    //	header.append("|"); 

    	//System.out.println("Gene Count: "+genes.size());
    	out.println(header.toString());
		out.println(sampleNames.toString());
    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
    		Collection reporters = geneResultset.getReporterResultsets();
        	//System.out.println("Repoter Count: "+reporters.size());
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
        		Collection groupTypes = reporterResultset.getGroupResultsets();
        		stringBuffer = new StringBuffer();
            	//System.out.println("Group Count: "+groupTypes.size());
        		String reporterName = reporterResultset.getReporter().getValue().toString();
        		if(reporterName.length()< 10){ //Remove this from table
        			reporterName= reporterName+"        ";
        			reporterName = reporterName.substring(0,10);
        		}
        		//get the gene name, and reported Name
        		
        		stringBuffer.append(geneResultset.getGeneSymbol().getValueObject().toString()+","+
    					reporterName+",");
    					

				// iterate through the groups (lables) and get the results
        		for (Iterator labelIterator = labels.iterator(); labelIterator.hasNext();) {
        			// GroupResultset groupResultset = (GroupResultset)groupIterator.next();
					String label = (String) labelIterator.next();
        			GroupResultset groupResultset = reporterResultset.getGroupResultset(label);
        			
	        			// String label = groupResultset.getType().getValue().toString();
	        			sampleIds = geneViewContainer.getBiospecimenLabels(label);
	        			if(groupResultset != null)
        				{
	                     	for (Iterator sampleIdIterator = sampleIds.iterator(); sampleIdIterator.hasNext();) {
	                       		String sampleId = (String) sampleIdIterator.next();
	                       		BioSpecimenResultset biospecimenResultset = groupResultset.getBioSpecimenResultset(sampleId);
	                       		if(biospecimenResultset != null){
	                       			Double ratio = (Double)biospecimenResultset.getFoldChangeRatioValue().getValue();
	                       			if(ratio != null)
		                       			stringBuffer.append(resultFormat.format(ratio)+",");  
		                       		else
		                       			stringBuffer.append(",");                                
	                       		}
	                       		else 
	                       		{
	                       			stringBuffer.append(",");
	                       		}
	                       	}
                       }
                       else	{
                       for(int s=0;s<sampleIds.size();s++) 
                        	stringBuffer.append("++,");                      
                       }

         		}
         		
        		// System.out.println(stringBuffer.toString());
        		out.println(stringBuffer.toString());
    		}
    	}
}
else
	out.println("No results");
%>