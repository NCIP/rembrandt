package gov.nih.nci.rembrandt.web.bean;

import gov.nih.nci.caintegrator.service.findings.Finding;

import java.io.Serializable;

import org.dom4j.Document;

public class FindingReportBean implements Serializable {
	
	private Document xmlDoc;
	private Finding finding;
	
	public FindingReportBean()	{}

	public Finding getFinding() {
		return finding;
	}

	public void setFinding(Finding finding) {
		this.finding = finding;
	}

	public Document getXmlDoc() {
		return xmlDoc;
	}

	public void setXmlDoc(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
	}
}
