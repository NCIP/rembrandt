package gov.nih.nci.rembrandt.cache;

import java.util.Date;

import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;
import gov.nih.nci.rembrandt.web.bean.UserPreferencesBean;
/**
 * This class is intended to provide the place where all user state is to be
 * placed at log out and throughout the use of the application.
 * 
 * @author BauerD
 *
 */
public class RembrandtUserSession {
	
	private String userName;
	private Date lastLogin;
	
	public final static String QUERY_BAG = "queryBag";
	public final static String CRITERIA_BAG = "criteriaBag";
	
	private SessionQueryBag queryBag;
	private SessionCriteriaBag criteriaBag;
	
	private UserPreferencesBean userPreferences;
}
