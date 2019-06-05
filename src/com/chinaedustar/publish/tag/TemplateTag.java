package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.chinaedustar.template.TemplateFactory;

/**
 * 在页面上面定义一个模板，此模板可以在后面被引用。
 * 
 * <p>
 * 在 JSP 页面写法: < pub:template name="template-name" >
 * </p>
 * 
 * <p>
 * 定义模板具有的属性：<br>
 *   name - string，此模板的名字，可以被 apply_template 引用时使用的名字。<br>
 *   <br>
 *   (optional) cache - boolean, 是否缓存此模板。<br>
 *   (reserved) engine - string, 解释此模板使用的引擎。<br>
 *   
 *   子标签:
 *   (reserved) param name='parameter-name' - 定义模板的参数。
 * </p>
 * 
 * @author liujunxing
 */
public class TemplateTag extends AbstractTemplateBaseTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6537690297316357427L;

	/** 为模板定义的名字。 */
	private String name;
	
	/** 是否调试输出这个模板内容。 */
	private boolean debug_output;

	/** name getter */
	public String getName() {
		return this.name;
	}
	
	/** name setter */
	public void setName(String name) {
		this.name = name;
	}
	
	/** debug getter */
	public boolean getDebug() {
		return this.debug_output;
	}
	
	/** debug setter */
	public void setDebug(boolean val) {
		this.debug_output = val;
	}
	
	/**
	 * 被 Servlet 容器调用，当处理开始标签时。
	 */
    public int doStartTag() throws JspException {
    	// 将标记内的内容全部缓存起来，doEndTag() 时候要使用。
    	return EVAL_BODY_BUFFERED;
    }

    /**
     * 被 Servlet 容器调用，当处理结束标签时。
     */
    public int doEndTag() throws JspException {
    	// 将标记里面所有内容都做为模板看待，获得该模板，然后编译。
    	String template_content = super.getBufferedBody();
    	
    	TemplateFactory factory = super.getTemplateFactory();
    	TemplateWrapper tw = new TemplateWrapper(factory, name, template_content);

    	// 将模板存放到模板容器中。
    	super.getPageTemplateContainer().putTemplate(tw);
    	
    	if (this.debug_output) {
    		JspWriter out = pageContext.getOut();
    		try {
    			out.write("<div style='border:1px solid gray;background:#ECECEC;width:95%;'>");
    			out.write(htmlEncode(template_content));
    			out.write("</div>");
    		} catch (java.io.IOException ex) {
    			throw new JspException(ex);
    		}
    	}
    	
        return EVAL_PAGE;
    }

}
