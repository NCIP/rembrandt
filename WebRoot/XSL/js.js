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
		for (i = 0; i < field.length; i++)
			field[i].checked = true ;
			
			var c;
			c = document.getElementsByTagName("input");
			for(i=0; i<c.length; i++)	{
				if(c[i].type == 'checkbox' && c[i].id == 'grpcheck')
					c[i].checked = true;
			}

	}
		
	function uncheckAll(field)	{
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;

			var c;
			c = document.getElementsByTagName("input");
			for(i=0; i<c.length; i++)	{
				if(c[i].type == 'checkbox' && c[i].id == 'grpcheck')
					c[i].checked = false;
			}
	}
		
	function checkById(field, idx)	{
		for (i = 0; i < field.length; i++)	{
			if(field[i].id == idx)	{
				field[i].checked = true;
				//field[i].checked = !field[i].checked;
			}
		}
	}
		
	function uncheckById(field, idx)	{
		for (i = 0; i < field.length; i++)	{
			if(field[i].id == idx)
				field[i].checked = false;
		}
	}

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
		//alert(document.prbSamples.prbQueryName.value);
		document.prbSamples.submit();
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

	var winw = 800;
	var winh = 550;
	var page = "";
	if(type == 'gene')	{
		page = escape('http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&SYM=&PATH=&ORG=Hs&TERM=')+escape(element);
		//spawn(page,winw,winh);
		//alert(page);
		rbtFrame(page);
	}
	else if(type == 'reporter')	{
		page = escape('http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&position=')+escape(element)+escape('&pix=620&hgsid=40518963&Submit=submit');
		//spawn(page,winw,winh);
		//alert(page);
		rbtFrame(page);
	}
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

function showCNumberFilter(v, id)	{
	if(v == 'copy number')	{
		displayDiv(id, "block");
		displayDiv('fb', "none");
	}
	else	{
		if(document.getElementById(id))	{
			displayDiv(id, "none");
		}
		displayDiv('fb', "inline");
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
