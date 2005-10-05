package gov.nih.nci.caintegrator.security;

public interface GroupInterface {

	/**
	 * This is the brief description of the group.
	 */
	public abstract String getGroupDesc();

	/**
	 * It is the unique id by which it is identified within an application.
	 */
	public abstract Long getGroupId();

	/**
	 * It is the logical name for the group.
	 */
	public abstract String getGroupName();

	/**
	 * The date when the group information was updated
	 */
	public abstract java.util.Date getUpdateDate();

	/**
	 * A collection of User objects. Indicates which users belongs to this group.
	 */
	public abstract java.util.Set getUsers();

	/**
	 * This is the brief description of the group.
	 * @param newVal
	 * 
	 */
	public abstract void setGroupDesc(String newVal);

	/**
	 * It is the unique id by which it is identified within an application.
	 * @param newVal
	 * 
	 */
	public abstract void setGroupId(Long newVal);

	/**
	 * It is the logical name for the group.
	 * @param newVal
	 * 
	 */
	public abstract void setGroupName(String newVal);

	/**
	 * The date when the group information was updated
	 * @param newVal
	 * 
	 */
	public abstract void setUpdateDate(java.util.Date newVal);

	/**
	 * A collection of User objects. Indicates which users belongs to this group.
	 * @param newVal
	 * 
	 */
	public abstract void setUsers(java.util.Set newVal);

}