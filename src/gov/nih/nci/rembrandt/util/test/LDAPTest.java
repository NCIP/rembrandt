package gov.nih.nci.rembrandt.util.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] attributeIDs = { "cn", "" };
		String searchFilter = "(" + "cn" + "=" + "georgeda" + "*)";

		try {
			Hashtable environment = new Hashtable();
			environment.clear();
			environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			environment.put(Context.PROVIDER_URL, "ldaps://ncids2b.nci.nih.gov:636");
			environment.put(Context.SECURITY_AUTHENTICATION, "simple");
			environment.put(Context.SECURITY_PROTOCOL, "ssl");
			DirContext dirContext = new InitialDirContext(environment);

			SearchControls searchControls = new SearchControls();
			searchControls.setReturningAttributes(null);
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String fullyDistinguishedName = null;
			NamingEnumeration searchEnum = dirContext.search(
					"ou=nci,o=nih",
					searchFilter, searchControls);
			dirContext.close();

			while (searchEnum.hasMore()) {
				SearchResult searchResult = (SearchResult) searchEnum.next();
				fullyDistinguishedName = searchResult.getName()	+ "," + "ou=nci,o=nih";
				System.out.println("fullyDistinguishedName=" + fullyDistinguishedName);
				System.out.println("Attributes=" + searchResult.getAttributes());
			}
		} catch (Exception ex) {
			throw new RuntimeException( "User Name doesnot exists" ,ex);
		}

	}

}
