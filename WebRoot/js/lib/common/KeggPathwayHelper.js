//Dependencies:  DynamicListHelper.js
var KeggPathwayHelper = {
	'delay' : 250,
	'getPathwayGeneSymbols' : function(pathwayName)	{
		
		DynamicListHelper.getPathwayGeneSymbols(KeggPathwayHelper.getPathwayGeneSymbols_cb, pathwayName);
	},
	'getPathwayGeneSymbols_cb' : function(txt)	{
			try	{

				var symbolsArray = eval('(' + txt + ')');
				var text = "";
				
				var size = symbolsArray.length;
				var num = 1.0;
				
				if (size > 14 && size < 22)
					num = size/7
				else if (size > 21 && size < 45)
					num = size/10
				else if (size > 44 && size < 76)
					num = size/15;
				else if (size > 75 && size < 100)
					num = size/20;
				else if (size > 99)
					num = size/25;
					
				var number = num + ".o";
				
				num = number.substring(0, number.indexOf("."));
				
				for(var i=0; i<symbolsArray.length; i++){
					
					if (i == 0)
						text = symbolsArray[i];
					else if ( i%num == 0)
						text += "<br/>" + symbolsArray[i];
					else
						text += "&nbsp;&nbsp;" + symbolsArray[i];
				}
				return overlib(text, CAPTION,  "Gene Symbols:");
			}
			catch(err)	{
				alert("ERR: " + err);
			}	
	}
};