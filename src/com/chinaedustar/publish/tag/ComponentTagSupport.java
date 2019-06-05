package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;

/**
 * (从 Struts 借鉴过来的，保留其类层次结构用)
 * 
 * @author liujunxing
 */
public abstract class ComponentTagSupport extends PublishBodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3143496054306049954L;
	// 让派生类一定实现这两个方法。
	public abstract int doStartTag() throws JspException;
	public abstract int doEndTag() throws JspException;
	
	/*
    protected Component component;

    public abstract Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res);

    public int doEndTag() throws JspException {
        component.end(pageContext.getOut(), getBody());
        component = null;
        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());

        if (evalBody) {
            return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    protected void populateParams() {
        component.setId(id);
    }

    public Component getComponent() {
        return component;
    }
    */
}