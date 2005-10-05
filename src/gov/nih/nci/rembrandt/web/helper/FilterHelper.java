/*
 * Created on Mar 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.rembrandt.web.helper;

import java.util.ArrayList;

/**
 * @author landyr
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FilterHelper {

	/**
	 * 
	 */
	public FilterHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * 	This method checks the filter criteria and returns a boolean based on a scenario
	 *  true = process this element (gene/reporter)
	 *  false = dont process this element, as it has been filtered off the report
	 * 	params: filter_element = gene | cytoband | reporter
	 *  		f_element = string version of filter_element for comparison
	 * 			name = name of the gene | reporter | cytoband for comparison
	 * 			filter_type = show | hide
	 * 			filter_string = ArrayList of all gene | reporter | cytoband to show | hide 
	 */
	public static boolean checkFilter(String filter_element, String f_element, String name, String filter_type, ArrayList filter_string)	{
			
		name = name.toUpperCase().trim();
		if(filter_type.equals("hide") && (!filter_element.equals(f_element) || (filter_element.equals(f_element) && !filter_string.contains(name)))) 
			return true;
		else if(filter_type.equals("show") && (!filter_element.equals(f_element) || (filter_element.equals(f_element) && filter_string.contains(name))))
			return true;	
		else if(!filter_type.equals("show") && !filter_type.equals("hide"))
			return true;
		else
			return false;
	}

}
