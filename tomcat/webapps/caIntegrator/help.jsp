<%@ page language="java" %>
<%!

public String genePlotHelp()	{
	String help = "<div><a name=\"gplot\"></a>\n"+
	"<b>Gene Expression plot (quick search):</b> <Br>\n"+
	"<li>The Gene expression plot displays average expression intensities for the gene of interest based on Affymetrix GeneChip arrays (U133 Plus 2.0 arrays)\n"+
	"<li>Multiple probesets (for some genes) are designed to measure the expression of the gene of interest. Please visit www.affymetrix.com for more information on their probeset design strategy for human genes.\n"+
	"<li>Group average (samples were averaged based on tumor subtypes into 4 categories, Glioblastoma Multiforme, Oligodendroglioma, Astrocytoma and Mixed tumors) was calculated for each probeset and is plotted on the Y-axis for each tumor type. As an indication of probabilities of obtaining the differences in expression values between tumor (or a subtype of tumor) and non-tumor samples, a permutation-based p-value is displayed on \"mouse-over\". Please visit the information portal of this application, http://rembrandt.nci.nih.gov, for detailed information on data processing.\n"+
	"</div>";
	return help;
}

public String kmPlotHelp()	{
	String help = "<div><a name=\"kmplot\"></a>\n"+
	"<B>Kaplan-Meier Survival plot (quick search):</b> <Br>\n"+
	"<li>For samples with certain expression characteristics (e.g. EGFR expression levels in tumor samples are greater than those in the non-tumor samples by 3 fold or higher), a Kaplan-Meier plot is provided to show the survival rate at each time point.\n"+
	"<li>Kaplan-Meier estimates are calculated based on the last follow-up time and the censor status (0=alive, 1=dead) from the samples of interest. The Kaplan-Meier estimates are then plotted against the survival time. The points that correspond to the events with censor status of 0 are indicated on the graph.\n"+
	"<li>Users can dynamically modify the fold change (up and down regulation) thresholds and redraw the plot\n"+
	"</div>\n";
	return help;
}

public String clinicalHelp()	{
	String help = "<div><a name=\"clinical\"></a>\n"+
	"<B>Clinical report (sample report): </b><Br>\n"+
	"<li>For samples that matched the criteria specified by the user (either in just a single domain, such as Gene Expression, or, a combination of queries from multiple domains such as Gene expression, Chromosomal aberrations and clinical areas), clinical information including gender of the patient, age at diagnosis, tumor subtype is displayed in this report.\n"+
	"<li>When either a gene expression filter and/or a copy number filter are applied, hyperlinks are provided in this report to display the gene expression and/or copy number data for a particular sample.\n"+
	"</div>\n";
	return help;
}

public String sampleHelp()	{
	String help = "<div><a name=\"sample\"></a>\n"+
	"<b>Gene Expression data per sample view: </b><Br>\n"+
	"<li>This report displays gene expression ratios (between the tumor sample and the average of non-tumor samples) for each probeset (or IMAGE clone) for the genes selected in the queries.\n"+
	"<li>Each column represents a sample and the samples are grouped based on the tumor sub-type.\n"+
	"<li>For Affymetrix probesets, the ratio is calculated between the absolute expression values of the tumor sample and the average expression value of the Non-tumor samples.\n"+
	"<li>For each Image Clone, missing values are handled and ratio of expressin values between the tumor and average of non-tumor group is calculated for each sample. Please visit http://rembrandt.nci.nih.gov for more information on data processing.\n"+
	"</div>\n";
	return help;
}

public String diseaseGroupHelp()	{
	String help = "<div><a name=\"diseaseGroup\"></a>\n"+
	"<B>Gene Expression data per Disease Group view </b><Br>\n"+
	"<li>This report displays mean gene expression ratios (between the tumor group and the average of non-tumor samples) for each probeset (or IMAGE clone) for the genes selected in the queries.\n"+
	"<li>Each column represents a sample group (tumor sub-type) \n"+
	"<li>Group average (samples were averaged based on tumor subtypes into 4 categories, Glioblastoma Multiforme, Oligodendroglioma, Astrocytoma and Mixed tumors) was calculated for each probeset (or IMAGE clone). As an indication of probabilities of obtaining the differences in expression values between tumor (or a subtype of tumor) and non-tumor samples, a permutation-based p-value is displayed within the parenthesis for each ratio. Please visit the information portal of this application, http://rembrandt.nci.nih.gov, for detailed information on data processing.\n"+
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
				"<a href=\"#kmplot\">Kaplan-Meier Survival plot</a><br>\n"+
				"<a href=\"#clinical\">Clinical report</a><br>\n"+
				"<a href=\"#sample\">Gene Expression data per sample view</a><br>\n"+
				"<a href=\"#diseaseGroup\">Gene Expression data per Disease Group view</a><br>\n"+
				"<a href=\"#copyNumber\">Copy Number data per Sample view</a><br>\n";
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
		if(sect.equals("kmplot"))
			out.println(kmPlotHelp());
		if(sect.equals("clinical"))
			out.println(clinicalHelp());
		if(sect.equals("sample"))
			out.println(sampleHelp());
		if(sect.equals("disease"))
			out.println(diseaseGroupHelp());
		if(sect.equals("copyNumber"))
			out.println(copyNumberHelp());
	}
	else	{
		out.println(menu + "<Br><br>\n");
		out.println(genePlotHelp() + "<Br>\n" + topLink);
		out.println(kmPlotHelp() + "<Br>\n" + topLink);
		out.println(clinicalHelp() + "<Br>\n" + topLink);
		out.println(sampleHelp() + "<Br>\n" + topLink);
		out.println(diseaseGroupHelp() + "<Br>\n" + topLink);
		out.println(copyNumberHelp() + "<Br>\n" + topLink);
	}

%>
<p>
For more assistance please view our <a href="tutorials.jsp">tutorials</a>, or 
<a href="mailto:#">email</a> NCICB Application Support.
</p>
</body>
</html>
