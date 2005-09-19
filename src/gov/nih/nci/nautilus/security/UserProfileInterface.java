package gov.nih.nci.nautilus.security;

import java.util.Date;

public interface UserProfileInterface {

	/**
	 * The name of the department that this user belongs to.
	 */
	public abstract String getDepartment();

	/**
	 * Email id for this user.
	 */
	public abstract String getEmailId();

	/**
	 * It is the end date for this user.
	 */
	public abstract Date getEndDate();

	/**
	 * The first name of the user
	 */
	public abstract String getFirstName();

	/**
	 * This attribute tells which groups does this user belong to.
	 */
	public abstract java.util.Set getGroups();

	/**
	 * The last name of the user
	 */
	public abstract String getLastName();

	/**
	 * This string is used for login into the application.
	 */
	public abstract String getLoginName();

	/**
	 * The name of the organization that this user belongs to.
	 */
	public abstract String getOrganization();

	/**
	 * The password used to login into the application
	 */
	public abstract String getPassword();

	/**
	 * This is the work phone of the user.
	 */
	public abstract String getPhoneNumber();

	/**
	 * It is the start date for this user.
	 */
	public abstract Date getStartDate();

	/**
	 * The name of the title for this user.
	 */
	public abstract String getTitle();

	/**
	 * It is the date when the user information was last updated
	 */
	public abstract Date getUpdateDate();

	/**
	 * This a unique id to identify a user within an application.
	 */
	public abstract Long getUserId();

	/**
	 * The name of the department that this user belongs to.
	 * @param newVal
	 * 
	 */
	public abstract void setDepartment(String newVal);

	/**
	 * Email id for this user.
	 * @param newVal
	 * 
	 */
	public abstract void setEmailId(String newVal);

	/**
	 * It is the end date for this user.
	 * @param newVal
	 * 
	 */
	public abstract void setEndDate(Date newVal);

	/**
	 * The first name of the user
	 * @param newVal
	 * 
	 */
	public abstract void setFirstName(String newVal);

	/**
	 * This attribute tells which groups does this user belong to.
	 * @param newVal
	 * 
	 */
	public abstract void setGroups(java.util.Set newVal);

	/**
	 * The last name of the user
	 * @param newVal
	 * 
	 */
	public abstract void setLastName(String newVal);

	/**
	 * This string is used for login into the application.
	 * @param newVal
	 * 
	 */
	public abstract void setLoginName(String newVal);

	/**
	 * The name of the organization that this user belongs to.
	 * @param newVal
	 * 
	 */
	public abstract void setOrganization(String newVal);

	/**
	 * The password used to login into the application
	 * @param newVal
	 * 
	 */
	public abstract void setPassword(String newVal);

	/**
	 * This is the work phone of the user.
	 * @param newVal
	 * 
	 */
	public abstract void setPhoneNumber(String newVal);

	/**
	 * It is the start date for this user.
	 * @param newVal
	 * 
	 */
	public abstract void setStartDate(Date newVal);

	/**
	 * The name of the title for this user.
	 * @param newVal
	 * 
	 */
	public abstract void setTitle(String newVal);

	/**
	 * It is the date when the user information was last updated
	 * @param newVal
	 * 
	 */
	public abstract void setUpdateDate(Date newVal);

	/**
	 * This a unique id to identify a user within an application.
	 * @param newVal
	 * 
	 */
	public abstract void setUserId(Long newVal);

}