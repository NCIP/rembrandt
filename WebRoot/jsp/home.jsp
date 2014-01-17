<%--L
  Copyright (c) 2006 SAIC, SAIC-F.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/rembrandt/LICENSE.txt for details.
L--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>

<%@ page import="java.util.*, java.lang.*, java.io.*, gov.nih.nci.caintegrator.util.CaIntegratorConstants" %>
<br clear="both"/>

 
    <s:form action="quickSearch" method="post" id="qsForm" theme="simple">  
    
  	<fieldset >
     
        <legend>
          Quick Search
        </legend>
       <s:actionerror />
        <br />
        
       <s:if test="quickSearchForm.getAllGeneAlias() != null">
            <app:cshelp topic="Eliminating_aliases" />
 		</s:if>
 		
 		<s:if test="quickSearchForm.getAllGeneAlias() == null">
           <app:cshelp topic="Simple_search_overview" />
   		</s:if>
   		
       <s:if test="quickSearchForm.getAllGeneAlias() == null">
	        <strong>
	          Select graph format:
	        </strong>    
	        <br><br>
	        
	        <h5>Gene Expression-based  and Copy Number-based Graphs&nbsp;&nbsp;&nbsp;&nbsp;
	        <!-- <app:help help="Select a search method and enter search criteria, such as a HUGO gene symbol." />-->
	        </h5>
	        <br>
	        <input type="radio" checked="checked" name="quickSearchForm.plot" id="geneExpPlot" class="radio" value="geneExpPlot" onclick="javascript:onRadio(this,0);needGVal = true;">
	        <label for="geneExpPlot">Gene Expression plot&nbsp;</label><br />
	        
	        <input type="radio" name="quickSearchForm.plot" class="radio" id="kapMaiPlotGE" value="kapMaiPlotGE" onclick="javascript:onRadio(this,1);needGVal = true;">
	        <label for="kapMaiPlotGE">Kaplan-Meier survival plot for Gene Expression Data&nbsp;</label><br />
	        
	        <input type="radio" name="quickSearchForm.plot" id="kapMaiPlotCN" class="radio" value="kapMaiPlotCN" onclick="javascript:onRadio(this,2);needGVal = true;">
	        <label for="kapMaiPlotCN">Kaplan-Meier survival plot for Copy Number Data</label><br/>
	           
	           <br>
	  		<label for="quickSearchType">&#160;</label>
	        <s:select name="quickSearchForm.quickSearchType" list="quickSearchForm.getQuickSearchTypes()" 
	        id="quickSearchType" style="width:140px" theme="simple" />
	        
      		<label for="quickSearchName">&#160;</label>
        	<s:textfield id="quickSearchName" name="quickSearchForm.quickSearchName" value="" size="40" theme="simple" />
        	
        	<!--  RCL testing -->
        	<script language="javascript">
			
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
		        				
		        				//mod the submit button to prevent double clicking?
								$('submitButton').disabled = "true";	
								$('submitButton').style.width="300px";
								$('submitButton').style.color="gray";
								$('submitButton').value="processing...";

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
        	
        	<span id="indic" class="message" style="display:none;"><img src="images/indicator.gif" alt="validating"/>validating...</span>
        	
        	<div id="gAliases" style="display:none; border:1px solid red; border-top:4px solid red;padding:5px;margin:10px; width:95%;  "></div>
        	<br>
        	
        	
	       	<label for="baselineGroupName">Restrict to sample group: </label>
        	 <s:select property="baselineGroup" list="quickSearchForm.sampleGroupsList" listKey="value" listValue="label"
        	 	id="baselineGroupName" disabled="true" theme="simple">
			 	
			</s:select>
	        
	        <br><br>
	        
	        <!--  sample based plots -->
	        <h5>Sample-based Graph&nbsp;&nbsp;&nbsp;&nbsp;</h5>
	        <br>
	        <input id="samplePlotRadio" type="radio" name="quickSearchForm.plot" class="radio" 
	        	value="<%=CaIntegratorConstants.SAMPLE_KMPLOT%>" onclick="javascript:onRadio(this,3); needGVal = false;">
	        <label for="samplePlotRadio">Kaplan-Meier survival plot for Sample Data&nbsp;</label>
	        <br><br>
	        
	        
	        <label for="groupName">&#160;</label>
	        <s:select name="quickSearchForm.groupName" list="quickSearchForm.sampleGroupsList" listKey="value" listValue="label"
	        theme="simple" headerKey="0" style="margin-left:20px;width:200px;" id="groupName" disabled="false" onchange="examineGroups(this);needGVal = false;">
			 	
			</s:select>
	         vs.
	         <s:select name="quickSearchForm.groupNameCompare" 
	         list="quickSearchForm.getSampleGroupListWithExtra()" listKey="value" listValue="label"
	         theme="simple" id="groupNameCompare" style="width:200px;" disabled="false" onchange="examineGroups(this);needGVal = false;">	         	
			 	
			</s:select>
	        
	        <label for="groupNameCompare">&#160;</label>
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
					 $('samplePlotRadio').checked = "true";
					  	    
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
        </s:if>
        
       <s:if test="quickSearchForm.getAllGeneAlias() != null && quickSearchForm.getAllGeneAlias().size() > 0">
        	<div style="text-align:center; margin-top:20px; margin-bottom:20px;">
	        <s:select name="quickSearchForm.quickSearchName" list="quickSearchForm.allGeneAlias" theme="simple">
	       <!--  Shan: there might be sth else here -->
	        </s:select>
	        </div>
	        <s:hidden name="quickSearchForm.plot" />
      		<s:hidden name="quickSearchForm.quickSearchName" />
      		<s:hidden name="quickSearchForm.baselineGroup" />
        </s:if>
        
        <br/><br/>
        <div style="text-align:center">
	        <input type="submit" id="submitButton" onclick="" class="xbutton" style="width:50px;" value="Go" />
	        
	        <!-- Shan: what is this -->
	        <s:if test="quickSearchForm.getAllGeneAlias() != null">
	       		<input type="button" class="xbutton" style="width:75px;" value="Cancel" onclick="javascript:location.href='home.action';" />
	        </s:if>
        
        
        </div>
        <br>         
      </fieldset>
      
    </s:form>
    <br/>
 
    <div class="message">
          Note: Please move your mouse over the
          <app:help help="Help messages will appear here."/>
          links for help throughout the application.
     </div><br/><br/>

<script type="text/javascript">
	window.onload = function()	{ 
		
		//Shan this probably is not needed
		//$('qsForm').reset();
		
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
			
			//alert("In onsubmit");
			
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
