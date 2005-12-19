package gov.nih.nci.rembrandt.util;

import gov.nih.nci.caintegrator.dto.de.DomainElement;

public class DEUtils {

	
	public static String checkNV(DomainElement element)	{
		String str = "--";
		if(element != null  && element.getValue() != null){
			str =  element.getValue().toString();
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
