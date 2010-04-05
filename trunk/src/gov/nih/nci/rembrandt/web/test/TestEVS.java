package gov.nih.nci.rembrandt.web.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.tree.DefaultMutableTreeNode;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.system.applicationservice.ApplicationService;

public class TestEVS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	   //String serverURL = "http://cbioqa101.nci.nih.gov:49080/cacore31/http/remoteService";
        //String serverURL = "http://cbioapp101.nci.nih.gov:49080/cacore31/http/remoteService";
        String serverURL = "http://cabio.nci.nih.gov/cacore31/http/remoteService";

        EVSQuery evsQuery = new EVSQueryImpl();
        String term = "biological_process";
        
        ApplicationService appService = ApplicationService.getRemoteInstance(serverURL);
        List results = null;
        
        evsQuery.getTree("GO", term, true, false, 0, "5", null);
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
        //out.println(theUserObject.getName() + " : " + theUserObject.getCode());
        searchTree(dn);

	}
    
    public static void searchTree(DefaultMutableTreeNode dn){ 
        System.out.println("<ul>");
        searchWholeTree(dn);
       System.out.println("</ul>");
    }
    
    public static void searchWholeTree(DefaultMutableTreeNode cn){
        if(cn!=null ){
                DescLogicConcept theUserObject = (DescLogicConcept) cn.getUserObject();
                
                if(theUserObject.getHasChildren()){
                    System.out.println("<li>" + theUserObject.getName() + "</li>\n<ul>");
                    if(!cn.isRoot()){
                        System.out.println("will be filled</ul>");
                    }
                    if(cn.getNextSibling()==null && !cn.isRoot()){
                        System.out.println("</ul>");
                    }
                    searchWholeTree(cn.getNextNode());
                }
                else{
                    System.out.println("<li>" + theUserObject.getName() + "</li>");
                    if(cn.getNextSibling()==null && !cn.isRoot()){
                        System.out.println("</ul>");
                    }
                    searchWholeTree(cn.getNextNode());
                }
           }
        
        }
      
        
    
}
