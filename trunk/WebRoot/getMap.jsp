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
			org.krysalis.jcharts.encoders.ServletEncoderHelper,
			gov.nih.nci.rembrandt.util.RembrandtConstants,
	 		org.apache.log4j.Logger"%>
<%

Logger logger = Logger.getLogger(RembrandtConstants.LOGGER);
Chart chart= (Chart) session.getAttribute( "chart" );

if(chart!=null)
{
	ServletEncoderHelper.encodePNG(chart, response);
}
else
	logger.debug("No chart in session");
	
    session.removeAttribute( "chart" );
%>