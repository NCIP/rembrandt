<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<fieldset class="gray">

<legend class="red">Region</legend>
<%
	String act = request.getParameter("act");
	System.out.println(act);
%>
	<!-- <html:form action="<%=act%>" > -->
<br />	&nbsp;&nbsp;Chromosome Number&nbsp;
	<select name="chrosomeNumber" onchange="javascript:changeList(this);">
	 <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
	 <option>1</option>
	 <option>2</option>
	 <option>3</option>
	 <option>4</option>
	 <option>5</option>
	 <option>6</option>
	 <option>7</option>
	 <option>8</option>
	 <option>9</option>
	 <option>10</option>
	 <option>11</option>
	 <option>12</option>
	 <option>13</option>
	 <option>14</option>
	 <option>15</option>
	 <option>16</option>
	 <option>17</option>
	 <option>18</option>
	 <option>19</option>
	 <option>20</option>
	 <option>21</option>
	 <option>22</option>
	 <option>X</option>
	 <option>Y</option>
	</select>&nbsp;<br>
	<blockquote>
	<html:radio property="region" value="cytoband" styleClass="radio" />
			Cytoband&nbsp; <html:select property="cytobandRegion">
			               <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>						   
                           </html:select>
			<input type="button" class="sbutton" value="MAP Browser..." disabled="true"><br />
	<html:radio property="region" value="basePairPosition" styleClass="radio" />
	        Base Pair Position (kb)&nbsp; <html:text property="basePairStart"/>
 	&nbsp;-to-&nbsp;<html:text property="basePairEnd"/>
	</blockquote>	
<!--
<input type="radio" class="radio" name="region" value="cytoband" checked>&nbsp;
Cytoband &nbsp;<input type="text" name="cytobandRegion">
-->


				<html:errors property="region"/>

				</fieldset>
				
				
				<!--
<input type="radio" class="radio" name="region" value="chnum">&nbsp;
Chromosome Number
<blockquote>
	<input type="text" name="chromosomeNumber">&nbsp;
	Base Pair Position (kb)&nbsp;
	<input type="text" size="10" name="basePairStart">&nbsp;start&nbsp;<input type="text" size="10" name="basePairEnd">&nbsp;end
</blockquote>
-->

<!-- </html:form> -->