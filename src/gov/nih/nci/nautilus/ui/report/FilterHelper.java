/*
 * Created on Mar 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.nautilus.ui.report;

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
	
	public static boolean checkFilter(String filter_element, String f_element, String name, String filter_type, ArrayList filter_string)	{
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
