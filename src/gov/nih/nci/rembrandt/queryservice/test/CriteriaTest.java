/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.test;

import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.critieria.GeneIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.RegionCriteria;
import gov.nih.nci.caintegrator.dto.de.BasePairPositionDE;
import gov.nih.nci.caintegrator.dto.de.ChromosomeNumberDE;
import gov.nih.nci.caintegrator.dto.de.CytobandDE;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.caintegrator.dto.view.ViewFactory;
import gov.nih.nci.caintegrator.dto.view.ViewType;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.QueryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;






/**
 * @author BhattarR
 */


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

public class CriteriaTest extends TestCase {
      static FoldChangeCriteria foldCrit;
      static  GeneIDCriteria  geneIDCrit;
      static  RegionCriteria regionCrit;
    public static class RegionCriteriaTest extends CriteriaTest {
        protected void setUp() {
            regionCrit = new RegionCriteria();
            regionCrit.setChromNumber(new ChromosomeNumberDE("chr17"));
            regionCrit.setStart(new BasePairPositionDE.StartPosition(new Long("34344251")));
            regionCrit.setEnd(new BasePairPositionDE.EndPosition(new Long("34344297")));
            regionCrit.setCytoband(new CytobandDE("17q11.2-q12"));
        }
        public RegionCriteriaTest () {
        }

        public void testRegionCriteria() {
            // TODO: really nothing
        }
    }
    public static class FoldChangeCriteriaTest extends CriteriaTest {
        Float upRegExpected = new Float(2.0);
        Float downRegExpected = new Float(1.0);
        //Float upperUnchangedExpected = new Float(4.0);
        //Float downUnChangedExpected = new Float(2.0);

        protected void setUp() {
            ExprFoldChangeDE.UpRegulation upRegObj = new ExprFoldChangeDE.UpRegulation(upRegExpected );
            ExprFoldChangeDE.DownRegulation downRegObj = new ExprFoldChangeDE.DownRegulation(downRegExpected );
            //ExprFoldChangeDE.UnChangedRegulationUpperLimit upUnChangedObj = new ExprFoldChangeDE.UnChangedRegulationUpperLimit(upperUnchangedExpected );
            //ExprFoldChangeDE.UnChangedRegulationDownLimit downUnChangedRegObj = new ExprFoldChangeDE.UnChangedRegulationDownLimit(downUnChangedExpected );

            foldCrit = new FoldChangeCriteria();
            Collection objs = new ArrayList(4);
            objs.add(upRegObj);
            objs.add(downRegObj);
            //objs.add(upUnChangedObj); objs.add(downUnChangedRegObj);
            foldCrit.setFoldChangeObjects(objs);
        }
        public void testFoldChangeCriteria() {
            Collection col = foldCrit.getFoldChangeObjects();
            for (Iterator iterator = col.iterator(); iterator.hasNext();) {
                ExprFoldChangeDE o = (ExprFoldChangeDE) iterator.next();
                Float result = o.getValueObject();
            }
            // TODO: verify result
        }
        public FoldChangeCriteriaTest() {
        }
    }
    public static class GeneIDCriteriaTest extends CriteriaTest {
        ArrayList inputIDs = new ArrayList();
	    protected void setUp() {
            inputIDs.add(0, "6352");
            inputIDs.add(1, "187011");
            GeneIdentifierDE.LocusLink geLocusObj =
                    new GeneIdentifierDE.LocusLink((String)inputIDs.get(0));
            GeneIdentifierDE.GenBankAccessionNumber geGenBankObj =
                            new GeneIdentifierDE.GenBankAccessionNumber((String)inputIDs.get(1));
            Vector geneIDentifiers = new Vector();
            geneIDentifiers.add(geLocusObj);
            geneIDentifiers.add(geGenBankObj);
            geneIDCrit = new GeneIDCriteria();
            geneIDCrit.setGeneIdentifiers(geneIDentifiers);

        }
        public void testGeneCriteria() {
            Collection objs = geneIDCrit.getGeneIdentifiers();
            ArrayList resultantIDs = new ArrayList();
            for (Iterator iterator = objs.iterator(); iterator.hasNext();) {
                GeneIdentifierDE o = (GeneIdentifierDE) iterator.next();
                resultantIDs.add(o.getValueObject());
            }
            assertTrue(resultantIDs.containsAll(inputIDs));
        }

        public GeneIDCriteriaTest() {
        }

	}
    public static class GeneExprQueryTest extends CriteriaTest {
        public GeneExprQueryTest() {
        }

        protected void setUp() {
            GeneExpressionQuery q = (GeneExpressionQuery) QueryManager.createQuery(QueryType.GENE_EXPR_QUERY_TYPE);
            q.setQueryName("Test Gene Query");
            q.setAssociatedView(ViewFactory.newView(ViewType.GENE_SINGLE_SAMPLE_VIEW));
            q.setGeneIDCrit(geneIDCrit);
            q.setRegionCrit(regionCrit);
            q.setFoldChgCrit(foldCrit);
            try {
                QueryManager.executeQuery(q);
            } catch(Throwable t ) {
                t.printStackTrace();
            }

        }
        public void testGeneExprQuery() {

        }
    }
    public static Test suite() {
		TestSuite suit =  new TestSuite();
        suit.addTest(new TestSuite(GeneIDCriteriaTest.class));
        suit.addTest(new TestSuite(FoldChangeCriteriaTest.class));
        suit.addTest(new TestSuite(RegionCriteriaTest.class));
        suit.addTest(new TestSuite(GeneExprQueryTest.class));

        return suit;
	}

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());

    }

}
