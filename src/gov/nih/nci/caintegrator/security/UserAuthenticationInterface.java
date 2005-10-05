package gov.nih.nci.caintegrator.security;

import javax.security.auth.login.LoginContext;
import javax.security.auth.spi.LoginModule;

public interface UserAuthenticationInterface {

	/**
	 * This method accepts the user credentials as parameter and uses the same to authenticate the user
	 * against the registered credential providers. It creates an instance of the  {@link CSMCallbackHandler} class 
	 * and populates the same with the user credentials. It also creates a JAAS {@link LoginContext} class using the 
	 * Application Context/Name as parameter. It then calls the <code>login</code> method on the {@link LoginContext} class.
	 * The login Method then uses the registered {@link LoginModule} for the given Application Context/Name in the JAAS policy file
	 * and authenticate the user credentails. There can be more than one {@link LoginModule} class registered for the application.
	 * @throws CSException
	 * 
	 * @see gov.nih.nci.security.AuthenticationManager#login(java.lang.String, java.lang.String)
	 */
	public abstract boolean login(String userName, String password);

}