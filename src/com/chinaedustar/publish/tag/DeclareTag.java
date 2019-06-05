package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;

/**
 * 数据定义部分，其不产生实际输出，从而输出被忽略。
 * 
 * @author liujunxing
 */
public final class DeclareTag extends ComponentTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3362316386145964175L;

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		// 输出被忽略。
		return EVAL_PAGE;
	}

}
