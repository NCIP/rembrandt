<%@ page import="java.awt.*,
			org.krysalis.jcharts.imageMap.*,
			java.util.*"%>

<%
long randomness = System.currentTimeMillis();
%> 
<img src="getMap.jsp?<%=randomness%>" usemap="#chartMap" border="0">
<br clear="all">
Legend: Probesets<br><br>
<a href="javascript:void(window.print())">[Print this graph]</a><Br><Br>
<b>To save this image, right click and select "save image as..."</b><br><Br>
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
/*
    html.append( "href=\"javascript:alert(" );
    html.append( imageMapArea.getValue() );
    html.append( ",'" );
    html.append( imageMapArea.getLengendLabel() );
    html.append( "','" );
    html.append( imageMapArea.getXAxisLabel() );
    html.append( "');\"" );
*/
	html.append( "href=\"javascript:void(0);\"" );
	html.append( "onmouseover=\"return overlib('"+imageMapArea.getLengendLabel()+": " + imageMapArea.getValue()+" PVALUE: " + pValues.get(counter).toString() +"', CAPTION, 'Value', FGCOLOR, '#FFFFFF', BGCOLOR, '#000000', WIDTH, 150, HEIGHT, 25);\"" );
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
<!--
</body>
</html>
-->