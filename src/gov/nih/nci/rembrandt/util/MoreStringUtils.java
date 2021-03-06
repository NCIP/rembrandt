/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.util;

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


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class MoreStringUtils extends StringUtils{
	/**
	 * The array of chars represents the illegal characters
	 * for the Unix file naming system.
	*/
	public static final String specialCharacters = "!@#$%^&*()+=[]\';,/{}|\":<>?\\";

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
	
	public static String cleanJavascriptAndSpecialChars(String unallowableCharacters, String stringToClean) {
		stringToClean = stringToClean.replaceAll("alert", "");
		stringToClean = stringToClean.replaceAll("script", "");
		stringToClean = stringToClean.replaceAll("javascript", "");
		stringToClean = stringToClean.replaceAll(".html", "");
		stringToClean = stringToClean.replaceAll("iframe", "");
		stringToClean = stringToClean.replaceAll(".net", "");
		
		return cleanString(unallowableCharacters,stringToClean);
	}
	
	public static String cleanJavascript(String stringToClean) {
		stringToClean = stringToClean.replaceAll("alert", "");
		stringToClean = stringToClean.replaceAll("script", "");
		stringToClean = stringToClean.replaceAll("javascript", "");
		stringToClean = stringToClean.replaceAll(".html", "");
		stringToClean = stringToClean.replaceAll("iframe", "");
		stringToClean = stringToClean.replaceAll(".net", "");
		
		return stringToClean;
	}
	
	public static boolean isURLSafe(String s){
		int index = -1;
		char[] sChar = s.toCharArray();
        for (int i = 0; i < sChar.length; i++)
        {
            index = specialCharacters.indexOf(sChar[i]);

            if (index != -1)
            {
                return false;
            }
        }
        return true;
	}
	
	/**
	 * Pass string through a white list for rembrandt before checking other special chars
	 * @param unallowableCharacters
	 * @param stringToClean
	 * @return
	 */
	public static String checkWhiteListBeforeCleaningJavascriptAndSpecialChars(
			String unallowableCharacters, String stringToClean) {
		if (stringToClean == null || stringToClean.length() == 0)
			return stringToClean;
		
		if (stringToClean.startsWith("http") && stringToClean.contains("nih.gov"))
			return stringToClean;
		
		if (stringToClean.startsWith("pcaApplet"))
			return stringToClean;
		
		if (stringToClean.startsWith("testApplet"))
			return stringToClean;
		
		if (stringToClean.startsWith("/rembrandt/popGraph"))
			return stringToClean;
		
		return cleanJavascriptAndSpecialChars(unallowableCharacters, stringToClean);
	}
	
	/**
	 * Replace token like "{0}" in action errors (configured in ApplicationResources.properties)
	 * with given param at given position
	 * 
	 * @param error error message from ApplicationResources.properties
	 * @param param value to replace token. Same token could repeat in error messages
	 * @param paramIndex param number (0, 1, 2...) of the token to be replaced
	 * @return if success, error with param value inserted <br>
	 * 		   if error, original error
	 */
	public static String insertParameterToErrorString(String error, String param, int paramIndex)  {
		if (error == null || error.length() == 0) 
			return error;
		
		if (paramIndex < 0)
			return error;
		
		//patttern: "{n}" with optional spaces between "{" or "}" and the number
		String regEx = "\\{ ?" + String.valueOf(paramIndex) + " ?\\}";
		
		return error.replaceAll(regEx, param);
	}
	
}












