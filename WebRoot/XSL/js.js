function stupidXSL(i, cPage)	{
	if(i == cPage)
		str = "["+(i+1)+"]&nbsp;"
	else
		str = "<a href=\"javascript:goPage('"+(i)+"');\">["+(i+1)+"]</a>&nbsp;";
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

function showHelp(help)	{
	return overlib(help, CAPTION, 'Help', CSSCLASS,TEXTFONTCLASS,'fontClass',FGCLASS,'fgClass',BGCLASS,'bgClass',CAPTIONFONTCLASS,'capfontClass', OFFSETX, -50);
}

function spawnAnnot(type, element)	{
	var winw = 800;
	var winh = 550;
	if(type == 'gene')
		spawn('http://cgap.nci.nih.gov/Genes/RunUniGeneQuery?PAGE=1&SYM=&PATH=&ORG=Hs&TERM='+escape(element),winw,winh);
	else if(type == 'reporter')
		spawn('http://genome.ucsc.edu/cgi-bin/hgTracks?clade=vertebrate&org=Human&db=hg17&position='+escape(element)+'&pix=620&hgsid=40518963&Submit=submit',winw,winh);
}

function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
}


