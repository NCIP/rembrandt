/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.dto.query;

import gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE;
import gov.nih.nci.caintegrator.dto.de.CloneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.ClusterTypeDE;
import gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE;
import gov.nih.nci.caintegrator.dto.de.GeneIdentifierDE;
import gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE;
import gov.nih.nci.caintegrator.dto.de.InstitutionDE;
import gov.nih.nci.caintegrator.dto.de.LinkageMethodTypeDE;
import gov.nih.nci.caintegrator.dto.query.HierarchicalClusteringQueryDTO;

import java.util.Collection;

/**
 * @author sahnih
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

public class HierarchicalClusteringQueryDTOImpl implements HierarchicalClusteringQueryDTO {
	private String queryName;
	private Collection<GeneIdentifierDE> geneIdentifierDEs;
	private Collection<CloneIdentifierDE> reporterIdentifierDEs;
	private ArrayPlatformDE arrayPlatformDE;
	private Collection<InstitutionDE> institutionDEs;
	private GeneVectorPercentileDE geneVectorPercentileDE;
	private DistanceMatrixTypeDE distanceMatrixTypeDE;
	private LinkageMethodTypeDE linkageMethodTypeDE;
	private ClusterTypeDE clusterTypeDE;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HierarchicalClusteringQueryDTOImpl() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return Returns the queryName.
	 */
	public String getQueryName() {
		return queryName;
	}


	/**
	 * @param queryName The queryName to set.
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getArrayPlatformDE()
	 */
	public ArrayPlatformDE getArrayPlatformDE() {
		return arrayPlatformDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setArrayPlatformDE(gov.nih.nci.caintegrator.dto.de.ArrayPlatformDE)
	 */
	public void setArrayPlatformDE(ArrayPlatformDE arrayPlatformDE) {
		this.arrayPlatformDE = arrayPlatformDE;
	}



	/**
	 * @return Returns the institutionDEs.
	 */
	public Collection<InstitutionDE> getInstitutionDEs() {
		return institutionDEs;
	}


	/**
	 * @param institutionDEs The institutionDEs to set.
	 */
	public void setInstitutionDEs(Collection<InstitutionDE> institutionDEs) {
		this.institutionDEs = institutionDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getGeneIdentifierDEs()
	 */
	public Collection<GeneIdentifierDE> getGeneIdentifierDEs() {
		return geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setGeneIdentifierDEs(java.util.Collection)
	 */
	public void setGeneIdentifierDEs(Collection<GeneIdentifierDE> geneIdentifierDEs) {
		this.geneIdentifierDEs = geneIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getGeneVectorPercentileDE()
	 */
	public GeneVectorPercentileDE getGeneVectorPercentileDE() {
		return geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setGeneVectorPercentileDE(gov.nih.nci.caintegrator.dto.de.GeneVectorPercentileDE)
	 */
	public void setGeneVectorPercentileDE(
			GeneVectorPercentileDE geneVectorPercentileDE) {
		this.geneVectorPercentileDE = geneVectorPercentileDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getReporterIdentifierDEs()
	 */
	public Collection<CloneIdentifierDE> getReporterIdentifierDEs() {
		return reporterIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setReporterIdentifierDEs(java.util.Collection)
	 */
	public void setReporterIdentifierDEs(
			Collection<CloneIdentifierDE> reporterIdentifierDEs) {
		this.reporterIdentifierDEs = reporterIdentifierDEs;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getClusterTypeDE()
	 */
	public ClusterTypeDE getClusterTypeDE() {
		return clusterTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setClusterTypeDE(gov.nih.nci.caintegrator.dto.de.ClusterTypeDE)
	 */
	public void setClusterTypeDE(ClusterTypeDE clusterTypeDE) {
		this.clusterTypeDE = clusterTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getDistanceMatrixTypeDE()
	 */
	public DistanceMatrixTypeDE getDistanceMatrixTypeDE() {
		return distanceMatrixTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setDistanceMatrixTypeDE(gov.nih.nci.caintegrator.dto.de.DistanceMatrixTypeDE)
	 */
	public void setDistanceMatrixTypeDE(DistanceMatrixTypeDE distanceMatrixTypeDE) {
		this.distanceMatrixTypeDE = distanceMatrixTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#getLinkageMethodTypeDE()
	 */
	public LinkageMethodTypeDE getLinkageMethodTypeDE() {
		return linkageMethodTypeDE;
	}


	/* (non-Javadoc)
	 * @see gov.nih.nci.rembrandt.dto.query.HierarchicalClusteringQueryDTO#setLinkageMethodTypeDE(gov.nih.nci.caintegrator.enumeration.LinkageMethodType)
	 */
	public void setLinkageMethodTypeDE(LinkageMethodTypeDE linkageMethodTypeDE) {
		this.linkageMethodTypeDE = linkageMethodTypeDE;
	}



}
