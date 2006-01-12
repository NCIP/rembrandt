package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.de.DomainElementClass;
import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import junit.framework.TestCase;

/**
 * @author SahniH
 * Date: Nov 1, 2004
 * 
 */
public class DomainElementClassTest extends TestCase {

	public static void main(String[] args) {
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*public void testGetLabel() {
		View geneView = ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW);
		DomainElementClass[] domainElements = geneView.getValidElements();
		System.out.println("Valid Domain Elements for GeneExprSampleView");
		for(int i=0;i<domainElements.length;i++){
			System.out.println("["+i+"]"+domainElements[i].getLabel());
		}
	}*/

}
