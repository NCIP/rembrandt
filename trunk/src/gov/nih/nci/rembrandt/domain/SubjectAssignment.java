package gov.nih.nci.rembrandt.domain;

import java.util.Date;





/**
 * The treatment arm and other specifics regarding the participation of the Subject to a particular Study.
 * @created 18-Nov-2005 01:57:05 PM
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

public class SubjectAssignment {

	private Long id;
	/**
	 * The unique number assigned to identify a patient on a study.
	 */
	private String studySubjectIdentifier;
	/**
	 * Protocol-specific arm assignment identified in a formal communication.
	 * NOTE:  When Activity.epochName is "Prior" or "Baseline" -- we will default the arm value
	 */
	private String arm;
	/**
	 * The date the patient acknowledged participation on the protocol by signing the informed consent docume
	 */
	private Date informedConsentFormSignedDate;
	/**
	 * The date when the patient is removed from the protocol, i.e., is not being followed and will not be retreated
	 */
	private Date offStudyDate;
	/**
	 * A unique code for identification of uniform groups of patients for separate analysis or treatment is defined in the
	 * Clinical Data Update System (CDUS) Version 2.0. Use the codes submitted to CTEP on the Protocol Submission Checklist.
	 * NOTE: Also maps to Patient Subgroup Assignment	2003305.
	 */
	private String subgroupCode;
	/**
	 * The reason that a subject is given a sponsor-approved waiver for meeting protocol-defined eligibility requirements.  
	 */
	private String eligibilityWaiverReason;
	/**
	 * The date of approval of eligibility criteria by NCI.
	 */
	private Date eligibilityCriteriaApprovalDate;
	/**
	 * Age at the time of study enrollment, expressed in number of years completed at the last birthday. Value is collected to
	 * two decimal points of precision to meet Clinical Trials Monitoring Service (CTMS) reporting requirements.
	 */
	private Integer ageAtEnrollment;
	/**
	 * the yes/no indicator whether the informed consent form was signed. 
	 */
	private String informedConsentSignedInd;
	/**
	 * Work in Progress: DSAM- a time interval in the planned conduct of a study. Values include: Baseline, Screening, Run-in,
	 * Treatment, Follow-Up, etc.
	 */
	private String epochName;
	/**
	 * The classification of the sex or gender role of the patient. Values include: Female, Male, Unknown.
	 */
	private String administrativeGenderCode;
	/**
	 * The patient's self declared racial origination, independent of ethnic origination, using OMB approved categories.
	 * Values include: Not Reported, American Indian or Alaska Native, Native Hawaiian or other Pacific Islander, Unknown,
	 * Asian, White, Black or African American.
	 */
	private String raceCode;
	/**
	 * The patient's self declared ethnic origination, independent of racial origination, based on OMB approved categories.
	 * Values include: Hispanic Or Latino, Unknown, Not reported, Not Hispanic Or Latino.
	 */
	private String ethnicGroupCode;
	/**
	 * The date when the Patient who was participating in the study died.
	 */
	private Date dateOfDeath;
	/**
	 * The age at which the Patient who was participating in the study was diagnosed
	 */
	private Integer ageAtDiagnosis;

	public SubjectAssignment(){

	}

	/**
	 * @return Returns the administrativeGenderCode.
	 */
	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	/**
	 * @param administrativeGenderCode The administrativeGenderCode to set.
	 */
	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	/**
	 * @return Returns the ageAtDiagnosis.
	 */
	public Integer getAgeAtDiagnosis() {
		return ageAtDiagnosis;
	}

	/**
	 * @param ageAtDiagnosis The ageAtDiagnosis to set.
	 */
	public void setAgeAtDiagnosis(Integer ageAtDiagnosis) {
		this.ageAtDiagnosis = ageAtDiagnosis;
	}

	/**
	 * @return Returns the ageAtEnrollment.
	 */
	public Integer getAgeAtEnrollment() {
		return ageAtEnrollment;
	}

	/**
	 * @param ageAtEnrollment The ageAtEnrollment to set.
	 */
	public void setAgeAtEnrollment(Integer ageAtEnrollment) {
		this.ageAtEnrollment = ageAtEnrollment;
	}

	/**
	 * @return Returns the arm.
	 */
	public String getArm() {
		return arm;
	}

	/**
	 * @param arm The arm to set.
	 */
	public void setArm(String arm) {
		this.arm = arm;
	}

	/**
	 * @return Returns the dateOfDeath.
	 */
	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	/**
	 * @param dateOfDeath The dateOfDeath to set.
	 */
	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	/**
	 * @return Returns the eligibilityCriteriaApprovalDate.
	 */
	public Date getEligibilityCriteriaApprovalDate() {
		return eligibilityCriteriaApprovalDate;
	}

	/**
	 * @param eligibilityCriteriaApprovalDate The eligibilityCriteriaApprovalDate to set.
	 */
	public void setEligibilityCriteriaApprovalDate(
			Date eligibilityCriteriaApprovalDate) {
		this.eligibilityCriteriaApprovalDate = eligibilityCriteriaApprovalDate;
	}

	/**
	 * @return Returns the eligibilityWaiverReason.
	 */
	public String getEligibilityWaiverReason() {
		return eligibilityWaiverReason;
	}

	/**
	 * @param eligibilityWaiverReason The eligibilityWaiverReason to set.
	 */
	public void setEligibilityWaiverReason(String eligibilityWaiverReason) {
		this.eligibilityWaiverReason = eligibilityWaiverReason;
	}

	/**
	 * @return Returns the epochName.
	 */
	public String getEpochName() {
		return epochName;
	}

	/**
	 * @param epochName The epochName to set.
	 */
	public void setEpochName(String epochName) {
		this.epochName = epochName;
	}

	/**
	 * @return Returns the ethnicGroupCode.
	 */
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	/**
	 * @param ethnicGroupCode The ethnicGroupCode to set.
	 */
	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the informedConsentFormSignedDate.
	 */
	public Date getInformedConsentFormSignedDate() {
		return informedConsentFormSignedDate;
	}

	/**
	 * @param informedConsentFormSignedDate The informedConsentFormSignedDate to set.
	 */
	public void setInformedConsentFormSignedDate(Date informedConsentFormSignedDate) {
		this.informedConsentFormSignedDate = informedConsentFormSignedDate;
	}

	/**
	 * @return Returns the informedConsentSignedInd.
	 */
	public String getInformedConsentSignedInd() {
		return informedConsentSignedInd;
	}

	/**
	 * @param informedConsentSignedInd The informedConsentSignedInd to set.
	 */
	public void setInformedConsentSignedInd(String informedConsentSignedInd) {
		this.informedConsentSignedInd = informedConsentSignedInd;
	}

	/**
	 * @return Returns the offStudyDate.
	 */
	public Date getOffStudyDate() {
		return offStudyDate;
	}

	/**
	 * @param offStudyDate The offStudyDate to set.
	 */
	public void setOffStudyDate(Date offStudyDate) {
		this.offStudyDate = offStudyDate;
	}

	/**
	 * @return Returns the raceCode.
	 */
	public String getRaceCode() {
		return raceCode;
	}

	/**
	 * @param raceCode The raceCode to set.
	 */
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}

	/**
	 * @return Returns the studySubjectIdentifier.
	 */
	public String getStudySubjectIdentifier() {
		return studySubjectIdentifier;
	}

	/**
	 * @param studySubjectIdentifier The studySubjectIdentifier to set.
	 */
	public void setStudySubjectIdentifier(String studySubjectIdentifier) {
		this.studySubjectIdentifier = studySubjectIdentifier;
	}

	/**
	 * @return Returns the subgroupCode.
	 */
	public String getSubgroupCode() {
		return subgroupCode;
	}

	/**
	 * @param subgroupCode The subgroupCode to set.
	 */
	public void setSubgroupCode(String subgroupCode) {
		this.subgroupCode = subgroupCode;
	}

}
