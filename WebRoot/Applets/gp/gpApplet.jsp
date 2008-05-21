<html>
<%
	//SignedHMVApplet.jar,
	//String gp_filenames = System.getProperty("gov.nih.nci.caintegrator.gpvisualizer.heatmapviewer.gp_filenames");
	String gp_lsid = System.getProperty("gov.nih.nci.caintegrator.gpvisualizer.heatmapviewer.gp_lsid");
	
	String supportFileURL = (String)request.getAttribute("supportFileURL");
	String gp_download = (String)request.getAttribute("gp_download");
	String commandLine = (String)request.getAttribute("commandLine");
	String gp_fileDates = (String)request.getAttribute("gp_fileDates");
	String gp_filenames = (String)request.getAttribute("gp_filenames");
	String ticketString = (String)request.getAttribute("ticketString");
	String name = (String)request.getAttribute("name");
	name = name.replace(" ", "");
 %>

<b>Please be patient, the viewer is loading...</b><img src="images/indicator.gif"/><br/><br/>

<script type="text/javascript">
document.writeln('<applet code="gov.nih.nci.caintegrator.application.gpvisualizer.CaIntegratorRunVisualizerApplet" archive="SignedGPApplet.jar,GenePattern.jar,commons-httpclient-3.1.jar,commons-logging-1.0.4.jar,commons-codec-1.3.jar" codebase="Applets/gp" width="100" height="100" alt="Your browser refuses to run applets" name="a1907740583592007" >');
document.writeln('<param name="name" value="<%= name %>" >');
document.writeln('<param name="gp_os" value="any">');
document.writeln('<param name="gp_cpuType" value="any">');
document.writeln('<param name="libdir" value="any">');
document.writeln('<param name="visualizer_java_flags" value="-Xmx512M">');
document.writeln('<param name="gp_paramNames" value="dataset" >');
document.writeln('<param name="supportFileURL" value="<%= supportFileURL %>" >');
document.writeln('<param name="dataset" value="<%= gp_download %>" >');
document.writeln('<param name="gp_download" value="dataset" >');
document.writeln('<param name="commandLine" value="<%= commandLine %>" >');
document.writeln('<param name="ticketString" value="<%= ticketString %>" >');
document.writeln('<param name="DEBUG" value="1" >');
document.writeln('<param name="gp_filenames" value="<%= gp_filenames %>" >');
document.writeln('<param name="gp_fileDates" value="<%= gp_fileDates %>" >');
document.writeln('<param name="gp_lsid" value="<%= gp_lsid %>" >');

document.writeln("<PARAM name=\"browserCookie\" value=\"" + document.cookie + "\">");
document.writeln('</applet>');	
</script>
<br/>
</html>
	



