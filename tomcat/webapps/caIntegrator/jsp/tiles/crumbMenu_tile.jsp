<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.util.*, gov.nih.nci.nautilus.query.*,gov.nih.nci.nautilus.constants.Constants" %> 

<%
	/*
	* generates the crumb menu
	*
	*/

%>
  
<DIV class="crumb">	
               <a class="possible" href="home.do">Search</A>
               <A class="possible" href="menu.do">Build Query</A>
	            <%
	            QueryCollection queryCollection = (QueryCollection) request.getSession().getAttribute(Constants.QUERY_KEY);
	            if(queryCollection == null){
	              System.out.println("no query collection");
	              out.println("<a class='notPossible'>Refine Query</a>");
	              }
	            else{
	            System.out.println("there is a query collection");
	            out.println("<A class='possible' href='refinecheck.do'>Refine Query</A>");
	            }
	            %>
	          <!--select presentation to be implemented post-nautilus-->  
	           <!--<%
	           if(queryCollection != null){
	               if(queryCollection.hasCompoundQuery()){		              
		              System.out.println("has compound query");
		              out.println("<A class='possible' href='compoundcheck.do'>Select Presentation</A>");
		              }
	               else{
	                   out.println("<A class='notPossible'>Select Presentation</A>");
	                   System.out.println("has no compound query");
	               }
	           }
	           else{
	                   out.println("<A class='notPossible'>Select Presentation</A>");
	                   System.out.println("has no compound query");
	           } 
				%>-->
                
				<span style="text-align:right;font-size:.85em;margin-left:280px;">Welcome, &nbsp;
				<% out.println(session.getAttribute("name")); %>&nbsp;|&nbsp;
				<a style="font-size:.85em;" href="logout.jsp">Logout</a></span>			
                
</DIV>
