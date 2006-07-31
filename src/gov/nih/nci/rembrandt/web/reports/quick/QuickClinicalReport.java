package gov.nih.nci.rembrandt.web.reports.quick;

import gov.nih.nci.rembrandt.queryservice.resultset.sample.SampleResultset;
import gov.nih.nci.rembrandt.queryservice.validation.ClinicalDataValidator;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class QuickClinicalReport {

	public static StringBuffer quickSampleReport(List<String> sampleIds){
		StringBuffer html = new StringBuffer();
		Document document = DocumentHelper.createDocument();
		String dv = "--";
		
		if(sampleIds != null)	{
			Map<String, SampleResultset> sampleResultsetMap;
			try {
				sampleResultsetMap = ClinicalDataValidator.getClinicalAnnotationsMapForSamples(sampleIds);
				if(sampleResultsetMap != null  && sampleIds != null){
					int count = 0;

					Element table = document.addElement("table").addAttribute("id", "reportTable").addAttribute("class", "report");
					Element tr = null;
					Element td = null;
					tr = table.addElement("tr").addAttribute("class", "header");
					td = tr.addElement("td").addAttribute("class", "header").addText("Sample ID");
					td = tr.addElement("td").addAttribute("class", "header").addText("Disease");
					td = tr.addElement("td").addAttribute("class", "header").addText("Gender");
					td = tr.addElement("td").addAttribute("class", "header").addText("Age");
					td = tr.addElement("td").addAttribute("class", "header").addText("Survival Length");
					
					for(String sampleId:sampleIds){
						SampleResultset sampleResultset = sampleResultsetMap.get(sampleId);
						//lose this
						if(sampleResultset != null && sampleResultset.getSampleIDDE()!= null)	{
							System.out.println(++count+" SampleID :" +sampleResultset.getSampleIDDE().getValue().toString());
						}
						//end lose
						if(sampleResultset!=null)	{
							tr = table.addElement("tr").addAttribute("class", "data");
							
							String sid = sampleResultset.getSampleIDDE()!=null && sampleResultset.getSampleIDDE().getValue() != null ?sampleResultset.getSampleIDDE().getValue().toString() : dv;
							td = tr.addElement("td").addText(sid);
							
							String dis = sampleResultset.getDisease() != null && sampleResultset.getDisease().getValue() != null ?sampleResultset.getDisease().getValue().toString() : dv;
							td = tr.addElement("td").addText(dis);
							
							String gen = sampleResultset.getGenderCode() != null && sampleResultset.getGenderCode().getValue() != null ? sampleResultset.getGenderCode().getValue().toString() : dv;
							td = tr.addElement("td").addText(gen);
							
							String age = sampleResultset.getAgeGroup() != null && sampleResultset.getAgeGroup().getValue() != null ? sampleResultset.getAgeGroup().getValue().toString() : dv;
							td = tr.addElement("td").addText(age);
							
							String slength = sampleResultset.getSurvivalLength() != null ? String.valueOf(sampleResultset.getSurvivalLength()) : dv;
							td = tr.addElement("td").addText(slength);
							
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html.append(document.asXML());
	}
}
