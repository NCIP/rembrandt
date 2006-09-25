/**
 * 
 */
package gov.nih.nci.rembrandt.util;

/**
 * @author sahnih
 *
 */

public class MathUtil {
	/**
	 * The method returns you the log2 value
	 *
	 */	
public static Double getLog2(Double value){
	if(value != null){
		return Math.log(value)/Math.log(2);
	}
	return null;
}
}
