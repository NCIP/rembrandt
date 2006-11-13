<%@ page import="java.util.*, gov.nih.nci.rembrandt.web.helper.PCAAppletHelper" %>
<%
		String taskId = request.getParameter("tid") != null ? (String) request.getParameter("tid") : "";
		String params = PCAAppletHelper.generateParams(session.getId(), taskId);
		String passed = request.getParameter("passed")!=null ? "yes" : "no";
		String v=  request.getParameter("v")!=null ? (String) request.getParameter("v") : "x";
%>
<html>
<head>
	<title>PCA Applet</title>
	<script type="text/javascript">
		var DBG = {
			'showMsg' : function(txt)	{
				document.getElementById("DBG").innerHTML += ("<br/>"+txt);
			},
			'clearMsgs' : function()	{
				document.getElementById("DBG").innerHTML = "";
			}
		}
	</script>
<% 
	if(passed.equals("no"))	{
%>
	<script type="text/javascript">

	var VERSION_REQUIRED = "1.5.0";
	
	function checkPlugIn() {
		// Check for Java Plugin Version
		var javaVersion;
		try {
			javaVersion =
			document.getElementById('detectPluginApplet').getJavaVersion();
		}
		catch (e) {
			javaVersion = null;
		}
		
		if(javaVersion == null) {
			DBG.showMsg("No Java Plugin detected");
		}
		else {
			DBG.showMsg("Java Plugin version " + javaVersion + " detected");
		}
		
		if ( javaVersion >= VERSION_REQUIRED ) {
			DBG.showMsg("Your plugin meets the minimum requirements. Applet starting...");
			gotoApplet("&passed=yes");
		}
		else if( javaVersion >= "1.4.0"	)	{
			//try and load the 1.4 jar
			DBG.showMsg("Using the 1.4 version.  Applet starting...");
			gotoApplet("&passed=yes&v=1.4");
		}
		else {
			DBG.showMsg("You must go to the java download page to get the correct Java Plugin.");
			document.getElementById("res").style.color = "red";
		}
		return true;
	}
	
	function gotoApplet(lnk)	{
		setTimeout(function()	{
			location.href=location.href+lnk;
			}, 750);
	}
	</script>
<% } %>
	</head>
<body <%if(passed.equals("no"))	{%> onload="checkPlugIn()" <%}%>>
<!-- 
<xmp>
<%=params%>
</xmp>
-->

<div id="res" style="font-family:arial; font-size:10px; color:gray;">
<b>Note: This applet requires the Java Plugin, version 1.4 or later (recommended 1.5 or later).  If you are unable to view the applet, please download/upgrade your plugin here: <a href="http://www.java.com/en/download/index.jsp" target="_blank">http://www.java.com/en/download/index.jsp</a></b><br/><br/>
<b>Don't know which version you have installed (if any)? Need help installing the plugin?  Visit this site: <a href="http://www.java.com/en/download/help/testvm.xml" target="_blank">http://www.java.com/en/download/help/testvm.xml</a> for details.</b><br/><br/><br/>
</div>

<div id="DBG" style="font-family:arial; font-size:11px; color:gray;"></div>

<%
if(passed.equals("no"))	{
//you have the correct plugin, and are on your second trip here
%>
<applet name="detectPluginApplet" id="detectPluginApplet" code="Detector.class" width="1" height="1" codebase="Applets/">
</applet>

<%}
else {
	String usejar = v.equals("1.4") ? "jmathplot-14" : "jmathplot";
%>
	<applet code="PCATestApplet.class" width="400" height="550"
	name="pcaApplet" id="pcaApplet" codebase="Applets/" archive="<%=usejar%>.jar,commons-lang-2.0.jar">
	 <param name="image" value="images/applet400x550.jpg">
	<%=params%>	
	<em>Your browser does not support Java!  Please download the Java Plugin for your browser, available from <a href="http://www.java.com/en/download/index.jsp" target="_blank">Sun</a></em>
	</applet> 
<% } %>

</body>
</html>
