package gov.nih.nci.nautilus.view;

import gov.nih.nci.nautilus.de.DomainElementClass;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: BhattarR
 * Date: Aug 12, 2004
 * Time: 6:21:03 PM
 * To change this template use Options | File Templates.
 */
abstract public class View implements Viewable{
   Collection selectedDomainElements;
   ViewType viewType;
   public Collection getSelectedElements() {
          return selectedDomainElements;
   }
   public void setSelectedElements(Collection selectedElems) {
          selectedDomainElements = selectedElems;
   }
   public abstract DomainElementClass[] getValidElements();
}
