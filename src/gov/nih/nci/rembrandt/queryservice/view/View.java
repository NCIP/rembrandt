package gov.nih.nci.rembrandt.queryservice.view;

import gov.nih.nci.caintegrator.dto.de.DomainElementClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author BhattarR
 */
abstract public class View implements Viewable{
	/**
	 * IMPORTANT! This class requires a clone method! This requires that any new
	 * data field that is added to this class also be cloneable and be added to
	 * clone calls in the clone method.If you do not do this, you will not
	 * seperate the references of at least one data field when we generate a
	 * copy of this object.This means that if the data field ever changes in one
	 * copy or the other it will affect both instances... this will be hell to
	 * track down if you aren't ultra familiar with the code base, so add those
	 * methods now! (Not necesary for primitives.)
	 */
   Collection selectedDomainElements;
   ViewType viewType;
   public Collection getSelectedElements() {
          return selectedDomainElements;
   }
   public void setSelectedElements(Collection selectedElems) {
          selectedDomainElements = selectedElems;
   }
   public abstract DomainElementClass[] getValidElements();
   /**
    * Compares the class names to determine if the passed view is
    * of the same type as this view
    * @param view
    * @return
    */
   public boolean equals(View view) {
	   	if(view!=null) {
		   	if((view.getClass()).getName().equals(this.getClass().getName())) {
		   		return true;
		   	}
	   	}
	   	return false;
   }
   public Object clone() {
   		View myClone = null;
		try {
			myClone = (View)super.clone();
			myClone.selectedDomainElements = new ArrayList();
            if(selectedDomainElements != null){
    			for(Iterator i = selectedDomainElements.iterator();i.hasNext(); ) {
    				myClone.selectedDomainElements.add(i.next());
    			}
            }
            if(viewType != null){
                myClone.viewType = (ViewType)viewType.clone();
            }
		} catch (CloneNotSupportedException e) {
			//This will never happen
		}
   		return myClone;
   }
}
