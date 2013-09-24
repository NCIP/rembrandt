/*L
 * Copyright (c) 2006 SAIC, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/rembrandt/LICENSE.txt for details.
 */

package gov.nih.nci.rembrandt.web.servlets;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.domain.TreeNode;
import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.tree.DefaultMutableTreeNode;


public class GOTreeServlet extends HttpServlet	{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 	{
		doPost(req,res);  			//  Pass all GET request to the the doPost method
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
		
		HttpSession session = req.getSession();
		String rKey = "test_results";
		
		//testing - clear the fake cache
		String action = req.getParameter("action")!=null ? req.getParameter("action") : "none";
		if(action.equals("clear")){
			if(session.getAttribute(rKey)!=null)
				session.removeAttribute(rKey);
		}
		
		res.setContentType("text/html");	//  Set the content type of the response
		PrintWriter out=res.getWriter();	//  PrintWriter to write text to the response
		//	out.println("Hello World");		//  Write Hello World
		//	out.println(req.getParameter("a"));
		//	out.close();			//  Close the PrintWriter
		
		out.println("<html>\n<head>\n" +
				"<script type='text/javascript' src='/rembrandt/go/tree.js'></script>\n" +
				"<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"/rembrandt/go/tree.css\" />\n" +
				"</head><body>\n<ul class='tree'>\n");
		
		List results = null;
		
		// implement a fake cache for testing
		if (session.getAttribute(rKey)==null) {
			
			String serverURL = "http://cbioqa101.nci.nih.gov:49080/cacore31/http/remoteService";
			//#serverURL = http://cbioapp101.nci.nih.gov:49080/cacore31/http/remoteService
			//#serverURL = http://cabio.nci.nih.gov/cacore31/http/remoteService
	
			EVSQuery evsQuery = new EVSQueryImpl();
			String term = "biological_process";
			
			ApplicationService appService = ApplicationService.getRemoteInstance(serverURL);
			
			evsQuery.getTree("GO", term, true, false, 0, 2, null);
			try {
				results =(List) appService.evsSearch(evsQuery);
				session.setAttribute(rKey, results);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		else	{
			results = (List) session.getAttribute(rKey);
		}
		
		for(Object o : results)	{
			DefaultMutableTreeNode dn = (DefaultMutableTreeNode)o;
			DescLogicConcept theUserObject = (DescLogicConcept) dn.getUserObject();
			//out.println(theUserObject.getName() + " : " + theUserObject.getCode());
			if(dn.getChildCount() > 0)	{
				//out.println("<br/>children: " + dn.getChildCount());
				Enumeration nodePathEnum = dn.preorderEnumeration();
				
				//int nodeIndex = 0;
				//int nodeCounter = 0;
				
				while (nodePathEnum.hasMoreElements()) {
					DefaultMutableTreeNode myChildNode = (DefaultMutableTreeNode) nodePathEnum.nextElement();

					DescLogicConcept childObj = (DescLogicConcept) myChildNode.getUserObject();
					
				//	nodeIndex = myChildNode.getRoot().getIndex(myChildNode) + 1;
					
					if (childObj != null) {

						//always show the item
						out.write("<li class='closed'><a href='#' onclick=\"window.opener.pickGo(this.id);\" id='"+childObj.getCode()+"'>" + childObj.getName() + " : " + childObj.getCode() + "</a>\n");
				
						if(myChildNode.isLeaf()){ //has no children
							if(myChildNode.getNextSibling() == null)	{ //last sibling
								//out.println("</ul>\n");
								//look back and close the parents if theyre the last
								Enumeration e = myChildNode.pathFromAncestorEnumeration(myChildNode.getRoot());
								while (e.hasMoreElements()) {
									DefaultMutableTreeNode eparent = (DefaultMutableTreeNode) e.nextElement();
									
									if(eparent.getNextSibling() == null && eparent != myChildNode)	{
										out.write("</ul>\n");
									}
								}
							}
							out.write("</li>\n");
						}
						else if(!myChildNode.isLeaf())	{
							out.write("\n<ul>\n");
						}
						
						/********* NEW ******************/
						/*
						if(childObj.getHasParents())	{
							for(int i=0; i<myChildNode.getLevel(); i++)	{
								out.write("\t");
							}
						}
						if(childObj.getHasChildren())
							out.write("[+]");
						else
							out.write("[ ]");
						
						int test = -1;
						if(childObj.getHasParents())	{
							//test = myChildNode.getParent().getIndex(myChildNode);
							test = myChildNode.getRoot().getIndex(myChildNode.getParent()) + 1;
							test = myChildNode.getSiblingCount();
						}
					//	out.write(nodeCounter + "," + test + " ");
						
						
						String myId = childObj.getCode();
						out.println(childObj.getName() + " : " + myId);
						//nodeIndex++;
						//nodeCounter++;
						  
						*/ 
					}
				}
			}
			//out.println(results);
		}
		out.println("</ul>\n</body>\n</html>");
		out.close();
	}
}