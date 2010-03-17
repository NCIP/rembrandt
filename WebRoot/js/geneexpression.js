var geneArray = new Array();
aliasChkPerformed = false;
previewClicked = false;
submitClicked = false;

var GeneAlias = {
		'validateAliases' : function(a, btn) {
			var returnValue = false;
			
			if ( btn == "Preview" ) {
				previewClicked = true;
			} else {
				submitClicked = true;
			}
			
			if ( aliasChkPerformed == false ) {

				a = a.gsub("\\r", ",");
				a = a.gsub("\\n", ",");
				a = a.gsub(",,", ",");
				a = a.gsub(" ", "");
					
				if(a == "")	{
					alert("Please enter a gene");
					return;
				}
	
				geneArray = a.split(",");
				geneArray = geneArray.compact();
				geneArray = geneArray.uniq();
					
				$('geneList').value = geneArray.join(",");
				
				DynamicListHelper.validateAliases(geneArray.join(","), GeneAlias.validateAliases_cb);
			} else {
				returnValue = true;
			}
			return returnValue;
		},
		'validateAliases_cb' : function(r)	{
			var geneListResult;
			if ( r != "true" ) {
				geneListResult = r.split("|");
				$('geneList').value = geneListResult[0];
				GeneAlias.checkAlias(geneListResult[1]);
				window.location.hash="geneTile";
			} else {
				aliasChkPerformed = true;
				if ( previewClicked ) {
					document.forms[0].previewButton.click();
				} else {
					document.forms[0].submittalButton.click();
				}
			}
		},
		'checkAlias' : function(g)	{
			$("indicator").show();
			g = g.gsub("\\r", ",");
			g = g.gsub("\\n", ",");
			g = g.gsub(",,", ",");
			g = g.gsub(" ", "");
			
			if(g == "")	{
				alert("Please enter a gene");
				$("indicator").hide();
				return;
			}

			geneArray = g.split(",");
			geneArray = geneArray.compact();
			geneArray = geneArray.uniq();
			
			$('geneList').value = geneArray.join(",");
			aliasChkPerformed = true;
			DynamicListHelper.getGeneAliasesList(geneArray.join(","), GeneAlias.checkAlias_cb);
		},
		'checkAlias_cb' : function(r)	{
			
			$('gAliases').update("");
			$('gAliases').hide();
			var validGenes = "";
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
					if ( g.status == "valid" ) {
						orightm += "<a href='#' onclick=\"GeneAlias.handleSymbol('"+g.original+"');return false;\">"+g.original + "</a>  ";
						validGenes += g.original + ",";
					} else {
						orightm += g.original + "  ";
					}
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
	        			gas+= g.original + "</a> - Invalid symbol or not in the database.<br/>";
	        			//gas+= "<a href='#' onclick=\"GeneAlias.handleSymbol('"+g.original+"');return false;\">"+g.original + "</a> - Invalid symbol or not in the database.<br/>";
					}
				});
				
				$('gAliases').innerHTML = galhtm + orightm + "<br/>" +  gas + "<br/>";
        		if(clearEntriesFlag)	{
        			//$('geneList').value = "";
        			//geneArray.clear();
        			GeneAlias.handleSymbol("");
        			$('geneList').style.border= "1px solid #AB0303";
        		}
        		if (validGenes.substr(validGenes.length-1) == ",") {
        			validGenes = validGenes.substr(0, validGenes.length-1)
        		}
    			$('geneList').value = validGenes;
        		$('gAliases').show();
        		$('gAliases').focus();
        		$("indicator").hide();
				
      		}
      		catch(err){
      			alert("ERROR OCCURED:: " + err);
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
		for (var i=0; i < document.forms[0].geneOption.length; i++) {
			var geneOptionVal = document.forms[0].geneOption[i].value;
	  		if (document.forms[0].geneOption[i].checked) {
			   if ( geneOptionVal == "standard" ) {
			       return GeneAlias.validateAliases($('geneList').value, 'Submit');
			   }
		       break;
	  		}
		}
	} else {
		return false;
	}
}

