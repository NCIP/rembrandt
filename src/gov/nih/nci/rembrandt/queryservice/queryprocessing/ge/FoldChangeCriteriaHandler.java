/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.FoldChangeCriteria;
import gov.nih.nci.caintegrator.dto.de.ExprFoldChangeDE;
import gov.nih.nci.rembrandt.dto.query.GeneExpressionQuery;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.AllGenesCritValidator;
import gov.nih.nci.rembrandt.queryservice.queryprocessing.QueryHandler;

import java.util.Collection;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.query.Criteria;

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

public class FoldChangeCriteriaHandler {
    public final static Float ALL_GENES_REGULATION_LIMIT = new Float(4.0);
    public final static Float ALL_GENES_UNCHANGED_UPPER_LIMIT = new Float(1.2);
    public final static Float ALL_GENES_UNCHANGED_DOWN_LIMIT = new Float(0.8);

    public static void addFoldChangeCriteriaForAllGenes(GeneExpressionQuery geQuery, Class targetFactClass, PersistenceBroker pb, Criteria sampleCrit)
    throws Exception {
         AllGenesCritValidator.validateFoldChangeForAllGenes(geQuery);
         addFoldChangeCriteria(geQuery, targetFactClass, pb, sampleCrit);
    }
    public static void addFoldChangeCriteria(GeneExpressionQuery geQuery, Class beanClass, PersistenceBroker pb, Criteria criteria)
    throws Exception {
       FoldChangeCriteria foldChangeCrit = geQuery.getFoldChgCrit();
       if (foldChangeCrit != null) {
               String columnName = QueryHandler.getColumnName(pb, ExprFoldChangeDE.class.getName(), beanClass.getName());
               Collection objs = foldChangeCrit.getFoldChangeObjects();
               Object[] foldObjs = objs.toArray();

                // Either only UpRegulation or DownRegulation
                if (foldObjs.length == 1) {
                    ExprFoldChangeDE foldChgObj = (ExprFoldChangeDE) foldObjs[0];
                    Double foldChange = new Double(foldChgObj.getValueObject().floatValue());
                    addSingleUpORDownCriteria(foldChange, foldChgObj.getRegulationType(), columnName, criteria, pb);
                }

                // else it could be EITHER both (UpRegulation or DownRegulation) OR UnChangedRegulation
                else if (foldObjs.length == 2) {
                   String type = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
                   if (type.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_UPPER_LIMIT) || type.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_DOWN_LIMIT) ) {
                       addUnChangedCriteria(foldObjs, columnName, criteria, pb);
                   }
                   else if (type.equals(ExprFoldChangeDE.UP_REGULATION) || type.equals(ExprFoldChangeDE.DOWN_REGULATION) ) {
                       addUpAndDownCriteria(foldObjs, columnName, criteria, pb);
                   }
                }
                else {
                   throw new Exception("Invalid number of FoldChange Criteria objects: " + foldObjs.length);
                }
           }

    }

    private static void addUpAndDownCriteria(Object[] foldObjs, String columnName, Criteria criteria, PersistenceBroker pb) throws Exception {
        String type1 = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
        Double foldChange1 = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
        addSingleUpORDownCriteria(foldChange1, type1, columnName, criteria, pb);

        String type2 = ((ExprFoldChangeDE)foldObjs[1]).getRegulationType();
        Double foldChange2 = new Double(((ExprFoldChangeDE)foldObjs[1]).getValueObject().floatValue());
        Criteria fold2Crit = new Criteria();
        addSingleUpORDownCriteria(foldChange2, type2, columnName, fold2Crit, pb);

        criteria.addOrCriteria(fold2Crit);
    }

    private static void addUnChangedCriteria(Object[] foldObjs, String columnName, Criteria criteria, PersistenceBroker pb) throws Exception {

        String type1 = ((ExprFoldChangeDE)foldObjs[0]).getRegulationType();
        Double upperLimit;
        Double lowerLimit;

        if (type1.equals(ExprFoldChangeDE.UNCHANGED_REGULATION_DOWN_LIMIT)) {
            upperLimit = new Double(((ExprFoldChangeDE) foldObjs[1]).getValueObject().floatValue());
            lowerLimit = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
        }
        else {
            upperLimit = new Double(((ExprFoldChangeDE)foldObjs[0]).getValueObject().floatValue());
            lowerLimit = new Double(((ExprFoldChangeDE)foldObjs[1]).getValueObject().floatValue());
        }
        criteria.addBetween(columnName, lowerLimit, upperLimit);
    }

    private static void addSingleUpORDownCriteria(Double foldChange, String type, String colunName, Criteria subCrit, PersistenceBroker pb) throws Exception {

        if (type.equals(ExprFoldChangeDE.UP_REGULATION)) {
            subCrit.addGreaterOrEqualThan(colunName,foldChange);
        }
        else if (type.equals(ExprFoldChangeDE.DOWN_REGULATION)) {
            double convertedDownFold = 1 / (foldChange.doubleValue());
            subCrit.addLessOrEqualThan(colunName, new Double(convertedDownFold));
        }
        else {
            throw new Exception("Invalid Regulation: " + type + " Value:" + foldChange);
        }

    }


}
