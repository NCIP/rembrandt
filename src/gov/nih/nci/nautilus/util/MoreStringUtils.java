package gov.nih.nci.nautilus.util;

import org.apache.commons.lang.StringUtils;

/**
 * A utility for various string operations we want to perform. Similar to 
 * org.apache.commons.lang.StringUtils, though this has methods that were not
 * found in that class
 *  
 * @author BauerD
 * Mar 22, 2005
 * 
 */
public class MoreStringUtils extends StringUtils{
	/**
	 * This method will take a string of characters that you do not want to
	 * appear in your string.  It will remove the unallowed characters and then
	 * return to you the string as it would be without those characters.  For
	 * instance if you passed ":" as the unallowed characters, and "I a:m ::Cle:an"
	 * is the string you passed, you would end up with "I am Clean".  It does 
	 * not replace the characters it removes them.
	 * 
	 * @param unallowableCharString --give me a string of all the chars you don't want in your
	 * final string.
	 * @param stringToClean  --this is the string that you want cleaned
	 * @return  your new string
	 */
	public static String cleanString(String unallowableCharacters, String stringToClean) {
		if(unallowableCharacters!=null && stringToClean!=null) {
			char[] filterString = unallowableCharacters.toCharArray();
			filterString = stringToClean.toCharArray();
			stringToClean="";
			for(int i = 0; i<filterString.length;i++) {
				if(unallowableCharacters.indexOf(filterString[i])==-1){
					stringToClean = stringToClean.concat(Character.toString(filterString[i]));
				}
			}
		}
		return stringToClean;		
	}
	/**
	 * This method will take a char[] of characters that you do not want to
	 * appear in your string.  It will remove the unallowed characters and then
	 * return to you the string as it would be without those characters.  For
	 * instance if you passed ':' as the unallowed characters, and "I a:m ::Cle:an"
	 * is the string you passed, you would end up with "I am Clean".  
	 * 
	 * Important:It does not replace the characters it removes them.
	 * 
	 * @param unallowableCharString --give me a string of all the chars you don't want in your
	 * final string.
	 * @param stringToCheck  --this is the string that you want cleaned
	 * @return  your new string
	 */
	public static String cleanString(char[] unallowableCharArray, String stringToClean) {
		return cleanString(new String(unallowableCharArray), stringToClean);
	}
}
