function spawn(url,winw,winh) {
  var w = window.open(url, "_blank",
      "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + 
      ",scrollbars=yes,resizable=yes");
} 

function setQuery(txt)	{
	document.forms[0].query.value = txt;
}


function moveEm()	{
	 // alert(document.forms[0].category.selectedIndex);
	  var category = document.forms[0].category.selectedIndex;
 	
	/*
	for(var w=0; w<document.forms[0].delement.options.length; w++)	{
	document.forms[0].delement.options[w] = null;
	}
	*/
		document.forms[0].delement.options.length = 1;
		//document.forms[0].operator.options.length = 1;
	
	if(category == "1")	{
		//gene ex  
		var geneEx = new Array("Gene Name", "Fold Change", "Clone ID", "GO", "Pathway", "Assay Platform", "Clone Location 5\' UTR", "Clone Location 3\' UTR"); 
		for(var t=0;t<geneEx.length; t++)	{
		  myOption = new Option();
		  myOption.text = geneEx[t];
		  document.forms[0].delement.options[document.forms[0].delement.options.length] = myOption;
		}
		
	}
	if(category == "2")	{
	
		var genomic = new Array("Gene Name", "Chrom. Aber. Status", "Chrom. No.", "Chrom. Name", "Cytoband", "Clone ID", "Chrom. Features", "SNP ID", "Assay Platform", "Allele Freq.");
		for(var z=0; z<genomic.length; z++)	{
		 genOption = new Option();
		   genOption.text = genomic[z];
		   document.forms[0].delement.options[document.forms[0].delement.options.length] = genOption;
		}
		
	}
	  
}
	  
function moveEmOp()	{
	
	var category = document.forms[0].category.selectedIndex;
	var de = document.forms[0].delement.selectedIndex;
	
	document.forms[0].operator.options.length = 1;
		
	if(category == "1" && de == "1")	{
		//gene Ex and Gene Name
		var geneExOp = new Array("=", "contains", "!=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "1" && de == "2")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("=", "<=", ">=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "2" && de == "5")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("=", "!=");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}
	
	else if(category == "2" && de == "2")	{
		//gene Ex and Fold Change
		var geneExOp = new Array("is", "is not");
		for(var a=0; a<geneExOp.length; a++)	{
			myOpOption = new Option();
			myOpOption.text = geneExOp[a];
			document.forms[0].operator.options[document.forms[0].operator.options.length] = myOpOption;
		}
	
	}

}
	 
	 
function changeBackground(num, obj) {
   tableRow = document.getElementById("row" + num);
   tableRow.style.backgroundColor = (obj.checked) ? "#A8A8A8" : "#E6E6E6";
}



function moveEm(formElement)	{	

	//selected index of the selected
	var element = formElement.name;
		//alert(element);
	
	var tt = document.forms[0].tumorType.selectedIndex;
	var ds = document.forms[0].dataSet.selectedIndex;
	var gi = document.forms[0].generatingInstitution.selectedIndex;
			
	if(element == "tumorType")	{
		//reset the dropdown for dataset	
		document.forms[0].dataSet.options.length = 1;
		//tumor type was moved, switch on tt
		switch(tt)	{
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				var dataSet = new Array("Rembrandt (GMDI)", "other");
			break;
			case 10:
				var dataSet = new Array("I-SPY", "other");
			break;
			default:
				var dataSet = new Array("");
		}
		//tumor type box has changed, so update data type
		for(var a=0; a<dataSet.length; a++)	{
			myOption = new Option();
			myOption.text = dataSet[a];
			myOption.value = dataSet[a];
			document.forms[0].dataSet.options[document.forms[0].dataSet.options.length] = myOption;
		}
		//reset generating instit as well since tumor type has changed
		document.forms[0].generatingInstitution.options.length=1;
		
	}
	else if(element == "dataSet")	{
		//reset the dropdown for gen inst	
		document.forms[0].generatingInstitution.options.length = 1;
		//data set was moved, switch on ds
		switch(ds)	{
			case 1:
				switch(tt)	{
					case 1: //brain tt
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						var genInst = new Array("all", "UCSF", "NCI", "Johns Hopkins", "MDACC", "other");
						break;
					case 10: //breast tt
						var genInst = new Array("all", "UNC", "NCI", "other");
						break;
					default:
						var genInst = new Array("all", "NCI","other");
				}
				
			break;
			default:
				var genInst = new Array("all", "NCI","other");
		}
		//data set box has changed, so update gen inst
		for(var a=0; a<genInst.length; a++)	{
			myOption = new Option();
			myOption.text = genInst[a];
			myOption.value = genInst[a];
			document.forms[0].generatingInstitution.options[document.forms[0].generatingInstitution.options.length] = myOption;
		}
		
		//default to all
		document.forms[0].generatingInstitution.selectedIndex = 1;
	}
	
}


function showName(item, box)	{
/*
	var t = item.selectedIndex;
	var r =	item.options[t].value;

	box.value=r;
*/
	var t = item.selectedIndex;
	var r = item.options[t].value;
	box.innerHTML = "<b class='message'>"+r+"</b>";
 
}

function selectToolTip(type)	{
			var theText = "Select a tumor type";
			var el = type.selectedIndex;
			var txt = type.options[type.selectedIndex].text;
			//alert(document.forms[0].tumorType.options[document.forms[0].tumorType.selectedIndex].text);
			switch(el)
			{
				case 1:
					theText = "<ol><li>Astrocytoma (WHO grade II)" +
							"<ol><li>Variants: protoplasmic, gemistocytic, fibrillary, mixed </ol> " +
							"<li>Anaplastic (malignant) astrocytoma (WHO grade III)" +
							"<li>Glioblastoma multiforme (WHO grade IV)" +
							"<li>Pilocytic astrocytoma (non-invasive, WHO grade I)" +
							"<li>Subependymal giant cell astrocytoma (non-invasive, WHO grade I)" +
							"<li>Pleomorphic xanthoastrocytoma (non-invasive, WHO grade I)</ol> ";

					break;
				case 2:
					theText = "<ol><li>Oligodendroglioma (WHO grade II) " +
							"<li>Anaplastic (malignant) oligodendroglioma (WHO grade III) </ol>";
					break;
				case 3:
					theText = "<ol><li>Ependymoma (WHO grade II) " +
						"<li>Anaplastic ependymoma (WHO grade III) " +
						"<li>Myxopapillary ependymoma " +
						"<li>Subependymoma (WHO grade I) </ol>";
					break;
				case 4:
					theText = "<ol><li>Mixed oligoastrocytoma (WHO grade II) " +
							"<li>Anaplastic (malignant) oligoastrocytoma (WHO grade III) " +
							"<li>Others (e.g. ependymo-astrocytomas) </ol>";
					break;
				case 5:
					theText = "<ol><li>Polar spongioblastoma (WHO grade IV) " +
						"<li>Astroblastoma (WHO grade IV) " +
						"<li>Gliomatosis cerebri (WHO grade IV) </ol>";
					break;
				case 6:
					theText = "<ol><li>Choroid plexus papilloma " +
						"<li>Choroid plexus carcinoma (anaplastic choroid plexus papilloma) </ol>";
					break;
				case 7:
					theText = "<ol><li>Gangliocytoma " +
						"<li>Dysplastic gangliocytoma of cerebellum (Lhermitte-Duclos)  " +
						"<li>Ganglioglioma  " +
						"<li>Anaplastic (malignant) ganglioglioma  " +
						"<li>Desmoplastic infantile ganglioglioma  " +
						"<li>Central neurocytoma  " +
						"<li>Dysembryoplastic neuroepithelial tumor  " +
						"<li>Olfactory neuroblastoma (esthesioneuroblastoma) </ol>";
				break;
				case 8:
					theText = "<ol><li>Pineocytoma "+
						"<li>Pineoblastoma " +
						"<li>Mixed pineocytoma/pineoblastoma </ol>";
				break;
				case 9:
					theText = "<ol><li>Medulloepithelioma "+
						"<li>Primitive neuroectodermal tumors with multipotent differentiation"+ 
						"<li>Neuroblastoma "+
						"<li>Retinoblastoma "+
						"<li>Ependymoblastoma </ol>";
				break;
				case 10:
					theText = "I-SPY Tumor sub-type";
				break;

				default:
					theText = "Select a tumor type to see its sub-types";
					break;
			
			
			}
			return overlib(theText, CAPTION, txt+' Tumor Sub-types', WIDTH, 300);
}	