package gov.nih.nci.rembrandt.queryservice.queryprocessing.ge;

import gov.nih.nci.caintegrator.dto.critieria.ArrayPlatformCriteria;
import gov.nih.nci.caintegrator.dto.critieria.CloneOrProbeIDCriteria;
import gov.nih.nci.caintegrator.dto.critieria.Constants;
import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.rembrandt.dbbean.CloneAccession;
import gov.nih.nci.rembrandt.dbbean.CloneDim;
import gov.nih.nci.rembrandt.dbbean.ProbesetDim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;

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

public class CloneProbePlatfromHandler {

    static  GEReporterIDCriteria buildCloneProbePlatformCriteria(CloneOrProbeIDCriteria cloneOrProbeCrit,  ArrayPlatformCriteria platCrit) throws Exception{
        PersistenceBroker _BROKER = PersistenceBrokerFactory.defaultPersistenceBroker();
        _BROKER.clearCache();

        GEReporterIDCriteria idsCriteria = new GEReporterIDCriteria();

        if (cloneOrProbeCrit != null ) {
            String probeType = getType(cloneOrProbeCrit);
            Collection inputCloneOrProbeDEs = cloneOrProbeCrit.getIdentifiers();
            if (inputCloneOrProbeDEs != null) {
                // convert ProbeNames/CloneNames into ProbesetIDs/CloneIDs
                ReportQueryByCriteria inputIDQuery = getCloneOrProbeIDsFromNameDEs(inputCloneOrProbeDEs, probeType, _BROKER);

                ArrayPlatformDE platObj = platCrit.getPlatform();
                if (platObj.getValueObject().equalsIgnoreCase(Constants.ALL_PLATFROM)) {
                    // use both cloneIDs and ProbesetIDs
                     if (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                        // this means inputIDs represent probesetIDs
                        ReportQueryByCriteria accessionQuery = getAccnsForProbeIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria cloneIDsQuery = getCloneIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(inputIDQuery);
                        idsCriteria.setCloneIDsSubQuery(cloneIDsQuery);
                     }
                     else if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE) ||
                             probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                        // this means inputIDs represent cloneIDs
                        ReportQueryByCriteria accessionQuery = getAccnsForCloneIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria probeIDsQuery = getProbeIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(probeIDsQuery);
                        idsCriteria.setCloneIDsSubQuery(inputIDQuery);
                     }
                }
                else if (platObj.getValueObject().equalsIgnoreCase(Constants.AFFY_OLIGO_PLATFORM)) {
                    if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE)||
                            probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                        /* this means inputIDs represent cloneIDs.  So convert these cloneIDs to
                        ProbesetIDs by going through GeneSymbol */
                        ReportQueryByCriteria accessionQuery = getAccnsForCloneIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria  probeIDsQuery = getProbeIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setProbeIDsSubQuery(probeIDsQuery);
                    }
                    else if  (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                         /* means inputIDs represent probeIDs  Since platform requested was OLIGO(Affy),
                            this means only probeIDs should be in the final result set */
                        idsCriteria.setProbeIDsSubQuery(inputIDQuery);
                    }
                }
                else if (platObj.getValueObject().equalsIgnoreCase(Constants.CDNA_ARRAY_PLATFORM)) {
                     if (probeType.equalsIgnoreCase(CloneIdentifierDE.PROBE_SET)) {
                        /* this means inputIDs represent probesetIDs.  So convert probesetID to
                         cloneIDs by going through GeneSymbol */
                        ReportQueryByCriteria accessionQuery = getAccnsForProbeIDs(inputIDQuery, _BROKER);
                        ReportQueryByCriteria cloneIDsQuery = getCloneIDsFor(accessionQuery, _BROKER);
                        idsCriteria.setCloneIDsSubQuery(cloneIDsQuery);
                    }
                     else if (probeType.equalsIgnoreCase(CloneIdentifierDE.IMAGE_CLONE)||
                            probeType.equalsIgnoreCase(CloneIdentifierDE.BAC_CLONE)) {
                         /* means inputIDs represent cloneIDs  Since platform requested was cDNA,
                            this means only cloneIDs should be in the final result set */
                          idsCriteria.setCloneIDsSubQuery(inputIDQuery);
                     }
                }

                //idCrit.addOrCriteria(idsCriteria.handle());
            }
        }
        _BROKER.close();
         return idsCriteria;
    }

    /*  This method converts ProbesetNames/CloneNames into thier respective IDs
    */
    public static ReportQueryByCriteria getCloneOrProbeIDsFromNameDEs(Collection probeOrCloneDEs, String probeType, PersistenceBroker _BROKER) throws Exception {
        Collection deNames = new ArrayList();
        org.apache.ojb.broker.query.ReportQueryByCriteria IDsQuery = null;
        for (Iterator iterator = probeOrCloneDEs.iterator(); iterator.hasNext();)
              deNames.add(((CloneIdentifierDE) iterator.next()).getValueObject());

        String nameCol = null;
        String idCol = null;
        if (probeType.equals(CloneIdentifierDE.PROBE_SET)) {
           nameCol = GeneExprQueryHandler.getColumnName(_BROKER, CloneIdentifierDE.ProbesetID.class.getName());
           idCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
           Criteria c = new Criteria();
           c.addColumnIn(nameCol, deNames);
           IDsQuery = QueryFactory.newReportQuery(ProbesetDim.class, new String[] {idCol}, c, true);
        }
        else if (probeType.equals(CloneIdentifierDE.IMAGE_CLONE) || probeType.equals(CloneIdentifierDE.BAC_CLONE) ) {
            nameCol = GeneExprQueryHandler.getColumnName(_BROKER, CloneIdentifierDE.IMAGEClone.class.getName());
            idCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, CloneDim.class.getName(), CloneDim.CLONE_ID);
            Criteria c = new Criteria();
            c.addColumnIn(nameCol, deNames);
            IDsQuery  =  QueryFactory.newReportQuery(CloneDim.class, new String[] {idCol}, c, true);
        }
        return IDsQuery ;
    }
    private static ReportQueryByCriteria  getCloneIDsFor(ReportQueryByCriteria  accnQuery, PersistenceBroker _BROKER) throws Exception {
        String cloneIDCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, CloneDim.class.getName(), CloneDim.CLONE_ID);
        return getCloneOrProbeIDsFor(accnQuery, cloneIDCol, CloneAccession.class, CloneAccession.ACCESSION_NUMBER, _BROKER);
    }
    private static ReportQueryByCriteria getProbeIDsFor(ReportQueryByCriteria accnQuery, PersistenceBroker _BROKER) throws Exception {
        String probeIDCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, ProbesetDim.class.getName(), ProbesetDim.PROBESET_ID);
        return getCloneOrProbeIDsFor(accnQuery, probeIDCol, ProbesetDim.class, ProbesetDim.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria  getCloneOrProbeIDsFor(ReportQueryByCriteria accnQuery, String cloneOrProbeIDCol, Class classToSearch, String fieldToSearch, PersistenceBroker _BROKER) throws Exception {

        Criteria clonOrProbeIDCrit = new Criteria();
        clonOrProbeIDCrit.addIn(fieldToSearch,  accnQuery);
        org.apache.ojb.broker.query.ReportQueryByCriteria cloneOrProbeIDSQuery =
                QueryFactory.newReportQuery(classToSearch, new String[] {cloneOrProbeIDCol}, clonOrProbeIDCrit , true);
        return cloneOrProbeIDSQuery;
    }

    private static ReportQueryByCriteria getAccnsForProbeIDs(ReportQueryByCriteria probeIDSubQuery, PersistenceBroker _BROKER) throws Exception {
        return getAccnForCloneOrProbesetIDs(probeIDSubQuery, ProbesetDim.PROBESET_ID, ProbesetDim.class, ProbesetDim.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria getAccnsForCloneIDs(ReportQueryByCriteria cloneIDSubQuery, PersistenceBroker _BROKER) throws Exception {
        return getAccnForCloneOrProbesetIDs(cloneIDSubQuery, CloneDim.CLONE_ID, CloneAccession.class, CloneAccession.ACCESSION_NUMBER, _BROKER);
    }

    private static ReportQueryByCriteria getAccnForCloneOrProbesetIDs(ReportQueryByCriteria probeOrCloneIDSubQuery, String probeOrCloneIDAttrName, Class classToSearch, String fieldToSearch, PersistenceBroker _BROKER) throws Exception {
        String accnCol = GeneExprQueryHandler.getColumnNameForBean(_BROKER, classToSearch.getName(), fieldToSearch);
        Criteria cloneOrProbeCriteria = new Criteria();
        cloneOrProbeCriteria.addIn(probeOrCloneIDAttrName, probeOrCloneIDSubQuery);
        org.apache.ojb.broker.query.ReportQueryByCriteria accessionQuery =
                QueryFactory.newReportQuery(classToSearch, new String[] {accnCol}, cloneOrProbeCriteria , true);
        return accessionQuery;
    }

    public static String getType(CloneOrProbeIDCriteria cloneProbeCrit ) {
        if (cloneProbeCrit != null ) {
            Collection cloneIDorProbeIDs = cloneProbeCrit.getIdentifiers();
             if (cloneIDorProbeIDs != null && cloneIDorProbeIDs.size() > 0) {
                 CloneIdentifierDE obj =  (CloneIdentifierDE ) cloneIDorProbeIDs.iterator().next();
                 return obj.getCloneIDType();
             }
        }
        return "";
    }

}
