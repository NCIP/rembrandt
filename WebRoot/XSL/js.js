function stupidXSL(i, cPage)	{
	if(i == cPage)
		str = "["+(i+1)+"]&nbsp;"
	else
		str = "<a href=\"javascript:goPage('"+(i)+"');\">["+(i+1)+"]</a>&nbsp;";
	document.write(str);

}