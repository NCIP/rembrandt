package gov.nih.nci.rembrandt.web.servlets;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.DefaultMutableTreeNode;

public class GOTreeServlet extends HttpServlet	{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 	{
		doPost(req,res);  			//  Pass all GET request to the the doPost method
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException	{
		res.setContentType("text/html");	//  Set the content type of the response
		PrintWriter out=res.getWriter();	//  PrintWriter to write text to the response
		//	out.println("Hello World");		//  Write Hello World
		//	out.println(req.getParameter("a"));
		//	out.close();			//  Close the PrintWriter
		
		String serverURL = "http://cbioqa101.nci.nih.gov:49080/cacore31/http/remoteService";
		//#serverURL = http://cbioapp101.nci.nih.gov:49080/cacore31/http/remoteService
		//#serverURL = http://cabio.nci.nih.gov/cacore31/http/remoteService

		EVSQuery evsQuery = new EVSQueryImpl();
		String term = "biological_process";
		
		ApplicationService appService = ApplicationService.getRemoteInstance(serverURL);
		List results = null;
		
		evsQuery.getTree("GO", term, true, false, 0, 3, null);
         long l1 = 0;
		try {
            GregorianCalendar gc1 = new GregorianCalendar();
            Date d1 = gc1.getTime();
            l1 = d1.getTime();
            
			results =(List) appService.evsSearch(evsQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        GregorianCalendar gc2 = new GregorianCalendar();
        Date d2 = gc2.getTime();
        long l2 = d2.getTime();
        
        long difference = l2 - l1;
        long seconds = difference/1000;
        System.out.println("Elapsed seconds: " + seconds);
        
		DefaultMutableTreeNode dn = (DefaultMutableTreeNode)results.get(0);
		DescLogicConcept theUserObject = (DescLogicConcept) dn.getUserObject();
		out.println(theUserObject.getName() + " : " + theUserObject.getCode());
		//out.println(results);

		out.close();
	}
}