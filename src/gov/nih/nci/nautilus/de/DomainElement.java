package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * This abstract class encapsulates the properties of an caintergator 
 * DomainElement object, it is a parent class for all data element objects.
 *  
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2003
 * Time: 3:06:10 PM
 * To change this template use Options | File Templates.
 */
abstract public class DomainElement {
   
   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************	
   
  /**
	* the object value, it repesents generic value for data element
    */
   protected Object value;
   
  /**
    * Sets the value for all <code>subclass</code> objects
    * @param object the value    
	*/  
   abstract public void setValue(Object obj) throws Exception;
   
  /**
    * Returns the generic value for all subclass obects.
    * @return the generic value for all <code>subclass</code> objects
    */	
   public Object getValue() {
      return value;
    }

  
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************
  
  
  /**
    * Initializes a newly created <code>DomainElement</code> object so that it represents an DomainElement.
    */
   protected DomainElement(Object value) {
       if(value != null){
        this.value = value;
		}
    }
	
   
}


