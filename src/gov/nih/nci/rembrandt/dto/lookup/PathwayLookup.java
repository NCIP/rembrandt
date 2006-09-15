package gov.nih.nci.rembrandt.dto.lookup;

import java.util.List;

public interface PathwayLookup {

	public abstract String getDataSource();

	public abstract void setDataSource(String param);

	public abstract Object getPathwayDesc();

	public abstract void setPathwayDesc(Object param);

	public abstract Long getPathwayId();

	public abstract void setPathwayId(Long param);

	public abstract String getPathwayName();

	public abstract void setPathwayName(String param);

	public abstract List<String> getGeneSymbols();

	public abstract void setGeneSymbols(List<String> geneSymbols);

}