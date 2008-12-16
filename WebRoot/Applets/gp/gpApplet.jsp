<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%

	String supportFileURL = (String)request.getAttribute("supportFileURL");
	String gp_lsid = (String)request.getAttribute("gp_lsid");
	String gp_download = (String)request.getAttribute("gp_download");
	String commandLine = (String)request.getAttribute("commandLine");
	String gp_fileDates = (String)request.getAttribute("gp_fileDates");
	String gp_filenames = (String)request.getAttribute("gp_filenames");
	String ticketString = (String)request.getAttribute("ticketString");
	String gp_paramNames = (String)request.getAttribute("gp_paramNames");
	String cdtFile = (String)request.getAttribute("cdtFile");
	String gtrFile = (String)request.getAttribute("gtrFile");
	String atrFile = (String)request.getAttribute("atrFile");
	
	String comparativeMarkerSelectionFilename = (String)request.getAttribute("comparativeMarkerSelectionFilename");
	String comparativeMarkerSelectionDatasetFilename = (String)request.getAttribute("comparativeMarkerSelectionDatasetFilename");
	
	String predictionResultsfilename = (String)request.getAttribute("predictionResultsfilename");
	
	String name = (String)request.getAttribute("name");
	//String ts = ticketString.replace("?", "&amp;");
	
	String appletName = "a" + ("" + Math.random()).substring(2);
 %>

<logic:present name="goApplet" >



<script type="text/javascript">
document.writeln('<applet code="gov.nih.nci.caintegrator.application.gpvisualizer.CaIntegratorRunVisualizerApplet" archive="SignedGPApplet.jar,GenePattern.jar,commons-httpclient-3.1.jar,commons-logging-1.0.4.jar,commons-codec-1.3.jar" codebase="Applets/gp" width="100" height="100" alt="Your browser refuses to run applets" name="<%= appletName %>" >');
document.writeln('<param name="moduleName" value="<%= name %>" >');
document.writeln('<param name="gp_os" value="any">');
document.writeln('<param name="gp_cpuType" value="any">');
document.writeln('<param name="libdir" value="any">');
document.writeln('<param name="visualizer_java_flags" value="-Xmx512M">');
document.writeln('<param name="gp_paramNames" value="<%= gp_paramNames %>" >');
document.writeln('<param name="supportFileURL" value="<%= supportFileURL %>" >');
<% if (name.equalsIgnoreCase("HeatMapViewer")) { %>
	document.writeln('<param name="dataset" value="<%= gp_download %>" >');
	document.writeln('<param name="gp_download" value="dataset" >');
<% } else if (name.equalsIgnoreCase("HierarchicalClusteringViewer")){ %>
	document.writeln('<param name="cdt.File" value="<%= cdtFile %>" >');
	document.writeln('<param name="gtr.File" value="<%= gtrFile %>" >');
	document.writeln('<param name="atr.File" value="<%= atrFile %>" >');
	document.writeln('<param name="gp_download" value="<%= gp_download %>" >');
<% } else if (name.equalsIgnoreCase("PredictionResultsViewer")){ %>
	document.writeln('<param name="prediction.results.filename" value="<%= predictionResultsfilename %>" >');
	document.writeln('<param name="gp_download" value="<%= gp_download %>" >');
<% } else if (name.equalsIgnoreCase("ComparativeMarkerSelectionViewer")){ %>
	document.writeln('<param name="comparative.marker.selection.filename" value="<%= comparativeMarkerSelectionFilename %>" >');
	document.writeln('<param name="dataset.filename" value="<%= comparativeMarkerSelectionDatasetFilename %>" >');
	document.writeln('<param name="gp_download" value="<%= gp_download %>" >');
<% } %>

document.writeln('<param name="commandLine" value="<%= commandLine %>" >');
document.writeln('<param name="ticketString" value="<%= ticketString %>" >');
document.writeln('<param name="DEBUG" value="1" >');
document.writeln('<param name="gp_filenames" value="<%= gp_filenames %>" >');
document.writeln('<param name="gp_fileDates" value="<%= gp_fileDates %>" >');
document.writeln('<param name="gp_lsid" value="<%= gp_lsid %>" >');

document.writeln("<PARAM name=\"browserCookie\" value=\"" + document.cookie + "\">");
document.writeln('</applet>');	
</script>

</logic:present>



