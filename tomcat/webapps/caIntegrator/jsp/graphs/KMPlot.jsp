<%@page contentType="text/html"%>
<%@taglib uri='/WEB-INF/cewolf.tld' prefix='cewolf' %>
<HTML>
	<BODY>
		<H1>Kaplan-Meier survival plot</H1>
		<HR>
		<jsp:useBean id="pageViews" class="gov.nih.nci.nautilus.graph.kaplanMeier.KMDataSetProducer"/>
		<cewolf:chart id="line"	title="Minutes Worked" type="line" xaxislabel="Day of the Week" yaxislabel="Minutes">
			<cewolf:data>
				<cewolf:producer id="pageViews"/>
			</cewolf:data>
		</cewolf:chart>
		<p>
			<cewolf:img chartid="line" renderer="/cewolf" width="400" height="300"/>
			<br>Hit Refesh and watch the data change!<br>  Thanks Random Number Generator!
		<P>
	</BODY>
</HTML>
