package gov.nih.nci.rembrandt.util;

import java.io.IOException; 
import java.net.MalformedURLException; 

import javax.servlet.Filter; 
import javax.servlet.FilterConfig; 
import javax.servlet.FilterChain; 
import javax.servlet.ServletException; 
import javax.servlet.ServletRequest; 
import javax.servlet.ServletResponse; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpServletResponseWrapper; 

/** 
 * Prevents the application from setting HTTP headers that include 
 * CR or LF characters. Specifically throws a MalformedURLException 
 * for the sendRedirect method which is already declared to throw 
 * IOException. 
 */ 
public class HttpResponseSplittingPreventionFilter 
    implements Filter 
{ 
    public void init(FilterConfig config) 
    { 
    } 

    /** 
     * Performs the filtering operation provided by this filter. 
     * 
     * @param request The request being made to the server. 
     * @param response The response object prepared for the client. 
     * @param chain The chain of filters providing request services. 
     */ 
    public void doFilter(ServletRequest request, 
                         ServletResponse response, 
                         FilterChain chain) 
        throws IOException, ServletException 
    { 
        String requestUrl = ((HttpServletRequest)request).getRequestURL().toString();
        
        if(containsCRorLF(requestUrl)) 
        	//request.getRequestDispatcher("/login.do").forward(request, response);
        	((HttpServletResponse)response).sendRedirect( ((HttpServletRequest)request).getContextPath() + "/login.do");
        else
        	chain.doFilter(request, response); 
    } 

    /** 
     * Called by the servlet container to indicate that a filter is being 
     * taken out of service.<p> 
     */ 
    public void destroy() 
    { 
    } 

    private boolean containsCRorLF(String s) 
    { 
        if(null == s) return false; 

        if ( s.toLowerCase().contains("%0d") || s.toLowerCase().contains("%0a") )
        	return true;

        return false; 
    } 
    
} 

