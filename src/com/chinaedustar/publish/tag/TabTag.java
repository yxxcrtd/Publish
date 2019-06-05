package com.chinaedustar.publish.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import com.chinaedustar.publish.comp.Tab;

/**
 * 在网页上面定义一个属性页的标签实现类。
 * 
 * 语法: <pub:tab name="the-tab-name" text="display text" 
 *    template="the-template-used-to-display-content"
 *    provider="标签页的数据提供者，可选。" />
 * 
 * @author liujunxing
 */
public class TabTag extends ComponentTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8485116748095241660L;

	private Tab tab = new Tab();
	
	// === getter, setter =======================================================
	
	public String getProvider() {
		return tab.getProvider();
	}

	public void setProvider(String contentDataProvider) {
		tab.setProvider(contentDataProvider);
	}

	public String getTemplate() {
		return tab.getTemplate();
	}

	public void setTemplate(String contentTemplate) {
		tab.setTemplate(contentTemplate);
	}

	public String getName() {
		return tab.getName();
	}

	public void setName(String name) {
		tab.setName(name);
	}

	public String getText() {
		return tab.getText();
	}

	public void setText(String text) {
		tab.setText(text);
	}
	
	public boolean getDefault() {
		return tab.getDefault();
	}

	public void setDefault(boolean _default) {
		tab.setDefault(_default);
	}

	public boolean getDisabled() {
		return tab.getDisabled();
	}

	public void setDisabled(boolean _disabled) {
		tab.setDisabled(_disabled);
	}

	// === doStartTag, doEndTag =============================================== 
	
	@Override
	public int doStartTag() throws JspException {
		Tag tabs = super.getParent();
		if (tabs instanceof TabsTag) {
			return EVAL_BODY_BUFFERED;
		}
		throw new JspException("tab 标签必须被使用在 tabs 标签里面。");
	}
	
	@Override
	public int doEndTag() throws JspException {
		Tag parent = super.getParent();
		if (parent instanceof TabsTag) {
			((TabsTag)parent).getTabs().add(tab);
		} else {
			throw new JspException("tab 标签必须被使用在 tabs 标签里面。");
		}
		tab = new Tab();
		return EVAL_PAGE;		
	}
}
