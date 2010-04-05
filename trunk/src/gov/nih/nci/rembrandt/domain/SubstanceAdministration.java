package gov.nih.nci.rembrandt.domain;





/**
 * The schedule and route of applying, dispensing or giving agents or medications to subjects as prescribed within a
 * clinical trial protocol.
 * @created 18-Nov-2005 01:57:07 PM
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

public class SubstanceAdministration extends Activity {

	/**
	 * The total dose of study drug given to the patient in the time period encompassed by the duration.
	 */
	private Long doseQuantity;
	/**
	 * Unit of measurement (UOM) used to express the amount of agent used in dosing.
	 * NOTE: Includes 110 Values.
	 */
	private String doseUnitOfMeasure;
	/**
	 * Name of an access route for administration of agents, evaluation of vital signs, etc. Values include:  Gastrostomy Tube,
	 * CIV Continuous Intravenous Infusion, IA Intra-Arterial, ID Intradermal, IH  Intrahepatic, IHI  Intrahepatic Infusion,
	 * IM  Intramuscular, Inhalatn  Inhalation, IP  Intraperitoneal, IT  Intrathecal, IV  Intravenous Bolus, IVI  Intravenous
	 * Fusion, NASAL, NG  Nasogastric, Oph Each  Ophthalmic, Each Eye ;  Oph Left  Ophthalmic, Left Eye; Oph Rt  Ophthalmic,
	 * Right Eye; PO  Oral, PR  Rectal, RT  Radiation, SC  Subcutaneous, SWSP  Swish & Spit, SWSW  Swish & Swallow, TOP
	 * Topical, INTUM  Intratumoral. 
	 */
	private String routeCode;
	/**
	 * The description of the therapy schedule. Values include:
	 * STAT Immediately, QD Every Day, BID Twice A Day, QID Four Times A Day, BIW Twice A Week, AC Before Meals, Q4HR Every 4
	 * Hours, Q8HR Every 8 Hours, PRN As Needed, QOD Every Other Day, TID Three Times A Day, HS At Bedtime, TIW Three Times A
	 * Week, PC After Meals, Q6HR Every 6 Hours, Q12HR Every 12 Hours
	 */
	private String medicationSchedule;
	/**
	 * The number of regular recurrences in a given time. Values include: Daily, Weekly, Monthly, Yearly, Never, Unknown, Some
	 * days (1-2 DAYS), Refused to answer the question.
	 */
	private String frequency;
	/**
	 * Value to represent the total dose administered of an agent within one day. 
	 */
	private Long totalDailyDose;
	/**
	 * A description of the modification of the dose. Values include: Agent Added, Agent Dose Decreased, Agent Dose Increased,
	 * Agent Dropped, Course Added, Course Decreased, Course Dropped, Course Increased, Cycle/Rotation Added, Cycle/Rotation
	 * Decreased, Cycle/Rotation Dropped, Cycle/Rotation Increased, Regimen Interrupted, Therapy Discontinued.
	 * 
	 * NOTE: Also maps to Dose Change Type	 2008137.
	 */
	private String doseModificationType;
	/**
	 * Value to represent a change in the plan for treatment dosage.  The change may be known or unknown, as well as planned
	 * or unplanned. Values include: 9 Unknown, 3 No, 1 Yes Planned, 2 Yes Unplanned.
	 */
	public enum DoseChangeType {Unknown, No, Yes, Planned, Unplanned};
	private DoseChangeType doseChangeType;
	/**
	 * Value that represents the total dose of an agent.  
	 */
	private Long totalDoseQuantity;

	public SubstanceAdministration(){

	}

	/**
	 * @return Returns the doseChangeType.
	 */
	public DoseChangeType getDoseChangeType() {
		return doseChangeType;
	}

	/**
	 * @param doseChangeType The doseChangeType to set.
	 */
	public void setDoseChangeType(DoseChangeType doseChangeType) {
		this.doseChangeType = doseChangeType;
	}

	/**
	 * @return Returns the doseModificationType.
	 */
	public String getDoseModificationType() {
		return doseModificationType;
	}

	/**
	 * @param doseModificationType The doseModificationType to set.
	 */
	public void setDoseModificationType(String doseModificationType) {
		this.doseModificationType = doseModificationType;
	}

	/**
	 * @return Returns the doseQuantity.
	 */
	public Long getDoseQuantity() {
		return doseQuantity;
	}

	/**
	 * @param doseQuantity The doseQuantity to set.
	 */
	public void setDoseQuantity(Long doseQuantity) {
		this.doseQuantity = doseQuantity;
	}

	/**
	 * @return Returns the doseUnitOfMeasure.
	 */
	public String getDoseUnitOfMeasure() {
		return doseUnitOfMeasure;
	}

	/**
	 * @param doseUnitOfMeasure The doseUnitOfMeasure to set.
	 */
	public void setDoseUnitOfMeasure(String doseUnitOfMeasure) {
		this.doseUnitOfMeasure = doseUnitOfMeasure;
	}

	/**
	 * @return Returns the frequency.
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency The frequency to set.
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return Returns the medicationSchedule.
	 */
	public String getMedicationSchedule() {
		return medicationSchedule;
	}

	/**
	 * @param medicationSchedule The medicationSchedule to set.
	 */
	public void setMedicationSchedule(String medicationSchedule) {
		this.medicationSchedule = medicationSchedule;
	}

	/**
	 * @return Returns the routeCode.
	 */
	public String getRouteCode() {
		return routeCode;
	}

	/**
	 * @param routeCode The routeCode to set.
	 */
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	/**
	 * @return Returns the totalDailyDose.
	 */
	public Long getTotalDailyDose() {
		return totalDailyDose;
	}

	/**
	 * @param totalDailyDose The totalDailyDose to set.
	 */
	public void setTotalDailyDose(Long totalDailyDose) {
		this.totalDailyDose = totalDailyDose;
	}

	/**
	 * @return Returns the totalDoseQuantity.
	 */
	public Long getTotalDoseQuantity() {
		return totalDoseQuantity;
	}

	/**
	 * @param totalDoseQuantity The totalDoseQuantity to set.
	 */
	public void setTotalDoseQuantity(Long totalDoseQuantity) {
		this.totalDoseQuantity = totalDoseQuantity;
	}

}
