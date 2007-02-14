<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.caintegrator.util.CaIntegratorConstants" %>
<br clear="both"/>
    <html:form action="/quickSearch.do?method=quickSearch" styleId="qsForm">   
      <fieldset>
        <legend>
          Quick Search
        </legend>
        <html:errors/>
        <br />
        
        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
            <script type="text/javascript">Help.insertHelp("Eliminating_aliases", " align='right'", "padding:2px;");</script>
 		</logic:notEmpty>
 		<logic:empty name="quickSearchForm" property="allGeneAlias">
           <script type="text/javascript">Help.insertHelp("Simple_search_overview", " align='right'", "padding:2px;");</script>
   		</logic:empty>
   		
        <logic:empty name="quickSearchForm" property="allGeneAlias">
	        <strong>
	          Select graph format:
	        </strong>    
	        <br />
	        
	        <h5>Gene Expression-based  and Copy Number-based Graphs&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Select a search method and enter search criteria, such as a HUGO gene symbol." /></h5>
	        <input type="radio" checked="checked" name="plot" class="radio" value="geneExpPlot" onclick="javascript:onRadio(this,0);needGVal = true;">
	        Gene Expression plot&nbsp;<br />
	        
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);needGVal = true;">
	        Kaplan-Meier survival plot for Gene Expression Data&nbsp;<br />
	        
	        <!-- <h5>Copy Number-based Graph&nbsp;&nbsp;&nbsp;&nbsp;
	        <app:help help="Enter a HUGO gene symbol (such as EGFR,WT1) or an Affymetrix 100K SNP Probeset ID (reporter) to plot a Kaplan-Meier survival plot based on the Gene copy number or the SNP reporter respectively." /></h5>
	        -->
	        <input type="radio" name="plot" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);needGVal = true;">
	        Kaplan-Meier survival plot for Copy Number Data<br/>
	           
	     <!--     <hr width=100% color="#002185" size="1px" /> -->
	        
	        <br/>
	        <select name="quickSearchType" style="width:140px">
	       		<option>Gene Keyword</option>
	        </select>

        </logic:empty>
        <logic:empty name="quickSearchForm" property="allGeneAlias">
        	<input type="text" name="quickSearchName" id="quickSearchName" value="" size="40" />&nbsp;
        	
        	<!--  RCL testing -->
        	<script language="javascript">
			//onblur="if(this.value!=''){$('indic').style.display='';DynamicListHelper.getGeneAliases($('quickSearchName').value, geneLookup_cb);}"
			//invoke when the forms submitted now
			var needGVal = true;
        		var geneLookup_cb = function(ga)	{
        			var gArray = eval('(' + ga + ')');
    				try	{
	        			if(gArray.length>0)	{
	        			
		        			if(gArray[0]=="valid")	{
		        				$('indic').style.display='none';
		        				needGVal = false;
		        				$('qsForm').submit();
		        				return;
		        				//throw("valid");
		        			}
		        			
	        				var galhtm = ""; 
	        				galhtm += "<div style=\"float:right;\"><a href=\"#\" onclick=\"$('gAliases').style.display='none';return false;\">[x]</a></div>";
		        			
	        				if(gArray[0]!="invalid")	{
			        			var gas = "";
			        			for(var i=0; i<gArray.length; i++)	{
			        				gas+= "<a href='#' onclick=\"$('quickSearchName').value='"+gArray[i].symbol+"';return false;\">"+gArray[i].symbol + "</a> - " +  gArray[i].name.replace(/'/g,"&apos;") + "<br/>";
			        			}
			        			galhtm += "<p style='padding:5px;margin:0px;background-color:#FF0000;' id='fal'>";
			        			galhtm += "One or more genes or their aliases have been found for the gene keyword:  ";
			        			galhtm += $('quickSearchName').value + ".  Please select an approved alias from below.";
			        			galhtm += "<br clear='all'/></p>";
			        			galhtm += gas;
			        		}
			        		else if(gArray[0]=="invalid")	{
			        			galhtm += "<p style='_display:block;_height:50px;padding:5px;margin:0px;background-color:#FF0000;' id='fal'>";
			        			galhtm += "<br/>The element you entered is either invalid, or not in the database.  Please select another.<br/>";
			        			galhtm += "<br clear='all'/></p>";
			        		}
		        			$('gAliases').innerHTML = galhtm;
		        			$('quickSearchName').value = "";
		        			$('quickSearchName').style.border= "1px solid red";
		        			//Effect.BlindDown('gAliases');
		        			Effect.toggle('gAliases');
		        			
		        			/*
		        			setTimeout(function()	{
			        			Fat.fade_element("fal", 10, 5000, "#FF0000", "#F5F5F5");
			        		}, 2000);
			        		*/
        				}
        			}
        			catch(err){}
   
        			setTimeout(function()	{$('indic').style.display='none';}, 500);
        		}
        	</script>
        	<span id="indic" class="message" style="display:none;"><img src="images/indicator.gif"/>validating...</span>
        	<!--  <a href="#" onclick="DynamicListHelper.getGeneAliases($('quickSearchName').value, geneLookup_cb);">[v]</a> -->
        	<div id="gAliases" style="display:none; border:1px solid red; border-top:4px solid red;padding:5px;margin:10px; width:95%;  "></div>
        	<br/>Restrict to sample group: 
        	 <html:select property="baselineGroup" styleId="baselineGroupName" disabled="true">
			 	<html:optionsCollection property="sampleGroupsList" />
			</html:select>
	
	        <br/><br/>
	 
	        <!--  sample based plots -->
	        <h5>Sample-based Graph&nbsp;&nbsp;&nbsp;&nbsp;</h5>
	        <input type="radio" name="plot" class="radio" value="<%=CaIntegratorConstants.SAMPLE_KMPLOT%>" onclick="javascript:onRadio(this,3); needGVal = false;">
	        Kaplan-Meier survival plot for Sample Data&nbsp;
	        <br/><br/>
	        <html:select property="groupName" style="margin-left:20px;width:200px;" styleId="groupName" disabled="false" onchange="examineGroups(this);">
			 	<html:optionsCollection property="sampleGroupsList" />
			</html:select>
			
			<!-- 
	        <input type="text" id="groupName" name="groupName" style="margin-left:25px"/> vs.
	        <input type="text" id="groupNameCompare" name="groupNameCompare"/>
	        -->
	         vs. 
	         <html:select property="groupNameCompare" styleId="groupNameCompare" style="width:200px;" disabled="false" onchange="examineGroups(this);">	         	
			 	<html:optionsCollection property="sampleGroupsList" />
			 	<option value="none" selected="true">None</option>
			 	<option value="Rest of the Gliomas">Rest of the Gliomas</option>
			</html:select>
	        <br/>
	        <script language="javascript">
	        	try	{
	        		//this overwrites the 'all' that is added in the qsForm as a placeholder
		        	//document.getElementById("baselineGroupName").options[0] = null;
		        	//document.getElementById("groupName").options[0] = null;
	    	    	//document.getElementById("groupNameCompare").options[0].text = "Rest of the Samples";
	    	    }
	    	    catch(err){}
	    	    
	    	    function examineGroups(dd)	{
					    	    
	    	    	if($('groupNameCompare').value == $('groupName').value){ 
	    	    		alert('Comparison Groups Can Not be the Same'); 
	    	    		dd.selectedIndex = 0;
	    	    	}
	    	    	if( $('groupNameCompare').value == 'Rest of the Gliomas' && $('groupName').value == "ALL GLIOMA" ){ 
	    	    		alert("Comparison between ALL GLIOMA and Rest of the Gliomas is not allowed, please select another group to compare to");
	    	    		dd.selectedIndex = 0;
	    	    	}
	    	    	if($('groupName').value == "ALL" && $('groupNameCompare').value=="Rest of the Samples"){ 
	    	    		alert("Comparison between All and Rest of the Samples is not allowed, please select another group to compare to");
	    	    		$('groupName').selectedIndex = 0;
	    	    		$('groupNameCompare').selectedIndex = 0;
	    	    		
	    	    	}
	    	    }
	        </script>
        </logic:empty>
        
        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
        	<div style="text-align:center; margin-top:20px; margin-bottom:20px;">
	        <select name="quickSearchName">
	        <logic:iterate name="quickSearchForm" property="allGeneAlias" id="test">
	          <option>
	            <bean:write name='test' property='approvedSymbol' filter='true' />:
	            <bean:write name='test' property='approvedName' filter='true' />            
	          </option>
	            </logic:iterate>
	        </select>
	        </div>
	        <html:hidden property="plot" />
      		<html:hidden property="quickSearchName" />
      		<html:hidden property="baselineGroup" />
        </logic:notEmpty>
        
        <br/><br/>
        <div style="text-align:center">
	        <input type="submit" id="submitButton" onclick="" class="xbutton" style="width:50px;" value="Go" />
		        
	        <logic:notEmpty name="quickSearchForm" property="allGeneAlias">
	       		<html:button styleClass="xbutton" property="method" style="width:75px;" value="Cancel" onclick="javascript:location.href='home.do';" />
	        </logic:notEmpty>
        <!-- 
        	<app:help help="Select either the Gene Keyword or SNP Probe set ID option, as applicable, from the drop-down list and enter the keyword or ID in the text box.The SNP Probe set ID option is available only for the Copy Number-based Graph format." />
        -->
        
        </div>
        <br />         
      </fieldset>
      <!-- 
      <html:hidden property="plot" />
      <html:hidden property="quickSearchName" />
      -->
    </html:form>
    <br/>
 
    <div class="message">
          Note: Please move your mouse over the
          <app:help help="Help messages will appear here."/>
          links for help throughout the application.
     </div><br/><br/>

<script type="text/javascript">
	window.onload = function()	{ 
		$('qsForm').reset();
		
		//preselect ALL
    	var opts = document.getElementById("baselineGroupName").options;
		for (var i=0; i < opts.length; i++) {
			if (opts[i].text == "ALL GLIOMA") {
				//alert("hit");
				opts[i].selected = true;
			}
		}
					
		$('quickSearchName').disabled = false;
		
		$('qsForm').onsubmit = function()	{
			//test this on mac
			//dont do the alias look up for SNP
			if(document.forms[0].quickSearchType.selectedIndex==1)	{
				needGVal = false;
			}
			
			$('gAliases').style.display = 'none'; //hide it again
			
			if(needGVal == true)	{
				$('indic').style.display='';
				if($('quickSearchName').value!='' && $('quickSearchName').value!='*')	{
					DynamicListHelper.getGeneAliases($('quickSearchName').value, geneLookup_cb);
				}
				else	{
					alert("You must enter a symbol or a partial symbol with wildcard.  Entering just a * is not valid.");
					$('indic').style.display='none';
					$('quickSearchName').style.border= "1px solid red";
				}
				return false;
			}
			else	{
				return true;
			}
		};
	};
	
</script>
