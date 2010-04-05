<%@ page import="java.util.*" %>
<%
if(session.getAttribute("sampleList")!= null)	{
	out.println("currently: <br/>");
	ArrayList mlist = (ArrayList) session.getAttribute("sampleList");
	for(int i=0; i<mlist.size(); i++)	{
		out.println(mlist.get(i)+"<br/>");
	}
}



	if(request.getParameter("firstName") != null)	{
	
		//debug
		if(request.getParameter("firstName").equals("DEBUG"))
			session.removeAttribute("sampleList");
		else	{	
			ArrayList lst;
			if(session.getAttribute("sampleList") != null)
				lst = (ArrayList) session.getAttribute("sampleList");
			else
				lst = new ArrayList();
		
			//add it to our list, if its not already there
			if(!lst.contains(request.getParameter("firstName")))
				lst.add(request.getParameter("firstName"));
				
			//put the list back into the session
			session.setAttribute("sampleList", lst);
			//out.println("I just put '"+request.getParameter("firstName")+"' into the list");
		}
	}
	else	{

%>
<html>
<body>
<applet code="AppletTest.class" width="600" height="600"
name="testApplet" id="testApplet" codebase="Applets">
<em>Your browser does not support Java!</em>
</applet> 

<!-- 
<jsp:plugin 
  type="applet" 
  code="AppletTest.class" 
  codebase="Applets" 
  jreversion="1.4" 
  width="600"
  height="600"
  name="testApplet"
  nspluginurl="http://java.sun.com/j2se/1.4.2/download.html" 
  iepluginurl="http://java.sun.com/j2se/1.4.2/download.html" >
  <jsp:fallback>
    <p>Unable to start plugin.</p>
  </jsp:fallback>
</jsp:plugin> 
-->
<div style="background-color:silver;"><br/><br/>
<script language="javascript">
var txt = document.getElementsByName("testApplet")[0].getAppletString();
	alert(txt);
	document.write(txt);
</script>
</div>
</body>
</html>
<%
	}
%>
