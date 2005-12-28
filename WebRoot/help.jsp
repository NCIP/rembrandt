<%@ page language="java" %>
<%!
public String refineQueryHelp()	{
	String help = "<div><a name=\"refineQuery\"></a>\n"+
	"<b>Refine Query Page:</b> <Br>\n"+
	"<li>This page helps you group your queries and select the report type in a systematic way using 5 steps. \n"+
	"<li>Step 1 helps you select the queries that you have built from the drop down list box.\n"+
	"<li>Selecting “and/or” operator at the end of a query row will enable the next query row where you can select another query of interest.\n"+
	"<li>Step 2 lets you select a previously saved result set to apply these queries to. You will not see any result sets if you have not saved them from a previously viewed report. Clicking on the “refresh” button brings up previously saved result sets in the drop down list box.\n"+
	"<li>Step 3 helps validate your query collection. It validates the number of parenthesis you may have added to the query grouping.\n"+
	"<li>Step 4 allows you to select a report. The available reports are context-sensitive to the queries that you have selected in step 1.\n"+
	"<li>Step 5 allows you to run the report. \n"+
	"</div>";
	return help;
}
public String viewResultsHelp()	{
	String help = "<div><a name=\"viewResults\"></a>\n"+
	"<b>View Results Page:</b> <Br>\n"+
	"<li>This page shows a collection of reports previously viewed in a particular user session.\n"+
	"<li>This allows you to compare different reports (example: clinical and Gene expression reports for a set of patient samples) by opening them in different windows.\n"+
	"</div>";
	return help;
}

public String genePlotHelp()	{
	String help = "<div><a name=\"gplot\"></a>\n"+
	"<b>Gene Expression plot (quick search):</b> <Br>\n"+
	"<li>The Gene expression plot displays average expression intensities for the gene of interest based on Affymetrix GeneChip arrays (U133 Plus 2.0 arrays)\n"+
	"<li>Multiple probesets (for some genes) are designed to measure the expression of the gene of interest. Please visit <a href='http://www.affymetrix.com' target='_blank'>www.affymetrix.com</a> for more information on their probeset design strategy for human genes.\n"+
	"<li>Group average (samples were averaged based on tumor subtypes into 4 categories, Glioblastoma Multiforme, Oligodendroglioma, Astrocytoma and Mixed tumors) was calculated for each probeset and is plotted on the Y-axis for each tumor type. As an indication of probabilities of obtaining the differences in expression values between tumor (or a subtype of tumor) and non-tumor samples, a permutation-based p-value is displayed on \"mouse-over\". Please visit the information portal of this application, <a href='http://rembrandt.nci.nih.gov' target='_blank'>http://rembrandt.nci.nih.gov</a>, for detailed information on data processing.\n"+
	"</div>";
	return help;
}

public String kmPlotHelpGE()	{
	String help = "<div><a name=\"kmplotGE\"></a>\n"+
	"<B>Kaplan-Meier Survival plot (Gene Expression K-M plot):</b> <Br>\n"+
	"<li>For samples with certain expression characteristics (e.g. EGFR expression levels in tumor samples are greater than those in the non-tumor samples by 3 fold or higher), a Kaplan-Meier plot is provided to show the survival rate at each time point.\n"+
	"<li>Kaplan-Meier estimates are calculated based on the last follow-up time and the censor status (0=alive, 1=dead) from the samples of interest. The Kaplan-Meier estimates are then plotted against the survival time. The points that correspond to the events with censor status of 0 are indicated on the graph.\n"+
	"<li>Users can dynamically modify the fold change (up and down regulation) thresholds and redraw the plot\n"+
	"<li>A log-rank p-value is provided as indication of significance of the difference in survival between any two groups of samples segregated based on gene expression of the gene of interest. The log rank p-value is calculated using Mantel-Haenszel procedure. The p-values are recalculated every time a new threshold is selected.\n"+
	"</div>\n";
	return help;
}

public String kmPlotHelpCN()	{
	String help = "<div><a name=\"kmplotCN\"></a>\n"+
	"<B>Kaplan-Meier Survival plot (Copy Number K-M plot):</b> <Br>\n"+
	"<li>For samples with certain amplification/deletion characteristics (e.g. amplification of the cytoband that EGFR maps to, 7p11.2), a Kaplan-Meier plot is provided for each SNP probeset associated with the gene of interest to show the survival rate at each time point.\n"+
	"<li>Kaplan-Meier estimates are calculated based on the last follow-up time and the censor status (0=alive, 1=dead) from the samples of interest. The Kaplan-Meier estimates are then plotted against the survival time. The points that correspond to the events with censor status of 0 are indicated on the graph.\n"+
	"<li>Users can dynamically modify the fold change (up and down regulation) thresholds and redraw the plot.\n"+
	"<li>A log-rank p-value is provided as indication of significance of the difference in survival between any two groups of samples segregated based on amplification/deletion of the gene or SNP of interest. The log rank p-value is calculated using Mantel-Haenszel procedure. The p-values are recalculated every time a new threshold is selected.\n"+
	"</div>\n";
	return help;
}

public String clinicalHelp()	{
	String help = "<div><a name=\"clinical\"></a>\n"+
	"<B>Clinical report (sample report): </b><Br>\n"+
	"<li>Clinical information including patient demographics, therapy and outcome data (either in just a single domain such as Gene Expression or by including a combination of queries from multiple domains such as Gene expression, Chromosomal aberrations and clinical areas) is displayed in this report.\n"+
	"<li>When either a gene expression filter and/or a copy number filter are applied, hyperlinks are provided in this report to display the gene expression and/or copy number data for a particular sample.\n"+
	"</div>\n";
	return help;
}

public String clinicalPlotHelp() {
	String help = "<div><a name=\"clinicalPlot\"></a>\n"+
	"<B> Clinical plot: </b><br />\n" +
	"<p>You can plot the clinical data from a report on two kinds of graphs:</p>\n" +
	"<li><i>Survival (months) vs Age at diagnosis (years):</i> The data points are colored by disease type. You can select the samples of interest by clicking on the graph and drawing a rectangle around the samples that you would like to save for future use.</li>\n" +
	"<li><i>Karnowsky score (Neurological assessment) vs Age at diagnosis (years):</i> The data points are colored by disease type. You can select the samples of interest by clicking on the graph and drawing a rectangle around the samples that you would like to save for future use.</li>\n" +
	"</div>\n";
	return help;
}

public String classComparisonHelp(){
	String help = "<div><a name=\"CC\"></a>\n"+
	"<B> Class Comparison report: </b><br />\n" +
	"<p>This report shows the results from the class comparison performed using the parameters (statistical methods and constraints) set by the user. The report displays group average, where the numerator is the mean of log(base 2) expression signals (geometric mean) from the samples in the first group and the denominator is the mean of log(base 2) expression signals (geometric mean) from the samples in the second group.</p>\n" + 
	"<p>Absolute fold change for the reporter between the selected groups is also displayed along with p-value. If multiple-comparison adjustment is chosen, then adjusted p-value is displayed. Gene symbol annotations are displayed for each reporter. Extensive annotations can be obtained by clicking the <i>excel</i> download button on the upper right-hand corner of the report.</p>\n" + 
	"</div>\n";
	return help;
}

public String pcaHelp(){
	String help = "<div><a name=\"pcaPlot\"></a>\n"+
	"<B> Principal Component Analysis report: </b><br />\n" +
	"<p>This two-dimensional graph plots the various principal components from the analyses. You can click on the three tabs at the top of the graph to display either PC1 vs PC2, or PC1 vs PC3, or PC2 vs PC3. Each point on the graph represents a sample. The samples are colored by disease type. You can color by gender by clicking on the link on the upper left-hand corner of the graph. Patients with different survival ranges are indicated by different shapes on the graph. You can select the samples of interest by clicking on the graph and drawing a rectangle around the samples that you would like to save for future use.</p>\n" + 
	"</div>\n";
	return help;
}
public String hcHelp(){
	String help = "<div><a name=\"hcPlot\"></a>\n"+
	"<B> Hierarchical Clustering report: </b><br />\n" +
	"<p>The Hierarchical Clustering report displays a dendrogram from hierarchical clustering analysis.</p>\n" +
	"<p>Clicking on full size at the top left-hand corner of the graph displays the image at full resolution. Based on the cluster parameter selected by the user, either sample or reporter annotations are displayed beneath the dendrogram.</p>\n" +
	"</div>\n";
	return help;
}

public String sampleHelp()	{
	String help = "<div><a name=\"sample\"></a>\n"+
	"<b>Gene Expression data per sample view: </b><Br>\n"+
	"<li>This report displays gene expression ratios (between the tumor sample and the average of non-tumor samples) for each probeset (or IMAGE clone) for the genes selected in the queries.\n"+
	"<li>Each column represents a sample and the samples are grouped based on the tumor sub-type.\n"+
	"<li>For Affymetrix probesets, the ratio is calculated between the absolute expression values of the tumor sample and the average expression value of the Non-tumor samples.\n"+
	"<li>For each Image Clone, missing values are handled and ratio of expression values between the tumor and average of non-tumor group is calculated for each sample. Please visit <a href='http://rembrandt.nci.nih.gov' target='_blank'>http://rembrandt.nci.nih.gov</a> for more information on data processing.\n"+
	"</div>\n";
	return help;
}

public String diseaseGroupHelp()	{
	String help = "<div><a name=\"diseaseGroup\"></a>\n"+
	"<B>Gene Expression data per Disease Group view </b><Br>\n"+
	"<li>This report displays mean gene expression ratios (between the tumor group and the average of non-tumor samples) for each probeset (or IMAGE clone) for the genes selected in the queries.\n"+
	"<li>Each column represents a sample group (tumor sub-type) \n"+
	"<li>Group average (samples were averaged based on tumor subtypes into 4 categories, Glioblastoma Multiforme, Oligodendroglioma, Astrocytoma and Mixed tumors) was calculated for each probeset (or IMAGE clone). As an indication of probabilities of obtaining the differences in expression values between tumor (or a subtype of tumor) and non-tumor samples, a permutation-based p-value is displayed within the parenthesis for each ratio. Please visit the information portal of this application, <a href='http://rembrandt.nci.nih.gov' target='_blank'>http://rembrandt.nci.nih.gov</a>, for detailed information on data processing.\n"+
	"</div>\n";
	return help;
}

public String copyNumberHelp()	{
	String help = "<div><a name=\"copyNumber\"></a>\n"+
	"<b>Copy Number data per Sample view </b><Br>\n"+
	"<li>This report displays the copy number data from Affymetrix 100K SNP arrays\n"+
	"<li>The CHP files from the Affymetrix Gene Chip Operating System were processed using the GDAS (GeneChip® DNA Analysis Software) and copy number data was collected for each mapping SNP reporter on the Chip, for all the tumor samples.\n"+
	"<li>Each column represents a sample and the samples are grouped based on the tumor sub-type.\n"+
	"</div>\n";
	return help;
}


String topLink = "<p class=\"tl\"><a href=\"#top\">[top]</a> | <a href=\"javascript:window.print();\">[print]</a> | <a href=\"javascript:window.close();\">[close window]</a></p>";

String menu = "<a href=\"#gplot\">Gene Expression Plot</a><br>\n"+
				"<a href=\"#kmplotGE\">Kaplan-Meier Survival plot (Gene Expression)</a><br>\n"+
				"<a href=\"#kmplotCN\">Kaplan-Meier Survival plot (Copy Number Data)</a><br>\n"+
				"<a href=\"#viewResults\">View Results Page</a><br>\n"+
				"<a href=\"#refineQuery\">Refine Query Page</a><br>\n"+
				"<a href=\"#clinical\">Clinical report</a><br>\n"+
				"<a href=\"#clinicalPlot\">Clinical Plot</a><br>\n"+
				"<a href=\"#sample\">Gene Expression data per sample view</a><br>\n"+
				"<a href=\"#diseaseGroup\">Gene Expression data per Disease Group view</a><br>\n"+
				"<a href=\"#copyNumber\">Copy Number data per Sample view</a><br>\n"+
				"<a href=\"#hcPlot\">Hierarchical Clustering report</a><br>\n"+
				"<a href=\"#CC\">Class Comparison report</a><br>\n"+
				"<a href=\"#pcaPlot\">Principal Component Analysis report</a><br>\n";
%>

<html>
<head>
	<title>Application Help</title>
	<LINK href="css/bigStyle.css" rel="stylesheet" type="text/css">
	<style>
		div { border: 1px solid black; padding:5px;}
		li { padding: 3px; }
		a {text-decoration: none }
		.hdr { font-size: 16px; font-weight: bold; background-color: silver; width:100% }
	</style>
</head>

<body>
<a name="top"></a>
<div style="background-color: #ffffff; border:0px"><img src="images/smallHead.jpg"></div>
<span class="hdr">Application Help</span>
<p class="message" align="center"><a href="javascript:window.print();">[print]</a> | 
<a href="javascript:window.close();">[close window]</a>

</p>
<% 
	String sect = request.getParameter("sect");
	if(sect!=null)	{
		if(sect.equals("gplot"))
			out.println(genePlotHelp()); 
		if(sect.equals("kmplotGE"))
			out.println(kmPlotHelpGE());
		if(sect.equals("kmplotCN"))
			out.println(kmPlotHelpCN());
		if(sect.equals("viewResults"))
			out.println(viewResultsHelp()); 
		if(sect.equals("CC"))
			out.println(classComparisonHelp());
		if(sect.equals("clinicalPlot"))
			out.println(clinicalPlotHelp()); 
		if(sect.equals("hcPlot"))
			out.println(hcHelp()); 
		if(sect.equals("pcaPlot"))
			out.println(pcaHelp()); 	 	 	
		if(sect.equals("refineQuery"))
			out.println(refineQueryHelp()); 
		if(sect.equals("clinical") || sect.equals("Clinical"))
			out.println(clinicalHelp());
		if(sect.equals("sample") || sect.equals("Gene Expression Sample"))
			out.println(sampleHelp());
		if(sect.equals("disease") || sect.equals("Gene Expression Disease"))
			out.println(diseaseGroupHelp());
		if(sect.equals("copyNumber") || sect.equals("Copy Number"))
			out.println(copyNumberHelp());
	}
	else	{
		out.println(menu + "<Br><br>\n");
		out.println(genePlotHelp() + "<Br>\n" + topLink);
		out.println(kmPlotHelpGE() + "<Br>\n" + topLink);
		out.println(kmPlotHelpCN() + "<Br>\n" + topLink);
		out.println(clinicalPlotHelp() + "<Br>\n" + topLink); 
		out.println(classComparisonHelp() + "<Br>\n" + topLink); 
		out.println(hcHelp() + "<Br>\n" + topLink);  
		out.println(pcaHelp() + "<Br>\n" + topLink);  	 	
		out.println(viewResultsHelp() + "<Br>\n" + topLink);
		out.println(refineQueryHelp() + "<Br>\n" + topLink);
		out.println(clinicalHelp() + "<Br>\n" + topLink);
		out.println(sampleHelp() + "<Br>\n" + topLink);
		out.println(diseaseGroupHelp() + "<Br>\n" + topLink);
		out.println(copyNumberHelp() + "<Br>\n" + topLink);
	}

%>
<p>
For more assistance please view our <a href="tutorials.jsp">tutorials</a>, or 
<a href="mailto:ncicb@pop.nci.nih.gov?subject=Rembrandt-0.5">email</a> NCICB Application Support.
</p>
</body>
</html>
