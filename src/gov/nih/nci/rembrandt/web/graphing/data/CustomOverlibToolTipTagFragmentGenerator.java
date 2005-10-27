/**
 * 
 */
package gov.nih.nci.rembrandt.web.graphing.data;

import org.jfree.chart.imagemap.ToolTipTagFragmentGenerator;

/**
 * @author landyr
 *
 */
public class CustomOverlibToolTipTagFragmentGenerator implements ToolTipTagFragmentGenerator{
	public String generateToolTipFragment(String toolTipText) {
        return " onMouseOver=\"return overlib('" + toolTipText 
            + "', CAPTION, 'Additional Info', FGCOLOR, '#FFFFFF', BGCOLOR, '#000000', WIDTH, 150, HEIGHT, 25);\" onMouseOut=\"return nd();\"";
    }
}
