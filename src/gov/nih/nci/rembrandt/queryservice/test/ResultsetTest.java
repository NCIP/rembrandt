/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.rembrandt.dbbean.DifferentialExpressionSfact;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.ojb.broker.PBFactoryException;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * @author SahniH
 * Date: September 20, 2004 
 * @version $Revision: 1.2 $
 * This junit test encapsulates the query and resultset tests
 * 
 * 
 *
 * */


/**
* caIntegrator License
* 
* Copyright 2001-2005 Science Applications International Corporation ("SAIC"). 
* The software subject to this notice and license includes both human readable source code form and machine readable, 
* binary, object code form ("the caIntegrator Software"). The caIntegrator Software was developed in conjunction with 
* the National Cancer Institute ("NCI") by NCI employees and employees of SAIC. 
* To the extent government employees are authors, any rights in such works shall be subject to Title 17 of the United States
* Code, section 105. 
* This caIntegrator Software License (the "License") is between NCI and You. "You (or "Your") shall mean a person or an 
* entity, and all other entities that control, are controlled by, or are under common control with the entity. "Control" 
* for purposes of this definition means (i) the direct or indirect power to cause the direction or management of such entity,
*  whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) 
* beneficial ownership of such entity. 
* This License is granted provided that You agree to the conditions described below. NCI grants You a non-exclusive, 
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and royalty-free right and license in its rights 
* in the caIntegrator Software to (i) use, install, access, operate, execute, copy, modify, translate, market, publicly 
* display, publicly perform, and prepare derivative works of the caIntegrator Software; (ii) distribute and have distributed 
* to and by third parties the caIntegrator Software and any modifications and derivative works thereof; 
* and (iii) sublicense the foregoing rights set out in (i) and (ii) to third parties, including the right to license such 
* rights to further third parties. For sake of clarity, and not by way of limitation, NCI shall have no right of accounting
* or right of payment from You or Your sublicensees for the rights granted under this License. This License is granted at no
* charge to You. 
* 1. Your redistributions of the source code for the Software must retain the above copyright notice, this list of conditions
*    and the disclaimer and limitation of liability of Article 6, below. Your redistributions in object code form must reproduce 
*    the above copyright notice, this list of conditions and the disclaimer of Article 6 in the documentation and/or other materials
*    provided with the distribution, if any. 
* 2. Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This 
*    product includes software developed by SAIC and the National Cancer Institute." If You do not include such end-user 
*    documentation, You shall include this acknowledgment in the Software itself, wherever such third-party acknowledgments 
*    normally appear.
* 3. You may not use the names "The National Cancer Institute", "NCI" "Science Applications International Corporation" and 
*    "SAIC" to endorse or promote products derived from this Software. This License does not authorize You to use any 
*    trademarks, service marks, trade names, logos or product names of either NCI or SAIC, except as required to comply with
*    the terms of this License. 
* 4. For sake of clarity, and not by way of limitation, You may incorporate this Software into Your proprietary programs and 
*    into any third party proprietary programs. However, if You incorporate the Software into third party proprietary 
*    programs, You agree that You are solely responsible for obtaining any permission from such third parties required to 
*    incorporate the Software into such third party proprietary programs and for informing Your sublicensees, including 
*    without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
*    before incorporating the Software into such third party proprietary software programs. In the event that You fail 
*    to obtain such permissions, You agree to indemnify NCI for any claims against NCI by such third parties, except to 
*    the extent prohibited by law, resulting from Your failure to obtain such permissions. 
* 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
*    to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses 
*    of modifications of the Software, or any derivative works of the Software as a whole, provided Your use, reproduction, 
*    and distribution of the Work otherwise complies with the conditions stated in this License.
* 6. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, 
*    THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. 
*    IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
*    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
*    GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
*    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
*    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
*/

public class ResultsetTest extends TestCase{
    PersistenceBroker broker;
    Collection exprObjects = null;
    private static Class CLASS = ResultsetTest.class;
    public ResultsetTest(String s) {
        super(s);
    }
   	public static junit.framework.Test suite() {	
    	junit.framework.TestSuite suite = new junit.framework.TestSuite();
        suite.addTest(new ResultsetTest("testResultset"));
    	return suite;
	}


    public static void main (String[] args) {
            junit.textui.TestRunner.run(suite());

        }

   public void setUp()
    {
       try {
           broker = PersistenceBrokerFactory.defaultPersistenceBroker();
           createQuery();
       } catch (PBFactoryException e) {
           e.printStackTrace();
           fail("Got " + e.getClass().getName() + ": " + e.getMessage());
       }

   }
    public void tearDown()
    {
        broker.close();
    }

    /**
	 * test EqualTo Criteria
	 */
    public void createQuery()
    {
    	try{
        Criteria exprCrit = new Criteria();
        exprCrit.addEqualTo("geneSymbol", "EGFR");
        Query exprQuery = QueryFactory.newQuery(DifferentialExpressionSfact.class, exprCrit);
        exprObjects = broker.getCollectionByQuery(exprQuery);
		System.out.println("Got " + exprObjects.size() + " exprObjects objects.");
        assertNotNull(exprObjects);
        assertTrue(exprObjects.size() > 0);
        
	        for (Iterator iterator = exprObjects.iterator(); iterator.hasNext();) {
	        	DifferentialExpressionSfact expObj = (DifferentialExpressionSfact) iterator.next();
	            System.out.println("expObj.geneSymbol: " + expObj.getGeneSymbol() + " |expObj.probesetId: " +expObj.getProbesetId()+ " |expObj.dieasesTypeID: " +expObj.getDiseaseTypeId()+ " |expObj.biospecimenID: " +expObj.getBiospecimenId() );
	        }
 		} catch (Exception ex) {
 			ex.printStackTrace();
 			fail("Got " + ex.getClass().getName() + ": " + ex.getMessage());
 		}  
    }
    public void testResultset(){
 /*   	ResultsetProcessor resultsetProc = new ResultsetProcessor();
    	assertNotNull(exprObjects);
        assertTrue(exprObjects.size() > 0);
    	resultsetProc.handleGeneView(exprObjects);
    	GeneExprSingleViewResultsContainer geneViewContainer = resultsetProc.getGeneViewContainer();
    	Collection genes = geneViewContainer.getGeneResultsets();
    	System.out.println("Gene Count: "+genes.size());
    	for (Iterator geneIterator = genes.iterator(); geneIterator.hasNext();) {
    		GeneResultset geneResultset = (GeneResultset)geneIterator.next();
    		Collection reporters = geneResultset.getReporterResultsets();
        	System.out.println("Repoter Count: "+reporters.size());
    		for (Iterator reporterIterator = reporters.iterator(); reporterIterator.hasNext();) {
        		ReporterResultset reporterResultset = (ReporterResultset)reporterIterator.next();
        		Collection diseaseTypes = reporterResultset.getDiseaseResultsets();
            	System.out.println("Disease Count: "+diseaseTypes.size());
        		for (Iterator diseaseIterator = diseaseTypes.iterator(); diseaseIterator.hasNext();) {
        			DiseaseTypeResultset dieaseResultset = (DiseaseTypeResultset)diseaseIterator.next();
            		Collection biospecimens = dieaseResultset.getBioSpecimenResultsets();
                	System.out.println("Biospecimen Count: "+biospecimens.size());
            		for (Iterator biospecimenIterator = biospecimens.iterator(); biospecimenIterator.hasNext();) {
            			BioSpecimenResultset biospecimenResultset = (BioSpecimenResultset)biospecimenIterator.next();
                	            System.out.println(	"GeneSymbol: "+geneResultset.getGeneSymbol().getValueObject().toString()+
                	            					"| ReporterId: "+ reporterResultset.getReporter().getValue().toString()+
													"| DieaseType Id: "+dieaseResultset.getDieaseType().getValue().toString()+
													"| BioSpecimen Id: "+ biospecimenResultset.getBiospecimen().getValue().toString()+
													"| FoldChange Value: "+ biospecimenResultset.getFoldChangeValue().getValue().toString());
            		}
        		}
    		}
    	}
 */   	
    }
   
}
