/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.domain;

import java.util.Date;





/**
 * A document that describes the objective(s), background and plan (including design, methodology, statistical
 * considerations and organization) of a Clinical Trial. It is the action plan for the conduct of a clinical trial.
 * @created 18-Nov-2005 01:56:58 PM
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

public class Protocol {

	private Long id;
	/**
	 * The numeric or alphanumeric identification assigned to the study by the NCI. Inter-Group protocols should use the lead
	 * Groups protocol number.
	 */
	private String number;
	/**
	 * Descriptive text used to represent the long title or name of a protocol.
	 */
	private String longTitle;
	/**
	 * Title of a protocol limited to 30 characters in length.
	 * BRIDG: A name or abbreviated title by which the document is known.
	 */
	private String shortTitle;
	/**
	 * Date of IRB approval of the initial protocol version; the date the IRB Chair signs off on a protocol and patient
	 * enrollment can begin.
	 */
	private Date activationDate;
	/**
	 * Date of closure refers to the closing of a study to enrollment.  Patients enrolled on the study at the time of closure
	 * will continue their treatment plan.
	 */
	private Date closureDate;
	/**
	 * Date of the status change of a protocol to 'suspended', requiring that patient accrual be halted until the protocol is
	 * restored to fully active status.
	 */
	private Date suspensionDate;
	/**
	 * Coded designation of phase (I, II, III, or IV) for a clinical trial. Values include: I, I/II, II, III, NA.
	 */
	private String phaseCode;
	/**
	 * Codes to represent the status of a protocol in relation to the ability to enroll participants/patients. Values include:
	 * C: Closed, O: Open, S: Suspended, T: Terminated.
	 */
	private String statusCode;
	/**
	 * Codes to identify a type of protocol based upon its intent (genetic, diagnostic, preventive, etc.). Values include: D:
	 * Diagnostic Protocol, GN: Genetic Non-therapeutic Protocol, GT: Genetic Therapeutic Protocol, N: Therapeutic Protocol, P:
	 * Primary Treatment Protocol , S:  Supportive Protocol, T: Preventive Protocol.
	 */
	private String intentCode;
	/**
	 * Code to represent the monitor for a protocol. Values iclude: CTEP, CTEP - CTMS; CTEP - CDUS Complete; CTEP - CDUS
	 * Abbreviated; Pharmaceutical Company; Internal Monitor.
	 */
	private String monitorCode;
	/**
	 * Indicator of Yes (Y) or No (N) to specify if a protocol is blinded.
	 */
	private Boolean blindIndicator;
	/**
	 * Code to represent at a  summary level the category of disease treated on a protocol (Cancer, AIDS, and Benign disease).
	 * Values Include: A: AIDS, B: Benign, C: Cancer.
	 */
	private String diseaseCode;
	/**
	 * Code used to identify the sponsor (IND holder) for a clinical trial. Values include: AB  Abbott Labs; AL  Alkermes, Inc.
	 * ; APH  Angiotech; AM  Amgen; BF  Brian Fuller, MD; BI  Boehringer Ingelheim; BM Battelle Memorial, Inc.; BW  Burroughs
	 * Wellcome; CG Celgene; CL  CanLab Pharm Research; CP  CellPro, Inc.; CT  CTEP  Cancer Therapy Evaluation Program, NCI;
	 * DHF  Daniel H. Fowler, MD; EL  Eli Lilly; EV  Ellen Vitetta, MD; FJ  Fujisawa; GH  Genentech; GI  Gilead Sciences; GX
	 * Glaxo; HLR  Hoffman LaRoche; IM  Immunogen; IRC  Immune Response Corp; JA  Janssen; KN  Knoll; LP  The Liposome Company;
	 * ME  Medarex, Inc.; MGI  MGI Pharma, Inc.; MK  Merck and Co., Inc.; MT  Maria Turner, MD; NCI National Cancer Institute
	 * program; NI NIAID; PF  Pfizer; PG  Proctor & Gamble; RF  Robert Fenton, MD; RI  RIBI Immunochem; SA  Sandoz; SG Sugen,
	 * Inc.; SP  Schering-Plough; THN  Therion; TW  Thomas Waldmann, MD; TX  Texcellon; US  US Biosciences; VI	Vion
	 * Pharmaceuticals; WCE  W.C. Eckelman, MD; XE Xenova, Ltd.
	 */
	private String sponsorCode;
	/**
	 * A Yes/No response to indicate if a protocol is being conducted at more than one site concurrently.
	 */
	private Boolean multiInstitutionIndicator;
	/**
	 * Total number of patients/subjects/participants needed for protocol enrollment (accrual). 
	 */
	private Long targetAccrualNumber;
	/**
	 * A structured summary description of a protocol document.
	 */
	private String precis;
	/**
	 * BRIDG: A statement describing the overall rationale of the study [PR Group].
	 */
	private String statementOfPurpose;
	private StudyObjective studyObjective;

	public Protocol(){

	}

	/**
	 * @return Returns the activationDate.
	 */
	public Date getActivationDate() {
		return activationDate;
	}

	/**
	 * @param activationDate The activationDate to set.
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	/**
	 * @return Returns the blindIndicator.
	 */
	public Boolean getBlindIndicator() {
		return blindIndicator;
	}

	/**
	 * @param blindIndicator The blindIndicator to set.
	 */
	public void setBlindIndicator(Boolean blindIndicator) {
		this.blindIndicator = blindIndicator;
	}

	/**
	 * @return Returns the closureDate.
	 */
	public Date getClosureDate() {
		return closureDate;
	}

	/**
	 * @param closureDate The closureDate to set.
	 */
	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	/**
	 * @return Returns the diseaseCode.
	 */
	public String getDiseaseCode() {
		return diseaseCode;
	}

	/**
	 * @param diseaseCode The diseaseCode to set.
	 */
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
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
	 * @return Returns the intentCode.
	 */
	public String getIntentCode() {
		return intentCode;
	}

	/**
	 * @param intentCode The intentCode to set.
	 */
	public void setIntentCode(String intentCode) {
		this.intentCode = intentCode;
	}

	/**
	 * @return Returns the longTitle.
	 */
	public String getLongTitle() {
		return longTitle;
	}

	/**
	 * @param longTitle The longTitle to set.
	 */
	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
	}

	/**
	 * @return Returns the monitorCode.
	 */
	public String getMonitorCode() {
		return monitorCode;
	}

	/**
	 * @param monitorCode The monitorCode to set.
	 */
	public void setMonitorCode(String monitorCode) {
		this.monitorCode = monitorCode;
	}

	/**
	 * @return Returns the multiInstitutionIndicator.
	 */
	public Boolean getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	/**
	 * @param multiInstitutionIndicator The multiInstitutionIndicator to set.
	 */
	public void setMultiInstitutionIndicator(Boolean multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}

	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return Returns the phaseCode.
	 */
	public String getPhaseCode() {
		return phaseCode;
	}

	/**
	 * @param phaseCode The phaseCode to set.
	 */
	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	/**
	 * @return Returns the precis.
	 */
	public String getPrecis() {
		return precis;
	}

	/**
	 * @param precis The precis to set.
	 */
	public void setPrecis(String precis) {
		this.precis = precis;
	}

	/**
	 * @return Returns the shortTitle.
	 */
	public String getShortTitle() {
		return shortTitle;
	}

	/**
	 * @param shortTitle The shortTitle to set.
	 */
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	/**
	 * @return Returns the sponsorCode.
	 */
	public String getSponsorCode() {
		return sponsorCode;
	}

	/**
	 * @param sponsorCode The sponsorCode to set.
	 */
	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	/**
	 * @return Returns the statementOfPurpose.
	 */
	public String getStatementOfPurpose() {
		return statementOfPurpose;
	}

	/**
	 * @param statementOfPurpose The statementOfPurpose to set.
	 */
	public void setStatementOfPurpose(String statementOfPurpose) {
		this.statementOfPurpose = statementOfPurpose;
	}

	/**
	 * @return Returns the statusCode.
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return Returns the studyObjective.
	 */
	public StudyObjective getStudyObjective() {
		return studyObjective;
	}

	/**
	 * @param studyObjective The studyObjective to set.
	 */
	public void setStudyObjective(StudyObjective studyObjective) {
		this.studyObjective = studyObjective;
	}

	/**
	 * @return Returns the suspensionDate.
	 */
	public Date getSuspensionDate() {
		return suspensionDate;
	}

	/**
	 * @param suspensionDate The suspensionDate to set.
	 */
	public void setSuspensionDate(Date suspensionDate) {
		this.suspensionDate = suspensionDate;
	}

	/**
	 * @return Returns the targetAccrualNumber.
	 */
	public Long getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	/**
	 * @param targetAccrualNumber The targetAccrualNumber to set.
	 */
	public void setTargetAccrualNumber(Long targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

}
