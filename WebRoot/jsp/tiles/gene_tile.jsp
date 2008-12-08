<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/rembrandt.tld" prefix="app" %>
<% 
/*
 * Gene Tile - used in: GeneExpression form, CGH form
 */

String act = request.getParameter("act");
%>
	
<script type="text/javascript">

	var geneArray = new Array();

	var GeneAlias = {
		'checkAlias' : function(g)	{
			$("indicator").show();
			g = g.gsub(" ", "");
			
			if(g == "")	{
				alert("Please enter a gene");
				$("indicator").hide();
				return;
			}
			//DynamicListHelper.getGeneAliases(g, GeneAlias.checkAlias_cb);
			geneArray = g.split(",");
			geneArray = geneArray.compact();
			geneArray = geneArray.uniq();
			
			//console.log(geneArray);
			$('geneList').value = geneArray.join(",");
			DynamicListHelper.getGeneAliasesList(geneArray.join(","), GeneAlias.checkAlias_cb);
		},
		'checkAlias_cb' : function(r)	{
			//console.log(r);
			
			$('gAliases').update("");
			$('gAliases').hide();
			var gArray = eval('(' + r + ')');
			try	{
				var clearEntriesFlag = false;
				var galhtm = "";
				galhtm += "<div style=\"float:right;color:#fff;\"><a href=\"#\" style=\"color:#fff;\" onclick=\"$('gAliases').style.display='none';return false;\">[x]</a></div>";
				galhtm += "<p style='padding:5px;margin:0px;background-color:#AB0303;color:#fff;' id='fal'>";
       			galhtm += "Please click a symbol below to add it to your query.";
       			galhtm += "<br clear='all'/></p>";
				var gas = "";
				var orightm = "You Entered: ";
				gArray.each( function(g, indx)	{
					orightm += "<a href='#' onclick=\"GeneAlias.handleSymbol('"+g.original+"');return false;\">"+g.original + "</a>  ";
       				gas += "<br/><div style='background-color:silver'>"+(indx+1) + ".) " + g.original + " " + (g.status == "hasAliases" ? "Aliases" : "") + " </div>";
       				if(g.status == "hasAliases")	{
       					clearEntriesFlag = true;
       					
       					geneArray = geneArray.without(g.original);
       					
	       				g.aliases.each( function(aa) {
	        				gas+= "<a href='#' onclick=\"GeneAlias.handleSymbol('"+aa.symbol+"');return false;\">"+aa.symbol + "</a> - " +  aa.name.replace(/'/g,"&apos;") + "<br/>";
	     					
	       				});
					}
					else if(g.status == "valid")	{
	        			gas+= "<a href='#' onclick=\"GeneAlias.handleSymbol('"+g.original+"');return false;\">"+g.original + "</a> - Valid Symbol.<br/>";
					}
					else if(g.status == "invalid")	{
						geneArray = geneArray.without(g.original);
	        			gas+= "<a href='#' onclick=\"GeneAlias.handleSymbol('"+g.original+"');return false;\">"+g.original + "</a> - Invalid symbol or not in the database.<br/>";
					}
				});
				
				$('gAliases').innerHTML = galhtm + orightm + "<br/>" +  gas + "<br/>";
        		if(clearEntriesFlag)	{
        			//$('geneList').value = "";
        			//geneArray.clear();
        			GeneAlias.handleSymbol("");
        			$('geneList').style.border= "1px solid #AB0303";
        		}
        		$('gAliases').show();
        		$("indicator").hide();
				
				//start legacy
	/*			
       			if(gArray.length>0)	{
        			if(gArray[0]=="valid")	{
        				alert("No aliases found");
        				return;
        				//throw("valid");
        			}
       				var galhtm = ""; 
       				galhtm += "<div style=\"float:right;color:#fff;\"><a href=\"#\" style=\"color:#fff;\" onclick=\"$('gAliases').style.display='none';return false;\">[x]</a></div>";
       				if(gArray[0]!="invalid")	{
	        			var gas = "";
	        			for(var i=0; i<gArray.length; i++)	{
	        				gas+= "<a href='#' onclick=\"$('geneList').value='"+gArray[i].symbol+"';return false;\">"+gArray[i].symbol + "</a> - " +  gArray[i].name.replace(/'/g,"&apos;") + "<br/>";
	        			}
	        			galhtm += "<p style='padding:5px;margin:0px;background-color:#AB0303;color:#fff;' id='fal'>";
	        			galhtm += "One or more genes or their aliases have been found for the gene keyword:  ";
	        			galhtm += $('geneList').value + ".  Please select an approved alias from below.";
	        			galhtm += "<br clear='all'/></p>";
	        			galhtm += gas;
	        		}
	        		else if(gArray[0]=="invalid")	{
	        			galhtm += "<p style='_display:block;_height:50px;padding:5px;margin:0px;background-color:#AB0303;color:#fff;' id='fal'>";
	        			galhtm += "<br/>The element you entered is either invalid, or not in the database.  Please select another.<br/>";
	        			galhtm += "<br clear='all'/></p>";
	        		}
        			$('gAliases').innerHTML = galhtm + "<br/>";
        			$('geneList').value = "";
        			$('geneList').style.border= "1px solid red";
        			$('gAliases').show();
      			}
      	*/
      		}
      		catch(err){
      			alert(err);
      		}
			//end legacy
		},
		'handleSymbol' : function(g)	{
			//add g to the global
			if(g!="")	{
				geneArray.push(g.strip());
			}
			geneArray = geneArray.compact();
			geneArray = geneArray.uniq();
			
			//console.log(geneArray);
			$('geneList').value = geneArray.join(",");
		},
		'armAliasLink' : function()	{
			if($F('geneType')!="genesymbol")	{
				$("aliasLink").hide();
			}
			else	{
				$('aliasLink').show();
			}
		}
	}

</script>

<fieldset class="gray">
<legend class="red">Gene
    <a href="javascript: Help.popHelp('<%=act%>_Gene_tooltip');">[?]</a>    
	<!-- <app:help help="Search on a gene identifier, a gene list, or All Genes. " />-->
</legend>

<br/>
<html:radio property="geneOption" styleClass="radio" value="standard" onclick="submitStandardQuery();"/>
  
	Type Genes:&nbsp;&nbsp;
	<html:select property="geneType" styleId="geneType" disabled="false" onchange="GeneAlias.armAliasLink();">
		<html:optionsCollection property="geneTypeColl" />
	</html:select>
 	
			<html:text property="geneList" styleId="geneList" disabled="false" onfocus="" onblur="" />
			<a href="#" id="aliasLink" onclick="GeneAlias.checkAlias($('geneList').value);return false;">check aliases</a>
			<img src="images/indicator.gif" id="indicator" style="display:none;"/>
			<br/><br/>
			<div id="gAliases" style="display:none; margin-left:20px;border:1px solid #AB0303;"></div>
			<br/><br/>
			
			<html:radio property="geneOption" styleClass="radio" value="geneList" onclick="submitStandardQuery();" styleId="geneOptionGeneList"/>
		
			 Choose a saved Gene List:&nbsp;&nbsp;
			<html:select property="geneFile" disabled="false" styleId="geneFileDD">
				<html:optionsCollection property="savedGeneList" />
			</html:select>
			<br/>
		   		
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/>
	
	<!-- 	
	<logic:present name="comparitivegenomicForm">
		<logic:equal name="comparitivegenomicForm" property="geneOption" scope="request" value="standard">
			<p style="margin-left:30px">
			<html:radio property="geneGroup" value="Specify" styleClass="radio" onfocus="javascript:onRadio(this,0);"/>
			<html:text property="geneList" disabled="false" onfocus="javascript:radioFold(this);" onblur="javascript:cRadio(this, document.forms[0].geneGroup[0]);" />
			&nbsp;-or-&nbsp;
			<html:radio property="geneGroup" value="Upload" styleClass="radio" onfocus="javascript:onRadio(this,1);"/>
			<html:file property="geneFile" disabled="true"  onblur="javascript:cRadio(this, document.forms[0].geneGroup[1]);" onfocus="javascript:document.forms[0].geneGroup[1].checked = true;" />
			<br/>
			</p>		
			<html:errors property="geneFile"/>
			<html:errors property="geneGroup"/>
			<html:errors property="geneList"/>
			<html:errors property="geneType"/>
		</logic:equal>
	</logic:present>
-->
<br/>
<html:radio property="geneOption" styleClass="radio" value="allGenes" onclick="submitAllGenesQuery();" />All Genes Query
		
</fieldset>		
<script language="javascript">
//run this onload
if($("geneFileDD").options.length<1)	{
	try	{
		$("geneOptionGeneList").checked = $("geneOptionGeneList").selected = false;
		$("geneOptionGeneList").disabled = true;
		$("geneFileDD").disabled = true;
	}
	catch(e){}
}

function submitAllGenesQuery(){
	//if(document.forms[0].multiUseButton.value!="AllGenes")	{
		document.forms[0].multiUseButton.value="AllGenes";
		document.forms[0].multiUseButton.click();
//	}
}
	    
function submitStandardQuery(){
	//if(document.forms[0].multiUseButton.value!="Standard")	{
		document.forms[0].multiUseButton.value="Standard";
		document.forms[0].multiUseButton.click();
	//}
}
</script>	