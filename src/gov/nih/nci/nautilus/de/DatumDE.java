package gov.nih.nci.nautilus.de;
//caintergator classes
import gov.nih.nci.nautilus.util.ApplicationContext;
/**
 * @author SahniH
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DatumDE extends DomainElement{
 
	 /**
	* type of datum
    */
    private String datumType;

  /**
	* FoldChange
    */
    public final static String FOLD_CHANGE =  "FoldChange";

  /**
	* CopyNumber
    */
    public final static String COPY_NUMBER =  "CopyNumber";

  /**
	* CLONE_ID
    */
    public final static String CLONE_ID =  "CloneID";

    /**
	* PROBESET_ID
    */
    public final static String PROBESET_ID =  "ProbeSetID";
   
// ****************************************************
//                   CONSTRUCTOR(S)
// ****************************************************
  
 
 /**
  * Initializes a newly created <code>DatumDE</code> object so that it represents an AlleleFrequencyDE.
  */
  public DatumDE(String datumType, Object value) {
    super(value);
  	this.datumType = datumType;
  }
  /**
   * Initializes a newly created <code>DatumDE</code> object so that it represents an AlleleFrequencyDE.
   */

 public String getType(){
 	return datumType;
 }
/* 
 * @see gov.nih.nci.nautilus.de.DomainElement#setValue(java.lang.Object)
 */
public void setValue(Object obj) throws Exception {
	if ( obj == null ){
		throw new Exception ( "Could not set the value.  Parameter is a null object");
	}
	else{
		value = obj;	
	}
 }
}
