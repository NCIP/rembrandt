package gov.nih.nci.nautilus.security;

import java.util.Date;

public interface RoleInterface {

	/**
	 * Indicates if the role is active or not.
	 */
	public abstract byte getActive_flag();

	/**
	 * The application to which the role belongs
	 
	 public Application getApplication(){
	 return application;
	 }
	 */
	/**
	 * This attribute describes the role in detail.
	 */
	public abstract String getDesc();

	/**
	 * This is the unique id by which this role can be identied  for an application.
	 */
	public abstract Long getId();

	/**
	 * This is the name of the role. This can be any user friendly name to address
	 * business needs.
	 */
	public abstract String getName();

	/**
	 * A collection of Privilege objects. Indicates which privileges belong to this
	 * role.
	 */
	public abstract java.util.Set getPrivileges();

	/**
	 * The date when the role information was last updated
	 */
	public abstract Date getUpdateDate();

	/**
	 * Indicates if the role is active or not.
	 * @param newVal
	 * 
	 */
	public abstract void setActive_flag(byte newVal);

	/**
	 * The application to which the role belongs
	 * @param newVal
	 * 
	 
	 public void setApplication(Application newVal){
	 application = newVal;
	 }
	 */
	/**
	 * This attribute describes the role in detail.
	 * @param newVal
	 * 
	 */
	public abstract void setDesc(String newVal);

	/**
	 * This is the unique id by which this role can be identied  for an application.
	 * @param newVal
	 * 
	 */
	public abstract void setId(Long newVal);

	/**
	 * This is the name of the role. This can be any user friendly name to address
	 * business needs.
	 * @param newVal
	 * 
	 */
	public abstract void setName(String newVal);

	/**
	 * A collection of Privilege objects. Indicates which privileges belong to this
	 * role.
	 * @param newVal
	 * 
	 */
	public abstract void setPrivileges(java.util.Set newVal);

	/**
	 * The date when the role information was last updated
	 * @param newVal
	 * 
	 */
	public abstract void setUpdateDate(Date newVal);

}