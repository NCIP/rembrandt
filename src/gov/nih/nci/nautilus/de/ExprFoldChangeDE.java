package gov.nih.nci.nautilus.de;

//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;

/**
 * This abstract class encapsulates the properties of an caintergator
 * ExprFoldChangeDE object.
 * It contains three child/nested classes:UpRegulation, DownRegulation & UnChangedRegulation
 *
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Jul 12, 2004
 * Time: 6:06:49 PM
 * To change this template use Options | File Templates.
 */
abstract public class ExprFoldChangeDE extends DomainElement {


   // ****************************************************
   //                     ATTRIBUTES
   // ****************************************************

  /**
	* type of regulation
    */
    private String regulationType;

  /**
	* UpRegulation
    */
    public final static String UP_REGULATION =  "UpRegulation";

  /**
	* DownRegulation
    */
    public final static String DOWN_REGULATION =  "DownRegulation";

  /**
	* UnchangedRegulationUpperLimit
   * */
    public final static String UNCHANGED_REGULATION_UPPER_LIMIT =  "UnchangedRegulationUpperLimit";

    /**
      * UnchangedRegulationUpperLimit
    * */
    public final static String UNCHANGED_REGULATION_DOWN_LIMIT =  "UnchangedRegulationDownLimit";

   // ****************************************************
   //                   CONSTRUCTOR(S)
   // *****************************************************

   
   private ExprFoldChangeDE() {
        super();        
    }
	
  /**
	* private parent constructor utilized in the two nested/childe classes
    */
    private ExprFoldChangeDE(String regulationType, Float value) {
        super(value);
        this.regulationType = regulationType;
    }


  /**
	* nested child class: UpRegulation
    */
    public final static class UpRegulation extends ExprFoldChangeDE {
       public UpRegulation(Float upRegValue) {
            super(UP_REGULATION, upRegValue);
       }
	   public UpRegulation() {
            super();
       }
    }

  /**
	* nested child class: DownRegulation
    */
    public final static class DownRegulation extends ExprFoldChangeDE {
      public DownRegulation(Float downRegValue) {
            super(DOWN_REGULATION, downRegValue);
      }
	  public DownRegulation() {
            super();
      }
    }


  /**
	* nested child class: UnChangedRegulation
    */
    public final static class UnChangedRegulationUpperLimit extends ExprFoldChangeDE {
      public UnChangedRegulationUpperLimit (Float unChangedRegUpperValue) {
            super(UNCHANGED_REGULATION_UPPER_LIMIT, unChangedRegUpperValue);
      }
	  public UnChangedRegulationUpperLimit () {
            super();
      }
    }

    /**
        * nested child class: UnChangedRegulation
        */
    public final static class UnChangedRegulationDownLimit extends ExprFoldChangeDE {
      public UnChangedRegulationDownLimit (Float unChangedRegDownValue) {
            super(UNCHANGED_REGULATION_DOWN_LIMIT, unChangedRegDownValue);
      }
	  public UnChangedRegulationDownLimit () {
            super();
      }
    }



  /**
    * Returns the regulationType for this ExprFoldChangeDE obect.
    * @return the regulationType for this <code>ExprFoldChangeDE</code> object
    */
    public String getRegulationType() {
        return regulationType;
    }

 /**
    * Sets the value for this <code>ExprFoldChangeDE</code> object
    * @param object the value
	*/
    public void setValue(Object obj) throws Exception {
         if (! (obj instanceof Float) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + obj);
         setValueObject((Float)obj);
    }

  /**
    * Returns the regulationType for this ExprFoldChangeDE obect.
    * @return the regulationType for this <code>ExprFoldChangeDE</code> object
    */
    public Float getValueObject() {
        return (Float) getValue();
    }

  /**
    * Sets the regulation for this <code>ExprFoldChangeDE</code> object
    * @param regulation the regulation
	*/
    public void setValueObject(Float regulation) {
	   if(regulation != null){
        value = regulation;
		}
    }
}
