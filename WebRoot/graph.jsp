<%@ page import="java.awt.*,
			org.krysalis.jcharts.imageMap.*,
			java.util.*,
			gov.nih.nci.nautilus.lookup.*"%>

<%
long randomness = System.currentTimeMillis(); //prevent image caching

 String helpLink = "<a href=\"javascript: spawn('help.jsp";
 String helpLinkClose = "', 350, 500);\">"+
				"<img align=\"right\" src=\"images/helpIcon.jpg\" border=\"0\" "+
				"onmouseover=\"return overlib('Click here for additional information about this report.', CAPTION, 'Help', OFFSETX, -50);\" onmouseout=\"return nd();\">"+
				"</a><br clear=\"all\">";
%> 
<div>
<%=helpLink%>?sect=gplot<%=helpLinkClose%>
</div>
<img src="getMap.jsp?<%=randomness%>" usemap="#chartMap" border="0">
<br clear="all">
Legend: Probesets<br><br><br>

 <map name="chartMap">
<%

StringBuffer html = new StringBuffer( 100 );
ImageMap imageMap = (ImageMap) request.getAttribute( "map" );

ArrayList pValues = (ArrayList) request.getAttribute( "pValues" );


int counter = 0;
Iterator iterator= imageMap.getIterator();
while( iterator.hasNext() )
{
    ImageMapArea imageMapArea= (ImageMapArea) iterator.next();

	html.append( "href=\"javascript:void(0);\"" );
	html.append( "onmouseover=\"return overlib('Intensity: " + imageMapArea.getValue()+" PVALUE: " + pValues.get(counter).toString() +"', CAPTION, '"+imageMapArea.getLengendLabel()+"', FGCOLOR, '#FFFFFF', BGCOLOR, '#000000', WIDTH, 150, HEIGHT, 25);\"" );
	html.append( "onmouseout=\"return nd();\"");

	
%>
<%= imageMapArea.toHTML( html.toString() ) %>
<%
    //---reuse same StringBuffer Object
    html.delete( 0, html.length() );
    
    counter++;
}
%>
</map>

<!-- <div style="border:1px solid black; width:300px; text-align:left; padding:3px"> -->
<fieldset style="width:300px; text-align:left; padding:3px">
	<legend>Abbreviations of Group Names</legend>
		<table>
	<%
	DiseaseTypeLookup[] diseaseTypes = LookupManager.getDiseaseType();
	if(diseaseTypes != null)	{
		for (int i = 0; i< diseaseTypes.length ; i++) {
			String diseaseType = diseaseTypes[i].getDiseaseType();
			if(diseaseType.equalsIgnoreCase(gov.nih.nci.nautilus.constants.NautilusConstants.ASTRO))	{
		    	diseaseType = diseaseType.substring(0,6);
		    }	
		 	out.println("<tr><Td>"+diseaseType+":</td><Td>"+diseaseTypes[i].getDiseaseDesc() + "</td></tr>\n" );
		}
		out.println("</table>\n");
	}
	else	{
	%>
		<pre>
			GBM:	Glioblastoma Multiforme
			OLIG:	Oligodendroglioma
			ASTRO:	Astrocytoma
			MIXED:	Mixed
			GLIOMA:	Glioma (Includes All)
			NON-TUMOR: Normal
		</pre>
	<%
	}
	%>
</fieldset>
<!--
</div>
<br clear="all">
Legend: Abbreviations of Group Names
-->
<br>
<a href="javascript:void(window.print())">[Print this graph]</a><Br><Br>
<b>To save this image, right click and select "save image as..."</b><br><Br>
