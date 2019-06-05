package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.chinaedustar.publish.impl.JsptagTemplateProcessEnvironment;
import com.chinaedustar.publish.itfc.WebContext;
import com.chinaedustar.template.ProcessEnvironment;
import com.chinaedustar.template.Template;
import com.chinaedustar.template.TemplateException;

/**
 * 执行一个模板的标签。
 * 
 * <pre>
 * 页面使用语法：
 *  <pub:process_template name="the_template_name" />
 * </pre>
 * 
 * @author liujunxing
 *
 */
public class ProcessTemplateTag extends AbstractTemplateBaseTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2047845802113292072L;
	
	/** 要执行的模板名字。 */
	private String name;
	
	/**
	 * 构造函数。
	 *
	 */
	public ProcessTemplateTag() {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 被 Servlet 容器调用，当处理开始标签时。
	 */
    public int doStartTag() throws JspException {
    	// 都在 doEndTag() 的时候完成。
    	return EVAL_BODY_INCLUDE;
    }

    /**
     * 被 Servlet 容器调用，当处理结束标签时。
     */
    public int doEndTag() throws JspException {
    	try {
	    	JspWriter out = pageContext.getOut();
	    	TemplateWrapper tw = super.getPageTemplateContainer().getTemplateWrapper(this.name);
	    	if (tw == null) {
	    		out.write("<h3><font color='red'>调用的名为 '" + this.name + "' 的模板未找到</font></h3>");
	    	} else {
	    		Template templ = tw.getTemplate();
	    		WebContext webContext = new WebContext(super.pageContext);
	    		ProcessEnvironment env = JsptagTemplateProcessEnvironment.createInstance(webContext); 
	    		try {
	    			String result = env.process(templ, null);
		    		out.write(result);
	    		} catch (TemplateException ex) {
	    			out.write("模板执行错误，请查看日志以得到详细信息。" + ex.getMessage());
	    		}
	    	}
    	} catch (java.io.IOException ex) {
    		throw new JspException(ex);
    	}
        return EVAL_PAGE;
    }
}
