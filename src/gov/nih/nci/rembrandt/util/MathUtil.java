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
	public static Double getLog2(Double value) {
		if (value != null) {
			return Math.log(value) / Math.log(2);
		}
		return null;
	}

	/**
	 * The method returns you the antilogarithm of log base 2
	 * 
	 */
	public static Double getAntiLog2(Double value) {
		// Calculate the antilogarithm of log base 2
		if (value != null) {
			return Math.pow(2.0, value);
		}
		return null;
	}
}
