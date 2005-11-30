package gov.nih.nci.rembrandt.util;

import gov.nih.nci.caintegrator.dto.de.DomainElement;

public class DEUtils {

	
	public static String checkNV(DomainElement element)	{
		String str = "--";
		try	{
			str = element.getValue().toString();
		}
		catch(Exception e){
			//
		}
		return str;
	}
	
	public static String checkNull(Object element)	{
		String str = "--";
		if(element != null) {
			str = element.toString();
		  }
		
		return str;
	}
}
