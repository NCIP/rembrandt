package gov.nih.nci.nautilus.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.config.ModuleConfig;

public final class CheckLogin extends TagSupport
{

    public CheckLogin()
    {
        name = "logged";
        page = "/login.jsp";
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public int doStartTag()
        throws JspException
    {
        return 0;
    }

    public int doEndTag()
        throws JspException
    {
        boolean valid = false;
        HttpSession session = pageContext.getSession();
        if(session != null && session.getAttribute(name) != null)
            valid = true;
        if(valid)
            return 6;
        ModuleConfig config = (ModuleConfig)pageContext.getServletContext().getAttribute("org.apache.struts.action.MODULE");
        try
        {
            pageContext.forward(config.getPrefix() + page);
        }
        catch(ServletException e)
        {
            throw new JspException(e.toString());
        }
        catch(IOException e)
        {
            throw new JspException(e.toString());
        }
        return 5;
    }

    public void release()
    {
        super.release();
        name = "user";
        page = "/login.jsp";
    }

    private String name;
    private String page;
}
