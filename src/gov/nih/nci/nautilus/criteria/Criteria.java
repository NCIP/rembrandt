package gov.nih.nci.nautilus.criteria;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:45:33 PM
 * To change this template use Options | File Templates.
 */
abstract public class Criteria {
    abstract public boolean isValid();

    //TODO: The followig method checks if a given Criteria is empty
	    public boolean isEmpty() {
			try {
		
				String currObjectName = this.getClass().getName();
				Class currClass = Class.forName(currObjectName);
				Method[] allPublicmethods = currClass.getMethods();
				
				for (int i = 0; i < allPublicmethods.length; i++) {
					Method currMethod = allPublicmethods[i];
					String currMethodString = currMethod.getName();
					if ((currMethodString.toUpperCase().startsWith("GET")) 
						&& (currMethod.getModifiers() == Modifier.PUBLIC) 
						&& (!currMethodString.equalsIgnoreCase("getclass")))
					{
							Object thisObj = currMethod.invoke(this, null);
	
							if (thisObj != null) {
								if (Collection.class.isInstance(thisObj)){
									Collection thisCollection = (Collection) thisObj;
									if (!thisCollection.isEmpty()) {return false;}
								}
								else {return false;} 
							}
					}
				}
				
			 } catch (Throwable e) {
					e.printStackTrace();
			 }
			 return true;
		}


}
