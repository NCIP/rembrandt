<%@ page import="java.awt.*,
			org.krysalis.jcharts.*,
			org.krysalis.jcharts.chartData.*,
			org.krysalis.jcharts.properties.*,
			org.krysalis.jcharts.types.ChartType,
			org.krysalis.jcharts.axisChart.*,
			org.krysalis.jcharts.test.TestDataGenerator,
			org.krysalis.jcharts.encoders.JPEGEncoder13,
			org.krysalis.jcharts.encoders.*,
			org.krysalis.jcharts.properties.util.ChartFont,
			org.krysalis.jcharts.imageMap.*,
			org.krysalis.jcharts.encoders.ServletEncoderHelper"%>
<%

Chart chart= (Chart) session.getAttribute( "chart" );

if(chart!=null)
{
	ServletEncoderHelper.encodePNG(chart, response);
}
else
	System.out.println("No chart in session");
	
    session.removeAttribute( "chart" );
%>