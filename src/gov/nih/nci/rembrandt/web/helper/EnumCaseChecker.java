package gov.nih.nci.rembrandt.web.helper;
/**
 * This class was written to deal with a bug found when running on the dev
 * server (Linux, JBoss 4, etc, etc) that changed the case of the parameter
 * to all lower case.  Thus when trying to use the Enum.valueOf(String) method
 * an exception would be thrown.  For instance, in the case of the Enum 
 * DistanceMatrixType we have two types, "Correlation" and "Euclidian", these
 * values are used to create the value objects for selection in the UI.  But
 * when selected the string returned would come back "correltation" or 
 * "euclidian", thus breaking the hopes that valueOf would return a valid
 * Enumeration
 * 
 * It allows the user to avoid a messy Switch statement where they would need to 
 * know in advance all possible Enum Types.
 * 
 * @author BauerD, HarrisM
 *
 */
public class EnumCaseChecker {
	/**
	 * This helper method will return the exact Enum type name if a match, 
	 * ignoring the case of the value, is found between the passed value
	 * and the array of Enumerations.
	 * 
	 * IMPORTANT!
	 * If no match can be found a NULL String will be returned.
	 * 
	 * @param value
	 * @param possibleValues
	 * @return
	 */
	public static String getEnumTypeName(String value, Enum[] possibleValues) {
		String myString = null;
		for(Enum myType: possibleValues) {
        	if(value.equalsIgnoreCase(myType.name())) {
        		myString = myType.name();
        		break;
        	}
        }
		return myString;
	}
}
