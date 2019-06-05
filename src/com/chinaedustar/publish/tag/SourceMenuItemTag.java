package com.chinaedustar.publish.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.chinaedustar.publish.comp.MenuItem;

public class SourceMenuItemTag extends ComponentTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8065453208139252582L;

	private String source;
	/**
	 * 获取菜单的来源。来源为存入pageContext attribute 中类型为MenuItem 或者 List<MenuItem>的数据对应的名称。
	 * @return
	 */
	public String getSource() {
		return source;
	}
	/**
	 * 设置菜单的来源。
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 当开始执行此标签时被调用。因为我们是数据定义，其不需要输出任何数据，从而
	 *   我们将内容全部缓冲起来，并在 doEndTag() 的时候吃掉所有内容，不显示出来。
	 */
	@Override
	public int doStartTag() throws JspException {
		Tag parent = super.getParent();
		if (parent instanceof MenuTag || parent instanceof SubMenuTag) 
			return EVAL_BODY_BUFFERED;
		
		throw new JspException("menuitem 标签必须被使用在 menu 或 submenu 标签里面。");
	}
	
	/**
	 * 在标签结束的时候被调用。
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int doEndTag() throws JspException {
		Object val = pageContext.getAttribute(source);
		if (val != null && val instanceof MenuItem || val instanceof List) {
			Tag parent = super.getParent();
			
			if (val instanceof MenuItem) {
				addMenuItem(val, parent);
			} else {
				List list = (List) val;
				for (int i = 0; i < list.size(); i++) {
					if (!(list.get(i) instanceof MenuItem)) {
						continue;
					} else {
						addMenuItem(list.get(i), parent);
					}
				}
			}
		}
		return EVAL_PAGE;
	}
	
	/**
	 * 添加菜单项到父标签中。
	 * @param obj
	 * @param parent
	 */
	private void addMenuItem(Object obj, Tag parent) {
		MenuItem menu_item = (MenuItem)obj;
		menu_item.setId(super.id);
		
		if (parent instanceof MenuTag) {
			((MenuTag)parent).getMenu().addMenuItem(-1, menu_item);
		} else if (parent instanceof SubMenuTag) {
			((SubMenuTag)parent).getMenu().addMenuItem(-1, menu_item);
		} 
	}

}
