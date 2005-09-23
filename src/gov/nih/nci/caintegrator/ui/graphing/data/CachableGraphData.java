package gov.nih.nci.caintegrator.ui.graphing.data;

import java.io.Serializable;

public interface CachableGraphData extends Serializable{
	public void setId(String id);
	public Object getDataset();
	public String getId();
}