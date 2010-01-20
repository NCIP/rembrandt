var geneArray = new Array();
aliasChkPerformed = false;

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
			aliasChkPerformed = true;
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
        		$('gAliases').focus();
        		$("indicator").hide();
				
      		}
      		catch(err){
      			alert(err);
      		}
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

function gecnSubmit() {
	document.forms[0].target='_self';	
	if ( checkNull(document.forms[0].queryName, 'true') ) {
		return autoCheckAliases();		
	} else {
		return false;
	}
}

function autoCheckAliases() {
	var geneList = document.forms[0].geneList.value;
	var returnValue = false;
	
	if ( aliasChkPerformed == false ) {
		//alert("The unchecked genelist is: " + geneList + "  returnValue = " + returnValue);
		GeneAlias.checkAlias(geneList);
		aliasChkPerformed = true;
		window.location.hash="geneTile";
	} else {
		returnValue = true;
		//alert("The checked genelist is: " + geneList + "  returnValue = " + returnValue);
	}
	return returnValue;
}

