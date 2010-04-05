package gov.nih.nci.rembrandt.workspace;

import gov.nih.nci.caintegrator.dto.query.QueryType;
import gov.nih.nci.rembrandt.dto.query.Query;
import gov.nih.nci.rembrandt.queryservice.QueryHandlerInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkspaceQuery extends Query implements Iterable {
	private ArrayList<Query> folderList = new ArrayList<Query>();
	
	public WorkspaceQuery()
	{
		super();
	}
	
	public WorkspaceQuery( String name )
	{
		super();
		setQueryName( name );
	}
	
	public void addLeaf( Query query )
	{
		folderList.add( query );
	}

	public ArrayList<Query> getFolderList() {
		return folderList;
	}
	
	public Iterator<Query> iterator()
	{
		return folderList.iterator();
	}
	
	public QueryHandlerInterface getQueryHandler() throws Exception
	{
		return null;
	}
    
    public QueryType getQueryType() throws Exception
    {
    	return null;
    }
    
    public String getName()
    {
    	return getQueryName();
    }

    public String toString()
    {
    	return null;
    }
	

}
