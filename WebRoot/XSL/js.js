hideLoadingMessage();
 
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
		document.prbSamples.prbQueryName.value = document.getElementById('tmp_prb_queryName').value;
		var can_continue = false;
		
		if(document.prbSamples.samples.length > 1)	{
			for (i = 0; i < document.prbSamples.samples.length; i++)	{
				if(document.prbSamples.samples[i].checked == true)
					can_continue = true;
			}
		}
		else	{
			if(document.prbSamples.samples.checked == true)
				can_continue = true;
		}
		if(can_continue)	{
			//alert("cool");
			document.prbSamples.submit();
		}
		else	
			alert("You must select at least one sample");
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

function spawnAnnot(type, element)	{

	var el; //either accept and object or a string
	if(element.innerHTML)
		el = element.innerHTML;
	else
		el = element;
		
	var winw = 800;
	var winh = 550;
	var page = "";
	/*
		params
		types:
		gene - comes from gene view report, or CC report, goes to CGAP always
		reporter - a generic affy reporter where we do nothing special, goes to affy always
		reporterFromGene - comes from a gene view report, goes to LPG
		cytoband - comes from a copy # report, goes to UCSC
		reporterFromCC - comes from a CC report, goes to LPG
		reporterFromGeneQS - comes from a Gene Quick Search (most complicated)
		
		elements:
		gene symbol
		reporter name
		
		hope this clears it up
		 -RCL
	
	*/
	if(type == 'gene')	{
		page = escape('http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&SYM=&PATH=&ORG=Hs&TERM=')+escape(el);
		rbtFrame(page);
	}
	else if(type == 'reporter' || type == 'reporterFromGene' || type == 'cytoband' || type == 'reporterFromCC')	{
		var annotLink = "";
		if(el.indexOf("IMAGE")!= -1 || type == 'cytoband')	{
			annotLink = "http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&pix=620&hgsid=40518963&Submit=submit&position=";
		}
		else if(type == 'reporterFromGene' || type == 'reporterFromCC')	{
			annotLink = "http://lpgws.nci.nih.gov/cgi-bin/AffyViewer.cgi?st=1&org=1&query=";
		}
		else	{
			//annotLink = "http://lpgws.nci.nih.gov/cgi-bin/AffyViewer.cgi?st=1&org=1&query=";
			annotLink = "https://www.affymetrix.com/LinkServlet?probeset=";
		}
		
		page = escape(annotLink + el);
		//alert(page);		
		//page = escape('http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&position=')+escape(el)+escape('&pix=620&hgsid=40518963&Submit=submit');
		rbtFrame(page);
	}
	else if(type == 'reporterFromGeneQS')	{
		var annotLink = "http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&ORG=Hs&SYM=&PATH=&TERM=";
		
		if(el.indexOf("IMAGE")!= -1)	{
			annotLink = "http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&pix=620&hgsid=40518963&Submit=submit&position=";
		}
		else if(/^\d+$/.test(el))	{
			//this is a llink
		}		
		else if(/^\d+_{1}a{1}\d+/.test(el))	{
			//this is a llink special - need to snip the _aXX
			el = el.substring(0, el.indexOf("_a"));
		}
		else if(/^(H{1}s{1}(\.){1})/i.test(el))	{
			//this is an acc no
		}
		else	{
			annotLink = "http://lpgws.nci.nih.gov/cgi-bin/AffyViewer.cgi?st=1&org=1&query=";
		}
		page = escape(annotLink + el);
		rbtFrame(page);
	}
	/*
	else if(type == 'reporterFromGene') {
		var annotLink = "";	
		if(el.indexOf("IMAGE")!= -1)	{
			annotLink = "http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&pix=620&hgsid=40518963&Submit=submit&position=";
		}
		else	{
			annotLink = "http://lpgws.nci.nih.gov/cgi-bin/AffyViewer.cgi?st=1&org=1&query=";
		}
		page = escape(annotLink + el);
		rbtFrame(page);
	}
	*/
	
}

function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");

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

function stupidXSLEscape(qname)	{
	var dest = "runReport.do?method=runGeneViewReport&queryName="+ escape(qname)+"&csv=true";
	//alert(dest);
	location.href = dest;
}

function runFindingCSV(key)	{
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

	if(history.length > 1)	{
		document.write("<a style=\"margin-left:10px\" href=\"#\" onclick=\"javascript:history.back();\">[Back]</a>");
	}	
}

function goQueryDetails(str)	{
	var newstr;
	newstr = str.replace(/\{/g, "<").replace(/\}/g, ">");
	document.write(newstr);
}

function switchViews(view, sample)	{
	document.switchViewsForm.reportView.value = view;
	document.switchViewsForm.samples.value = sample;
	document.switchViewsForm.submit();
	

}