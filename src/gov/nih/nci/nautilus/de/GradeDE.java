package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;


/**
 * This  class encapsulates the properties of an caintergator 
 * GradeDE object.
 *  
 * Dana Zhang
 * Date: August 12, 2004 
 * Version 1.0
 */
public class GradeDE extends DomainElement{
   
    
  // ****************************************************
  //                   CONSTRUCTOR(S)
  // ****************************************************

     public GradeDE() {
        super();
    }
	
   /**
    * Initializes a newly created <code>GradeDE</code> object so that it represents an GradeDE.
    */
    public GradeDE(String grade) {
        super(grade);
    }
	
   String grade_desc = null;

  /**
    * Sets the value for this <code>GradeDE</code> object
    * @param object the value    
	*/  	
    public void setValue(Object obj) throws Exception {
        if (! (obj instanceof String) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
        setValueObject((String)obj);
    }

  /**
    * Returns the grade for this GradeDE obect.
    * @return the grade for this <code>GradeDE</code> object
    */	
    public String getValueObject() {
        return (String) getValue();
    }

  /**
    * Sets the grade for this <code>GradeDE</code> object
    * @param grade the grade    
	*/ 
    public void setValueObject(String grade) {
	  if(grade != null){
        value = grade;
		}
    }
}
