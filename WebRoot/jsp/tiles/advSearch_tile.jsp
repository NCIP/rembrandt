<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.util.*, java.lang.*, java.io.*" %> 

<fieldset class="sidebar" style="border-width: 2px">
<legend style="background-color:#ffffff">Advanced Search Areas</legend>
<table cellspacing="0" cellpadding="4" border="0">
  <tr valign="top">
 <td><strong>Gene Expression Analysis</strong><br />
 <!--read from Gene Expression Analysis text file-->
  <% 
  Properties props1 = new Properties();
    try {
    props1.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+"geneExpressionAreas.properties"));
    } 
	catch (IOException e) {
    out.println("cant read props");
	}
	for (int t=1; t<props1.size()+1; t++){
		String Props = props1.getProperty(String.valueOf(t));
		out.print(Props);
		out.print("<br> ");
	}   
 %>

 </td>
	<td>
	<strong>Comparative Genomic Analysis</strong><br />
	<!--read from Comparative Genomic Analysis text file-->
	<% 
	  Properties props2 = new Properties();
	    try {
	    props2.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+"comparitiveGenomicAreas.properties"));
	    } 
		catch (IOException e) {
	    out.println("cant read props");
		}
		for (int t=1; t<props2.size()+1; t++){
			String Props = props2.getProperty(String.valueOf(t));
			out.print(Props);
			out.print("<Br>");
		}   
	 %>
	</td>
	</tr>
	<tr>
	<td>
	<strong>Clinical Study Analysis</strong><br />
	<!--read from Clinical Study Analysis text file-->
	<% 
	  Properties props3 = new Properties();
	    try {
	    props3.load(new FileInputStream(getServletConfig().getServletContext().getRealPath("WEB-INF")+"/"+"clinicalAreas.properties"));
	    } 
		catch (IOException e) {
	    out.println("cant read props");
		}
		for (int t=1; t<props3.size()+1; t++){
			String Props = props3.getProperty(String.valueOf(t));
			out.print(Props);
			out.print("<br />");
		}   
	 %>
	</td>
	<td>&nbsp;</td>
  </tr>
</table>
</fieldset>
