/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.queryservice.resultset.gene;

import gov.nih.nci.caintegrator.dto.de.DatumDE;
import gov.nih.nci.caintegrator.dto.de.DiseaseNameDE;
import gov.nih.nci.caintegrator.dto.de.DomainElement;

/**
 * @author SahniH
 * Date: Oct 29, 2004
 * 
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

public class DiseaseGroupResultset implements Groupable, FoldChange{
	private DiseaseNameDE diseaseType = null;
	private DatumDE foldChangeRatioValue = null;
	private DatumDE foldChangeIntensity = null;
	private DatumDE foldChangeNonTumorIntensity = null;
	private DatumDE ratioPval = null;
	private DatumDE standardDeviationRatio;
	private DatumDE foldChangeLog2Intensity;
	/**
	 * @return Returns the diseaseType.
	 */
	public DiseaseNameDE getDiseaseType() {
		return this.diseaseType;
	}
	/**
	 * @param diseaseType The diseaseType to set.
	 */
	public void setDiseaseType(DiseaseNameDE diseaseType) {
		this.diseaseType = diseaseType;
	}
	/**
	 * @param disease
	 */
	public DiseaseGroupResultset(DiseaseNameDE diseaseType) {		
		setDiseaseType(diseaseType);
	}

	/**
	 * @return Returns the foldChangeRatioValue.
	 */
	public DatumDE getFoldChangeRatioValue() {
		return this.foldChangeRatioValue;
	}
	/**
	 * @param foldChangeRatioValue The foldChangeRatioValue to set.
	 */
	public void setFoldChangeRatioValue(DatumDE foldChangeRatioValue) {
		this.foldChangeRatioValue = foldChangeRatioValue;
	}
	/**
	 * @return Returns the ratioPval.
	 */
	public DatumDE getRatioPval() {
		return this.ratioPval;
	}
	/**
	 * @param ratioPval The ratioPval to set.
	 */
	public void setRatioPval(DatumDE ratioPval) {
		this.ratioPval = ratioPval;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#getType()
	 */
	public DomainElement getType() {
		return (DiseaseNameDE) getDiseaseType();
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.nautilus.resultset.ViewByGroupResultset#setType(gov.nih.nci.nautilus.de.DomainElement)
	 */
	public void setType(DomainElement type) throws Exception {
        if (! (type instanceof DiseaseNameDE) )
            throw new Exception ( "Could not set the value.  Parameter is of invalid data type: " + type);
        setDiseaseType((DiseaseNameDE)type);
	}


	/**
	 * @return Returns the foldChangeIntensity.
	 */
	public DatumDE getFoldChangeIntensity() {
		return foldChangeIntensity;
	}
	/**
	 * @param foldChangeIntensity The foldChangeIntensity to set.
	 */
	public void setFoldChangeIntensity(DatumDE foldChangeIntensity) {
		this.foldChangeIntensity = foldChangeIntensity;
	}
	/**
	 * @return Returns the foldChangeNonTumorIntensity.
	 */
	public DatumDE getFoldChangeNonTumorIntensity() {
		return foldChangeNonTumorIntensity;
	}
	/**
	 * @param foldChangeNonTumorIntensity The foldChangeNonTumorIntensity to set.
	 */
	public void setFoldChangeNonTumorIntensity(
			DatumDE foldChangeNonTumorIntensity) {
		this.foldChangeNonTumorIntensity = foldChangeNonTumorIntensity;
	}
	public DatumDE getStandardDeviationRatio() {
		return standardDeviationRatio;
	}
	public void setStandardDeviationRatio(DatumDE standardDeviationRatio) {
		this.standardDeviationRatio = standardDeviationRatio;
	}
	/**
	 * @return Returns the foldChangeLog2Intensity.
	 */
	public DatumDE getFoldChangeLog2Intensity() {
		return foldChangeLog2Intensity;
	}
	/**
	 * @param foldChangeLog2Intensity The foldChangeLog2Intensity to set.
	 */
	public void setFoldChangeLog2Intensity(DatumDE foldChangeLog2Intensity) {
		this.foldChangeLog2Intensity = foldChangeLog2Intensity;
	}
}
