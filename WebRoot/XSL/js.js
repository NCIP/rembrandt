hideLoadingMessage();


function openCCHelp() {
	window.open ("https://wiki.nci.nih.gov/display/icrportals/5+Viewing+REMBRANDT+Results+v1.5.8#id-5ViewingREMBRANDTResultsv158-ClassComparisonReport", 
			"Help", "status,scrollbars,resizable,width=800,height=500");  
	//use the below if you want the "always on top" feature, most dont like it
	//window.open (Help.url+topic, "Help", "alwaysRaised,dependent,status,scrollbars,resizable,width=800,height=500");  
	//alert(Help.url+topic);
}

function stupidXSL(i, cPage, total)	{
	var str = "";
	if(i == cPage)
		str = "["+(i+1)+"]&nbsp;"
	else if(i < cPage+3 && i > cPage-3)
		str = "<a href=\"javascript:goPage('"+(i)+"');\">["+(i+1)+"]</a>&nbsp;";
	else if(i == 0)
		str = "<a href=\"javascript:goPage('"+(i)+"');\">[first]</a>&nbsp;...&nbsp;";
	else if(i == total-1)
		str = "...&nbsp;<a href=\"javascript:goPage('"+(i)+"');\">[last]</a>&nbsp;";
	
	if(str!="")
		document.write(str);

}

	function checkAll(field)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)
				field[i].checked = true ;
		}
		else
			field.checked = true;
			
			var c;
			c = document.getElementsByTagName("input");
			for(i=0; i<c.length; i++)	{
				if(c[i].type == 'checkbox' && c[i].id == 'grpcheck')
					c[i].checked = true;
			}

	}
		
	function uncheckAll(field)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)
				field[i].checked = false ;
		}
		else
			field.checked = false;

			var c;
			c = document.getElementsByTagName("input");
			for(i=0; i<c.length; i++)	{
				if(c[i].type == 'checkbox' && c[i].id == 'grpcheck')
					c[i].checked = false;
			}
	}
		
	function checkById(field, idx)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)	{
				if(field[i].id == idx)	{
					field[i].checked = true;
					//field[i].checked = !field[i].checked;
				}
			}
		}
		else	{
			if(field.id == idx)
				field.checked = true;
		}
	}
		
	function uncheckById(field, idx)	{
		if(field.length > 1)	{
			for (i = 0; i < field.length; i++)	{
				if(field[i].id == idx)
					field[i].checked = false;
			}
		}
		else	{
			if(field.id == idx)
				field.checked = false;
		}
	}

	//not in use
	function toggleCheckById(field, idx)	{
		for (i = 0; i < field.length; i++)	{
			if(field[i].id == idx)	{
				field[i].checked = !field[i].checked;
				//field[i].checked = true;
			}
		}
	}

	function saveSamples()	{
		//save the list via first, then process the report
		var savedSamples = Array();
	
		document.prbSamples.prbQueryName.value = document.getElementById('tmp_prb_queryName').value;
		var can_continue = checkIfSamplesSelected( savedSamples );
		
		if(can_continue)	{
			try	{
				if(savedSamples.length>0)
					DynamicReport.saveSamples(savedSamples.join(","), document.prbSamples.prbQueryName.value, saveSamples_cb);
			}
			catch(e){alert("list did not save successfully");}
			//document.prbSamples.submit();
		}
		else	
			alert("You must select at least one sample");
	}
	
	function webGenomePlotRequest(qName)	{
	//save the list via first, then process the report
	var savedSamples = Array();

	document.prbSamples.prbQueryName.value = document.getElementById('tmp_prb_queryName').value;
	var can_continue = checkIfSamplesSelected( savedSamples );
	
	if(can_continue)	{
		try	{
			if(savedSamples.length>0 && savedSamples.length<=50 ){
				DynamicReport.saveSamplesForWebGenome(savedSamples.join(","), qName, webGenome_cb);
				}
			else	{
				alert("For WebGenome plot, Please select less than 50 samples at a time");
				}
		}
		catch(e){alert("Web Genome Plot Request Unsuccessful");}
		//document.prbSamples.submit();
	}
	else	
		alert("For WebGenome plot, you must select at least one sample but not more than 20 samples");
	}
	
	function checkIfSamplesSelected( savedSamples ) {
		var can_continue = false;
		
		if(document.prbSamples.samples.length > 1)	{
			for (i = 0; i < document.prbSamples.samples.length; i++)	{
				// check if they are selected and the Parent cell <td> is not hidden
				if(document.prbSamples.samples[i].checked == true && document.prbSamples.samples[i].parentNode.style.display != "none" )	{
					can_continue = true;
					savedSamples.push(document.prbSamples.samples[i].value);
				}
			}
		}
		else	{
			if(document.prbSamples.samples.checked == true)	{
				can_continue = true;
				savedSamples.push(document.prbSamples.samples.value);
			}
		}
		
		return can_continue;
	}
	

	function saveSamples_cb(txt)	{
		if(txt!="fail" && document.prbSamples)	{
			document.prbSamples.submit();
		}
		else
			alert('list didnt save');
	}
	
	function webGenome_cb(txt) {
		if(txt!="fail")	{
		var dest ="runReport.do?method=webGenomeRequest"
		var winw = 800;
		var winh = 550;
		spawn(dest, winw, winh);
		}
		else
			alert('No samples selected for Web Genome Plot');
	}
	
	function groupCheck(field, idx, ischecked)	{
		if(ischecked)	{
			checkById(field, idx);
		}
		else	{
			uncheckById(field, idx);
		}
	}

function clearFilterForm(form)	{
	form.filter_string.value = '';
	form.submit();
}

function goFilterColumn(status, my_class)	{
		var st = 'block';
		if(status) { st = 'none'; }
		if(!status)	{ st = '';}
		
		c = document.getElementsByTagName("td");
		
		for(i=0; i<c.length; i++)	{
				if(c[i].className == my_class)	{
						c[i].style.display = st; 			  
				}
		}		
}
function goFilterColumnMg(box, my_class)	{
	if(box.checked == true)
		goFilterColumn(true, my_class);
	else if(box.checked == false)
		goFilterColumn(false, my_class);
}

function toggleDiv(id)	{
	
		if(document.getElementById(id).style.display == "none")
			document.getElementById(id).style.display = "block";
		else if(document.getElementById(id).style.display != "none")
			document.getElementById(id).style.display = "none";	
}

function displayDiv(id, dis)	{ 
 	document.getElementById(id).style.display = dis;
}

function showHelp(help)	{
	return overlib(help, CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);
}

var w; //hold the window ref

function spawnAnnot(type, element)	{

	var el; //either accept and object or a string
	if(element.innerHTML)
		el = element.innerHTML;
	else
		el = element;
		
	var page = "";
	var jso = Object();
	//types: gene, image, reporter, reporterc
	
	if(type == 'reporterFromGeneQS')	{
		if(/^\d+_{1}a{1}\d+/.test(el))	{
			//this is a llink special - need to snip the _aXX
			//_a then an integer ex 1956_a1
			el = el.substring(0, el.indexOf("_a"));
		}
	}
	
	jso.keyType = "";
	//clean up the types before passing to server
	switch(type)	{
		case "gene":
			jso.keyType = type;
		break;
		case "reporterFromCopy":
		case "reporterFromGene":
		case "cytoband":
		case "reporterFromCC":
		case "reporterFromGeneQS":
			//all these reporter's -might- be IMAGE clones so need to check that first
			if(el.indexOf("IMAGE")!= -1)	{
				//remove the IMAGE:? 
				//el = el.substring(el.indexOf("IMAGE:"), el.length);
				jso.keyType = "image";
			}
			else if(type == 'cytoband')	{
				jso.keyType = "cytoband";
			}
			else if(type == "reporterFromCopy")	{
				jso.keyType = "reporterc";
			}
			else if(type == "reporterFromGeneQS")	{
				if(/^\d+$/.test(el))	{
					//all numbers
					//this is a llink usually
					jso.keyType = "gene";
				}		
				else if(/^(H{1}s{1}(\.){1})/i.test(el))	{
					//this is an acc no
					jso.keyType = "gene";
				}
				else	{
					jso.keyType = "reporter";
				}
			}
			else	{
				jso.keyType = "reporter";
			}
		break;
	}
	
	jso.key = escape(el);
	if(jso.keyType == "")	{
		alert("Annotation link is not currently available.");
	}
	else	{
		try	{	
			rbtFrame('rbtFrame.jsp?p=');
			DynamicReport.processAnnotation(jso.toJSONString(), spawnAnnot_cb);
		}
		catch(err)	{
			alert("Annotation link is not currently available.");
		}
	}

}

function spawnAnnot_cb(txt)	{
	try	{
		var page = escape(txt);
		w.location.replace('rbtFrame.jsp?p='+page);
	}
	catch(err){}
}

function checkWGThresh(samples, did)	{
	try	{
		DynamicReport.checkWGThresh(samples, checkWGThresh_cb);
	}
	catch(er)	{}	
}

function checkWGThresh_cb(txt)	{
	if(txt!='' && $('wgThresh'))
		$('wgThresh').innerHTML = "<br/><b class='msg' style='color:#AB0303;'>Note: " + txt + "</b>";
}

function spawn(url,winw,winh) {
	if(window){
  	 w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
   	if (window.focus){w.focus()}
   }

return false;   
}

function rbtFrame(page)	{
	var winw = 800;
	var winh = 550;
	spawn('rbtFrame.jsp?p='+page, winw, winh);
}

function selectHOperand(select, value)	{
	select.value = value;
}

function selectHideShowOnly(select, value)	{
	for(i=0; i<select.length; i++)	{

		if(select[i].value == value)	{
			select[i].checked = "true";
			select[i].checked = true;
		}
	}
}

function showCNumberFilter(v, id)	{
	if(v == 'copy number')	{
		displayDiv(id, "block");
		displayDiv('fb', "none");
		displayDiv('hideLabel', "none");
		document.getElementById('showOnly_radio').checked = true;
	}
	else	{
		if(document.getElementById(id))	{
			displayDiv(id, "none");
		}
		displayDiv('fb', "inline");
		displayDiv('hideLabel', "inline");
	}
}

function checkElement(id)	{
	
		if(id.value == '')	{
			alert("You must enter text in the filter field");
			id.className = "er";
			return false;
		}
		else
			return true;
}

function stupidXSLEscape(qname, rtype)	{
	// For Preview Results, no checkboxes are shown for Samples. So, Export All by default.
	if ( qname === 'previewResults' ) {
		var dest = "runReport.do?method=runGeneViewReport&queryName="+ escape(qname)+"&csv=true";
		location.href = dest;
		return;
	}
	
	var savedSamples = Array();
	var can_continue = false;
	
	if ( rtype == "Gene Expression Sample" || rtype == "Copy Number" ) 
		can_continue = checkIfSamplesSelected( savedSamples );
	else
		can_continue = currentTmpSamplesCount > 0;			// for clinical view, the records selected are stored in this variable.
	
	if ( can_continue ) {
		try	{
			if(savedSamples.length>0)	// only for Gene Expression Sample View or Copy Number View
				DynamicReport.saveSamplesForExcelExport(savedSamples.join(","), qname, rtype, excel_export_cb);
			else
				DynamicReport.saveSamplesForExcelExport("", qname, rtype, excel_export_cb);	 // for clinical view, the selected records are already stored in session.
		}
		catch(e){alert("list did not save successfully");}
	}
	else
	{
		alert("You must select at least one sample or select Check All.");
		return false;
	}
	
}

function excel_export_cb(qnameAndrType) {
	var qnameAndrTypeArray = qnameAndrType.split(",");
	var dest = "runReport.do?method=exportToExcelForGeneView&queryName="+ escape(qnameAndrTypeArray[0])+"&reportType="+escape(qnameAndrTypeArray[1])+"&csv=true&checkedAll=true";
	location.href = dest;
}
function igvEscape(qname)	{
	// For Preview Results, no checkboxes are shown for Samples. So, Export All by default.
	if ( qname === 'previewResults' ) {
		var dest = "runReport.do?method=runIGVReport&queryName="+ escape(qname)+"&igv=true";
		location.href = dest;
		return;
	}
	
	var savedSamples = Array();
	var can_continue = false;
	var rtype = "Copy Number";
		can_continue = checkIfSamplesSelected( savedSamples );
	
	if ( can_continue ) {
		try	{
			if(savedSamples.length>0)	// only for Gene Expression Sample View or Copy Number View
				DynamicReport.saveSamplesForExcelExport(savedSamples.join(","), qname, rtype, igv_export_cb);
			else
				DynamicReport.saveSamplesForExcelExport("", qname, rtype, igv_export_cb);	 // for clinical view, the selected records are already stored in session.
		}
		catch(e){alert("list did not save successfully");}
	}
	else
	{
		alert("You must select at least one sample or select Check All.");
		return false;
	}
	
}

function igv_export_cb(qnameAndrType) {
	var qnameAndrTypeArray = qnameAndrType.split(",");
	var dest = "runReport.do?method=runIGVReport&queryName="+ escape(qnameAndrTypeArray[0])+"&reportType="+escape(qnameAndrTypeArray[1])+"&checkedAll=true"+"&igv=true";
//	var dest = "runReport.do?method=runGeneViewReport&queryName="+ escape(qname)+"&igv=true";
	location.href = dest;
}
function runFindingCSV(key)	{
	if( currentTmpReportersCount == 0) {
		alert("You must select at least one reporter.");
		return false;
	}
	
	var dest = "jsp/csvWrapper.jsp?key="+ escape(key)+"&csv=true";
	location.href = dest;
}
function doShowAllValues(q, state)	{
	if(!state)	{
		var oldq = "";
		var tmp = "";
		if(q.indexOf(" filter report")!= -1)	{
			//we have a filter report
			tmp = q.indexOf(" filter report");
		}
		else if(q.indexOf(" show all values report") != -1)	{
			//we have a show all vals report
			tmp = q.indexOf(" show all values report");

		}
		oldq = q.substr(0, tmp);
		//alert("'"+oldq+"'");
		if(oldq!="")				
			location.href='runReport.do?method=runGeneViewReport&queryName='+oldq;
	}
}

function goPage(p)	{
	try	{
	 	document.forms['paginate'].filter_value2.value = p;
	 }
	 catch(er)	{
	 	document.forms['paginate'].p_page.value = p;
	 }
	 	document.forms['paginate'].submit();
}
function goPageChangeStep(p, s)	{
	if(s != '')	{
		if(document.forms['paginate'].filter_value2)
		 	document.forms['paginate'].filter_value2.value = 0; 
		if(document.forms['paginate'].filter_value3)
		 	document.forms['paginate'].filter_value3.value = s; 
		
		if(document.forms['paginate'].p_page)
			document.forms['paginate'].p_page.value = 0; 
		if(document.forms['paginate'].p_step)
		 	document.forms['paginate'].p_step.value = s; 

	 	document.forms['paginate'].submit(); 
	}
}

function checkForBack()	{
/*
	if(history.length > 1)	{
		document.write("<a style=\"margin-left:10px\" href=\"#\" onclick=\"javascript:history.back();\">[Back]</a>");
	}
*/	
}

function goQueryDetails(str)	{
	if(str!="N/A" && str!= "")	{
		var newstr;
		newstr = str.replace(/\{/g, "<").replace(/\}/g, ">");
		document.write(newstr);
	}
}

function switchViews(view, sample)	{
	document.switchViewsForm.reportView.value = view;
	document.switchViewsForm.samples.value = sample;
	document.switchViewsForm.submit();
}

function switchViewMultiSamples(view, samplesArray)	{
	document.switchViewsForm.reportView.value = view;
	var f = document.switchViewsForm;
	
	for(var i=0; i<samplesArray.length; i++)	{
		var hid = document.createElement("input");
		hid.setAttribute("type", "hidden");
		hid.setAttribute("name", "samples");
		hid.setAttribute("value", samplesArray[i]);
		f.appendChild(hid);
	}

	document.switchViewsForm.submit();
}


//for scientific notation conversion
//http://www.actionscript.org/forums/showthread.php3?s=&threadid=2652

//format a number into specified number of decimal places
Math.formatDecimals = function (num, digits) {
        //if no decimal places needed, we're done
        if (digits <= 0) {
                return Math.round(num); 
        } 
        //round the number to specified decimal places
        //e.g. 12.3456 to 3 digits (12.346) -> mult. by 1000, round, div. by 1000
        var tenToPower = Math.pow(10, digits);
        var cropped = String(Math.round(num * tenToPower) / tenToPower);

        //add decimal point if missing
        if (cropped.indexOf(".") == -1) {
                cropped += ".0";  //e.g. 5 -> 5.0 (at least one zero is needed)
        }

        //finally, force correct number of zeroes; add some if necessary
        var halves = cropped.split("."); //grab numbers to the right of the decimal
        //compare digits in right half of string to digits wanted
        var zerosNeeded = digits - halves[1].length; //number of zeros to add
        for (var i=1; i <= zerosNeeded; i++) {
                cropped += "0";
        }
        return(cropped);
} //Robert Penner May 2001 - source@robertpenner.com


//convert any number to scientific notation with specified significant digits
//e.g. .012345 -> 1.2345e-2 -- but 6.34e0 is displayed "6.34"
//requires function formatDecimals()
Math.toScientific = function (num, sigDigs) {
        //deal with messy input values
        num = Number(parseFloat(num)); //try to convert to a number
        //alert(num);
        if (isNaN(num)) return num; //garbage in, NaN out

        //find exponent using logarithm
        //e.g. log10(150) = 2.18 -- round down to 2 using floor()
        var exponent = Math.floor(Math.log(Math.abs(num)) / Math.LN10); 
        if (num == 0) exponent = 0; //handle glitch if the number is zero

        //find mantissa (e.g. "3.47" is mantissa of 3470; need to divide by 1000)
        var tenToPower = Math.pow(10, exponent);
        var mantissa = num / tenToPower;

        //force significant digits in mantissa
        //e.g. 3 sig digs: 5 -> 5.00, 7.1 -> 7.10, 4.2791 -> 4.28
        mantissa = Math.formatDecimals(mantissa, sigDigs-1); //use custom function
        var output = mantissa;
        //if exponent is zero, don't include e
        if (exponent != 0) {
                output += "e" + exponent;
        }
        return(output);
} //Robert Penner May 2001 - source@robertpenner.com


function convertSci()	{
	//get all elements with id=pval and convert to # by parseFloat then back to sci for readability
	var els = document.getElementsByName("pval");
	for(var i=0; i<els.length; i++)	{
		//var tmp = parseFloat(els[i].innerHTML);
		var tmp = els[i].innerHTML;
		if(tmp.indexOf("span")!=-1 || tmp.indexOf("SPAN")!=-1)	{
			tmp = els[i].childNodes[0].innerHTML;
			els[i].childNodes[0].innerHTML = Math.toScientific(tmp, 3);
		}
		else	{
			els[i].innerHTML = Math.toScientific(tmp, 3);
		}
	}
		
}

function fixQueryDetails()	{

	if(document.getElementById("query_details"))	{
		var rExp = /\|\|\|/g;
		var qd = document.getElementById("query_details");
		var tmp = qd.innerHTML;
		qd.innerHTML = "<b>Query Details: </b><br/>" + tmp.replace(rExp, "<br/>");
	}
}

function autoCheckHighlighted(el)	{
	var theRow = document.getElementById(el);
	if(theRow)	{
		try	{
			theRow.childNodes[0].childNodes[0].checked = true;
			A_saveTmpReporter(theRow.childNodes[0].childNodes[0]);
		}
		catch(err){ 
			//alert(err);
		}
	}
}

function autoCheckHighlightedSamples(sarray)	{
	if(sarray.length>0)	{
	
		var h = $$("span.highlighted");
		//alert(h.length);
		if(h.length > 0)	{
			var tbl = h[0].parentNode.parentNode.parentNode.parentNode;
			//alert(tbl.nodeName);
			//looks for tbody? thats why the extra parentNode call?
			//var srow = tbl.childNodes[1].childNodes[2]; //tbody on down via trial and error
			//nodeName to tell the HTML elem
			var _srow = $$("tr.sampleRow");
			var srow = _srow[0];
			//alert(srow.childNodes.length);
			
			var s = "";			
			for(var i=0; i<h.length; i++)	{
				//get the cellIndex from the h.parentNode, go to the first row, get that td cell index, check it
				var ci = h[i].parentNode.cellIndex;
				//alert(ci);
				for(var t=0; t<srow.childNodes.length; t++)	{
					if(srow.childNodes[t].nodeName=="TD" && srow.childNodes[t].cellIndex == (ci-1))	{
						s = srow.childNodes[t];
						break;
					}
				}
				if(s!="")	{
					//why was this 1 not 0?
					s.childNodes[1].selected = true;			
					s.childNodes[1].checked = true;			
				}
			}
		}
	}

}


function processHighlightForm(key, ac)	{
	var k = encodeURIComponent(key);
	
	var rn = Math.floor(Math.random()*11)
	
	document.highlight_form.action = "testReport.do?key="+k+"&r="+rn; 
	if(ac == "unhighlight")	{
		document.highlight_form.p_highlight.value="";
	}
	document.highlight_form.submit();
}

function processFilterForm(key, ac)	{
	var k = encodeURIComponent(key);
	
	var rn = Math.floor(Math.random()*11)
	
	document.pval_filter_form.action = "testReport.do?key="+k+"&r="+rn; 
	if(ac == "reset")	{
		document.pval_filter_form.p_pval_filter_value.value='';
	}
	document.pval_filter_form.submit();

}

function prepQuickClinical()	{
	var s = Array();
	
	//console.log($$("tr.sampleRow td input.checkorradio").length);
	$$("tr.sampleRow td input.checkorradio").each( function(e)	{
		//console.log(e.value);
		if(e.value != "")
			s.push(e.value);
	});
	/*
	console.log("++++++++++++++++++++++");
	console.log(document.prbSamples.samples);
	for(var i=0; i<document.prbSamples.samples.length; i++)	{
		console.log(document.prbSamples.samples[i].value);
		if(document.prbSamples.samples[i].value && document.prbSamples.samples[i].value.length>0)
			s.push(document.prbSamples.samples[i].value);
	}
	*/
	
	//alert(s);
	switchViewMultiSamples("CLINICAL", s);

/*
	//this is the quick clinical way
	var f = document.getElementById("quickClinicalWrapper");
	
	//quickly clear the node, so we dont get duplicate elements when the back button is used
	while(f.firstChild) f.removeChild(f.firstChild);
	
	if(!f)	{ return; }
	//set up the form
	f.setAttribute("method", "post");
	f.setAttribute("action", "quickClinical.do");
	f.setAttribute("name", "quickClinicalWrapper");
	
	for(var i=0; i<pendingSamples.length; i++)	{
		var hid = document.createElement("input");
		hid.setAttribute("type", "hidden");
		hid.setAttribute("name", "sampleList");
		hid.setAttribute("value", pendingSamples[i]);
		f.appendChild(hid);
	}
	
	f.submit();
*/
}