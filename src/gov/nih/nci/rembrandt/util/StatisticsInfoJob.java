/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.util;

import gov.nih.nci.rembrandt.dto.lookup.LookupManager;
import gov.nih.nci.rembrandt.dto.lookup.PatientDataLookup;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import java.util.HashSet;
import java.util.StringTokenizer;
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

/**
 * The StatisticsInfoJob class is the job to fetch statistics data from the 
 * Rembrandt database. This is a quartz job that will
 * be scheduled to run at scheduled time point.
 * <P>
 */
public class StatisticsInfoJob implements Job
{
	private static Logger logger = Logger.getLogger(StatisticsInfoJob.class);
	private static HashMap statisticsMap;
	private static Set<Long> institutionIds;

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		statisticsMap = getStatisticsMapData();
	}

	public HashMap getStatisticsMap() {
		if (statisticsMap == null)
		{
			statisticsMap = getStatisticsMapData();
		}

		return statisticsMap;
	}

	public void setStatisticsMap(HashMap statisticsMap) {
		StatisticsInfoJob.statisticsMap = statisticsMap;
	}
	private HashMap getStatisticsMapData(){
		HashMap tempMap = new HashMap();
		if (institutionIds == null){
			institutionIds = getInstituteIds();
		}
		try{
			Integer integer1 = LookupManager.getClinicalStudyParticipantCount(institutionIds);
			Integer integer2 = LookupManager.getClinicalSpecimenCount(institutionIds);
			
			HashMap map1 = new HashMap();
			map1.put("sample", integer1.toString());
			map1.put("specieman", integer2.toString());
			tempMap.put("Clinic Data",map1);
			
			Integer integer3 = LookupManager.getGESpecimenCount(institutionIds); // new Integer("34152");
			//Integer integer3 =  new Integer("34152");
			Integer integer4 = LookupManager.getGESampleCount(institutionIds);
			
			HashMap map2 = new HashMap();
			map2.put("sample", integer3.toString());
			map2.put("specieman", integer4.toString());
			tempMap.put("Gene Expression Data", map2);
			
			Integer integer5 = LookupManager.getCNSampleCount(institutionIds); // new Integer("34141"); //
			//Integer integer5 = new Integer("34141"); //

			Integer integer6 = LookupManager.getCNSpecimenCount(institutionIds);

			HashMap map3 = new HashMap();
			map3.put("sample", integer5.toString());
			map3.put("specieman", integer6.toString());
			tempMap.put("Copy Number Data", map3);
			IGVHelper igvHelper = new IGVHelper();
			File file = new File(igvHelper.getIgvClinicalFileName());
			if(file.exists()){
				file.delete();
			}
				igvHelper.createIGVSampleDataFile();				

			
			
			
			
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		
		return tempMap;
	}
	private Set<Long> getInstituteIds(){
		String instituteIds = System.getProperty("rembrandt.institute.ids");
		Set<Long> tempSet = new HashSet<Long>();
		StringTokenizer st = new StringTokenizer(instituteIds, ",");
		while (st.hasMoreTokens()){
			String id = st.nextToken();
			tempSet.add(Long.parseLong(id));
		}
		return tempSet;
	}

}
