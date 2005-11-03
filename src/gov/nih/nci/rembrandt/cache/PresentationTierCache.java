package gov.nih.nci.rembrandt.cache;

import gov.nih.nci.caintegrator.dto.view.View;
import gov.nih.nci.caintegrator.ui.graphing.data.CachableGraphData;
import gov.nih.nci.rembrandt.dto.query.CompoundQuery;
import gov.nih.nci.rembrandt.queryservice.resultset.kaplanMeierPlot.KaplanMeierPlotContainer;
import gov.nih.nci.rembrandt.web.bean.ReportBean;
import gov.nih.nci.rembrandt.web.bean.SessionCriteriaBag;
import gov.nih.nci.rembrandt.web.bean.SessionQueryBag;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface PresentationTierCache {

	public void putSessionCriteriaBag(String sessionId,
			SessionCriteriaBag theBag);

	public SessionCriteriaBag getSessionCriteriaBag(String sessionId);

	public void addSessionGraphingData(String sessionId,
			CachableGraphData graphData);

	public CachableGraphData getSessionGraphingData(String sessionId,
			String graphId);

	public Collection<ReportBean> getAllReportBeans(String sessionId);

	public Collection getAllSampleSetReportBeans(String sessionId);

	public SessionQueryBag getSessionQueryBag(String sessionId);

	public ReportBean getReportBean(String sessionId, String queryName,
			View view);

	public ReportBean getReportBean(String sessionId, String queryName);

	public Object getObjectFromSessionCache(String sessionId, String key);

	public String getTempReportName(String sessionId);

	public Collection checkLookupCache(String lookupType);

	public boolean removeSessionCache(String sessionId);

	public void putSessionQueryBag(String sessionId, SessionQueryBag theBag);

	public void addToApplicationCache(Serializable key, Serializable value);

	public CompoundQuery getQuery(String sessionId, String queryName);

	public List getSampleSetNames(String sessionId);

	public String[] getCacheList();
	
	public void addToSessionCache(String id, String string, Serializable object);
	
}

	