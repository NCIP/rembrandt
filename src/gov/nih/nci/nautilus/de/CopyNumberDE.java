package gov.nih.nci.nautilus.de;

//caintergator classes

/**
 * This abstract class encapsulates the properties of an caintergator
 * CopyNumberDE object.
 * It contains three child/nested classes:Amplification, Deletion & Unchange
 *
 * Dana Zhang
 * Date: August 12, 2004
 * Version 1.0
 */
abstract public class CopyNumberDE extends DomainElement {

   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************

  /**
	* type of CGH
    */
    private String CGHType;

  /**
	* Amplification
    */
    public static final String AMPLIFICATION = "Amplification";

  /**
	* Deletion
    */
    public static final String DELETION= "Deletion";


 /**
   * UnchangedCopyNoUpperLimit
   * */
	public final static String UNCHANGED_COPYNUMBER_UPPER_LIMIT =  "UnchangedCopyNoUpperLimit";

 /**
   * UnchangedCopyNoDownLimit
   * */
	public final static String UNCHANGED_COPYNUMBER_DOWN_LIMIT =  "UnchangedCopyNoDownLimit";

  /**
	* Unchange: make sure later that we don't need this field.
    */
   // public static final String UNCHANGE= "Unchange";

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************


  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private CopyNumberDE(String CGHType, Float value) {
        super(value);
        this.CGHType = CGHType;
    }

  /**
	* nested child class: Amplification
    */
    public final static class Amplification extends CopyNumberDE {
        public Amplification(Float AmplificationNumber) {
            super(AMPLIFICATION, AmplificationNumber);
        }
    }

 /**
	* nested child class: Deletion
    */
    public final static class Deletion extends CopyNumberDE {
       public Deletion(Float deletionNumber) {
            super(DELETION, deletionNumber);
       }
    }

 /**
	* nested child class: UnChangedCopyNumberUpperLimit
    */
    public final static class UnChangedCopyNumberUpperLimit extends CopyNumberDE {
      public UnChangedCopyNumberUpperLimit (Float unChangedCopyNoUpperValue) {
            super(UNCHANGED_COPYNUMBER_UPPER_LIMIT, unChangedCopyNoUpperValue);
      }

    }

    /**
        * nested child class: UnChangedCopyNumberDownLimit
        */
    public final static class UnChangedCopyNumberDownLimit extends CopyNumberDE {
      public UnChangedCopyNumberDownLimit (Float unChangedCopyNoDownValue) {
            super(UNCHANGED_COPYNUMBER_DOWN_LIMIT, unChangedCopyNoDownValue);
      }

    }
  /**
	* nested child class: Unchange--make sure that we don't need this later
    */
  /*  public final static class Unchange extends CopyNumberDE {
       public Unchange(Float unchangeNumber) {
            super(UNCHANGE, unchangeNumber);
       }

    }
*/

  /**
    * Returns the CGHTpye for this CopyNumberDE obect.
    * @return the CGHTpye for this <code>CopyNumberDE</code> object
    */
	public String getCGHType() {
        return CGHType;
    }

  /**
    * Sets the value for this <code>CopyNumberDE</code> object
    * @param object the value
	*/
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Float) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Float)obj);
    }


  /**
    * Returns the CGHType for this CopyNumberDE obect.
    * @return the CGHType for this <code>CopyNumberDE</code> object
    */
    public Float getValueObject() {
        return (Float) getValue();
    }

  /**
    * Sets the copyNumber for this <code>CopyNumberDE</code> object
    * @param copyNumber the copyNumber
	*/
    public void setValueObject(Float copyNumber) {
        if(copyNumber != null){
           this.value = copyNumber;
		 }
    }
  /**
    * Sets the CGHType for this <code>CopyNumberDE</code> object
    * @param CGHType the CGHType
	*/
    public void GetCGHType(String CGHType) {
        if(CGHType != null){
           value = CGHType;
		 }
    }

}
