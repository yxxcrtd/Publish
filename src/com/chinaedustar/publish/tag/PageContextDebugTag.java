package com.chinaedustar.publish.tag;

import java.util.Enumeration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * 用于在页面上面输出 pageContext 信息的调试标记。
 * 
 * <p>
 * 页面用法： < pub:page_debug />
 * </p>
 * @author liujunxing
 *
 */
public class PageContextDebugTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8480062661373792788L;

	public PageContextDebugTag() {
		
	}
	
    public int doStartTag() throws JspException {
    	JspWriter out = pageContext.getOut();
    	try {
    		out.println("<h3>PageScope 变量</h3>");
    		out.println("<table border='1' width='100%' cellspace='1' cellpadding='2'>");
	    	Enumeration<String> vars = super.pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
	    	showVariables(vars, PageContext.PAGE_SCOPE);
	    	out.println("</table>");

    		out.println("<h3>RequestScope 变量</h3>");
    		out.println("<table border='1' width='100%' cellspace='1' cellpadding='2'>");
	    	vars = super.pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
	    	showVariables(vars, PageContext.REQUEST_SCOPE);
	    	out.println("</table>");

    		out.println("<h3>SessionScope 变量</h3>");
    		out.println("<table border='1' width='100%' cellspace='1' cellpadding='2'>");
	    	vars = super.pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
	    	showVariables(vars, PageContext.SESSION_SCOPE);
	    	out.println("</table>");
	    	
    		out.println("<h3>ApplicationScope 变量</h3>");
    		out.println("<table border='1' width='100%' cellspace='1' cellpadding='2'>");
	    	vars = super.pageContext.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE);
	    	showVariables(vars, PageContext.APPLICATION_SCOPE);
	    	out.println("</table>");
    	} catch (java.io.IOException ex) {
    		throw new JspException(ex);
    	}
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private void showVariables(Enumeration<String> vars, int scope) throws java.io.IOException {
    	JspWriter out = pageContext.getOut();
    	while (vars.hasMoreElements()) {
    		String var_name = vars.nextElement();
    		Object var = pageContext.getAttribute(var_name, scope);
    		out.write("<tr><td>");
    		out.write(var_name);
    		out.write("</td><td>");
    		out.write((var == null) ? "(null)" : var.toString());
    		out.println("</td></tr>");
    	}
    }
}
