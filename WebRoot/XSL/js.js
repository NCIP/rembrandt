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

