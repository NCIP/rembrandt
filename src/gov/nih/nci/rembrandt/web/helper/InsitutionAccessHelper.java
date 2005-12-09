/**
 * 
 */
package gov.nih.nci.rembrandt.web.helper;

import gov.nih.nci.caintegrator.dto.critieria.InstitutionCriteria;
import gov.nih.nci.caintegrator.security.UserCredentials;
import gov.nih.nci.rembrandt.util.RembrandtConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sahnih
 * This class provides a convinence way to get the users acess levels 
 * and populates the InsitutionCriteria
 *
 */
public class InsitutionAccessHelper {
    public static InstitutionCriteria getInsititutionCriteria(HttpServletRequest request){
        InstitutionCriteria institutionCriteria = new InstitutionCriteria();
        //Check user credentials and constrain query by Institutions
        if(request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS)!=null){
        	UserCredentials credentials = (UserCredentials) request.getSession().getAttribute(RembrandtConstants.USER_CREDENTIALS);
            institutionCriteria.setInstitutions(credentials.getInstitutes());
        }
        return institutionCriteria;
    }

}
