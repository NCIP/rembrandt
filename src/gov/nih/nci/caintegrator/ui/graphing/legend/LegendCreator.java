package gov.nih.nci.caintegrator.ui.graphing.legend;

import java.awt.Color;

import org.jfree.chart.LegendItemCollection;

public class LegendCreator {

	/**
	 * creates and HTML legend and returns a string of HTML
	 * @param lic - a jFreechart LegendItemCollection
	 * @return
	 */
	public static String buildLegend(LegendItemCollection lic, String legendTitle)	{
		String html = new String();
		Color p = null;
		html = "<fieldset style='display:table;width:600; border:1px solid gray; text-align:left; padding:5px;'>";
		html += "<legend>Legend: "+ legendTitle+"</legend>";
		
		for(int i=0; i<lic.getItemCount(); i++)	{
			p = (Color) lic.get(i).getFillPaint();
			//html += "<div style='margin:10px; padding:10px;border:1px solid red;'><label style='width:30px; height:10px; background-color: "+ c2hex(p)+"; border:1px solid black;'>&nbsp;&nbsp;&nbsp;</label><b style='color: "+ c2hex(p)+"'>"+lic.get(i).getLabel()+"</b></div>\n";	
			html += "<table style=\"display:inline;\"><tr><td style=\"width:10px; height:10px; background-color: "+ c2hex(p)+"; border:1px solid black;\">&nbsp;&nbsp;&nbsp;</td><td><a style=\"text-decoration:none\" href=\"javascript:alert(\'some annotation for:"+lic.get(i).getLabel()+"\');\"><b style=\"color: "+ c2hex(p)+"\">"+lic.get(i).getLabel()+"</b></a></td></tr></table>";
		}
		html += "</fieldset>";
		
		String js="";
		//js = "<script language=\"javascript\">document.getElementById('legend').innerHTML = \""+ html +" \"; </script>";
		return html;
	}
	
	/**
	 * converts an awt.Color to a hex color string
	 * in the format: #zzzzzz for use in HTML
	 * @param c
	 * @return
	 */
    public static String c2hex(Color c) {
    	//http://rsb.info.nih.gov/ij/developer/source/index.html
		final char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    
        int i = c.getRGB();
        char[] buf7 = new char[7];
        buf7[0] = '#';
        for (int pos=6; pos>=1; pos--) {
            buf7[pos] = hexDigits[i&0xf];
            i >>>= 4;
        }
        return new String(buf7);
    }
}
