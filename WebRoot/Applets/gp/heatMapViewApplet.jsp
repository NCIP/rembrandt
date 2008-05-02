
<%
	//SignedHMVApplet.jar,
	//String gp_filenames = System.getProperty("gov.nih.nci.caintegrator.gpvisualizer.heatmapviewer.gp_filenames");
	String gp_lsid = System.getProperty("gov.nih.nci.caintegrator.gpvisualizer.heatmapviewer.gp_lsid");
	
	String genePatternServer = (String)request.getAttribute("genePatternServer");
	String gp_download = (String)request.getAttribute("gp_download");
	String commandLine = (String)request.getAttribute("commandLine");
	String gp_fileDates = (String)request.getAttribute("gp_fileDates");
	String gp_filenames = (String)request.getAttribute("gp_filenames");
 %>




<applet code="gov.nih.nci.caintegrator.application.gpvisualizer.heatmapviewer.CaIntegratorRunVisualizerApplet" 
	archive="SignedHMVApplet.jar,commons-httpclient-3.1.jar,log4j.jar" 
	codebase="Applets/gp" width="1" height="1" alt="Your browser refuses to run applets" name="a1907740583592007" >
<param name="name" value="HeatMapViewer" >
<param name="gp_os" value="any">
<param name="gp_cpuType" value="any">
<param name="libdir" value="any">
<param name="visualizer_java_flags" value="-Xmx512M">
<param name="gp_paramNames" value="dataset" >
<param name="genePatternServer" value="<%= genePatternServer %>" >
<param name="dataset" value="<%= gp_download %>" >
<param name="gp_download" value="dataset" >
<param name="commandLine" value="<%= commandLine %>" >
<param name="DEBUG" value="1" >
<param name="gp_filenames" value="<%= gp_filenames %>" >
<param name="gp_fileDates" value="<%= gp_fileDates %>" >
<param name="gp_lsid" value="<%= gp_lsid %>" >
</applet>	
<br/>

	



